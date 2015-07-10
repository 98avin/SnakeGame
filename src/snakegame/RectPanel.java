/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.swing.*;

/**
 *
 * @author 11768
 */
public class RectPanel extends JPanel {

    // <<DATA>>
    int colorOrder;
    boolean colorDecreaseFlag;
    public static final int NUM_BACK_COLORS = 3;
    public static final int MAX_COLOR_VALUE = 255;
    public static final int MIN_COLOR_VALUE = 0;
    public static final int RAVE_THRESHOLD = 5;

    public static double WINDOW_WIDTH = 800.0;
    public static double WINDOW_HEIGHT = 800.0;

    private int backgroundColors[];
    private Rect2d head;

    private Rect2d back;

    double snakeWidth;

    public static Snake bernie;

    public boolean music;

    public static ArrayList<Rect2d> food;

    final double moveAccel = 1000.0;
    public static KeysPressed keysPressed;
    static ImageIcon EXPLODE;

    private static Sequencer sequencer;
    
    static Rect2d cambounds;

    // <<CONSTRUCTOR>>
    public RectPanel() {

        try {
            loadMusic();//loads the MIDI file to play later, prevents lag
        } catch (Exception ex) {
            Logger.getLogger(RectPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Initialize default background colors
        colorDecreaseFlag = false;
        backgroundColors = new int[NUM_BACK_COLORS];
        for (int i = 0; i < NUM_BACK_COLORS; i++) {
            backgroundColors[i] = 0;
        }
        colorOrder = 0;

        music = false;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //sets window to fit screen

        WINDOW_WIDTH = screenSize.getWidth() - 10;
        WINDOW_HEIGHT = screenSize.getHeight() - 72;

        setPreferredSize(new Dimension((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT));
        keysPressed = new KeysPressed();

        bernie = new Snake();

       

        back = new Rect2d(-500, -500, 10000, 10000);

       
        food = new ArrayList<Rect2d>();
        
        cambounds = new Rect2d(100, 100, screenSize.width - 200, screenSize.height - 250);

        for (int i = 1; i < 0; i++) {
            bernie.addS(new Rect2d(30.0 + (i * 30), 170.0, bernie.getWidth(), bernie.getWidth()));
        }

        for (int i = 0; i < bernie.getSSize(); i++) {
            bernie.addH(new SquareCoords((int) bernie.getRect(i).getLeft(), (int) bernie.getRect(i).getTop()));
        }

        for (int i = 1; i < 10; i++) {
            food.add(new Rect2d(random_number(0, (int) WINDOW_WIDTH), random_number(0, (int) WINDOW_HEIGHT), 10, 10));
        }

        this.setFocusable(true);

        this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                switch (keycode) {
                    case java.awt.event.KeyEvent.VK_W:  // the keycode for W (Virtual Key)
                        keysPressed.Up = true;
                        break;
                    case java.awt.event.KeyEvent.VK_S:
                        keysPressed.Down = true;
                        break;
                    case java.awt.event.KeyEvent.VK_A:
                        keysPressed.Left = true;
                        break;
                    case java.awt.event.KeyEvent.VK_D:
                        keysPressed.Right = true;
                        break;
                }
            }

            public void keyReleased(KeyEvent e) {
                int keycode = e.getKeyCode();
                switch (keycode) {
                    case java.awt.event.KeyEvent.VK_W:  // the keycode for W (Virtual Key)
                        keysPressed.Up = false;
                        break;
                    case java.awt.event.KeyEvent.VK_S:
                        keysPressed.Down = false;
                        break;
                    case java.awt.event.KeyEvent.VK_A:
                        keysPressed.Left = false;
                        break;
                    case java.awt.event.KeyEvent.VK_D:
                        keysPressed.Right = false;
                        break;
                }
            }
        });
    }

    // <<FILLRECT>>   (a static ‘helper’ method to draw a Rect2d)
    static void fillRect(Graphics g, Rect2d rect, Color c) {
        int x = (int) rect.getLeft();
        int y = (int) rect.getTop();
        int w = (int) rect.getWidth();
        int h = (int) rect.getHeight();
        g.setColor(c);
        g.fillRect(x, y, w, h);
    }

    Color insane = new Color(0, 0, 0);

    private int getRandomColorValue() {
        Random rnd = new Random();
        return Math.abs(rnd.nextInt()) % (MAX_COLOR_VALUE);
    }

