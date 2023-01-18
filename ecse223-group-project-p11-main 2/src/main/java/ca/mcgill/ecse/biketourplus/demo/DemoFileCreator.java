package ca.mcgill.ecse.biketourplus.demo;

import java.sql.Date;
import java.util.Calendar;

import ca.mcgill.ecse.biketourplus.persistence.BikeTourPlusPersistence;
import ca.mcgill.ecse.biketourplus.model.BikeTourPlus;
import ca.mcgill.ecse.biketourplus.model.Combo;
import ca.mcgill.ecse.biketourplus.model.Gear;
import ca.mcgill.ecse.biketourplus.model.Guide;
import ca.mcgill.ecse.biketourplus.model.Lodge;
import ca.mcgill.ecse.biketourplus.model.Lodge.LodgeRating;
import ca.mcgill.ecse.biketourplus.model.Manager;
import ca.mcgill.ecse.biketourplus.model.Participant;

public class DemoFileCreator {

	public static void main(String[] args) {
		BikeTourPlus bikeTourPlus = createDemoData();
		// save
		BikeTourPlusPersistence.setFilename("btp.data");
		BikeTourPlusPersistence.save(bikeTourPlus);
		// test
		DemoFileVerifier.verify(bikeTourPlus);
	}

	private static BikeTourPlus createDemoData() {
		// root (NMP program info)
		Calendar calendar = Calendar.getInstance();
		calendar.set(2023, Calendar.JUNE, 1, 0, 0, 0);
		BikeTourPlus bikeTourPLus = new BikeTourPlus(new Date(calendar.getTimeInMillis()), 5, 50);

		// admin
		new Manager("manager@btp.com", "manager", bikeTourPLus);

		// equipment
		Gear repairKit = new Gear("repair kit", 30, bikeTourPLus);
		Gear spareTire = new Gear("spare tire", 100, bikeTourPLus);
		Gear helmet = new Gear("helmet", 50, bikeTourPLus);

		// equipment bundle
		Combo standard = new Combo("standard", 20, bikeTourPLus);
		standard.addComboItem(1, bikeTourPLus, spareTire);
		standard.addComboItem(2, bikeTourPLus, repairKit);
		Combo plus = new Combo("plus", 30, bikeTourPLus);
		plus.addComboItem(2, bikeTourPLus, spareTire);
		plus.addComboItem(4, bikeTourPLus, repairKit);
		plus.addComboItem(3, bikeTourPLus, helmet);

		// hotel
		new Lodge("Climbers' Lodge", "123 Mountain View Road", LodgeRating.ThreeStars, bikeTourPLus);
		new Lodge("High Peak", "455 Mountain View Road", LodgeRating.FourStars, bikeTourPLus);

		// guide
		new Guide("bob@gmail.com", "password", "Bob Hill", "(222) 123-4567", bikeTourPLus);
		new Guide("sarah@yahoo.ca", "pwd", "Sarah Hill", "(222) 123-7654", bikeTourPLus);

		// member
		Participant joe = new Participant("joe@hotmail.com", "1234", "Joe Black", "(222) 987-6540", 2, 2, 6, false, null, 0, bikeTourPLus);
		joe.addBookedItem(1, bikeTourPLus, plus);
		joe.addBookedItem(2, bikeTourPLus, repairKit);
		Participant jane = new Participant("jane@hotmail.com", "1234", "Jane Black", "(222) 987-6541", 3, 3, 5, false, null, 0, bikeTourPLus);
		jane.addBookedItem(1, bikeTourPLus, repairKit);
		jane.addBookedItem(1, bikeTourPLus, spareTire);
		jane.addBookedItem(1, bikeTourPLus, helmet);
		Participant jack = new Participant("jack@hotmail.com", "1234", "Jack Black", "(222) 987-6542", 4, 1, 6, true, null, 0, bikeTourPLus);
		jack.addBookedItem(1, bikeTourPLus, standard);
		jack.addBookedItem(1, bikeTourPLus, plus);
		new Participant("julie@hotmail.com", "1234", "Julie Black", "(222) 987-6543", 1, 1, 6, false, null, 0, bikeTourPLus);
		Participant jon = new Participant("jon@hotmail.com", "1234", "Jon Black", "(222) 987-6544", 3, 1, 6, false, null, 0, bikeTourPLus);
		jon.addBookedItem(3, bikeTourPLus, repairKit);
		jon.addBookedItem(1, bikeTourPLus, spareTire);
		jon.addBookedItem(2, bikeTourPLus, helmet);

		return bikeTourPLus;
	}

}
