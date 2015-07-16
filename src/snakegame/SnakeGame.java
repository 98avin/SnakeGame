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
public class SnakeGame {

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
        SnakePanel snakePanel = new SnakePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(snakePanel);

        JLabel statusLabel = new JLabel();
        fontLoader();
        statusLabel.setFont(customFont);
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        snakePanel.add(statusLabel);

        frame.pack();

        frame.setVisible(true);

        while (true) {
            updateScoreboard(snakePanel, statusLabel);
            snakePanel.update();

            try {
                Thread.sleep(FRAME_RATE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            frame.repaint();
        }

    }

    public static void updateScoreboard(SnakePanel rectPanel, JLabel statusLabel) {
        //FLICKERS
        statusLabel.setText("");
        for (int i = 0; i < rectPanel.snakes.length; i++) {
            statusLabel.setText(statusLabel.getText() + " " + rectPanel.snakes[i].getName() + ":" + rectPanel.snakes[i].getScore());
        }
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
