package it.itido.quinta.informatici.HolyBorio.gameclasses.endgame;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ScrollableText {

	protected int waitTime = 1500;
	protected String text;
	
	public ScrollableText() {
		this.text = "Questo è\n"
				+ "un test\n"
				+ "del testo\n"
				+ "scorrevole.";
	}
	
	
	public ScrollableText(String text) {
		setText(text);
	}
	
	private void setText(String text) {
		this.text = text;
	}


	public void setWaitTime(int wait) {
		this.waitTime = wait;
	}
	
	public void show() {
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if(c == '\n') {
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
				}
			}
			System.out.print(c);
		}
	}
	
	public void show(boolean first) {
		String[] route = this.text.split("\\|");
		
		if(first) {
			setText(route[0]);
		}
		else {
			setText(route[1]);
		}
		
		show();
	}
	
	public int getReturns() {
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			if(text.charAt(i) == '\n') {
				count++;
			}
		}
		
		return count;
	}
	
	public static void showSclollTextFromXml(String tag) {
		showSclollTextFromXml(tag, 2500);
	}
	
	public static String readFromXml(String tag) {
		File file = new File(ScrollableText.class.getResource("/resources/dialoghi.xml").getFile());
		
		String text = "";

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("dialogs");

			for(int i = 0; i < nList.getLength(); i++) {
				Node node = nList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE) {

					text = doc.getElementsByTagName(tag).item(0).getTextContent();
					break;
				}
					
			}
		} catch(IOException | ParserConfigurationException | SAXException e) {
		}
		
		return text;
	}
	
	public static void showSclollTextFromXml(String text, int waitTime) {
			ScrollableText st = new ScrollableText(text);
			st.setWaitTime(waitTime);
			st.show();
	}
	
}
