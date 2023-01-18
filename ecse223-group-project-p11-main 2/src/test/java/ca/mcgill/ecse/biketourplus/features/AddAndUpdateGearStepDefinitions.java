package ca.mcgill.ecse.biketourplus.features;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.BookableItem;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet5Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddAndUpdateGearStepDefinitions {
  private BikeTourPlus btp;
  private String error;
  private int errorCntr;


  /**
   * @author William Zhang
   * 
   * @param dataTable
   */
  @Given("the following BikeTourPlus system exists: \\(p4)")
  public void the_following_bike_tour_plus_system_exists_p4(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      Date startDate = Date.valueOf(row.get("startDate")); // Extract data from the Cucumber data
                                                           // table
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      int priceOfGuidePerWeek = Integer.parseInt(row.get("priceOfGuidePerWeek"));
      btp = BikeTourPlusApplication.getBikeTourPlus(); // Instantiate btp
      btp.setStartDate(startDate); // Setters
      btp.setNrWeeks(nrWeeks);
      btp.setPriceOfGuidePerWeek(priceOfGuidePerWeek);

      error = ""; // error counter
      errorCntr = 0;

    }
  }

  /**
   * @author Estefania Vazquez
   * 
   * @param dataTable - the data table containing the elements to test
   */
  @Given("the following pieces of gear exist in the system: \\(p4)")
  public void the_following_pieces_of_gear_exist_in_the_system_p4(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name"); // Extract data from the Cucumber data table
      int pricePerWeek = Integer.parseInt(row.get("pricePerWeek")); // Extract data from the
                                                                    // Cucumber data table
      btp.addGear(name, pricePerWeek); // Add extracted data to the current btp
    }
  }

  /**
   * @author William Zhang
   * 
   * @param dataTable
   */
  @Given("the following combos exist in the system: \\(p4)")
  public void the_following_combos_exist_in_the_system_p4(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows2 = dataTable.asMaps();
    for (var row : rows2) { // for each row of the table
      String name = row.get("name"); // Extract data from the table
      int discount = Integer.parseInt(row.get("discount"));
      Combo combo = new Combo(name, discount, btp); // Create new combo
      String[] itemsList = row.get("items").split(","); // Create lists from table data
      String[] quantityList = row.get("quantity").split(",");

      for (int i = 0; i < itemsList.length; i++) { // Since each quantity is always related in a
                                                   // one-to-one relationship to each item, the
                                                   // length can be either of the number of items or
                                                   // the quantity of each item.
        int qty = Integer.parseInt(quantityList[i]); // Transform quantity from String to int
        for (var gear : btp.getGear()) { // Iterate through the list of gears
          if (itemsList[i].equals(gear.getName())) {
            gear.addComboItem(qty, btp, combo); // Add combo item to related gear and combo (new
                                                // ComboItem(int aQuantity, BikeTourPlus
                                                // aBikeTourPlus, Combo aCombo, this))
          }

        }
      }
    }
  }

  /**
   * @author Faiza Chowdhury
   *
   * @param string - the old name of the gear
   * @param string2 - the new name of the gear
   * @param string3 - new price per week of the gear
   *
   *        When the given situation is encountered, the required method in the Controller is called
   */
  @When("the manager attempts to update the piece of gear with name {string} to have name {string} and price per week {string} \\(p4)")
  public void the_manager_attempts_to_update_the_piece_of_gear_with_name_to_have_name_and_price_per_week_p4(
      String string, String string2, String string3) {
    callController(
        BikeTourPlusFeatureSet5Controller.updateGear(string, string2, Integer.parseInt(string3)));
  }

  /**
   * @author Qin
   * 
   * @param string - name of the gear
   * @param string2 - price per week
   */
  @When("the manager attempts to add a new piece of gear with name {string} and price per week {string} \\(p4)")
  public void the_manager_attempts_to_add_a_new_piece_of_gear_with_name_and_price_per_week_p4(
      String string, String string2) {
    callController(BikeTourPlusFeatureSet5Controller.addGear(string, Integer.parseInt(string2)));
  }

  /**
   * @author Lin Wei
   * 
   * @param string - name
   * @param string2 - price per week Expected to find no Gear with this name and price per week
   */
  @Then("a piece of gear shall not exist with name {string} and price per week {string} \\(p4)")
  public void a_piece_of_gear_shall_not_exist_with_name_and_price_per_week_p4(String string,
      String string2) {
    boolean found = false; // keep track of if a Gear with given name and pricePerWeek was found
    // Check all Gear to find a match for given name and pricePerWeek
    for (Gear oneGear : btp.getGear()) {
      if (oneGear.getName().equals(string) && Integer.toString(oneGear.getPricePerWeek()).equals(string2)) {
        // Found a Gear with given name and pricePerWeek
        found = true;
        break;

      }
    }
    // Assert that a Gear was not found to have the given name and pricePerWeek
    assertFalse(found);
  }

  /**
   * @author Lin Wei
   * 
   * @param string - number of pieces of gear expected for the btp
   */
  @Then("the number of pieces of gear shall be {string} \\(p4)")
  public void the_number_of_pieces_of_gear_shall_be_p4(String string) {
    assertEquals(string, Integer.toString(btp.numberOfGear()));
  }

  /**
   * @author Ali Khasawneh
   * 
   * @param string
   */
  @Then("the system shall raise the error {string} \\(p4)")
  public void the_system_shall_raise_the_error_p4(String string) {
    assertTrue(error.contains(string));

  }

  /**
   * @author Estefania Vazquez
   * 
   * @param string - the name of the gear
   * @param string2 - the price per week of the gear
   */
  @Then("a piece of gear shall exist with name {string} and price per week {string} \\(p4)")
  public void a_piece_of_gear_shall_exist_with_name_and_price_per_week_p4(String string,
      String string2) {
    Gear gear = null;
    BookableItem bookableItem = BookableItem.getWithName(string); // Get the item with name string
    if (bookableItem instanceof Gear) {
      gear = (Gear) bookableItem; // If the item is of type Gear, then we do have a gear
    }

    assertNotNull(gear); // Check if there exists a gear with name string

    int pricePerWeek = gear.getPricePerWeek(); // Obtain the price of the gear with name string, if
                                               // there is one
    assertEquals(Integer.parseInt(string2), pricePerWeek); // Check if string has the same price as
                                                           // the one it should have
  }

  /**
   * @author Ali Khasawneh
   * 
   * @param string the number of gear pieces
   */
  @Then("the number of pieces of gear in the system shall be {string} \\(p4)")
  public void the_number_of_pieces_of_gear_in_the_system_shall_be_p4(String string) {
    assertEquals(string, Integer.toString(btp.numberOfGear()));
  }



  /**
   * This method calls controller and sets error and counter. (Was taken from the BTMS example)
   * 
   * @param result
   */
  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
      errorCntr += 1;
    }
  }
}
