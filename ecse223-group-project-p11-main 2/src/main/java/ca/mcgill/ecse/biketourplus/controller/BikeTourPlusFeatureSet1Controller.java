package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.model.*;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ca.mcgill.ecse.biketourplus.controller.*;
import java.util.ArrayList;
import java.util.List;

public class BikeTourPlusFeatureSet1Controller {
  private static BikeTourPlus bikeTourPlus = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * Updates manager's password
   * 
   * @author Martin Nguyen
   * @param password new manager's password
   * @return an empty error string if successful
   */
  public static String updateManager(String password) {
    // CONSTRAINTS
    // length check
    String errorText = "";
    if (password.length() == 0) {
      errorText += "Password cannot be empty\n";
    }
    if (password.length() <= 3) {
      errorText += "Password must be at least four characters long\n";
    }
    // uppercase check
    boolean containsUpperCase = false;
    for (int i = 0; i < password.length(); i++) {
      if (Character.isUpperCase(password.charAt(i))) {
        containsUpperCase = true;
        break;
      }
    }
    if (!containsUpperCase) {
      errorText += "Password must contain one upper-case character\n";
    }
    // lowercase check
    boolean containsLowerCase = false;
    for (int i = 0; i < password.length(); i++) {
      if (Character.isLowerCase(password.charAt(i))) {
        containsLowerCase = true;
        break;
      }
    }
    if (!containsLowerCase) {
      errorText += "Password must contain one lower-case character\n";
    }
    // special characters check
    boolean containsSpecial = false;
    for (int i = 0; i < password.length(); i++) {
      char currentChar = password.charAt(i);
      if (currentChar == '$' || currentChar == '!' || currentChar == '#') {
        containsSpecial = true;
        break;
      }
    }
    if (!containsSpecial) {
      errorText += "Password must contain one character out of !#$\n";
    }


    if (errorText.isEmpty()) {
      boolean error = !(bikeTourPlus.getManager().setPassword(password));
      try {
        BikeTourPlusPersistence.save();
      } catch (RuntimeException e) {
        return e.getMessage();
      }
      if (error) {
        errorText += "Failure changing password\n";
        return errorText;
      }
    } else {
      return errorText;

    }

    return errorText;
  }

  /**
   * Get a bike tour by id
   * 
   * @author Martin Nguyen
   * @param id Bike tour's id
   * @return a TOBikeTour of the requested bike tour
   */
  public static TOBikeTour getBikeTour(int id) {
    BikeTour bikeTour = BikeTour.getWithId(id);
    if (bikeTour == null) {
      return null;
    }

    int aStartWeek = bikeTour.getStartWeek();
    int aEndWeek = bikeTour.getEndWeek();
    Guide guide = bikeTour.getGuide();
    String aGuideEmail = guide.getEmail();
    String aGuideName = guide.getName();

    List<Participant> allParticipants = bikeTour.getParticipants();
    List<TOParticipantCost> allParticipantsCost = new ArrayList<>();

    int numTourWeeks = aEndWeek - aStartWeek + 1;
    int aTotalCostForGuide = numTourWeeks * bikeTourPlus.getPriceOfGuidePerWeek();

    for (Participant participant : allParticipants) {
      int aTotalCostForBikingTour = aTotalCostForGuide;
      List<BookedItem> bookedItems = participant.getBookedItems();
      int aTotalCostForBookableItems = 0;
      for (BookedItem bookedItem : bookedItems) {
        BookableItem bookableItem = bookedItem.getItem();
        int quantity = bookedItem.getQuantity();
        if (bookableItem instanceof Gear) { // check if Gear
          aTotalCostForBookableItems += ((Gear) bookableItem).getPricePerWeek() * quantity;
        } else if (bookableItem instanceof Combo) { // check if Combo
          List<ComboItem> comboItems = ((Combo) bookableItem).getComboItems();
          int comboCost = 0;
          for (ComboItem comboItem : comboItems) {
            comboCost += comboItem.getGear().getPricePerWeek() * comboItem.getQuantity();
          }
          if (participant.getLodgeRequired()) { // give combo discount
            comboCost *= (double) (100 - ((Combo) bookableItem).getDiscount()) / 100;
          }
          aTotalCostForBookableItems += comboCost * quantity;
        }
      }
      aTotalCostForBookableItems *= numTourWeeks; // price over total tour weeks
      aTotalCostForBikingTour += aTotalCostForBookableItems;
      TOParticipantCost toParticipantCost =
          new TOParticipantCost(participant.getEmail(), participant.getName(),
              aTotalCostForBookableItems, aTotalCostForBikingTour, participant.getSmFullName(),
              participant.getAuthorizationCode(), participant.getRefundedPercentageAmount());
      allParticipantsCost.add(toParticipantCost);
    }
    return new TOBikeTour(id, aStartWeek, aEndWeek, aGuideEmail, aGuideName, aTotalCostForGuide,
        allParticipantsCost.toArray(new TOParticipantCost[0]));
  }


