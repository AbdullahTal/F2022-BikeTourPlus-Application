/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.model;
import java.util.*;

// line 27 "../../../../../BikeTourPlus.ump"
public class Manager extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  private BikeTourPlus bikeTourPlus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(String aEmail, String aPassword, BikeTourPlus aBikeTourPlus)
  {
    super(aEmail, aPassword);
    boolean didAddBikeTourPlus = setBikeTourPlus(aBikeTourPlus);
    if (!didAddBikeTourPlus)
    {
      throw new RuntimeException("Unable to create manager due to bikeTourPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public BikeTourPlus getBikeTourPlus()
  {
    return bikeTourPlus;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setBikeTourPlus(BikeTourPlus aNewBikeTourPlus)
  {
    boolean wasSet = false;
    if (aNewBikeTourPlus == null)
    {
      //Unable to setBikeTourPlus to null, as manager must always be associated to a bikeTourPlus
      return wasSet;
    }
    
    Manager existingManager = aNewBikeTourPlus.getManager();
    if (existingManager != null && !equals(existingManager))
    {
      //Unable to setBikeTourPlus, the current bikeTourPlus already has a manager, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    BikeTourPlus anOldBikeTourPlus = bikeTourPlus;
    bikeTourPlus = aNewBikeTourPlus;
    bikeTourPlus.setManager(this);

    if (anOldBikeTourPlus != null)
    {
      anOldBikeTourPlus.setManager(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    BikeTourPlus existingBikeTourPlus = bikeTourPlus;
    bikeTourPlus = null;
    if (existingBikeTourPlus != null)
    {
      existingBikeTourPlus.setManager(null);
    }
    super.delete();
  }

}