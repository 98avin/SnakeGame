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
public class Rect2d {

    private double x;
    private double y;
    private double w;
    private double h;
    static Rect2d EmptyRect = new Rect2d(0.0, 0.0, 0.0, 0.0);

    enum Direction {

        Left, Right, Up, Down
    };

    Rect2d(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    void moveTo(SquareCoords a) {
        this.setX(a.x);
        this.setY(a.y);
    }

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

    void setX(double x) {
        this.x = x;
    }

    void setY(double y) {
        this.y = y;
    }

    SquareCoords getCenter() {
        return new SquareCoords((int)(this.getLeft()+this.getRight())/2,(int)(this.getTop()+this.getBottom())/2);
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

    boolean checkCollisions(Rect2d B) {
        if (intersect(this, B) != EmptyRect) {
            return true;
        } else {
            return false;
        }
    }

    void translate(double xMove, double yMove) {
        x += xMove;
        y += yMove;
    }

}
