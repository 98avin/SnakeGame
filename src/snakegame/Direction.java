/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;

/**
 *
 * @author skinnnero5
 */
public enum Direction {
    Up,Right,Down,Left;
    
    static Direction[] vals = values();
    
    public Direction getNext() {
     return this.ordinal() < Direction.values().length - 1
         ? Direction.values()[this.ordinal() + 1]
         : null;
   }
}
