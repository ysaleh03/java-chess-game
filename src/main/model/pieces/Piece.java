package model.pieces;

import model.Board;
import model.Position;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/**
 * The {@code Piece} class is an abstract representation of a chess piece.
 * <p>
 * All piece objects store their type, color, and icon, whether they have moved, and where they can move.
 *
 * @author Youssef Saleh
 */
public abstract class Piece implements Writable {
    protected final int color; // 1 = white, -1 = black
    protected String type;
    protected String iconPath;
    protected boolean moved;
    protected ArrayList<Position> availablePositions;

    /**
     * Constructs a new unmoved piece of the given color
     *
     * @param color The color of the piece
     *              <p>white = 1
     *              <p>black = -1
     */
    public Piece(int color) {
        this.availablePositions = new ArrayList<>();
        this.moved = false;
        this.color = color;
    }

    /**
     * Generates a list of positions that this piece can be moved to.
     *
     * @param board    board this piece is on
     * @param position position of this piece
     * @return list of positions
     */
    public abstract ArrayList<Position> getAvailablePositions(Board board, Position position);

    /**
     * Follows path of given directions, checking for available positions
     * until edge or obstacle reached.
     *
     * @param deltaR   direction of vertical movement (one of -1, 0, 1)
     * @param deltaF   direction of horizontal movement (one of -1, 0, 1)
     * @param board    board this piece is on
     * @param position position of this piece
     */
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

    /**
     * Follows path of given directions <b>once</b>, checking if position is available
     *
     * @param deltaR   direction of vertical movement (one of -1, 0, 1)
     * @param deltaF   direction of horizontal movement (one of -1, 0, 1)
     * @param board    board this piece is on
     * @param position position of this piece
     */
    protected void getPathOnce(int deltaR, int deltaF, Board board, Position position) {
        int rank = position.getRank() + deltaR;
        int file = position.getFile() + deltaF;
        if (rank >= 0 && rank <= 7 && file >= 0 && file <= 7) {
            availablePositions.add(board.getPos(rank, file));
        }
    }

    /**
     * Checks if piece cannot move to given position.
     *
     * @param position position to be checked
     * @param board    board this piece is on
     * @return {@code true} if position contains piece of same color, else {@code false}
     */
    protected boolean checkInvalid(Position position, Board board) {
        int rank = position.getRank();
        int file = position.getFile();
        return (board.getPos(rank, file).getPiece() != null
                && board.getPos(rank, file).getPiece().getColor() == color);
    }

    // Setters

    /**
     * Sets {@code moved} to given boolean
     *
     * @param moved boolean
     */
    public void setMoved(Boolean moved) {
        this.moved = moved;
    }

    // Getters

    /**
     * @return a string representation this piece's type
     */
    public String getType() {
        return type;
    }

    /**
     * @return an integer representation of this piece's color
     * <p>white = 1
     * <p>black = -1
     */
    public int getColor() {
        return color;
    }

    /**
     * @return the filepath to this piece's icon
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * @return {@code true} if this piece has moved, else {@code false}
     */
    public boolean hasMoved() {
        return moved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("moved", moved);
        json.put("color", color);
        json.put("type", type);
        return json;
    }
}
