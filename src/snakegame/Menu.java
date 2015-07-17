/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;

public class Menu {
    
    static SnakePanel sp;
    ArrayList<Component> menuArray;
    
    public Menu(SnakePanel sp) {
        this.sp = sp;
        menuArray = new ArrayList<Component>();
    }

    public void buildMenu() {

        

        final MyButton button = new MyButton("PLAY");
        button.setPreferredSize(new Dimension((int) SnakePanel.WINDOW_WIDTH, (int) SnakePanel.WINDOW_HEIGHT));

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sp.remove(button);
                SnakeGame.state = SnakeGame.STATE.GAME;

            }
        });
        menuArray.add(button);

    }

    public void renderMenu() {
        for (int i = 0; i < menuArray.size(); i++) {
            sp.add(menuArray.get(i));
        }
    }

    static class MyButton extends JButton {

        String buttonWord;

        MyButton(String s) {
            super();
            this.buttonWord = s;
        }

        @Override
        protected void paintBorder(Graphics g) {
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.white);
            g.drawString(buttonWord, 100, 100);
        }
    }

}
