/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm.ui.lift;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Karl
 */
public class People {
    public enum PeopleDesign{
        AGENT("Agent"), ALIGATOR("Aligator"), ARCHITECT("Architect"), ASTRONAUT("Astronaut"), CELEBRITY("Celebrity"),
        DELIVERY("Delivery"), GLASSES("Glasses"), HARRY("Harry"), WORKER("Worker");
        
        private static final List<PeopleDesign> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();
        
        /**
         * Return a random value from the enum.
         * 
         * @return the random value.
         */
        public static PeopleDesign randomDesign()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }

        private String fileName;
        
        private PeopleDesign(String fileName){
            this.fileName = fileName;
        }
        
        private String getFileName(){
            return this.fileName;
        }
    }
    private enum Movement{LEFT, RIGHT}
    
    public final static PeopleDesign DEFAULT_THEME = PeopleDesign.AGENT;

    private int pathLength;
    private int pos;
    private int target;
    private BufferedImage imageLeft;
    private BufferedImage imageRight;
    private Movement movement;
    
    /**
     * Create a People with the {@link #DEFAULT_THEME}.
     * 
     * @param design Theme choice.
     */
    public People(int pathLength){
        this(DEFAULT_THEME, pathLength);
    }
    
    /**
     * Create a People with a specific theme.
     * 
     * @param design Theme choice.
     */
    public People(PeopleDesign design, int pathLength){
        this.pos = 0;
        this.setTarget();
        this.pathLength = pathLength;
        this.loadDesign(design);
    }
    
    /**
     * Load a specific people theme.
     * 
     * @param design Theme choice.
     */
    public final void loadDesign(PeopleDesign design){
        try {
            URL urlLeft = LiftPanel.class.getResource("res/peoples/" + design.getFileName() + "Left.png");
            this.imageLeft = ImageIO.read(urlLeft);
            URL urlRight = LiftPanel.class.getResource("res/peoples/" + design.getFileName() + "Right.png");
            this.imageRight = ImageIO.read(urlRight);
        } catch (IOException ex) {
            Logger.getLogger(People.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Draw the representative image at the coordinates (x, y) with the Graphics2D;
     * 
     * @param g2d The Graphics2D used to draw.
     * @param x X position of the image.
     * @param y y position of the image.
     * @param obs ImageObserver gived to the Graphics2D in the draw.
     */
    public void paintComponent(Graphics2D g2d, int x, int y, ImageObserver obs){
        switch(this.movement){
            case RIGHT:
                g2d.drawImage(this.imageRight, x, y, obs);
                break;
            case LEFT:
                g2d.drawImage(this.imageLeft, x, y, obs);
                break;
        }
    }
    
    /**
     * Get the current position.
     * 
     * @return the position.
     */
    public int getPosition(){
        return this.pos;
    }
    
    /**
     * Calculate a new position.
     */
    public void move(){
        if(this.target == this.pos){
            this.setTarget();
        }
        switch(this.movement){
            case RIGHT:
                if(this.pos < this.pathLength){
                    this.pos++;
                }
                else{
                    this.setTarget();
                }
                break;
            case LEFT:
                if(this.pos > 0){
                    this.pos--;
                }
                else{
                    this.setTarget();
                }
                break;
        }
    }
    
    /**
     * Set a new target position.
     */
    private void setTarget(){
        this.target = (int)(Math.random() * (pathLength + 1));
        if(this.target < this.pos){
            this.movement = Movement.LEFT;
        }
        else{
            this.movement = Movement.RIGHT;
        }
    }
}
