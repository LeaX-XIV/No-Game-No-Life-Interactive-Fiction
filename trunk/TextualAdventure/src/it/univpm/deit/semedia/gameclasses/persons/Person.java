package it.univpm.deit.semedia.gameclasses.persons;

import it.univpm.deit.semedia.gameclasses.ContainerImpl;
import it.univpm.deit.semedia.gameclasses.GameObject;
import it.univpm.deit.semedia.gameclasses.IContainer;
import it.univpm.deit.semedia.gameclasses.ILivingStatus;
import it.univpm.deit.semedia.gameclasses.LivingStatusIml;

import java.util.Collection;
import java.util.Iterator;

public class Person extends GameObject implements ILivingStatus, IContainer {
	
	ILivingStatus status;
	private IContainer mybag;
	
	public Person(String name, int healthpoints) {
		super(name);
		status = new LivingStatusIml();
		status.heal(healthpoints);
		mybag = new ContainerImpl("La borsa di " + name);
	}

	public int getEnergy() {
		return status.getEnergy();
	}

	public boolean damage(int points) {
		return status.damage(points);
	}

	public boolean heal(int points) {
		return status.heal(points);
	}

	public boolean isAlive() {
		return status.isAlive();
	}

	public void add(GameObject object) {
		mybag.add(object);
	}

	public boolean remove(GameObject object) {
		return mybag.remove(object);
	}

	public boolean moveContainedTo(GameObject object, IContainer newContainer) {
		return mybag.moveContainedTo(object, newContainer);
	}

	public Iterator listItems() {
		return mybag.listItems();
	}

	public boolean contains(GameObject object) {
		return mybag.contains(object);
	}
	
	/**
	 * Returns a list of objects that are "visible" to this person.
	 * This implementation returns the objects that are either in the same container
	 * or in the person's bag
	 * 
	 * @return a hashmap with a mapping String name -> GameObject , no duplicate names are present, identically named objects have a progressive number attached to it
	
	public Map getVisibleObjects() {
		HashMap result= new HashMap();
		bagItems(result,mybag.listItems());
		bagItems(result,getContainedIn().listItems());
		return result;  
	}*/

	public GameObject objectByName(String objectname) {
		return mybag.objectByName(objectname);
	}

	public String getContentDescription() {
		return "Borsa:\n" + mybag.getContentDescription();
	}

	public GameObject objectByNameRecursive(String objectname) {
		return mybag.objectByNameRecursive(objectname);
	}

	public Collection listItemsRecursive() {
		return mybag.listItemsRecursive();
	}
	
}
