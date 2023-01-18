package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.*;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;

public class BikeTourPlusCommonAPI {
  private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * Create bike tours based on requests and availabilities. Assign participants and guides to tours
   * and tour weeks.
   *
   * @author Andrew Kan
   * @return error string, empty if successful and all participants assigned tours
   */
  public static String initiateTourCreation() {
    String error = "";

    if (btp.numberOfBikeTours() > 0) {
      return "Bike tours already created";
    }

    int nextBikeTourId = 1; // used to assign ids to tours

    // Create tours for each guide
    for (Guide g : btp.getGuides()) {
      boolean[] onTourWeeks = new boolean[btp.getNrWeeks() + 1]; // holds guide availability

      // Assign participants in order, if they haven't been assigned yet
      for (Participant p : btp.getParticipants()) {
        if (!p.hasBikeTour()) {
          boolean assigned = false;
          int weekAvailableFrom = p.getWeekAvailableFrom();
          int weekAvailableUntil = p.getWeekAvailableUntil();
          int nrWeeks = p.getNrWeeks();

          // Check if participant can go on guide's existing tours (matches participant requests)
          for (BikeTour tour : g.getBikeTours()) {
            if (((tour.getEndWeek() - tour.getStartWeek() + 1) == nrWeeks)
                && (tour.getStartWeek() >= weekAvailableFrom)
                && (tour.getEndWeek() <= weekAvailableUntil)) {
              tour.addParticipant(p); // Assign participant to the existing tour
              p.assignParticipant();
              assigned = true;
              break;
            }
          }

          // If participant not assigned above, check if guide can run new tour for participant
          if (!assigned) {
            int weekCounter = 0; // counts consecutive available weeks

            for (int i = weekAvailableFrom; i <= weekAvailableUntil; i++) {
              if (i < onTourWeeks.length && !onTourWeeks[i]) { // Guide available for week i
                weekCounter++;

                // If guide available for # requested weeks
                if (weekCounter == nrWeeks) {
                  int startWeek = i - nrWeeks + 1;
                  // Create new tour and assign participant
                  BikeTour newTour = btp.addBikeTour(nextBikeTourId, startWeek, i, g);
                  nextBikeTourId++;
                  newTour.addParticipant(p);
                  p.assignParticipant();

                  // Mark guide as on tour for those weeks
                  for (int j = startWeek; j <= i; j++) {
                    onTourWeeks[j] = true;
                  }
                  break;
                }
              } else { // reset counter since trip needs consecutive weeks
                weekCounter = 0;
              }
            }
          }
        }
      }
    }

    // Check that all participants have been assigned a tour
    for (Participant p : btp.getParticipants()) {
      if (!p.hasBikeTour()) {
        error = "At least one participant could not be assigned to their bike tour";
        break;
      }
    }

    // Save system data to persistence layer
    try {
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      error += e.getMessage();
    }

    return error;

  }

  /**
   * Pay for a participant's trip if they have been assigned but have not started. AuthorizationCode
   * represents payment for trip.
   *
   * @author Andrew Kan
   * @param email email of participant to cancel trip
   * @param authorizationCode non-empty string representing payment
   * @return error string, empty if successful
   */
  public static String payForParticipantTrip(String email, String authorizationCode) {
    String error = "";

    // Get participant with email if exists
    Participant p;
    User tempUser = User.getWithEmail(email);
    if (tempUser instanceof Participant) {
      p = (Participant) tempUser;
    } else {
      return ("Participant with email address " + email + " does not exist");
    }

    // Check authorization code
    if (authorizationCode == null || authorizationCode.isEmpty()) {
      return "Invalid authorization code";
    }

    // Check participant state
    switch (p.getSm()) {
      case NotAssigned:
        return "The participant has not been assigned to their tour";
      case Cancelled:
        return "Cannot pay for tour because the participant has cancelled their tour";
      case Banned:
        return "Cannot pay for tour because the participant is banned";
      case Paid, Started, Finished:
        return "The participant has already paid for their tour";
    }

    // Add auth code for payment. Use transition to change state
    try {
      p.setAuthorizationCode(authorizationCode);
      p.pay();
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      error = e.getMessage();
    }
    return error;
  }

  /**
   * Start bike tours for a specified week. Changes participants' states if they have been assigned
   * trip but have not started. If participant has not paid when trip starts, they will be banned.
   *
   * @author Andrew Kan
   * @param weekNum week number to start trips
   * @return error string, empty if fully successful
   */
  public static String startTripsForWeek(int weekNum) {
    String error = "";

    // Input validation
    if (weekNum < 1) {
      return "Week number must be greater than 0";
    }
    if (weekNum > btp.getNrWeeks()) {
      return "Week number must be less than or equal to the number of weeks in the biking season";
    }

    // Go through each tour that starts on specified week
    for (BikeTour tour : btp.getBikeTours()) {
      if (tour.getStartWeek() == weekNum) {

        // Go through tour participants. Check state.
        // Start participant trip by changing state (if allowed)
        for (Participant p : tour.getParticipants()) {
          // Check participant state
          switch (p.getSm()) {
            case Started:
              error += "Cannot start tour because the participant has already started their tour\n";
              break;
            case Finished:
              error += "Cannot start tour because the participant has finished their tour\n";
              break;
            case Cancelled:
              error += "Cannot start tour because the participant has cancelled their tour\n";
              break;
            case Banned:
              error += "Cannot start tour because the participant is banned\n";
              break;
            case Paid, Assigned: // If not paid, participant will be banned
              try {
                p.goOnTour();
                BikeTourPlusPersistence.save();
              } catch (RuntimeException e) {
                error += e.getMessage();
              }
              break;
          }
        }
      }
    }
    return error;
  }

  /**
   * Finish a participant's trip if participant has started trip.
   *
   * @author Andrew Kan
   * @param email email of participant to cancel trip
   * @return error string, empty if successful
   */
  public static String finishParticipantTrip(String email) {
    String error = "";

    // Get participant with email if exists
    Participant p;
    User tempUser = User.getWithEmail(email);
    if (tempUser instanceof Participant) {
      p = (Participant) tempUser;
    } else {
      return ("Participant with email address " + email + " does not exist");
    }

    // Check participant state. Can only finish trip if trip started.
    switch (p.getSm()) {
      case NotAssigned, Assigned, Paid:
        return "Cannot finish a tour for a participant who has not started their tour";
      case Finished:
        return "Cannot finish tour because the participant has already finished their tour";
      case Cancelled:
        return "Cannot finish tour because the participant has cancelled their tour";
      case Banned:
        return "Cannot finish tour because the participant is banned";
    }

    // Use transition to change state
    try {
      p.finishTour();
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      error = e.getMessage();
    }
    return error;
  }

  /**
   * Cancel a participant's trip if they are not finished, cancelled, or banned.
   *
   * @author Andrew Kan
   * @param email email of participant to cancel trip
   * @return error string, empty if successful
   */
  public static String cancelParticipantTrip(String email) {
    String error = "";

    // Get participant with email if exists
    Participant p;
    User tempUser = User.getWithEmail(email);
    if (tempUser instanceof Participant) {
      p = (Participant) tempUser;
    } else {
      return ("Participant with email address " + email + " does not exist");
    }

    // Check participant state
    switch (p.getSm()) {
      case Finished:
        return "Cannot cancel tour because the participant has finished their tour";
      case Cancelled:
        return "Cannot cancel tour because the participant has already cancelled their tour";
      case Banned:
        return "Cannot cancel tour because the participant is banned";
    }

    // Use transition to change state
    try {
      p.cancel();
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      error = e.getMessage();
    }
    return error;
  }


}
