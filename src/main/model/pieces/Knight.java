package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

/**
 * The {@code Knight} class represents a knight chess piece, extends {@link model.pieces.Piece}.
 * @see model.pieces.Piece
 * @author Youssef Saleh
 */
public class Knight extends Piece {
    /**
     * Constructs a new unmoved knight of the given color
     * @param color The color of the knight
     *              <p>white = 1
     *              <p>black = -1
     */
    public Knight(int color) {
        super(color);
        type = "Knight";
        if (color == 1) {
            iconPath = "./data/icons/knightw.png";
        } else {
            iconPath = "./data/icons/knightb.png";
        }
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Able to move two squares in any one direction, then one square perpendicularly. Can jump over other pieces.
     */
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
