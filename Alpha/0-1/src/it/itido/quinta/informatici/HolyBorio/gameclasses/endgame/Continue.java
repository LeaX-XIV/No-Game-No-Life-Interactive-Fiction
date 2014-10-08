package it.itido.quinta.informatici.HolyBorio.gameclasses.endgame;

/**
 * 
 * @author LeaX
 *
 */
public class Continue {
	
//	private final static int WAIT_FAST = 1000;
	private final static int WAIT_SLOW = 2500;
	
	private String continua;
	
	public Continue() {
		this.continua = "\t\t\t\t\t\t\t\t\t   Fine";
		System.out.print(continua);
//		for (int i = 0; i < 3; i++) {
//			try {
//				Thread.sleep(WAIT_FAST);
//			} catch (InterruptedException e) {
//			}
//			System.out.print(".");
//		}
		try {
			Thread.sleep(WAIT_SLOW);
		} catch (InterruptedException e) {
		}
		System.out.print("?");
		
	}
	
	public static void main(String[] args) {
		new Continue();
	}
}
