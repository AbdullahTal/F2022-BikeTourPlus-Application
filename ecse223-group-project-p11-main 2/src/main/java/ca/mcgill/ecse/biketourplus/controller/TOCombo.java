/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 61 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOCombo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOCombo Attributes
  private String name;
  private int discount;
  private double price;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOCombo(String aName, int aDiscount, double aPrice)
  {
    name = aName;
    discount = aDiscount;
    price = aPrice;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getName()
  {
    return name;
  }

  public int getDiscount()
  {
    return discount;
  }

  public double getPrice()
  {
    return price;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "discount" + ":" + getDiscount()+ "," +
            "price" + ":" + getPrice()+ "]";
  }
}