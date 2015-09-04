//WE NEED TO CLEAN THE CODE UP AND MAKE IT MORE READABLE!!!!!

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import javax.swing.*;
import java.awt.*;
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
    static SnakePanel snakePanel;
    static JLabel statusLabel;
    static JFrame frame;
    
    static boolean ingame = true;

    public static enum STATE {

        MENU, GAME
    };

    public static STATE state = STATE.MENU;

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
    static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static void main(String[] args) {
        while(true){
            frame = new JFrame("Snake Game");

            snakePanel = new SnakePanel();

            frame.setUndecorated(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(snakePanel);
            frame.pack();
            frame.setVisible(true);

            while (ingame) {

                if (state == STATE.MENU) {
                try {
                            Thread.sleep(FRAME_RATE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        frame.repaint();
                } else if (state == STATE.GAME) {
                    statusLabel = new JLabel("", SwingConstants.CENTER);
                    fontLoader();
                    statusLabel.setFont(customFont);
                    statusLabel.setForeground(TEXT_COLOR);
                    statusLabel.setBounds(0, -30, (int) SnakePanel.WINDOW_WIDTH, 100);
                    snakePanel.add(statusLabel);

                    frame.pack();

                    frame.setVisible(true);

                    updateScoreboard();

                    while (state == STATE.GAME) {
                        snakePanel.update();

                        try {
                            Thread.sleep(FRAME_RATE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        frame.repaint();
                    }

                }

            }
            ingame = true;
        }
    }

    public static void updateScoreboard() {
        //FLICKERS
        statusLabel.setText("");
        for (int i = 0; i < snakePanel.snakes.length; i++) {
            statusLabel.setText(statusLabel.getText() + " " + snakePanel.snakes[i].getName() + ":" + snakePanel.snakes[i].getScore());
        }
    }

    /////
    public static class MyJLabel extends JLabel {

        public static final int MARQUEE_SPEED_DIV = 1;
        public static final int REPAINT_WITHIN_MS = 5;

        /**
         *
         */
        private static final long serialVersionUID = -7737312573505856484L;

        /**
         *
         */
        public MyJLabel() {
            super();
            // TODO Auto-generated constructor stub
        }

        /**
         * @param image
         * @param horizontalAlignment
         */
        public MyJLabel(Icon image, int horizontalAlignment) {
            super(image, horizontalAlignment);
            // TODO Auto-generated constructor stub
        }

        /**
         * @param image
         */
        public MyJLabel(Icon image) {
            super(image);
            // TODO Auto-generated constructor stub
        }

        /**
         * @param text
         * @param icon
         * @param horizontalAlignment
         */
        public MyJLabel(String text, Icon icon, int horizontalAlignment) {
            super(text, icon, horizontalAlignment);
            // TODO Auto-generated constructor stub
        }

        /**
         * @param text
         * @param horizontalAlignment
         */
        public MyJLabel(String text, int horizontalAlignment) {
            super(text, horizontalAlignment);
            // TODO Auto-generated constructor stub
        }

        /**
         * @param text
         */
        public MyJLabel(String text) {
            super(text);
        }

        /* (non-Javadoc)
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        @Override
        protected void paintComponent(Graphics g) {
            g.translate((int) ((System.currentTimeMillis() / MARQUEE_SPEED_DIV) % (getWidth() * 2)) - getWidth(), 0);
            super.paintComponent(g);
            repaint(REPAINT_WITHIN_MS);
        }
    }
    /////

    public static void pause() {
        state = STATE.MENU;
        snakePanel.menu.pause();
        snakePanel.menu.renderMenu();
        try {
            snakePanel.stopMusic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.repaint();

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
