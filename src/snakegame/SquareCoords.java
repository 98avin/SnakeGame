/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;

/**
 *
 * @author cs1
 */
public class SquareCoords {
        int x, y;

    SquareCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setX(int x){
    this.x = x;
    }
    
    public void setY(int y){
    this.y = y;
    }
    
    public void translate(int x, int y){
    this.x += x;
    this.y += y;
    }
}
