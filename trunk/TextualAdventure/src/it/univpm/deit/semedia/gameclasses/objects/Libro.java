package it.univpm.deit.semedia.gameclasses.objects;

import it.univpm.deit.semedia.gameclasses.GameObject;
import it.univpm.deit.semedia.gameclasses.persons.Person;

public class Libro extends GameObject {

	private String text;
	
	public Libro(String name, String text) {
		super(name);
		this.text = text;
	}
	
	@Override
	public String use(Person who) {
		return text;
	}
	
}
