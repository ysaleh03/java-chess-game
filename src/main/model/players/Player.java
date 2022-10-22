package model.players;

import exceptions.InvalidMoveException;
import model.Board;
import model.Position;
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
public abstract class Player implements Writable {
    protected final String name;
    protected final int color;
    protected final ArrayList<Piece> capturedPieces;
    protected Board board;

    // EFFECTS: Constructs a new Player with given name,
    //          no captures, null board
    public Player(String name, int color) {
        this.name = name;
        this.color = color;
        this.capturedPieces = new ArrayList<>();
    }

    // MODIFIES: this, pos1, pos2, board
    //  EFFECTS: gets piece from pos1,
    //           if pos2 is in piece's availablePositions, moves piece to pos2,
    //           else throws InvalidMoveException.
    //           if pos2 already contains a piece, removes it and adds it
    //           to capturedPieces
    public abstract void makeMove(Position pos1, Position pos2) throws InvalidMoveException;

    // Setters
    public void setBoard(Board board) {
        this.board = board;
    }

    public void setCapturedPieces(ArrayList<Piece> pieces) {
        capturedPieces.addAll(pieces);
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    // EFFECTS: turns player as JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("color", color);
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
