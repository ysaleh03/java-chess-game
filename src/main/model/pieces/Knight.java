package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// Knight is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
public class Knight extends Piece {
    public Knight(int color, Board board, Position position) {
        super(color, board, position);
        if (color == 1) {
            icon = "♘";
        } else {
            icon = "♞";
        }
    }

    // MODIFIES: this
    //  EFFECTS: generates and returns available Positions
    //           Knight allowed to move 2 in one direction,
    //           then 1 perpendicular to it
    @Override
    public ArrayList<Position> getAvailablePositions() {
        availablePositions.clear();
        // THIS DOESN'T WORK!!!!!!!
        // two up
        // one right
        getPathOnce(-2,1);
        // one left
        getPathOnce(-2,-1);

        // two down
        // one right
        getPathOnce(2,1);
        // one left
        getPathOnce(2,-1);

        // two right
        // one up
        getPathOnce(-1,2);
        // one down
        getPathOnce(1,2);

        // two left
        // one up
        getPathOnce(-1,-2);
        // one down
        getPathOnce(1,-2);

        availablePositions.removeIf(this::checkInvalid); //suggested by IntelliJ, from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
