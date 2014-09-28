package it.itido.quinta.informatici.HolyBorio.gameclasses.giochi;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.univpm.deit.semedia.ConsoleCommand;
import it.univpm.deit.semedia.GenericConsole;
import it.univpm.deit.semedia.gameclasses.rooms.Door;

public class ConnectedWords extends GenericConsole{

	private final File xmlFile = new File(ConnectedWords.class.getResource("/resources/words.xml").getFile());
	private ArrayList<LinkedHashMap<String, String>> words = new ArrayList<LinkedHashMap<String, String>>();

	private String lastSyllabe = null;
	
	private Timer timer;
	private TimerTask endTurn;
	private static final long turnTime = 30000;

	public ConnectedWords(InputStream in, PrintStream out) {
		super(in, out);
		
		timer = new Timer();
		endTurn = new TimerTask() {

			@Override
			public void run() {
				out.println("Tempo scaduto.");
				// FIXME: IL THREAD CESSA SOLO DOPO AVER SCRITTO QUALCOSA
				timer.cancel();
				ConnectedWords.this.executeLine("exit");
//				System.exit(0);
			}
			
		};
		
		ConsoleCommand say = new ConsoleCommand("say") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if(args.length == 0) {
					out.println("D� qualcosa!");
				}

				if(args.length == 1) {
					String word = (String) args[0];
					if(startsWhitLastSyllabe(word)) {
						if(contains(words, word)) {
							// Parola esatta
							timer.cancel();
							
							String[] syllabe = Door.getKeyByValue(removeGroup(words, word), word).split("-");
							String lastSyllabe = getLastSyllabe(syllabe);
							ConnectedWords.this.lastSyllabe = lastSyllabe;
							out.println(word);
							timer = new Timer();
							
							timer.schedule(endTurn, turnTime);
						}
						else {
							out.println("Non puoi dire quella parola.");
						}
					}
					else {
						out.println("Devi dire una parola che inizia con \"" + lastSyllabe + "\".");
					}
				}
			}

			@Override
			public String description() {
				return "Dici la parola specificata (deve iniziare con l'ultima sillaba della parola precedente).";
			}
			
			public String getUsage() {
				return "WORD\n\n"
						+ "WORD: la parola da dire da collegare alla precedente";
			}
		};

		super.registerCommand(say);
		super.setDefaultCommand(say);

		init();
		
		timer.schedule(endTurn, turnTime);
		
	}

	private void init() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("words");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				NodeList list = nNode.getChildNodes();

				for(int j = 0; j < list.getLength(); j++) {

					LinkedHashMap<String, String> temp = new LinkedHashMap<String, String>();
					
					Node node = list.item(j);

					if(node.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) node;

						int k = 0;
						try {
							while(true) {

								String sillabe = eElement.getElementsByTagName("parola").item(k).getTextContent();
								String unita = uniteStringArray(sillabe.split("-"));

								temp.put(sillabe, unita);
								k++;
							}
						} catch(NullPointerException ex) {
							words.add(temp);
						}


					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static String uniteStringArray(String[] str) {
		String finale = "";

		for (int i = 0; i < str.length; i++) {
			finale += str[i];
		}

		return finale;
	}
	
	private boolean startsWhitLastSyllabe(String word) {
		return lastSyllabe == null || word.indexOf(lastSyllabe) == 0;
	}
	
	private static String getLastSyllabe(String[] str) {
		return str[str.length - 1];
	}
	
	private static boolean contains(ArrayList<LinkedHashMap<String, String>> list, String value) {
		boolean present = false;
		
		for (Iterator<LinkedHashMap<String, String>> iter = list.iterator(); iter.hasNext();) {
			if(iter.next().containsValue(value)) {
				present = true;
				break;
			}
		}
		
		return present;
	}
	
	private static LinkedHashMap<String, String> removeGroup(ArrayList<LinkedHashMap<String, String>> list, String value) {
		LinkedHashMap<String, String> removed = new LinkedHashMap<String, String>();
		
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap<String, String> map = list.get(i);
			if(map.containsValue(value)) {
				list.remove(i);
				removed.put(Door.getKeyByValue(map, value), map.get(Door.getKeyByValue(map, value)));
			}
		}
		
		return removed;
	}

	public static void main(String[] args) {
		ConnectedWords game = new ConnectedWords(System.in, System.out);
//		System.out.println(game.words.toString());
		game.run();
	}

}
