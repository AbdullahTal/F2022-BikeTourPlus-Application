package ca.mcgill.ecse.biketourplus.features;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;
// First file: Karl, Walid, Sami

public class DeleteComboStepDefinitions {
  private static BikeTourPlus btp = BikeTourPlusApplication.getBikeTourPlus();
  private String error;
  private int errorCntr;

  /**
   * Given Step to make sure that the BikeTourPlus system exists
   * before the beginning of a test
   * 
   * @author Karl Bridi
   */
  @Given("the following BikeTourPlus system exists: \\(p3)")
  public void the_following_bike_tour_plus_system_exists_p3( // K
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      Date startDate = Date.valueOf(row.get("startDate"));
      btp.setStartDate(startDate);
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      btp.setNrWeeks(nrWeeks);
      int priceOfGuidePerWeek = Integer.parseInt(row.get("priceOfGuidePerWeek"));
      btp.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
    }
  }

  /**
   * Given Step to make sure that pieces of gear exist in the system
   * before the beginning of a certain test
   * 
   * @author Karl Bridi
   */
  @Given("the following pieces of gear exist in the system: \\(p3)")
  public void the_following_pieces_of_gear_exist_in_the_system_p3( // K
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int pricePerWeek = Integer.parseInt(row.get("pricePerWeek"));
      new Gear(name, pricePerWeek, btp);
    }
  }

  /**
   * Given step to make sure that certain combos exist in the system
   * before the beginning of a certain test
   * 
   * @author Karl Bridi
   */
  @Given("the following combos exist in the system: \\(p3)")
  public void the_following_combos_exist_in_the_system_p3( // K
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));
      Combo combo = new Combo(name, discount, btp);
      for (int i = 0; i < row.get("items").split(",").length; i++) {
        combo.addComboItem(Integer.parseInt(row.get("quantity").split(",")[i]), btp,
            (Gear) BookableItem.getWithName(row.get("items").split(",")[i]));
      }
    }
  }

  /**
   * Given step to make sure that certain participants exist in the system
   * before the beginning of a certain test
   * 
   * @author Karl Bridi
   */
  @Given("the following participants exist in the system: \\(p3)")
  public void the_following_participants_exist_in_the_system_p3( // K
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencyContact = row.get("emergencyContact");
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      int weekAvailableFrom = Integer.parseInt(row.get("weeksAvailableFrom"));
      int weekAvailableUntil = Integer.parseInt(row.get("weeksAvailableUntil"));
      boolean lodgeRequired = Boolean.parseBoolean(row.get("lodgeRequired"));
      new Participant(email, password, name, emergencyContact, nrWeeks, weekAvailableFrom, weekAvailableUntil,
          lodgeRequired, null, 0, btp);
    }
  }

  /**
   * Given step to make sure that certain participants have been assigned certain
   * combos
   * in the system before the beginning of a certain test
   * 
   * @author Walid Aissa
   */
  @Given("the following participants request the following combos: \\(p3)")
  public void the_following_participants_request_the_following_combos_p3( // W
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String name = row.get("combo");
      int quantity = Integer.parseInt(row.get("quantity"));
      Participant participant = (Participant) Participant.getWithEmail(email);
      BookableItem combo = BookableItem.getWithName(name);
      new BookedItem(quantity, btp, participant, combo);
    }
  }

  /**
   * When step that uses the controller to delete the Combo with name <string>
   * when
   * the manager tries to.
   * 
   * @author Walid Aissa
   */
  @When("the manager attempts to delete the combo with name {string} \\(p3)")
  public void the_manager_attempts_to_delete_the_combo_with_name_p3(String string) { // W
    BikeTourPlusFeatureSet2Controller.deleteCombo(string);
  }

  /**
   * Then step to verify that no combo with name <string> exists in the system.
   * 
   * @author Walid Aissa
   */
  @Then("a combo shall not exist with name {string} \\(p3)")
  public void a_combo_shall_not_exist_with_name_p3(String string) { // W
    assertNull(BookableItem.getWithName(string));
  }

  /**
   * Then step definition to make sure that the number of combos is right
   * 
   * @author Karl Bridi
   * @param string
   */
  @Then("the number of combos shall be {string} \\(p3)")
  public void the_number_of_combos_shall_be_p3(String string) { // K
    assertEquals(Integer.parseInt(string), btp.getCombos().size());
  }

  /**
   * Then step definition to make sure that a certain participant has the right
   * amount of combos
   * 
   * @author Samer AbdelKarim
   * @param string
   */
  @Then("the participant with email {string} shall have a combo with name {string} and quantity {string} \\(p3)")
  public void the_participant_with_email_shall_have_a_combo_with_name_and_quantity_p3(String string,
      String string2, String string3) {
    boolean name = false;
    boolean quantity = false; // j
    for (BookedItem bookedItem : ((Participant) Participant.getWithEmail(string)).getBookedItems()) {
      name = bookedItem.getItem().getName().equals(string2);
      if (name && bookedItem.getItem() instanceof Combo) {
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
   * Then step definition to make sure that the number of bookableItems is right
   * 
   * @author Sami Bayoud
   * @param string
   */
  @Then("the number of pieces of gear for the participant with email {string} shall be {string} \\(p3)")
  public void the_number_of_pieces_of_gear_for_the_participant_with_email_shall_be_p3(String string,
      String string2) { // S
    int total = 0;
    Participant participant = getParticipantByEmail(string);

    // We assumed that pieces of gear in both context of delete gear and delete
    // combo means "BookableItem"
    // Because this step definition is used in both features (Combo and Gear)
    // In the combo feature it checks for the number of remaining combos
    // ("<numberOfRequestedCombos>") after a deletion
    // In the gear feature it checks for the number of remaining gears
    // ("<numberOfRequestedGears>") after a deletion
    total = participant.getBookedItems().size();

    assertEquals(Integer.parseInt(string2), total);
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
