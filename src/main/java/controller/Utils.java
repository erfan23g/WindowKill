package controller;

import view.GameFrame;

import javax.sound.sampled.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class Utils {
    public static Point2D relativeLocation(Point2D point, Point2D anchor){
        return new Point2D.Double(point.getX()-anchor.getX(),point.getY()-anchor.getY());
    }
    public static void playSound(String soundFile) {
        try {
            // Load the sound file as an audio input stream
            File file = new File(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            // Get a sound clip resource and open it
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            double minVolume = volumeControl.getMinimum(); // Minimum possible volume
            double maxVolume = volumeControl.getMaximum(); // Maximum possible volume
            double linearGain = Math.pow(10, maxVolume/10) * ((double) GameFrame.getVolume() / 100);


//            float gain = Math.min(Math.max(minVolume, GameFrame.getVolume()), maxVolume); // Ensure within bounds
            double gain = Math.min(Math.max(minVolume, Math.log(linearGain) * 10), maxVolume); // Ensure within bounds

            System.out.println(minVolume + " " + maxVolume + " " + gain);
            volumeControl.setValue((float) gain);

            // Play the audio clip once
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
