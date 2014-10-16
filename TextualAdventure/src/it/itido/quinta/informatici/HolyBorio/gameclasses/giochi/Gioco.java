package it.itido.quinta.informatici.HolyBorio.gameclasses.giochi;

import java.io.InputStream;
import java.io.PrintStream;

import it.univpm.deit.semedia.GenericConsole;

public abstract class Gioco extends GenericConsole {

	protected Boolean win;
	protected final Object qualcosaPerSincronizzare = new Object();
	
	public Gioco(InputStream in, PrintStream out) {
		super(in, out);
		win = null;
	}
	
	public boolean getResult() {
		super.run();
		
		synchronized (qualcosaPerSincronizzare) {
			while(win == null) {
				try {
					qualcosaPerSincronizzare.wait();
				} catch (InterruptedException e) {
				}
			}
			
			this.executeLine("exit");
			
			return win;
		}
	}
	
	public void endGame(boolean result) {
		synchronized (qualcosaPerSincronizzare) {
			win = result;

			qualcosaPerSincronizzare.notify();
		}
		
		executeLine("exit");
	}

}
