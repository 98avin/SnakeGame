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

    public static int musicIndex = 1;

    static SnakePanel sp;
    static Component[] mainArray;
    static Component[] optArray;
    static Component[] pauseArray;
    static Component[] musicArray;
    static Component[] aiArray;
    static Component[] gameoverArray;
    static Component[] visibleArray;

    public static final int MAIN_BUTTON1_YLOCATION = 350;
    public static final int MAIN_BUTTON2_YLOCATION = 475;
    public static final int MAIN_BUTTON3_YLOCATION = 600;

    public static final int OPT_BUTTON1_YLOCATION = 350;
    public static final int OPT_BUTTON2_YLOCATION = 475;
    public static final int OPT_BUTTON3_YLOCATION = 600;
    public static final int OPT_BUTTON4_YLOCATION = 725;

    public static final int PAUSE_BUTTON1_YLOCATION = 350;
    public static final int PAUSE_BUTTON2_YLOCATION = 475;
    public static final int PAUSE_BUTTON3_YLOCATION = 600;

    public static final int MUSIC_BUTTON1_YLOCATION = 350;
    public static final int MUSIC_BUTTON2_YLOCATION = 475;

    public static final int AI_BUTTON1_YLOCATION = 350;
    public static final int AI_BUTTON2_YLOCATION = 475;
    public static final int AI_BUTTON3_YLOCATION = 600;

    public Menu(SnakePanel sp) {
        this.sp = sp;
        sp.setLayout(null);
        mainArray = buildMain();
        optArray = buildOptions();
        pauseArray = buildPause();
        musicArray = buildMusic();
        aiArray = buildAI();
        gameoverArray = buildGameover();
        visibleArray = mainArray;
    }

    public Component[] buildMain() {
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

        MyButton playButton = makeButton("PLAY", MAIN_BUTTON1_YLOCATION, 77, 63);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.state = SnakeGame.STATE.GAME;
                sp.removeAll();
                sp.requestFocusInWindow();
            }
        });
        tempList.add(playButton);

        MyButton optButton = makeButton("OPTIONS", MAIN_BUTTON2_YLOCATION, 41, 63);
        optButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                sp.requestFocusInWindow();
                for (int i = 0; i < optArray.length; i++) {
                    sp.add(optArray[i]);
                }
                visibleArray = optArray;
            }
        });
        tempList.add(optButton);

        MyButton quitButton = makeButton("QUIT", MAIN_BUTTON3_YLOCATION, 77, 63);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        tempList.add(quitButton);
        Component[] tempArray = new Component[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            tempArray[i] = tempList.get(i);
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

        MyButton songButton = makeButton("SONG", OPT_BUTTON1_YLOCATION, 77, 63);
        songButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                sp.requestFocusInWindow();
                for (int i = 0; i < musicArray.length; i++) {
                    sp.add(musicArray[i]);
                }
                visibleArray = musicArray;
            }
        });
        tempList.add(songButton);

        MyButton AIButton = makeButton("AI", OPT_BUTTON2_YLOCATION, 77, 63);
        AIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                sp.requestFocusInWindow();
                for (int i = 0; i < aiArray.length; i++) {
                    sp.add(aiArray[i]);
                }
                visibleArray = aiArray;
            }
        });
        tempList.add(AIButton);

        MyButton playButton = makeButton("PLAY", OPT_BUTTON3_YLOCATION, 77, 63);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.state = SnakeGame.STATE.GAME;
                sp.removeAll();
                sp.requestFocusInWindow();
                
            }
        });
        tempList.add(playButton);

        MyButton backButton = makeButton("BACK", OPT_BUTTON4_YLOCATION, 77, 63);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                sp.requestFocusInWindow();
                for (int i = 0; i < mainArray.length; i++) {
                    sp.add(mainArray[i]);
                }
                visibleArray = mainArray;
            }
        });
        tempList.add(backButton);

        Component[] tempArray = new Component[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            tempArray[i] = tempList.get(i);
        }
        return tempArray;
    }

    public Component[] buildPause() {
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
                    SnakePanel.unloadMusic();
                    SnakePanel.loadMusic(musicIndex);//loads the WAV file to play later, prevents lag
                } catch (Exception ex) {
                    Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tempList.add(nextSong);

        MyButton prevSong = makeButton("<", 350, 20, 20);
        prevSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) - 250, 400, 50, 50);
        prevSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicIndex--;
                if (musicIndex == -1) {
                    musicIndex = SnakePanel.musicArray.length - 1;
                }
                currentSong.setText(SnakePanel.musicArray[musicIndex]);
                try {
                    SnakePanel.unloadMusic();
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
                sp.clearGame();
            }
        });
        tempList.add(backButton);

        Component[] tempArray = new Component[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            tempArray[i] = tempList.get(i);
        }
        return tempArray;
    }

    public Component[] buildMusic() {
        ArrayList<Component> tempList = new ArrayList<>();

        final JLabel title = new JLabel("MUSIC!!!");
        title.setFont(fontLoader(200F));
        title.setForeground(Color.white);
        title.setBounds(sp.getScreenWidth() / 22, 0, 2500, 250);
        tempList.add(title);

        final JLabel currentSong = new JLabel(SnakePanel.musicArray[musicIndex]);
        currentSong.setForeground(Color.white);
        currentSong.setFont(fontLoader(20F));
        currentSong.setLayout(null);
        currentSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) - 125, MUSIC_BUTTON1_YLOCATION, 250, 100);
        tempList.add(currentSong);

        MyButton nextSong = makeButton(">", 350, 20, 60);
        nextSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) + 200, MUSIC_BUTTON1_YLOCATION, 50, 70);
        nextSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicIndex++;
                if (musicIndex == SnakePanel.musicArray.length) {
                    musicIndex = 0;
                }
                currentSong.setText(SnakePanel.musicArray[musicIndex]);
                try {
                    SnakePanel.unloadMusic();
                    SnakePanel.loadMusic(musicIndex);//loads the WAV file to play later, prevents lag
                } catch (Exception ex) {
                    Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tempList.add(nextSong);

        MyButton prevSong = makeButton("<", 350, 20, 60);
        prevSong.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) - 250, MUSIC_BUTTON1_YLOCATION, 50, 70);
        prevSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicIndex--;
                if (musicIndex == -1) {
                    musicIndex = SnakePanel.musicArray.length - 1;
                }
                currentSong.setText(SnakePanel.musicArray[musicIndex]);
                try {
                    SnakePanel.unloadMusic();
                    SnakePanel.loadMusic(musicIndex);//loads the WAV file to play later, prevents lag
                } catch (Exception ex) {
                    Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tempList.add(prevSong);

        MyButton backButton = makeButton("BACK", MUSIC_BUTTON2_YLOCATION, 77, 63);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                sp.requestFocusInWindow();
                for (int i = 0; i < optArray.length; i++) {
                    sp.add(optArray[i]);
                }
                visibleArray = optArray;
            }
        });
        tempList.add(backButton);

        Component[] tempArray = new Component[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            tempArray[i] = tempList.get(i);
        }
        return tempArray;
    }

    public Component[] buildAI() {
        ArrayList<Component> tempList = new ArrayList<>();

        final JLabel title = new JLabel("AI!!!");
        title.setFont(fontLoader(200F));
        title.setForeground(Color.white);
        title.setBounds(sp.getScreenWidth() / 22, 0, 2500, 250);
        tempList.add(title);

        final JLabel AI1 = new JLabel("AI_M1000");
        AI1.setForeground(Color.white);
        AI1.setFont(fontLoader(20F));
        AI1.setLayout(null);
        AI1.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) - 150, MUSIC_BUTTON1_YLOCATION, 250, 100);
        tempList.add(AI1);
        
        final JLabel NUM_AI1 = new JLabel(Integer.toString(sp.NUM_AI_M1000));
        NUM_AI1.setForeground(Color.white);
        NUM_AI1.setFont(fontLoader(20F));
        NUM_AI1.setLayout(null);
        NUM_AI1.setBounds((int) ((SnakePanel.getScreenWidth()) / 2)+65, MUSIC_BUTTON1_YLOCATION, 250, 100);
        tempList.add(NUM_AI1);

        MyButton plusAI = makeButton("+", 350, 20, 60);
        plusAI.setBounds((int) ((SnakePanel.getScreenWidth()) / 2) + 95, MUSIC_BUTTON1_YLOCATION, 50, 70);
        plusAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //sp.NUM_AI_M1000++;
                System.out.println(sp.NUM_AI_M1000);
                NUM_AI1.setText(Integer.toString(sp.NUM_AI_M1000));
            }
        });
        tempList.add(plusAI);
        
        MyButton minusAI = makeButton("-", 350, 20, 60);
        minusAI.setBounds((int) ((SnakePanel.getScreenWidth()) / 2-5), MUSIC_BUTTON1_YLOCATION, 50, 70);
        minusAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sp.NUM_AI_M1000 != 0){
                //sp.NUM_AI_M1000--;
                NUM_AI1.setText(Integer.toString(sp.NUM_AI_M1000));
                }
            }
        });
        tempList.add(minusAI);

        MyButton backButton = makeButton("BACK", MUSIC_BUTTON2_YLOCATION, 77, 63);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.removeAll();
                sp.requestFocusInWindow();
                for (int i = 0; i < optArray.length; i++) {
                    sp.add(optArray[i]);
                }
                visibleArray = optArray;
            }
        });
        tempList.add(backButton);

        Component[] tempArray = new Component[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            tempArray[i] = tempList.get(i);
        }
        return tempArray;
    }
    
    public Component[] buildGameover() {
        ArrayList<Component> tempList = new ArrayList<>();

        final JLabel title = new JLabel("   GAME");
        title.setFont(fontLoader(150F));
        title.setForeground(Color.white);
        title.setBounds((-sp.getScreenWidth() / 15) + 25, 50, 2500, 250);
        tempList.add(title);
        
        final JLabel title1 = new JLabel("  OVER");
        title1.setFont(fontLoader(150F));
        title1.setForeground(Color.white);
        title1.setBounds((sp.getScreenWidth() / 22) + 25, 250, 2500, 250);
        tempList.add(title1);
        

        MyButton backButton = makeButton("MAIN", 600, 77, 63);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.clearGame();
            }
        });
        tempList.add(backButton);

        Component[] tempArray = new Component[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            tempArray[i] = tempList.get(i);
        }
        return tempArray;
    }


    public void pause() {
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
        button.setRolloverEnabled(false);
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
        protected void paintBorder(Graphics g) {

        }
    }
}
