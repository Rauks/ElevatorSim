/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Karl
 * 
 * All formulas are adapted on NB_FLOORS changes.
 * 
 */
public class LiftPanel extends JPanel {
    public final static int NB_FLOORS = 4;
    public final static int MAX_POS_X = 100;
    public final static int MAX_DOORS_OPENING = 100;
    private int posX;
    private int doorsOverture;

    public LiftPanel() {
        super();
        this.posX = 0;
        this.doorsOverture = 0;
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
        //Background
        super.paintComponent(g);

        //Calculus for placing draws
        int width = this.getWidth() - 1;
        int height = this.getHeight() - 1;
        int interFloorPadding = 10;

        //Cab
        int cabX = 10 ;
        int cabY = (int) ((float) ((LiftPanel.NB_FLOORS - 1) * height / LiftPanel.NB_FLOORS) * (1 - (float) this.posX / ((float) LiftPanel.MAX_POS_X))) + interFloorPadding;
        int cabWidth = width - 20;
        int cabHeight = (height / LiftPanel.NB_FLOORS) - (2 * interFloorPadding);

        g.setColor(Color.getHSBColor(0f, 0f, .9f));
        g.fillRect(cabX, cabY, cabWidth, cabHeight);
        g.setColor(Color.BLACK);
        g.drawRect(cabX, cabY, cabWidth, cabHeight);

        //Vertical guide
        g.setColor(Color.BLACK);
        g.drawLine(5, 0, 5, height);
        g.drawLine(width - 5, 0, width - 5, height);

        //Cab doors
        int doorPadding = 2;
        float doorOpeningPercent = 1f - (float) this.doorsOverture / ((float) LiftPanel.MAX_DOORS_OPENING);
        int doorWidth = (width - 22) / 2 - (doorPadding + doorPadding / 2);
        int doorWidthWithOpening = (int) ((float) (doorWidth * doorOpeningPercent));
        int doorHeight = height / LiftPanel.NB_FLOORS - 2 - (2 * doorPadding) - (2 * interFloorPadding);
        int leftDoorX = 11 + doorPadding;
        int leftDoorXWithOpening = leftDoorX;
        int rightDoorX = doorWidth + 12 + (width - 22) / 2 + (doorPadding / 2);
        int rightDoorXWithOpening = rightDoorX - doorWidthWithOpening;
        int doorY = cabY + 1 + doorPadding;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(leftDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        g.fillRect(rightDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        g.setColor(Color.BLACK);
        g.drawRect(leftDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        g.drawRect(rightDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        
        //Interfloor blocs
        int blockWidth = width - 11;
        int blockHeight = interFloorPadding;
        int blockX = 6;
        int blockY = 2 - (interFloorPadding / 2);
        
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= LiftPanel.NB_FLOORS; i++) {
            g.fillRect(blockX, i * (height / LiftPanel.NB_FLOORS) + blockY, blockWidth, blockHeight);
        }
        
    }
}
