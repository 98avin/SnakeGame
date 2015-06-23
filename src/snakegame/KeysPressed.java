/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_W;
import java.awt.event.KeyListener;

/**
 *
 * @author cs1
 */
public class KeysPressed implements KeyListener {
    Direction dir =  Direction.Up;
public void keyTyped(KeyEvent e) {}
public void keyReleased(KeyEvent e) { }
public void keyPressed(KeyEvent e) {
int keycode = e.getKeyCode();
if(keycode == VK_W && dir == Direction.Down){return;}
if(keycode == VK_S && dir == Direction.Up){return;}
if(keycode == VK_A && dir == Direction.Right){return;}
if(keycode == VK_D && dir == Direction.Left){return;}
switch (keycode) {
case VK_W:
dir = Direction.Up;
break;
case VK_S:
dir = Direction.Down;
break;
case VK_A:
dir = Direction.Left;
break;
case VK_D:
dir = Direction.Right;
break;
}
}
}
