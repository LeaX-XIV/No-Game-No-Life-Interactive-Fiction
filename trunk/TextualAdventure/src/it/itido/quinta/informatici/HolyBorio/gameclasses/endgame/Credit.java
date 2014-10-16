package it.itido.quinta.informatici.HolyBorio.gameclasses.endgame;

/**
 * 
 * @author LeaX
 *
 */
public class Credit extends ScrollableText{
	
	private MusicPlayer mp;
	private boolean trueEnd;
	
	private final static String MAIN_PROGRAMMER = "LeaX";
	private final static String SIDE_PROGRAMMER = "Drudz";
	private final static String SIDE_SIDE_PROGRAMMER = "Pagnoz";
	private final static String SPECIAL_THANKS = "Andrea Primavera\nGianluca Belardinelli";
	
	public Credit(boolean end) {
		super.text = "\n\n\n\n\nCredits\n\n"
						+ "Direttore Esecutivo\t\t" + MAIN_PROGRAMMER + "\n"
						+ "Responsabile Progetto\t\t" + MAIN_PROGRAMMER + "\n"
						+ "General Manager\t\t\t" + MAIN_PROGRAMMER + "\n"
						+ "Assistente alla Direzione\t" + SIDE_PROGRAMMER + "\n\n"
						+ "Cast\n"
						+ "Abbiamo delle voci?\n\n"
						+ "Ending Theme\n"
						// FIXME: NON SI LEGGE
						+ "Oracion\n"
						+ "Voce\t\t\t\tKayano Ai\n"
						+ "Testi\t\t\t\tNazca\n"
						+ "Musica\t\t\t\tNazca\n"
						+ "Arrangementi\t\t\tNazca\n\n"
						+ "Sceneggiatura\t\t\t" + MAIN_PROGRAMMER + "\n"
						+ "Storyboard\t\t\t" + MAIN_PROGRAMMER + "\n\n"
						+ "Direttore Programmazione\t" + MAIN_PROGRAMMER + "\n"
						+ "Programmazione\t\t\t" + MAIN_PROGRAMMER + "\n"
						+ "\t\t\t\t" + SIDE_PROGRAMMER + "\n"
						+ "Programmazione Giochi\t\t" + MAIN_PROGRAMMER + "\n"
						+ "\t\t\t\t" + SIDE_PROGRAMMER + "\n"
						+ "\t\t\t\t" + SIDE_SIDE_PROGRAMMER + "\n"
						+ "Test\t\t\t\t" + MAIN_PROGRAMMER + "\n"
						+ "\t\t\t\t" + SIDE_PROGRAMMER + "\n"
						+ "\t\t\t\t" + SIDE_SIDE_PROGRAMMER + "\n\n\n"
						+ "Liberamente tratto da\n"
						// FIXME: NON SI LEGGE
						+ "No Game No Life\n"
						+ "di Kamiya Yuu\n\n"
						+ "Ringraziamenti Speciali\n"
						+ SPECIAL_THANKS + "\n"
						+ "e...\n"
						+ "TU!\n\n\n\n\n\n\n\n"
						+ "Grazie per aver giocato!\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
		
		
		mp = new MusicPlayer(getClass().getResource("/resources/endingTheme1.mp3").getFile());
		setWaitTime((int) (mp.getDurationAudioFile() / getReturns()));
		
		trueEnd = end;
	}
	
	@Override
	public void show() {
		mp.start();
		super.show();
		
		if(trueEnd) {
			System.out.print("\t\t\t\t\t\t\t\t\t    Fine");
		}
		else {
			new Continue();
		}
				
		System.exit(0);
		
	}
	
	public static void main(String[] args) {
		Credit c = new Credit(false);
		c.show();
	}

}
