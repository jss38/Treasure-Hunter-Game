package com.treasurehunter.game.ui;

import com.treasurehunter.game.model.Game;

import java.util.Scanner;

// Class responsible for the text interface the user interacts with
public class TextUserInterface {
    Game game = new Game();

    public void start() {
        Scanner keyboard = new Scanner(System.in);
        char userMove;

        System.out.println(game.Help());

        do {
            System.out.println("Maze:");
            System.out.print(game);
            System.out.println("Total number of relics to be collected: " + game.getRelicsNeededToBeFound());
            System.out.println("Number of relics currently in possession: " + game.getRelicsFound());
            System.out.print("Enter your move [WASD?]: ");

            userMove = keyboard.next().charAt(0);

            switch (userMove) {
                case ('W' | 'w') -> {
                    if(!game.move(1)) {
                        System.out.println("Invalid move: you cannot move through walls!");
                    }
                }
                case ('D' | 'd') -> {
                    if(!game.move(2)) {
                        System.out.println("Invalid move: you cannot move through walls!");
                    }
                }
                case ('S' | 's') -> {
                    if(!game.move(3)) {
                        System.out.println("Invalid move: you cannot move through walls!");
                    }
                }
                case ('A' | 'a') -> {
                    if(!game.move(4)) {
                        System.out.println("Invalid move: you cannot move through walls!");
                    }
                }
                case ('C' | 'c') -> game.setRelicsNeededToBeFound(1);
                case ('M' | 'm') -> game.revealAll();
                case ('?') -> game.Help();
            }
        } while(!game.isGameFinished());

        if(game.isGameWon()) {
            System.out.println("Congratulations! You won!");
        }

        if(game.isGameLost()) {
            System.out.println("Oh no! The hunter has been killed!\n");
            System.out.print(game);
            System.out.println("Total number of relics to be collected: " + game.getRelicsNeededToBeFound());
            System.out.println("Number of relics currently in possession: " + game.getRelicsFound());
            System.out.println("GAME OVER... please try again.");
        }
    }
}
