package ca.mcgill.ecse.biketourplus.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet4Controller;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Manager;
import ca.mcgill.ecse.biketourplus.model.User;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.biketourplus.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.Date;
import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;

public class DeleteGuideStepDefinitions {

  private static BikeTourPlus bikeTourPlus = BikeTourPlusApplication.getBikeTourPlus();

  /**
   * @author Omar Marwan and Ahmed Nami
   * 
   *         Adding guides to system
   * 
   * @param dataTable
   */
  @Given("the following guides exist in the system: \\(p6)")
  public void the_following_guides_exist_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> guides = dataTable.asMaps(String.class, String.class); // list of all
                                                                                     // guides

    for (var guide : guides) {
      bikeTourPlus.addGuide(guide.get("email"), guide.get("password"), guide.get("name"),
          guide.get("emergencyContact")); // for each guide we're getting its email, password, name
                                          // and
                                          // emergency contact
    }

  }

  /**
   * @author Karim Al Sabbagh and Ahmed Nami
   *
   * 
   *         Adding participants to system
   *
   */
  @Given("the following participants exist in the system: \\(p6)")
  public void the_following_participants_exist_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions

    List<Map<String, String>> participants = dataTable.asMaps(String.class, String.class);

    for (var participant : participants) {

      int numberOfWeeks = Integer.parseInt(participant.get("nrWeeks")); // we're getting the number
                                                                        // of weeks
      int weeksAvailableFrom = Integer.parseInt(participant.get("weeksAvailableFrom")); // we're
                                                                                        // getting
                                                                                        // the begin
                                                                                        // date of
                                                                                        // availability
      int weeksAvailableUntil = Integer.parseInt(participant.get("weeksAvailableUntil")); // we're
                                                                                          // getting
                                                                                          // the end
                                                                                          // date of
                                                                                          // the
                                                                                          // availability
      boolean lodgeRequired = Boolean.valueOf(participant.get("lodgeRequired")); // we're checking
                                                                                 // if the
                                                                                 // participant
                                                                                 // required a lodge
                                                                                 // (yes or no)

      bikeTourPlus.addParticipant(participant.get("email"), participant.get("password"),
          participant.get("name"), participant.get("emergencyContact"), numberOfWeeks,
          weeksAvailableFrom, weeksAvailableUntil, lodgeRequired, null, 0); // we're adding the
                                                                            // participants
                                                                            // (iterating through a
                                                                            // for loop). For each
                                                                            // participant we are
                                                                            // getting its email,
                                                                            // password, name,
                                                                            // emergency contact,
                                                                            // number of weeks, the
                                                                            // begin and end dates
                                                                            // of the availability
                                                                            // and if the
                                                                            // participant required
                                                                            // a lodge

    }

  }

  /**
   * @author Omar Marwan
   *
   *         Manager attempts to delete a guide with their email
   *
   * @param string the guide's email
   */
  @When("the manager attempts to delete the guide with email {string} \\(p6)")
  public void the_manager_attempts_to_delete_the_guide_with_email_p6(String string) {

    BikeTourPlusFeatureSet4Controller.deleteGuide(string); // deleting the guide

  }

  /**
     * @author Omar Marwan and Lynn Haddad
     *
     *         Checking that a manager exists with the given email
     *
     * @param string
     */
    @Then("a manager account shall exist with email {string} \\(p6)")
    public void a_manager_account_shall_exist_with_email_p6(String string) {
        Manager manager = bikeTourPlus.getManager();
        assertNotNull(manager); // making sure that the manager exists
        User managerUser = (User) manager;
        assertEquals(string, managerUser.getEmail()); // making sure the manager has the email {string}

  }

  /**
   * @author Karim Al Sabbagh
   *
   *         Checking number of guides
   *
   * @param string number of managers
   */

  @Then("the number of guides shall be {string} \\(p6)")
  public void the_number_of_guides_shall_be_p6(String string) {
    int nrGuide = Integer.valueOf(string); // Convert to integer the number of guides given by the
                                           // string
    assertEquals(nrGuide, bikeTourPlus.getGuides().size()); // Check if the number of guides in the
                                                            // system is
                                                            // correct

  }

  /**
   * @author Karim Al Sabbagh
   *
   *         Checking number of managers
   *
   * @param string number of managers
   */
  @Then("the number of managers shall be {string} \\(p6)")
  public void the_number_of_managers_shall_be_p6(String string) {
    // Write code here that turns the phrase above into concrete actions
    int nrManagers = 0; // initialize the number of manager to 0
    if (bikeTourPlus.hasManager()) { // if hasManager is true, there is 1 manager in the system
      nrManagers++; // the number of manager become 1
    }

    assertEquals(Integer.valueOf(string), nrManagers); // Check if the number of manager in the
                                                       // system is correct

  }

  /**
   * @author Karim Al Sabbagh
   *
   *         Adding bike tour plus to system
   *
   * @param string number of managers
   */

  @Given("the following BikeTourPlus system exists: \\(p6)")
  public void the_following_bike_tour_plus_system_exists_p6(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> bikeTourPluses = dataTable.asMaps(String.class, String.class); // get
                                                                                             // the
                                                                                             // dataTable
                                                                                             // from
                                                                                             // the
                                                                                             // .feature
                                                                                             // file
    for (var btp : bikeTourPluses) { // extract the rows' values from bikeTourPluses and store them
                                     // into btp
      String startDate = btp.get("startDate"); // initialize Date startDate to btp's startDate
      Date stDate = Date.valueOf(startDate); // since startDate is a string, we must convert from
                                             // string to Date
                                             // using valueOf()
      int nrWeeks = Integer.valueOf(btp.get("nrWeeks")); // get and convert the string "nrWeeks" in
                                                         // btp to int
                                                         // using valueOf(). Store the value in
                                                         // integer nrWeeks
      int prg = Integer.valueOf(btp.get("priceOfGuidePerWeek")); // get and convert the string
                                                                 // "priceOfGuidePerWeek" in btp to
                                                                 // int using
                                                                 // valueOf(). Store the value in
                                                                 // integer prg
      bikeTourPlus.setStartDate(stDate); // set the system's start date to stDate
      bikeTourPlus.setNrWeeks(nrWeeks); // set the system's number of weeks to nrWeeks
      bikeTourPlus.setPriceOfGuidePerWeek(prg); // set the system's priceOfGuidePerWeek to prg

    }

    Manager manager = new Manager("manager@btp.com", "manager", bikeTourPlus); // create a new
                                                                               // manager
    bikeTourPlus.setManager(manager); // set the system's manager
  }

  /**
   * @author Sirine Tarhouni
   *
   *         Check if a participant account exists with the email given
   *
   * @param string, the email of the participant
   */
  @Then("a participant account shall exist with email {string} \\(p6)")
  public void a_participant_account_shall_exist_with_email_p6(String string) {
    User existingUser = User.getWithEmail(string); // get a user with email <string>
    Boolean participantExistance = false; // initialize participantExistance to false
    if (existingUser != null) { // if the user with email <string> exists
      if (existingUser instanceof Participant) { // check that the user is a participant
        participantExistance = true; // if the user with email <string> is a participant, then
                                     // participantExistance is true
      }
    }
    assertTrue(participantExistance); // the participant shall exist with email <string>. Therefore,
                                      // we
                                      // expect participantExistance to be true

  }

  /**
   * @author Karim Al Sabbagh
   *
   *         Checking number of participants
   *
   * @param string number of participants
   */
  @Then("the number of participants shall be {string} \\(p6)")
  public void the_number_of_participants_shall_be_p6(String string) {
    // Write code here that turns the phrase above into concrete actions
    int nrParticipants = Integer.valueOf(string); // convert string to integer and store the value
                                                  // in nrParticipants
    assertEquals(nrParticipants, bikeTourPlus.getParticipants().size()); // we expect the number of
                                                                         // participants in
                                                                         // the system to be equal
                                                                         // to nrParticipants

  }

  /**
   * @author Omar Marwan
   *
   *         Checking that a guide doesn't exist with the given email
   *
   * @param string
   */
  @Then("a guide account shall not exist with email {string} \\(p6)")
  public void a_guide_account_shall_not_exist_with_email_p6(String string) {
    User existingUser = User.getWithEmail(string); // get a user with email <string>
    Boolean guideExistance = false; // initialize a boolean that checks the existance of a guide
    if (existingUser != null) { // if the existing user isn't null
      if (existingUser instanceof Guide) { // check that the existing user is a guide
        guideExistance = true; // if it is, then guideExistance is true
      }
    }
    assertFalse(guideExistance); // the expected value for guideExistance is false since the guide
                                 // account
                                 // shall not exist with email <string>

  }
}
