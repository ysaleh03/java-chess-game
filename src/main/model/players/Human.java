package model.players;

import exceptions.InvalidMoveException;
import model.Position;
import model.pieces.Piece;

// Human is a subclass of Player representing a human player,
// holds the following information:
// - the player's name
// - the color the player is using
// - the pieces the player captured
public class Human extends Player {
    public Human(String name, int color) {
        super(name, color);
    }

    // MODIFIES: this, pos1, pos2, board
    //  EFFECTS: gets piece from pos1,
    //           if pos2 is in piece's availablePositions, moves piece to pos2,
    //           else throws InvalidMoveException.
    //           if pos2 already contains a piece, removes it and adds it
    //           to capturedPieces
    @Override
    public void makeMove(Position pos1, Position pos2) throws InvalidMoveException {
        assert (this.board != null);
        Piece piece = pos1.getPiece();
        if (piece.getAvailablePositions(this.board, pos1).contains(pos2)) {
            if (pos2.getPiece() != null) {
                capturedPieces.add(pos2.getPiece());
                pos2.removePiece();
            }
            piece.setMoved(true);
            pos2.setPiece(piece);
            pos1.removePiece();
        } else {
            throw new InvalidMoveException();
        }
    }
}
