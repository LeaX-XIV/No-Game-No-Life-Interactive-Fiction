package it.univpm.deit.semedia.gameclasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
/**
 * A container in the game, a room , a bag anythign.
 * 
 * 
 * @author Acer
 *
 */
public class ContainerImpl extends GameObject implements IContainer{
	
	private ArrayList content = new ArrayList();
	public ContainerImpl(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see it.univpm.deit.semedia.gameclasses.IContainer#add(java.lang.Object)
	 */
	public void add(GameObject object) {
		content.add(object);
		object.setContainedIn(this);
	}
	
	/* (non-Javadoc)
	 * @see it.univpm.deit.semedia.gameclasses.IContainer#remove(java.lang.Object)
	 */
	public boolean remove(GameObject object) {
		object.setContainedIn(null);
		
		return content.remove(object);
	}
	
	/* (non-Javadoc)
	 * @see it.univpm.deit.semedia.gameclasses.IContainer#moveTo(java.lang.Object, it.univpm.deit.semedia.gameclasses.IContainer)
	 */
	public boolean moveContainedTo(GameObject object, IContainer newContainer) {
		if(remove(object)) {
			newContainer.add(object);
			
			return true;
		}
		
		return false;
	}
	/** (non-Javadoc)
	 * @see it.univpm.deit.semedia.gameclasses.IContainer#listItems()
	 */
	public Iterator listItems() {
		return content.iterator();
	}

	public boolean contains(GameObject object) {
		return content.contains(object);
	}

	public Collection listItemsRecursive() {
		ArrayList list = new ArrayList();
		for(Iterator iter = content.iterator(); iter.hasNext();) {
			GameObject element = (GameObject)iter.next();
			if(element instanceof IContainer) {
				list.addAll(((IContainer)element).listItemsRecursive());
			}
			list.add(element);
		}
		
		return list;
	}	

	public String getContentDescription() {
		String result = "";
		if(listItems().hasNext()) {;
			for(Iterator<GameObject> iter = listItems(); iter.hasNext();) {
				result += "* " + iter.next().getName() + "\n"; 
			}
		}
		else {
			result += "Nulla";
		}

		return result + "\n";
	}
	
	public String getDescription() {
		return super.getDescription() + "\n" + getContentDescription();
	}
	
	/**
	 * retrieves the first GameObject with a given name in a collection
	 * @param objectname
	 * @param collection
	 * @return
	 */
	private GameObject _objectByName(String objectname, Collection collection) {
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			GameObject element = (GameObject) iter.next();
			if(element.getName().equals(objectname)) 
				return element;
		}
		
		return null;
	}
	
	public GameObject objectByName(String objectname) {
		return _objectByName(objectname, content);
	}
	
	public GameObject objectByNameRecursive(String objectname) {
		return _objectByName(objectname, listItemsRecursive());
	}
}
