/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;
import java.util.*;

// line 3 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOBikeTour
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBikeTour Attributes
  private int id;
  private int startWeek;
  private int endWeek;
  private String guideEmail;
  private String guideName;
  private int totalCostForGuide;

  //TOBikeTour Associations
  private List<TOParticipantCost> participantCosts;

  //Helper Variables
  private boolean canSetParticipantCosts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBikeTour(int aId, int aStartWeek, int aEndWeek, String aGuideEmail, String aGuideName, int aTotalCostForGuide, TOParticipantCost... allParticipantCosts)
  {
    id = aId;
    startWeek = aStartWeek;
    endWeek = aEndWeek;
    guideEmail = aGuideEmail;
    guideName = aGuideName;
    totalCostForGuide = aTotalCostForGuide;
    canSetParticipantCosts = true;
    participantCosts = new ArrayList<TOParticipantCost>();
    boolean didAddParticipantCosts = setParticipantCosts(allParticipantCosts);
    if (!didAddParticipantCosts)
    {
      throw new RuntimeException("Unable to create TOBikeTour, must not have duplicate participantCosts. See http://manual.umple.org?RE001ViolationofImmutability.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getId()
  {
    return id;
  }

  public int getStartWeek()
  {
    return startWeek;
  }

  public int getEndWeek()
  {
    return endWeek;
  }

  public String getGuideEmail()
  {
    return guideEmail;
  }

  public String getGuideName()
  {
    return guideName;
  }

  public int getTotalCostForGuide()
  {
    return totalCostForGuide;
  }
  /* Code from template association_GetMany */
  public TOParticipantCost getParticipantCost(int index)
  {
    TOParticipantCost aParticipantCost = participantCosts.get(index);
    return aParticipantCost;
  }

  public List<TOParticipantCost> getParticipantCosts()
  {
    List<TOParticipantCost> newParticipantCosts = Collections.unmodifiableList(participantCosts);
    return newParticipantCosts;
  }

  public int numberOfParticipantCosts()
  {
    int number = participantCosts.size();
    return number;
  }

  public boolean hasParticipantCosts()
  {
    boolean has = participantCosts.size() > 0;
    return has;
  }

  public int indexOfParticipantCost(TOParticipantCost aParticipantCost)
  {
    int index = participantCosts.indexOf(aParticipantCost);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfParticipantCosts()
  {
    return 0;
  }
  /* Code from template association_SetUnidirectionalMany */
  private boolean setParticipantCosts(TOParticipantCost... newParticipantCosts)
  {
    boolean wasSet = false;
    if (!canSetParticipantCosts) { return false; }
    canSetParticipantCosts = false;
    ArrayList<TOParticipantCost> verifiedParticipantCosts = new ArrayList<TOParticipantCost>();
    for (TOParticipantCost aParticipantCost : newParticipantCosts)
    {
      if (verifiedParticipantCosts.contains(aParticipantCost))
      {
        continue;
      }
      verifiedParticipantCosts.add(aParticipantCost);
    }

    if (verifiedParticipantCosts.size() != newParticipantCosts.length)
    {
      return wasSet;
    }

    participantCosts.clear();
    participantCosts.addAll(verifiedParticipantCosts);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "startWeek" + ":" + getStartWeek()+ "," +
            "endWeek" + ":" + getEndWeek()+ "," +
            "guideEmail" + ":" + getGuideEmail()+ "," +
            "guideName" + ":" + getGuideName()+ "," +
            "totalCostForGuide" + ":" + getTotalCostForGuide()+ "]";
  }
}