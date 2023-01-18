package ca.mcgill.ecse.biketourplus.features;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet6Controller;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Gear;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateComboStepDefinitions {
  private BikeTourPlus btp;
  private String error;

  /**
   * This method creates a btp and initializes the error message.
   * 
   * @author Simon Li (slsecrets357)
   * @param dataTable from feature file containing startDate, nrWeeks and priceOfGuidePerWeek
   * @return returns nothing
   */
  @Given("the following BikeTourPlus system exists: \\(p7)")
  public void the_following_bike_tour_plus_system_exists_p7(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    btp = BikeTourPlusApplication.getBikeTourPlus();
    btp.setStartDate(java.sql.Date.valueOf(rows.get(0).get("startDate")));
    btp.setNrWeeks(Integer.parseInt(rows.get(0).get("nrWeeks")));
    btp.setPriceOfGuidePerWeek(Integer.parseInt(rows.get(0).get("priceOfGuidePerWeek")));
    error = "";
  }

  /**
   * This method checks whether the pieces of gear in the datatable are in the system.
   * 
   * @author Simon Li (slsecrets357)
   * @param dataTable from feature file containing the gears
   * @return returns error message, if any
   */
  @Given("the following pieces of gear exist in the system: \\(p7)")
  public void the_following_pieces_of_gear_exist_in_the_system_p7(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      btp.addGear(columns.get("name"), Integer.parseInt(columns.get("pricePerWeek")));
    }
  }

  /**
   * This method checks whether the pieces of combos in the datatable are in the system.
   * 
   * @author Simon Li (slsecrets357)
   * @param dataTable from feature file containing the combos
   * @return returns error message, if any
   */
  @Given("the following combos exist in the system: \\(p7)")
  public void the_following_combos_exist_in_the_system_p7(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> columns : rows) {
      Combo c = new Combo(columns.get("name"), Integer.parseInt(columns.get("discount")), btp);
      String str = columns.get("items");
      String q = columns.get("quantity");
      List<String> quantities = Arrays.asList(q.split(","));
      List<String> items = Arrays.asList(str.split(","));
      String item;
      int quantity;
      for (int i = 0; i < items.size(); i++) {
        item = items.get(i);
        quantity = Integer.parseInt(quantities.get(i));
        List<Gear> gears = btp.getGear();
        Gear g1 = null;
        for (Gear g : gears) {
          if (g.getName().equals(item)) {
            g1 = g;
          }
        }
        c.addComboItem(quantity, btp, g1);
      }
    }
  }

  /**
   * This method checks whether the combo is updated successfully
   * 
   * @author Antoine Deng (AntoineDeng)
   * @param old name of combo, new name of combo, discount
   * @return returns error message, if any
   */
  @When("the manager attempts to update the combo with name {string} to have name {string} and discount {string} \\(p7)")
  public void the_manager_attempts_to_update_the_combo_with_name_to_have_name_and_discount_p7(
      String string, String string2, String string3) {
    callController(
        BikeTourPlusFeatureSet6Controller.updateCombo(string, string2, Integer.parseInt(string3)));
  }

  /**
   * This method checks whether the combo is added successfully
   * 
   * @author Antoine Deng (AntoineDeng)
   * @param name of combo, discount
   * @return returns error message, if any
   */
  @When("the manager attempts to add a combo with name {string} and discount {string} \\(p7)")
  public void the_manager_attempts_to_add_a_combo_with_name_and_discount_p7(String string,
      String string2) {

    callController(BikeTourPlusFeatureSet6Controller.addCombo(string, Integer.parseInt(string2)));
  }

  /**
   * This method checks whether a combo with name and discount exist
   * 
   * @author Antoine Deng (AntoineDeng)
   * @param name of combo, discount
   * @return returns error message, if any
   */
  @Then("a combo shall not exist with name {string} and discount {string} \\(p7)")
  public void a_combo_shall_not_exist_with_name_and_discount_p7(String string, String string2) {
    boolean exist = false;
    for (Combo c : btp.getCombos()) {
      if (c.getName().equals(string) && c.getDiscount() == Integer.parseInt(string2)) {
        exist = true;
        break;
      }
    }

    assertTrue(!exist);

  }

  /**
   * This method checks whether a combo with a given name exists
   * 
   * @author Antoine Deng (AntoineDeng)
   * @param name of combo
   * @return returns error message, if any
   */
  @Then("the number of combos shall be {string} \\(p7)")
  public void the_number_of_combos_shall_be_p7(String string) {
    assertEquals(Integer.parseInt(string), btp.getCombos().size());
  }

  /**
   * This method checks whether a combo has a given number of gear types
   * 
   * @author Antoine Deng (AntoineDeng)
   * @param name of combo, number of gears
   * @return returns error message, if any
   */
  @Then("the number of pieces of gear for the combo with name {string} shall be {string} \\(p7)")
  public void the_number_of_pieces_of_gear_for_the_combo_with_name_shall_be_p3(String string,
      String string2) {
    Combo c1 = null;
    for (Combo c : btp.getCombos()) {
      if (c.getName().equals(string)) {
        c1 = c;
        break;
      }
    }
    assertNotNull(c1);
    int q = c1.getComboItems().size();
    assertEquals(Integer.parseInt(string2), q);

  }

  /**
   * This method checks whether a combo with given name and discount exist
   * 
   * @author Antoine Deng (AntoineDeng)
   * @param name of combo, discount
   * @return returns error message, if any
   */
  @Then("a combo shall exist with name {string} and discount {string} \\(p7)")
  public void a_combo_shall_exist_with_name_and_discount_p7(String string, String string2) {
    boolean exist = false;

    for (Combo c : btp.getCombos()) {
      if (c.getName().equals(string) && c.getDiscount() == Integer.parseInt(string2)) {
        exist = true;
        break;
      }
    }
    assertTrue(exist);

  }

  /**
   * This method checks whether an error exists
   * 
   * @author Simon Li (slsecrets357)
   * @param error string
   * @return nothing
   */
  @Then("the system shall raise the error {string} \\(p7)")
  public void the_system_shall_raise_the_error_p7(String string) {
    assertTrue(error.contains(string));
  }

  private void callController(String result) { // Taken from tutorial 6
    if (!result.isEmpty()) {
      error += result;
    }
  }

}
