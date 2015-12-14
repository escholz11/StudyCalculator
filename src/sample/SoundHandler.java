package sample;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Project Name Study Calculator
 * This Class FILL IN CONTENT
 * Created by E.Scholz on 31.01.2015 15:56.
 */
public class SoundHandler {

    String gameSoundPath = "Amelie.wav";
    String clickSoundPath = "click.wav";
    public static boolean gameSoundPlayed = false;
    AudioInputStream audioInputStream;
    Clip clickSound;
    Clip gameSound;

    private static boolean soundStatus = true;

    public static boolean soundStatus() {
        return soundStatus;
    }

    public void activateSound() {
        soundStatus = true;
    }

    public void deactivateSound() {
        soundStatus = false;
    }

    public void playGameSound() {
        if(gameSoundPlayed == false) {
            try {
                audioInputStream = AudioSystem.getAudioInputStream(new File(gameSoundPath));
                gameSound = AudioSystem.getClip();
                gameSound.open(audioInputStream);
                gameSound.loop(5);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        gameSoundPlayed = true;
    }

    public void stopGameSound() {
        if(gameSound != null) {
            gameSound.stop();
        }
    }

    public void playClickSound() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(clickSoundPath));
            clickSound = AudioSystem.getClip();
            clickSound.open(audioInputStream);
            clickSound.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopClickSound() {
        if(clickSound != null) {
            this.clickSound.stop();
        }
    }
}
