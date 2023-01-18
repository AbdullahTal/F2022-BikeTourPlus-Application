package ca.mcgill.ecse.biketourplus.persistence;

import ca.mcgill.ecse.biketourplus.application.BikeTourPlusApplication;
import ca.mcgill.ecse.biketourplus.model.*;

import java.sql.Date;

/**
 * Persistence layer for BikeTourPlus system. Allows for saving and loading of system data.
 *
 * Adapted from ECSE223 Tutorial BTMS program.
 */
public class BikeTourPlusPersistence {

  private static String filename = "btp.data";
  private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.biketourplus");

  public static void setFilename(String filename) {
    BikeTourPlusPersistence.filename = filename;
  }

  public static void save() {
    save(BikeTourPlusApplication.getBikeTourPlus());
  }

  public static void save(BikeTourPlus btp) {
    serializer.serialize(btp, filename);
  }

  public static BikeTourPlus load() {
    var btp = (BikeTourPlus) serializer.deserialize(filename);
    // model cannot be loaded - create empty btp
    if (btp == null) {
      btp = new BikeTourPlus(new Date(0), 0, 0);
    } else {
      btp.reinitialize(); 
    }
    return btp;
  }

}
