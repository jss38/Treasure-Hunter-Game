package com.treasurehunter.game.model;

import java.util.List;
import java.util.Random;

// Class responsible for the overall game
public class Game {
    private Maze game;
    private List<List<Cell>> gameGrid;
    private int HEIGHT;
    private int WIDTH;

    int relicsFound = 0;
    int relicsNeededToBeFound = 3; // 1 if 'c' option selected
    boolean gameFinished = false;
    boolean gameWon = false;
    boolean gameLost = false;

    // Stored as (y,x)
    int[] treasureHunterPos;

    // First guardian: guardianPos[0 .. 1]
    // Second guardian: guardianPos[2 .. 3]
    // Third guardian: guardianPos[3 .. 4]
    int[] guardianPos;
    int[] prevGuardianMoves = new int[] {-1, -1, -1};
    int[] relicPos;

    public Game() {
        setMaze();
        createGame();
    }

    private void setMaze() {
        Maze potentialMaze = new Maze();

        while(!isValidMaze(potentialMaze)) {
            potentialMaze = new Maze();
        }

        game = potentialMaze;
        gameGrid = potentialMaze.getMaze();
        HEIGHT = gameGrid.size();
        WIDTH = gameGrid.get(0).size();
    }

    private boolean isValidMaze(Maze potentialMaze) {
        List<List<Cell>> grid = potentialMaze.getMaze();
        int gridHeight = grid.size();
        int gridWidth = grid.get(0).size();

        for(int y = 0; y < gridHeight-1; y++) {
            for(int x = 0; x < gridWidth-1; x++) {
                if((grid.get(y).get(x).isPassage() && !grid.get(y).get(x).isBlocked())
                && (grid.get(y).get(x+1).isPassage() && !grid.get(y).get(x+1).isBlocked())
                && (grid.get(y+1).get(x).isPassage() && !grid.get(y+1).get(x).isBlocked())
                && (grid.get(y+1).get(x+1).isPassage() && !grid.get(y+1).get(x+1).isBlocked())) {
                    return false;
                }
            }
        }

        return true;
    }

    private void createGame() {
        gameGrid.get(1).get(1).setTreasureHunter(true);

        treasureHunterPos = new int[] {1,1};

        gameGrid.get(1).get(WIDTH-2).setGuardian(true);
        gameGrid.get(HEIGHT-2).get(1).setGuardian(true);
        gameGrid.get(HEIGHT-2).get(WIDTH-2).setGuardian(true);

        guardianPos = new int[] {1, WIDTH-2, HEIGHT-2, 1, HEIGHT-2, WIDTH-2};

        boolean success;

        do {
            success = generateRelic();
        } while(!success);

        revealCell(treasureHunterPos);
    }

    private void revealCell(int[] treasureHunterPos) {
        int y = treasureHunterPos[0];
        int x = treasureHunterPos[1];

        // 0,0 , 0,1 , 0,2
        gameGrid.get(y-1).get(x-1).setRevealed(true);
        gameGrid.get(y-1).get(x).setRevealed(true);
        gameGrid.get(y-1).get(x+1).setRevealed(true);

        // 1,0 , 1,1 , 1,2
        gameGrid.get(y).get(x-1).setRevealed(true);
        gameGrid.get(y).get(x).setRevealed(true);
        gameGrid.get(y).get(x+1).setRevealed(true);

        // 2,0 , 2,1 , 2,2
        gameGrid.get(y+1).get(x-1).setRevealed(true);
        gameGrid.get(y+1).get(x).setRevealed(true);
        gameGrid.get(y+1).get(x+1).setRevealed(true);
    }

    private boolean generateRelic() {
        boolean setRelic = false;

        Random rand = new Random();

        int y = rand.nextInt(HEIGHT);
        int x = rand.nextInt(WIDTH);

        // Cannot be a wall
        // Cannot be a treasure hunter
        // Can be a guardian
        if(gameGrid.get(y).get(x).isPassage() && !gameGrid.get(y).get(x).isBlocked()
        && !gameGrid.get(y).get(x).isTreasureHunter()) {
            gameGrid.get(y).get(x).setRelic(true);
            setRelic = true;

            relicPos = new int[] {y, x};
        }

        return setRelic;
    }

