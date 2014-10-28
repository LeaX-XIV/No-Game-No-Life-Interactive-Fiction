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
public class TestaCroce extends Gioco {

	public TestaCroce(InputStream in, PrintStream out) {
		super(in, out);
		
		super.registerCommand(new ConsoleCommand("testa") {
			
			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if(flipCoin()) {
					endGame(true);
				}
				else {
					endGame(false);
				}
			}
			
			@Override
			public String description() {
				return "Scegli testa.";
			}
		});
		
		super.registerCommand(new ConsoleCommand("croce") {
			
			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if(!flipCoin()) {
					endGame(true);
				}
				else {
					endGame(false);
				}
			}
			
			@Override
			public String description() {
				return "Scegli croce.";
			}
		});
	}
	
	@Override
	protected String consolePrompt() {
		return "> ";
	}
	
	private boolean flipCoin() {
		Random r = new Random();
		return r.nextBoolean();
	}
	
	public static void main(String[] args) {
		TestaCroce coin = new TestaCroce(System.in, System.out);
		coin.run();
	}

}
