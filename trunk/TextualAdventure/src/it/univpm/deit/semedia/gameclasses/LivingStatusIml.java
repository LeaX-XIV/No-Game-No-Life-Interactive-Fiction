package it.univpm.deit.semedia.gameclasses;

import java.io.Serializable;

public class LivingStatusIml implements ILivingStatus, Serializable {
	
	private int energy;
	
	public int getEnergy() {
		return energy;
	}
	
	/**
	 * @see ILivingStatus#damage(int) 
	 */
	public boolean damage(int points) {
		if(points < 0) {
			heal(points * (-1));
		}
		else {
			energy -= points;
		}
		
		return isAlive();
	}
	
	/**
	 * @see ILivingStatus#heal(int)
	 */
	public boolean heal(int points) {
		if(points < 0) {
			damage(points * (-1));
		}
		else {
			energy += points;
		}
		
		return isAlive();
	}

	public boolean isAlive() {
		return energy > 0;
	}
}
