package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// King is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
public class King extends Piece {
    public King(int color, Board board, Position position) {
        super(color, board, position);
        if (color == 1) {
            icon = "♔";
        } else {
            icon = "♚";
        }
    }

    // MODIFIES: this
    //  EFFECTS: generates and returns available Positions
    //           King allowed to move 1 in any direction
    @Override
    public ArrayList<Position> getAvailablePositions() {
        availablePositions.clear();
        int rank = position.getRank();
        int file = position.getFile();

        getPathOnce(-1,0); //N
        getPathOnce(-1,1); //NE
        getPathOnce(0,1); //E
        getPathOnce(1, 1); //SE
        getPathOnce(1,0); //S
        getPathOnce(1,-1); //SW
        getPathOnce(0,-1); //W
        getPathOnce(-1,-1); //NW

        availablePositions.removeIf(this::checkInvalid); //suggested by IntelliJ, from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
