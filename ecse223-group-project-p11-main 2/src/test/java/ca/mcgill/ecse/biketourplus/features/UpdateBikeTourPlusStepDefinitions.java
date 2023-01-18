package ca.mcgill.ecse.biketourplus.features;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.controller.BikeTourPlusFeatureSet2Controller;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.List;
import java.util.Map;


public class UpdateBikeTourPlusStepDefinitions {

  private String error;
  private BikeTourPlus btp;


  /**
   * This function sets up an instance of BikeTourPlus with values specified in the feature file
   * 
   * @param dataTable the data table from the feature file
   * @author LukeBebee
   */
  @Given("the following BikeTourPlus system exists: \\(p8)")
  public void the_following_bike_tour_plus_system_exists_p8(
      io.cucumber.datatable.DataTable dataTable) {
    // clear errors
    error = "";

    // create instance of BikeTourPlus
    btp = BikeTourPlusApplication.getBikeTourPlus();

    // initialize variables that will be used for attributes (null s.t. an error is thrown if the
    // feature file is not giving values)
    String startDateString = null;
    String nrWeeksString = null;
    String priceOfGuidePerWeekString = null;

    // making a list of maps from the data table from the feature file
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) { // this will only have one iteration as the feature file
                                           // only gives one row
      startDateString = row.get("startDate");
      nrWeeksString = row.get("nrWeeks");
      priceOfGuidePerWeekString = row.get("priceOfGuidePerWeek");
    }

    // set attributes
    // startDate
    Date startDate = Date.valueOf(startDateString);
    btp.setStartDate(startDate);

    // nrWeeks
    int nrWeeks = Integer.parseInt(nrWeeksString);
    btp.setNrWeeks(nrWeeks);

    // priceOfGuidePerWeek
    int priceOfGuidePerWeek = Integer.parseInt(priceOfGuidePerWeekString);
    btp.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
  }

  /**
   * This function updates the instance of BikeTourPlus with variables from the feature file
   * 
   * @param string string from data table in feature file (Start date)
   * @param string2 string from data table in feature file (Number of weeks)
   * @param string3 string from data table in feature file (Price of guide per week)
   * @author LukeBebee
   */
  @When("the manager attempts to update the BikeTourPlus information to start date {string}, number of weeks {string}, and price of guide per week {string} \\(p8)")
  public void the_manager_attempts_to_update_the_bike_tour_plus_information_to_start_date_number_of_weeks_and_price_of_guide_per_week_p8(
      String string, String string2, String string3) {

    // strings into proper types to call controller
    Date startDate = Date.valueOf(string);
    int nrWeeks = Integer.parseInt(string2);
    int price = Integer.parseInt(string3);

    // call controller
    // (this is called on controller, not instance, but I am not sure how it knows that there is
    // only one instance of BikeTourPlus, maybe ask TA)
    // save error
    error = BikeTourPlusFeatureSet2Controller.updateBikeTourPlus(startDate, nrWeeks, price);
  }

  /**
   * This funciton checks whether the updated variables of the BikeTourPlus instance are correct
   * 
   * @param string string from data table in feature file (Start date)
   * @param string2 string from data table in feature file (Number of weeks)
   * @param string3 string from data table in feature file (Price of guide per week)
   * @author LukeBebee
   * @author RalphChoucha
   * @author Morava83
   */
  @Then("the BikeTourPlus information shall be start date {string}, number of weeks {string}, and price of guide per week {string} \\(p8)")
  public void the_bike_tour_plus_information_shall_be_start_date_number_of_weeks_and_price_of_guide_per_week_p8(
      String string, String string2, String string3) {

    // get attributes of instance
    Date instanceStartDate = btp.getStartDate();
    int instanceNrWeeks = btp.getNrWeeks();
    int instancePrice = btp.getPriceOfGuidePerWeek();

    // get desired attributes
    Date startDate = Date.valueOf(string);
    int nrWeeks = Integer.parseInt(string2);
    int price = Integer.parseInt(string3);

    // assert equalities and check for errors
    assertEquals(startDate, instanceStartDate);
    assertEquals(nrWeeks, instanceNrWeeks);
    assertEquals(price, instancePrice);
  }

  /**
   * Check if error risen by controller is the desired error
   * 
   * @param string Desired error message to be raised (from feature file)
   * @author LukeBebee
   * @author RalphChoucha
   * @author Morava83
   */
  @Then("the system shall raise the error {string} \\(p8)")
  public void the_system_shall_raise_the_error_p8(String string) {
    assertEquals(string, error);
  }
}
