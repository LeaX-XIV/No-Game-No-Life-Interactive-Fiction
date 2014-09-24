package it.univpm.deit.semedia.gameclasses;

import it.univpm.deit.semedia.gameclasses.persons.Person;

/**
 * Basic class for each object in the game
 * @author Andrea Primavera
 *
 */
public class GameObject {
	
	private String description = "";
	private String name;
	private int size;
	private IContainer containedin;
	
	/**
	 * The name of the object
	 * @param containedin
	 */
	public GameObject(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the container that currently holds this object
	 * @return
	 */
	public IContainer getContainedIn() {
		return containedin;
	}
	
	/**
	 * Private method used by the container implementation
	 * @param container
	 */
	protected void setContainedIn(IContainer container) {
		this.containedin = container;
	}
	
	public String toString() {
		return name;
	}
	
	/**
	 * returns a description of this object
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the size of this object in imaginary cubicles
	 * @return
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Ground implementation of self use nothing is done
	 * @return
	 */
	public String use(Person who) {
		return "It doesn't make sense";
	}
	
	/**
	 * Ground implementation of use upon another object nothing is done
	 * @return
	 */
	public String use(Person who, GameObject target) {
		return "It doesn't make sense to use " + getName() + " on " + target.getName();
	}
}
