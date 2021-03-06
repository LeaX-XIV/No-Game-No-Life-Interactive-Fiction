package it.itido.quinta.informatici.HolyBorio.gameclasses.endgame;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

public class MusicPlayer extends Thread{
		
	private File mp3;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private Player player;
	
	public MusicPlayer(String file) {
		
		try {
			mp3 = new File(file);
			fis = new FileInputStream(mp3);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		}catch (IOException e) {
			e.printStackTrace();
		}catch(JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
	public long getDurationAudioFile() {

		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(mp3);
			AudioFormat format = audioInputStream.getFormat();
			long frames = audioInputStream.getFrameLength();
			double durationInSeconds = (frames+0.0) / format.getFrameRate();
			long millis = (long) (durationInSeconds * 1000);
			
			return millis;
		} catch (UnsupportedAudioFileException e) {
		} catch (IOException e) {
		}
		
		return 90000;
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
		MusicPlayer mp = new MusicPlayer(MusicPlayer.class.getResource("/resources/audio/endingTheme1.mp3").getFile());
		mp.run();
	}
}
