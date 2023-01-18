package ca.mcgill.ecse.biketourplus.application;

import ca.mcgill.ecse.biketourplus.javafx.fxml.BikeTourPlusFxmlView;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import javafx.application.Application;

public class BikeTourPlusApplication {

  private static BikeTourPlus bikeTourPlus;
  public static boolean DARK_MODE = false;  // initial style

  public static void main(String[] args) {
    //start ui 
    Application.launch(BikeTourPlusFxmlView.class, args);
  }

  public static BikeTourPlus getBikeTourPlus() {
    if (bikeTourPlus == null) {
      // load btp from persistence layer
      bikeTourPlus = BikeTourPlusPersistence.load();
    }
    return bikeTourPlus;
  }

}
