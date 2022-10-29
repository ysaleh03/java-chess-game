package model;

import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Queen;
import org.json.JSONObject;
import persistence.Writable;

// Represents a position/tile on a chess board
// holds the following information:
// - its rank (row)
// - its file (column)
// - the piece it contains
public class Position implements Writable {
    private final int rank; //row
    private final int file; //column
    private Piece piece; //content

    // REQUIRES: r, f in [0,7]
    //  EFFECTS: constructs an empty Position
    //           with rank r, file f
    public Position(int rank, int file) {
        this.rank = rank;
        this.file = file;
        this.piece = null;
    }

    // MODIFIES: this
    // EFFECT: sets this.piece to given piece, unless piece is a pawn
    //         reaching other side, in which case promotes to queen
    public void setPiece(Piece piece) {
        if (rank == 0 && piece instanceof Pawn && piece.getColor() == 1) {
            this.piece = new Queen(1);
        } else if (rank == 7 && piece instanceof Pawn && piece.getColor() == -1) {
            this.piece = new Queen(-1);
        } else {
            this.piece = piece;
        }
    }

    public void removePiece() {
        this.piece = null;
    }

    // Getters
    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

    public Piece getPiece() {
        return piece;
    }

    // EFFECTS: turns position into JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("rank", rank);
        json.put("file", file);
        if (piece != null) {
            json.put("piece", piece.toJson());
        } else {
            json.put("piece", JSONObject.NULL);
        }
        return json;
    }
}
