package it.univpm.deit.semedia.gameclasses.objects;

import it.univpm.deit.semedia.gameclasses.CollectableItem;
import it.univpm.deit.semedia.gameclasses.GameObject;
import it.univpm.deit.semedia.gameclasses.persons.Person;
import it.univpm.deit.semedia.gameclasses.rooms.Door;
import it.univpm.deit.semedia.gameclasses.rooms.Room;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Key extends CollectableItem implements Serializable {
	
	private ArrayList<Byte> denti;
	
	public Key() {
		super("chiave");
		denti = new ArrayList<Byte>();
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			denti.add((byte) r.nextInt(256));			
		}
	}
	
	public Key(ArrayList<Byte> denti) {
		super("chiave");
		if(denti != null) {
			if(denti.size() == 4) {
				this.denti = denti;
				return;
			}
		}
		
		throw new InvalidParameterException();
	}
	
	public Key(String name, ArrayList<Byte> denti) {
		super(name);
		if(denti != null) {
			if(denti.size() == 4) {
				this.denti = denti;
				return;
			}
		}
		
		throw new InvalidParameterException();
	}
	
	public ArrayList<Byte> getCode() {
		return this.denti;
	}
	
	@Override
	public String use(Person who) {
		Room room = (Room) who.getContainedIn();
		if(room.hasClosedDoor()) {
			ArrayList<Door> closed = room.getClosedDoor();
			Iterator<Door> it = closed.iterator();
			while(it.hasNext()) {
				Door d = it.next();
				if(d.unlock(this)) {
					return "Hai aperto la porta a " + Door.getKeyByValue(d.getOtherEnd(room), d.getRooms().indexOf(room)==0? d.getRooms().get(1):d.getRooms().get(0));
				}
			}
		}
		
		return "Nessuna porta da aprire con questa chiave.";
	}
	
	@Override
	public String toString() {
		return denti.toString().replace("[", "").replace("]", "").replace(", ", ".");
	}

}
