/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.model;
import java.sql.Date;
import java.util.*;

// line 1 "../../../../../BikeTourPlusPersistence.ump"
// line 6 "../../../../../BikeTourPlus.ump"
public class BikeTourPlus
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BikeTourPlus Attributes
  private Date startDate;
  private int nrWeeks;
  private int priceOfGuidePerWeek;

  //BikeTourPlus Associations
  private Manager manager;
  private List<Guide> guides;
  private List<Participant> participants;
  private List<BookedItem> bookedItems;
  private List<Gear> gear;
  private List<Combo> combos;
  private List<ComboItem> comboItems;
  private List<Lodge> lodges;
  private List<BikeTour> bikeTours;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BikeTourPlus(Date aStartDate, int aNrWeeks, int aPriceOfGuidePerWeek)
  {
    startDate = aStartDate;
    nrWeeks = aNrWeeks;
    priceOfGuidePerWeek = aPriceOfGuidePerWeek;
    guides = new ArrayList<Guide>();
    participants = new ArrayList<Participant>();
    bookedItems = new ArrayList<BookedItem>();
    gear = new ArrayList<Gear>();
    combos = new ArrayList<Combo>();
    comboItems = new ArrayList<ComboItem>();
    lodges = new ArrayList<Lodge>();
    bikeTours = new ArrayList<BikeTour>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setNrWeeks(int aNrWeeks)
  {
    boolean wasSet = false;
    nrWeeks = aNrWeeks;
    wasSet = true;
    return wasSet;
  }

  public boolean setPriceOfGuidePerWeek(int aPriceOfGuidePerWeek)
  {
    boolean wasSet = false;
    priceOfGuidePerWeek = aPriceOfGuidePerWeek;
    wasSet = true;
    return wasSet;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public int getNrWeeks()
  {
    return nrWeeks;
  }

  public int getPriceOfGuidePerWeek()
  {
    return priceOfGuidePerWeek;
  }
  /* Code from template association_GetOne */
  public Manager getManager()
  {
    return manager;
  }

  public boolean hasManager()
  {
    boolean has = manager != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Guide getGuide(int index)
  {
    Guide aGuide = guides.get(index);
    return aGuide;
  }

  public List<Guide> getGuides()
  {
    List<Guide> newGuides = Collections.unmodifiableList(guides);
    return newGuides;
  }

  public int numberOfGuides()
  {
    int number = guides.size();
    return number;
  }

  public boolean hasGuides()
  {
    boolean has = guides.size() > 0;
    return has;
  }

  public int indexOfGuide(Guide aGuide)
  {
    int index = guides.indexOf(aGuide);
    return index;
  }
  /* Code from template association_GetMany */
  public Participant getParticipant(int index)
  {
    Participant aParticipant = participants.get(index);
    return aParticipant;
  }

  public List<Participant> getParticipants()
  {
    List<Participant> newParticipants = Collections.unmodifiableList(participants);
    return newParticipants;
  }

  public int numberOfParticipants()
  {
    int number = participants.size();
    return number;
  }

  public boolean hasParticipants()
  {
    boolean has = participants.size() > 0;
    return has;
  }

  public int indexOfParticipant(Participant aParticipant)
  {
    int index = participants.indexOf(aParticipant);
    return index;
  }
  /* Code from template association_GetMany */
  public BookedItem getBookedItem(int index)
  {
    BookedItem aBookedItem = bookedItems.get(index);
    return aBookedItem;
  }

  public List<BookedItem> getBookedItems()
  {
    List<BookedItem> newBookedItems = Collections.unmodifiableList(bookedItems);
    return newBookedItems;
  }

  public int numberOfBookedItems()
  {
    int number = bookedItems.size();
    return number;
  }

  public boolean hasBookedItems()
  {
    boolean has = bookedItems.size() > 0;
    return has;
  }

  public int indexOfBookedItem(BookedItem aBookedItem)
  {
    int index = bookedItems.indexOf(aBookedItem);
    return index;
  }
  /* Code from template association_GetMany */
  public Gear getGear(int index)
  {
    Gear aGear = gear.get(index);
    return aGear;
  }

  public List<Gear> getGear()
  {
    List<Gear> newGear = Collections.unmodifiableList(gear);
    return newGear;
  }

  public int numberOfGear()
  {
    int number = gear.size();
    return number;
  }

  public boolean hasGear()
  {
    boolean has = gear.size() > 0;
    return has;
  }

  public int indexOfGear(Gear aGear)
  {
    int index = gear.indexOf(aGear);
    return index;
  }
  /* Code from template association_GetMany */
  public Combo getCombo(int index)
  {
    Combo aCombo = combos.get(index);
    return aCombo;
  }

  public List<Combo> getCombos()
  {
    List<Combo> newCombos = Collections.unmodifiableList(combos);
    return newCombos;
  }

  public int numberOfCombos()
  {
    int number = combos.size();
    return number;
  }

  public boolean hasCombos()
  {
    boolean has = combos.size() > 0;
    return has;
  }

  public int indexOfCombo(Combo aCombo)
  {
    int index = combos.indexOf(aCombo);
    return index;
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
  /* Code from template association_GetMany */
  public Lodge getLodge(int index)
  {
    Lodge aLodge = lodges.get(index);
    return aLodge;
  }

  public List<Lodge> getLodges()
  {
    List<Lodge> newLodges = Collections.unmodifiableList(lodges);
    return newLodges;
  }

  public int numberOfLodges()
  {
    int number = lodges.size();
    return number;
  }

  public boolean hasLodges()
  {
    boolean has = lodges.size() > 0;
    return has;
  }

  public int indexOfLodge(Lodge aLodge)
  {
    int index = lodges.indexOf(aLodge);
    return index;
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
  /* Code from template association_SetOptionalOneToOne */
  public boolean setManager(Manager aNewManager)
  {
    boolean wasSet = false;
    if (manager != null && !manager.equals(aNewManager) && equals(manager.getBikeTourPlus()))
    {
      //Unable to setManager, as existing manager would become an orphan
      return wasSet;
    }

    manager = aNewManager;
    BikeTourPlus anOldBikeTourPlus = aNewManager != null ? aNewManager.getBikeTourPlus() : null;

    if (!this.equals(anOldBikeTourPlus))
    {
      if (anOldBikeTourPlus != null)
      {
        anOldBikeTourPlus.manager = null;
      }
      if (manager != null)
      {
        manager.setBikeTourPlus(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGuides()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Guide addGuide(String aEmail, String aPassword, String aName, String aEmergencyContact)
  {
    return new Guide(aEmail, aPassword, aName, aEmergencyContact, this);
  }

  public boolean addGuide(Guide aGuide)
  {
    boolean wasAdded = false;
    if (guides.contains(aGuide)) { return false; }
    BikeTourPlus existingBikeTourPlus = aGuide.getBikeTourPlus();
    boolean isNewBikeTourPlus = existingBikeTourPlus != null && !this.equals(existingBikeTourPlus);
    if (isNewBikeTourPlus)
    {
      aGuide.setBikeTourPlus(this);
    }
    else
    {
      guides.add(aGuide);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGuide(Guide aGuide)
  {
    boolean wasRemoved = false;
    //Unable to remove aGuide, as it must always have a bikeTourPlus
    if (!this.equals(aGuide.getBikeTourPlus()))
    {
      guides.remove(aGuide);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGuideAt(Guide aGuide, int index)
  {  
    boolean wasAdded = false;
    if(addGuide(aGuide))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuides()) { index = numberOfGuides() - 1; }
      guides.remove(aGuide);
      guides.add(index, aGuide);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGuideAt(Guide aGuide, int index)
  {
    boolean wasAdded = false;
    if(guides.contains(aGuide))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuides()) { index = numberOfGuides() - 1; }
      guides.remove(aGuide);
      guides.add(index, aGuide);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGuideAt(aGuide, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfParticipants()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Participant addParticipant(String aEmail, String aPassword, String aName, String aEmergencyContact, int aNrWeeks, int aWeekAvailableFrom, int aWeekAvailableUntil, boolean aLodgeRequired, String aAuthorizationCode, int aRefundedPercentageAmount)
  {
    return new Participant(aEmail, aPassword, aName, aEmergencyContact, aNrWeeks, aWeekAvailableFrom, aWeekAvailableUntil, aLodgeRequired, aAuthorizationCode, aRefundedPercentageAmount, this);
  }

  public boolean addParticipant(Participant aParticipant)
  {
    boolean wasAdded = false;
    if (participants.contains(aParticipant)) { return false; }
    BikeTourPlus existingBikeTourPlus = aParticipant.getBikeTourPlus();
    boolean isNewBikeTourPlus = existingBikeTourPlus != null && !this.equals(existingBikeTourPlus);
    if (isNewBikeTourPlus)
    {
      aParticipant.setBikeTourPlus(this);
    }
    else
    {
      participants.add(aParticipant);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeParticipant(Participant aParticipant)
  {
    boolean wasRemoved = false;
    //Unable to remove aParticipant, as it must always have a bikeTourPlus
    if (!this.equals(aParticipant.getBikeTourPlus()))
    {
      participants.remove(aParticipant);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addParticipantAt(Participant aParticipant, int index)
  {  
    boolean wasAdded = false;
    if(addParticipant(aParticipant))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfParticipants()) { index = numberOfParticipants() - 1; }
      participants.remove(aParticipant);
      participants.add(index, aParticipant);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveParticipantAt(Participant aParticipant, int index)
  {
    boolean wasAdded = false;
    if(participants.contains(aParticipant))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfParticipants()) { index = numberOfParticipants() - 1; }
      participants.remove(aParticipant);
      participants.add(index, aParticipant);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addParticipantAt(aParticipant, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBookedItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BookedItem addBookedItem(int aQuantity, Participant aParticipant, BookableItem aItem)
  {
    return new BookedItem(aQuantity, this, aParticipant, aItem);
  }

  public boolean addBookedItem(BookedItem aBookedItem)
  {
    boolean wasAdded = false;
    if (bookedItems.contains(aBookedItem)) { return false; }
    BikeTourPlus existingBikeTourPlus = aBookedItem.getBikeTourPlus();
    boolean isNewBikeTourPlus = existingBikeTourPlus != null && !this.equals(existingBikeTourPlus);
    if (isNewBikeTourPlus)
    {
      aBookedItem.setBikeTourPlus(this);
    }
    else
    {
      bookedItems.add(aBookedItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBookedItem(BookedItem aBookedItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aBookedItem, as it must always have a bikeTourPlus
    if (!this.equals(aBookedItem.getBikeTourPlus()))
    {
      bookedItems.remove(aBookedItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBookedItemAt(BookedItem aBookedItem, int index)
  {  
    boolean wasAdded = false;
    if(addBookedItem(aBookedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookedItems()) { index = numberOfBookedItems() - 1; }
      bookedItems.remove(aBookedItem);
      bookedItems.add(index, aBookedItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBookedItemAt(BookedItem aBookedItem, int index)
  {
    boolean wasAdded = false;
    if(bookedItems.contains(aBookedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookedItems()) { index = numberOfBookedItems() - 1; }
      bookedItems.remove(aBookedItem);
      bookedItems.add(index, aBookedItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBookedItemAt(aBookedItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGear()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Gear addGear(String aName, int aPricePerWeek)
  {
    return new Gear(aName, aPricePerWeek, this);
  }

  public boolean addGear(Gear aGear)
  {
    boolean wasAdded = false;
    if (gear.contains(aGear)) { return false; }
    BikeTourPlus existingBikeTourPlus = aGear.getBikeTourPlus();
    boolean isNewBikeTourPlus = existingBikeTourPlus != null && !this.equals(existingBikeTourPlus);
    if (isNewBikeTourPlus)
    {
      aGear.setBikeTourPlus(this);
    }
    else
    {
      gear.add(aGear);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGear(Gear aGear)
  {
    boolean wasRemoved = false;
    //Unable to remove aGear, as it must always have a bikeTourPlus
    if (!this.equals(aGear.getBikeTourPlus()))
    {
      gear.remove(aGear);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGearAt(Gear aGear, int index)
  {  
    boolean wasAdded = false;
    if(addGear(aGear))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGear()) { index = numberOfGear() - 1; }
      gear.remove(aGear);
      gear.add(index, aGear);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGearAt(Gear aGear, int index)
  {
    boolean wasAdded = false;
    if(gear.contains(aGear))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGear()) { index = numberOfGear() - 1; }
      gear.remove(aGear);
      gear.add(index, aGear);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGearAt(aGear, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCombos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Combo addCombo(String aName, int aDiscount)
  {
    return new Combo(aName, aDiscount, this);
  }

  public boolean addCombo(Combo aCombo)
  {
    boolean wasAdded = false;
    if (combos.contains(aCombo)) { return false; }
    BikeTourPlus existingBikeTourPlus = aCombo.getBikeTourPlus();
    boolean isNewBikeTourPlus = existingBikeTourPlus != null && !this.equals(existingBikeTourPlus);
    if (isNewBikeTourPlus)
    {
      aCombo.setBikeTourPlus(this);
    }
    else
    {
      combos.add(aCombo);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCombo(Combo aCombo)
  {
    boolean wasRemoved = false;
    //Unable to remove aCombo, as it must always have a bikeTourPlus
    if (!this.equals(aCombo.getBikeTourPlus()))
    {
      combos.remove(aCombo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addComboAt(Combo aCombo, int index)
  {  
    boolean wasAdded = false;
    if(addCombo(aCombo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCombos()) { index = numberOfCombos() - 1; }
      combos.remove(aCombo);
      combos.add(index, aCombo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveComboAt(Combo aCombo, int index)
  {
    boolean wasAdded = false;
    if(combos.contains(aCombo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCombos()) { index = numberOfCombos() - 1; }
      combos.remove(aCombo);
      combos.add(index, aCombo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addComboAt(aCombo, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfComboItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ComboItem addComboItem(int aQuantity, Combo aCombo, Gear aGear)
  {
    return new ComboItem(aQuantity, this, aCombo, aGear);
  }

  public boolean addComboItem(ComboItem aComboItem)
  {
    boolean wasAdded = false;
    if (comboItems.contains(aComboItem)) { return false; }
    BikeTourPlus existingBikeTourPlus = aComboItem.getBikeTourPlus();
    boolean isNewBikeTourPlus = existingBikeTourPlus != null && !this.equals(existingBikeTourPlus);
    if (isNewBikeTourPlus)
    {
      aComboItem.setBikeTourPlus(this);
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
    //Unable to remove aComboItem, as it must always have a bikeTourPlus
    if (!this.equals(aComboItem.getBikeTourPlus()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLodges()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Lodge addLodge(String aName, String aAddress, Lodge.LodgeRating aRating)
  {
    return new Lodge(aName, aAddress, aRating, this);
  }

  public boolean addLodge(Lodge aLodge)
  {
    boolean wasAdded = false;
    if (lodges.contains(aLodge)) { return false; }
    BikeTourPlus existingBikeTourPlus = aLodge.getBikeTourPlus();
    boolean isNewBikeTourPlus = existingBikeTourPlus != null && !this.equals(existingBikeTourPlus);
    if (isNewBikeTourPlus)
    {
      aLodge.setBikeTourPlus(this);
    }
    else
    {
      lodges.add(aLodge);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLodge(Lodge aLodge)
  {
    boolean wasRemoved = false;
    //Unable to remove aLodge, as it must always have a bikeTourPlus
    if (!this.equals(aLodge.getBikeTourPlus()))
    {
      lodges.remove(aLodge);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLodgeAt(Lodge aLodge, int index)
  {  
    boolean wasAdded = false;
    if(addLodge(aLodge))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLodges()) { index = numberOfLodges() - 1; }
      lodges.remove(aLodge);
      lodges.add(index, aLodge);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLodgeAt(Lodge aLodge, int index)
  {
    boolean wasAdded = false;
    if(lodges.contains(aLodge))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLodges()) { index = numberOfLodges() - 1; }
      lodges.remove(aLodge);
      lodges.add(index, aLodge);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLodgeAt(aLodge, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBikeTours()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BikeTour addBikeTour(int aId, int aStartWeek, int aEndWeek, Guide aGuide)
  {
    return new BikeTour(aId, aStartWeek, aEndWeek, aGuide, this);
  }

  public boolean addBikeTour(BikeTour aBikeTour)
  {
    boolean wasAdded = false;
    if (bikeTours.contains(aBikeTour)) { return false; }
    BikeTourPlus existingBikeTourPlus = aBikeTour.getBikeTourPlus();
    boolean isNewBikeTourPlus = existingBikeTourPlus != null && !this.equals(existingBikeTourPlus);
    if (isNewBikeTourPlus)
    {
      aBikeTour.setBikeTourPlus(this);
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
    //Unable to remove aBikeTour, as it must always have a bikeTourPlus
    if (!this.equals(aBikeTour.getBikeTourPlus()))
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
    Manager existingManager = manager;
    manager = null;
    if (existingManager != null)
    {
      existingManager.delete();
      existingManager.setBikeTourPlus(null);
    }
    while (guides.size() > 0)
    {
      Guide aGuide = guides.get(guides.size() - 1);
      aGuide.delete();
      guides.remove(aGuide);
    }
    
    while (participants.size() > 0)
    {
      Participant aParticipant = participants.get(participants.size() - 1);
      aParticipant.delete();
      participants.remove(aParticipant);
    }
    
    while (bookedItems.size() > 0)
    {
      BookedItem aBookedItem = bookedItems.get(bookedItems.size() - 1);
      aBookedItem.delete();
      bookedItems.remove(aBookedItem);
    }
    
    while (gear.size() > 0)
    {
      Gear aGear = gear.get(gear.size() - 1);
      aGear.delete();
      gear.remove(aGear);
    }
    
    while (combos.size() > 0)
    {
      Combo aCombo = combos.get(combos.size() - 1);
      aCombo.delete();
      combos.remove(aCombo);
    }
    
    while (comboItems.size() > 0)
    {
      ComboItem aComboItem = comboItems.get(comboItems.size() - 1);
      aComboItem.delete();
      comboItems.remove(aComboItem);
    }
    
    while (lodges.size() > 0)
    {
      Lodge aLodge = lodges.get(lodges.size() - 1);
      aLodge.delete();
      lodges.remove(aLodge);
    }
    
    while (bikeTours.size() > 0)
    {
      BikeTour aBikeTour = bikeTours.get(bikeTours.size() - 1);
      aBikeTour.delete();
      bikeTours.remove(aBikeTour);
    }
    
  }

  // line 4 "../../../../../BikeTourPlusPersistence.ump"
   public void reinitialize(){
    List<User> users = new ArrayList<User>();
    users.add(getManager());
    users.addAll(getGuides());
    users.addAll(getParticipants());
    User.reinitializeUniqueEmail(users);

    List<BookableItem> items = new ArrayList<BookableItem>();
    items.addAll(getGear());
    items.addAll(getCombos());
    BookableItem.reinitializeUniqueName(items);

    Lodge.reinitializeUniqueName(getLodges());
    BikeTour.reinitializeUniqueId(getBikeTours());
  }


  public String toString()
  {
    return super.toString() + "["+
            "nrWeeks" + ":" + getNrWeeks()+ "," +
            "priceOfGuidePerWeek" + ":" + getPriceOfGuidePerWeek()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "manager = "+(getManager()!=null?Integer.toHexString(System.identityHashCode(getManager())):"null");
  }
}