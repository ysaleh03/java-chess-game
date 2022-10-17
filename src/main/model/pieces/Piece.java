package model.pieces;

import model.Board;
import model.Position;

import java.util.ArrayList;

// Piece is an abstract representation of a chess piece on the board,
// holds the following info:
// - the board it is on
// - its position on the board
// - the positions it can move to (abstract)
// - whether it has moved or not
// - its color
// - its icon
public abstract class Piece {
    protected final Board board;
    protected Position position;
    protected ArrayList<Position> availablePositions;
    protected boolean moved;
    protected final int color; // 1 = white, -1 = black
    protected String icon;

    // EFFECTS: constructs a new piece
    public Piece(int color, Board board, Position position) {
        this.board = board;
        this.position = position;
        this.availablePositions = new ArrayList<>();
        this.moved = false;
        this.color = color;
    }

    // MODIFIES: this
    // EFFECTS: generates and returns a list of positions that
    //          this piece can be moved to
    public abstract ArrayList<Position> getAvailablePositions();

    // REQUIRES: deltaR, deltaF can only be
    //           -1, 0, or 1.
    // MODIFIES: this
    //  EFFECTS: follows path of given directions,
    //           adding positions to availablePositions
    //           until edge or other piece are reached.
    protected void getPath(int deltaR, int deltaF) {
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
    protected void getPathOnce(int deltaR, int deltaF) {
        int rank = position.getRank() + deltaR;
        int file = position.getFile() + deltaF;
        if (rank >= 0 && rank <= 7 && file >= 0 && file <= 7) {
            availablePositions.add(board.getPos(rank, file));
        }
    }

    // EFFECTS: returns true if given position is
    //          empty, or of the opposite color
    protected boolean checkInvalid(Position position) {
        int rank = position.getRank();
        int file = position.getFile();
        return (board.getPos(rank, file).getPiece() != null
                && board.getPos(rank, file).getPiece().getColor() == color);
    }

    // Setters
    public void setPosition(Position position) {
        this.position = position;
    }

    public void setMoved() {
        this.moved = true;
    }

    // Getters
    public Position getPosition() {
        return position;
    }

    public int getColor() {
        return color;
    }

    public String getIcon() {
        return icon;
    }
}
