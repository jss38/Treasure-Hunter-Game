# Treasure Hunter Game

A text-based maze adventure game where you play as a treasure hunter searching for relics while avoiding guardians.

## Game Description

In Treasure Hunter, you navigate through a randomly generated maze to collect relics. Your goal is to find 3 relics to win the game, but beware of the guardians patrolling the maze! If a guardian catches you, the game is over.

## Game Features

- Randomly generated maze for each playthrough
- Three guardians that move around the maze
- Relics that appear in random locations
- Fog of war - only areas around the player are visible
- Simple text-based interface

## Maze Generation Algorithm

The game uses a randomized version of Prim's algorithm to generate unique mazes for each playthrough. Here's how it works:

1. The algorithm starts with a grid entirely composed of walls (blocked cells)
2. It randomly selects a starting cell and marks it as a passage
3. It adds all neighboring walls (at a distance of 2 cells) to a "frontier" list
4. While the frontier list is not empty:
   - It randomly selects a frontier cell
   - It randomly selects one of the frontier cell's neighboring passage cells
   - It connects the frontier cell to the chosen passage cell by making the wall between them a passage
   - It adds the frontier cell's neighboring walls to the frontier list
   - It removes the processed frontier cell from the list
5. The result is a perfect maze with exactly one path between any two cells

This algorithm ensures that each maze is unique and fully connected, providing a different gameplay experience each time.

## Game Controls

- **W**: Move up
- **A**: Move left
- **S**: Move down
- **D**: Move right
- **?**: Show help
- **C**: Cheat mode (only need to collect 1 relic to win)
- **M**: Reveal the entire maze

## Legend

- **#**: Wall
- **@**: You (the treasure hunter)
- **!**: Guardian
- **^**: Relic
- **.**: Unexplored space

## Project Structure

```
Treasure-Hunter-Game/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── treasurehunter/
│                   └── game/
│                       ├── model/
│                       │   ├── Cell.java
│                       │   ├── Game.java
│                       │   └── Maze.java
│                       ├── ui/
│                       │   └── TextUserInterface.java
│                       └── Main.java
└── build.gradle
```

## How to Build and Run

### Prerequisites
- Java 17 or higher
- Gradle (optional, wrapper included)

### Building and Running with Gradle
```bash
# Navigate to the project directory
cd Treasure-Hunter-Game

# Build with Gradle
./gradlew build

# Run with Gradle
./gradlew run

# Or run the JAR file directly after building
java -jar build/libs/Treasure-Hunter-Game-1.0-SNAPSHOT.jar
```

### Quick Build and Run (Windows)
```bash
# Use the provided batch file to compile and run the game
.\compile_and_run.bat
```

## Game Architecture

- **Main.java**: Entry point of the application
- **model/Cell.java**: Represents a single cell in the maze
- **model/Maze.java**: Handles maze generation and structure
- **model/Game.java**: Manages game state and logic
- **ui/TextUserInterface.java**: Handles user input and display

## Future Improvements

- Add difficulty levels
- Add a graphical user interface
- Add sound effects
- Add more types of enemies and items
- Add a scoring system
- Add a save/load feature