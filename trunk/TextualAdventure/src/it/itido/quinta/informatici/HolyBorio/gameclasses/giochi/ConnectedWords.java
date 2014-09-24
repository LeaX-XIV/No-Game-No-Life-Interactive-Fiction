package it.itido.quinta.informatici.HolyBorio.gameclasses.giochi;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.univpm.deit.semedia.ConsoleCommand;
import it.univpm.deit.semedia.GenericConsole;

public class ConnectedWords extends GenericConsole{

	private final File xmlFile = new File(ConnectedWords.class.getResource("/resources/words.xml").getFile());
	private HashMap<String, String> words = new HashMap<String, String>();


	private String lastSyllabe = null;

	public ConnectedWords(InputStream in, PrintStream out) {
		super(in, out);

		super.registerCommand(new ConsoleCommand("say") {

			@Override
			public void run(Object[] args, Class[] types, InputStream in, PrintStream out) {
				if(args.length == 0) {
					out.println("D\u00ec qualcosa!");
				}

				if(args.length == 1) {
					String word = (String) args[0];

				}
			}

			@Override
			public String description() {
				return "Dici la parola specificata (deve iniziare con l'ultima sillaba della parola precedente).";
			}
		});

		init();
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

					Node node = list.item(j);

					if(node.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) node;

						int k = 0;
						try {
							while(true) {

								String sillabe = eElement.getElementsByTagName("parola").item(k).getTextContent();
								String unita = uniteStringArray(sillabe.split("-"));

								words.put(sillabe, unita);
								k++;
							}
						} catch(NullPointerException ex) {
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

	public static void main(String[] args) {
		ConnectedWords game = new ConnectedWords(System.in, System.out);
		System.out.println(game.words.toString());
	}

}
