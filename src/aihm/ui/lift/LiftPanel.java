/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm.ui.lift;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Karl
 * 
 * All formulas are adapted on NB_FLOORS changes.
 * 
 */
public class LiftPanel extends JPanel {
    public enum LiftDesign{
        BLUE("blue"), CHEAP("cheap"), CLASSIC("classic"), FUTURE("future"), GOLD("gold");
        
        private String dir;
        
        private LiftDesign(String dir){
            this.dir = dir;
        }
        
        private String getDir(){
            return this.dir;
        }
    }
    
    public final static int NB_FLOORS = 15;
    public final static int MAX_POS_X = NB_FLOORS * 56;
    public final static int MAX_DOORS_OPENING = 6;
    
    private int posX;
    private int doorsOverture;
    
    BufferedImage imageBackground;
    BufferedImage imageCane;
    BufferedImage imageBottom;
    BufferedImage[] imagesFloors;
    
    BufferedImage imageLiftFront;
    BufferedImage imageLiftBack;
    BufferedImage imageLiftLeftDoor;
    BufferedImage imageLiftRightDoor;

    public LiftPanel() {
        super();
        try {
            //Lift initialisation
            this.posX = 0;
            this.doorsOverture = 0;
            
            //Building images loading
            URL urlBackground = LiftPanel.class.getResource("res/Background.png");
            this.imageBackground = ImageIO.read(urlBackground);
            URL urlCane = LiftPanel.class.getResource("res/Cane.png");
            this.imageCane = ImageIO.read(urlCane);
            URL urlBottom = LiftPanel.class.getResource("res/Bottom.png");
            this.imageBottom = ImageIO.read(urlBottom);
            
            this.imagesFloors = new BufferedImage[NB_FLOORS - 2]; //Lobby & roof already in background
            this.loadFloors();
            
            //Lift images loading
            this.loadLift(LiftDesign.CLASSIC);
            
            //JPanel preferred size is the background image size
            this.setPreferredSize(new Dimension(this.imageBackground.getWidth(), this.imageBackground.getHeight()));
        } catch (IOException ex) {
            Logger.getLogger(LiftPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public final void loadFloors(){
        try {
            int floorFileMin = 1;
            int floorFileMax = 52;
            for (int i = 0; i < imagesFloors.length; i++) {
                int fileChoosedIndex = floorFileMin + (int)(Math.random() * ((floorFileMax - floorFileMin) + 1));
                URL urlFloor = LiftPanel.class.getResource(new StringBuilder().append("res/floors/").append(fileChoosedIndex).append(".png").toString());
                this.imagesFloors[i] = ImageIO.read(urlFloor);
            }
        } catch (IOException ex) {
            Logger.getLogger(LiftPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void loadLift(LiftDesign design){
        try {
            URL urlLiftFront = LiftPanel.class.getResource("res/lifts/" + design.getDir() + "/Front.png");
            this.imageLiftFront = ImageIO.read(urlLiftFront);
            URL urlLiftBack = LiftPanel.class.getResource("res/lifts/" + design.getDir() + "/Back.png");
            this.imageLiftBack = ImageIO.read(urlLiftBack);
            URL urlLiftLeftDoor = LiftPanel.class.getResource("res/lifts/" + design.getDir() + "/LeftDoor.png");
            this.imageLiftLeftDoor = ImageIO.read(urlLiftLeftDoor);
            URL urlLiftRightDoor = LiftPanel.class.getResource("res/lifts/" + design.getDir() + "/RightDoor.png");
            this.imageLiftRightDoor = ImageIO.read(urlLiftRightDoor);
        } catch (IOException ex) {
            Logger.getLogger(LiftPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        if (posX > LiftPanel.MAX_POS_X) {
            posX = LiftPanel.MAX_POS_X;
        } else if (posX < 0) {
            posX = 0;
        }
        this.posX = posX;
    }

    public void incrPosX() {
        this.setPosX(this.getPosX() + 1);
    }

    public void decrPosX() {
        this.setPosX(this.getPosX() - 1);
    }

    public int getDoorsOverture() {
        return this.doorsOverture;
    }

    public void setDoorsOverture(int open) {
        if (open > LiftPanel.MAX_DOORS_OPENING) {
            open = LiftPanel.MAX_DOORS_OPENING;
        } else if (open < 0) {
            open = 0;
        }
        this.doorsOverture = open;
    }

    public void incrDoorsOverture() {
        this.setDoorsOverture(this.getDoorsOverture() + 1);
    }

    public void decrDoorsOverture() {
        this.setDoorsOverture(this.getDoorsOverture() - 1);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawImage(this.imageBackground, 0, 0, null);
        
        int imageFloorHeight = MAX_POS_X / NB_FLOORS;
        for (int i = 0; i < imagesFloors.length; i++) {
            g2d.drawImage(this.imagesFloors[i], 273, 133 + i * imageFloorHeight, null);
        }
        
        int cabX = 241;
        int minCabY = 80;
        int maxCabY = minCabY + imageFloorHeight * (NB_FLOORS - 1);
        int cabY = maxCabY - this.posX;
        g2d.drawImage(this.imageLiftBack, cabX, cabY, null);
        g2d.drawImage(this.imageLiftLeftDoor, cabX - this.doorsOverture, cabY, null);
        g2d.drawImage(this.imageLiftRightDoor, cabX + this.doorsOverture, cabY, null);
        g2d.drawImage(this.imageLiftFront, cabX, cabY, null);
        
        g2d.drawImage(this.imageCane, 240, 7, null);
        g2d.drawImage(this.imageBottom, 212, 917, null);
        
        /* 
         * Old horrible graphics, new fancy graphics are more awsome !
         * 
        //Background
        super.paintComponent(g);

        //Calculus for placing draws
        int width = this.getWidth() - 1;
        int height = this.getHeight() - 1;
        int interFloorPadding = 10;

        //Cab
        int cabX = 4 ;
        int cabY = (int) ((float) ((LiftPanel.NB_FLOORS - 1) * height / LiftPanel.NB_FLOORS) * (1 - (float) this.posX / ((float) LiftPanel.MAX_POS_X))) + interFloorPadding;
        int cabWidth = width - 8;
        int cabHeight = (height / LiftPanel.NB_FLOORS) - (2 * interFloorPadding);

        g.setColor(Color.getHSBColor(0f, 0f, .9f));
        g.fillRect(cabX, cabY, cabWidth, cabHeight);
        g.setColor(Color.BLACK);
        g.drawRect(cabX, cabY, cabWidth, cabHeight);

        //Vertical guide
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 0, height);
        g.drawLine(width, 0, width, height);

        //Cab doors
        int doorPadding = 2;
        float doorOpeningPercent = 1f - (float) this.doorsOverture / ((float) LiftPanel.MAX_DOORS_OPENING);
        int doorWidth = (width - 12) / 2 - (doorPadding + doorPadding / 2);
        int doorWidthWithOpening = (int) ((float) (doorWidth * doorOpeningPercent));
        int doorHeight = height / LiftPanel.NB_FLOORS - 2 - (2 * doorPadding) - (2 * interFloorPadding);
        int leftDoorX = 6 + doorPadding;
        int leftDoorXWithOpening = leftDoorX;
        int rightDoorX = doorWidth + 7 + (width - 12) / 2 + (doorPadding / 2);
        int rightDoorXWithOpening = rightDoorX - doorWidthWithOpening;
        int doorY = cabY + 1 + doorPadding;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(leftDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        g.fillRect(rightDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        g.setColor(Color.BLACK);
        g.drawRect(leftDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        g.drawRect(rightDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        
        //Interfloor blocs
        int blockWidth = width - 1;
        int blockHeight = interFloorPadding;
        int blockX = 1;
        int blockY = 3 - (interFloorPadding / 2);
        
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= LiftPanel.NB_FLOORS; i++) {
            g.fillRect(blockX, i * (height / LiftPanel.NB_FLOORS) + blockY, blockWidth, blockHeight);
        }
        */
    }
}
