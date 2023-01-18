package ca.mcgill.ecse.biketourplus.features;

import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet3Controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication.getBikeTourPlus;
import static org.junit.Assert.*;

/**
 * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
 */
public class RegisterAndUpdateParticipantStepDefinitions {
  private final BikeTourPlus bikeTourPlus = getBikeTourPlus();
  private String message;

  /**
   * @param dataTable
   * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
   */
  @Given("the following BikeTourPlus system exists: \\(p1)")
  public void the_following_BikeTourPlus_System_exists_p1(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) { //for each row, get the information to create a BikeTourPlus system
      Date startDate = Date.valueOf(row.get("startDate"));
      bikeTourPlus.setStartDate(startDate);
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      bikeTourPlus.setNrWeeks(nrWeeks);
      int priceOfGuidePerWeek = Integer.parseInt(row.get("priceOfGuidePerWeek"));
      bikeTourPlus.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
    }
  }

  /**
   * @param dataTable
   * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
   */
  @Given("the following guides exist in the system: \\(p1)")
  public void the_following_guides_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencyContact = row.get("emergencyContact");
      bikeTourPlus.addGuide(email, password, name, emergencyContact);
    }
  }

  /**
   * @param dataTable
   * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
   */
  @Given("the following participants exist in the system: \\(p1)")
  public void the_following_participants_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) { //for each row, get the information to create a participant within the system
      bikeTourPlus.addParticipant(row.get("email"), row.get("password"), row.get("name"),
          row.get("emergencyContact"), Integer.parseInt(row.get("nrWeeks")),
          Integer.parseInt(row.get("weeksAvailableFrom")),
          Integer.parseInt(row.get("weeksAvailableUntil")),
          Boolean.parseBoolean(row.get("lodgeRequired")), "", 0);
    }
  }

  /**
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param nrWeeks
   * @param weeksAvailableFrom
   * @param weeksAvailableUntil
   * @param lodgeRequired
   * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
   */
  @When(
      "the participant with email {string} attempts to update their account information to password {string}, name {string}, emergency contact {string}, number of weeks {string}, week available from {string}, week available until {string}, and lodge required {string} \\(p1)")
  public void the_participant_with_email_attempts_to_update_their_account_information_to_password_name_emergency_contact_number_of_weeks_week_available_from_week_available_until_and_lodge_required_p1(
      String email, String password, String name, String emergencyContact, String nrWeeks,
      String weeksAvailableFrom, String weeksAvailableUntil, String lodgeRequired) {
    message =
        BikeTourPlusFeatureSet3Controller.updateParticipant(email, password, name, emergencyContact,
            Integer.parseInt(nrWeeks), Integer.parseInt(weeksAvailableFrom),
            Integer.parseInt(weeksAvailableUntil), Boolean.parseBoolean(lodgeRequired));
  }

  /**
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param nrWeeks
   * @param weeksAvailableFrom
   * @param weeksAvailableUntil
   * @param lodgeRequired
   * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
   */
  @When(
      "a new participant attempts to register with email {string}, password {string}, name {string}, emergency contact {string}, number of weeks {string}, week available from {string}, week available until {string}, and lodge required {string} \\(p1)")
  public void a_new_participant_attempts_to_register_with_email_password_name_emergency_contact_number_of_weeks_week_available_from_week_available_until_and_lodge_required_p1(
      String email, String password, String name, String emergencyContact, String nrWeeks,
      String weeksAvailableFrom, String weeksAvailableUntil, String lodgeRequired) {
    // Write code here that turns the phrase above into concrete actions
    message = BikeTourPlusFeatureSet3Controller.registerParticipant(email, password, name,
        emergencyContact, Integer.parseInt(nrWeeks), Integer.parseInt(weeksAvailableFrom),
        Integer.parseInt(weeksAvailableUntil), Boolean.parseBoolean(lodgeRequired));
  }

  /**
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param nrWeeks
   * @param weeksAvailableFrom
   * @param weeksAvailableUntil
   * @param lodgeRequired
   * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
   */
  @Then(
      "a participant account shall not exist with email {string}, password {string}, name {string}, emergency contact {string}, number of weeks {string}, week available from {string}, week available until {string}, and lodge required {string} \\(p1)")
  public void a_participant_account_shall_not_exist_with_email_password_name_emergency_contact_number_of_weeks_week_available_from_week_available_until_and_lodge_required_p1(
      String email, String password, String name, String emergencyContact, String nrWeeks,
      String weeksAvailableFrom, String weeksAvailableUntil, String lodgeRequired) {
    for (Participant participant : bikeTourPlus.getParticipants()) {
      assertFalse(participant.getEmail().equals(email) && participant.getPassword()
          .equals(password) && participant.getName()
          .equals(name) && participant.getEmergencyContact()
          .equals(emergencyContact) && participant.getNrWeeks() == Integer.parseInt(
          nrWeeks) && participant.getWeekAvailableFrom() == Integer.parseInt(
          weeksAvailableFrom) && participant.getWeekAvailableUntil() == Integer.parseInt(
          weeksAvailableUntil) && participant.getLodgeRequired() == Boolean.parseBoolean(
          lodgeRequired));
    }
  }

  /**
   * @param numberOfParticipants
   * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
   */
  @Then("the number of participants shall be {string} \\(p1)")
  public void the_number_of_participants_shall_be_p1(String numberOfParticipants) {
    assertEquals(Integer.parseInt(numberOfParticipants), bikeTourPlus.numberOfParticipants());
  }

  /**
   * @param errorMsg
   * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
   */
  @Then("the system shall raise the error {string} \\(p1)")
  public void the_system_shall_raise_the_error_p1(String errorMsg) {
    assertTrue(message.contains(errorMsg));
  }

  /**
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param nrWeeks
   * @param weeksAvailableFrom
   * @param weeksAvailableUntil
   * @param lodgeRequired
   * @author Yifan Du, Shuzhao Feng, Caroline Kouri, Hao Yuan Lu, Raphaël Verger, Barry Zhang
   */
  @Then(
      "a participant account shall exist with email {string}, password {string}, name {string}, emergency contact {string}, number of weeks {string}, week available from {string}, week available until {string}, and lodge required {string} \\(p1)")
  public void a_participant_account_shall_exist_with_email_password_name_emergency_contact_number_of_weeks_week_available_from_week_available_until_and_lodge_required_p1(
      String email, String password, String name, String emergencyContact, String nrWeeks,
      String weeksAvailableFrom, String weeksAvailableUntil, String lodgeRequired) {
    User maybeParticipant = User.getWithEmail(email);
    assertTrue(maybeParticipant instanceof Participant);
    Participant participant = (Participant) maybeParticipant;
    assertEquals(email, participant.getEmail());
    assertEquals(password, participant.getPassword());
    assertEquals(name, participant.getName());
    assertEquals(emergencyContact, participant.getEmergencyContact());
    assertEquals(Integer.parseInt(nrWeeks), participant.getNrWeeks());
    assertEquals(Integer.parseInt(weeksAvailableFrom), participant.getWeekAvailableFrom());
    assertEquals(Integer.parseInt(weeksAvailableUntil), participant.getWeekAvailableUntil());
    assertEquals(Boolean.parseBoolean(lodgeRequired), participant.getLodgeRequired());
  }
}
