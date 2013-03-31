/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm.ui.lift;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    public enum CityDesign{
        DAY("day"), NIGHT("night");
        
        private String dir;
        
        private CityDesign(String dir){
            this.dir = dir;
        }
        
        private String getDir(){
            return this.dir;
        }
    }
    
    public final static int NB_FLOORS = 15;
    public final static int MAX_POSITION = NB_FLOORS * 56;
    public final static int MAX_DOORS_OPENING = 6;
    
    private int position = 0;
    private int doorsOverture = 0;
    private final int cabX = 241;
    private final int minCabY = 80;
    private final int imageFloorHeight = MAX_POSITION / NB_FLOORS;
    private final int maxCabY = minCabY + imageFloorHeight * (NB_FLOORS - 1);
    
    private People[] people;
    
    private BufferedImage imageBackground;
    private BufferedImage imageTower;
    private BufferedImage imageCane;
    private BufferedImage imageBottom;
    
    private BufferedImage[] imagesFloors;
    
    private BufferedImage imageLiftFront;
    private BufferedImage imageLiftBack;
    private BufferedImage imageLiftLeftDoor;
    private BufferedImage imageLiftRightDoor;

    public LiftPanel() {
        super();
        
        this.people = new People[NB_FLOORS - 1]; //Nobody in roof
        
        //Building images loading
        this.loadBuilding(CityDesign.DAY);

        //Floors images loading
        this.imagesFloors = new BufferedImage[NB_FLOORS - 2]; //Lobby & roof already in background
        this.loadFloors();

        //Lift images loading
        this.loadLift(LiftDesign.CLASSIC);
        
        //People
        this.loadPeople();

        //JPanel preferred size is the background image size
        this.setPreferredSize(new Dimension(this.imageBackground.getWidth(), this.imageBackground.getHeight()));
    }
    
    /**
     * Load a specific building and city theme.
     * 
     * @param design Theme choice
     */
    public final void loadBuilding(CityDesign design){
        try {
            URL urlBackground = LiftPanel.class.getResource("res/building/" + design.getDir() + "/Background.png");
            this.imageBackground = ImageIO.read(urlBackground);
            URL urlTower = LiftPanel.class.getResource("res/building/" + design.getDir() + "/Tower.png");
            this.imageTower = ImageIO.read(urlTower);
            URL urlCane = LiftPanel.class.getResource("res/building/" + design.getDir() + "/Cane.png");
            this.imageCane = ImageIO.read(urlCane);
            URL urlBottom = LiftPanel.class.getResource("res/building/" + design.getDir() + "/Bottom.png");
            this.imageBottom = ImageIO.read(urlBottom);
        } catch (IOException ex) {
            Logger.getLogger(LiftPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Load randomly the floors for the building.
     * 
     */
    public final void loadFloors(){
        try {
            //Load the floors
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
    
    /**
     * Load randomly the people for the building.
     * 
     */
    public final void loadPeople(){
        for (int i = 0; i < this.people.length; i++) {
            this.people[i] = new People(People.PeopleDesign.randomDesign(), 116);
        }
    }

    /**
     * Load a specific lift theme.
     * 
     * @param design Theme choice
     */
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
    
    /**
     * Position of the lift on the lift axe.
     * 
     * @return Position from 0 to MAX_POSITION
     */
    public int getPosition() {
        return this.position;
    }
    
    /**
     * Position on Y axis in pixels of the lift on the panel.
     * 
     * @return Position
     */
    public int getCabY(){
        return this.maxCabY - this.position;
    }
    
    /**
     * Position on X axis in pixels of the lift on the panel.
     * 
     * @return Position
     */
    public int getCabX(){
        return this.cabX;
    }

    /**
     * Change the position of the lift. If the position value is out of bound, the minimum or the maximum position is set.
     * 
     * @param position The new position.
     */
    public void setPosition(int position) {
        if (position > LiftPanel.MAX_POSITION) {
            position = LiftPanel.MAX_POSITION;
        } else if (position < 0) {
            position = 0;
        }
        this.position = position;
    }
    /**
     * Increase the position of the lift by one. Safe for the positions bounds.
     */
    public void incrPosition() {
        this.setPosition(this.getPosition() + 1);
    }

    /**
     * Decrease the position of the lift by one. Safe for the positions bounds.
     */
    public void decrPosition() {
        this.setPosition(this.getPosition() - 1);
    }
    
    /**
     * Get the current doors ouverture.
     * 
     * @return The door overture.
     */
    public int getDoorsOverture() {
        return this.doorsOverture;
    }

    /**
     * Set the door ouverture of the lift. If the ouverture value is out of bound, the minimum or the maximum position is set.
     * 
     * @param open The ouverture value.
     */
    public void setDoorsOverture(int open) {
        if (open > LiftPanel.MAX_DOORS_OPENING) {
            open = LiftPanel.MAX_DOORS_OPENING;
        } else if (open < 0) {
            open = 0;
        }
        this.doorsOverture = open;
    }

    /**
     * Increase the door ouverture of the lift by one. Safe for the ouverture value bounds.
     */
    public void incrDoorsOverture() {
        this.setDoorsOverture(this.getDoorsOverture() + 1);
    }

    /**
     * Decrease the door ouverture of the lift by one. Safe for the ouverture value bounds.
     */
    public void decrDoorsOverture() {
        this.setDoorsOverture(this.getDoorsOverture() - 1);
    }
    
    /**
     * Request the next move of all of the people in the building.
     */
    public void movePeople(){
        for (int i = 0; i < this.people.length; i++) {
            this.people[i].move();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawImage(this.imageBackground, 0, 0, null);
        g2d.drawImage(this.imageTower, 0, 0, null);

        for (int i = 0; i < this.imagesFloors.length; i++) {
            g2d.drawImage(this.imagesFloors[i], 273, 133 + i * this.imageFloorHeight, null);
        }
        for (int i = 0; i < this.people.length; i++) {
            if(this.people[i] != null){
                People p = this.people[i];
                p.paintComponent(g2d, 273 + p.getPosition(), 171 + i * this.imageFloorHeight, null);
            }
        }
        
        g2d.drawImage(this.imageLiftBack, this.getCabX(), this.getCabY(), null);
        g2d.drawImage(this.imageLiftLeftDoor, this.getCabX() - this.doorsOverture, this.getCabY(), null);
        g2d.drawImage(this.imageLiftRightDoor, this.getCabX() + this.doorsOverture, this.getCabY(), null);
        g2d.drawImage(this.imageLiftFront, this.getCabX(), this.getCabY(), null);
        
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
