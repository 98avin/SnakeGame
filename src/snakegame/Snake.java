/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;///YO

import java.util.LinkedList;

/**
 *
 * @author cs1
 */
public class Snake {

    Direction dir;
    LinkedList<SquareCoords> body;
    boolean alive;
    int growAmount;
    int score = 0;
    int color;

    Snake(int x, int y, Direction dir, int color) {

        this.dir = dir;
        this.alive = true;
        this.growAmount = 0;
        this.color = color;
        body = new LinkedList<SquareCoords>();
        body.addLast(new SquareCoords(x + 5, y + 27));

    }

    int getX() {
        return body.get(0).x;
    }

    int getY() {
        return body.get(0).y;
    }

    int count = 0;

    void update(Direction dir, SnakePanel sp) {
        if (alive != true) {
            return;
        } else {
            SquareCoords head = body.getFirst();
            int x = head.x;
            int y = head.y;
            int transval = 1;
            switch (dir) {
                case Up:
                    //System.out.println(sp.checkAtBounds());
                    if (sp.checkCamBounds() || (sp.checkAtUp() == false)) { //Snake should be able to move only when inbounds of cam-rect, should stop at line but still be able to be moved
                        y--;
                    }
                    if (sp.checkCamBounds() == false && sp.checkAtRight() == false && sp.checkAtLeft() == false || sp.checkAtUp()) {
                        for (int i = 0; i < sp.food.size(); i++) {
                            sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 0);
                            sp.food.get(i).translate(0, transval);
                            sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 3);
                        }

                    }
                    break;

                case Down:
                    //System.out.println(sp.checkAtBounds());
                    if (sp.checkCamBounds() || (sp.checkAtDown() == false)) {
                        y++;
                    }
                    if (sp.checkCamBounds() == false && sp.checkAtRight() == false && sp.checkAtLeft() == false || sp.checkAtDown()) {
                        for (int i = 0; i < sp.food.size(); i++) {
                            sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 0);
                            sp.food.get(i).translate(0, -transval);
                            sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 3);
                        }

                    }
                    break;

                case Left:
                    //System.out.println(sp.checkAtBounds());
                    if (sp.checkCamBounds() || (sp.checkAtLeft() == false)) {
                        x--;
                    }
                    if (sp.checkCamBounds() == false && sp.checkAtUp() == false && sp.checkAtDown() == false || sp.checkAtLeft()) {
                        for (int i = 0; i < sp.food.size(); i++) {
                            sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 0);
                            sp.food.get(i).translate(transval, 0);
                            sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 3);
                        }

                    }
                    break;

                case Right:
                    //System.out.println(sp.checkAtBounds());
                    if (sp.checkCamBounds() || (sp.checkAtRight() == false)) {
                        x++;
                    }
                    if (sp.checkCamBounds() == false && sp.checkAtUp() == false && sp.checkAtDown() == false || sp.checkAtRight()) {
                        for (int i = 0; i < sp.food.size(); i++) {
                            sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 0);
                            sp.food.get(i).translate(-transval, 0);
                            sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 3);
                        }

                    }
                    break;

            }
            /////COLLISION MANAGEMENT
            SquareCoords pos = new SquareCoords(0, 0);

            if (sp.readSquare(x, y) != 0 && sp.readSquare(x, y) != 3 && sp.readSquare(x, y) != 4 && sp.readSquare(x, y) != 1) {
                alive = false;
                for (int i = 0; i < body.size() - 1; i++) {
                    pos = body.get(i);
                    sp.writeSquare(pos.x, pos.y, 3);
                }
            }

            //WHERE I GROW STUFF
            if (sp.readSquare(x, y) == 3) {
                try{
                SoundUtils.tone(750,15);}
                catch(Exception e){}
                sp.drawMouse();
                growAmount = growAmount + 5;
                score++;

                for (int i = 0; i < sp.food.size(); i++) {
                    if (sp.food.get(i).x == x && sp.food.get(i).y == y) {
                        sp.food.remove(i);
                    }

                }
            }
            //EVERYTHING WE NEED TO FIX INVOLVING THE COLLAPSING SNAKE IS RIGHT HERE
            //Snake still dies if collision into self is enabled
            if (sp.checkAtLeft() && dir == Direction.Left) {
                for (int i = 0; i < body.size(); i++) {
                    body.get(i).translate(1, 0);
                }

            }
            if (sp.checkAtRight() && dir == Direction.Right) {
                for (int i = 0; i < body.size(); i++) {
                    body.get(i).translate(-1, 0);
                }

            }
            if (sp.checkAtUp() && dir == Direction.Up) {
                for (int i = 0; i < body.size(); i++) {
                    body.get(i).translate(0, 1);
                }

            }
            if (sp.checkAtDown() && dir == Direction.Down) {
                for (int i = 0; i < body.size(); i++) {
                    body.get(i).translate(0, -1);
                }

            }

            body.addFirst(new SquareCoords(x, y));
            if (growAmount > 0) {
                growAmount--;
                return;
            }

            body.removeLast();

        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    void draw(SnakePanel sp) {
        for (int i = 0; i < body.size(); i++) {
            SquareCoords pos = body.get(i);
            sp.writeSquare(pos.x, pos.y, color);
        }
    }

}
