/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Karl
 */
public class LiftPanel extends JPanel{
    public static int MAX_X = 100;
    
    private int posX;
    
    public LiftPanel(){
        super();
        this.posX = 0;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        if(posX > LiftPanel.MAX_X){
            posX = LiftPanel.MAX_X;
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
    
    @Override 
    public void paintComponent(Graphics g) {
        //background
        super.paintComponent(g);
        
        //custom paint
        int width = this.getWidth() - 1;
        int height = this.getHeight() - 1;
        int calcPos = (int)((float)(2 * height/3) * (1 - (float)this.posX/((float) LiftPanel.MAX_X)));
        g.drawRect(10, calcPos, width - 20, height/3);
        g.drawString(String.valueOf(this.posX) + " %", width/2 - 10, calcPos + height/6 + 5);
        g.drawLine(5, 0, 5, height);
        g.drawLine(width - 5, 0, width - 5, height);
    }
}
