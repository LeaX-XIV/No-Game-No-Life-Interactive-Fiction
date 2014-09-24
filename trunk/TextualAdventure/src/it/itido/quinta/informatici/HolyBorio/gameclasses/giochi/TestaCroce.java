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
public class TestaCroce extends GenericConsole {

	public TestaCroce(InputStream in, PrintStream out) {
		super(in, out);
		
		super.registerCommand(new ConsoleCommand("head") {
			
			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if(flipCoin()) {
					out.println("You won!");
				}
				else {
					out.println("You lost :(");
				}
				executeLine("exit");
			}
			
			@Override
			public String description() {
				return "Choose head";
			}
		});
		
		super.registerCommand(new ConsoleCommand("tails") {
			
			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if(!flipCoin()) {
					out.println("You won!");
				}
				else {
					out.println("You lost :(");
				}
				executeLine("exit");
			}
			
			@Override
			public String description() {
				return "Choose tails";
			}
		});
		
		
	}
	
	@Override
	protected String welcomeMsg() {
		return "";
	}
	
	@Override
	protected String consolePrompt() {
		return "Choose head or tails > ";
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
