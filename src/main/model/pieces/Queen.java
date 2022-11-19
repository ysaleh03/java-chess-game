package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// Queen is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
public class Queen extends Piece {
    public Queen(int color) {
        super(color);
        type = "Queen";
        if (color == 1) {
            iconPath = "./data/icons/queenw.png";
        } else {
            iconPath = "./data/icons/queenb.png";
        }
    }

    // MODIFIES: this
    //  EFFECTS: generates and returns available Positions
    //           Queen allowed to move any dist. in all directions
    @Override
    public ArrayList<Position> getAvailablePositions(Board board, Position position) {
        availablePositions.clear();
        // go N
        getPath(-1,0, board, position);
        // go NE
        getPath(-1,1, board, position);
        // go E
        getPath(0,1, board, position);
        // go SE
        getPath(1,1, board, position);
        // go S
        getPath(1,0, board, position);
        // go SW
        getPath(1,-1, board, position);
        // go W
        getPath(0,-1, board, position);
        // go NW
        getPath(-1,-1, board, position);
        availablePositions.removeIf(p -> checkInvalid(p, board)); //from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
