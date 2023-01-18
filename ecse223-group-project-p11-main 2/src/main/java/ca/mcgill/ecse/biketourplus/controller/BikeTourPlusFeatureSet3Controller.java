package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;

import ca.mcgill.ecse.biketourplus.model.*;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;


public class BikeTourPlusFeatureSet3Controller {

  private static BikeTourPlus bikeTourPlus = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * Checks if the input information are valid
   * 
   * @author Annie Gouchee
   * @param password participant's password
   * @param name participant's name
   * @param emergencyContact participant's emergency contact
   * @param nrWeeks participant's number of available weeks
   * @param weekAvailableFrom participant's starting available week
   * @param weekAvailableUntil participant's ending available week
   * @return String containing error, else empty if all is valid
   */
  public static String hasValidInformation(String password, String name, String emergencyContact,
      int nrWeeks, int weekAvailableFrom, int weekAvailableUntil) {

    String error = "";

    if (name == null || name.equals("")) {
      error += "Name cannot be empty \n";
    }
    if (emergencyContact == null || emergencyContact.equals("")) {
      error += "Emergency contact cannot be empty \n";
    }
    if (password == null || password.equals("")) {
      error += "Password cannot be empty \n";
    }

    if (nrWeeks <= 0) {
      error += "Number of weeks must be greater than zero \n";
    }
    if (nrWeeks > bikeTourPlus.getNrWeeks()) {
      error +=
          "Number of weeks must be less than or equal to the number of biking weeks in the biking season \n";
    }

    if (weekAvailableUntil - weekAvailableFrom < nrWeeks - 1) {
      error += "Number of weeks must be less than or equal to the number of available weeks \n";
    }
    if (weekAvailableFrom > weekAvailableUntil) {
      error +=
          "Week from which one is available must be less than or equal to the week until which one is available \n";
    }

    if (weekAvailableFrom <= 0 || weekAvailableFrom > bikeTourPlus.getNrWeeks()
        || weekAvailableUntil <= 0 || weekAvailableUntil > bikeTourPlus.getNrWeeks()) {
      error += "Available weeks must be within weeks of biking season (1-"
          + bikeTourPlus.getNrWeeks() + ") \n";
    }

    return error;
  }

  /**
   * Registers new participant
   * 
   * @author Annie Gouchee
   * @param email participant's email
   * @param password participant's password
   * @param name participant's name
   * @param emergencyContact participant's emergency contact
   * @param nrWeeks participant's number of available weeks
   * @param weekAvailableFrom participant's starting available week
   * @param weekAvailableUntil participant's ending available week
   * @param lodgeRequired boolean indicating if a lodge is required
   * @return String containing error, else empty if all is valid
   */
  public static String registerParticipant(String email, String password, String name,
      String emergencyContact, int nrWeeks, int weekAvailableFrom, int weekAvailableUntil,
      boolean lodgeRequired) {

    String error = "";

    if (email.equals("manager@btp.com")) {
      error += "Email cannot be manager@btp.com \n";
    }

    User user = User.getWithEmail(email);
    if (user instanceof Guide) {
      error += "Email already linked to a guide account \n";
    }
    if (user instanceof Participant) {
      error += "Email already linked to a participant account \n";
    }

    if (email.contains(" ")) {
      error += "Email must not contain any spaces \n";
    }

    if (email.indexOf("@") <= 0 || email.indexOf("@") != email.lastIndexOf("@")
        || email.indexOf("@") >= email.lastIndexOf(".") - 1
        || email.lastIndexOf(".") >= email.length() - 1) {
      error += "Invalid email \n";
    }

    if (email == null || email.equals("")) {
      error += "Email cannot be empty \n";
    }
    error += hasValidInformation(password, name, emergencyContact, nrWeeks, weekAvailableFrom,
        weekAvailableUntil);

    if (!error.isEmpty()) {
      return error;
    }

    try {
      Participant p = new Participant(email, password, name, emergencyContact, nrWeeks,
          weekAvailableFrom, weekAvailableUntil, lodgeRequired, "", 0, bikeTourPlus);
      bikeTourPlus.addParticipant(p);
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }

    return error;
  }

