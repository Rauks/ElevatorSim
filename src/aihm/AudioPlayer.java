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
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * @author Karl
 */
public class AudioPlayer{
    public final static float MIN_VOLUME = 0f;
    public final static float RESET_VOLUME = 1f;
    
    private class PlayThread extends Thread{
        @Override
        public void run(){
            try{
                sourceDataLine.start();

                int cnt;
                byte tempBuffer[] = new byte[1000];

                audioInputStream.reset();
                boolean firstPlay = true;

                while(loop || firstPlay){
                    firstPlay = false;
                    while((cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1){
                        if(cnt > 0){
                            sourceDataLine.write(tempBuffer, 0, cnt);
                        }
                    }
                    audioInputStream.reset();
                }
                sourceDataLine.drain();
            } catch (IOException ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private AudioInputStream audioInputStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;
    
    private PlayThread playThread;
    
    private boolean loop;
    
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
        this.loop = false;
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
    
    public void stop(){
        if(this.playThread != null && !this.playThread.isInterrupted()){
            this.playThread.interrupt();
        }
        this.playThread = new PlayThread();
    }
    
    public void play(){
        this.stop();
        this.playThread.start();
    }
}
