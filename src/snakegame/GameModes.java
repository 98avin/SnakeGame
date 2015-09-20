/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

/**
 *
 * @author Josemon Abraham
 */
public class GameModes {
    //multiple functions in this page...
    //each function will modify certain values to provide a certain game type...
    //an instance of Gamemode will be triggered
    //depending on an int value determined through menu, a certain function will be ran
    //an example is provided below
    
    //also how to go about doing juggernaut? call on gameMode function multiple times to change values
    //of winning snake?
    
    public int gameMode = 0;
    public static final String[] gameModes = new String[]{"DEFAULT", "MICROBIAL ONSLAUGHT", "JUGGERNAUT"};
    
    public GameModes(int a){
    
    gameMode = a;
        
    switch (gameMode){
        case 0:
        Default();
    }          
    }
    
    public void Default(){
    //SnakePanel.a = b
    //etc....
    }
}