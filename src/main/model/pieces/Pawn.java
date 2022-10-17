package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// Pawn is a subclass of Piece representing a King chess piece,
// holds the same information as superclass
public class Pawn extends Piece {
    public Pawn(int color, Board board, Position position) {
        super(color, board, position);
        if (color == 1) {
            icon = "♙";
        } else {
            icon = "♟";
        }
    }

    // MODIFIES: this
    //  EFFECTS: generates and returns available Positions
    //           Pawn allowed to move 1 forward, take 1 forward+diagonal
    //           if !moved, can move 2 forward, no taking
    @Override
    public ArrayList<Position> getAvailablePositions() {
        availablePositions.clear();
        availablePositions.add(board.getPos(position.getRank() - color, position.getFile()));
        if (!moved && availablePositions.get(0).getPiece() == null) {
            availablePositions.add(board.getPos(position.getRank() - (2 * color), position.getFile()));
        }
        if (checkInvalid(availablePositions.get(0))) {
            availablePositions.clear();
        }
        if (position.getFile() < 7) {
            availablePositions.add(board.getPos(position.getRank() - color, position.getFile() + 1));
        }
        if (position.getFile() > 0) {
            availablePositions.add(board.getPos(position.getRank() - color, position.getFile() - 1));
        }
        availablePositions.removeIf(this::checkInvalid); //suggested by IntelliJ, from ArrayList.java, Predicate.java
        return availablePositions;
    }

    // EFFECTS: if position in same file, returns true if empty,
    //          else returns true if occupied by enemy Piece.
    @Override
    protected boolean checkInvalid(Position position) {
        int rank = position.getRank();
        int file = position.getFile();
        if (this.position.getFile() != file) {
            return (board.getPos(rank, file).getPiece() == null
                    || board.getPos(rank, file).getPiece().getColor() == color);
        } else {
            return (board.getPos(rank, file).getPiece() != null);
        }
    }
}