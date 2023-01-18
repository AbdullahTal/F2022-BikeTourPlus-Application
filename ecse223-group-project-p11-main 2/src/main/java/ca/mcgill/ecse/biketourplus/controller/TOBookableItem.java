/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.biketourplus.controller;

// line 39 "../../../../../BikeTourPlusTransferObjects.ump"
public class TOBookableItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBookableItem Attributes
  private String name;
  private double price;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBookableItem(String aName, double aPrice)
  {
    name = aName;
    price = aPrice;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getName()
  {
    return name;
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
            "price" + ":" + getPrice()+ "]";
  }
}