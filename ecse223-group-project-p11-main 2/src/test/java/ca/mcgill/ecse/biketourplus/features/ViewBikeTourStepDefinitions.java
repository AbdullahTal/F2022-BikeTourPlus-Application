package ca.mcgill.ecse.biketourplus.features;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet1Controller;
import ca.mcgill.ecse.biketourplus.controller.TOBikeTour;
import ca.mcgill.ecse.biketourplus.controller.TOParticipantCost;
import ca.mcgill.ecse.biketourplus.model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class ViewBikeTourStepDefinitions {
  private BikeTourPlus bikeTourPlus;
  private TOBikeTour tour;

  /**
   * @author Simon Younaki
   * @param dataTable
   */
  @Given("the following BikeTourPlus system exists: \\(p11)")
  public void the_following_bike_tour_plus_system_exists_p11(
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
   * @author Simon Younaki
   * @param dataTable
   */
  @Given("the following pieces of gear exist in the system: \\(p11)")
  public void the_following_pieces_of_gear_exist_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String gearName = row.get("name");
      int price = Integer.valueOf(row.get("pricePerWeek"));

      bikeTourPlus.addGear(gearName, price);
    }
  }

  /**
   * @Author Ari Smith
   * @param dataTable
   */
  @Given("the following combos exist in the system: \\(p11)")
  public void the_following_combos_exist_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {

      String comboName = row.get("name");
      int discount = Integer.valueOf(row.get("discount"));

      List<String> items = Arrays.asList(row.get("items").split(","));

      List<String> tempQuantity = Arrays.asList(row.get("quantity").split(","));
      List<Integer> quantity = new ArrayList<Integer>();
      for (String q : tempQuantity) {
        quantity.add(Integer.valueOf(q));
      }


      Combo combo = bikeTourPlus.addCombo(comboName, discount);

      int i = 0;
      for (String item : items) {
        for (Gear existingGear : bikeTourPlus.getGear()) {
          if (existingGear.getName().equals(item)) {
            int gearQuantity = quantity.get(i);
            bikeTourPlus.addComboItem(gearQuantity, combo, existingGear);
            i++;

          }
        }
      }

    }
  }

  /**
   * @author Ari Smith
   * @param dataTable
   */
  @Given("the following guides exist in the system: \\(p11)")
  public void the_following_guides_exist_in_the_system_p11(
      io.cucumber.datatable.DataTable dataTable) {

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
   * @author Annie Gouchee
   * @param dataTable
   */
  @Given("the following participants exist in the system: \\(p11)")
  public void the_following_participants_exist_in_the_system_p11(
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
   * @author Annie Gouchee
   * @param dataTable
   */
  @Given("the following participants request the following pieces of gear: \\(p11)")
  public void the_following_participants_request_the_following_pieces_of_gear_p11(
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
   * @author Andrew Kan
   * @param dataTable
   */
  @Given("the following participants request the following combos: \\(p11)")
  public void the_following_participants_request_the_following_combos_p11(
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
   * @author Andrew Kan
   * @param dataTable
   */
  @Given("the following bike tours exist in the system: \\(p11)")
  public void the_following_bike_tours_exist_in_the_system_p11(
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
      }
    }
  }

  /**
   * @author Martin Nguyen
   * @param string
   */
  @When("the manager attempts to view the bike tour with id {string} \\(p11)")
  public void the_manager_attempts_to_view_the_bike_tour_with_id_p11(String string) {
    tour = BikeTourPlusFeatureSet1Controller.getBikeTour(Integer.parseInt(string));
  }

  /**
   * @author Andrew Kan
   * @param dataTable
   */
  @Then("the following bike tour information shall be provided: \\(p11)")
  public void the_following_bike_tour_information_shall_be_provided_p11(
      io.cucumber.datatable.DataTable dataTable) {
    assertNotNull(tour); // tour with id should exist
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      assertEquals(Integer.parseInt(row.get("id")), tour.getId());
      assertEquals(Integer.parseInt(row.get("startWeek")), tour.getStartWeek());
      assertEquals(Integer.parseInt(row.get("endWeek")), tour.getEndWeek());
      assertEquals(row.get("guideEmail"), tour.getGuideEmail());
      assertEquals(row.get("guideName"), tour.getGuideName());
      assertEquals(Integer.parseInt(row.get("totalCostForGuide")), tour.getTotalCostForGuide());

      // Check info for each participant in list, which is stored in participantCost transfer
      // objects
      String[] givenParticipantEmails = row.get("participantsEmail").split(",");
      assertEquals(givenParticipantEmails.length, tour.numberOfParticipantCosts());

      List<TOParticipantCost> participantCosts = tour.getParticipantCosts();
      List<Integer> checkedIndices = new ArrayList<Integer>();
      for (int i = 0; i < participantCosts.size(); i++) {
        TOParticipantCost pc = participantCosts.get(i);
        // Need to use participant info corresponding to participantCost in list
        int index = Arrays.asList(givenParticipantEmails).indexOf(pc.getParticipantEmail());
        assertNotEquals(-1, index); // check user email exists in list
        assertFalse(checkedIndices.contains(index)); // check that this index hasn't been checked
        checkedIndices.add(index);

        assertEquals(row.get("participantsEmail").split(",")[index], pc.getParticipantEmail());
        assertEquals(row.get("participantsName").split(",")[index], pc.getParticipantName());
        assertEquals(Integer.parseInt(row.get("totalCostsForBookableItems").split(",")[index]),
            pc.getTotalCostForBookableItems());
        assertEquals(Integer.parseInt(row.get("totalCostsForBikeTour").split(",")[index]),
            pc.getTotalCostForBikingTour());
      }
    }
  }
}
