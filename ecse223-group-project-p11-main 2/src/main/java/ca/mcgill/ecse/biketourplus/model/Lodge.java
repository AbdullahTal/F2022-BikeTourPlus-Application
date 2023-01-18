/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.model;
import java.util.*;

// line 38 "../../../../../BikeTourPlusPersistence.ump"
// line 79 "../../../../../BikeTourPlus.ump"
public class Lodge
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum LodgeRating { OneStar, TwoStars, ThreeStars, FourStars, FiveStars }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Lodge> lodgesByName = new HashMap<String, Lodge>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Lodge Attributes
  private String name;
  private String address;
  private LodgeRating rating;

  //Lodge Associations
  private BikeTourPlus bikeTourPlus;
  private List<BikeTour> bikeTours;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Lodge(String aName, String aAddress, LodgeRating aRating, BikeTourPlus aBikeTourPlus)
  {
    address = aAddress;
    rating = aRating;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddBikeTourPlus = setBikeTourPlus(aBikeTourPlus);
    if (!didAddBikeTourPlus)
    {
      throw new RuntimeException("Unable to create lodge due to bikeTourPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    bikeTours = new ArrayList<BikeTour>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (anOldName != null && anOldName.equals(aName)) {
      return true;
    }
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      lodgesByName.remove(anOldName);
    }
    lodgesByName.put(aName, this);
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setRating(LodgeRating aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static Lodge getWithName(String aName)
  {
    return lodgesByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public String getAddress()
  {
    return address;
  }

  public LodgeRating getRating()
  {
    return rating;
  }
  /* Code from template association_GetOne */
  public BikeTourPlus getBikeTourPlus()
  {
    return bikeTourPlus;
  }
  /* Code from template association_GetMany */
  public BikeTour getBikeTour(int index)
  {
    BikeTour aBikeTour = bikeTours.get(index);
    return aBikeTour;
  }

  public List<BikeTour> getBikeTours()
  {
    List<BikeTour> newBikeTours = Collections.unmodifiableList(bikeTours);
    return newBikeTours;
  }

  public int numberOfBikeTours()
  {
    int number = bikeTours.size();
    return number;
  }

  public boolean hasBikeTours()
  {
    boolean has = bikeTours.size() > 0;
    return has;
  }

  public int indexOfBikeTour(BikeTour aBikeTour)
  {
    int index = bikeTours.indexOf(aBikeTour);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBikeTourPlus(BikeTourPlus aBikeTourPlus)
  {
    boolean wasSet = false;
    if (aBikeTourPlus == null)
    {
      return wasSet;
    }

    BikeTourPlus existingBikeTourPlus = bikeTourPlus;
    bikeTourPlus = aBikeTourPlus;
    if (existingBikeTourPlus != null && !existingBikeTourPlus.equals(aBikeTourPlus))
    {
      existingBikeTourPlus.removeLodge(this);
    }
    bikeTourPlus.addLodge(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBikeTours()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addBikeTour(BikeTour aBikeTour)
  {
    boolean wasAdded = false;
    if (bikeTours.contains(aBikeTour)) { return false; }
    Lodge existingLodge = aBikeTour.getLodge();
    if (existingLodge == null)
    {
      aBikeTour.setLodge(this);
    }
    else if (!this.equals(existingLodge))
    {
      existingLodge.removeBikeTour(aBikeTour);
      addBikeTour(aBikeTour);
    }
    else
    {
      bikeTours.add(aBikeTour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBikeTour(BikeTour aBikeTour)
  {
    boolean wasRemoved = false;
    if (bikeTours.contains(aBikeTour))
    {
      bikeTours.remove(aBikeTour);
      aBikeTour.setLodge(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBikeTourAt(BikeTour aBikeTour, int index)
  {  
    boolean wasAdded = false;
    if(addBikeTour(aBikeTour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBikeTours()) { index = numberOfBikeTours() - 1; }
      bikeTours.remove(aBikeTour);
      bikeTours.add(index, aBikeTour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBikeTourAt(BikeTour aBikeTour, int index)
  {
    boolean wasAdded = false;
    if(bikeTours.contains(aBikeTour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBikeTours()) { index = numberOfBikeTours() - 1; }
      bikeTours.remove(aBikeTour);
      bikeTours.add(index, aBikeTour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBikeTourAt(aBikeTour, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    lodgesByName.remove(getName());
    BikeTourPlus placeholderBikeTourPlus = bikeTourPlus;
    this.bikeTourPlus = null;
    if(placeholderBikeTourPlus != null)
    {
      placeholderBikeTourPlus.removeLodge(this);
    }
    while( !bikeTours.isEmpty() )
    {
      bikeTours.get(0).setLodge(null);
    }
  }

  // line 40 "../../../../../BikeTourPlusPersistence.ump"
   public static  void reinitializeUniqueName(List<Lodge> lodges){
    lodgesByName.clear();
    for (var lodge : lodges) {
       lodgesByName.put(lodge.getName(), lodge);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "address" + ":" + getAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "bikeTourPlus = "+(getBikeTourPlus()!=null?Integer.toHexString(System.identityHashCode(getBikeTourPlus())):"null");
  }
}