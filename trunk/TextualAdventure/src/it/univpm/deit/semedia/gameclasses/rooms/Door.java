package it.univpm.deit.semedia.gameclasses.rooms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Door{

	private Lock lock;
	private HashMap<String, Room> link;
	
	public Door() {
		this.lock = new Lock();
		this.link = new HashMap<String, Room>();
	}
	
	public Door(Lock lock) {
		this.lock = lock;
		this.link = new HashMap<String, Room>();
	}
	
	public void addLink(String direction1, String direction2, Room room1, Room room2) {
		link.put(direction2, room1);
		link.put(direction1, room2);
		room1.addDoor(this);
		room2.addDoor(this);
	}
	
	public boolean isOpen() {
		return this.lock.isOpen();
	}
	
	public ArrayList<Room> getRooms() {
		ArrayList<Room> list = new ArrayList<Room>();
		Iterator<Room> it = link.values().iterator();
		while(it.hasNext()) {
			list.add(it.next());
		}
		
		return list;
	}
	
	public HashMap<String, Room> getOtherEnd(Room room) {
		if(link.containsValue(room)) {
			HashMap<String, Room> rooms = (HashMap<String, Room>) link.clone();
			rooms.remove(getKeyByValue(room), room);
			return rooms;
		}
		return null;
	}
	
	private String getKeyByValue(Room value) {
	   return getKeyByValue(link, value);
	}
	
	public void open() {
		lock.open();
	}
	
	public boolean unlock(Key key) {
		return lock.unlock(key);
	}
	
	public static <K, V> K getKeyByValue(HashMap<K, V> map, Room value) {
		 for (Entry<K, V> entry : map.entrySet()) {
		        if (value.equals(entry.getValue())) {
		            return entry.getKey();
		        }
		    }
		    return null;
	}

}
