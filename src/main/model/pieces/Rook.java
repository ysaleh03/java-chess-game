package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// Rook is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
public class Rook extends Piece {

    public Rook(int color) {
        super(color);
        type = "Rook";
        if (color == 1) {
            icon = "♖";
        } else {
            icon = "♜";
        }
    }

    // MODIFIES: this
    //  EFFECTS: generates and returns available Positions
    //           Rook allowed to move any dist. vertical/horizontal
    @Override
    public ArrayList<Position> getAvailablePositions(Board board, Position position) {
        availablePositions.clear();
        // go N
        getPath(-1, 0, board, position);
        // go E
        getPath(0, 1, board, position);
        // go S
        getPath(1, 0, board, position);
        // go W
        getPath(0, -1, board, position);
        availablePositions.removeIf(p -> checkInvalid(p, board)); //from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
