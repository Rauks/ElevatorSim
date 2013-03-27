/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * @author Karl
 */
public class AudioPlayer extends Thread{
    public final static float MIN_VOLUME = 0f;
    public final static float RESET_VOLUME = 1f;
    
    private Clip audioClip;
    
    public AudioPlayer(String audioFile){
        AudioInputStream sound = null;
        try {
            sound = AudioSystem.getAudioInputStream(AudioPlayer.class.getResource(audioFile));
            this.audioClip = AudioSystem.getClip();
            this.audioClip.open(sound);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                sound.close();
            } catch (IOException ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public AudioPlayer loop(){
        this.audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        return this;
    }
    
    public AudioPlayer volume(float gain){
        FloatControl volume = (FloatControl) this.audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        volume.setValue(dB);
        return this;
    }
    
    @Override
    public void run(){
        this.audioClip.setFramePosition(0);  // Must always rewind!
        this.audioClip.start();
    }
}
