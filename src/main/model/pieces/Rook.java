package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// Rook is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
/**
 * The {@code Rook} class represents a rook chess piece, extends {@link model.pieces.Piece}.
 * @see model.pieces.Piece
 * @author Youssef Saleh
 */
public class Rook extends Piece {
    /**
     * Constructs a new unmoved rook of the given color
     * @param color The color of the rook
     *              <p>white = 1
     *              <p>black = -1
     */
    public Rook(int color) {
        super(color);
        type = "Rook";
        if (color == 1) {
            iconPath = "./data/icons/rookw.png";
        } else {
            iconPath = "./data/icons/rookb.png";
        }
    }

    // MODIFIES: this
    //  EFFECTS: generates and returns available Positions
    //           Rook allowed to move any dist. vertical/horizontal
    /**
     * {@inheritDoc}
     * <p></p>
     * Able to move in a straight line in any direction, for any number of squares, until it reaches an edge or obstacle.
     */
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
