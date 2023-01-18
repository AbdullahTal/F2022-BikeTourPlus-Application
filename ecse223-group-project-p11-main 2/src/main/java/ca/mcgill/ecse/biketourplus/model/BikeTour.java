/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.model;
import java.util.*;

// line 47 "../../../../../BikeTourPlusPersistence.ump"
// line 86 "../../../../../BikeTourPlus.ump"
public class BikeTour
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, BikeTour> biketoursById = new HashMap<Integer, BikeTour>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BikeTour Attributes
  private int id;
  private int startWeek;
  private int endWeek;

  //BikeTour Associations
  private List<Participant> participants;
  private Guide guide;
  private Lodge lodge;
  private BikeTourPlus bikeTourPlus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BikeTour(int aId, int aStartWeek, int aEndWeek, Guide aGuide, BikeTourPlus aBikeTourPlus)
  {
    startWeek = aStartWeek;
    endWeek = aEndWeek;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    participants = new ArrayList<Participant>();
    boolean didAddGuide = setGuide(aGuide);
    if (!didAddGuide)
    {
      throw new RuntimeException("Unable to create bikeTour due to guide. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBikeTourPlus = setBikeTourPlus(aBikeTourPlus);
    if (!didAddBikeTourPlus)
    {
      throw new RuntimeException("Unable to create bikeTour due to bikeTourPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    Integer anOldId = getId();
    if (anOldId != null && anOldId.equals(aId)) {
      return true;
    }
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      biketoursById.remove(anOldId);
    }
    biketoursById.put(aId, this);
    return wasSet;
  }

  public boolean setStartWeek(int aStartWeek)
  {
    boolean wasSet = false;
    startWeek = aStartWeek;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndWeek(int aEndWeek)
  {
    boolean wasSet = false;
    endWeek = aEndWeek;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static BikeTour getWithId(int aId)
  {
    return biketoursById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public int getStartWeek()
  {
    return startWeek;
  }

  public int getEndWeek()
  {
    return endWeek;
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
  /* Code from template association_GetOne */
  public Guide getGuide()
  {
    return guide;
  }
  /* Code from template association_GetOne */
  public Lodge getLodge()
  {
    return lodge;
  }

  public boolean hasLodge()
  {
    boolean has = lodge != null;
    return has;
  }
  /* Code from template association_GetOne */
  public BikeTourPlus getBikeTourPlus()
  {
    return bikeTourPlus;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfParticipants()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addParticipant(Participant aParticipant)
  {
    boolean wasAdded = false;
    if (participants.contains(aParticipant)) { return false; }
    BikeTour existingBikeTour = aParticipant.getBikeTour();
    if (existingBikeTour == null)
    {
      aParticipant.setBikeTour(this);
    }
    else if (!this.equals(existingBikeTour))
    {
      existingBikeTour.removeParticipant(aParticipant);
      addParticipant(aParticipant);
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
    if (participants.contains(aParticipant))
    {
      participants.remove(aParticipant);
      aParticipant.setBikeTour(null);
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
  /* Code from template association_SetOneToMany */
  public boolean setGuide(Guide aGuide)
  {
    boolean wasSet = false;
    if (aGuide == null)
    {
      return wasSet;
    }

    Guide existingGuide = guide;
    guide = aGuide;
    if (existingGuide != null && !existingGuide.equals(aGuide))
    {
      existingGuide.removeBikeTour(this);
    }
    guide.addBikeTour(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setLodge(Lodge aLodge)
  {
    boolean wasSet = false;
    Lodge existingLodge = lodge;
    lodge = aLodge;
    if (existingLodge != null && !existingLodge.equals(aLodge))
    {
      existingLodge.removeBikeTour(this);
    }
    if (aLodge != null)
    {
      aLodge.addBikeTour(this);
    }
    wasSet = true;
    return wasSet;
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
      existingBikeTourPlus.removeBikeTour(this);
    }
    bikeTourPlus.addBikeTour(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    biketoursById.remove(getId());
    while( !participants.isEmpty() )
    {
      participants.get(0).setBikeTour(null);
    }
    Guide placeholderGuide = guide;
    this.guide = null;
    if(placeholderGuide != null)
    {
      placeholderGuide.removeBikeTour(this);
    }
    if (lodge != null)
    {
      Lodge placeholderLodge = lodge;
      this.lodge = null;
      placeholderLodge.removeBikeTour(this);
    }
    BikeTourPlus placeholderBikeTourPlus = bikeTourPlus;
    this.bikeTourPlus = null;
    if(placeholderBikeTourPlus != null)
    {
      placeholderBikeTourPlus.removeBikeTour(this);
    }
  }

  // line 49 "../../../../../BikeTourPlusPersistence.ump"
   public static  void reinitializeUniqueId(List<BikeTour> bikeTours){
    biketoursById.clear();
    for (var bikeTour : bikeTours) {
       biketoursById.put(bikeTour.getId(), bikeTour);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "startWeek" + ":" + getStartWeek()+ "," +
            "endWeek" + ":" + getEndWeek()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "guide = "+(getGuide()!=null?Integer.toHexString(System.identityHashCode(getGuide())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "lodge = "+(getLodge()!=null?Integer.toHexString(System.identityHashCode(getLodge())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bikeTourPlus = "+(getBikeTourPlus()!=null?Integer.toHexString(System.identityHashCode(getBikeTourPlus())):"null");
  }
}