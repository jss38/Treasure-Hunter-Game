package com.treasurehunter.game.model;

import java.util.*;

// Class responsible for the creation and generation of a random maze
public class Maze {
    private final int MAZE_HEIGHT = 16;
    private final int MAZE_WIDTH = 20;
    private final List<List<Cell>> grid = new ArrayList<>();

    public Maze() {
        initializeMaze();
        createMaze();
        cleanMaze();
    }

    private void initializeMaze() {
        for(int y = 0; y < MAZE_HEIGHT; y++) {
            List<Cell> cellList = new ArrayList<>();
            for(int x = 0; x < MAZE_WIDTH; x++) {
                cellList.add(new Cell());
            }
            grid.add(cellList);
        }
    }

    private void createMaze() {
        Random rand = new Random();
        int[] chosenCell = {rand.nextInt(MAZE_HEIGHT), rand.nextInt(MAZE_WIDTH)};
        grid.get(chosenCell[0]).get(chosenCell[1]).setPassage(true);
        grid.get(chosenCell[0]).get(chosenCell[1]).setBlocked(false);

        List<int[]> frontierCells = frontierCells(chosenCell);

        // While the list of frontier cells is not empty:
        while(!frontierCells.isEmpty()) {
            // Pick a random frontier cell from the list of frontier cells.
            int[] chosenFrontierCell = frontierCells.get(rand.nextInt(frontierCells.size()));

            // Let neighbours(chosenFrontierCell) = All cells in distance 2 in state Passage.
            List<int[]> neighbours = neighbours(chosenFrontierCell);

            // Pick a random neighbour
            int[] chosenNeighbour = neighbours.get(rand.nextInt(neighbours.size()));

            // and connect the frontier cell with the neighbor
            // by setting the cell in-between to state Passage.
            connect(chosenFrontierCell, chosenNeighbour);

            // Compute the frontier cells of the chosen frontier cell and add them to the frontier list.
            frontierCells.addAll(frontierCells(chosenFrontierCell));

            // Remove the chosen frontier cell from the list of frontier cells.
            frontierCells.remove(chosenFrontierCell);
        }
    }

    private List<int[]> frontierCells(int[] chosenCell) {
        List<int[]> frontierCells = new ArrayList<>();

        int y = chosenCell[0];
        int x = chosenCell[1];

        if(y >= 2 && grid.get(y-2).get(x).isBlocked()) {
            frontierCells.add(new int[] {y-2, x});
        }
        if(x >= 2 && grid.get(y).get(x-2).isBlocked()) {
            frontierCells.add(new int[] {y, x-2});
        }
        if(y < MAZE_HEIGHT-2 && grid.get(y+2).get(x).isBlocked()) {
            frontierCells.add(new int[] {y+2, x});
        }
        if(x < MAZE_WIDTH-2 && grid.get(y).get(x+2).isBlocked()) {
            frontierCells.add(new int[] {y, x+2});
        }

        return frontierCells;
    }

    private List<int[]> neighbours(int[] chosenFrontierCell) {
        List<int[]> neighbours = new ArrayList<>();

        int y = chosenFrontierCell[0];
        int x = chosenFrontierCell[1];

        if(y >= 2 && grid.get(y-2).get(x).isPassage()) {
            neighbours.add(new int[] {y-2, x});
        }
        if(x >= 2 && grid.get(y).get(x-2).isPassage()) {
            neighbours.add(new int[] {y, x-2});
        }
        if(y < MAZE_HEIGHT-2 && grid.get(y+2).get(x).isPassage()) {
            neighbours.add(new int[] {y+2, x});
        }
        if(x < MAZE_WIDTH-2 && grid.get(y).get(x+2).isPassage()) {
            neighbours.add(new int[] {y, x+2});
        }

        return neighbours;
    }

    private void connect(int[] chosenFrontierCell, int[] chosenNeighbour) {
        int y = (chosenFrontierCell[0] + chosenNeighbour[0]) / 2;
        int x = (chosenFrontierCell[1] + chosenNeighbour[1]) / 2;

        grid.get(y).get(x).setPassage(true);
        grid.get(y).get(x).setBlocked(false);

        grid.get(chosenFrontierCell[0]).get(chosenFrontierCell[1]).setPassage(true);
        grid.get(chosenFrontierCell[0]).get(chosenFrontierCell[1]).setBlocked(false);
    }

    private void cleanMaze() {
        for(int y = 0; y < MAZE_HEIGHT; y++) {
            grid.get(y).get(0).setBlocked(true);
            grid.get(y).get(MAZE_WIDTH-1).setBlocked(true);

            grid.get(y).get(0).setPassage(false);
            grid.get(y).get(MAZE_WIDTH-1).setPassage(false);

            grid.get(y).get(0).setRevealed(true);
            grid.get(y).get(MAZE_WIDTH-1).setRevealed(true);
        }

        for(int x = 0; x < MAZE_WIDTH; x++) {
            grid.get(0).get(x).setBlocked(true);
            grid.get(MAZE_HEIGHT-1).get(x).setBlocked(true);

            grid.get(0).get(x).setPassage(false);
            grid.get(MAZE_HEIGHT-1).get(x).setPassage(false);;

            grid.get(0).get(x).setRevealed(true);
            grid.get(MAZE_HEIGHT-1).get(x).setRevealed(true);
        }

        grid.get(1).get(1).setBlocked(false);
        grid.get(1).get(1).setPassage(true);

        grid.get(1).get(MAZE_WIDTH-2).setBlocked(false);
        grid.get(1).get(MAZE_WIDTH-2).setPassage(true);

        grid.get(MAZE_HEIGHT-2).get(1).setBlocked(false);
        grid.get(MAZE_HEIGHT-2).get(1).setPassage(true);

        grid.get(MAZE_HEIGHT-2).get(MAZE_WIDTH-2).setBlocked(false);
        grid.get(MAZE_HEIGHT-2).get(MAZE_WIDTH-2).setPassage(true);
    }

    public List<List<Cell>> getMaze() {
        return grid;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for(int y = 0; y < MAZE_HEIGHT; y++) {
            for(int x = 0; x < MAZE_WIDTH; x++) {
                string.append(grid.get(y).get(x));
            }
            string.append('\n');
        }
        return string.toString();
    }
}
