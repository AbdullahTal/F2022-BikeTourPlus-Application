package ca.mcgill.ecse.biketourplus.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.model.User;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BikeTourPlusFeatureSet4Controller {
  private static BikeTourPlus biketourplus = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * Registers a new guide
   *
   * @Author Ari Smith
   *
   * @param email- the guide's email
   * @param password- the guide's password
   * @param name - the guide's name
   * @param emergencyContact- the guide's emergency contact
   * @return error string, empty if successful
   */
  public static String registerGuide(String email, String password, String name,
      String emergencyContact) {
    // input validation
    var error = "";
    if (email == null || email == "") {
      error += "Email cannot be empty\n";
    } else if (email.indexOf("@") == 0 || email.indexOf("@") != email.lastIndexOf("@")
        || email.indexOf("@") > email.lastIndexOf(".") - 1
        || email.indexOf("@") == email.indexOf(".") - 1
        || email.lastIndexOf(".") >= email.length() - 1) {
      error += "Invalid email\n";
    } else if (!email.contains("@")) {
      error += "Invalid email\n";
    }
    if (email.contains(" ")) {
      error += "Email must not contain any spaces\n";
    }
    if (email.equals("manager@btp.com")) {
      error += "Email cannot be manager@btp.com\n";
    }
    if (name == null || name == "") {
      error += "Name cannot be empty\n";
    }
    if (password == null || password == "") {
      error += "Password cannot be empty\n";
    }
    if (emergencyContact == null || emergencyContact == "") {
      error += "Emergency contact cannot be empty\n";
    }

    for (Guide guide : biketourplus.getGuides()) {
      if (guide.getEmail().equals(email)) {
        error += "Email already linked to a guide account\n";
      }
    }
    for (Participant participant : biketourplus.getParticipants()) {
      if (participant.getEmail().equals(email)) {
        error += "Email already linked to a participant account\n";
      }
    }
    // call model
    if (error.isEmpty()) {
      try {
        biketourplus.addGuide(email, password, name, emergencyContact);
        BikeTourPlusPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }

    }
    return error.trim();
  }

  /**
   * Updates information associated with a guide
   *
   * @Author Ari Smith
   *
   * @param email- the guide's new email
   * @param newPassword- the guide's new password
   * @param newName- the guide's new name
   * @param newEmergencyContact- the guide's new emergency contact
   * @return error string, empty if successful
   */
  public static String updateGuide(String email, String newPassword, String newName,
      String newEmergencyContact) {
    // input validation
    var error = "";
    if (email == null || email == "") {
      error += "Email cannot be empty\n";
    }
    if (newName == null || newName == "") {
      error += "Name cannot be empty\n";
    }
    if (newPassword == null || newPassword == "") {
      error += "Password cannot be empty\n";
    }
    if (newEmergencyContact == null || newEmergencyContact == "") {
      error += "Emergency contact cannot be empty\n";
    }

    // call model
    if (error.isEmpty()) {
      try {
        boolean guideFound = false;
        for (Guide guide : biketourplus.getGuides()) {
          if (guide.getEmail().equals(email)) {
            guideFound = true;
            guide.setPassword(newPassword);
            guide.setName(newName);
            guide.setEmergencyContact(newEmergencyContact);
            BikeTourPlusPersistence.save();
          }

        }
        if (!guideFound) {
          error += "The guide account does not exist";
        }
      } catch (RuntimeException e) {
        return e.getMessage();
      }
    }
    return error.trim();
  }

  /**
   * Deletes the guide
   * 
   * @Author Ari Smith
   * 
   * @param email- the email of the guide to be deleted
   */
  public static String deleteGuide(String email) {
    boolean result = false;
    String error = "";

    // iterate through all guides to find the guide
    for (Guide guide : biketourplus.getGuides()) {
      if (guide.getEmail().equals(email)) {
        if (guide.hasBikeTours()) {
          return "Cannot delete guide assigned to tour";
        }
        guide.delete();
        result = biketourplus.removeGuide(guide);
        BikeTourPlusPersistence.save();
        break;
      }
    }

    if (!result)
      error += "Guide with email " + email + " does not exist";;
    return error;

  }

  /**
   * Returns list of guides in systems as transfer objects
   *
   * @return ObservableList of TOGuide objects for guides in system
   */
  public static ObservableList<TOGuide> getGuides() {
    List<Guide> guideList = biketourplus.getGuides();
    List<TOGuide> TOGuideList = new ArrayList<TOGuide>();;
    for (Guide guide : guideList) {
      String email = guide.getEmail();
      String password = guide.getPassword();
      String name = guide.getName();
      String emergencyContact = guide.getEmergencyContact();
      TOGuideList.add(new TOGuide(email, password, name, emergencyContact));
    }
    return FXCollections.observableList(TOGuideList);

  }

  /**
   * Retrieves guide with specified email
   *
   * @param email email of guide to get
   * @return TOGuide transfer object of specified guide
   */
  public static TOGuide getGuideFromEmail(String email) {
    if (User.getWithEmail(email) instanceof Guide) {
      Guide g = (Guide) User.getWithEmail(email);
      return new TOGuide(g.getEmail(), g.getPassword(), g.getName(), g.getEmergencyContact());
    } else {
      return null;
    }
  }

  /**
   * Returns list of guide emails.
   *
   * @return ObservableList of guide emails (String)
   */
  public static ObservableList<String> getAllGuideEmail() {
    List<Guide> guideList = biketourplus.getGuides();
    List<String> guideEmailList = new ArrayList<String>();;
    for (Guide guide : guideList) {
      String email = guide.getEmail();
      guideEmailList.add(email);
    }
    return FXCollections.observableList(guideEmailList);
  }

}
