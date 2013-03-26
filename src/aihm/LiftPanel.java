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
 */
public class LiftPanel extends JPanel{
    public static int MAX_POS_X = 100;
    public static int MAX_DOORS_OPENING = 100;
    
    private int posX;
    private int doorsOpening;
    
    public LiftPanel(){
        super();
        this.posX = 0;
        this.doorsOpening = 0;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        if(posX > LiftPanel.MAX_POS_X){
            posX = LiftPanel.MAX_POS_X;
        }
        else if(posX < 0){
            posX = 0;
        }
        this.posX = posX;
    }
    
    public void incrPosX(){
        this.setPosX(this.getPosX() + 1);
    }
    
    public void decrPosX(){
        this.setPosX(this.getPosX() - 1);
    }
    
    public int getDoorsOpening() {
        return this.doorsOpening;
    }

    public void setDoorsOpening(int open) {
        if(open > LiftPanel.MAX_DOORS_OPENING){
            open = LiftPanel.MAX_DOORS_OPENING;
        }
        else if(open < 0){
            open = 0;
        }
        this.doorsOpening = open;
    }
    
    public void incrDoorsOpening(){
        this.setDoorsOpening(this.getDoorsOpening() + 1);
    }
    
    public void decrDoorsOpening(){
        this.setDoorsOpening(this.getDoorsOpening() - 1);
    }
    
    @Override 
    public void paintComponent(Graphics g) {
        //Background
        super.paintComponent(g);
        
        //Calculus for placing draws
        int width = this.getWidth() - 1;
        int height = this.getHeight() - 1;
        
        //Cab
        int cabY = (int)((float)(2 * height/3) * (1 - (float)this.posX/((float) LiftPanel.MAX_POS_X)));
        
        g.setColor(Color.BLACK);
        g.drawRect(10, cabY, width - 20, height/3);
        
        //Vertical guide
        g.setColor(Color.BLACK);
        g.drawLine(5, 0, 5, height);
        g.drawLine(width - 5, 0, width - 5, height);
        
        //Cab doors
        int doorPadding = 3;
        float doorOpeningPercent = (float) this.doorsOpening / ((float) LiftPanel.MAX_DOORS_OPENING);
        int doorWidth = (width - 22) / 2 - (doorPadding + doorPadding/2);
        int doorWidthWithOpening = (int)((float)(doorWidth * doorOpeningPercent));
        int doorHeight = height/3 - 2 - 2 * doorPadding;
        int leftDoorX = 11 + doorPadding;
        int leftDoorXWithOpening = leftDoorX;
        int rightDoorX = 12 + (width - 22) / 2 + (doorPadding/2);
        int rightDoorXWithOpening = rightDoorX + doorWidth - doorWidthWithOpening;
        int doorY = cabY + 1 + doorPadding;
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(leftDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        g.fillRect(rightDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        g.setColor(Color.BLACK);
        g.drawRect(leftDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
        g.drawRect(rightDoorXWithOpening, doorY, doorWidthWithOpening, doorHeight);
    }
}
