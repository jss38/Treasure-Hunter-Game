@echo off
echo Compiling Treasure Hunter Game...

mkdir -p build\classes

javac -d build\classes src\main\java\com\treasurehunter\game\model\*.java src\main\java\com\treasurehunter\game\ui\*.java src\main\java\com\treasurehunter\game\*.java

if %ERRORLEVEL% == 0 (
    echo Compilation successful! Running the game...
    java -cp build\classes com.treasurehunter.game.Main
) else (
    echo Compilation failed.
)

pause
