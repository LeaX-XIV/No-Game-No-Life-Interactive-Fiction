package it.univpm.deit.semedia;

import it.univpm.deit.semedia.gameclasses.ContainerImpl;
import it.univpm.deit.semedia.gameclasses.GameObject;
import it.univpm.deit.semedia.gameclasses.IContainer;
import it.univpm.deit.semedia.gameclasses.objects.Banana;
import it.univpm.deit.semedia.gameclasses.objects.Weapon;
import it.univpm.deit.semedia.gameclasses.persons.Person;
import it.univpm.deit.semedia.gameclasses.rooms.Room;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import com.sun.jmx.snmp.Timestamp;

public class Game extends GenericConsole implements Serializable {

	static Person mc;
	static Game game;

	public Game(InputStream in, PrintStream out) {
		super(in, out);
		
		// TODO: INSERIRE FORZIERE
		// FIXME: CORREGGERE GET YOU CON TANTE GERARCHIE
		mc = new Person("Sora", 100);
		mc.setDescription("18 anni, vergine, introverso, NEET, dipendente dai videogiochi");

		Room mountainPass = new Room("Sentiero Montano");
		mountainPass.setDescription("Il sentiero in cui ti ritrovi dopo essere stato trasportato in un mondo fantastico.");
		
		ContainerImpl closet = new ContainerImpl("closet");
		GameObject dagger = new Weapon("dagger", 25);
		dagger.setDescription("old, but likely to work");
		closet.add(dagger);
		mountainPass.add(closet);
		
		Room inn = new Room("Locanda");
		// XXX:
		inn.setDescription("Una locanda fuori citt\u00e0.");
		Room elchea = new Room("Piazza di Elchea");
		elchea.setDescription("La piazza di Elchea, l'ultimo territorio rimasto in mano agli umani.\n"
				// TODO: INSERIRE OROLOGIO
				// TODO: X LIPPUZ: FAI STAMPARE L'ORA
/*			+ "Puoi scorgere l'orologio situato sulla torre del palazzo. Segna le " +*/ );
		Room elcheaStreets = new Room("Vie di Elchea");
		elcheaStreets.setDescription("Vie che attraversano Elchea.");
		Room elcheaPalace = new Room("Palazzo di Elchea");
		elcheaPalace.setDescription("Il palazzo dove risiede il re degli umani.");
		Room kingRoom = new Room("Stanza del re");
		kingRoom.setDescription("Camera da letto del re di Elchea.");
		Room secretRoom = new Room("???");
		secretRoom.setDescription("Una stanza misteriosa nascosta dietro la libreria nella camera del re.");
		Room library = new Room("Biblioteca Nazionale di Elchea");
		// XXX:
		library.setDescription("La biblioteca pi\u00f9 grande di Elchea. Contiene migliaia di libri provenienti da altri paesi.");
		Room easternUnion = new Room("Federazione dell'Est");
		easternUnion.setDescription("Stato confinante con Elchea, controllato dai Werebeast.");

		mountainPass.addDoor("nord", inn);
		inn.addDoor("sud", mountainPass);
		inn.addDoor("nord", elchea);
		elchea.addDoor("sud", inn);
		elchea.addDoor("nord", elcheaStreets);
		elcheaStreets.addDoor("sud", elchea);
		elcheaStreets.addDoor("est", library);
		library.addDoor("ovest", elcheaStreets);
		elcheaStreets.addDoor("nord", elcheaPalace);
		elcheaPalace.addDoor("sud", elcheaStreets);
		elcheaPalace.addDoor("ovest", kingRoom);
		kingRoom.addDoor("est", elcheaPalace);
		//		XXX: kingRoom.addDoor("sud", secretRoom);
		secretRoom.addDoor("nord", kingRoom);
		//		XXX: elchea.addDoor("ovest", easternUnion);
		easternUnion.addDoor("est", elchea);

		mountainPass.add(mc);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		game = new Game(System.in, System.out);
		game.registerCommand(new ConsoleCommand("startnew") {
			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {

				Room room1 = new Room("the white room");
				room1.setDescription("Its a white room, with black curtains, near the station.");
				Room room2 = new Room("a corridor");
				room1.addDoor("north", room2);
				room2.addDoor("south", room1);

				Room room3 = new Room("the armery");
				room2.addDoor("north", room3);
				room3.addDoor("south", room2);

				ContainerImpl closet = new ContainerImpl("closet");
				GameObject dagger = new Weapon("dagger", 25);
				dagger.setDescription("old, but likely to work");
				closet.add(dagger);
				room3.add(closet);

				Room room4 = new Room("King's bedroom");
				Room room5 = new Room("Queen's bedroom");
				room3.addDoor("east", room4);
				room4.addDoor("west", room3);
				room3.addDoor("west", room5);
				room5.addDoor("east", room3);
				GameObject mirror = new GameObject("mirror") {
					@Override
					public String use(Person who) {
						return "you see " + who.getDescription() + ". Hey he's reversed!";
					}
				};
				mirror.setDescription("A gold encrusted mirror, unfortunately the glass is broken");
				room5.add(mirror);		
				room5.add(new Banana());
				room5.add(new Banana());



				out.println("New game started!\n" +
						"\n " +
						"Ops, it happened again. You try to recall the last time you promised yourself " +
						"no more \"happy mushrooms\" but you miserably fail. You wake up and you're somewhere you dont know but somehow not so unfamiliar.\n");

				// the look command is executed
				game.executeLine("look");
			}

			@Override
			public String description() {
				return "Comincia un nuovo gioco.";
			}

		});
		game.registerCommand(new ConsoleCommand("bag") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				out.println(mc.getContentDescription());
			}

			@Override
			public String description() {
				return "Mostra il contenuto della borsa.";
			}

		});
		game.registerCommand(new ConsoleCommand("look") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				// Lists the container description and the contained objects
				if (args.length == 0) {
					IContainer container = mc.getContainedIn();
					out.println(((GameObject)container).getDescription());
				}
				// one might look at either an object that is in the room or one in the inventory
				if (args.length == 1) {
					GameObject object = mc.getContainedIn().objectByNameRecursive(args[0].toString());
					if (object != null) {
						out.println(object.getDescription()+ "\n\n");
					}
					else {
						out.println("Nessun oggetto \"" + args[0] + "\"");
					}
				}
			}
			@Override
			public String getUsage() {
				return "[OBJECTNAME] \n\n" +
						"OBJECTNAME (optional) = the name of the object to look at\n\nNote that " +
						"look will only show you the most direct objects, e.g. looking at a room wil tell you about its furniture but not the content of each of its pieces";
			}

			@Override
			public String description() {
				return "Guarda intorno o a uno specifico oggetto.";
			}

		});
		
		game.registerCommand(new ConsoleCommand("get") {

			// FIXME: CAMBIARE GameObject IN Item
			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if (args.length == 1) {
					// first see if there is such object in the room the hero is
					GameObject object = mc.getContainedIn().objectByName(args[0].toString());
					if (object != null) {
						// if so place it in the hem.. hero
						object.getContainedIn().moveContainedTo(object, mc);
						out.println("Metti " + object + " nella tua borsa.");
					}
					else {
						out.println("Non riesci a trovare l'oggetto " + args[0]);
					}
				}
				if(args.length == 2) {
					GameObject object = mc.getContainedIn().objectByName((String) args[0]);
					if(object != null) {
						if(object instanceof ContainerImpl) {
							ContainerImpl container = (ContainerImpl) object;
							GameObject target = container.objectByNameRecursive((String) args[1]);
							if(target != null) {
								target.getContainedIn().moveContainedTo(target, mc);
								out.println("Metti " + target + " nella tua borsa.");
							}
							else {
								out.println("Non riesci a trovare l'oggetto " + args[1]);
							}
						}
						else {
							out.println("L'oggetto " + args[0] + " non è un contenitore.");
						}
					}
					else {
						out.println("Non riesci a trovare l'oggetto " + args[0]);
					}
				}
			}

			@Override
			public String description() {
				return "Prende un oggetto e lo mette nella borsa.";
			}
			@Override
			public String getUsage() {
				return "OBJECTNAME = l'oggetto da prendere";
			}
		});
		
		
		game.registerCommand(new ConsoleCommand("drop") {
			
			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				// TODO Auto-generated method stub
				
				if(args.length == 0) {
					out.println("Devi lasciar cadere qualcosa");
				}
				else if(args.length == 1) {
					String objectName = args[0].toString();
					GameObject object = mc.objectByName(objectName);
					if(object != null) {
						object.getContainedIn().remove(object);
					}
					else {
						out.println("Nessun oggetto " + objectName);
					}
				}
				else {
					out.println("Valore parametri errato.");
				}
				
				
			}
			
			@Override
			public String description() {
				return "Rimuove dalla borsa l'oggetto specificato.";
			}
		});
		
		game.registerCommand(new ConsoleCommand("go"){

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if (args.length==1) {
					IContainer container = mc.getContainedIn();
					if (container instanceof Room) {
						Room room = ((Room)container);
						HashMap<String, Room> doors = room.getDoors();
						if (doors.containsKey(args[0].toString())) {
							//moves ourhere to the room with the specified name
							room.moveContainedTo(mc,(IContainer)doors.get(args[0].toString()));
						}
						else {
							out.println("Uscita inesistente.");
						}
					}
					else {
						out.println("wrong parameter count");
					}
				}
			}

			@Override
			public String description() {
				return "Attraversa l'uscita specificata.";
			}
		});
		game.registerCommand(new ConsoleCommand("use") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if(args.length == 0) {
					out.println("Cosa?");
				} 
				// one might look at either an object that is in the room or one in the inventory
				if(args.length > 0) {
					GameObject object = mc.getContainedIn().objectByNameRecursive(args[0].toString());
					if(object != null) {
						if(args.length == 1) {
							out.println(object.use(mc));
						}
						else {
							GameObject target = mc.getContainedIn().objectByNameRecursive(args[1].toString());
							if(target != null) {
								out.println(object.use(mc, target));
							}
							else {
								out.println("Nessun oggetto " + args[1].toString());
							}
						} 
					}
					else {
						out.println("Nessun oggetto " + args[0].toString());
					}
				}
			}
			@Override
			public String getUsage() {
				return "OBJECT [TARGET]\n\n" +
						"OBJECT = l'oggetto da usare, deve essere nella borsa o nella stessa stanza\n" +
						"TARGET = alcuni oggetti richiedono obiettivi(chiavi)";
			}
			@Override
			public String description() {
				return "Usa un oggetto da solo o su un'altro.";
			}

		});
		game.run();
	}
	
	// XXX: AGGIUNGERE COMANDI SAVE E LOAD

	@Override
	protected String welcomeMsg() {
		return "Welcome to the Interactive Fiction 2006 console" +
				"\n-- Textual adventures aren't dead, they just smell funny --" +
				"\n\n press HELP for help";
	}

	@Override
	protected String consolePrompt() {
		String herostring = "";
		/* if the game has started show the energy of ourhero */
		if (mc != null) {
			String doorlist = "";
			if (mc.getContainedIn() instanceof Room) {
				Iterator<String> dooriter = ((Room) mc.getContainedIn()).getDoors().keySet().iterator();
				while (dooriter.hasNext()) {
					doorlist += " " + dooriter.next() + " ";
				}
			}
			herostring = "|| " + mc.getContainedIn() + " --- " + "uscite: " + doorlist + "\n";
		}
		return herostring + "> ";
	}

}
