package it.univpm.deit.semedia.gameclasses.objects;

import it.univpm.deit.semedia.gameclasses.GameObject;
import it.univpm.deit.semedia.gameclasses.persons.Person;

public class Banana extends GameObject {

	public Banana() {
		this("banana");
	}

	public Banana(String name) {
		super(name);
	}

	public String use(Person who) {
		// give energy
		who.heal(10);
		// disappair
		getContainedIn().remove(this);
		// communicate happyness
		return "That was a good banana";
	}

	public String use(Person who, GameObject target) {
		if (target instanceof Person) {
			return "That would be highly immoral";
		} else {
			return super.use(who, target);
		}
	}
}
