package model;

import model.exceptions.IllegalMoveException;
import model.pieces.Piece;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/**
 * The {@code Player} class represents a chess player.
 * Can make moves, has a name, a color, a board, and a list of captured pieces.
 */
public class Player implements Writable {
    private final String name;
    private final ArrayList<Piece> capturedPieces;
    private Board board;

    private final EventLog theLog = EventLog.getInstance();

    /**
     * Constructs a new player with the given name, no captured pieces, and no board.
     * @param name a string of the player's name
     */
    public Player(String name) {
        this.name = name;
        this.capturedPieces = new ArrayList<>();
    }

    /**
     * Constructs a previously existing player, with the given name, captured pieces, and no board.
     * @param name a string of the player's name
     * @param capturedPieces a list of pieces captured by the player
     */
    public Player(String name, ArrayList<Piece> capturedPieces) {
        this.name = name;
        this.capturedPieces = capturedPieces;
    }

    // MODIFIES: this, pos1, pos2, board
    //  EFFECTS: gets piece from pos1,
    //           if pos2 is in piece's availablePositions, moves piece to pos2,
    //           else throws IllegalMoveException.
    //           if pos2 already contains a piece, removes it and adds it
    //           to capturedPieces

    /**
     * Moves the piece at {@code pos1} to {@code pos2}. If {@code pos2} contains a piece, adds it to player's captured pieces.
     * @param pos1 position of the piece being moved
     * @param pos2 position the piece is moving to
     * @throws IllegalMoveException if {@code pos2} is not in selected piece's available positions
     */
    public void makeMove(Position pos1, Position pos2) throws IllegalMoveException {
        StringBuilder msg = new StringBuilder(name + " moved " + pos1.getName() + " to " + pos2.getName());
        Piece piece = pos1.getPiece();
        if (piece.getAvailablePositions(this.board, pos1).contains(pos2)) {
            if (pos2.getPiece() != null) {
                capturedPieces.add(pos2.getPiece());
                pos2.removePiece();
                msg.append("; captured piece (added to their capturedPieces)");
            }
            piece.setMoved(true);
            pos2.setPiece(piece);
            pos1.removePiece();
        } else {
            throw new IllegalMoveException();
        }
        theLog.logEvent(new Event(msg.toString()));
    }

    // Setters

    /**
     * Sets board to given board.
     * @param board board to be set
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    // Getters

    /**
     * @return string name of player
     */
    public String getName() {
        return name;
    }

    /**
     * @return list of pieces captured by player
     */
    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("capturedPieces", capturesToJson());
        return json;
    }

    /**
     * @return JSONArray of captured pieces
     */
    private JSONArray capturesToJson() {
        JSONArray json = new JSONArray();
        for (Piece p : capturedPieces) {
            json.put(p.toJson());
        }
        return json;
    }
}
