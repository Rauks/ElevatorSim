/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * @author Karl
 */
public class AudioPlayer{
    public final static int LOOP_CONTINUOUSLY = Clip.LOOP_CONTINUOUSLY;
    
    private Clip clip;
    
    /**
     * Create an AudioPlayer.
     * 
     * @param audioFile The URL of the audi file to play.
     */
    public AudioPlayer(URL audioURL){
        try {
            this.clip = AudioSystem.getClip();
            try {
                this.clip.open(AudioSystem.getAudioInputStream(audioURL));
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(LiftFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Change the volume of the audio.
     * 
     * @param gain Gain value between 0.0 and 1.0.
     */
    public void setVolume(float gain){
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        FloatControl volume = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(dB);
    }
    
    /**
     * Stop the audio playback.
     */
    public void stop(){
        this.clip.stop();
        this.clip.flush();
    }
    
    /**
     * Play the audio in loop.
     */
    public void playLooped(){
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        this.clip.start();
    }
    
    /**
     * Play the audio only once.
     */
    public void play(){
        this.stop();
        this.clip.setFramePosition(0);
        this.clip.start();
    }
}
