package model;

import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Queen;
import org.json.JSONObject;
import persistence.Writable;

/**
 * The {@code Position} class represents a square on the chess board, identified by its rank and file.
 * Can contain up to one piece.
 */
public class Position implements Writable {
    private final int rank; //row
    private final int file; //column
    private Piece piece; //content

    /**
     * Constructs an empty position at the given rank and file.
     *
     * @param rank integer between 0 and 7
     * @param file integer between 0 and 7
     */
    public Position(int rank, int file) {
        this.rank = rank;
        this.file = file;
        this.piece = null;
    }

    /**
     * Removes the piece in this position, if any.
     */
    public void removePiece() {
        this.piece = null;
    }

    /**
     * @return integer rank number of this position
     */
    public int getRank() {
        return rank;
    }

    // Getters

    /**
     * @return integer file number of this position
     */
    public int getFile() {
        return file;
    }

    /**
     * @return piece in this position, {@code null} if none
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Places given piece in this position.
     * <p> If piece is a pawn eligible for promotion, places queen instead.
     *
     * @param piece piece being placed
     */
    public void setPiece(Piece piece) {
        if (rank == 0 && piece instanceof Pawn && piece.getColor() == 1) {
            this.piece = new Queen(1);
        } else if (rank == 7 && piece instanceof Pawn && piece.getColor() == -1) {
            this.piece = new Queen(-1);
        } else {
            this.piece = piece;
        }
    }

    /**
     * @return string name of position in algebraic notation
     */
    public String getName() {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        return letters[file] + (8 - rank);
    }

    /**
     * {@inheritDoc}
     */
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
