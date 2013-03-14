/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm;

import javax.swing.SwingUtilities;

/**
 *
 * @author Karl
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                    MainFrame gui = new MainFrame();
                    gui.pack();
                    gui.setVisible(true);
            }
	});
    }
}
