package it.univpm.deit.semedia;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * DBinCommand class is meant to be subclassed and registered to the DBin console with the  DBinConsole.registerCommand method
 * A DBin command specifies a name in the constructor, a description and might provide usage instructions in case parameters are used.
 * Implementation must be given for the run method, which provides a list of parsed arguments
 * @author Acer
 *
 */
public abstract class ConsoleCommand {
	
	String name;
	
	public ConsoleCommand(String name) {
		this.name = name;
	}
	
	/**
	 * If the command accepts parameters, they should be explained here
	 * e.g. return "[filename] \n\nfilename=name of the file to be bla bla."
	 * @return
	 */
	public String getUsage() {
		return null;
	}
	
	/**
	 * Implementing classes must put the action code here. 
	 * Arguments are provided as parsed basic types (boolean, integer, float or string) 
	 * in the arg parameter, types are given in the types parameter, input and output 
	 * stream and writer are provided to allow complex interactive commands
	 * 
	 * @param args
	 * @param types
	 * @param in
	 * @param out
	 */
	public abstract void run(Object[] args, Class[] types, InputStream in,PrintStream out);
	
	/**
	 * The entry in the HELP command
	 * @return
	 */
	public abstract String description();
	
	public final String getName() {
		return name;
	}
}