package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.*;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;

public class BikeTourPlusFeatureSet6Controller {

  private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * Adds a combo with name and discount.
   *
   * @author Andrew Kan
   * @param name name for combo
   * @param discount discount of combo, integer between 0 and 100.
   * @return error message string, empty if successful
   */
  public static String addCombo(String name, int discount) {
    // input validation
    String error = "";
    if (name == null || name.equals("")) {
      error += "The name must not be empty\n";
    }
    if (discount < 0) {
      error += "Discount must be at least 0\n";
    }
    if (discount > 100) {
      error += "Discount must be no more than 100";
    }
    if (!error.isEmpty()) {
      return error;
    }

    // Try to add combo to btp
    try {
      btp.addCombo(name, discount);
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      error = e.getMessage();
      if (error.startsWith("Cannot create due to duplicate name.")) {
        BookableItem tempItem = BookableItem.getWithName(name);
        if (tempItem instanceof Gear) {
          error = "A piece of gear with the same name already exists";
        } else if (tempItem instanceof Combo) {
          error = "A combo with the same name already exists";
        }
      }
    }

    return error;
  }

  /**
   * Updates a combo's name and/or discount.
   *
   * @author Andrew Kan
   * @param oldName name of combo to update
   * @param newName new name for combo
   * @param newDiscount new discount for combo, integer between 0 and 100
   * @return error message string, empty if successful
   */
  public static String updateCombo(String oldName, String newName, int newDiscount) {
    // input validation
    String error = "";
    if (oldName == null || oldName.equals("")) {
      error += "The name must not be empty\n";
    }
    if (newName == null || newName.equals("")) {
      error += "The name must not be empty\n";
    }
    if (newDiscount < 0) {
      error += "Discount must be at least 0\n";
    }
    if (newDiscount > 100) {
      error += "Discount must be no more than 100";
    }
    if (!error.isEmpty()) {
      return error;
    }

    // Find combo
    BookableItem tempItem = BookableItem.getWithName(oldName);
    if (!(tempItem instanceof Combo)) {
      return ("The combo does not exist");
    }
    Combo combo = (Combo) tempItem; // cast to Combo after checking type

    // Update combo info
    try {
      if (!oldName.equals(newName)) {
        // Check if BookableItem exists with newName
        tempItem = BookableItem.getWithName(newName);
        if (tempItem instanceof Gear) {
          return ("A piece of gear with the same name already exists");
        } else if (tempItem instanceof Combo) {
          return ("A combo with the same name already exists");
        }
        combo.setName(newName);
      }

      combo.setDiscount(newDiscount);
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      error = e.getMessage();
    }
    return error;
  }

  /**
   * Adds a piece of gear to a combo.
   *
   * @author Andrew Kan
   * @param gearName name of gear to add
   * @param comboName combo to add gear to
   * @return error message string, empty if successful
   */
  public static String addGearToCombo(String gearName, String comboName) {
    // input validation
    String error = "";
    if (gearName == null || gearName.equals("")) {
      error += "The name must not be empty\n";
    }
    if (comboName == null || comboName.equals("")) {
      error += "The name must not be empty\n";
    }
    BookableItem tempItem = BookableItem.getWithName(gearName);
    if (!(tempItem instanceof Gear)) {
      error += "The piece of gear does not exist";
    }
    if (!error.isEmpty()) {
      return error;
    }

    Gear gear = (Gear) tempItem; // cast to Gear after checking type
    // Find combo
    tempItem = BookableItem.getWithName(comboName);
    if (!(tempItem instanceof Combo)) {
      return ("The combo does not exist");
    }
    Combo combo = (Combo) tempItem; // cast to Combo after checking type

    // Add gear to combo
    try {
      for (ComboItem c : combo.getComboItems()) {
        Gear g = c.getGear();
        if (g != null && g.equals(gear)) { // change quantity of existing comboItem
          c.setQuantity(c.getQuantity() + 1);
          BikeTourPlusPersistence.save();
          return error;
        }
      }
      combo.addComboItem(1, btp, gear); // add new ComboItem if doesn't exist in combo
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      error = e.getMessage();
    }

    return error;
  }

  /**
   * Removes a piece of gear from a combo.
   *
   * @author Andrew Kan
   * @param gearName name of gear to remove
   * @param comboName combo to remove from
   * @return error message string, empty if successful
   */
  public static String removeGearFromCombo(String gearName, String comboName) {
    // input validation
    String error = "";
    if (gearName == null || gearName.equals("")) {
      error += "The name must not be empty\n";
    }
    if (comboName == null || comboName.equals("")) {
      error += "The name must not be empty\n";
    }
    BookableItem tempItem = BookableItem.getWithName(gearName);
    if (!(tempItem instanceof Gear)) {
      error += ("The piece of gear does not exist");
    }
    if (!error.isEmpty()) {
      return error;
    }

    Gear gear = (Gear) tempItem; // cast to Gear after checking type
    // Find combo
    tempItem = BookableItem.getWithName(comboName);
    if (!(tempItem instanceof Combo)) {
      return ("The combo does not exist");
    }
    Combo combo = (Combo) tempItem; // cast to Combo after checking type

    // Remove gear from combo
    try {
      for (ComboItem c : combo.getComboItems()) {
        Gear g = c.getGear();
        if (g != null && g.equals(gear)) { // change quantity of existing comboItem
          if (c.getQuantity() > 1) {
            c.setQuantity(c.getQuantity() - 1);
          } else {
            if (combo.numberOfComboItems() == 2) {
              return ("A combo must have at least two pieces of gear");
            }
            c.delete(); // delete the combo item
          }
          BikeTourPlusPersistence.save();
          return error;
        }
      }
      error = "The piece of gear does not exist";
    } catch (RuntimeException e) {
      error = e.getMessage();
    }

    return error;
  }
}
