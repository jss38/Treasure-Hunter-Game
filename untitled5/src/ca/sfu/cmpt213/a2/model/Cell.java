package ca.sfu.cmpt213.a2.model;

// Class responsible for each individual cell
public class Cell {
    boolean blocked = true;
    boolean passage = false;
    boolean revealed = false;
    boolean treasureHunter = false;
    boolean relic = false;
    boolean guardian = false;

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isTreasureHunter() {
        return treasureHunter;
    }

    public void setTreasureHunter(boolean treasureHunter) {
        this.treasureHunter = treasureHunter;
    }

    public boolean isRelic() {
        return relic;
    }

    public void setRelic(boolean relic) {
        this.relic = relic;
    }

    public boolean isGuardian() {
        return guardian;
    }

    public void setGuardian(boolean guardian) {
        this.guardian = guardian;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isPassage() {
        return passage;
    }

    public void setPassage(boolean passage) {
        this.passage = passage;
    }

    @Override
    public String toString() {
        String string;

        if(revealed || treasureHunter || guardian || relic) {
            if(treasureHunter && guardian) {
                string = "X";
            } else if(treasureHunter) {
                string = "@";
            } else if(guardian) {
                string = "!";
            } else if(relic) {
                string = "^";
            } else if(passage && !blocked) {
                string = ".";
            } else {
                string = "#";
            }
        } else {
            string = ".";
        }

        return string;
    }
}
