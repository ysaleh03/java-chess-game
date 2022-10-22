package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// King is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
public class King extends Piece {
    public King(int color) {
        super(color);
        type = "King";
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
    public ArrayList<Position> getAvailablePositions(Board board, Position position) {
        availablePositions.clear();
        int rank = position.getRank();
        int file = position.getFile();

        getPathOnce(-1,0, board, position); //N
        getPathOnce(-1,1, board, position); //NE
        getPathOnce(0,1, board, position); //E
        getPathOnce(1, 1, board, position); //SE
        getPathOnce(1,0, board, position); //S
        getPathOnce(1,-1, board, position); //SW
        getPathOnce(0,-1, board, position); //W
        getPathOnce(-1,-1, board, position); //NW

        availablePositions.removeIf(p -> checkInvalid(p, board)); //from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