  /**
   * Get all bike tours in system.
   *
   * @author Andrew Kan
   * @return List of TOBikeTour objects in system
   */
  public static List<TOBikeTour> getAllBikeTours() {
    var items = new ArrayList<TOBikeTour>();
    for (int i = 1; i <= bikeTourPlus.numberOfBikeTours(); i++) {
      items.add(getBikeTour(i));
    }
    return items;
  }

  /**
   * Returns the number of bike tours in the system
   *
   * @author Andrew Kan
   * @return number of bike tours
   */
  public static int getNumTours() {
    return bikeTourPlus.numberOfBikeTours();
  }

  /**
   * Returns list of participantCosts in a specified bike tour.
   *
   * @author Andrew Kan
   * @param tourId ID of bike tour to search
   * @return List of TOParticipantCost objects from specified bike tour
   */
  public static List<TOParticipantCost> getParticipantCosts(int tourId) {
    TOBikeTour tour = getBikeTour(tourId);
    if (tour == null) {
      return null;
    }
    return tour.getParticipantCosts();
  }

  /**
   * Returns participant cost transfer object of specified participant
   *
   * @author Ari Smith
   * @param email email of participant to get info
   * @return TOParticipantCost object for specified participant
   */
  public static TOParticipantCost getParticipantCostByEmail(String email) {
    User tempUser = User.getWithEmail(email);
    Participant p;
    if (!(tempUser instanceof Participant)) {
      return null;
    } else {
      p = (Participant) tempUser;
    }

    BikeTour bikeTour = p.getBikeTour();
    if (bikeTour == null) {
      return new TOParticipantCost(email, p.getName(), 0, 0, p.getSmFullName(),
          p.getAuthorizationCode(), p.getRefundedPercentageAmount());
    }
    int aStartWeek = bikeTour.getStartWeek();
    int aEndWeek = bikeTour.getEndWeek();

    int numTourWeeks = aEndWeek - aStartWeek + 1;
    int aTotalCostForGuide = numTourWeeks * bikeTourPlus.getPriceOfGuidePerWeek();


    int aTotalCostForBikingTour = aTotalCostForGuide;
    List<BookedItem> bookedItems = p.getBookedItems();
    int aTotalCostForBookableItems = 0;
    for (BookedItem bookedItem : bookedItems) {
      BookableItem bookableItem = bookedItem.getItem();
      int quantity = bookedItem.getQuantity();
      if (bookableItem instanceof Gear) { // check if Gear
        aTotalCostForBookableItems += ((Gear) bookableItem).getPricePerWeek() * quantity;
      } else if (bookableItem instanceof Combo) { // check if Combo
        List<ComboItem> comboItems = ((Combo) bookableItem).getComboItems();
        int comboCost = 0;
        for (ComboItem comboItem : comboItems) {
          comboCost += comboItem.getGear().getPricePerWeek() * comboItem.getQuantity();
        }
        if (p.getLodgeRequired()) { // give combo discount
          comboCost *= (double) (100 - ((Combo) bookableItem).getDiscount()) / 100;
        }
        aTotalCostForBookableItems += comboCost * quantity;
      }
    }
    aTotalCostForBookableItems *= numTourWeeks; // price over total tour weeks
    aTotalCostForBikingTour += aTotalCostForBookableItems;
    TOParticipantCost toParticipantCost = new TOParticipantCost(p.getEmail(), p.getName(),
        aTotalCostForBookableItems, aTotalCostForBikingTour, p.getSmFullName(),
        p.getAuthorizationCode(), p.getRefundedPercentageAmount());
    return toParticipantCost;
  }

  /**
   * Gets list of participant emails in system
   *
   * @author Ari Smith
   * @return Observable List of participant emails (String)
   */
  public static ObservableList<String> getParticipantEmails() {
    List<String> participantList = new ArrayList<String>();
    for (Participant p : bikeTourPlus.getParticipants()) {
      participantList.add(p.getEmail());
    }
    return FXCollections.observableArrayList(participantList);

  }

  /**
   * Gets participant transfer object of specified participant
   *
   * @author Ari Smith
   * @param email email of participant to get info
   * @return TOParticipant for specified participant
   */
  public static TOParticipant getParticipantFromEmail(String email) {
    if (User.getWithEmail(email) instanceof Participant) {
      Participant p = (Participant) User.getWithEmail(email);
      return new TOParticipant(p.getEmail(), p.getName(), p.getPassword(), p.getEmergencyContact(),
          p.getNrWeeks(), p.getWeekAvailableFrom(), p.getWeekAvailableUntil(), p.getLodgeRequired(),
          p.getAuthorizationCode(), p.getRefundedPercentageAmount());
    } else {
      return null;
    }
  }