    public void paintComponent(Graphics g) {
        if (!bernie.isLiving()) {
            try {
                stopMusic();
            } catch (Exception ex) {
                Logger.getLogger(RectPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            fillRect(g, back, Color.GRAY);
            for (int i = 1; i < bernie.getSSize(); i++) {
                fillRect(g, bernie.getRect(i), Color.BLUE);
            }

            for (int i = 0; i < food.size(); i++) {
                fillRect(g, food.get(i), Color.white);
            }
            return;
        }

        //this.backgroundColors[1] = 10;
        //this.backgroundColors[2] = 250;
        if (colorOrder < 2) {
            //max red
            if (this.backgroundColors[colorOrder] < MAX_COLOR_VALUE && colorDecreaseFlag == false) {
                this.backgroundColors[colorOrder]++;
            } //max green/blue
            else if (this.backgroundColors[colorOrder + 1] < MAX_COLOR_VALUE) {
                this.backgroundColors[colorOrder + 1]++;
            } //mins red/green
            else if (this.backgroundColors[colorOrder] > 0) {
                colorDecreaseFlag = true;
                this.backgroundColors[colorOrder]--;
            } else {
                colorDecreaseFlag = false;
                colorOrder++;
            }
        } else if (colorOrder == 2) {
            //re-maxes red 
            if (this.backgroundColors[0] < MAX_COLOR_VALUE) {
                this.backgroundColors[0]++;
            }//mins blue 
            else if (this.backgroundColors[colorOrder] > 0) {
                colorDecreaseFlag = true;
                this.backgroundColors[colorOrder]--;
            } else {
                colorDecreaseFlag = false;
                colorOrder = 0;
            }
        }

        //this.backgroundColors[0] = (this.backgroundColors[0] + 1) % (MAX_COLOR_VALUE);
        //this.backgroundColors[1] = (this.backgroundColors[1] + 1) % (MAX_COLOR_VALUE);
        //this.backgroundColors[2] = (this.backgroundColors[2] + 1) % (MAX_COLOR_VALUE);
        insane = new Color(this.backgroundColors[0],
                this.backgroundColors[1],
                this.backgroundColors[2]);
        //    System.out.println(insane.toString());

        if (bernie.getSSize() > RAVE_THRESHOLD) {
            fillRect(g, back, insane);

            if (music == false) {
                try {
                    playMusic();
                } catch (Exception ex) {
                    Logger.getLogger(RectPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                music = true;
            }

        } else {
            fillRect(g, back, Color.GRAY);

        }

        // Set snake's head to black
        fillRect(g, bernie.getHead(), Color.black);

        // Fill the rest of the snake's body with colors
        for (int i = 1; i < bernie.getSSize(); i++) {

            if (bernie.getSSize() > RAVE_THRESHOLD) {
                insane = new Color(getRandomColorValue(),
                        getRandomColorValue(),
                        getRandomColorValue());
                fillRect(g, bernie.getRect(i), insane);
            } else {
                fillRect(g, bernie.getRect(i), Color.blue);
            }
        }

        for (int i = 0; i < food.size(); i++) {
            fillRect(g, food.get(i), Color.white);
        }

    }

    public void update() {
        bernie.update();
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

    public static void playMusic() throws Exception {
        sequencer.start();

        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

    }

    public static void loadMusic() throws Exception {

        // Obtains the default Sequencer connected to a default device.
        sequencer = MidiSystem.getSequencer();

        // Opens the device, indicating that it should now acquire any
        // system resources it requires and become operational.
        sequencer.open();

        // create a stream from a file
        InputStream is = new BufferedInputStream(new FileInputStream(new File("nyancat.mid")));

        // Sets the current sequence on which the sequencer operates.
        // The stream must point to MIDI file data.
        sequencer.setSequence(is);
    }

    public static void stopMusic() throws Exception {
        sequencer.stop();
    }

     static boolean checkCamBounds() {
        if (bernie.getHead().getTop() > (cambounds.getTop()) && bernie.getHead().getTop() < (cambounds.getBottom()) && bernie.getHead().getLeft() < (cambounds.getRight()) && bernie.getHead().getLeft() > (cambounds.getLeft())) {
            return true;
        } else {
            return false;
        }
    }

    static boolean checkAtUp() {
        System.out.println("------------CHECKING IF AT TOP--------------");
        int a = ((int) (bernie.getHead().getTop()));
        int b = ((int) (cambounds.getTop()));
        System.out.println(a);
        System.out.println(b);
        if (b - 50 <= a && a <= b + 50) {
            System.out.println("TRUE");
            System.out.println("-------------CHECK DONE--------------\n");
            return true;
        } else {
            System.out.println("FALSE");
            System.out.println("-------------CHECK DONE--------------\n");
            return false;
        }
    }

    static boolean checkAtDown() {
        System.out.println("------------CHECKING IF AT BOTTOM--------------");
        int a = ((int) (bernie.getHead().getTop()));
        int b = ((int) (cambounds.getBottom()));
        System.out.println(a);
        System.out.println(b);
        if (b - 50 <= a && a <= b + 50) {
            System.out.println("TRUE");
            System.out.println("-------------CHECK DONE--------------\n");
            return true;
        } else {
            System.out.println("FALSE");
            System.out.println("-------------CHECK DONE--------------\n");
            return false;
        }
    }

    static boolean checkAtLeft() {
        System.out.println("------------CHECKING IF AT LEFT--------------");
        int a = ((int) (bernie.getHead().getLeft()));
        int b = ((int) (cambounds.getLeft()));
        System.out.println(a);
        System.out.println(b);
        if (b - 50 <= a && a <= b + 50) {
            System.out.println("TRUE");
            System.out.println("-------------CHECK DONE--------------\n");
            return true;
        } else {
            System.out.println("FALSE");
            System.out.println("-------------CHECK DONE--------------\n");
            return false;
        }
    }

    static boolean checkAtRight() {
        System.out.println("------------CHECKING IF AT RIGHT--------------");
        int a = ((int) (bernie.getHead().getLeft()));
        int b = ((int) (cambounds.getRight()));
        System.out.println(a);
        System.out.println(b);
        if (b - 50 <= a && a <= b + 50) {
            System.out.println("TRUE");
            System.out.println("-------------CHECK DONE--------------\n");
            return true;
        } else {
            System.out.println("FALSE");
            System.out.println("-------------CHECK DONE--------------\n");
            return false;
        }
    }

}
