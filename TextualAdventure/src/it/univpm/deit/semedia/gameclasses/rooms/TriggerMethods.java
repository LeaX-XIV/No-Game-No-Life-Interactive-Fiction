package it.univpm.deit.semedia.gameclasses.rooms;

import it.itido.quinta.informatici.HolyBorio.gameclasses.endgame.Credit;
import it.univpm.deit.semedia.gameclasses.objects.Key;
import it.univpm.deit.semedia.gameclasses.objects.SecretKey;
import it.univpm.deit.semedia.gameclasses.persons.Person;

public class TriggerMethods {
	
	public static void throneEvent(Trigger t) {
		Room r = t.getRoom();
		int count = r.getCount();
		t.setTriggerCount(count + 1);
	}
	
	public static void libraryEvent(Person mc, SecretKey chiave) {
		mc.add(chiave);
	}
	
	public static void federationEvent() {
		new Credit(false).show();
		System.exit(0);
	}

}
