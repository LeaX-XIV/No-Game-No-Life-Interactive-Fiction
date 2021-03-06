package it.univpm.deit.semedia;

import it.itido.quinta.informatici.HolyBorio.gameclasses.endgame.MusicPlayer;
import it.itido.quinta.informatici.HolyBorio.gameclasses.endgame.ScrollableText;
import it.itido.quinta.informatici.HolyBorio.gameclasses.giochi.ConnectedWords;
import it.itido.quinta.informatici.HolyBorio.gameclasses.giochi.MorraCinese;
import it.itido.quinta.informatici.HolyBorio.gameclasses.giochi.TestaCroce;
import it.univpm.deit.semedia.gameclasses.CollectableItem;
import it.univpm.deit.semedia.gameclasses.ContainerImpl;
import it.univpm.deit.semedia.gameclasses.GameObject;
import it.univpm.deit.semedia.gameclasses.IContainer;
import it.univpm.deit.semedia.gameclasses.objects.Libro;
import it.univpm.deit.semedia.gameclasses.objects.SecretKey;
import it.univpm.deit.semedia.gameclasses.persons.Person;
import it.univpm.deit.semedia.gameclasses.rooms.Door;
import it.univpm.deit.semedia.gameclasses.rooms.Lock;
import it.univpm.deit.semedia.gameclasses.rooms.Room;
import it.univpm.deit.semedia.gameclasses.rooms.Trigger;
import it.univpm.deit.semedia.gameclasses.rooms.TriggerMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Game extends GenericConsole implements Serializable {

	private static Person mc;
	private static Game game;
	private static ArrayList<Room> world;

	public Game(InputStream in, PrintStream out) {
		super(in, out);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		game = new Game(System.in, System.out);
		game.registerCommand(new ConsoleCommand("start") {
			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				
				mc = new Person("[]", 100);

				//				Room room1 = new Room("the white room");
				//				room1.setDescription("Its a white room, with black curtains, near the station.");
				//				Room room2 = new Room("a corridor");
				//				Room room3 = new Room("the armery");
				//
				//				ContainerImpl closet = new ContainerImpl("closet");
				//				GameObject dagger = new Weapon("dagger", 25);
				//				dagger.setDescription("old, but likely to work");
				//				closet.add(dagger);
				//				room3.add(closet);
				//
				//				Room room4 = new Room("King's bedroom");
				//				Room room5 = new Room("Queen's bedroom");
				//				GameObject mirror = new GameObject("mirror") {
				//					@Override
				//					public String use(Person who) {
				//						return "you see " + who.getDescription() + ". Hey he's reversed!";
				//					}
				//				};
				//				mirror.setDescription("A gold encrusted mirror, unfortunately the glass is broken");
				//				room5.add(mirror);		
				//				room5.add(new Banana());
				//				room5.add(new Banana());
				
				
				// ROOMS
				
				Room mountainPass = new Room("Sentiero Montano");
				mountainPass.setDescription("Il sentiero in cui ti ritrovi dopo essere stato trasportato in un mondo\nfantastico.");

				Room inn = new Room("Locanda");
				inn.setDescription("Una locanda fuori citt�.");
				Room elchea = new Room("Piazza di Elchea") {

					@Override
					public String getDescription() {

						DateFormat ora = new SimpleDateFormat("hh:mm"); 				

						return super.description + ora.format(new Date()) + "\n" + getContentDescription() ;
					}
				};		
				elchea.setDescription("La piazza di Elchea, l'ultimo territorio rimasto in mano agli umani.\n"
						+ "Puoi scorgere l'orologio situato sulla torre del palazzo. Segna le ");
				Room elcheaStreets = new Room("Vie di Elchea");
				elcheaStreets.setDescription("Vie che attraversano Elchea.");
				Room elcheaPalace = new Room("Palazzo di Elchea");
				elcheaPalace.setDescription("Il palazzo dove risiede il re degli umani.");
				Room kingRoom = new Room("Stanza del re");
				kingRoom.setDescription("Camera da letto del re di Elchea.");
				Room secretRoom = new Room("???");
				secretRoom.setDescription("Una stanza misteriosa nascosta dietro la libreria nella camera del re.");
				Room library = new Room("Biblioteca Nazionale di Elchea");
				library.setDescription("La biblioteca pi\u00f9 grande di Elchea.\nContiene migliaia di libri provenienti da altri paesi.");
				Room easternUnion = new Room("Federazione dell'Est");
				easternUnion.setDescription("Stato confinante con Elchea, controllato dai Werebeast.");
				Room throneHall = new Room("Sala del trono");
				throneHall.setDescription("La sala del trono del palazzo");

				// DOORS
				
				Door d1 = new Door(true);
				Door d2 = new Door(true);
				Door d3 = new Door(true);
				Door d4 = new Door(true);
				Door d5 = new Door(true);
				Door d6 = new Door(true);
				Door d7 = new Door(new Lock(new ArrayList<Byte>(Arrays.asList(new Byte[]{31, (byte) 192, 116, 24}))));
				Door d8 = new Door();
				Door d9 = new Door(true);

				d1.addLink("ovest", "sud", mountainPass, inn);
				d2.addLink("nord", "sud", inn, elchea);
				d3.addLink("nord", "sud", elchea, elcheaStreets);
				d4.addLink("est", "ovest", elcheaStreets, library);
				d5.addLink("nord", "sud", elcheaStreets, elcheaPalace);
				d6.addLink("ovest", "est", elcheaPalace, kingRoom);
				d7.addLink("sud", "nord", kingRoom, secretRoom);
				d8.addLink("ovest", "est", elchea, easternUnion);
				d9.addLink("ovest", "est", throneHall, elcheaPalace);

				world = new ArrayList<Room>();
				world.add(mountainPass);
				world.add(inn);
				world.add(elchea);
				world.add(elcheaStreets);
				world.add(library);
				world.add(elcheaPalace);
				world.add(kingRoom);
				world.add(secretRoom);
				world.add(easternUnion);
				world.add(throneHall);
				
				// ITEMS

				CollectableItem rock;
				GameObject throne;
				GameObject pigeons;
				GameObject bed;
				ContainerImpl leftBookshelf;
					Libro historyBook;
				ContainerImpl rightBookshelf;
					Libro racesBook;
				SecretKey secretKey;
				GameObject book;		

				throne = new GameObject("trono");
				pigeons = new GameObject("piccioni");
				rock = new CollectableItem("sasso") {
					@Override
					public String use(Person who, GameObject target) {
						if(target == pigeons) {
							mc.getContainedIn().remove(pigeons);
							getContainedIn().remove(this);
							new MusicPlayer(getClass().getResource("/resources/audio/pigeons.mp3").getFile()).start();
							return "I piccioni sono volati via.";
						}
						else {
							return "Non � consigliabile lanciare un sasso a " + target;
						}
					}
				};
				bed = new GameObject("letto") {
					@Override
					public String use(Person who) {
						out.println("Ti addormenti sul letto.");
						new MusicPlayer(getClass().getResource("/resources/audio/Bed.mp3").getFile()).run();
						return "Al tuo risveglio, sei caduto dal lato sinistro.";
					}
				};
				leftBookshelf = new ContainerImpl("libreria_sinistra");
				historyBook = new Libro("libro_storia", ScrollableText.readFromXml("historyBook")) {
					
				};
				rightBookshelf = new ContainerImpl("libreria_destra");
				racesBook = new Libro("libro_razze", ScrollableText.readFromXml("racesBook"));
				secretKey = new SecretKey("chiave", new ArrayList<Byte>(Arrays.asList(new Byte[]{31, (byte) 192, 116, 24})), leftBookshelf);
				book = new GameObject("libro") {
					@Override
					public String use(Person who) {
						if(d8.isOpen()) {	// piazza di elchea <-> federazione dell'est
							return "Il libro scritto dal precedente re che ti ha rivelato l'ubicazione della\nFederazione dell'Est.";
						}
						else {
							d8.open();
							return "Questo libro contiene le informazioni sull'ubicazione della Federazione\ndell'Est.\nA quanto pare si trova a Ovest.";
						}
					}
				};

				rock.setDescription("Un sasso della grandezza di una gomma.");
				throne.setDescription("Il trono sul quale puoi sederti in funzione di re.");
				pigeons.setDescription("Dei tranquillissimi piccioni sopra un tetto.");
				bed.setDescription("Il letto a baldacchino del precendte re. Ha un aria meastosa.");
				leftBookshelf.setDescription("La libreria che si trova alla sinistra dell'entrata.");
				historyBook.setDescription("Un libro che parla della storia di Disboard.");
				rightBookshelf.setDescription("La libreria che si trova alla destra dell'entrata.");
				racesBook.setDescription("Un libro che tratta la suddivisione in razze di questo mondo.");
				secretKey.setDescription("Una chiave con una dentatura gi� vista prima.");
				book.setDescription("Un libro misterioso ricoperto di ragnatele.");

				mountainPass.add(rock);
				throneHall.add(throne);
				elcheaStreets.add(pigeons);
				kingRoom.add(bed);
				leftBookshelf.add(historyBook);
				kingRoom.add(leftBookshelf);
				rightBookshelf.add(racesBook);
				kingRoom.add(rightBookshelf);
				secretRoom.add(book);
				
				
				// TRIGGERS
				
				Trigger prologue = new Trigger(mountainPass);
				prologue.init(1, ScrollableText.readFromXml("prologue"), null, null);
				prologue.setStartMusic(new MusicPlayer(MusicPlayer.class.getResource("/resources/audio/Prologue.mp3").getFile()));
				
				Trigger innTrigger = new Trigger(inn);
				innTrigger.init(1, ScrollableText.readFromXml("innEvent"), new MorraCinese(in, out), new ScrollableText(ScrollableText.readFromXml("janKenWin") + "|" + ScrollableText.readFromXml("janKenLost")));
				
				Trigger libraryEvent = new Trigger(library);
				libraryEvent.init(0, ScrollableText.readFromXml("libraryEvent"), new ConnectedWords(in, out), new ScrollableText(ScrollableText.readFromXml("connectedWordsWin") + "|" + ScrollableText.readFromXml("connectedWordsLost")));
				libraryEvent.setMethod(TriggerMethods.class, "libraryEvent", mc, secretKey);
				
				Trigger throneEvent = new Trigger(throneHall);
				throneEvent.init(1, ScrollableText.readFromXml("throneEvent"), null, null);
				throneEvent.setMethod(TriggerMethods.class, "throneEvent", libraryEvent);
				throneEvent.setFatal(false);
				
				Trigger federationTrigger = new Trigger(easternUnion);
				federationTrigger.init(1, ScrollableText.readFromXml("easternUnionEvent"), new TestaCroce(in, out), new ScrollableText(ScrollableText.readFromXml("headTailWin") + "|" + ScrollableText.readFromXml("headTailLost")));
				federationTrigger.setMethod(TriggerMethods.class, "federationEvent");
				federationTrigger.setLoseMusic(new MusicPlayer(MusicPlayer.class.getResource("/resources/audio/HeadTailLost.mp3").getFile()));

				world.get(0).enter(mc);
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
						"OBJECTNAME = Il nome dell'oggetto da guardare.\nDa notare che " +
						"guardando una stanza, verrano mostrati i mobili, ma non ci� che � contenuto in essi.";
			}

			@Override
			public String description() {
				return "Guarda intorno o a uno specifico oggetto.";
			}
		});

		game.registerCommand(new ConsoleCommand("get") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if (args.length == 1) {
					// first see if there is such object in the room the hero is
					GameObject object = mc.getContainedIn().objectByName(args[0].toString());
					if (object != null) {
						if(object instanceof CollectableItem) {

							// if so place it in the hem.. hero
							object.getContainedIn().moveContainedTo(object, mc);
							out.println("Metti " + object.getName() + " nella tua borsa.");
						}
						else {
							out.println("L'oggetto " + args[0].toString() + " non pu� essere messo nella borsa.");
						}
					}
					else {
						out.println("Non riesci a trovare l'oggetto " + args[0]);
					}
				}
				
				if(args.length == 2) {
					GameObject object = mc.getContainedIn().objectByName((String) args[1]);
					if(object != null) {
						if(object instanceof ContainerImpl) {
							ContainerImpl container = (ContainerImpl) object;
							GameObject target = container.objectByNameRecursive((String) args[0]);
							if(target != null) {
								if(target instanceof CollectableItem) {
									target.getContainedIn().moveContainedTo(target, mc);
									out.println("Metti " + target.getName() + " nella tua borsa.");
								}
								else {
									out.println("L'oggetto " + args[0].toString() + " non pu� essere messo nella borsa.");
								}
							}
							else {
								out.println("Non riesci a trovare l'oggetto " + args[0]);
							}
						}
						else {
							out.println("L'oggetto " + args[1] + " non � un contenitore.");
						}
					}
					else {
						out.println("Non riesci a trovare l'oggetto " + args[1]);
					}
				}
			}

			@Override
			public String description() {
				return "Prende un oggetto e lo mette nella borsa.";
			}
			@Override
			public String getUsage() {
				return "OGGETTO [CONTENITORE]\n\n"
						+ "OGGETTO = l'oggetto da prendere\n"
						+ "CONTENITORE = il contenitore dell'oggetto (es. armadio)";
			}
		});

		game.registerCommand(new ConsoleCommand("drop") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {				
				if(args.length == 0) {
					out.println("Devi lasciar cadere qualcosa");
				}
				else if(args.length == 1) {
					String objectName = args[0].toString();
					GameObject object = mc.objectByName(objectName);
					if(object != null) {
						object.getContainedIn().moveContainedTo(object, mc.getContainedIn());
						out.println("Hai lasciato cadere " + args[0]);
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
				if (args.length == 1) {
					IContainer container = mc.getContainedIn();
					if (container instanceof Room) {
						Room room = ((Room)container);
						HashMap<String, Room> doors = room.getDoors();
						if (doors.containsKey(args[0].toString())) {
							//moves mc to the room with the specified name
							room.personExits(mc, args[0].toString());
						}
						else {
							out.println("Uscita inesistente.");
						}
					}
					else {
						out.println("Numero parametri errato.");
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
				return "OGGETTO [BERSAGLIO]\n\n" +
						"OGGETTO = l'oggetto da usare, deve essere nella borsa o nella stessa stanza\n" +
						"BERSAGLIO = alcuni oggetti richiedono obiettivi(chiavi)";
			}
			@Override
			public String description() {
				return "Usa un oggetto da solo o su un'altro.";
			}

		});

		/*
		game.registerCommand(new ConsoleCommand("save") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if(args.length == 0) {

					JFileChooser fc = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("NGNL Save (*.ngnl)", "ngnl");
					fc.setFileFilter(filter);
					int returnVal = fc.showSaveDialog(new JFrame());
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						String path = fc.getSelectedFile().getAbsolutePath();
						FileNameExtensionFilter f = (FileNameExtensionFilter) fc.getFileFilter();
						String[] s = f.getExtensions();
						if(!path.endsWith(s[0])){
							path += "." + s[0];
						}
						game.executeLine("save " + path);
					}
				}

				if(args.length == 1) {
					String path = (String) args[0];

					if(path.toLowerCase().endsWith(".ngnl")) {
						File f = new File(path);
						ObjectOutputStream stream = null;
						try {
							stream = new ObjectOutputStream(new FileOutputStream(f));
							// FIXME: NON SALVA NIENTE (FORSE)
							stream.writeObject(world);
							stream.writeObject(mc);
						} catch (IOException e) {
						}finally {
							try {
								if(stream != null) {
									stream.close();
								}
							} catch (IOException e) {
							}
						}

					}
					else {
						out.println("Il file di destinazione ha qualche problemino.");
					}
				}

			}

			public String getUsage() {
				return "[PATH]\n\n"
						+ "PATH = il percorso del file da salvare";
			}

			@Override
			public String description() {
				return "Salva i progressi del gioco.";
			}
		});

		game.registerCommand(new ConsoleCommand("load") {

			@SuppressWarnings("unchecked")
			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if(args.length == 0) {
					JFileChooser choose = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("NGNL Save (*.ngnl)", "ngnl");
					choose.setFileFilter(filter);
					int returnVal = choose.showOpenDialog(new JFrame());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						String path = choose.getSelectedFile().getAbsolutePath();
						game.executeLine("load " + path);
					}
				}

				if(args.length == 1) {
					String path = (String) args[0];
					if(path.toLowerCase().endsWith(".ngnl")) {
						ObjectInputStream stream = null;
						try {
							stream = new ObjectInputStream(new FileInputStream(path));
							// FIXME: SI BLOCCA QUI, NotSerializableException
							world = (ArrayList<Room>) stream.readObject();
							mc = (Person) stream.readObject();
						} catch (FileNotFoundException e) {
							out.println("I nostri gnometti da giardino non riescono a trovare il file.");
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}catch(Exception e) {
							e.printStackTrace();
						}finally {
							if(stream != null) {
								try {
									stream.close();
								} catch (IOException e) {
								}
							}
						}
					}
				}

			}

			@Override
			public String description() {
				return "Riprende il gioco da un punto salvato.";
			}

			public String getUsage() {
				return "[PATH]\n\n"
						+ "PATH = il percorso del file da caricare";
			}
		});
		//*/

		game.run();

	}

	@Override
	protected String welcomeMsg() {
		return "Usa HELP per leggere i comandi.";
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
