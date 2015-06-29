/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.Event.*;
import java.util.ArrayList;

/**
 *
 * @author 11768
 */
public class Rect2d extends Shape2d {

    private double x;
    private double y;
    private double w;
    private double h;
    static Rect2d EmptyRect = new Rect2d(0.0, 0.0, 0.0, 0.0);

    Rect2d(double x, double y, double w, double h) {
    this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;    
    }

    enum Direction {

        Left, Right, Up, Down
    };

    double getLeft() {
        return x;
    }

    double getRight() {
        return (x + w);
    }

    double getTop() {
        return y;
    }

    double getBottom() {
        return (y + h);
    }

    double getWidth() {
        return w;
    }

    double getHeight() {
        return h;
    }

    void notifyPushed(Rect2d.Direction d, Rect2d B) {
    }

    static Rect2d intersect(Rect2d A, Rect2d B) {
        double left = Math.max(A.getLeft(), B.getLeft());
        double right = Math.min(A.getRight(), B.getRight());
        double top = Math.max(A.getTop(), B.getTop());
        double bottom = Math.min(A.getBottom(), B.getBottom());
        double w = right - left;
        double h = bottom - top;
        if (w < 0.0 || h < 0.0) {
            return EmptyRect;
        }
        Rect2d out = new Rect2d(left, top, w, h);
        return out;
    }

    static void resolveOverlap(Rect2d A, Rect2d B) {
// we'll move A, not B, to resolve the collision.
        Rect2d overlap = intersect(A, B);
        if (overlap.getHeight() < overlap.getWidth()) {
// Resolve VERTICALLY...either up or down, whichever is less.
            double moveUpAmount = A.getBottom() - B.getTop();
            double moveDownAmount = B.getBottom() - A.getTop();
            if (moveUpAmount < moveDownAmount) {
                A.translate(0, -moveUpAmount);
                A.notifyPushed(Direction.Up, B);
                B.notifyPushed(Direction.Down, A);
            } else {
                A.translate(0, moveDownAmount);
                A.notifyPushed(Direction.Down, B);
                B.notifyPushed(Direction.Up, A);
            }
        } else {
// Resolve HORIZONTALLY...left or right, whichever is less.
            double moveLeftAmount = A.getRight() - B.getLeft();
            double moveRightAmount = B.getRight() - A.getLeft();
            if (moveLeftAmount < moveRightAmount) {
                A.translate(-moveLeftAmount, 0);
                A.notifyPushed(Direction.Left, B);
                B.notifyPushed(Direction.Right, A);
            } else {
                A.translate(moveRightAmount, 0);
                A.notifyPushed(Direction.Right, B);
                B.notifyPushed(Direction.Left, A);
            }
        }
    }

    boolean checkCollisions(Rect2d B) {
        if (intersect(this, B) != EmptyRect) {
            return true;
        } else {
            return false;
        }
    }

    // is 'this' supported by B?


    void update(double dt) {

    }

    void applyAccel(double xAmt, double yAmt) {

    }

    @Override
    double getArea() {
        return (w * h);
    }
    @Override
    void print() {
        System.out.println("Rect2d");
        System.out.println("X: " + x + " Y: " + y + " W: " + w + " H: " + h);
    }
    
     @Override
    void translate(double xMove, double yMove) {
        x += xMove;
        y += yMove;
    }

}