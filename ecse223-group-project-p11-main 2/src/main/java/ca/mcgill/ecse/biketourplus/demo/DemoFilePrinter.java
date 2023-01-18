package ca.mcgill.ecse.biketourplus.demo;

import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.BookedItem;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.ComboItem;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Lodge;
import ca.mcgill.ecse.biketourplus.model.Manager;
import ca.mcgill.ecse.biketourplus.model.Participant;

public class DemoFilePrinter {

	public static void print(BikeTourPlus bikeTourPlus) {
		System.out.println("BikeTourPlus: startDate = " + bikeTourPlus.getStartDate().toString() + "; nrOfWeeks = " + bikeTourPlus.getNrWeeks()
			+ "; priceOfGuidePerWeek = " + bikeTourPlus.getPriceOfGuidePerWeek());
		Manager manager = bikeTourPlus.getManager();
		System.out.println("Manager: email = " + manager.getEmail() + "; password = " + manager.getPassword());
		for (Gear gear : bikeTourPlus.getGear()) {
			System.out.println("Gear: name = " + gear.getName() + "; pricePerWeek = " + gear.getPricePerWeek());
		}
		for (Combo combo : bikeTourPlus.getCombos()) {
			System.out.println("Combo: name = " + combo.getName() + "; discount = " + combo.getDiscount());
			for (ComboItem item : combo.getComboItems()) {
				System.out.println("   Item: name = " + item.getGear().getName() + "; quantity = " + item.getQuantity());
			}
		}
		for (Lodge lodge : bikeTourPlus.getLodges()) {
			System.out.println("Lodge: name = " + lodge.getName() + "; address = " + lodge.getAddress()
				+ "; rating = " + lodge.getRating());
		}
		for (Guide guide : bikeTourPlus.getGuides()) {
			System.out.println("Guide: email = " + guide.getEmail() + "; password = " + guide.getPassword() + "; name = " + guide.getName()
				+ "; emergencyContact = " + guide.getEmergencyContact());
		}
		for (Participant participant : bikeTourPlus.getParticipants()) {
			System.out.println("Member: email = " + participant.getEmail() + "; password = " + participant.getPassword() + "; name = " + participant.getName()
				+ "; emergencyContact = " + participant.getEmergencyContact() + "; nrWeeks = " + participant.getNrWeeks()
				+ "; weekAvailableFrom = " + participant.getWeekAvailableFrom() + "; weekAvailableUntil = " + participant.getWeekAvailableUntil()
				+ "; lodgeRequired = " + participant.getLodgeRequired());
			for (BookedItem item : participant.getBookedItems()) {
				System.out.println("   Item: name = " + item.getItem().getName() + "; quantity = " + item.getQuantity());
			}
		}
	}

}
