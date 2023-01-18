package ca.mcgill.ecse.biketourplus.features;

import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet6Controller;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.sql.Date;
import java.util.Arrays;
import java.util.Map;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse.biketourplus.model.*;

import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.BookableItem;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Gear;

/**
 * This class provides the step definitions for AddAndRemoveGearForCombo
 */
public class AddAndRemoveGearForComboStepDefinitions {

  private String error;
  private BikeTourPlus bikeTourPlus;

  /**
   * @author @author mohammad_junaid
   * @param dataTable This method parses the given dataTable to initialize the BikeTourPlus system
   */
  @Given("the following BikeTourPlus system exists: \\(p2)")
  public void the_following_bike_tour_plus_system_exists_p2(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    // loop through each row of data table
    for (var row : rows) {
      // Create the BikeTourPlus System with the specified startDate, nrWeeks,
      // priceOfGuidePerWeek
      Date startDate = java.sql.Date.valueOf(row.get("startDate"));
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      int priceOfGuidePerWeek = Integer.parseInt(row.get("priceOfGuidePerWeek"));
      bikeTourPlus = BikeTourPlusApplication.getBikeTourPlus();
      bikeTourPlus.setNrWeeks(nrWeeks);
      bikeTourPlus.setStartDate(startDate);
      bikeTourPlus.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
      error = "";

    }
  }

  /**
   * @author Juliana Xu
   * @param dataTable This method parses the given dataTable to generate the gear in the system
   */
  @Given("the following pieces of gear exist in the system: \\(p2)")
  public void the_following_pieces_of_gear_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {
    // Create gear pieces in the system with the specified name, pricePerWeek
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int pricePerWeek = Integer.parseInt(row.get("pricePerWeek"));
      bikeTourPlus.addGear(name, pricePerWeek);
    }
  }

  /**
   * @author Gabrielle Lavoie
   * @param dataTable This method parses the given dataTable to create combos associated with
   *        multiple gear items and quantities
   */
  @Given("the following combos exist in the system: \\(p2)")
  public void the_following_combos_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();

    // loop through each row of data table
    for (var row : rows) {
      // create a combo with the specified name and discount
      String name = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));
      Combo combo = bikeTourPlus.addCombo(name, discount);

      // get items and quantities strings
      String item_names_string = row.get("items");
      String item_quantities_string = row.get("quantity");

      // split the comma-separated strings into list of strings
      List<String> item_names_list = Arrays.asList(item_names_string.split(","));
      List<String> item_quantities_list = Arrays.asList(item_quantities_string.split(","));

      for (int i = 0; i < (item_names_list).size(); i++) {
        // get existing gear object with name in data table
        Gear gear = (Gear) BookableItem.getWithName(item_names_list.get(i));

        // create a combo item, which links the combo with its gear items and specifies
        // quantities
        gear.addComboItem(Integer.valueOf(item_quantities_list.get(i)), bikeTourPlus, combo);
      }
    }
  }

  /**
   * @author Lina
   * @param testGearName
   * @param testComboName This method attempts to add a specific piece of gear to a specific combo
   */
  @When("the manager attempts to add a piece of gear with name {string} to the combo with name {string} \\(p2)")
  public void the_manager_attempts_to_add_a_piece_of_gear_with_name_to_the_combo_with_name_p2(
      String string, String string2) {
    callController(BikeTourPlusFeatureSet6Controller.addGearToCombo(string, string2));

  }

  /**
   * @author Juliana Xu
   * @param string
   * @param string2 This method calls a controller method to remove a specific gear from a specific
   *        combo
   */
  @When("the manager attempts to remove a piece of gear with name {string} from the combo with name {string} \\(p2)")
  public void the_manager_attempts_to_remove_a_piece_of_gear_with_name_from_the_combo_with_name_p2(
      String string, String string2) {
    callController(BikeTourPlusFeatureSet6Controller.removeGearFromCombo(string, string2));
  }

  /**
   * @author Gabrielle Lavoie This method asserts that the number of combos currently in the system
   *         is equal to the value specified in the .feature file
   * @param string Correct number of combos
   */
  @Then("the number of combos shall be {string} \\(p2)")
  public void the_number_of_combos_shall_be_p2(String string) {
    assertEquals(Integer.parseInt(string), bikeTourPlus.getCombos().size());
  }

  /**
   * @author Afnan Waheed
   * @param string This method verifies system catches the correct error
   */
  @Then("the system shall raise the error {string} \\(p2)")
  public void the_system_shall_raise_the_error_p2(String string) {
    assertEquals(string, error);
  }

  /**
   * @author Afnan Waheed
   * @param string
   * @param string2 This method checks if combo has correct number of gear for a combo
   */
  @Then("the number of pieces of gear for the combo with name {string} shall be {string} \\(p2)")
  public void the_number_of_pieces_of_gear_for_the_combo_with_name_shall_be_p2(String string,
      String string2) {
    // get item with specified name
    BookableItem foundItem = BookableItem.getWithName(string);

    // ensure that the foundItem exists
    assertNotNull(foundItem);

    // ensure that the foundItem is a combo
    assertTrue(foundItem instanceof Combo);

    // compare number of pieces of gear
    Combo combo = (Combo) foundItem;
    assertEquals(Integer.valueOf(string2), combo.numberOfComboItems());
  }

  /**
   * @author Juliana Xu, Gabrielle Lavoie, Mohammad Junaid Arif, Afnan Waheed
   * @param string
   * @param string2
   * @param string3
   */
  @Then("a piece of gear shall exist with name {string} and quantity {string} for the combo with name {string} \\(p2)")
  public void a_piece_of_gear_shall_exist_with_name_and_quantity_for_the_combo_with_name_p2(
      String string, String string2, String string3) {
    assertNotNull(BookableItem.getWithName(string)); // check if gear exists
    assertNotNull(BookableItem.getWithName(string3)); // check if combo exists

    int quantity = -1;
    for (ComboItem comboItem : bikeTourPlus.getComboItems()) {
      if (comboItem.getGear().getName().equals(string)
          && comboItem.getCombo().getName().equals(string3)) {
        quantity = comboItem.getQuantity();
      }
    }
    assertEquals(Integer.parseInt(string2), quantity);
  }

  /**
   * @author Juliana Xu, Gabrielle Lavoie, Mohammad Junaid Arif, Afnan Waheed
   * @param string
   * @param string2
   */
  @Then("a piece of gear shall not exist with name {string} for the combo with name {string} \\(p2)")
  public void a_piece_of_gear_shall_not_exist_with_name_for_the_combo_with_name_p2(String string,
      String string2) {
    for (ComboItem comboItem : bikeTourPlus.getComboItems()) {
      if (comboItem.getGear().getName().equals(string)
          && comboItem.getCombo().getName().equals(string2)) {
        fail("An unexpected gear exists in the combo");
      }
    }
  }

  /** Calls controller and sets error and counter */
  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
    }
  }

}
