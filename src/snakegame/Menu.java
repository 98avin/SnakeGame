/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Menu {

    static SnakePanel sp;
    ArrayList<Component> menuArray;

    public Menu(SnakePanel sp) {
        this.sp = sp;
        sp.setLayout(null);
        menuArray = new ArrayList<Component>();
    }

    public void buildMenu() {

        final MyButton button = new MyButton("PLAY");

        button.setLayout(null);
        button.setPreferredSize(new Dimension(250, 100));
        button.setBounds((int) ((SnakePanel.WINDOW_WIDTH)), 150, 250, 100);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                SnakeGame.state = SnakeGame.STATE.GAME;

            }
        });
        menuArray.add(button);

        final JLabel title = new JLabel("SNAKE!!!");
        title.setFont(fontLoader(200F));
        title.setForeground(Color.white);
        title.setBounds(50,0,2500,300);
        menuArray.add(title);

    }

    public void renderMenu() {
        for (int i = 0; i < menuArray.size(); i++) {
            sp.add(menuArray.get(i));
        }
    }
    
    public static Font fontLoader(float size) {
        SnakeGame.fontLoader();
        Font currentFont = SnakeGame.customFont;
        Font newFont = currentFont.deriveFont(size);
        return newFont;
    }

    static class MyButton extends JButton {

        String buttonWord;

        MyButton(String s) {
            super();
            this.buttonWord = s;

            this.setLayout(null);

            this.setPreferredSize(new Dimension(250, 100));

        }

       // @Override
        // protected void paintBorder(Graphics g) {
        //  }
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.white);

            g.setFont(fontLoader(25F));
            g.drawString(buttonWord, 125, 50);

        }
    }

}
