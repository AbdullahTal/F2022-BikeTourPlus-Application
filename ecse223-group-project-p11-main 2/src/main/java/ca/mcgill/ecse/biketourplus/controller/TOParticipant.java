/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 25 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOParticipant
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOParticipant Attributes
  private String participantEmail;
  private String participantName;
  private String participantPassword;
  private String participantEmergencyContact;
  private int nrWeeks;
  private int weekAvailableFrom;
  private int weekAvailableUntil;
  private boolean lodgeRequired;
  private String authorizationCode;
  private int refundedPercentageAmount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOParticipant(String aParticipantEmail, String aParticipantName, String aParticipantPassword, String aParticipantEmergencyContact, int aNrWeeks, int aWeekAvailableFrom, int aWeekAvailableUntil, boolean aLodgeRequired, String aAuthorizationCode, int aRefundedPercentageAmount)
  {
    participantEmail = aParticipantEmail;
    participantName = aParticipantName;
    participantPassword = aParticipantPassword;
    participantEmergencyContact = aParticipantEmergencyContact;
    nrWeeks = aNrWeeks;
    weekAvailableFrom = aWeekAvailableFrom;
    weekAvailableUntil = aWeekAvailableUntil;
    lodgeRequired = aLodgeRequired;
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

  public String getParticipantPassword()
  {
    return participantPassword;
  }

  public String getParticipantEmergencyContact()
  {
    return participantEmergencyContact;
  }

  public int getNrWeeks()
  {
    return nrWeeks;
  }

  public int getWeekAvailableFrom()
  {
    return weekAvailableFrom;
  }

  public int getWeekAvailableUntil()
  {
    return weekAvailableUntil;
  }

  public boolean getLodgeRequired()
  {
    return lodgeRequired;
  }

  public String getAuthorizationCode()
  {
    return authorizationCode;
  }

  public int getRefundedPercentageAmount()
  {
    return refundedPercentageAmount;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isLodgeRequired()
  {
    return lodgeRequired;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "participantEmail" + ":" + getParticipantEmail()+ "," +
            "participantName" + ":" + getParticipantName()+ "," +
            "participantPassword" + ":" + getParticipantPassword()+ "," +
            "participantEmergencyContact" + ":" + getParticipantEmergencyContact()+ "," +
            "nrWeeks" + ":" + getNrWeeks()+ "," +
            "weekAvailableFrom" + ":" + getWeekAvailableFrom()+ "," +
            "weekAvailableUntil" + ":" + getWeekAvailableUntil()+ "," +
            "lodgeRequired" + ":" + getLodgeRequired()+ "," +
            "authorizationCode" + ":" + getAuthorizationCode()+ "," +
            "refundedPercentageAmount" + ":" + getRefundedPercentageAmount()+ "]";
  }
}