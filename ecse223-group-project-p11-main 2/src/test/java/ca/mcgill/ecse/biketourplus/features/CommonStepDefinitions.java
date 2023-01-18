package ca.mcgill.ecse.biketourplus.features;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import io.cucumber.java.After;

import java.io.File;

public class CommonStepDefinitions {
  /**
   * Method used to delete the current bikeSafePlus system instance before the next test. This is
   * effective for all scenerios in all feature files
   */
  @After
  public void tearDown() {
    BikeTourPlusApplication.getBikeTourPlus().delete();
    new File("btp.data").delete(); // delete persistence data
  }
}
