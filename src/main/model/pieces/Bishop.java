package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(int color) {
        super(color);
        type = "Bishop";
        if (color == 1) {
            iconPath = "./data/icons/bishopw.png";
        } else {
            iconPath = "./data/icons/bishopb.png";
        }
    }

    // MODIFIES: this
    //  EFFECTS: generates and returns available Positions
    //           Bishop allowed to move any dist. diagonally
    @Override
    public ArrayList<Position> getAvailablePositions(Board board, Position position) {
        availablePositions.clear();
        // go NW
        getPath(-1,-1, board, position);
        // go NE
        getPath(-1,1, board, position);
        // go SE
        getPath(1,1, board, position);
        // go SW
        getPath(1,-1, board, position);
        availablePositions.removeIf(p -> checkInvalid(p, board)); //from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
