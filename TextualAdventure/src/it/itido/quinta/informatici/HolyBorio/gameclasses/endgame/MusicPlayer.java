package it.itido.quinta.informatici.HolyBorio.gameclasses.endgame;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

public class MusicPlayer extends Thread{
	
	private final static String FILE_1 = MusicPlayer.class.getResource("/resources/endingTheme1.mp3").getFile();
	private final static String FILE_2 = MusicPlayer.class.getResource("/resources/endingTheme2.mp3").getFile();
	
	private File mp3;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private Player player;
	
	public MusicPlayer(boolean lost) {
		
		try {
			if(lost) {
				mp3 = new File(FILE_2);
			}
			else {
				mp3 = new File(FILE_1);
			}
			fis = new FileInputStream(mp3);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		}catch (IOException e) {
			e.printStackTrace();
		}catch(JavaLayerException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void run() {
		try {
			player.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		MusicPlayer mp = new MusicPlayer(false);
		mp.run();
	}
}
