package it.univpm.deit.semedia.gameclasses.rooms;

import it.univpm.deit.semedia.gameclasses.persons.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Room extends it.univpm.deit.semedia.gameclasses.ContainerImpl implements Serializable {
	
	// XXX: NON USARE STRINGHE MA ENUM
	ArrayList<Door> doors = new ArrayList<Door>();
	
	private int enterCount;
	private Trigger trigger;
	
	public Room(String description) {
		super(description);
		enterCount = 0;
		trigger = null;
	}
	
	/**
	 * Adds a door to this room, 
	 * @param doorname the door name e.g. "north" "south"
	 * @param room the room that this door will lead to
	 */
	protected void addDoor(Door door) {
		if(door.getRooms().contains(this)) {
			doors.add(door);
		}
	}
	
//	/**
//	 * @return the hashmap doorname->newroom
//	 */
//	public HashMap<String, Room> getDoors() {
//		return doors;
//	}
	

	public HashMap<String, Room> getDoors() {
		HashMap<String, Room> doorList = new HashMap<String, Room>();
		
		Iterator<Door> it = doors.iterator();
		while(it.hasNext()) {
			Door door = it.next();
			if(door.isOpen()) {
				HashMap<String, Room> temp = door.getOtherEnd(this);
				if(temp != null) {
					doorList.putAll(temp);
				}
			}
		}
		
		return doorList;
	}
	
	public void enter(Person person) {
		add(person);
		enterCount++;
		if(trigger != null) {
			trigger.execute();
		}
	}
	
	public boolean hasClosedDoor() {
		return !getClosedDoor().isEmpty();
	}
	
	public ArrayList<Door> getClosedDoor() {
		ArrayList<Door> closed = new ArrayList<Door>();
		Iterator<Door> it = doors.iterator();
		while(it.hasNext()) {
			Door d = it.next();
			if(!d.isOpen()) {
				closed.add(d);
			}
		}
		
		return closed;
	}
	
	public boolean personExits(Person person, String doorname) {
		if(contains(person)) {
			HashMap<String, Room> porte = getDoors();
			if(porte.containsKey(doorname)) {
				Room nextRoom = porte.get(doorname);
				remove(person);
				nextRoom.enter(person);
				
				return true;
			}
			
		}
		
		return false;
	}
	
	public int getCount() {
		return enterCount;
	}
	
	public void setTrigger() {
		this.trigger = new Trigger(this);
	}
}
