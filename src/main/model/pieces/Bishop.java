package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(int color, Board board, Position position) {
        super(color, board, position);
        if (color == 1) {
            icon = "♗";
        } else {
            icon = "♝";
        }
    }

    // MODIFIES: this
    //  EFFECTS: generates and returns available Positions
    //           Bishop allowed to move any dist. diagonally
    @Override
    public ArrayList<Position> getAvailablePositions() {
        availablePositions.clear();
        // go NW
        getPath(-1,-1);
        // go NE
        getPath(-1,1);
        // go SE
        getPath(1,1);
        // go SW
        getPath(1,-1);
        availablePositions.removeIf(this::checkInvalid); //suggested by IntelliJ, from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
