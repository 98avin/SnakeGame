/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author cs1
 */
///CHECKING IF PUSHES ERIC
public class SnakeGame {

    public static void main(String[] args) {

        JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SnakePanel snakeGame = new SnakePanel(81, 159);
        String[] names ={"Bernie","Bernita","Snaaakeee","Solid","???","Avin","Erik","Richard","Owen","AHHHHHHH"};
        String snakeOneName=names[(int)((Math.random()*1000)%names.length)];
        String snakeTwoName=names[(int)((Math.random()*1000)%names.length)];
        if(snakeTwoName.equals(snakeOneName)){
            snakeTwoName=names[(int)((Math.random()*1000)%names.length)];
        }
        //Spawns Food
        for (int i = 0; i < 3; i++) {
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
            snakeGame.update(snakeGame.keysPressed2.dir, snakeGame.bernita);

            try {
                if (i > 50) {///Accelerates into fast pace so time to adapt
                    i--;
                }
                Thread.sleep(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mainWindow.repaint();
            
            statusLabel.setText(snakeOneName + ": "+ Integer.toString(snakeGame.bernie.score) + "   " +snakeTwoName + ": "+ Integer.toString(snakeGame.bernita.score));
                    
        }

    }

}
