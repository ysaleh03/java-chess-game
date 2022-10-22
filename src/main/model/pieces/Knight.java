package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// Knight is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
public class Knight extends Piece {
    public Knight(int color) {
        super(color);
        type = "Knight";
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
    public ArrayList<Position> getAvailablePositions(Board board, Position position) {
        availablePositions.clear();
        // two up
        // one right
        getPathOnce(-2,1, board, position);
        // one left
        getPathOnce(-2,-1, board, position);

        // two down
        // one right
        getPathOnce(2,1, board, position);
        // one left
        getPathOnce(2,-1, board, position);

        // two right
        // one up
        getPathOnce(-1,2, board, position);
        // one down
        getPathOnce(1,2, board, position);

        // two left
        // one up
        getPathOnce(-1,-2, board, position);
        // one down
        getPathOnce(1,-2, board, position);

        availablePositions.removeIf(p -> checkInvalid(p, board)); //from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
