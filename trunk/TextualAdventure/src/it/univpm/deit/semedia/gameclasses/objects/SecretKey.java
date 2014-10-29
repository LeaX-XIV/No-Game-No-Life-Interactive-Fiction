package it.univpm.deit.semedia.gameclasses.objects;

import java.util.ArrayList;

import it.univpm.deit.semedia.gameclasses.GameObject;
import it.univpm.deit.semedia.gameclasses.persons.Person;

public class SecretKey extends Key{

	private GameObject target;
	
	public SecretKey(String name, ArrayList<Byte> denti, GameObject target) {
		super(name, denti);
		
		this.target = target;
	}
	
	@Override
	public String use(Person who) {
		return "È la stanza dei porno, non sarà così facile trovarla.";
	}

	@Override
	public String use(Person who, GameObject target) {
		if(target != null) {
			if(target == this.target) {
				return super.use(who);
			}
			else {
				return "Non so cosa tu voglia aprire, ma non sembra una buona idea.";
			}
		}
		else {
			return "Non puoi usare la chiave qui.";
		}
	}

}
