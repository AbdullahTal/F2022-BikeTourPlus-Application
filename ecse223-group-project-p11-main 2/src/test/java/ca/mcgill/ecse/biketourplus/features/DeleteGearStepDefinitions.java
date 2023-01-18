package ca.mcgill.ecse.biketourplus.features;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet5Controller;
import ca.mcgill.ecse.biketourplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// Second File: Samer, Emilien, Clara
public class DeleteGearStepDefinitions {
  private BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();
  private String error;
  private int errorCntr;

  /**
   * Given Step to make sure that the BikeTourPlus system exists before the beginning of a test
   * 
   * @author Samer AbdelKarim
   */
  @Given("the following participants request the following pieces of gear: \\(p3)")
  public void the_following_participants_request_the_following_pieces_of_gear_p3( // S
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String gear = row.get("gear");
      int quantity = Integer.parseInt(row.get("quantity"));
      getParticipantByEmail(row.get("email")).addBookedItem(quantity, btp,
          BookableItem.getWithName(gear));
    }
  }

  /**
   * When Step to attempt to delete a piece of gear
   * 
   * @author Samer AbdelKarim
   */
  @When("the manager attempts to delete the piece of gear with name {string} \\(p3)")
  public void the_manager_attempts_to_delete_the_piece_of_gear_with_name_p3(String string) { // S
    // Write code here that turns the phrase above into concrete actions
    callController(BikeTourPlusFeatureSet5Controller.deleteGear(string));
  }

  /**
   * Then Step to make sure that a certain piece of gear exists
   * 
   * @author Samer AbdelKarim
   */
  @Then("a piece of gear shall exist with name {string} and price per week {string} \\(p3)")
  public void a_piece_of_gear_shall_exist_with_name_and_price_per_week_p3(String string,
      String string2) { // S
    BookableItem bookableItem = BookableItem.getWithName(string);
    assertNotNull(bookableItem);
    Gear gear = ((Gear) bookableItem);
    assertEquals(string, gear.getName());
    assertEquals(Integer.parseInt(string2), gear.getPricePerWeek());
    // no need to check if gear exists as that will be sufficient
  }

  /**
   * Then Step to make sure that the number of pieces of gear is right
   * 
   * @author Emilien Taisne
   */
  @Then("the number of pieces of gear shall be {string} \\(p3)")
  public void the_number_of_pieces_of_gear_shall_be_p3(String string) { // E
    // Write code here that turns the phrase above into concrete actions
    assertEquals(Integer.parseInt(string), btp.numberOfGear());
  }

  /**
   * Then Step to make sure that a certain combo has the right amount of piece of gear
   * 
   * @author Emilien Taisne
   */
  @Then("the combo with name {string} shall have a piece of gear with name {string} and quantity {string} \\(p3)")
  public void the_combo_with_name_shall_have_a_piece_of_gear_with_name_and_quantity_p3(
      String string, String string2, String string3) { // E
    // Write code here that turns the phrase above into concrete actions
    ComboItem aComboItem = null;
    for (ComboItem comboItem : ((Combo) BookableItem.getWithName(string)).getComboItems()) {
      if (comboItem.getGear().getName().equals(string2)) {
        aComboItem = comboItem;
      }
    }
    assertEquals(string2, aComboItem.getGear().getName());
    assertEquals(Integer.parseInt(string3), aComboItem.getQuantity());
  }

  /**
   * Then Step to make sure that the system raises an error
   * 
   * @author Emilien Taisne
   */
  @Then("the system shall raise the error {string} \\(p3)")
  public void the_system_shall_raise_the_error_p3(String string) { // E
    assertTrue(error.contains(string));
  }

  /**
   * Then Step to make sure that a certain piece of gear doesn't exist
   * 
   * @author Samer AbdelKarim
   */
  @Then("a piece of gear shall not exist with name {string} \\(p3)")
  public void a_piece_of_gear_shall_not_exist_with_name_p3(String string) { // S
    // Write code here that turns the phrase above into concrete actions
    assertNull(BookableItem.getWithName(string));
  }

  /**
   * Then Step to make sure that participant has the right amount of gear
   * 
   * @author Samer AbdelKarim
   */
  @Then("the participant with email {string} shall have a piece of gear with name {string} and quantity {string} \\(p3)")
  public void the_participant_with_email_shall_have_a_piece_of_gear_with_name_and_quantity_p3(
      String string, String string2, String string3) { // C
    boolean name = false;
    boolean quantity = false;
    for (BookedItem bookedItem : (((Participant) Participant.getWithEmail(string))
        .getBookedItems())) {
      name = bookedItem.getItem().getName().equals(string2);
      if (name && bookedItem.getItem() instanceof Gear) {
        if (Integer.parseInt(string3) == bookedItem.getQuantity()) {
          quantity = true;
          break;
        }
      } else
        name = false;

    }
    assertTrue(name && quantity);
  }

  /**
   * Then Step to make sure that the number of pieces of gear is right before the beginning of a
   * test
   * 
   * @author Clara Jabbour
   */

  @Then("the number of pieces of gear for the combo with name {string} shall be {string} \\(p3)")
  public void the_number_of_pieces_of_gear_for_the_combo_with_name_shall_be_p3(String string,
      String string2) {
    Combo combo = (Combo) BookableItem.getWithName(string);
    assertEquals(Integer.parseInt(string2), combo.getComboItems().size());
  }

  private void callController(String result) {
    if (!result.isEmpty()) {
      error = result;
      errorCntr += 1;
    }
  }

  /**
   * Helper method to get a participant by email
   * 
   * @param email
   * @return
   */
  private Participant getParticipantByEmail(String email) {
    for (var participant : btp.getParticipants()) {
      if (participant.getEmail().equals(email)) {
        return participant;
      }
    }
    return null;
  }

}
