package it.univpm.deit.semedia.gameclasses;

import java.util.Collection;
import java.util.Iterator;

public interface IContainer {

	/**
	 * Adds an object to this container.
	 * Implementations must also update the containedIn field in each object they handle
	 * @param object
	 */
	public abstract void add(GameObject object);

	/**
	 * Removes an object if present
	 * Implementations must also update the containedIn field in each object they handle
	 * @param object
	 * @return true if the object was present
	 */
	public abstract boolean remove(GameObject object);

	/**
	 * Atomically moves an object contained in this contaner to another
	 * @param object
	 * @param newContainer
	 * @return
	 */
	public abstract boolean moveContainedTo(GameObject object, IContainer newContainer);
	
	/**
	 * Returns an iterator for the items here contained
	 * @return
	 */
	public Iterator listItems();
	
	/**
	 * Returns true if a given object is contained
	 * @param object
	 * @return
	 */
	public boolean contains(GameObject object);
	
	/**
	 * If a named object is contained this is returned, if multiple objects share the same name, the first one will be returned
	 * @param objectname
	 * @return the GameObject or null if no such named object was found inside the container
	 */
	public GameObject objectByName(String objectname);
	
	/**
	 * If a named object is contained, also within subcontainers, this is returned, if multiple objects share the same name, the first one will be returned
	 * @param objectname
	 * @return the GameObject or null if no such named object was found inside the container
	 */
	public GameObject objectByNameRecursive(String objectname);
	
	/**
	 * Returns a string describing the content
	 */
	public String getContentDescription();
	
	/**
	 * Returns all the items contained in this container including those in subcontainers
	 * @return
	 */
	public abstract Collection listItemsRecursive();
}