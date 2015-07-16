/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author skinnnero5
 */
public class Menu extends JPanel {

    public static double WINDOW_WIDTH = 800.0;
    public static double WINDOW_HEIGHT = 800.0;

    public Menu() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //sets window to fit screen
        
        WINDOW_WIDTH = screenSize.getWidth() - 10;
        WINDOW_HEIGHT = screenSize.getHeight() - 72;
        
        setPreferredSize(new Dimension((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT));
    }
}
