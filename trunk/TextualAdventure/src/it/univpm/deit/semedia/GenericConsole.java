package it.univpm.deit.semedia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
/**
 * This class allows execution of commands via command line interface
 * use RegisterCommand to register a ConsoleCommand. The only commands supported right away are "Help" and "Exit"
 * ConsoleCommands provide the help content for their respective needs.
 * @author Acer
 *
 */
public class GenericConsole implements Runnable {

	private InputStream in;
	private PrintStream out;
	private boolean consoleRunning;
	private final HashMap commands = new HashMap();
	private ConsoleCommand defaultCommand;

	/**
	 * Installs a DBinCommand for execution in this console.
	 * @param command
	 */
	public void registerCommand(ConsoleCommand command) {
		commands.put(command.getName(),command);
	}

	public GenericConsole(InputStream in, PrintStream out) {
		this.in = in;
		this.out = out;

		registerCommand(new ConsoleCommand("help") {
			public String description() {
				return "Questa schermata - HELP \"COMMAND\" descrive il comando.";
			}
			public String getUsage() {
				return "[commandname]\n\ncommandname = il nome del comando su cui vuoi spiegazioni.";
			}
			public void run(Object[] args, Class[] types,InputStream in,PrintStream out) {

				if (args.length == 1) {
					ConsoleCommand command = (ConsoleCommand)commands.get(args[0]);
					if(command == null) {
						out.println("Nessun comando \"" + args[0]+"\"");
					}
					else {
						String usage = command.getUsage();
						if(usage == null) {
							usage = command.description();
						}
						out.println("Uso: " + command.getName().toUpperCase() + " " + usage);
					}
					return;
				}
				// no parameter HELP, list all the function names
				MessageFormat form = new MessageFormat("{0}        {1}");
				// TAB doesnt work??
				ConsoleCommand[] sorted = (ConsoleCommand[])commands.values().toArray(new ConsoleCommand[] {});
				Arrays.sort(sorted,new Comparator () {
					public int compare(Object arg0, Object arg1) {
						return ((ConsoleCommand)arg0).getName().compareTo(
								((ConsoleCommand)arg1).getName());
					}});
				for (int i = 0; i < sorted.length; i++) {
					out.println(form.format(new Object[] {sorted[i].getName().toUpperCase(),
							sorted[i].description()}));
				}
			}	
		});	
		registerCommand(new ConsoleCommand("exit") {
			public String description() {
				return "Chiude la console.";
			}
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				consoleRunning = false;
			}
		});
	}


	//	Parse a command line into a list of tokens
	private final String[] parse( String line ) {
		StringTokenizer st = new StringTokenizer( line );
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			tokens.add(token);
		}
		return tokens.toArray(new String[0]);
	}

	// Narrow a type from String to the
	// narrowest possible type 
	private final Object narrow(String argstring) {
		// Try integer
		try {
			return Integer.valueOf(argstring);
		} catch(NumberFormatException nfe) {
		}
		// Try double
		try {
			return Double.valueOf(argstring);
		} catch(NumberFormatException nfe) {
		}
		// Try boolean
		if (argstring.equalsIgnoreCase("true")) {
			return Boolean.TRUE;
		}
		else if (argstring.equalsIgnoreCase("false")) {
			return Boolean.FALSE;
		}

		// Give up -- it's a string
		return argstring;
	}

	// Narrow the the arguments
	private final Object[] narrow(String argstrings[], int startIndex) {
		Object narrowed[] =	new Object[argstrings.length - startIndex];

		for (int i=0; i<narrowed.length; ++i) {
			narrowed[i] = narrow(argstrings[startIndex + i]);
		}

		return narrowed;
	}

	// Get an array of the types of the give
	// array of objects
	private final Class[] getTypes( Object objs[] ) {
		Class types[] = new Class[objs.length];

		for (int i = 0; i < objs.length; ++i) {
			types[i] = objs[i].getClass();

			// Convert wrapper types (like Double)
			// to primitive types (like double)

			if(types[i] == Double.class) {
				types[i] = double.class;
			}
			if(types[i] == Integer.class) {
				types[i] = int.class;
			}
		}

		return types;
	}
	/**
	 * Implementing classes should override to provide a welcome message for the specific console
	 * @return
	 */
	protected String welcomeMsg() {
		return "";
	}

	public void run() {
		BufferedReader keyboardInput;
		keyboardInput = new BufferedReader(new InputStreamReader(in));
		String newLine = null;
		out.println(welcomeMsg());
		consoleRunning = true; 
		while(consoleRunning) {
			out.print(consolePrompt());
			out.flush();
			try {
				newLine = keyboardInput.readLine();
			} catch (IOException e) {
				e.printStackTrace(out);
				consoleRunning = false;
			}
			executeLine(newLine);			
		} 	
	}

	/**
	 * Directly executes a command line
	 * @param newLine
	 */
	protected void executeLine(String newLine) {

		String[] line = parse(newLine);
		// Command line must be at least two tokens long
		if (line.length < 1) {
			return;
		}

		// The first token is the command name
		String commandName = line[0];
		// Narrow the arguments
		Object args[] = narrow(line, 1);
		Class types[] = getTypes(args);
		ConsoleCommand command = (ConsoleCommand)commands.get(commandName.toLowerCase());
		try {
			if(command != null) {
				command.run(args, types, in, out);				
			}
			else if(defaultCommand != null) {
				// default command parameters start at 0 of course so reparsing is needed
				args = narrow(line, 0);
				types = getTypes(args);
				defaultCommand.run(args, types, in, out);
			}
			else {
				out.println("Nessun comando \"" + commandName + "\"");
			}
		} catch (Throwable t) {
			out.println("Il comando ha generato il seguente errore:\n" + t.toString());
		}
	}

	/**
	 * Implementing classes should override to provide a specific console prompt e.g. ">";
	 * @return
	 */
	protected String consolePrompt() {
		return "";
	}

	/**
	 * This command will be executed if no command matches the entered line.  
	 * @return
	 */
	public ConsoleCommand getDefaultCommand() {
		return defaultCommand;
	}

	public void setDefaultCommand(ConsoleCommand defaultCommand) {
		this.defaultCommand = defaultCommand;
	}
}
