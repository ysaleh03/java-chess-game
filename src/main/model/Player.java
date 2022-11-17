package model;

import model.exceptions.IllegalMoveException;
import model.pieces.Piece;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a chess player that can make moves
// holds the following information:
// - name
// - color (using)
// - captured pieces
// - board
public class Player implements Writable {
    protected final String name;
    protected final ArrayList<Piece> capturedPieces;
    protected Board board;

    // EFFECTS: Constructs a new Player with given name,
    //          no captures, null board
    public Player(String name) {
        this.name = name;
        this.capturedPieces = new ArrayList<>();
    }

    // EFFECTS: Constructs a loaded Player with given name,
    //          captures, null board
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
    public void makeMove(Position pos1, Position pos2) throws IllegalMoveException {
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
            throw new IllegalMoveException();
        }
    }

    // Setters
    public void setBoard(Board board) {
        this.board = board;
    }

    // Getters
    public String getName() {
        return name;
    }

    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    // EFFECTS: returns player as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("capturedPieces", capturesToJson());
        return json;
    }

    // EFFECTS: iterates over capturedPieces, turning pieces
    //          into JSONObjects, returns them in JSONArray
    private JSONArray capturesToJson() {
        JSONArray json = new JSONArray();
        for (Piece p : capturedPieces) {
            json.put(p.toJson());
        }
        return json;
    }
}
