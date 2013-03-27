/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * @author Karl
 */
public class AudioPlayer extends Thread{
    public final static float MIN_VOLUME = 0f;
    public final static float RESET_VOLUME = 1f;
    
    private AudioInputStream audioInputStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;
    
    private boolean loop = false;
    
    public AudioPlayer(String audioFile){
        try {
            URL audioUrl = AudioPlayer.class.getResource(audioFile);
            this.audioInputStream = AudioSystem.getAudioInputStream(audioUrl);
            this.audioFormat = this.audioInputStream.getFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, this.audioFormat);
            this.sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
            this.audioInputStream.mark(audioUrl.openConnection().getContentLength());
            this.sourceDataLine.open(audioFormat);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loop(boolean loop){
        this.loop = loop;
    }
    
    public void volume(float gain){
        if (this.sourceDataLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volume = (FloatControl) this.sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            volume.setValue(dB);

        }
    }
    
    @Override
    public void run(){
        try{
            this.sourceDataLine.start();

            int cnt;
            byte tempBuffer[] = new byte[1000];
            
            this.audioInputStream.reset();
            boolean firstPlay = true;
            
            while(this.loop || firstPlay){
                while((cnt = this.audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1){
                    if(cnt > 0){
                        this.sourceDataLine.write(tempBuffer, 0, cnt);
                    }
                }
                this.audioInputStream.reset();
            }
            this.sourceDataLine.drain();
        } catch (IOException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
