package ca.mcgill.ecse.biketourplus.controller;

import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.BookableItem;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.application.*;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains addGear(String name, int pricePerWeek), updateGear(String oldName, String newName, int
 * newPricePerWeek),and deleteGear(String name) methods
 *
 * @author SIMON
 */
public class BikeTourPlusFeatureSet5Controller {

  private static BikeTourPlus BTP = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * @author Simon Younaki
   * @param name is the name of the gear. it should not be empty nor null and it must be unique. No
   *        duplicates allowed
   * @param pricePerWeek the gear's price per week. it must not be a negative value
   * @return a String than contains the error. if the returned String is empty, then there are no
   *         errors.
   */
  public static String addGear(String name, int pricePerWeek) {
    String error = "";
    if (pricePerWeek < 0) {

      error += "The price per week must be greater than or equal to 0. ";
    }

    if (name.equals(null) || name.equals("")) {
      error += "The name must not be empty.";
    }

    if (!error.equals("")) {
      return error;
    }

    BookableItem tempItem = BookableItem.getWithName(name);
    if (tempItem != null) {
      if (tempItem instanceof Combo) {
        error = "A combo with the same name already exists";
      }
      if (tempItem instanceof Gear) {
        error = "A piece of gear with the same name already exists";
      }
      return error;
    }

    try {
      BTP.addGear(name, pricePerWeek);
      BikeTourPlusPersistence.save();
    } catch (RuntimeException e) {
      error = e.getMessage();
      return error;
    }

    return error;
  }

  /**
   * @author Simon Younaki
   * @param oldName is the name of the already existing gear that the user is going to update. The
   *        name must not be empty and it must exist already
   * @param newName is the new name of the updated gear. The name must not be empty and it should be
   *        unique. no duplicates allowed
   * @param newPricePerWeek the new price of the updated. The price must not be a negative value
   * @return error String. if the returned string is empty then there has been no errors.
   */
  public static String updateGear(String oldName, String newName, int newPricePerWeek) {
    String error = "";
    if (newPricePerWeek < 0) {

      error += "The price per week must be greater than or equal to 0. ";
    }
    if (oldName == null || oldName.equals("")) {
      error += "The name must not be empty. Old name that you entered is invalid";
    }
    if (newName == null || newName.equals("")) {
      error +=
          "The name must not be empty.  The 'new name' that you entered is invalid. Please make sure you enter a valid new name. ";
    }

    if (!error.equals("")) {
      return error;
    }

    BookableItem tempItem = BookableItem.getWithName(oldName);
    if (!(tempItem instanceof Gear)) {
      return "The piece of gear does not exist";
    }
    Gear gear = (Gear) tempItem;

    tempItem = BookableItem.getWithName(newName);
    if (!oldName.equals(newName) && tempItem != null) {
      if (tempItem instanceof Combo) {
        error = "A combo with the same name already exists";
      }
      if (tempItem instanceof Gear) {
        error = "A piece of gear with the same name already exists";
      }
      return error;
    }

    try {
      gear.setName(newName);
      gear.setPricePerWeek(newPricePerWeek);
      BikeTourPlusPersistence.save();

    } catch (RuntimeException e) {
      error = e.getMessage();
    }
    return error;

  }

  /**
   * @author Simon Younaki
   * @param name is the name of the gear that the user wants to delete. The gear is identified by
   *        its unique name property. Note: the name must not be empty. A gear that does not exist
   *        cannot be deleted. A gear that is part of a combo cannot be deleted. It must be deleted
   *        from the combo first, then deleted as a gear. If deleted, gear will be deleted from
   *        everywhere
   * @return error String. If there are no errors, the returned String must be empty. otherwise,
   *         there are some errors
   */
  public static String deleteGear(String name) {
    String error = "";
    if (name == null || name.equals("")) {
      error +=
          "The name must not be empty. Cannot proceed with your request, The name that you entered is invalid.";
    }

    if (error.equals("")) {
      try {
        Gear gearToDelete = null;
        List<Gear> theListOfGears = BTP.getGear(); // This is a list of name. This list is what BTP
        // object sees.
        for (Gear g : theListOfGears) {
          if (g.getName().equals(name)) {
            gearToDelete = g;
            break;
          }
        }
        // If we did not find the gear with the OldName.
        if (gearToDelete.equals(null)) {
          error += "The piece of gear does not exist. ";
          return error;
        }
        // If we find the gearToDelete, we need to remove it
        if (!gearToDelete.hasComboItems()) {
          gearToDelete.delete();
          BikeTourPlusPersistence.save();
        } else {
          error += "The piece of gear is in a combo and cannot be deleted. ";
        }

        // The delete method removes the gear from the list of gears in the BikeTourPlus class (BTP
        // object)
        // The delete method delete all comboItems related to this specific gear.
      } catch (RuntimeException e) {
        error = e.getMessage();
        return error;
      }
    }

    return error;
  }
}
