package model;

import model.pieces.Piece;

// Represents a position/tile on a chess board
// holds the following information:
// - its rank (row)
// - its file (column)
// - the piece it contains
public class Position {
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

    // Setters
    public void setPiece(Piece piece) {
        this.piece = piece;
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
}
