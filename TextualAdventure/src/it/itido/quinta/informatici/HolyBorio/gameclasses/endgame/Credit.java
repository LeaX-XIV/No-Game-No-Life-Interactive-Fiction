package it.itido.quinta.informatici.HolyBorio.gameclasses.endgame;

/**
 * 
 * @author LeaX
 *
 */
public class Credit {
	
	private final static String MAIN_PROGRAMMER = "LeaX";
	private final static String SIDE_PROGRAMMER = "Drudz";
	private final static String SIDE_SIDE_PROGRAMMER = "Pagnoz";
	private final static String SPECIAL_THANKS = "Andrea Primavera\nGianluca Belardinelli";
	
	private int waitTime = 1363;
	
	private String credit;
	
	public Credit() {
		this.credit = "\n\n\n\n\nCredits\n\n"
						+ "Direttore Esecutivo\t\t" + MAIN_PROGRAMMER + "\n"
						+ "Responsabile Progetto\t\t" + MAIN_PROGRAMMER + "\n"
						+ "General Manager\t\t\t" + MAIN_PROGRAMMER + "\n"
						+ "Assistente alla Direzione\t" + SIDE_PROGRAMMER + "\n\n"
						+ "Cast\n"
						+ "Abbiamo delle voci?\n\n"
						+ "Ending Theme\n"
						// FIXME: NON SI LEGGE
						+ "\uff62\u30aa\u30e9\u30b7\u30aa\u30f3\uff63\n"
						+ "\t\t\t\tKayano Ai\n"
						+ "Testi\t\t\t\tNazca\n"
						+ "Musica\t\t\t\tNazca\n"
						+ "Arrangementi\t\t\tNazca\n\n"
						+ "Sceneggiatura\t\t\t" + MAIN_PROGRAMMER + "\n"
						+ "Storyboard\t\t\t" + SIDE_PROGRAMMER + "\n"
						+ "Assistente Storyboard\t\t" + SIDE_SIDE_PROGRAMMER + "\n\n"
						+ "Direttore Programmazione\t" + MAIN_PROGRAMMER + "\n"
						+ "Programmazione\t\t\t" + MAIN_PROGRAMMER + "\n"
						+ "\t\t\t\t" + SIDE_PROGRAMMER + "\n"
						+ "Programmazione Giochi\t\t" + MAIN_PROGRAMMER + "\n"
						+ "\t\t\t\t" + SIDE_PROGRAMMER + "\n"
						+ "Tester\t\t\t\t" + MAIN_PROGRAMMER + "\n"
						+ "\t\t\t\t" + SIDE_PROGRAMMER
						+ "\t\t\t\t" + SIDE_SIDE_PROGRAMMER + "\n\n\n"
						+ "Liberamente tratto da\n"
						// FIXME: NON SI LEGGE
						+ "\uff62\u30ce\u30fc\u30b2\u30fc\u30e0\u30ce\u30fc\u30e9\u30a4\u30d5\uff63\n"
						+ "di Kamiya Yuu\n\n"
						+ "Ringraziamenti Speciali\n"
						+ SPECIAL_THANKS + "\n"
						+ "e...\n"
						+ "TU!\n\n\n\n"
						+ "Grazie per aver giocato!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	}
	
	public void setWaitTime(int wait) {
		this.waitTime = wait;
	}
	
	/**
	 * Stampa i crediti inizializzati. Ogni volta che deve stampare "a capo", prima aspetta {@link #WAIT_TIME} millisecondi.
	 */
	public void show() {
		for(int i = 0; i < credit.length(); i++) {
			char c = credit.charAt(i);
			if(c == '\n') {
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
				}
			}
			System.out.print(c);
		}
	}
	
	public static void main(String[] args) {
		Credit c = new Credit();
		c.show();
	}

}
