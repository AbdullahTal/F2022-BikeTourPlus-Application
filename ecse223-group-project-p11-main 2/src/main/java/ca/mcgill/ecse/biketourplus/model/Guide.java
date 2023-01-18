/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.model;
import java.util.*;

// line 38 "../../../../../BikeTourPlus.ump"
public class Guide extends NamedUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Guide Associations
  private BikeTourPlus bikeTourPlus;
  private List<BikeTour> bikeTours;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Guide(String aEmail, String aPassword, String aName, String aEmergencyContact, BikeTourPlus aBikeTourPlus)
  {
    super(aEmail, aPassword, aName, aEmergencyContact);
    boolean didAddBikeTourPlus = setBikeTourPlus(aBikeTourPlus);
    if (!didAddBikeTourPlus)
    {
      throw new RuntimeException("Unable to create guide due to bikeTourPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    bikeTours = new ArrayList<BikeTour>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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
      existingBikeTourPlus.removeGuide(this);
    }
    bikeTourPlus.addGuide(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBikeTours()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BikeTour addBikeTour(int aId, int aStartWeek, int aEndWeek, BikeTourPlus aBikeTourPlus)
  {
    return new BikeTour(aId, aStartWeek, aEndWeek, this, aBikeTourPlus);
  }

  public boolean addBikeTour(BikeTour aBikeTour)
  {
    boolean wasAdded = false;
    if (bikeTours.contains(aBikeTour)) { return false; }
    Guide existingGuide = aBikeTour.getGuide();
    boolean isNewGuide = existingGuide != null && !this.equals(existingGuide);
    if (isNewGuide)
    {
      aBikeTour.setGuide(this);
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
    //Unable to remove aBikeTour, as it must always have a guide
    if (!this.equals(aBikeTour.getGuide()))
    {
      bikeTours.remove(aBikeTour);
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
    BikeTourPlus placeholderBikeTourPlus = bikeTourPlus;
    this.bikeTourPlus = null;
    if(placeholderBikeTourPlus != null)
    {
      placeholderBikeTourPlus.removeGuide(this);
    }
    for(int i=bikeTours.size(); i > 0; i--)
    {
      BikeTour aBikeTour = bikeTours.get(i - 1);
      aBikeTour.delete();
    }
    super.delete();
  }

}