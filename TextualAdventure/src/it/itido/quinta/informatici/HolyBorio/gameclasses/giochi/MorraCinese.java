package it.itido.quinta.informatici.HolyBorio.gameclasses.giochi;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

import it.univpm.deit.semedia.ConsoleCommand;
import it.univpm.deit.semedia.GenericConsole;

/**
 * 
 * @author LeaX
 *
 */
public class MorraCinese extends Gioco {

	public MorraCinese(InputStream in, PrintStream out) {
		super(in, out);
		
		super.registerCommand(new ConsoleCommand("carta") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				int cpu = getCpu();
				writeResult(0, cpu);
				if(!check(0, cpu)) {
					out.println("Ancora!");
					executeLine("start");
				}
				else {
					if(play(0, cpu)) {
						endGame(true);
					}
					else {
						endGame(false);
					}
				}
			}

			@Override
			public String description() {
				return "Gioca carta.";
			}

		});
		
		super.registerCommand(new ConsoleCommand("sasso") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				int cpu = getCpu();
				writeResult(1, cpu);
				if(!check(1, cpu)) {
					out.println("Ancora!");
					executeLine("start");
				}
				else {
					if(play(1, cpu)) {
						endGame(true);
					}
					else {
						endGame(false);
					}
				}
			}

			@Override
			public String description() {
				return "Gioca sasso.";
			}

		});
		
		super.registerCommand(new ConsoleCommand("forbici") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				int cpu = getCpu();
				writeResult(2, cpu);
				if(!check(2, cpu)) {
					out.println("Ancora!");
					executeLine("start");
				}
				else {
					if(play(2, cpu)) {
						endGame(true);
					}
					else {
						endGame(false);
					}
				}
			}

			@Override
			public String description() {
				return "Gioca forbici.";
			}

		});
		
		super.registerCommand(new ConsoleCommand("start") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
			}

			@Override
			public String description() {
				return "Inizia il gioco.";
			}

		});
		
	}
	
	@Override
	protected String consolePrompt() {
		return "> ";
	}
	
	private int getCpu() {
		Random r = new Random();
		int cpu = r.nextInt(3);
		
		return cpu;
	}
	
	/**
	 * 
	 * @param i 0=paper, 1=rock, 2=scissors
	 * @param cpu 0=paper, 1=rock, 2=scissors
	 * @return true if win, false if lose
	 */
	private boolean play(int i, int cpu) {
		boolean result = false;
		
		if(i == 0 && cpu == 1) {
			result = true;
		}
		else if(i == 1 && cpu == 2) {
			result = true;
		}
		else if(i == 2 && cpu == 0) {
			result = true;
		}
		
		return result;
	}
	
	private boolean check(int i, int  cpu) {		
		if(i == cpu) {
			return false;
		}
		
		return true;
	}
	
	private void writeResult(int i, int cpu) {
		System.out.println("" + (i==0?"carta":i==1?"sasso":"forbici") + "          -          " + (cpu==0?"carta":cpu==1?"sasso":"forbici"));
	}
	
	public static void main(String[] args) {
		MorraCinese morra = new MorraCinese(System.in, System.out);
		morra.run();
		
	}

	@Override
	public void endGame(boolean end) {
		synchronized(qualcosaPerSincronizzare) {
			win = end;
			qualcosaPerSincronizzare.notify();
		}
		executeLine("exit");
	}
}
