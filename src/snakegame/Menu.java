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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Menu {

    public static int musicIndex = 0;

    static SnakePanel sp;
    Component[] menuArray;
    Component[] optArray;
    Component[] pauseArray;
    Component[] visibleArray;

    public Menu(SnakePanel sp) {
        this.sp = sp;
        sp.setLayout(null);
        menuArray = buildMenu();
        optArray = buildOptions();
        pauseArray = buildPause();
        visibleArray=menuArray;
    }
    public Component[] buildPause(){
        ArrayList<Component> tempList = new ArrayList<>();
        final JLabel title = new JLabel("OPTIONS!!!");
        title.setFont(fontLoader(200F));
        title.setForeground(Color.white);
        title.setBounds(sp.getScreenWidth() / 22, 50, 2500, 250);
        tempList.add(title);

        final JLabel currentSong = new JLabel(SnakePanel.musicArray[musicIndex]);
        currentSong.setForeground(Color.white);
        currentSong.setFont(fontLoader(20F));
        currentSong.setLayout(null);
        currentSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) - 125, 350, 250, 100);
        tempList.add(currentSong);

        MyButton nextSong = makeButton(">", 350, 20, 20);
        nextSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) + 200, 400, 50, 50);
        nextSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicIndex++;
                if (musicIndex == SnakePanel.musicArray.length) {
                    musicIndex = 0;
                }
                currentSong.setText(SnakePanel.musicArray[musicIndex]);
                try {
                    SnakePanel.unLoadMusic();
                    SnakePanel.loadMusic(musicIndex);//loads the WAV file to play later, prevents lag
                } catch (Exception ex) {
                    Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tempList.add(nextSong);
        
        MyButton prevSong = makeButton("<", 350, 20, 20);
        prevSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2)-250, 400, 50, 50);
        prevSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicIndex--;
                if (musicIndex == -1) {
                    musicIndex = SnakePanel.musicArray.length-1;
                }
                currentSong.setText(SnakePanel.musicArray[musicIndex]);
                try {
                    SnakePanel.unLoadMusic();
                    SnakePanel.loadMusic(musicIndex);//loads the WAV file to play later, prevents lag
                } catch (Exception ex) {
                    Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tempList.add(prevSong);
        
        MyButton playButton = makeButton("PLAY", 475, 77, 63);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.state = SnakeGame.STATE.GAME;
                sp.removeAll();
                sp.requestFocusInWindow();
            }
        });
        tempList.add(playButton);

        MyButton backButton = makeButton("QUIT", 600, 77, 63);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                sp.requestFocusInWindow();
                sp.clearGame();
                for(int i = 0; i<menuArray.length;i++){
                sp.add(menuArray[i]);
                }
                visibleArray=menuArray;
            }
        });
        tempList.add(backButton);
        
        Component[] tempArray= new Component[tempList.size()];
        for(int i = 0; i < tempList.size();i++){
            tempArray[i]=tempList.get(i);
        }
        return tempArray;
    }
    
    public Component[] buildMenu() {
        ArrayList<Component> tempList;
        tempList = new ArrayList<>();

        final JLabel title = new JLabel("SNAKE!!!", SwingConstants.CENTER);
        title.setFont(fontLoader(200F));
        title.setForeground(Color.white);
        title.setBounds(-sp.getScreenWidth() / 4, 0, 2500, 250);
        tempList.add(title);

        try {
            SnakePanel.loadMusic(musicIndex);//loads the WAV file to play later, prevents lag
        } catch (Exception ex) {
            Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        MyButton playButton = makeButton("PLAY", 350, 77, 63);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.state = SnakeGame.STATE.GAME;
                sp.removeAll();
                sp.requestFocusInWindow();
            }
        });
        tempList.add(playButton);

        MyButton optButton = makeButton("OPTIONS", 475, 41, 63);
        optButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                sp.requestFocusInWindow();                
                for(int i = 0; i<optArray.length;i++){
                sp.add(optArray[i]);
                }
                visibleArray = optArray;
            }
        });
        tempList.add(optButton);

        MyButton quitButton = makeButton("QUIT", 600, 77, 63);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        tempList.add(quitButton);
        Component[] tempArray= new Component[tempList.size()];
        for(int i = 0; i < tempList.size();i++){
            tempArray[i]=tempList.get(i);
        }
        return tempArray;
    }

    public Component[] buildOptions() {
        ArrayList<Component> tempList = new ArrayList<>();
        final JLabel title = new JLabel("OPTIONS!!!");
        title.setFont(fontLoader(200F));
        title.setForeground(Color.white);
        title.setBounds(sp.getScreenWidth() / 22, 0, 2500, 250);
        tempList.add(title);

        final JLabel currentSong = new JLabel(SnakePanel.musicArray[musicIndex]);
        currentSong.setForeground(Color.white);
        currentSong.setFont(fontLoader(20F));
        currentSong.setLayout(null);
        currentSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) - 125, 350, 250, 100);
        tempList.add(currentSong);

        MyButton nextSong = makeButton(">", 350, 20, 20);
        nextSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) + 200, 400, 50, 50);
        nextSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicIndex++;
                if (musicIndex == SnakePanel.musicArray.length) {
                    musicIndex = 0;
                }
                currentSong.setText(SnakePanel.musicArray[musicIndex]);
                try {
                    SnakePanel.unLoadMusic();
                    SnakePanel.loadMusic(musicIndex);//loads the WAV file to play later, prevents lag
                } catch (Exception ex) {
                    Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tempList.add(nextSong);
        
        MyButton prevSong = makeButton("<", 350, 20, 20);
        prevSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2)-250, 400, 50, 50);
        prevSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicIndex--;
                if (musicIndex == -1) {
                    musicIndex = SnakePanel.musicArray.length-1;
                }
                currentSong.setText(SnakePanel.musicArray[musicIndex]);
                try {
                    SnakePanel.unLoadMusic();
                    SnakePanel.loadMusic(musicIndex);//loads the WAV file to play later, prevents lag
                } catch (Exception ex) {
                    Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tempList.add(prevSong);

        MyButton AIButton = makeButton("ADD AI", 475, 41, 63);
        AIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tempList.add(AIButton);
        
        MyButton playButton = makeButton("PLAY", 600, 77, 63);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.state = SnakeGame.STATE.GAME;
                sp.removeAll();
                sp.requestFocusInWindow();
            }
        });
        tempList.add(playButton);

        MyButton backButton = makeButton("BACK", 725, 77, 63);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                sp.requestFocusInWindow();
                for(int i = 0; i<menuArray.length;i++){
                sp.add(menuArray[i]);
                }
                visibleArray=menuArray;
            }
        });
        tempList.add(backButton);
        
        Component[] tempArray= new Component[tempList.size()];
        for(int i = 0; i < tempList.size();i++){
            tempArray[i]=tempList.get(i);
        }
        return tempArray;
    }

    public void pause(){
        visibleArray = pauseArray;
    }
    public void renderMenu() {
        for (int i = 0; i < visibleArray.length; i++) {
            sp.add(visibleArray[i]);
        }
    }

    public static Font fontLoader(float size) {
        SnakeGame.fontLoader();
        Font currentFont = SnakeGame.customFont;
        Font newFont = currentFont.deriveFont(size);
        return newFont;
    }

    public static MyButton makeButton(String buttontext, int y, int textx, int texty) {
        MyButton button = new MyButton(buttontext, textx, texty);
        button.setLayout(null);
        button.setPreferredSize(new Dimension(250, 100));
        button.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) - 125, y, 250, 100);
        return button;
    }

    public static class MyButton extends JButton {

        String buttonWord;
        int textx;
        int texty;

        MyButton(String s, int textx, int texty) {
            super();
            this.buttonWord = s;
            this.textx = textx;
            this.texty = texty;

            this.setLayout(null);

            this.setPreferredSize(new Dimension(250, 100));

        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.white);

            g.setFont(fontLoader(25F));
            g.drawString(buttonWord, textx, texty);

        }
        @Override
        protected void paintBorder(Graphics g){
            
        }
    }
}
