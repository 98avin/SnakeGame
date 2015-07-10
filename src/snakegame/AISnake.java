/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import static snakegame.RectPanel.random_number;

/**
 *
 * @author skinnnero5
 */
public class AISnake extends Snake {

    boolean isPathing;
    SquareCoords scanLocation;
    Rect2d vision, pathX, pathY;
    int randomCooldown;

    public AISnake() {
        super();
        isPathing = false;
        isPlayer = false;
        vision = new Rect2d(this.getHead().getCenter().x - 500, this.getHead().getCenter().y - 500, 1000, 1000);
        pathX = new Rect2d(this.getHead().getLeft(), this.getHead().getCenter().x - 500, 1000, this.getWidth());
        pathY = new Rect2d(this.getHead().getLeft(), this.getHead().getCenter().y - 500, this.getWidth(), 1000);
        randomCooldown = 0;
    }

    void scan() {

        if (!isPathing) {
            for (int i = 0; i < RectPanel.food.size(); i++) {
                if (Rect2d.intersect(this.vision, RectPanel.food.get(i)) != Rect2d.EmptyRect) {
                    isPathing = true;
                    pathTo(RectPanel.food.get(i));
                }
            }

        }

        //System.out.println(isPathing);
    }

    void pathTo(Rect2d target) {
        if (Rect2d.intersect(this.pathY, target) != Rect2d.EmptyRect) {
            if (this.getHead().getTop() > target.getBottom()) {
                this.dir = Direction.Up;
                return;
            }
            if (this.getHead().getTop() < target.getBottom()) {
                this.dir = Direction.Down;
                return;
            }
        }

        if (Rect2d.intersect(this.pathX, target) != Rect2d.EmptyRect) {
            if (this.getHead().getLeft() > target.getRight()) {
                this.dir = Direction.Left;
                return;
            }
            if (this.getHead().getRight() > target.getLeft()) {
                this.dir = Direction.Right;
                return;
            }
        }
        if (randomCooldown == 0) {
            randomDirection();
            randomCooldown = 5;
        }
        randomCooldown--;
    }

    void randomDirection() {
        int random = random_number(0, 4);
        switch (random) {
            case 0:
                this.dir = Direction.Up;
                break;

            case 1:
                this.dir = Direction.Down;
                break;

            case 2:
                this.dir = Direction.Left;
                break;

            case 3:
                this.dir = Direction.Right;
                break;

            default:
                random = random_number(0, 4);
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

    @Override
    void update() {
        if (alive == false) {
            return;
        }

        //System.out.println("s" + this.getSSize());
        double widthfactor = 1;
        for (int j = 0; j < RectPanel.food.size(); j++) {
            if (Rect2d.intersect(RectPanel.food.get(j), this.getHead()) != Rect2d.EmptyRect) {//when snake touches food
                //Rect2d.resolveOverlap(food.get(j), snake.get(0));
                this.addS(new Rect2d(1000, 1000.0, this.getWidth(), this.getWidth()));
                this.addH(new SquareCoords(0, 0));
                RectPanel.food.remove(j);
                RectPanel.food.add(new Rect2d(random_number(0, (int) RectPanel.WINDOW_WIDTH), random_number(0, (int) RectPanel.WINDOW_HEIGHT), 10, 10));
                widthfactor = this.getSSize() / 10;
                widthfactor += 1;
                this.setWidth(10 + (widthfactor * 5));
            }
        }

        for (int j = 1; j < this.getSSize(); j++) {
            if (Rect2d.intersect(this.getRect(j), this.getHead()) != Rect2d.EmptyRect) {//when snake touches food
                this.die();
                return;
            }
        }

        for (int i = 0; i < this.getSSize(); i++) {
            this.setH(i, new SquareCoords((int) this.getRect(i).getLeft(), (int) this.getRect(i).getTop()));
        }
        this.setMoving(true);

        for (int i = 0; i < this.getHSize(); i++) {
            if (i == 0) {
                scan();
            } else {
                if (this.isMoving()) {
                    this.getRect(i).moveTo(this.getH(i - 1));
                }

            }

        }

        switch (this.dir) {
            case Left:
                this.getHead().translate(-this.getWidth() - 1, 0.0);
                break;

            case Right:
                this.getHead().translate(this.getWidth() + 1, 0.0);
                break;

            case Down:
                this.getHead().translate(0.0, this.getWidth() + 1);
                break;

            case Up:
                this.getHead().translate(0.0, -this.getWidth() - 1);
                break;
        }

        this.updateSize();

    }

}
