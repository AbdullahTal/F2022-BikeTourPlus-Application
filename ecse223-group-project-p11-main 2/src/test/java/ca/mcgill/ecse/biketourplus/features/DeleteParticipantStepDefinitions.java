package ca.mcgill.ecse.biketourplus.features;

import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.biketourplus.application.*;
import ca.mcgill.ecse.biketourplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteParticipantStepDefinitions {
  private static BikeTourPlus BikeTourPlus = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * @author Sirine Tarhouni
   *
   *         Add given pieces of gear to the BikeTourPlus system
   *
   * @param dataTable
   */

  @Given("the following pieces of gear exist in the system: \\(p6)")
  public void the_following_pieces_of_gear_exist_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> gears = dataTable.asMaps(); // List of all the rows of gear

    for (var gear : gears) { // going through each gear (the name and pricePerWeek)

      String name = gear.get("name"); // getting the name of each gear
      int pricePerWeek = Integer.parseInt(gear.get("pricePerWeek")); // getting the price of each
                                                                     // gear
      BikeTourPlus.addGear(name, pricePerWeek); // adding each gear to the system
    }

  }

  /**
   * This method adds the requested combo to system
   *
   * @param dataTable
   * @author Karim Al Sabbagh and Omar Marwan
   *
   */
  @Given("the following combos exist in the system: \\(p6)")
  public void the_following_combos_exist_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // List of all the rows of combos

    for (var row : rows) { // going through each combo

      String comboName = row.get("name"); // getting the name of each combo
      int comboDiscount = Integer.parseInt(row.get("discount"));// getting the discount of each
                                                                // combo
      int nrOfItems = row.get("items").split(",").length; // getting the number of items in each
                                                          // combo
      Combo combo = new Combo(comboName, comboDiscount, BikeTourPlus); // adding each combo to the
                                                                       // system

      for (int i = 0; i < nrOfItems; i++) { // going through each item in each combo

        String itemName = row.get("items").split(",")[i];// getting the name of each item
        Gear gear = (Gear) BookableItem.getWithName(itemName); // getting the gear with the item
                                                               // name
        int itemQuantity = Integer.parseInt(row.get("quantity").split(",")[i]); // getting the
                                                                                // quantity of each
        // item
        combo.addComboItem(itemQuantity, BikeTourPlus, gear); // adding ComboItems to the system

      }
    }
  }

  /**
   * This method adds a requested gear to a participant.
   *
   * @param dataTable
   * @author Karim Al Sabbagh and Meriem
   */

  @Given("the following participants request the following pieces of gear: \\(p6)")
  public void the_following_participants_request_the_following_pieces_of_gear_p6(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // List of all the rows (with participant
                                                         // emails, gear
    // names and quantity)
    for (var row : rows) { // going through each row
      String gear = row.get("gear"); // getting the name of each gear
      int quantity = Integer.parseInt(row.get("quantity")); // getting the quantity of each gear
      Participant p = (Participant) Participant.getWithEmail(row.get("email")); // getting the
                                                                                // participant with
      // email
      p.addBookedItem(quantity, BikeTourPlus, BookableItem.getWithName(gear)); // adding bookedItem
                                                                               // to participant

    }
  }

  /**
   * This method adds a requested combo to a participant.
   *
   * @param dataTable
   * @author Meriem and Omar Marwan
   */
  @Given("the following participants request the following combos: \\(p6)")
  public void the_following_participants_request_the_following_combos_p6(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // list of all the rows of combos and the
                                                         // participants
    // emails

    for (var row : rows) { // going through each row
      String participantEmail = row.get("email"); // getting email from participants in the row
      Participant participant = (Participant) Participant.getWithEmail(participantEmail); // getting
                                                                                          // the
      // corresponding
      // participants from the
      // emails
      String gearName = row.get("gear"); // get name of each gear
      BookableItem gearBookableItem = BookableItem.getWithName(gearName); // getting the bookable
                                                                          // item for the
      // gear name
      int gearQuantity = Integer.parseInt(row.get("quantity")); // getting the quantity of gears for
                                                                // every
      // participant
      participant.addBookedItem(gearQuantity, BikeTourPlus, gearBookableItem); // adding booked item
                                                                               // to
      // participant

    }

  }

  /**
   * @author Omar Marwan
   *
   *         Manager attempts to delete a participant with their email
   *
   * @param string the participant's email
   */
  @When("the manager attempts to delete the participant with email {string} \\(p6)")
  public void the_manager_attempts_to_delete_the_participant_with_email_p6(String string) {
    BikeTourPlusFeatureSet2Controller.deleteParticipant(string); // delete participant connected to
                                                                 // the input email
  }

  /**
   * @author Omar Marwan
   *
   *         Checking that a participant account doesn't exist with the given email
   *
   * @param string
   */
  @Then("a participant account shall not exist with email {string} \\(p6)")
  public void a_participant_account_shall_not_exist_with_email_p6(String string) {
    User existingUser = User.getWithEmail(string); // getting user with the email
    Boolean participantExistance = false; // initializing boolean variable
    if (existingUser != null) { // if user exists
      if (existingUser instanceof Participant) { // making sure user is a participant
        participantExistance = true; // if user is participant boolean is true
      }
    }
    assertFalse(participantExistance); // assert statement to make sure that the participant does
                                       // not exist

  }

  /**
   * this method asserts if the number of the number of booked items of type gear is equal to the
   * given value in string 2
   *
   * @param string - gear name
   * @param string2 - email
   * @author Meriem and Sirine Tarhouni
   */
  @Then("the piece of gear with name {string} shall be requested by {string} \\(p6)")
  public void the_piece_of_gear_with_name_shall_be_requested_by_p6(String string, String string2) {

    assertEquals(String.valueOf(BookableItem.getWithName(string).numberOfBookedItems()), string2); // assert
                                                                                                   // that
    // the number of
    // a certain
    // booked item of
    // tyoe gear is
    // equal to the
    // input string 2

  }

  /**
   *
   * this method asserts if the number of booked items of type combo is equal to the given value in
   * string 2
   * 
   * @param string - combo name
   * @param string2 - email
   * @author Meriem and Sirine Tarhouni
   */

  @Then("the combo with name {string} shall be requested by {string} \\(p6)")
  public void the_combo_with_name_shall_be_requested_by_p6(String string, String string2) {
    assertEquals(String.valueOf(BookableItem.getWithName(string).numberOfBookedItems()), string2); // assert
                                                                                                   // that
    // the number of
    // a certain
    // booked item of
    // type combos is
    // equal to the
    // input string 2

  }

  /**
   * @author Omar Marwan
   *
   *         Checking that a guide exists with the given email
   *
   * @param string
   */
  @Then("a guide account shall exist with email {string} \\(p6)")
  public void a_guide_account_shall_exist_with_email_p6(String string) {
    User existingUser = User.getWithEmail(string); // get the user with the email corresponding to
                                                   // the input email
    Boolean guideExistance = false; // initialize the boolean variable as false
    if (existingUser != null) { // check to make sure that the user with email exists
      if (existingUser instanceof Guide) { // check to make sure that the user with corresponding
                                           // email is of type
        // guide
        guideExistance = true; // set boolean to true since guide exists
      }
    }
    assertTrue(guideExistance); // assert that the guide with the input email exists
  }

}
