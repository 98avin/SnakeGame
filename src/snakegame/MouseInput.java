/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author abrahama8
 */
public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();
        System.out.println("X: " + mx + "\nY: " + my);

        if (mx >= SnakePanel.WINDOW_WIDTH / 2 + 120 && mx <= SnakePanel.WINDOW_WIDTH / 2 + 220) {
            if (my >= 150 && my <= 200) {
                SnakeGame.state = SnakeGame.STATE.GAME;
                System.out.println(SnakeGame.state);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mx >= SnakePanel.WINDOW_WIDTH / 2 + 120 && mx <= SnakePanel.WINDOW_WIDTH / 2 + 220) {
            if (my >= 150 && my <= 200) {
                SnakeGame.state = SnakeGame.STATE.GAME;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mx >= SnakePanel.WINDOW_WIDTH / 2 + 120 && mx <= SnakePanel.WINDOW_WIDTH / 2 + 220) {
            if (my >= 150 && my <= 200) {
                SnakeGame.state = SnakeGame.STATE.GAME;
                System.out.println(SnakeGame.state);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mx >= SnakePanel.WINDOW_WIDTH / 2 + 120 && mx <= SnakePanel.WINDOW_WIDTH / 2 + 220) {
            if (my >= 150 && my <= 200) {
                SnakeGame.state = SnakeGame.STATE.GAME;
                System.out.println(SnakeGame.state);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (mx >= SnakePanel.WINDOW_WIDTH / 2 + 120 && mx <= SnakePanel.WINDOW_WIDTH / 2 + 220) {
            if (my >= 150 && my <= 200) {
                SnakeGame.state = SnakeGame.STATE.GAME;
                System.out.println(SnakeGame.state);
            }
        }
    }

}