  /**
   * Gets list of bookable items in system
   *
   * @author Ari Smith
   * @return list of TOBookableItem's with info from system
   */
  public static List<TOBookableItem> getListBookableItems() {
    List<TOBookableItem> bookableItemsList = new ArrayList<TOBookableItem>();
    for (Gear g : bikeTourPlus.getGear()) {
      bookableItemsList.add(new TOBookableItem(g.getName(), g.getPricePerWeek()));
    }
    for (Combo c : bikeTourPlus.getCombos()) {
      double price = 0;
      for (ComboItem i : c.getComboItems()) {
        if (i.getGear() != null) {
          price += i.getQuantity() * i.getGear().getPricePerWeek();
        }
      }
      price = price * (100 - c.getDiscount()) / 100;
      bookableItemsList.add(new TOBookableItem(c.getName(), price));
    }
    return bookableItemsList;
  }

  /**
   * Gets list of gears as transfer objects
   *
   * @author Annie Gouchee
   * @return list of TOBookableItem's for gears
   */
  public static List<TOBookableItem> getGears() {
    List<TOBookableItem> gearItemsList = new ArrayList<TOBookableItem>();
    for (Gear g : bikeTourPlus.getGear()) {
      gearItemsList.add(new TOBookableItem(g.getName(), g.getPricePerWeek()));
    }
    return gearItemsList;
  }

  /**
   * Gets list of combos as transfer objects
   *
   * @author Annie Gouchee
   * @return list of TOBookableItem's for combos
   */
  public static List<TOBookableItem> getCombos() {
    List<TOBookableItem> comboItemsList = new ArrayList<TOBookableItem>();
    for (Combo c : bikeTourPlus.getCombos()) {
      double price = 0;
      for (ComboItem i : c.getComboItems()) {
        Gear g = i.getGear();
        if (g != null) {
          price += i.getQuantity() * g.getPricePerWeek();
        }
      }
      price = price * (100 - c.getDiscount()) / 100;
      comboItemsList.add(new TOBookableItem(c.getName(), price));
    }
    return comboItemsList;
  }

  /**
   * Gets booked items of specified participant as transfer object list
   *
   * @author Ari Smith
   * @param email email of participant to get items info
   * @return list of TOBookedItem's that participant has booked
   */
  public static List<TOBookedItem> getBookedItemsByParticipantEmail(String email) {
    User tempUser = User.getWithEmail(email);
    Participant p;
    if (!(tempUser instanceof Participant)) {
      return FXCollections.emptyObservableList();
    } else {
      p = (Participant) tempUser;
    }

    List<TOBookedItem> returnList = new ArrayList<>();

    List<BookedItem> bookedItemList = p.getBookedItems();
    for (BookedItem b : bookedItemList) {
      BookableItem item = b.getItem();
      returnList.add(new TOBookedItem(item.getName(), b.getQuantity()));
    }

    return returnList;
  }


  /**
   * Gets list of combos as transfer objects
   *
   * @author Annie Gouchee
   * @return list of TOCombo objects from system
   */
  public static List<TOCombo> getComboWithDiscount() {
    List<TOCombo> comboItemsList = new ArrayList<TOCombo>();
    for (Combo c : bikeTourPlus.getCombos()) {
      double price = 0;
      for (ComboItem i : c.getComboItems()) {
        Gear g = i.getGear();
        if (g != null) {
          price += i.getQuantity() * g.getPricePerWeek();
        }
      }
      price = price * (100 - c.getDiscount()) / 100;
      comboItemsList.add(new TOCombo(c.getName(), c.getDiscount(), price));
    }
    return comboItemsList;
  }

  /**
   * Gets list of gear in specified combo as transfer objects
   *
   * @author Annie Gouchee
   * @param comboName name of combo to get gear from
   * @return list of TOComboItem objects from specified combo
   */
  public static List<TOComboItem> getGearOfCombo(String comboName) {
    List<TOComboItem> gearItemsList = new ArrayList<TOComboItem>();
    BookableItem tempCombo = BookableItem.getWithName(comboName);
    Combo c;
    if (!(tempCombo instanceof Combo)) {
      return null;
    } else {
      c = (Combo) tempCombo;
    }

    for (ComboItem item : c.getComboItems()) {
      Gear g = item.getGear();
      if (g != null) {
        gearItemsList.add(new TOComboItem(g.getName(), item.getQuantity()));
      }
    }

    return gearItemsList;

  }

  /**
   * Gets guide assigned to participant as transfer object
   *
   * @param email email of participant to get info from
   * @return TOGuide object representing assigned guide of participant
   */
  public static TOGuide getGuideByParticipant(String email) {
    User tempUser = User.getWithEmail(email);
    Participant p;
    if (!(tempUser instanceof Participant)) {
      return null;
    } else {
      p = (Participant) tempUser;
    }

    if (p.hasBikeTour()) {
      Guide g = p.getBikeTour().getGuide();
      return new TOGuide(g.getEmail(), g.getPassword(), g.getName(), g.getEmergencyContact());
    } else {
      return null;
    }
  }

}
