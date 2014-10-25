package it.univpm.deit.semedia.gameclasses.rooms;

import it.univpm.deit.semedia.gameclasses.objects.Key;
import it.univpm.deit.semedia.gameclasses.persons.Person;

public class TriggerMethods {
	
	public static void libraryEvent(Person mc, Key chiave) {
		mc.add(chiave);
	}

}
