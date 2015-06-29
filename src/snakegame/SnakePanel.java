/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    ArrayList<SquareCoords>food;

    static Snake bernie;
    static Snake bernita;

    public SnakePanel(int rows, int cols, int squareSize) {
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
        bernie = new Snake(5, 5, Direction.Right, 1);
        bernita = new Snake(7, 7, Direction.Left, 4);
        this.setFocusable(true);
        this.addKeyListener(keysPressed);
        this.addKeyListener(keysPressed2);
        food = new ArrayList<SquareCoords>();
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
    
    Color insane = new Color(0, 0, 0);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color colors[] = {Color.black, Color.green, Color.white, Color.white, Color.blue};
        for (int i = 0; i < NumRows * NumCols; i++) {
            if (board[i] != 3) {
                board[i] = 0;
            }
        }
        if (bernie.alive) {
            bernie.draw(this);
        }
        if (bernita.alive) {
            bernita.draw(this);
        }
        int q = (int) (Math.random() * 1000 % 20);
        int w = (int) (Math.random() * 1000 % 15);
        int e = (int) (Math.random() * 1000 % 15);
        insane = new Color(q, w, e);
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
        int randX=random_number(NumCols);
        int randY=random_number(NumRows);
        this.writeSquare(randX, randY, 3);
        food.add(new SquareCoords(randX,randY));
    }

    public static int random_number(int number) {
        double rand = Math.random() * 1000 % number; //generates a random number
        return (int) rand;//returns the random number's value
    }
}
