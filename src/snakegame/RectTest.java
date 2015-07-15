//WE NEED TO CLEAN THE CODE UP AND MAKE IT MORE READABLE!!!!!

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.*;
import static java.awt.event.KeyEvent.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author 11768
 */
public class RectTest {

    static Font customFont;
    static Color TEXT_COLOR = Color.lightGray;
    static int FRAME_RATE = 35;
    static int playerscore = 0;
    static int aiscore = 0;
    static int aiscore2 = 0;


    public static void fontLoader() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("customFont.ttf")).deriveFont(25f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("customFont.ttf")));
        } catch (IOException | FontFormatException e) {
            //Handle exception
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame("Snake Game");
        RectPanel rectPanel = new RectPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(rectPanel);

        JLabel statusLabel = new JLabel();
        fontLoader();
        statusLabel.setFont(customFont);
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        rectPanel.add(statusLabel);

        frame.pack();

        frame.setVisible(true);

        
        while (true) {
            rectPanel.update();
            updateScoreboard(rectPanel, statusLabel);
             

            try {
                Thread.sleep(FRAME_RATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            frame.repaint();
        }

    }

    public static void updateScoreboard(RectPanel rectPanel, JLabel statusLabel) {
       
       /*if (!RectPanel.bernie.isLiving()) {
            statusLabel.setText("BERNIE: " + Integer.toString(playerscore) + " BERNINATOR: " + Integer.toString(aiscore) + " ROBOBERNIE: " + Integer.toString(aiscore2));
        } else {
            playerscore = (int) RectPanel.bernie.getScore();
            statusLabel.setText("BERNIE: " + Integer.toString(playerscore) + " BERNINATOR: " + Integer.toString(aiscore) + " ROBOBERNIE: " + Integer.toString(aiscore2));
        }

        if (!RectPanel.berninator.isLiving()) {
            statusLabel.setText("BERNIE: " + Integer.toString(playerscore) + " BERNINATOR: " + Integer.toString(aiscore) + " ROBOBERNIE: " + Integer.toString(aiscore2));
        } else {
            aiscore = (int) RectPanel.berninator.getScore();
            statusLabel.setText("BERNIE: " + Integer.toString(playerscore) + " BERNINATOR: " + Integer.toString(aiscore) + " ROBOBERNIE: " + Integer.toString(aiscore2));
        }

        if (!RectPanel.robobernie.isLiving()) {
            statusLabel.setText("BERNIE: " + Integer.toString(playerscore) + " BERNINATOR: " + Integer.toString(aiscore) + " ROBOBERNIE: " + Integer.toString(aiscore2));
        } else {
            aiscore2 = (int) RectPanel.robobernie.getScore();
            statusLabel.setText("BERNIE: " + Integer.toString(playerscore) + " BERNINATOR: " + Integer.toString(aiscore) + " ROBOBERNIE: " + Integer.toString(aiscore2));
        }*/
        
        
    }
    

    public static int random_number(int low, int high) {
        double rand = Math.random(); //generates a random number
        int rand2 = (int) (rand * 100000); //casts the random number as int
        int interval = high - low;//interval in which to put the number ie 1-100
        rand2 = rand2 % interval;//puts the number into the interval
        rand2 = rand2 + low;//acertains that the number is above the minimum
        int randNum = rand2;//assigns the random number's value
        return randNum;//returns the random number's value
    }

}
