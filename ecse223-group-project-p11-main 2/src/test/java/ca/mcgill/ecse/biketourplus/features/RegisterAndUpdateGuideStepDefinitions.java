package ca.mcgill.ecse.biketourplus.features;

import static ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet4Controller.*;
import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class RegisterAndUpdateGuideStepDefinitions {

  private BikeTourPlus btp;
  private String error;
  private int errorCount;

  /**
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  @Given("the following participants exist in the system: \\(p9)")
  public void the_following_participants_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) { // for each row, parse the information to create a guide within the
                           // system
      btp.addParticipant(row.get("email"), row.get("password"), row.get("name"),
          row.get("emergencyContact"), Integer.parseInt(row.get("nrWeeks")),
          Integer.parseInt(row.get("weeksAvailableFrom")),
          Integer.parseInt(row.get("weeksAvailableUntil")),
          Boolean.parseBoolean(row.get("lodgeRequired")), null, 0);
    }
  }


  /**
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  @Given("the following BikeTourPlus system exists: \\(p9)")
  public void the_following_bike_tour_plus_system_exists_p9(
      io.cucumber.datatable.DataTable dataTable) {
    Map<String, String> data = dataTable.asMaps().get(0);
    btp = BikeTourPlusApplication.getBikeTourPlus();
    btp.setStartDate(Date.valueOf(data.get("startDate")));
    btp.setNrWeeks(Integer.parseInt(data.get("nrWeeks")));
    btp.setPriceOfGuidePerWeek(Integer.parseInt(data.get("priceOfGuidePerWeek")));
    error = "";
    errorCount = 0;
  }

  /**
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  @Given("the following guides exist in the system: \\(p9)")
  public void the_following_guides_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      btp.addGuide(row.get("email"), row.get("password"), row.get("name"),
          row.get("emergencyContact"));
    }
  }

  /**
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  @When("the guide with email {string} attempts to update their account information to password {string}, name {string}, and emergency contact {string} \\(p9)")
  public void the_guide_with_email_attempts_to_update_their_account_information_to_password_name_and_emergency_contact_p9(
      String email, String password, String name, String emergencyContact) {
    callController(updateGuide(email, password, name, emergencyContact)); // add guides and register
                                                                          // error
  }

  /**
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  @When("a new guide attempts to register with email {string}, password {string}, name {string}, and emergency contact {string} \\(p9)")
  public void a_new_guide_attempts_to_register_with_email_password_name_and_emergency_contact_p9(
      String email, String password, String name, String emergencyContact) {
    callController(registerGuide(email, password, name, emergencyContact));
  }

  /**
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  @Then("a guide account shall not exist with email {string}, password {string}, name {string}, and emergency contact {string} \\(p9)")
  public void a_guide_account_shall_not_exist_with_email_password_name_and_emergency_contact_p9(
      String email, String password, String name, String emergencyContact) {
    User user = User.getWithEmail(email); // try to find user with given email
    Guide guide = user instanceof Guide ? ((Guide) user) : null; // Is User a Guide? if so, cast it
                                                                 // to a Guide
    if (guide != null) { // if guide exists, then assert that there was a change
      boolean atLeastOneGuideAttributeInvalid =
          (!(password.equals(guide.getPassword())) || !(name.equals(guide.getName()))
              || !(emergencyContact.equals(guide.getEmergencyContact())));
      assertTrue(atLeastOneGuideAttributeInvalid);
    }
  }

  /**
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  @Then("the number of guides shall be {string} \\(p9)")
  public void the_number_of_guides_shall_be_p9(String string) {
    assertEquals(Integer.parseInt(string), btp.getGuides().size());
  }

  /**
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  @Then("the system shall raise the error {string} \\(p9)")
  public void the_system_shall_raise_the_error_p9(String error) {
    assertEquals(error, this.error);
    assertNotEquals(0, this.errorCount);
  }

  /**
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  @Then("a guide account shall exist with email {string}, password {string}, name {string}, and emergency contact {string} \\(p9)")
  public void a_guide_account_shall_exist_with_email_password_name_and_emergency_contact_p9(
      String email, String password, String name, String emergencyContact) {
    User user = User.getWithEmail(email); // try to find user with given email
    Guide guide = user instanceof Guide ? ((Guide) user) : null; // Is User a Guide? if so, cast it
                                                                 // to a Guide
    // assert that guide exists
    assertNotNull(guide);
    // assert that all information corresponds to the inputs
    assertEquals(email, guide.getEmail());
    assertEquals(password, guide.getPassword());
    assertEquals(name, guide.getName());
    assertEquals(emergencyContact, guide.getEmergencyContact());
  }

  /**
   * This method counts and registers the errors Taken from btms example
   * 
   * @author aptrott, adrianmarcellus, David-Ly-3990, A13XBGA, 0vErHe4vEn
   */
  private void callController(String result) {
    if (!result.isEmpty()) {
      error += result;
      errorCount += 1;
    }
  }
}
