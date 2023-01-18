/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.model;
import java.util.*;

// line 63 "../../../../../BikeTourPlus.ump"
public class Gear extends BookableItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gear Attributes
  private int pricePerWeek;

  //Gear Associations
  private BikeTourPlus bikeTourPlus;
  private List<ComboItem> comboItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gear(String aName, int aPricePerWeek, BikeTourPlus aBikeTourPlus)
  {
    super(aName);
    pricePerWeek = aPricePerWeek;
    boolean didAddBikeTourPlus = setBikeTourPlus(aBikeTourPlus);
    if (!didAddBikeTourPlus)
    {
      throw new RuntimeException("Unable to create gear due to bikeTourPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    comboItems = new ArrayList<ComboItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPricePerWeek(int aPricePerWeek)
  {
    boolean wasSet = false;
    pricePerWeek = aPricePerWeek;
    wasSet = true;
    return wasSet;
  }

  public int getPricePerWeek()
  {
    return pricePerWeek;
  }
  /* Code from template association_GetOne */
  public BikeTourPlus getBikeTourPlus()
  {
    return bikeTourPlus;
  }
  /* Code from template association_GetMany */
  public ComboItem getComboItem(int index)
  {
    ComboItem aComboItem = comboItems.get(index);
    return aComboItem;
  }

  public List<ComboItem> getComboItems()
  {
    List<ComboItem> newComboItems = Collections.unmodifiableList(comboItems);
    return newComboItems;
  }

  public int numberOfComboItems()
  {
    int number = comboItems.size();
    return number;
  }

  public boolean hasComboItems()
  {
    boolean has = comboItems.size() > 0;
    return has;
  }

  public int indexOfComboItem(ComboItem aComboItem)
  {
    int index = comboItems.indexOf(aComboItem);
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
      existingBikeTourPlus.removeGear(this);
    }
    bikeTourPlus.addGear(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfComboItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ComboItem addComboItem(int aQuantity, BikeTourPlus aBikeTourPlus, Combo aCombo)
  {
    return new ComboItem(aQuantity, aBikeTourPlus, aCombo, this);
  }

  public boolean addComboItem(ComboItem aComboItem)
  {
    boolean wasAdded = false;
    if (comboItems.contains(aComboItem)) { return false; }
    Gear existingGear = aComboItem.getGear();
    boolean isNewGear = existingGear != null && !this.equals(existingGear);
    if (isNewGear)
    {
      aComboItem.setGear(this);
    }
    else
    {
      comboItems.add(aComboItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeComboItem(ComboItem aComboItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aComboItem, as it must always have a gear
    if (!this.equals(aComboItem.getGear()))
    {
      comboItems.remove(aComboItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addComboItemAt(ComboItem aComboItem, int index)
  {  
    boolean wasAdded = false;
    if(addComboItem(aComboItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfComboItems()) { index = numberOfComboItems() - 1; }
      comboItems.remove(aComboItem);
      comboItems.add(index, aComboItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveComboItemAt(ComboItem aComboItem, int index)
  {
    boolean wasAdded = false;
    if(comboItems.contains(aComboItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfComboItems()) { index = numberOfComboItems() - 1; }
      comboItems.remove(aComboItem);
      comboItems.add(index, aComboItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addComboItemAt(aComboItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    BikeTourPlus placeholderBikeTourPlus = bikeTourPlus;
    this.bikeTourPlus = null;
    if(placeholderBikeTourPlus != null)
    {
      placeholderBikeTourPlus.removeGear(this);
    }
    for(int i=comboItems.size(); i > 0; i--)
    {
      ComboItem aComboItem = comboItems.get(i - 1);
      aComboItem.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "pricePerWeek" + ":" + getPricePerWeek()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "bikeTourPlus = "+(getBikeTourPlus()!=null?Integer.toHexString(System.identityHashCode(getBikeTourPlus())):"null");
  }
}