package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

/**
 * The {@code King} class represents a king chess piece.
 * @see model.pieces.Piece
 * @author Youssef Saleh
 */
public class King extends Piece {
    /**
     * Constructs a new unmoved king of the given color
     * @param color The color of the bishop
     *              <p>white = 1
     *              <p>black = -1
     */
    public King(int color) {
        super(color);
        type = "King";
        if (color == 1) {
            iconPath = "./data/icons/kingw.png";
        } else {
            iconPath = "./data/icons/kingb.png";
        }
    }

    /**
     * Generates a list of all the positions the king can move to,
     * going one square in all directions unless blocked by an edge or obstacle.
     * @param board board this piece is on
     * @param position position of this piece
     * @return the list of positions
     */
    @Override
    public ArrayList<Position> getAvailablePositions(Board board, Position position) {
        availablePositions.clear();

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
