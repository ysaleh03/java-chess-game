package model;

import model.pieces.King;
import org.json.JSONObject;
import persistence.Writable;

// Represents a game of chess,
// holds following information:
// - Board game is being played on
// - Players playing game
// - Num of turns elapsed
// - Winner of game (set when game over)
public class ChessGame implements Writable {
    private final Player player1;
    private final Player player2;
    private final Board board;
    private int turns;
    private int state; // one of: 0 - player1 selecting piece, 1 - player1 selecting position,
                       //         2 - player2 selecting piece, 3 - player2 selecting position.
    private Player winner;

    // EFFECTS: Constructs a new ChessGame object
    public ChessGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
        this.board.setDefaultBoard();
        this.player1.setBoard(board);
        this.player2.setBoard(board);
        this.turns = 0;
        this.state = 0;
    }

    // EFFECTS: Constructs a ChessGame object from given parameters
    public ChessGame(Player player1, Player player2, Board board, int turns, int state, Player winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.player1.setBoard(board);
        this.player2.setBoard(board);
        this.turns = turns;
        this.state = state;
        this.winner = winner;
    }

    //  EFFECTS: returns false if both kings on board,
    //           else returns true
    public boolean checkMate() {
        boolean whiteKing = false;
        boolean blackKing = false;
        for (Position[] rank : this.board.getPositions()) {
            for (Position pos : rank) {
                if (pos.getPiece() instanceof King) {
                    if (pos.getPiece().getColor() == 1) {
                        whiteKing = true;
                    } else {
                        blackKing = true;
                    }
                }
            }
        }
        if (!whiteKing) {
            winner = player2;
        } else if (!blackKing) {
            winner = player1;
        }
        return !(whiteKing && blackKing);
    }

    //Setters
    public void setWinner(Player player) {
        winner = player;
    }

    public void incrementTurns() {
        turns++;
    }

    public void incrementState() {
        if (state >= 3) {
            state = 0;
        } else {
            state++;
        }
    }

    public void resetState() {
        if (state <= 1) {
            state = 0;
        } else if (state <= 3) {
            state = 2;
        }
    }

    //Getters
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }

    public int getTurns() {
        return turns;
    }


    public int getState() {
        return state;
    }

    public Player getWinner() {
        return winner;
    }

    @Override
    public JSONObject toJson() {
        resetState();
        JSONObject json = new JSONObject();
        json.put("player1", player1.toJson());
        json.put("player2", player2.toJson());
        json.put("board", board.toJson());
        json.put("turns", turns);
        json.put("state", state);
        return json;
    }
}