  /**
   * Updates information of existing participant
   * 
   * @author Annie Gouchee
   * @param email participant's email
   * @param newPassword participant's new password
   * @param newName participant's new name
   * @param newEmergencyContact participant's new emergency contact
   * @param newNrWeeks participant's new number of available weeks
   * @param newWeekAvailableFrom participant's new starting available week
   * @param newWeekAvailableUntil participant's new ending available week
   * @param newLodgeRequired new boolean indicating if a lodge is required
   * @return String containing error, else empty if all is valid
   */
  public static String updateParticipant(String email, String newPassword, String newName,
      String newEmergencyContact, int newNrWeeks, int newWeekAvailableFrom,
      int newWeekAvailableUntil, boolean newLodgeRequired) {

    String error = "";

    User user = User.getWithEmail(email);
    if (user == null || user instanceof Guide || user instanceof Manager) {
      error += "The participant account does not exist \n";
    }

    error += hasValidInformation(newPassword, newName, newEmergencyContact, newNrWeeks,
        newWeekAvailableFrom, newWeekAvailableUntil);

    if (!error.isEmpty())
      return error;

    try {
      Participant p = (Participant) User.getWithEmail(email);
      p.setPassword(newPassword);
      p.setName(newName);
      p.setEmergencyContact(newEmergencyContact);
      p.setNrWeeks(newNrWeeks);
      p.setWeekAvailableFrom(newWeekAvailableFrom);
      p.setWeekAvailableUntil(newWeekAvailableUntil);
      p.setLodgeRequired(newLodgeRequired);
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      return e.getMessage();
    }

    return error;

  }

  /**
   * Adds a gear/combo to the participant
   * 
   * @author Annie Gouchee
   * @param email participant's email
   * @param bookableItemName name of the gear/combo to be added to the participant
   * @return String containing error, else empty if all is valid
   */
  public static String addBookableItemToParticipant(String email, String bookableItemName) {

    String error = "";

    User user = User.getWithEmail(email);
    if (!(user instanceof Participant)) {
      error += "The participant does not exist \n";
    }

    BookableItem item = BookableItem.getWithName(bookableItemName);
    if (item == null) {
      error += "The piece of gear or combo does not exist \n";
    }

    if (!error.isEmpty()) {
      return error;
    }

    try {
      Participant participant = (Participant) Participant.getWithEmail(email);
      int i = 0;
      for (BookedItem existingBooking : participant.getBookedItems()) {
        if (existingBooking.getItem().getName().equals(bookableItemName)) {
          existingBooking.setQuantity(existingBooking.getQuantity() + 1);
          i++;
        }
      }

      if (i == 0) {
        participant.addBookedItem(1, bikeTourPlus, item);
      }
      BikeTourPlusPersistence.save();

    } catch (RuntimeException e) {
      return e.getMessage();
    }

    return error;

  }

  /**
   * Removes a gear/combo from the participant
   * 
   * @author Annie Gouchee
   * @param email participant's email
   * @param bookableItemName name of gear/combo to be removed
   * @return String containing error, else empty if all is valid
   */
  public static String removeBookableItemFromParticipant(String email, String bookableItemName) {

    String error = "";

    User user = User.getWithEmail(email);
    if (!(user instanceof Participant)) {
      error += "The participant does not exist /n";
    }

    BookableItem item = BookableItem.getWithName(bookableItemName);
    if (item == null) {
      error += "The piece of gear or combo does not exist /n";
    }

    if (!error.isEmpty()) {
      return error;
    }

    try {
      Participant p = (Participant) Participant.getWithEmail(email);
      for (BookedItem participantItem : p.getBookedItems()) {
        if (participantItem.getItem().getName().equals(bookableItemName)) {
          if (participantItem.getQuantity() == 1) {
            participantItem.delete();
          } else {
            participantItem.setQuantity(participantItem.getQuantity() - 1);
          }
          BikeTourPlusPersistence.save();
          return error;
        }
      }
      error += "The participant does not have the piece of gear or combo";
    } catch (RuntimeException e) {
      return e.getMessage();

    }

    return error;
  }


}
