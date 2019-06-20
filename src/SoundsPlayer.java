import java.io.*;
import javax.sound.sampled.*;

public enum SoundsPlayer {
    DEAL("sounds/deal.wav"),
    WIN("sounds/win.wav"),
    LOSE("sounds/lose.wav"),
    SHUFFLE("sounds/shuffle.wav"),
    CLICK("sounds/click.wav");

    public enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    // Constructor to construct each element of the enum with its own sound file.
    SoundsPlayer(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            InputStream url = this.getClass().getClassLoader().getResourceAsStream(soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();   // Stop the player if it is still running
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
        }
    }

    public static void init() {
        values(); // calls the constructor for all the elements
    }
}
