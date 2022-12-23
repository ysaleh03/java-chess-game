package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

/**
 * The {@code Pawn} class represents a pawn chess piece, extends {@link model.pieces.Piece}.
 * @see model.pieces.Piece
 * @author Youssef Saleh
 */
public class Pawn extends Piece {
    private int file;

    /**
     * Constructs a new unmoved pawn of the given color
     * @param color The color of the pawn
     *              <p>white = 1
     *              <p>black = -1
     */
    public Pawn(int color) {
        super(color);
        type = "Pawn";
        if (color == 1) {
            iconPath = "./data/icons/pawnw.png";
        } else {
            iconPath = "./data/icons/pawnb.png";
        }
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * Able to move one square to the front,
     * <p>or two squares to the front if unmoved,
     * <p>or one square diagonally to the front when taking.
     */
    @Override
    public ArrayList<Position> getAvailablePositions(Board board, Position position) {
        file = position.getFile();
        availablePositions.clear();
        availablePositions.add(board.getPos(position.getRank() - color, position.getFile()));
        if (!moved && availablePositions.get(0).getPiece() == null) {
            availablePositions.add(board.getPos(position.getRank() - (2 * color), position.getFile()));
        }
        if (checkInvalid(availablePositions.get(0), board)) {
            availablePositions.clear();
        }
        if (position.getFile() < 7) {
            availablePositions.add(board.getPos(position.getRank() - color, position.getFile() + 1));
        }
        if (position.getFile() > 0) {
            availablePositions.add(board.getPos(position.getRank() - color, position.getFile() - 1));
        }
        availablePositions.removeIf(p -> checkInvalid(p, board)); //from ArrayList.java, Predicate.java
        return availablePositions;
    }

    /**
     * {@inheritDoc}
     * @param position position to be checked
     * @param board board this piece is on
     * @return {@code true} if position in same file and empty,
     * else {@code true} if occupied by enemy piece,
     * else {@code false}.
     */
    @Override
    protected boolean checkInvalid(Position position, Board board) {
        int rank = position.getRank();
        int file = position.getFile();
        if (this.file != file) {
            return (board.getPos(rank, file).getPiece() == null
                    || board.getPos(rank, file).getPiece().getColor() == color);
        } else {
            return (board.getPos(rank, file).getPiece() != null);
        }
    }
}