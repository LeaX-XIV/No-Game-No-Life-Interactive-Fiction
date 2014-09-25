package it.univpm.deit.semedia.gameclasses.rooms;

import it.univpm.deit.semedia.gameclasses.persons.Person;

import java.io.Serializable;
import java.util.HashMap;

public class Room extends it.univpm.deit.semedia.gameclasses.ContainerImpl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// XXX: NON USARE STRINGHE MA ENUM
	HashMap<String, Room> doors = new HashMap<String, Room>();
	
	public Room(String description) {
		super(description);
	}
	
	/**
	 * Adds a door to this room, 
	 * @param doorname the door name e.g. "north" "south"
	 * @param room the room that this door will lead to
	 */
	public void addDoor(String doorname, Room room) {
		doors.put(doorname,  room);
	}
	
	/**
	 * @return the hashmap doorname->newroom
	 */
	public HashMap<String, Room> getDoors() {
		return doors;
	}
	
	public void Enter(Person person) {
		add(person);
	}
	
	public boolean personExits(Person person,String doorname) {
		if(doors.containsKey(doorname) && contains(person)) {
			Room nextRoom = (Room)doors.get(doorname);
			remove(person);
			nextRoom.Enter(person);
		}
		else {
			return false;
		}
		
		return true;
	}
}
