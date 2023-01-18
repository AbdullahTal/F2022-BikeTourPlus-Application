package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.BookableItem;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class BikeTourPlusFeatureSet2Controller {

  private static final BikeTourPlus bikeTourPlus = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * Updates a BikeTourPlus' start date, number of weeks and price of guide per week
   *
   * @author Ali Tahmasebi
   * @param startDate new start date of the tour
   * @param nrWeeks new number of weeks of the tour
   * @param priceOfGuidePerWeek new price of the guide per week
   * @return error message string, empty if successful
   */
  public static String updateBikeTourPlus(Date startDate, int nrWeeks, int priceOfGuidePerWeek) {

    String errorMessage = "";

    int btpStartYear = bikeTourPlus.getStartDate().getYear();

    // check startDate length
    if (nrWeeks < 0) {
      errorMessage += "The number of riding weeks must be greater than or equal to zero";
    } else {
      if (priceOfGuidePerWeek < 0) {
        errorMessage += "The price of guide per week must be greater than or equal to zero";
      } else {
        if (startDate == null) {
          errorMessage += "There was an error with setting the data";
        } else if (startDate.getYear() < btpStartYear) {
          errorMessage += "The start date cannot be from previous year or earlier";
        } else {
          bikeTourPlus.setNrWeeks(nrWeeks);
          bikeTourPlus.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
          bikeTourPlus.setStartDate(startDate);
          try {
            BikeTourPlusPersistence.save();
          } catch (RuntimeException e) {
            return e.getMessage();
          }
        }
      }
    }

    return errorMessage;
  }

  /**
   * Delete a participant given their email
   *
   * @author Ali Tahmasebi
   * @param email participant's email that need to be deleted
   */
  public static String deleteParticipant(String email) {

    String errorMessage = "";

    // check valid email string input - this can further be put into a helper
    if (email == null || email.equals("")) {
      errorMessage += "Received empty email \n";
    } else if (email.contains(" ")) {
      errorMessage += "Email must not contain any spaces. Got: \"" + email + "\"\n";
    }

    else if (email.indexOf("@") <= 0 || email.indexOf("@") != email.lastIndexOf("@")
        || email.indexOf("@") >= email.lastIndexOf(".") - 1
        || email.lastIndexOf(".") >= email.length() - 1) {
      errorMessage +=
          "Invalid Email. Please enter an email in the \"example@btp.com\" format. Got: " + email
              + "\n";
    }


    if (errorMessage.isEmpty()) {
      try {
        List<Participant> participants = bikeTourPlus.getParticipants();
        int participantCount = participants.size();

        if (participantCount > 0) {
          int i = 0;
          while (i < participantCount) {
            Participant p = participants.get(i);
            if (Objects.equals(p.getEmail(), email)) {
              p.delete();
              try {
                BikeTourPlusPersistence.save();
              } catch (RuntimeException e) {
                System.out.println(e.getMessage());
              }
              // System.out.println("Successfully deleted participant with email: " + email);
              break;
            } else
              i++;
          }
          // errorMessage += "Could not find participant with email \"" + email + "\"\n";//
        }
      } catch (RuntimeException e) {
        System.out.println(errorMessage + "\n");
      }
    }

    return errorMessage;
  }

  // this method does not need to be implemented by a team with five team members
  /**
   * Delete a combo given the combo's name
   *
   * @author Ali Tahmasebi
   * @author Ari Smith (revision)
   * @param name name of the combo that need to be deleted
   * @return error message string, empty if no error
   */
  public static String deleteCombo(String name) {

    String error = "";

    if (name == null || Objects.equals(name, "")) {
      error += "Cannot handle empty combo name, please enter a valid name";
    }
    try {

      BookableItem item = BookableItem.getWithName(name);
      if (item instanceof Combo) {
        item.delete();
      } else {
        error += "Could not find combo with name \"" + name + "\"\n";
      }
      try {
        BikeTourPlusPersistence.save();
      } catch (RuntimeException e) {
        error += e.getMessage();

      }
    } catch (RuntimeException e) {
      return error;
    }
    return error;
  }

  /**
   * Returns transfer object of btp system
   *
   * @author Andrew Kan
   * @return TOBikeTourPlus object with system info
   */
  public static TOBikeTourPlus getBikeTourPlus() {
    return new TOBikeTourPlus(bikeTourPlus.getStartDate(), bikeTourPlus.getNrWeeks(),
        bikeTourPlus.getPriceOfGuidePerWeek());
  }
}
