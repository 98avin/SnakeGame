/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;

/**
 *
 * @author 11768
 */
public class SnakePanel extends JPanel {

    public static boolean starfield = true;

    // <<DATA>>
    int colorOrder;
    boolean colorDecreaseFlag;
    
    public static Color COLOR_RAINBOW_CYCLE = new Color(0, 0, 0);
    public static Color COLOR_RAINBOW_CYCLE2 = new Color(0, 0, 0);
    
    public static final int NUM_BACK_COLORS = 3;
    public static final int MAX_COLOR_VALUE = 255;
    public static final int MIN_COLOR_VALUE = 0;
    public static final int COLOR_INCREMENT = 10;

    public static final Color DEFAULT_BACKGROUND_COLOR = Color.black;
    
    public static final int MUSIC_THRESHOLD = 0;

    public static final Color FOOD_COLOR = Color.white;
    public static final Color PLAYER_SNAKE_COLOR = Color.blue;
    public static final Color AI_SNAKE_COLOR = Color.red;
    public static final int NUMBER_OF_FOOD = 1;

    public static double WINDOW_WIDTH = 800.0;
    public static double WINDOW_HEIGHT = 800.0;

    private static int backgroundColors[];
    private static Rect2d back;

    double snakeWidth;

    public static final int NUM_PLAYERS = 1;
    public static final int NUM_AI_M1000 = 1;
    public static final int NUM_AI_M2000 = 1;
    private final int[] NUMBER_SNAKES = {NUM_PLAYERS, NUM_AI_M1000, NUM_AI_M2000};
    public Snake snakes[] = new Snake[(NUM_PLAYERS + NUM_AI_M1000 + NUM_AI_M2000)];
    //Just a large list of names for AI snakes.
    String[] modelName = {"Berninator", "Bern-OS", "Robo-Bernie", "Bernie-Prime", "Star Bern", "Telebernie", "iBernie", "B.E.R.N.I.E", "Bern Machine", "B3RN1E"};

    public static final int WINNING_SNAKE_WIDTH = 10;
    public static final int LOSING_SNAKE_WIDTH = 10;

    public boolean music;
    public static String[] musicArray = {"sandstorm1.wav", "remix10.wav", "MEGA_MAN.wav", "9ts.wav"};

    public static ArrayList<Rect2d> food;

    final double moveAccel = 1000.0;
    public static KeysPressed keysPressed;
    static ImageIcon EXPLODE;

    static AudioInputStream audioIn;
    static Clip clip;

    static Menu menu;

    //static Rect2d cambounds; //CAMERA WINDOW(Snake touches the edge of this to begin "scrolling")
    // <<CONSTRUCTOR>>
    public SnakePanel() {

        menu = new Menu(this);

        menu.buildMain();
        
        constructSnake();

        // Initialize colors for rainbow cycle
        colorDecreaseFlag = false;
        backgroundColors = new int[NUM_BACK_COLORS];
        for (int i = 0; i < NUM_BACK_COLORS; i++) {
            backgroundColors[i] = 0;
        }
        colorOrder = 0;

        music = false;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //sets window to fit screen

        WINDOW_WIDTH = screenSize.getWidth();
        WINDOW_HEIGHT = screenSize.getHeight();
        setPreferredSize(new Dimension((int) WINDOW_WIDTH, (int) WINDOW_HEIGHT));
        keysPressed = new KeysPressed();

        back = new Rect2d(-500, -500, 10000, 10000);

        food = new ArrayList<>();

        //cambounds = new Rect2d(100, 100, screenSize.width - 200, screenSize.height - 250);
        for (int i = 0; i < NUMBER_OF_FOOD; i++) {
            food.add(new Rect2d(random_number(0, 1000), random_number(0, 500), 10, 10));
        }

        this.setFocusable(true);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                switch (keycode) {
                    case java.awt.event.KeyEvent.VK_W:
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
                    case java.awt.event.KeyEvent.VK_UP:
                        keysPressed.Up = true;
                        break;
                    case java.awt.event.KeyEvent.VK_DOWN:
                        keysPressed.Down = true;
                        break;
                    case java.awt.event.KeyEvent.VK_LEFT:
                        keysPressed.Left = true;
                        break;
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        keysPressed.Right = true;
                        break;
                    case java.awt.event.KeyEvent.VK_ESCAPE:
                        SnakeGame.pause();
                        /*for(int i = 0;i<snakes.length;i++){
                         snakes[i].die();
                         }*/
                        break;
                    case java.awt.event.KeyEvent.VK_SPACE:
                        keysPressed.Random = !keysPressed.Random;
                        break;
                }
            }

