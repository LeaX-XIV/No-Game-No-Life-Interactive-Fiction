package it.univpm.deit.semedia.gameclasses;
/**
 * An interface for the "LivingStatus" of any living being in the game
 *
 * @author Acer
 *
 */
public interface ILivingStatus {
	
	/**
	 * Returns the current healt points
	 * @return
	 */
	public int getEnergy();
	
	/**
	 * Subtracts healt points
	 * @param points
	 * @return isAlive
	 */
	boolean damage(int points);
	
	/**
	 * Adds health points
	 * @param points
	 * @return isAlive
	 */
	boolean heal(int points);
	
	/**
	 * Healthpoints>0
	 * @return
	 */
	public boolean isAlive();
}
