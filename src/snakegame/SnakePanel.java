/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author cs1
 */
public class SnakePanel extends JPanel {

    int board[];
    int NumRows, NumCols;
    int squareSize;
    KeysPressed keysPressed;
    KeysPressed2 keysPressed2;
    ArrayList<SquareCoords> food;
    Rect2d cambounds;

    static int colorOrder;
    boolean colorDecreaseFlag;
    public static final int NUM_BACK_COLORS = 3;
    public static final int MAX_COLOR_VALUE = 150;
    public static final int MIN_COLOR_VALUE = 100;
    private int backgroundColors[];
    static Color insane = new Color(0,0,0);

    static Snake bernie;

    public SnakePanel(int rows, int cols, int squareSize) {

        colorDecreaseFlag = false;
        backgroundColors = new int[NUM_BACK_COLORS];
        for (int i = 0; i < NUM_BACK_COLORS; i++) {
            backgroundColors[i] = 0;
        }
        colorOrder = 0;

        keysPressed = new KeysPressed();
        keysPressed2 = new KeysPressed2();
        NumRows = rows;
        NumCols = cols;
        this.squareSize = squareSize;
        board = new int[NumRows * NumCols];
        for (int i = 0; i < NumRows * NumCols; i++) {
            board[i] = 0;
        }
        //setPreferredSize(new Dimension(NumCols * squareSize + 1, squareSize * NumRows + 1));

        this.setFocusable(true);
        this.addKeyListener(keysPressed);
        this.addKeyListener(keysPressed2);
        food = new ArrayList<SquareCoords>();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        cambounds = new Rect2d(100, 100, screenSize.width - 200, screenSize.height - 250);
        System.out.println(cambounds.getTop());
        System.out.println(cambounds.getBottom());
        System.out.println(cambounds.getLeft());
        System.out.println(cambounds.getRight());
        bernie = new Snake(((NumCols + 500) / squareSize), ((NumRows + 200) / squareSize), Direction.Right, 1);
    }
    

    public int readSquare(int col, int row) {
        if (col < 0) {
            return -1;
        }
        if (col >= NumCols) {
            return -2;
        }
        if (row < 0) {
            return -3;
        }
        if (row >= NumRows) {
            return -4;
        }
        return board[col + row * NumCols];
    }

    public void writeSquare(int col, int row, int c) {
        if (row < 0 || row >= NumRows || col < 0 || col >= NumCols) {
            return;
        }
        board[NumCols * row + col] = c;
    }

    public void print() {
        for (int row = 0; row < NumRows; row++) {
            for (int col = 0; col < NumCols; col++) {
                System.out.print(" " + readSquare(col, row));
            }
            System.out.println();
        }
    }

    void update(Direction dir, Snake c) {
        c.update(dir, this);
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        Color colors[] = {insane, Color.black, Color.white, Color.white, Color.blue};
        for (int i = 0; i < NumRows * NumCols; i++) {
            if (board[i] != 3) {
                board[i] = 0;
            }
        }
        if (bernie.alive) {
            bernie.draw(this);
        }

        if (colorOrder < 2) {
            //max red
            if (this.backgroundColors[colorOrder] < MAX_COLOR_VALUE && colorDecreaseFlag == false) {
                this.backgroundColors[colorOrder]++;
            } //max green/blue
            else if (this.backgroundColors[colorOrder + 1] < MAX_COLOR_VALUE) {
                this.backgroundColors[colorOrder + 1]++;
            } //mins red/green
            else if (this.backgroundColors[colorOrder] > MIN_COLOR_VALUE) {
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
            else if (this.backgroundColors[colorOrder] > MIN_COLOR_VALUE) {
                colorDecreaseFlag = true;
                this.backgroundColors[colorOrder]--;
            } else {
                colorDecreaseFlag = false;
                colorOrder = 0;
            }
        }

        insane = new Color(this.backgroundColors[0],
                this.backgroundColors[1],
                this.backgroundColors[2]);

        for (int col = 0; col < NumCols; col++) {
            for (int row = 0; row < NumRows; row++) {

                g.setColor(colors[readSquare(col, row)]);
                g.fillRect(col * squareSize,
                        row * squareSize,
                        squareSize,
                        squareSize);

                //g.setColor(Color.black);
                g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);

            }

        }

    }

    void drawMouse() {
        int randX = random_number(100000000);
        int randY = random_number(100000000);
        int negornaw = random_number(4);
        if (negornaw == 1) {
            randY = -randY;
        }
        if (negornaw == 2) {
            randX = -randX;
        }
        if (negornaw == 3) {
            randX = -randX;
            randY = -randY;
        }
        this.writeSquare(randX, randY, 3);
        food.add(new SquareCoords(randX, randY));
    }

    public static int random_number(int number) {
        double rand = Math.random() * 1000 % number; //generates a random number
        return (int) rand;//returns the random number's value
    }

    boolean checkCamBounds() {
        if (bernie.getY() > (cambounds.getTop() / squareSize) && bernie.getY() < (cambounds.getBottom() / squareSize) && bernie.getX() < (cambounds.getRight() / squareSize) && bernie.getX() > (cambounds.getLeft() / squareSize)) {
            return true;
        } else {
            return false;
        }
    }

    boolean checkAtUp() {
        System.out.println("------------CHECKING IF AT TOP--------------");
        int a = ((int) (bernie.getY()));
        int b = ((int) (cambounds.getTop() / squareSize));
        System.out.println(a);
        System.out.println(b);
        if (b - 1 <= a && a <= b + 1) {
            System.out.println("TRUE");
            System.out.println("-------------CHECK DONE--------------\n");
            return true;
        } else {
            System.out.println("FALSE");
            System.out.println("-------------CHECK DONE--------------\n");
            return false;
        }
    }

    boolean checkAtDown() {
        System.out.println("------------CHECKING IF AT BOTTOM--------------");
        int a = ((int) (bernie.getY()));
        int b = ((int) (cambounds.getBottom() / squareSize));
        System.out.println(a);
        System.out.println(b);
        if (b - 1 <= a && a <= b + 1) {
            System.out.println("TRUE");
            System.out.println("-------------CHECK DONE--------------\n");
            return true;
        } else {
            System.out.println("FALSE");
            System.out.println("-------------CHECK DONE--------------\n");
            return false;
        }
    }

    boolean checkAtLeft() {
        System.out.println("------------CHECKING IF AT LEFT--------------");
        int a = ((int) (bernie.getX()));
        int b = ((int) (cambounds.getLeft() / squareSize));
        System.out.println(a);
        System.out.println(b);
        if (b - 1 <= a && a <= b + 1) {
            System.out.println("TRUE");
            System.out.println("-------------CHECK DONE--------------\n");
            return true;
        } else {
            System.out.println("FALSE");
            System.out.println("-------------CHECK DONE--------------\n");
            return false;
        }
    }

    boolean checkAtRight() {
        System.out.println("------------CHECKING IF AT RIGHT--------------");
        int a = ((int) (bernie.getX()));
        int b = ((int) (cambounds.getRight() / squareSize));
        System.out.println(a);
        System.out.println(b);
        if (b - 1 <= a && a <= b + 1) {
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
