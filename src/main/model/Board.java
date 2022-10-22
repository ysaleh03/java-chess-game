package model;

import model.pieces.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Board is a representation of the chessboard,
// it holds a single 8x8 array of Positions
public class Board implements Writable {
    private final Position[][] board;

    // EFFECTS: Constructs board and fills it up with Position objects
    public Board() {
        this.board = new Position[8][8];
        setEmptyBoard();
    }

    // EFFECTS: Constructs a Board object from given board
    public Board(Position[][] board) {
        this.board = board;
    }

    //  EFFECTS: returns false if both kings on board,
    //           else returns true
    public boolean checkMate() {
        int numKings = 0;
        // This would be more efficient if you
        // could check capture lists for King objects
        for (Position[] rank : this.board) {
            for (Position pos : rank) {
                if (pos.getPiece() instanceof King) {
                    numKings += 1;
                }
            }
        }
        return (numKings < 2);
    }

    // REQUIRES: board is empty
    // MODIFIES: this
    //  EFFECTS: constructs pieces in their beginning positions
    public void setDefaultBoard() {
        board[0][0].setPiece(new Rook(-1));
        board[0][1].setPiece(new Knight(-1));
        board[0][2].setPiece(new Bishop(-1));
        board[0][3].setPiece(new Queen(-1));
        board[0][4].setPiece(new King(-1));
        board[0][5].setPiece(new Bishop(-1));
        board[0][6].setPiece(new Knight(-1));
        board[0][7].setPiece(new Rook(-1));
        for (Position p : board[1]) {
            p.setPiece(new Pawn(-1));
        }
        board[7][0].setPiece(new Rook(1));
        board[7][1].setPiece(new Knight(1));
        board[7][2].setPiece(new Bishop(1));
        board[7][3].setPiece(new Queen(1));
        board[7][4].setPiece(new King(1));
        board[7][5].setPiece(new Bishop(1));
        board[7][6].setPiece(new Knight(1));
        board[7][7].setPiece(new Rook(1));
        for (Position p : board[6]) {
            p.setPiece(new Pawn(1));
        }
    }

    // MODIFIES: this
    //  EFFECTS: fills board up with Position objects
    private void setEmptyBoard() {
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                this.board[i][k] = new Position(i, k);
            }
        }
    }

    // Getters
    public Position[][] getBoard() {
        return board;
    }

    public Position getPos(int rank, int field) {
        return board[rank][field];
    }

    // EFFECTS: turns board into JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("board", boardToJson());
        return json;
    }

    // EFFECTS: turns board into 2d JSONArray representation
    private JSONArray boardToJson() {
        JSONArray json = new JSONArray();
        for (Position[] rank : this.board) {
            JSONArray jsonRank = new JSONArray();
            for (Position pos : rank) {
                jsonRank.put(pos.toJson());
            }
            json.put(jsonRank);
        }
        return json;
    }
}
