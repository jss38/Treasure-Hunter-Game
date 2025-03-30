package com.treasurehunter.game;

import com.treasurehunter.game.ui.TextUserInterface;

/**
 * Main entry point for the Treasure Hunter game
 */
public class Main {
    public static void main(String[] args) {
        TextUserInterface userInterface = new TextUserInterface();
        userInterface.start();
    }
}
