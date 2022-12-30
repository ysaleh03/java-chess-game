package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

/**
 * The {@code Bishop} class represents a bishop chess piece, extends {@link model.pieces.Piece}.
 *
 * @author Youssef Saleh
 * @see model.pieces.Piece
 */
public class Bishop extends Piece {
    /**
     * Constructs a new unmoved bishop of the given color
     *
     * @param color The color of the bishop
     *              <p>white = 1
     *              <p>black = -1
     */
    public Bishop(int color) {
        super(color);
        type = "Bishop";
        if (color == 1) {
            iconPath = "./data/icons/bishopw.png";
        } else {
            iconPath = "./data/icons/bishopb.png";
        }
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Able to move diagonally in any direction, for any number of squares, until it reaches an edge or obstacle.
     */
    @Override
    public ArrayList<Position> getAvailablePositions(Board board, Position position) {
        availablePositions.clear();
        // go NW
        getPath(-1, -1, board, position);
        // go NE
        getPath(-1, 1, board, position);
        // go SE
        getPath(1, 1, board, position);
        // go SW
        getPath(1, -1, board, position);
        availablePositions.removeIf(p -> checkInvalid(p, board)); //from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
