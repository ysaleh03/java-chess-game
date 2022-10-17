package model.players;

import model.Position;
import model.pieces.Piece;

import java.util.ArrayList;

// Human is a subclass of Player representing a human player,
// holds the following information:
// - the player's name
// - the color the player is using
// - the pieces the player captured
public class Human implements Player {
    private final String name;
    private final int color;
    private final ArrayList<Piece> capturedPieces;

    // EFFECTS: Constructs a new Player with given name,
    //          no captures, null board
    public Human(String name, int color) {
        this.name = name;
        this.color = color;
        this.capturedPieces = new ArrayList<>();
    }

    // MODIFIES: this, piece, pos
    //  EFFECTS: if pos is in piece's availablePositions,
    //           moves piece to pos true, else returns false
    //           if pos already contains piece, removes it
    //           and adds it to capturedPieces
    @Override
    public boolean makeMove(Piece piece, Position pos) {
        if (piece.getAvailablePositions().contains(pos)) {
            if (pos.getPiece() != null) {
                capturedPieces.add(pos.getPiece());
                pos.removePiece();
            }
            piece.getPosition().removePiece();
            piece.setPosition(pos);
            piece.setMoved();
            pos.setPiece(piece);
            return true;
        } else {
            return false;
        }
    }

    //Getters
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public ArrayList<Piece> getCaptures() {
        return capturedPieces;
    }
}
