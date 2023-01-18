package ca.mcgill.ecse.biketourplus.features;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.*;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusCommonAPI;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class BikeToursStepDefinitions {

  private BikeTourPlus bikeTourPlus;
  private String error;

  /**
   * Instantiates the BikeTourPlus system by setting the startDate, nrWeeks and priceOfGuidePerWeek.
   * Taken from ViewBikeTourStepDefinitions.java
   * 
   * @author Simon Younaki
   * @param dataTable Table with bikeTourPlus data specified in Cucumber Feature file
   */
  @Given("the following BikeTourPlus system exists:")
  public void the_following_bike_tour_plus_system_exists(
      io.cucumber.datatable.DataTable dataTable) {
    bikeTourPlus = BikeTourPlusApplication.getBikeTourPlus();

    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      Date startDate = Date.valueOf(row.get("startDate"));
      int nrWeeks = Integer.valueOf(row.get("nrWeeks"));
      int weeklyGuidePrice = Integer.valueOf(row.get("priceOfGuidePerWeek"));
      bikeTourPlus.setStartDate(startDate);
      bikeTourPlus.setNrWeeks(nrWeeks);
      bikeTourPlus.setPriceOfGuidePerWeek(weeklyGuidePrice);
    }
  }

  /**
   * Adds the guides with the specified email, password, name and emergencyContact to the system.
   * Taken from ViewBikeTourStepDefinitions.java
   * 
   * @author Ari Smith
   * @param dataTable Table with guide data specified in Cucumber Feature file
   */
  @Given("the following guides exist in the system:")
  public void the_following_guides_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {

      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencyContact = row.get("emergencyContact");

      bikeTourPlus.addGuide(email, password, name, emergencyContact);
    }
  }

  /**
   * Adds the participants with the specified email, password, name, emergencyContact, nrWeeks
   * weeksAvailableFrom, weeksAvailableUntil and lodgeRequired. Taken from
   * ViewBikeTourStepDefinitions.java
   * 
   * @author Annie Gouchee
   * @param dataTable Table with participant data specified in Cucumber Feature file
   */
  @Given("the following participants exist in the system:")
  public void the_following_participants_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencyContact = row.get("emergencyContact");
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      int weeksAvailableFrom = Integer.parseInt(row.get("weeksAvailableFrom"));
      int weeksAvailableUntil = Integer.parseInt(row.get("weeksAvailableUntil"));
      boolean lodgeRequired = Boolean.parseBoolean(row.get("lodgeRequired"));
      bikeTourPlus.addParticipant(email, password, name, emergencyContact, nrWeeks,
          weeksAvailableFrom, weeksAvailableUntil, lodgeRequired, "", 0);
    }
  }

  /**
   * Initiates the tour creation process by calling the controller method.
   * 
   * @author Ari Smith
   */
  @When("the administrator attempts to initiate the bike tour creation process")
  public void the_administrator_attempts_to_initiate_the_bike_tour_creation_process() {
    error = BikeTourPlusCommonAPI.initiateTourCreation();
  }



  /**
   * Checks that the expected bike tours exist with the specified parameters.
   * 
   * @author Ari Smith
   * @param dataTable Table with expected bike tour data specified in Cucumber Feature File
   */
  @Then("the following bike tours shall exist in the system:")
  public void the_following_bike_tours_shall_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {

      int id = Integer.parseInt(row.get("id"));
      BikeTour bikeTour = BikeTour.getWithId(id);
      assertEquals(Integer.parseInt(row.get("startWeek")), bikeTour.getStartWeek());
      assertEquals(Integer.parseInt(row.get("endWeek")), bikeTour.getEndWeek());
      assertEquals(row.get("guide"), bikeTour.getGuide().getEmail());

      String[] participants = row.get("participants").split(",");
      for (String p : participants) {
        assertEquals(bikeTour, ((Participant) User.getWithEmail(p)).getBikeTour());
      }


    }

  }

  /**
   * Checks that the participant associated with the specified email has the correct status.
   * 
   * @author Martin Nguyen
   * @param string Participant email
   * @param string2 Expected status of participant
   */
  @Then("the participant with email {string} shall be marked as {string}")
  public void the_participant_with_email_shall_be_marked_as(String string, String string2) {
    Participant p = (Participant) User.getWithEmail(string);
    assertEquals(string2, p.getSmFullName());
  }

  /**
   * Checks that the number of bike tours is correct.
   * 
   * @author Martin Nguyen
   * @param string Expected number of bike tours
   */
  @Then("the number of bike tours shall be {string}")
  public void the_number_of_bike_tours_shall_be(String string) {
    String numberOfBikeTours = Integer.toString(bikeTourPlus.numberOfBikeTours());
    assertEquals(string, numberOfBikeTours);
  }

  /**
   * Checks that the system raises the correct error.
   * 
   * @author Martin Nguyen
   * @param string Expected error message
   */
  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String string) {
    assertTrue(error.contains(string));
  }

  /**
   * Adds the pieces of gear with the specified name and pericePerWeek to the system. Taken from
   * ViewBikeTourStepDefinitions.java
   * 
   * @author Simon Younaki
   * @param dataTable Table with gear data specified in Cucumber Feature File
   */
  @Given("the following pieces of gear exist in the system:")
  public void the_following_pieces_of_gear_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String gearName = row.get("name");
      int price = Integer.valueOf(row.get("pricePerWeek"));

      bikeTourPlus.addGear(gearName, price);
    }
  }

  /**
   * Checks that the participant under the specified email has cancelled their tour.
   * 
   * @author Martin Nguyen
   * @param string Participant email
   */
  @Given("the participant with email {string} has cancelled their tour")
  public void the_participant_with_email_has_cancelled_their_tour(String string) {
    Participant p = (Participant) User.getWithEmail(string);
    p.cancel();

  }

  /**
   * Adds the combo items with the specified, name, discount, gears and associated quantities. Taken
   * from ViewBikeTourStepDefinitions.java
   * 
   * @author Ari Smith
   * @param dataTable Table with combo data specified in Cucumber Feature File
   */
  @Given("the following combos exist in the system:")
  public void the_following_combos_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {

      String comboName = row.get("name");
      int discount = Integer.valueOf(row.get("discount"));

      List<String> items = Arrays.asList(row.get("items").split(","));
      List<String> quantity = Arrays.asList(row.get("quantity").split(","));

      Combo combo = bikeTourPlus.addCombo(comboName, discount);

      int i = 0;
      for (String item : items) {
        for (Gear existingGear : bikeTourPlus.getGear()) {
          if (existingGear.getName().equals(item)) {
            int gearQuantity = Integer.parseInt(quantity.get(i));
            bikeTourPlus.addComboItem(gearQuantity, combo, existingGear);
            i++;
          }
        }
      }
    }
  }

  /**
   * Books the specified gears and their quantities for the participant account under the specified
   * email. Taken from ViewBikeTourStepDefinitions.java
   * 
   * @author Annie Gouchee
   * @param dataTable Table with participant and gear data specified in Cucumber Feature File
   */
  @Given("the following participants request the following pieces of gear:")
  public void the_following_participants_request_the_following_pieces_of_gear(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      Participant p = (Participant) Participant.getWithEmail(email);

      String gearName = row.get("gear");
      BookableItem item = BookableItem.getWithName(gearName);

      int quantity = Integer.valueOf(row.get("quantity"));
      p.addBookedItem(quantity, bikeTourPlus, item);
    }
  }

  /**
   * Books the specified combos and their quantities for the participant account under the specified
   * email. Taken from ViewBikeTourStepDefinitions.java
   * 
   * @author Andrew Kan
   * @param dataTable Table with participant and combo data specified in Cucumber Feature File
   */
  @Given("the following participants request the following combos:")
  public void the_following_participants_request_the_following_combos(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String comboName = row.get("gear");
      int quantity = Integer.parseInt(row.get("quantity"));

      Participant participant = (Participant) User.getWithEmail(email);
      Combo combo = (Combo) BookableItem.getWithName(comboName);
      participant.addBookedItem(quantity, bikeTourPlus, combo);
    }
  }

  /**
   * Sets the participant under the specified email's status to Banned.
   * 
   * @author Annie Gouchee
   * @param string Participant email
   */
  @Given("the participant with email {string} is banned")
  public void the_participant_with_email_is_banned(String string) {
    Participant participant = (Participant) User.getWithEmail(string);
    participant.setAuthorizationCode(null);
    participant.goOnTour();
  }

  /**
   * Sets the participant under the specified email's status to Started.
   * 
   * @author Annie Gouchee
   * @param string Participant email
   */
  @Given("the participant with email {string} has started their tour")
  public void the_participant_with_email_has_started_their_tour(String string) {
    Participant participant = (Participant) User.getWithEmail(string);
    participant.setAuthorizationCode("PAID");
    participant.pay();
    participant.goOnTour();

  }

  /**
   * Sets the participant under the specified email's status to Paid.
   * 
   * @author Martin Nguyen
   * @param string Participant email
   */
  @Given("the participant with email {string} has paid for their tour")
  public void the_participant_with_email_has_paid_for_their_tour(String string) {
    Participant p = (Participant) User.getWithEmail(string);
    p.setAuthorizationCode("PAID");
    p.pay();
  }

  /**
   * Adds the bike tours with the specified id, startWeek, endWeek, participants and guides. Taken
   * from ViewBikeTourStepDefinitions.java and modified.
   * 
   * @author Ari Smith
   * @param dataTable Table with bike tour data specified in Cucumber Feature File
   */
  @Given("the following bike tours exist in the system:")
  public void the_following_bike_tours_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      int id = Integer.parseInt(row.get("id"));
      int startWeek = Integer.parseInt(row.get("startWeek"));
      int endWeek = Integer.parseInt(row.get("endWeek"));
      String[] participantEmails = row.get("participants").split(",");
      String guideEmail = row.get("guide");

      Guide guide = (Guide) User.getWithEmail(guideEmail);
      BikeTour tour = bikeTourPlus.addBikeTour(id, startWeek, endWeek, guide);

      for (String email : participantEmails) {
        Participant p = (Participant) User.getWithEmail(email);
        tour.addParticipant(p);
        p.assignParticipant();
      }


    }
  }

  /**
   * Sets the status of the participant under the specified email to Finished.
   * 
   * @author Martin Nguyen, Annie Gouchee and Simon Younaki
   * @param string Participant email
   */
  @Given("the participant with email {string} has finished their tour")
  public void the_participant_with_email_has_finished_their_tour(String string) {
    Participant p = (Participant) User.getWithEmail(string);
    p.setAuthorizationCode("PAID");
    p.pay();
    p.goOnTour();
    p.finishTour();
  }

  /**
   * Attempts to cancel the tour for the account under the specified email using the controller
   * method.
   * 
   * @author Simon Younaki
   * @param string Participant email
   */
  @When("the manager attempts to cancel the tour for email {string}")
  public void the_manager_attempts_to_cancel_the_tour_for_email(String string) {
    error = BikeTourPlusCommonAPI.cancelParticipantTrip(string);
  }

  /**
   * Attempts to finish the tour for the account under the specified email using the controller
   * method.
   * 
   * @author Simon Younaki
   * @param string Participant email
   */
  @When("the manager attempts to finish the tour for the participant with email {string}")
  public void the_manager_attempts_to_finish_the_tour_for_the_participant_with_email(
      String string) {
    error = BikeTourPlusCommonAPI.finishParticipantTrip(string);
  }

  /**
   * Attempts to start the tour for the account under the specified email using the controller
   * method.
   * 
   * @author Simon Younaki
   * @param string Bike tour start week number
   */
  @When("the manager attempts to start the tours for week {string}")
  public void the_manager_attempts_to_start_the_tours_for_week(String string) {
    int weekNum = Integer.parseInt(string);
    error = BikeTourPlusCommonAPI.startTripsForWeek(weekNum);
  }

  /**
   * Attempts to confirm the payment by inputting the authorization code using the controller
   * method.
   * 
   * @author Simon Younaki
   * @param string Participant email
   * @param string2 Authorization code
   */
  @When("the manager attempts to confirm payment for email {string} using authorization code {string}")
  public void the_manager_attempts_to_confirm_payment_for_email_using_authorization_code(
      String string, String string2) {
    error = BikeTourPlusCommonAPI.payForParticipantTrip(string, string2);
  }

  /**
   * Validates that a participant account under a specified email that should not exist in the
   * system does not exist.
   * 
   * @author Simon Younaki
   * @param string Email of a participant that does not exist in the bike tour plus system
   */
  @Then("a participant account shall not exist with email {string}")
  public void a_participant_account_shall_not_exist_with_email(String string) {
    User theUser = User.getWithEmail(string);
    boolean doesItExist = theUser instanceof Participant;
    assertFalse(doesItExist);
  }

  /**
   * Checks the number of participants in the bike tour plus system.
   * 
   * @author Annie Gouchee
   * @param string Expected number of participants
   */
  @Then("the number of participants shall be {string}")
  public void the_number_of_participants_shall_be(String string) {
    assertEquals(Integer.parseInt(string), bikeTourPlus.numberOfParticipants());
  }


  /**
   * Checks that a participant under the specified email exists and has the correct refund
   * percentage.
   * 
   * @author Annie Gouchee
   * @param string Participant email
   * @param string2 Refund percentage
   */
  @Then("a participant account shall exist with email {string} and a refund of {string} percent")
  public void a_participant_account_shall_exist_with_email_and_a_refund_of_percent(String string,
      String string2) {

    User user = User.getWithEmail(string);
    assertTrue(user instanceof Participant);
    Participant p = (Participant) user;
    assertEquals(Integer.parseInt(string2), p.getRefundedPercentageAmount());

  }

  /**
   * Checks that a participant under the specified email exists and has the authorizaiton code.
   * 
   * @author Annie Gouchee
   * @param string Participant email
   * @param string2 Authorization code
   */
  @Then("a participant account shall exist with email {string} and authorization code {string}")
  public void a_participant_account_shall_exist_with_email_and_authorization_code(String string,
      String string2) {
    User user = User.getWithEmail(string);
    assertTrue(user instanceof Participant);
    Participant p = (Participant) user;
    assertEquals(string2, p.getAuthorizationCode());
  }
}
