package ui;

import exceptions.IllegalPieceException;
import exceptions.InvalidPieceException;
import exceptions.InvalidPositionException;
import model.Position;
import model.pieces.Piece;

// Tool that allows user to select positions on the board.
public final class MoveTool {

    public MoveTool() {
    }

    // REQUIRES: colors = ±1
    //  EFFECTS: takes int color and a position,
    //           if position does not contain piece, throws InvalidPieceException,
    //           if position contains piece, but color does not match, throws IllegalPieceException,
    //           else returns same position
    public static Position selectPiecePos(int color, Position position)
            throws IllegalPieceException, InvalidPieceException {
        Piece piece = position.getPiece();
        if (piece == null) {
            throw new InvalidPieceException();
        }
        if (piece.getColor() != color) {
            throw new IllegalPieceException();
        }
        return position;
    }

    // REQUIRES: colors = ±1
    //  EFFECTS: takes int color and a position,
    //           if position contains piece of same color, throws InvalidPositionException,
    //           else returns same position
    public static Position selectPosition(int color, Position position) throws InvalidPositionException {
        if (position.getPiece() == null || position.getPiece().getColor() != color) {
            return position;
        } else {
            throw new InvalidPositionException();
        }
    }
}
