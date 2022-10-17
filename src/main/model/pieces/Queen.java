package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// Queen is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
public class Queen extends Piece {
    public Queen(int color, Board board, Position position) {
        super(color, board, position);
        if (color == 1) {
            icon = "♕";
        } else {
            icon = "♛";
        }
    }

    // MODIFIES: this
    //  EFFECTS: generates and returns available Positions
    //           Queen allowed to move any dist. in all directions
    @Override
    public ArrayList<Position> getAvailablePositions() {
        availablePositions.clear();
        // go N
        getPath(-1,0);
        // go NE
        getPath(-1,1);
        // go E
        getPath(0,1);
        // go SE
        getPath(1,1);
        // go S
        getPath(1,0);
        // go SW
        getPath(1,-1);
        // go W
        getPath(0,-1);
        // go NW
        getPath(-1,-1);
        availablePositions.removeIf(this::checkInvalid); //suggested by IntelliJ, from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
