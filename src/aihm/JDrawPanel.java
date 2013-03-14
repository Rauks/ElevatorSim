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
public class JDrawPanel extends JPanel{
    private int posX;
    
    public JDrawPanel(){
        super();
        this.posX = 0;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        if(posX > 100){
            posX = 100;
        }
        else if(posX < 0){
            posX = 0;
        }
        this.posX = posX;
    }
    
    @Override 
    public void paintComponent(Graphics g) {
        //background
        super.paintComponent(g);
        
        //custom paint
        int width = this.getWidth();
        int height = this.getHeight();
        int calcPos = (int)((float)(2 * height/3) * (1 - (float)this.posX/100.));
        g.drawRect(10, calcPos, width - 20, height/3);
        g.drawString(String.valueOf(this.posX) + " %", width/2 - 10, calcPos + height/6 + 5);
        g.drawLine(5, 0, 5, height);
        g.drawLine(width - 5, 0, width - 5, height);
    }
}
