/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;
import java.sql.Date;

// line 74 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOBikeTourPlus
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBikeTourPlus Attributes
  private Date startDate;
  private int nrWeeks;
  private int priceOfGuidePerWeek;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBikeTourPlus(Date aStartDate, int aNrWeeks, int aPriceOfGuidePerWeek)
  {
    startDate = aStartDate;
    nrWeeks = aNrWeeks;
    priceOfGuidePerWeek = aPriceOfGuidePerWeek;
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "nrWeeks" + ":" + getNrWeeks()+ "," +
            "priceOfGuidePerWeek" + ":" + getPriceOfGuidePerWeek()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}