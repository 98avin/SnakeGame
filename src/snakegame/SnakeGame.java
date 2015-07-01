/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author cs1
 */
///CHECKING IF PUSHES ERIC
public class SnakeGame {

    public static void main(String[] args) {

        JFrame mainWindow = new JFrame();
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setPreferredSize(new Dimension(500, 500));
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int size = 10;//Square size

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int taskBarHeight = screenSize.height - winSize.height;
        SnakePanel snakeGame = new SnakePanel((height - taskBarHeight) / size, width / size, size);
        
        //Random Names
        String[] names = {"Bernie", "Bernita", "Snaaakeee", "Solid", "???", "Avin", "Erik", "Richard", "Owen", "AHHHHHHH", "Liquid", "Gaseous", "Plasma", "Blank"};
        String snakeOneName = names[(int) ((Math.random() * 1000) % names.length)];
        String snakeTwoName = names[(int) ((Math.random() * 1000) % names.length)];
        if (snakeTwoName.equals(snakeOneName)) {
            snakeTwoName = names[(int) ((Math.random() * 1000) % names.length)];
        }
        //Spawns Food
        for (int i = 0; i < 10000; i++) {
            snakeGame.drawMouse();
        }
        mainWindow.add(snakeGame);

        JLabel statusLabel = new JLabel("SCORE: " + Integer.toString(snakeGame.bernie.score));
        statusLabel.setFont(new Font("Chiller", Font.PLAIN, 75));
        statusLabel.setForeground(Color.white);
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        snakeGame.add(statusLabel);
        int i = 100;
        mainWindow.pack();
        mainWindow.setVisible(true);

        while (true) {

            snakeGame.update(snakeGame.keysPressed.dir, snakeGame.bernie);
            //snakeGame.update(snakeGame.keysPressed2.dir, snakeGame.bernita);

            try {
                //if (i > 35) {///Accelerates into fast pace so time to adapt
                //    i--;
               // }
                Thread.sleep(35);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mainWindow.repaint();

            statusLabel.setText(snakeOneName + ": " + Integer.toString(snakeGame.bernie.score));

        }

    }

}
