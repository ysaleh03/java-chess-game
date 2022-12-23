package model;

import model.pieces.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

/**
 * The {@code Board} class represents a chessboard, holding a 8x8 array of {@link model.Position}.
 */
public class Board implements Writable {
    private final Position[][] positions;

    /**
     * Constructs a new board with 64 empty positions.
     */
    public Board() {
        this.positions = new Position[8][8];
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                this.positions[i][k] = new Position(i, k);
            }
        }
    }

    /**
     * Constructs a new board from given positions.
     */
    public Board(Position[][] positions) {
        this.positions = positions;
    }

    /**
     * Places pieces in their default beginning positions.
     */
    public void setDefaultBoard() {
        positions[0][0].setPiece(new Rook(-1));
        positions[0][1].setPiece(new Knight(-1));
        positions[0][2].setPiece(new Bishop(-1));
        positions[0][3].setPiece(new Queen(-1));
        positions[0][4].setPiece(new King(-1));
        positions[0][5].setPiece(new Bishop(-1));
        positions[0][6].setPiece(new Knight(-1));
        positions[0][7].setPiece(new Rook(-1));
        for (Position p : positions[1]) {
            p.setPiece(new Pawn(-1));
        }
        positions[7][0].setPiece(new Rook(1));
        positions[7][1].setPiece(new Knight(1));
        positions[7][2].setPiece(new Bishop(1));
        positions[7][3].setPiece(new Queen(1));
        positions[7][4].setPiece(new King(1));
        positions[7][5].setPiece(new Bishop(1));
        positions[7][6].setPiece(new Knight(1));
        positions[7][7].setPiece(new Rook(1));
        for (Position p : positions[6]) {
            p.setPiece(new Pawn(1));
        }
    }

    // Getters

    /**
     * @return 8x8 array of positions
     */
    public Position[][] getPositions() {
        return positions;
    }

    /**
     * @param rank integer rank number
     * @param file integer file number
     * @return position with given rank and file
     */
    public Position getPos(int rank, int file) {
        return positions[rank][file];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("board", boardToJson());
        return json;
    }

    /**
     * @return 2d JSONArray representation of board
     */
    private JSONArray boardToJson() {
        JSONArray json = new JSONArray();
        for (Position[] rank : this.positions) {
            JSONArray jsonRank = new JSONArray();
            for (Position pos : rank) {
                jsonRank.put(pos.toJson());
            }
            json.put(jsonRank);
        }
        return json;
    }
}
