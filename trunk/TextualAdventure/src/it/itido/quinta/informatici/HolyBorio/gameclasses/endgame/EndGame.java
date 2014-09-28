package it.itido.quinta.informatici.HolyBorio.gameclasses.endgame;

/**
 * 
 * @author LeaX
 *
 */
public class EndGame {
	
	private boolean finishedWithoutDeath;
	
	public EndGame(boolean finished) {
		finishedWithoutDeath = finished;
	}
	
	public void run() {
		Credit c = new Credit();
		MusicPlayer mp = new MusicPlayer(false);
		
		c.setWaitTime((int) (mp.getDurationAudioFile() / c.getReturns()));

		mp.start();
		c.show();
		
		if(finishedWithoutDeath) {
			System.out.print("\t\t\t\t\t\t\t\t\t    Fine");
		}
		else {
			new Continue();
		}
				
		System.exit(0);
	}
	
	public static void main(String[] args) {
		EndGame eg = new EndGame(false);
		eg.run();
	}
}
