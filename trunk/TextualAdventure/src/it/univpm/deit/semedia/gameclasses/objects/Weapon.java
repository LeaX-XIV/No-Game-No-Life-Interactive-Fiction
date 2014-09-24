package it.univpm.deit.semedia.gameclasses.objects;

import it.univpm.deit.semedia.gameclasses.ILivingStatus;
import it.univpm.deit.semedia.gameclasses.persons.Person;

public class Weapon extends it.univpm.deit.semedia.gameclasses.GameObject {
	
	int hitpoint;
	
	public Weapon(String name, int hitpoint) {
		super(name);
		this.hitpoint = hitpoint;
	}
	
	public String use(Person who, it.univpm.deit.semedia.gameclasses.GameObject target) {
		if(target instanceof ILivingStatus) {
			
			if(!((ILivingStatus)target).damage(hitpoint)); // target is dead
			return "A damage of " + hitpoint + " was inflicted\n"
					+ target.getName() + " is dead.";
		}
		else {
			return super.use(who, target);
		}
	}
}
