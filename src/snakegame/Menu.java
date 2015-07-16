/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;


/**
 *
 * @author skinnnero5
 */
public class Menu extends JPanel{

    
    public Rectangle playButton = new Rectangle((int)SnakePanel.WINDOW_WIDTH / 2 +120, 150, 100, 50);

    
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("SNAKE!!!", (int)SnakePanel.WINDOW_WIDTH /2 ,100);
        
        g2d.drawString("PLAY", playButton.x + 19, playButton.y + 30);
        g2d.draw(playButton);
    }
}
