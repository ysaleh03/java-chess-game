package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// Rook is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
public class Rook extends Piece {

    public Rook(int color, Board board, Position position) {
        super(color, board, position);
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
    public ArrayList<Position> getAvailablePositions() {
        availablePositions.clear();
        // go N
        getPath(-1, 0);
        // go E
        getPath(0, 1);
        // go S
        getPath(1, 0);
        // go W
        getPath(0, -1);
        availablePositions.removeIf(this::checkInvalid); //suggested by IntelliJ, from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
