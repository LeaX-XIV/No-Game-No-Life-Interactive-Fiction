package it.univpm.deit.semedia.gameclasses.rooms;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

public class Lock implements Serializable {

	private ArrayList<Byte> cilindro;
	private boolean open;

	public Lock() {
		open = false;
		cilindro = new ArrayList<Byte>();
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			cilindro.add((byte) r.nextInt(256));			
		}
	}

	public Lock(ArrayList<Byte> cilindro) {
		this.open = false;
		if(cilindro != null) {
			if(cilindro.size() == 4) {
				this.cilindro = cilindro;
				return;
			}
		}

		throw new InvalidParameterException();
	}

	public Lock(boolean open) {
		this.open = open;
		cilindro = new ArrayList<Byte>();
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			cilindro.add((byte) r.nextInt(256));			
		}
	}
	
	public Lock(ArrayList<Byte> cilindro, boolean open) {
		this.open = open;
		if(cilindro != null) {
			if(cilindro.size() == 4) {
				this.cilindro = cilindro;
				return;
			}
		}

		throw new InvalidParameterException();
	}

	public ArrayList<Byte> getCode() {
		return this.cilindro;
	}

	public boolean isOpen() {
		return this.open;
	}

	public void open() {
		open = true;
	}

	public boolean unlock(Key key) {

		if(!open) {
			if(key.getCode().equals(this.getCode())) {
				open = true;
			}
		}

		return open;
	}

}
