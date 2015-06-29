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
                    if (sp.checkCamBounds()) {
                        y--;
                    }
                    for (int i = 0; i < sp.food.size(); i++) {
                        sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 0);
                        if (sp.checkCamBounds() == false) {
                            sp.food.get(i).translate(0, transval);
                        }
                        sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 3);
                    }
                    break;

                case Down:
                    if (sp.checkCamBounds()) {
                        y++;
                    }
                    for (int i = 0; i < sp.food.size(); i++) {
                        sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 0);
                        if (sp.checkCamBounds() == false) {
                            sp.food.get(i).translate(0, -transval);
                        }
                        sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 3);
                    }
                    break;

                case Left:
                    if (sp.checkCamBounds()) {
                        x--;
                    }
                    for (int i = 0; i < sp.food.size(); i++) {
                        sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 0);
                        if (sp.checkCamBounds() == false) {
                            sp.food.get(i).translate(transval, 0);
                        }
                        sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 3);
                    }
                    break;

                case Right:
                    if (sp.checkCamBounds()) {
                        x++;
                    }
                    for (int i = 0; i < sp.food.size(); i++) {
                        sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 0);
                        if (sp.checkCamBounds() == false) {
                            sp.food.get(i).translate(-transval, 0);
                        }
                        sp.writeSquare(sp.food.get(i).x, sp.food.get(i).y, 3);
                    }
                    break;

            }
            /////COLLISION MANAGEMENT
            SquareCoords pos = new SquareCoords(0, 0);

            if (sp.readSquare(x, y) != 0 && sp.readSquare(x, y) != 3 && sp.readSquare(x, y) != 1 && sp.readSquare(x, y) != 4) {
                alive = false;
                for (int i = 0; i < body.size() - 1; i++) {
                    pos = body.get(i);
                    sp.writeSquare(pos.x, pos.y, 3);
                }
            }
            //If Bernie(Player 1) collides into Bernita(Player 2) and Bernie is smaller
            if (this.color == 1 && this.body.size() + 20 < sp.bernita.body.size() && sp.readSquare(x, y) == 4) {
                sp.bernie.alive = false;
                for (int i = 0; i < body.size() - 1; i++) {
                    pos = body.get(i);
                    sp.writeSquare(pos.x, pos.y, 3);
                }
            }
            //If Bernie collides into Bernita and Bernie is bigger
            if (this.color == 1 && this.body.size() > sp.bernita.body.size() + 20 && sp.readSquare(x, y) == 4) {
                for (int i = 0; i < sp.bernita.body.size() - 1; i++) {
                    pos = sp.bernita.body.get(i);
                    sp.writeSquare(pos.x, pos.y, 3);
                    sp.bernita.alive = false;
                }
            }
            //If Bernita collides with Bernie and Bernita is smaller
            if (this.color == 4 && this.body.size() + 20 < sp.bernie.body.size() && sp.readSquare(x, y) == 1) {
                sp.bernita.alive = false;
                for (int i = 1; i < body.size() - 1; i++) {
                    pos = body.get(i);
                    sp.writeSquare(pos.x, pos.y, 3);
                }
            }
            //If Bernita collides with Bernie and Bernita is bigger
            if (this.color == 4 && this.body.size() > sp.bernie.body.size() + 20 && sp.readSquare(x, y) == 1) {
                for (int i = 0; i < sp.bernie.body.size() - 1; i++) {
                    pos = sp.bernita.body.get(i);
                    sp.writeSquare(pos.x, pos.y, 3);
                    sp.bernie.alive = false;
                }
            }
            //WHERE I GROW STUFF
            if (sp.readSquare(x, y) == 3) {
                sp.drawMouse();
                growAmount = growAmount + 5;
                score++;
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
