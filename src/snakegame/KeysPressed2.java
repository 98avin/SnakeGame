/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.KeyListener;

/**
 *
 * @author cs1
 */
public class KeysPressed2 implements KeyListener{
    Direction dir =  Direction.Up;
public void keyTyped(KeyEvent e) {}
public void keyReleased(KeyEvent e) { }
public void keyPressed(KeyEvent e) {
int keycode = e.getKeyCode();
if(keycode == VK_UP && dir == Direction.Down){return;}
if(keycode == VK_DOWN && dir == Direction.Up){return;}
if(keycode == VK_LEFT && dir == Direction.Right){return;}
if(keycode == VK_RIGHT && dir == Direction.Left){return;}
switch (keycode) {
case VK_UP:
dir = Direction.Up;
break;
case VK_DOWN:
dir = Direction.Down;
break;
case VK_LEFT:
dir = Direction.Left;
break;
case VK_RIGHT:
dir = Direction.Right;
break;
}
}
}
