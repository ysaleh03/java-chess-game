package model.pieces;

import model.Board;
import model.Position;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Piece is an abstract representation of a chess piece on the board,
// holds the following info:
// - the positions it can move to (abstract)
// - its color
// - whether it has moved or not
// - its icon
// - its type
public abstract class Piece implements Writable {
    protected String type;
    protected final int color; // 1 = white, -1 = black
    protected String iconPath;
    protected boolean moved;
    protected ArrayList<Position> availablePositions;

    // EFFECTS: constructs a new piece
    public Piece(int color) {
        this.availablePositions = new ArrayList<>();
        this.moved = false;
        this.color = color;
    }

    // MODIFIES: this
    // EFFECTS: generates and returns a list of positions that
    //          this piece can be moved to
    public abstract ArrayList<Position> getAvailablePositions(Board board, Position position);

    // REQUIRES: deltaR, deltaF can only be
    //           -1, 0, or 1.
    // MODIFIES: this
    //  EFFECTS: follows path of given directions,
    //           adding positions to availablePositions
    //           until edge or other piece are reached.
    protected void getPath(int deltaR, int deltaF, Board board, Position position) {
        int rank = position.getRank() + deltaR;
        int file = position.getFile() + deltaF;
        while (rank >= 0 && rank <= 7
            && file >= 0 && file <= 7) {
            availablePositions.add(board.getPos(rank, file));
            if (board.getPos(rank, file).getPiece() != null) {
                break;
            }
            rank += deltaR;
            file += deltaF;
        }
    }

    // REQUIRES: reps >= 0
    // MODIFIES: this
    //  EFFECTS: follows path of given directions,
    //           adding positions to availablePositions
    //           until edge, other piece reached
    protected void getPathOnce(int deltaR, int deltaF, Board board, Position position) {
        int rank = position.getRank() + deltaR;
        int file = position.getFile() + deltaF;
        if (rank >= 0 && rank <= 7 && file >= 0 && file <= 7) {
            availablePositions.add(board.getPos(rank, file));
        }
    }

    // EFFECTS: returns true if given position is
    //          empty, or of the opposite color
    protected boolean checkInvalid(Position position, Board board) {
        int rank = position.getRank();
        int file = position.getFile();
        return (board.getPos(rank, file).getPiece() != null
                && board.getPos(rank, file).getPiece().getColor() == color);
    }

    // Setters
    public void setMoved(Boolean moved) {
        this.moved = moved;
    }

    // Getters
    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public String getIconPath() {
        return iconPath;
    }

    public boolean hasMoved() {
        return moved;
    }

    // EFFECTS: turns piece as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("moved", moved);
        json.put("color", color);
        json.put("type", type);
        return json;
    }
}