    // North = 1, East = 2, South = 3, West = 4
    public boolean move(int direction) {
        int y;
        int x;

        int[] arr = moveHelper(direction);

        y = arr[0];
        x = arr[1];

        if(gameGrid.get(treasureHunterPos[0] + y).get(treasureHunterPos[1] + x).isPassage()
        && !gameGrid.get(treasureHunterPos[0] + y).get(treasureHunterPos[1] + x).isBlocked()) {

            // Relic found
            if(gameGrid.get(treasureHunterPos[0] + y).get(treasureHunterPos[1] + x).isRelic()) {
                gameGrid.get(treasureHunterPos[0] + y).get(treasureHunterPos[1] + x).setRelic(false);

                boolean success;

                do {
                    success = generateRelic();
                } while(!success);

                relicsFound++;

                if(relicsFound == relicsNeededToBeFound) {
                    gameFinished = true;
                    gameWon = true;
                }
            }

            // Guardian found
            if(gameGrid.get(treasureHunterPos[0] + y).get(treasureHunterPos[1] + x).isGuardian()) {
                gameFinished = true;
                gameLost = true;
            }

            // Move treasureHunter
            gameGrid.get(treasureHunterPos[0]).get(treasureHunterPos[1]).setTreasureHunter(false);
            gameGrid.get(treasureHunterPos[0] + y).get(treasureHunterPos[1] + x).setTreasureHunter(true);

            // Update treasureHunter position
            treasureHunterPos[0] = treasureHunterPos[0] + y;
            treasureHunterPos[1] = treasureHunterPos[1] + x;

            if(!gameFinished) {
                moveGuardians();
            }

            revealCell(treasureHunterPos);

            return true;
        }

        return false;
    }

    private int[] moveHelper(int direction) {
        int y, x;

        switch (direction) {
            case 1 -> {
                y = -1;
                x = 0;
            }
            case 2 -> {
                y = 0;
                x = 1;
            }
            case 3 -> {
                y = 1;
                x = 0;
            }
            case 4 -> {
                y = 0;
                x = -1;
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }

        return new int[] {y, x};
    }

    private void moveGuardians() {
        Random rand = new Random();
        int direction;
        int attempts;

        attempts = 0;
        do {
            direction = rand.nextInt(4) + 1;
            while(direction == prevGuardianMoves[0]) {
                direction = rand.nextInt(4) + 1;

                if(attempts > 25) {
                    break;
                }
                attempts++;
            }
        } while(guardianHelper(direction, 1));

        attempts = 0;
        do {
            direction = rand.nextInt(4) + 1;
            while(direction == prevGuardianMoves[1]) {
                direction = rand.nextInt(4) + 1;

                if(attempts > 25) {
                    break;
                }
                attempts++;
            }

        } while(guardianHelper(direction, 2));

        attempts = 0;
        do {
            direction = rand.nextInt(4) + 1;
            while(direction == prevGuardianMoves[2]) {
                direction = rand.nextInt(4) + 1;

                if(attempts > 25) {
                    break;
                }
                attempts++;
            }
        } while(guardianHelper(direction, 3));
    }

    private boolean guardianHelper(int direction, int guardian) {
        int y, x;
        int y1, x1; // To prevent index errors?
        int[] arr = moveHelper(direction);

        switch (guardian) {
            case 1 -> {
                y = guardianPos[0];
                x = guardianPos[1];
                y1 = 0;
                x1 = 1;
            }
            case 2 -> {
                y = guardianPos[2];
                x = guardianPos[3];
                y1 = 2;
                x1 = 3;
            }
            case 3 -> {
                y = guardianPos[4];
                x = guardianPos[5];
                y1 = 4;
                x1 = 5;
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }

        if(gameGrid.get(y + arr[0]).get(x + arr[1]).isPassage()
        && !gameGrid.get(y + arr[0]).get(x + arr[1]).isBlocked()) {
            int prevMove;
            
            switch (direction) {
                case 1 -> prevMove = 3; // Opposite of N(1) is S(3)
                case 2 -> prevMove = 4; // ... etc etc
                case 3 -> prevMove = 1;
                case 4 -> prevMove = 2;
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }
            
            prevGuardianMoves[guardian-1] = prevMove;

            gameGrid.get(y).get(x).setGuardian(false);
            gameGrid.get(y + arr[0]).get(x + arr[1]).setGuardian(true);

            // Update guardian position
            guardianPos[y1] = guardianPos[y1] + arr[0];
            guardianPos[x1] = guardianPos[x1] + arr[1];

            return false;
        }

        return true;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setRelicsNeededToBeFound(int relicsNeededToBeFound) {
        this.relicsNeededToBeFound = relicsNeededToBeFound;
    }

    public void revealAll() {
        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                gameGrid.get(y).get(x).setRevealed(true);
            }
        }
    }

    // Taken from assignment description
    public String Help() {
        return "DIRECTIONS:\n" +
                "    Collect 3 relics!\n" +
                "LEGEND:\n" +
                "    #: Wall\n" +
                "    @: You (the treasure hunter)\n" +
                "    !: Guardian\n" +
                "    ^: Relic\n" +
                "    .: Unexplored space\n" +
                "MOVES:\n" +
                "    Use W (up), A (left), S (down) and D (right) to move.\n" +
                "    (You must press enter after each move).\n";
    }

    public int getRelicsFound() {
        return relicsFound;
    }

    public int getRelicsNeededToBeFound() {
        return relicsNeededToBeFound;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for(int y = 0; y < HEIGHT; y++) {
            for(int x = 0; x < WIDTH; x++) {
                string.append(gameGrid.get(y).get(x));
            }
            string.append('\n');
        }
        return string.toString();
    }
}
