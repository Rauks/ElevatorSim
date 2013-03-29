/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author Karl
 */
public class ScrollBar extends BasicScrollBarUI{
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);    
 
        if(trackHighlight == DECREASE_HIGHLIGHT)    {
            paintDecreaseHighlight(g);
        } 
        else if(trackHighlight == INCREASE_HIGHLIGHT)       {
            paintIncreaseHighlight(g);
        }
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if(thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
        return;
    }
 
        int w = thumbBounds.width;
        int h = thumbBounds.height;     
 
        g.translate(thumbBounds.x, thumbBounds.y);

        g.setColor(thumbDarkShadowColor);
        g.drawRect(0, 0, w-1, h-1);    
        g.setColor(thumbColor);
        g.fillRect(0, 0, w-1, h-1);

            g.setColor(thumbHighlightColor);
            g.drawLine(1, 1, 1, h-2);
            g.drawLine(2, 1, w-3, 1);

            g.setColor(thumbLightShadowColor);
            g.drawLine(2, h-2, w-2, h-2);
            g.drawLine(w-2, 1, w-2, h-3);

        g.translate(-thumbBounds.x, -thumbBounds.y);
    }
}
