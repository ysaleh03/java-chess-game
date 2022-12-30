package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

/**
 * The {@code Queen} class represents a queen chess piece, extends {@link model.pieces.Piece}.
 *
 * @author Youssef Saleh
 * @see model.pieces.Piece
 */
public class Queen extends Piece {
    /**
     * Constructs a new unmoved queen of the given color
     *
     * @param color The color of the queen
     *              <p>white = 1
     *              <p>black = -1
     */
    public Queen(int color) {
        super(color);
        type = "Queen";
        if (color == 1) {
            iconPath = "./data/icons/queenw.png";
        } else {
            iconPath = "./data/icons/queenb.png";
        }
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Able to move any number of squares in a straight or diagonal line, until it reaches an edge or obstacle.
     */
    @Override
    public ArrayList<Position> getAvailablePositions(Board board, Position position) {
        availablePositions.clear();
        // go N
        getPath(-1, 0, board, position);
        // go NE
        getPath(-1, 1, board, position);
        // go E
        getPath(0, 1, board, position);
        // go SE
        getPath(1, 1, board, position);
        // go S
        getPath(1, 0, board, position);
        // go SW
        getPath(1, -1, board, position);
        // go W
        getPath(0, -1, board, position);
        // go NW
        getPath(-1, -1, board, position);
        availablePositions.removeIf(p -> checkInvalid(p, board)); //from ArrayList.java, Predicate.java
        return availablePositions;
    }
}
