/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;
import java.net.*;
import java.io.*;

/**
 *
 * @author cs5
 */
public class Client extends JFrame {

    JFrame f;
    JTextArea usertext;

    private String hostname;
    private String showtext;
    private Socket connection;
    private BufferedReader serverinput;
    private PrintWriter serveroutput;
    private int port;

    private static final long serialVersionUID = 1L;

}

class sender implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String text = usertext.getText();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}
