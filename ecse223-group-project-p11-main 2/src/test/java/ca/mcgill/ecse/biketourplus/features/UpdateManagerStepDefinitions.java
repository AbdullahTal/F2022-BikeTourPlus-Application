package ca.mcgill.ecse.biketourplus.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.mcgill.ecse.biketourplus.model.*;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.Date;
import java.util.*;

public class UpdateManagerStepDefinitions {
  private BikeTourPlus btp;
  private String error = "";

  /**
   * Make sure the following BikeTourPlus system exists
   * 
   * @param dataTable
   * @author Chenxin Xun, Marco Vidalon
   */
  @Given("the following BikeTourPlus system exists: \\(p5)")
  public void the_following_bike_tour_plus_system_exists_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    Map<String, String> btpMap = rows.get(0);
    Date startDate = null;
    startDate = Date.valueOf(btpMap.get("startDate"));
    btp = BikeTourPlusApplication.getBikeTourPlus();
    btp.setStartDate(startDate);
    btp.setNrWeeks(Integer.parseInt((btpMap.get("nrWeeks"))));
    btp.setPriceOfGuidePerWeek(Integer.parseInt((btpMap.get("priceOfGuidePerWeek"))));
  }

  /**
   * This method creates the manager with the given information from the dataTable into the
   * bikeTourPlus system.
   * 
   * @param dataTable
   * @author Yicheng Yuan
   */
  @Given("the following manager exists in the system: \\(p5)")
  public void the_following_manager_exists_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(); // retrieve the information from the
                                                         // dataTable

    for (Map<String, String> row : rows) { // Create manager with the dataTable information
      String email = row.get("email");
      String password = row.get("password");
      Manager manager = new Manager(email, password, btp);
      btp.setManager(manager);
    }
  }

  /**
   *
   * @param string -- new password that the manager wants to change to
   * @author Mike Zhang
   *
   *         Description: The method gets the manager of the BikeTourPlus, and the manager will
   *         attempt to set the password to string
   */
  @When("the manager attempts to update their password to {string} \\(p5)")
  public void the_manager_attempts_to_update_their_password_to_p5(String string) {
    error = BikeTourPlusFeatureSet1Controller.updateManager(string);
  }


  /**
   * Asserts whether a manager account exists with the provided email and password.
   * 
   * @param email
   * @param password
   * @author Shaun Soobagrah
   */
  @Then("a manager account shall exist with email {string} and password {string} \\(p5)")
  public void a_manager_account_shall_exist_with_email_and_password_p5(String email,
      String password) {

    Manager manager = btp.getManager();

    assertTrue(manager != null);
    assertEquals(email, manager.getEmail());
    assertEquals(password, manager.getPassword());

  }

  /**
   * Check whether input==number of managers
   * 
   * @param input
   * @author Weiheng Xiao
   */
  @Then("the number of managers shall be {string} \\(p5)")
  public void the_number_of_managers_shall_be_p5(String input) {
    int numOfManager = Integer.parseInt(input);
    int indicator = 0;
    if (btp.getManager() != null) {
      indicator = 1;
    }
    assertEquals(numOfManager, indicator);
  }

  /**
   * Asserts whether the expected error was raised.
   * 
   * @param errorMessage
   * @author Marco Vidalon
   */
  @Then("the system shall raise the error {string} \\(p5)")
  public void the_system_shall_raise_the_error_p5(String errorMessage) {
    assertTrue(error.contains(errorMessage));
  }

  /**
   * Asserts whether a manager account exists with the provided email and password.
   * 
   * @param email
   * @param password
   * @author Marco Vidalon
   */
  @Then("a manager account shall exist with email {string} and with password {string} \\(p5)")
  public void a_manager_account_shall_exist_with_email_and_with_password_p5(String email,
      String password) {
    Manager manager = btp.getManager();
    assertTrue(manager != null);
    assertTrue(manager.getEmail().equals(email));
    assertTrue(manager.getPassword().equals(password));
  }
}
