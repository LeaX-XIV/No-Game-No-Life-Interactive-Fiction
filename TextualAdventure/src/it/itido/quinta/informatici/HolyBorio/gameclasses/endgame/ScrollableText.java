package it.itido.quinta.informatici.HolyBorio.gameclasses.endgame;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
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
	
	public static void showSclollTextFromXml(String tag, int waitTime) {
		File file = new File(ScrollableText.class.getResource("/resources/dialoghi.xml").getFile());

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			doc.getDocumentElement().normalize();
			
			
			String prologue = doc.getElementsByTagName(tag).item(0).getTextContent();
				
			ScrollableText st = new ScrollableText(prologue);
			st.setWaitTime(waitTime);
			st.show();
		}catch(IOException | ParserConfigurationException | SAXException e) {
		}
	}
	
}
