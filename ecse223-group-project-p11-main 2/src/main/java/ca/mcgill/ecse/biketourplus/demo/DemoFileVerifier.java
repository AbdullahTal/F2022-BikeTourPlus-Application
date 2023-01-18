package ca.mcgill.ecse.biketourplus.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.BookableItem;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Lodge;
import ca.mcgill.ecse.biketourplus.model.Lodge.LodgeRating;
import ca.mcgill.ecse.biketourplus.model.Manager;
import ca.mcgill.ecse.biketourplus.model.Participant;
import ca.mcgill.ecse.biketourplus.model.User;

public class DemoFileVerifier {

	public static void verify(BikeTourPlus bikeTourPlus) {
		// clear
		bikeTourPlus.delete();
		// load
		bikeTourPlus = BikeTourPlusPersistence.load();
		// test
		verifyLoaded(bikeTourPlus);
		// output
		DemoFilePrinter.print(bikeTourPlus);
	}

	private static void verifyLoaded(BikeTourPlus bikeTourPlus) {
		// root
		verifyBikeTourPlus(bikeTourPlus, "2023-06-01", 5, 50);

		// admin
		verifyManager("manager@btp.com", "manager");

		// equipment
		assertEquals(3, bikeTourPlus.getGear().size());
		verifyGear("repair kit", 1000, 30);
		verifyGear("spare tire", 2500, 100);
		verifyGear("helmet", 2000, 50);

		// equipment bundle
		assertEquals(2, bikeTourPlus.getCombos().size());
		verifyCombo("standard", 20, new int[] {1, 2}, new String[] {"spare tire", "repair kit"});
		verifyCombo("plus", 30, new int[] {2, 4, 3}, new String[] {"spare tire", "repair kit", "helmet"});

		// hotel
		assertEquals(2, bikeTourPlus.getLodges().size());
		verifyLodge("Climbers' Lodge", "123 Mountain View Road", LodgeRating.ThreeStars);
		verifyLodge("High Peak", "455 Mountain View Road", LodgeRating.FourStars);

		// guide
		assertEquals(2, bikeTourPlus.getGuides().size());
		verifyGuide("bob@gmail.com", "password", "Bob Hill", "(222) 123-4567");
		verifyGuide("sarah@yahoo.ca", "pwd", "Sarah Hill", "(222) 123-7654");

		// member
		assertEquals(5, bikeTourPlus.getParticipants().size());
		verifyParticipant("joe@hotmail.com", "1234", "Joe Black", "(222) 987-6540", 2, 2, 6, false, new int[] {1, 2}, new String[] {"plus", "repair kit"});
		verifyParticipant("jane@hotmail.com", "1234", "Jane Black", "(222) 987-6541", 3, 3, 5, false, new int[] {1, 1, 1},
				new String[] {"repair kit", "spare tire", "helmet"});
		verifyParticipant("jack@hotmail.com", "1234", "Jack Black", "(222) 987-6542", 4, 1, 6, true, new int[] {1, 1}, new String[] {"standard", "plus"});
		verifyParticipant("julie@hotmail.com", "1234", "Julie Black", "(222) 987-6543", 1, 1, 6, false, new int[] {}, new String[] {});
		verifyParticipant("jon@hotmail.com", "1234", "Jon Black", "(222) 987-6544", 3, 1, 6, false, new int[] {3, 1, 2},
				new String[] {"repair kit", "spare tire", "helmet"});

		System.out.println("=======================");
		System.out.println("Verification successful");
		System.out.println("=======================");
	}

	private static void verifyManager(String email, String password) {
		Manager admin = (Manager) User.getWithEmail(email);
		assertNotNull(admin);
		assertEquals(password, admin.getPassword());
	}

	private static void verifyBikeTourPlus(BikeTourPlus bikeTourPlus, String startDate, int nrWeeks, int priceOfGuidePerWeek) {
		assertEquals(startDate, bikeTourPlus.getStartDate().toString());
		assertEquals(nrWeeks, bikeTourPlus.getNrWeeks());
		assertEquals(priceOfGuidePerWeek, bikeTourPlus.getPriceOfGuidePerWeek());
	}

	private static void verifyGear(String name, int weight, int pricePerWeek) {
		Gear gear = (Gear) BookableItem.getWithName(name);
		assertNotNull(gear);
		assertEquals(pricePerWeek, gear.getPricePerWeek());
	}

	private static void verifyCombo(String name, int discount, int[] quantities, String[] gearNames) {
		Combo combo = (Combo) BookableItem.getWithName(name);
		assertNotNull(combo);
		assertEquals(discount, combo.getDiscount());
		assertEquals(quantities.length, combo.getComboItems().size());
		for (int i=0; i<quantities.length; i++) {
			assertEquals(quantities[i], combo.getComboItem(i).getQuantity());
			assertEquals(gearNames[i], combo.getComboItem(i).getGear().getName());
		}
	}

	private static void verifyGuide(String email, String password, String name, String emergencyContact) {
		Guide guide = (Guide) User.getWithEmail(email);
		assertNotNull(guide);
		assertEquals(password, guide.getPassword());
		assertEquals(name, guide.getName());
		assertEquals(emergencyContact, guide.getEmergencyContact());
	}

	private static void verifyLodge(String name, String address, LodgeRating rating) {
		Lodge lodge = Lodge.getWithName(name);
		assertNotNull(lodge);
		assertEquals(address, lodge.getAddress());
		assertEquals(rating, lodge.getRating());
	}

	private static void verifyParticipant(String email, String password, String name, String emergencyContact,
			int nrWeeks, int weekAvailableFrom, int weekAvailableUntil,
			boolean lodgeRequired, int[] quantities, String[] bookableItemsNames) {
		Participant participant = (Participant) User.getWithEmail(email);
		assertNotNull(participant);
		assertEquals(password, participant.getPassword());
		assertEquals(name, participant.getName());
		assertEquals(emergencyContact, participant.getEmergencyContact());
		assertEquals(nrWeeks, participant.getNrWeeks());
		assertEquals(weekAvailableFrom, participant.getWeekAvailableFrom());
		assertEquals(weekAvailableUntil, participant.getWeekAvailableUntil());
		assertEquals(nrWeeks, participant.getNrWeeks());
		assertEquals(lodgeRequired, participant.getLodgeRequired());
		assertEquals(quantities.length, participant.getBookedItems().size());
		for (int i=0; i<quantities.length; i++) {
			assertEquals(quantities[i], participant.getBookedItem(i).getQuantity());
			assertEquals(bookableItemsNames[i], participant.getBookedItem(i).getItem().getName());
		}
	}

}
