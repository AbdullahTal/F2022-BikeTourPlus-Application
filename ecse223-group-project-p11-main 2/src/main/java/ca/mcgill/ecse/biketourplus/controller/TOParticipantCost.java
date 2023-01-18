/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 14 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOParticipantCost
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOParticipantCost Attributes
  private String participantEmail;
  private String participantName;
  private int totalCostForBookableItems;
  private int totalCostForBikingTour;
  private String status;
  private String authorizationCode;
  private int refundedPercentageAmount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOParticipantCost(String aParticipantEmail, String aParticipantName, int aTotalCostForBookableItems, int aTotalCostForBikingTour, String aStatus, String aAuthorizationCode, int aRefundedPercentageAmount)
  {
    participantEmail = aParticipantEmail;
    participantName = aParticipantName;
    totalCostForBookableItems = aTotalCostForBookableItems;
    totalCostForBikingTour = aTotalCostForBikingTour;
    status = aStatus;
    authorizationCode = aAuthorizationCode;
    refundedPercentageAmount = aRefundedPercentageAmount;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getParticipantEmail()
  {
    return participantEmail;
  }

  public String getParticipantName()
  {
    return participantName;
  }

  public int getTotalCostForBookableItems()
  {
    return totalCostForBookableItems;
  }

  public int getTotalCostForBikingTour()
  {
    return totalCostForBikingTour;
  }

  public String getStatus()
  {
    return status;
  }

  public String getAuthorizationCode()
  {
    return authorizationCode;
  }

  public int getRefundedPercentageAmount()
  {
    return refundedPercentageAmount;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "participantEmail" + ":" + getParticipantEmail()+ "," +
            "participantName" + ":" + getParticipantName()+ "," +
            "totalCostForBookableItems" + ":" + getTotalCostForBookableItems()+ "," +
            "totalCostForBikingTour" + ":" + getTotalCostForBikingTour()+ "," +
            "status" + ":" + getStatus()+ "," +
            "authorizationCode" + ":" + getAuthorizationCode()+ "," +
            "refundedPercentageAmount" + ":" + getRefundedPercentageAmount()+ "]";
  }
}