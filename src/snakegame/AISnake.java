/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author skinnnero5
 */
public class AISnake extends Snake {

    boolean isPathing;
    SquareCoords scanLocation;
    Rect2d vision, pathX, pathY;
    int randomCooldown;
    static ArrayList<Double> fooddist = new ArrayList<Double>();
    static ArrayList<Integer> whichfood = new ArrayList<Integer>();
    Rect2d targettemp;

    private static ArrayList<Rect2d> snake;
    private static ArrayList<SquareCoords> history;

    public AISnake() {
        this.dir = Direction.Right;
        snake = new ArrayList<Rect2d>();
        history = new ArrayList<SquareCoords>();
        snakeWidth = 20;
        head = new Rect2d(500.0, 1000.0, snakeWidth, snakeWidth);
        this.addS(head);
        moving = false;
        alive = true;
        isPlayer = true;

        isPathing = false;
        isPlayer = false;
        vision = new Rect2d(this.getHead().getCenter().x - 500, this.getHead().getCenter().y - 500, 1000, 1000);
        pathY = new Rect2d(this.getHead().getLeft(), this.getHead().getCenter().x - 500, 1000, this.getWidth());
        pathX = new Rect2d(this.getHead().getLeft(), this.getHead().getCenter().y - 500, this.getWidth(), 1000);
        randomCooldown = 0;
    }

    void scan() {

        System.out.println("--------------------Scanning--------------------");
        if (!isPathing) {
            for (int i = 0; i < RectPanel.food.size(); i++) {
                if (Rect2d.intersect(this.vision, RectPanel.food.get(i)) != Rect2d.EmptyRect) {
                    fooddist.add(findDistance(RectPanel.food.get(i)));
                    whichfood.add(i);
                }
            }

            if (isFood()) {
                System.out.println("food found");
                isPathing = true;
                pathTo(giveClosest());
                targettemp = giveClosest();
            }

            if (isFood() == false) {
            }

        }
        System.out.println("--------------------End Scan--------------------");
        pathTo(targettemp);

    }

    void pathTo(Rect2d target) {
        System.out.println("----------------------Pathing----------------------");
        System.out.println("TargetX: " + target.getLeft());
        System.out.println("TargetY: " + target.getTop());
        System.out.println("PATHX: " + pathX.getLeft());
        System.out.println("PATHY: " + pathY.getTop());
        if (!pathX.checkCollisions(target)) {
            System.out.println("horizontal change needed");
            if (pathX.getLeft() < target.getLeft()) {
                this.dir = Direction.Right;
                return;
            }
            if (pathX.getLeft() > target.getLeft()) {
                this.dir = Direction.Left;
                return;
            }
        } else if (pathX.checkCollisions(target)) {
            System.out.println("vertical change needed");
            if (!pathY.checkCollisions(target)) {
                if (pathY.getTop() > target.getTop()) {
                    this.dir = Direction.Up;
                    return;
                }
                if (pathY.getTop() < target.getTop()) {
                    this.dir = Direction.Down;
                    return;
                }

            } else if (pathY.checkCollisions(target)) {
                isPathing = false;
                System.out.println("----------------------Pathing Success----------------------");
                return;
            }

        }
        System.out.println("whoops");
    }

    Rect2d giveClosest() {

        int index = whichfood.get(returnLowest());

        return RectPanel.food.get(index);
    }

    boolean isFood() {
        if (fooddist.isEmpty()) {
            return false;
        }
        return true;
    }

    double findDistance(Rect2d target) {
        double distance = 0;
        double x1 = this.getHead().getCenter().x;
        double x2 = target.getCenter().x;
        double y1 = this.getHead().getCenter().y;
        double y2 = target.getCenter().y;

        distance = Math.sqrt((Math.pow((x2 - x1), 2)) + (Math.pow((y2 - y1), 2)));
        return distance;
    }

    int returnLowest() {
        double x = Collections.min(fooddist);
        for (int i = 0; i < fooddist.size(); i++) {
            if (fooddist.get(i) == x) {
                return i;
            }
        }
        return 26812334;
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

        double widthfactor = 1;
        for (int j = 0; j < RectPanel.food.size(); j++) {
            if (Rect2d.intersect(RectPanel.food.get(j), this.getHead()) != Rect2d.EmptyRect) {//when snake touches food
                //Rect2d.resolveOverlap(food.get(j), snake.get(0));
                this.addS(new Rect2d(1000, 1000.0, this.getWidth(), this.getWidth()));
                this.addH(new SquareCoords(0, 0));
                RectPanel.food.remove(j);
                RectPanel.food.add(new Rect2d(random_number(0, 1000), random_number(0, 500), 10, 10));
                widthfactor = this.getSSize() / 10;
                widthfactor += 1;
                this.setWidth(10 + (widthfactor * 5));
            }
        }

        //SNAKE COLLISION INTO ITSELF
        for (int j = 1; j < this.getSSize(); j++) {
            if (Rect2d.intersect(this.getRect(j), this.getHead()) != Rect2d.EmptyRect) {//when snake touches itself
                //this.die();
                //return;
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
        vision = new Rect2d(this.getHead().getCenter().x - 500, this.getHead().getCenter().y - 500, 1000, 1000);
        pathY = new Rect2d(this.getHead().getLeft() - 500, this.getHead().getTop(), 1000, this.getWidth());
        pathX = new Rect2d(this.getHead().getLeft(), this.getHead().getCenter().y - 500, this.getWidth(), 1000);
        this.updateSize();
    }

}