            @Override
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
                    case java.awt.event.KeyEvent.VK_UP:  // the keycode for W (Virtual Key)
                        keysPressed.Up = false;
                        break;
                    case java.awt.event.KeyEvent.VK_DOWN:
                        keysPressed.Down = false;
                        break;
                    case java.awt.event.KeyEvent.VK_LEFT:
                        keysPressed.Left = false;
                        break;
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        keysPressed.Right = false;
                        break;
                    case java.awt.event.KeyEvent.VK_ESCAPE:
                        break;
                }
            }
        });
    }

    void constructSnake() {
        for (int j = 0; j < NUMBER_SNAKES.length; j++) {
            for (int i = 0; i < NUMBER_SNAKES[j]; i++) {
                switch (j) {
                    case 0:
                        snakes[i] = new Snake(PLAYER_SNAKE_COLOR, "Bernie");
                        break;
                    case 1:
                        snakes[NUM_PLAYERS + i] = new AISnake(AI_SNAKE_COLOR, (modelName[random_number(0, this.modelName.length)] + " " + random_number(0, 9000)));
                        break;
                    case 2:
                        snakes[NUM_PLAYERS + NUM_AI_M1000 + i] = new AISnake2(AI_SNAKE_COLOR, (modelName[random_number(0, this.modelName.length)] + " " + random_number(0, 9000)));
                        break;
                }
            }
        }
        for (int i = 0; i < snakes.length; i++) {
            buildSnake(snakes[i]);
        }
    }

    void buildSnake(Snake snake) {
        for (int i = 1; i < 0; i++) {
            snake.addS(new Rect2d(30.0 + (i * 30), 170.0, snake.getWidth(), snake.getWidth()));
        }

        for (int i = 0; i < snake.getSSize(); i++) {
            snake.addH(new SquareCoords((int) snake.getRect(i).getLeft(), (int) snake.getRect(i).getTop()));
        }

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

    //CREDIT TO CHRIS WELLONS --- http://nullprogram.com/blog/2011/06/13/
    public static final int STAR_SEED = 0x9d2c5680;
    public static int STAR_TILE_SIZE = 1000;

    public void drawStars(Graphics2D g, int xoff, int yoff, int starscale) {

        int size = STAR_TILE_SIZE / starscale;
        int w = 5000;
        int h = 3000;

        /* Top-left tile's top-left position. */
        int sx = ((xoff - w / 2) / size) * size - size;
        int sy = ((yoff - h / 2) / size) * size - size;

        /* Draw each tile currently in view. */
        for (int i = sx; i <= w + sx + size * 3; i += size) {
            for (int j = sy; j <= h + sy + size * 3; j += size) {
                int hash = mix(STAR_SEED, i, j);
                for (int n = 0; n < 3; n++) {
                    int px = (hash % size) + (i - xoff);
                    hash >>= 3;
                    int py = (hash % size) + (j - yoff);
                    hash >>= 3;
                    g.drawLine(px, py, px, py);
                }
            }
        }
    }

    /**
     * Robert Jenkins' 96 bit Mix Function.
     */
    private static int mix(int a, int b, int c) {
        a = a - b;
        a = a - c;
        a = a ^ (c >>> 13);
        b = b - c;
        b = b - a;
        b = b ^ (a << 8);
        c = c - a;
        c = c - b;
        c = c ^ (b >>> 13);
        a = a - b;
        a = a - c;
        a = a ^ (c >>> 12);
        b = b - c;
        b = b - a;
        b = b ^ (a << 16);
        c = c - a;
        c = c - b;
        c = c ^ (b >>> 5);
        a = a - b;
        a = a - c;
        a = a ^ (c >>> 3);
        b = b - c;
        b = b - a;
        b = b ^ (a << 10);
        c = c - a;
        c = c - b;
        c = c ^ (b >>> 15);
        return c;
    }
    /////////////

    public boolean checkLiving(Snake snake, Graphics g) {
        if (!snake.isLiving()) {
            //Convert snake to food
            for (int i = 0; i < food.size(); i++) {
                fillRect(g, food.get(i), FOOD_COLOR);
            }
            return true;
        }
        return false;
    }

    public boolean checkAllLiving(Snake[] snakes, Graphics g) {
        int temp = 0;
        for (int i = 0; i < snakes.length; i++) {
            if (checkLiving(snakes[i], g)) {
                temp++;
            }
        }
        return (temp == snakes.length);
    }

    public static int starx = 0;
    public static int stary = 0;
    public static int starscale = 10;

    public void clearGame() {
     try {
     stopMusic();
     unloadMusic();
     } catch (Exception ex) {
     Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
     }
     this.removeAll();
     SnakeGame.frame.removeAll();
     SnakeGame.ingame = false;
     }

    @Override
    public void paintComponent(Graphics g) {
        if (SnakeGame.state == SnakeGame.STATE.MENU) {
            fillRect(g, back, DEFAULT_BACKGROUND_COLOR);
            moveStars(g);
            menu.renderMenu();
        } else if (SnakeGame.state == SnakeGame.STATE.GAME) {
            //this needs fixin (it causes the game to be unable to play mutiple times)
            if (checkLiving(snakes[0], g)) {
                Menu.visibleArray = Menu.gameoverArray;
                SnakeGame.state = SnakeGame.STATE.MENU;
                for (int i = 0; i < snakes.length; i++) {
                    snakes[i].reset();
                }
                for (int i = 0; i < NUMBER_OF_FOOD; i++) {
                    food.add(new Rect2d(random_number(0, 1000), random_number(0, 500), 10, 10));
                }
            }
            fillRect(g, back, DEFAULT_BACKGROUND_COLOR);
            //RAINBOW CYCLE COLOR
            backColorFlow();

            COLOR_RAINBOW_CYCLE = new Color(this.backgroundColors[0],
                    this.backgroundColors[1],
                    this.backgroundColors[2]);
            COLOR_RAINBOW_CYCLE2 = new Color(this.backgroundColors[1],
                    this.backgroundColors[2],
                    this.backgroundColors[0]);
            //COLOR RAINBOW CYCLE END

            if (snakes[0].getScore() >= MUSIC_THRESHOLD || this.isBigger(snakes[0], 0)) {
                try {
                    playMusic();
                } catch (Exception ex) {
                    Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    stopMusic();
                } catch (Exception ex) {
                    Logger.getLogger(SnakePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //g.setColor(Color.white);
            //g.drawLine((int) berninator.getHead().getCenter().x, (int) berninator.getHead().getCenter().y, (int) berninator.targettemp.getCenter().x, (int) berninator.targettemp.getCenter().y);
            //fillRect(g, bernie1.vision, Color.yellow);
            //fillRect(g, bernie1.pathX, Color.blue);
            //fillRect(g, bernie1.pathY, Color.red);
            // Fill snake's body with colors
            moveStars(g);
            fillSnake(g);

            //Draw food
            for (int i = 0; i < food.size(); i++) {
                fillRect(g, food.get(i), FOOD_COLOR);
            }

        }
    }

    public void moveStars(Graphics g) {
        if (starfield) {

            starx += 1;

            STAR_TILE_SIZE = 3000;
            g.setColor(COLOR_RAINBOW_CYCLE2);
            drawStars((Graphics2D) g, starx, stary, 10);

            STAR_TILE_SIZE = 2000;
            g.setColor(Color.LIGHT_GRAY);
            drawStars((Graphics2D) g, starx / 2, stary, 5);

            STAR_TILE_SIZE = 500;
            g.setColor(Color.GRAY);
            drawStars((Graphics2D) g, starx / 3, stary, 3);

            starx++;
            STAR_TILE_SIZE = 1000;
            g.setColor(Color.DARK_GRAY);
            drawStars((Graphics2D) g, -starx / 4, stary, 15);

            starx--;
            STAR_TILE_SIZE = 10000;
            g.setColor(Color.blue);
            drawStars((Graphics2D) g, -starx / 5, stary, 8);

            STAR_TILE_SIZE = 6000;
            g.setColor(Color.red);
            drawStars((Graphics2D) g, -starx / 3, stary, 5);

            STAR_TILE_SIZE = 5000;
            g.setColor(Color.yellow);
            drawStars((Graphics2D) g, -starx / 4, stary, 7);

            STAR_TILE_SIZE = 7000;
            g.setColor(Color.MAGENTA);
            drawStars((Graphics2D) g, -starx / 6, stary, 6);

        }
    }

    public boolean isBigger(Snake snake, int index) {
        int tempCount = 0;
        for (int i = 0; i < snakes.length; i++) {
            if (snake.getScore() > snakes[i].getScore()) {
                tempCount++;
            }
        }

        return (tempCount == snakes.length - 1);
    }

    public void fillSnake(Graphics g) {
        for (int i = 0; i < snakes.length; i++) {
            for (int j = 0; j < snakes[i].getSSize(); j++) {
                if (isBigger(snakes[i], i)) {
                    snakes[i].snakeWidth = WINNING_SNAKE_WIDTH;
                    fillRect(g, snakes[i].getRect(j), COLOR_RAINBOW_CYCLE);
                } else {
                    snakes[i].snakeWidth = LOSING_SNAKE_WIDTH;
                    fillRect(g, snakes[i].getRect(j), snakes[i].getColor());
                }
            }
        }
    }

    public void update() {
        for (int i = 0; i < snakes.length; i++) {
            snakes[i].update();
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

    public static void playMusic() throws Exception {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void loadMusic(int index) throws Exception {
        File file = new File(musicArray[index]);
        audioIn = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioIn.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioIn);
    }

    public static void unloadMusic() {
        clip.close();
    }

    public static void stopMusic() throws Exception {
        clip.stop();
    }

    void backColorFlow() {
        if (colorOrder < 2) {
            //max red
            if (this.backgroundColors[colorOrder] < MAX_COLOR_VALUE && colorDecreaseFlag == false) {
                if (this.backgroundColors[colorOrder] + COLOR_INCREMENT < MAX_COLOR_VALUE) {
                    this.backgroundColors[colorOrder] += COLOR_INCREMENT;
                } else {
                    this.backgroundColors[colorOrder] = MAX_COLOR_VALUE;
                }
            } //max green/blue
            else if (this.backgroundColors[colorOrder + 1] < MAX_COLOR_VALUE) {
                if (this.backgroundColors[colorOrder + 1] + COLOR_INCREMENT < MAX_COLOR_VALUE) {
                    this.backgroundColors[colorOrder + 1] += COLOR_INCREMENT;
                } else {
                    this.backgroundColors[colorOrder + 1] = MAX_COLOR_VALUE;
                }
            } //mins red/green
            else if (this.backgroundColors[colorOrder] > 0) {
                colorDecreaseFlag = true;
                if (this.backgroundColors[colorOrder] - COLOR_INCREMENT >= 0) {
                    this.backgroundColors[colorOrder] -= COLOR_INCREMENT;
                } else {
                    this.backgroundColors[colorOrder] = MIN_COLOR_VALUE;
                }
            } else {
                colorDecreaseFlag = false;
                colorOrder++;
            }
        } else if (colorOrder == 2) {
            //re-maxes red 
            if (this.backgroundColors[0] < MAX_COLOR_VALUE) {
                if (this.backgroundColors[0] + COLOR_INCREMENT < MAX_COLOR_VALUE) {
                    this.backgroundColors[0] += COLOR_INCREMENT;
                } else {
                    this.backgroundColors[0] = MAX_COLOR_VALUE;
                }
            }//mins blue 
            else if (this.backgroundColors[colorOrder] > MIN_COLOR_VALUE) {
                colorDecreaseFlag = true;
                if (this.backgroundColors[colorOrder] - COLOR_INCREMENT > 0) {
                    this.backgroundColors[colorOrder] -= COLOR_INCREMENT;
                } else {
                    this.backgroundColors[colorOrder] = MIN_COLOR_VALUE;
                }
            } else {
                colorDecreaseFlag = false;
                colorOrder = 0;
            }
        }
    }

    static void defineScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //sets window to fit screen

        WINDOW_WIDTH = screenSize.getWidth() - 10;
        WINDOW_HEIGHT = screenSize.getHeight() - 72;
    }

    public static int getScreenWidth() {
        defineScreen();
        return (int) WINDOW_WIDTH;
    }

    public static int getScreenHeight() {
        defineScreen();
        return (int) WINDOW_HEIGHT;
    }

    //FUNCTIONS NEEDED FOR CAMERA SCROLLING
    /*static boolean checkCamBounds() {
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
     }*/
}
