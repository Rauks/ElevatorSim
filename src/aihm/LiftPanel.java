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
        //Background
        super.paintComponent(g);
        
        //Calculus for placing draws
        int width = this.getWidth() - 1;
        int height = this.getHeight() - 1;
        int calcPos = (int)((float)(2 * height/3) * (1 - (float)this.posX/((float) LiftPanel.MAX_X)));
        int doorPadding = 2;
        
        //Cab
        g.drawRect(10, calcPos, width - 20, height/3);
        
        //Vertical guide
        g.drawLine(5, 0, 5, height);
        g.drawLine(width - 5, 0, width - 5, height);
        
        //Cab doors
        g.setColor(Color.LIGHT_GRAY);
        g.drawRoundRect(
                11 + doorPadding,                                   //X
                calcPos + 1 + doorPadding,                          //Y
                (width - 22) / 2 - (doorPadding + doorPadding/2),   //Witdh
                height/3 - 2 - 2 * doorPadding,                     //Height
                5,                                                  //RoundX
                5                                                   //RoundY
            );
        g.drawRoundRect(
                12 + (width - 22) / 2 + (doorPadding/2), 
                calcPos + 1 + doorPadding, 
                (width - 22) / 2 - (doorPadding + doorPadding/2), 
                height/3 - 2 - 2 * doorPadding, 
                5, 
                5
            );
    }
}
