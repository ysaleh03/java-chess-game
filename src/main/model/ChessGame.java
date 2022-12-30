package model;

import model.pieces.King;
import model.players.Player;
import org.json.JSONObject;
import persistence.Writable;

/**
 * The {@code ChessGame} class represents a game of chess, including the following information:
 * <p>
 * - The board the game is being played on, <p>
 * - The players playing the game, <p>
 * - The number of turns elapsed, <p>
 * - The winner of the game, if one exists.
 */
public class ChessGame implements Writable {
    private final Leaderboard theLeaderboard = Leaderboard.getInstance();
    private final Player player1;
    private final Player player2;
    private final Board board;
    private final EventLog theLog = EventLog.getInstance();
    private int turns;
    /**
     * one of:
     * <p> 0 - player1 selecting piece,
     * <p> 1 - player1 selecting position,
     * <p> 2 - player2 selecting piece,
     * <p> 3 - player2 selecting position.
     */
    private int state; // one of: 0 - player1 selecting piece, 1 - player1 selecting position,
    //         2 - player2 selecting piece, 3 - player2 selecting position.
    private Player winner;

    /**
     * Constructs a new chess game on turn and state 0; with two players and null winner.
     *
     * @param player1 the first player (white)
     * @param player2 the second player (black)
     */
    public ChessGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
        this.board.setDefaultBoard();
        this.player1.setBoard(board);
        this.player2.setBoard(board);
        this.turns = 0;
        this.state = 0;
        theLog.logEvent(new Event("New game created; players: "
                + player1.getName() + " and " + player2.getName()));
    }

    /**
     * Constructs a previously existing chess game from the given parameters.
     *
     * @param player1 the first player (white)
     * @param player2 the second player (black)
     * @param board   the board
     * @param turns   the turn the game is on
     * @param state   the state the game is on
     * @param winner  the winner, if any
     */
    public ChessGame(Player player1, Player player2, Board board, int turns, int state, Player winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.player1.setBoard(board);
        this.player2.setBoard(board);
        this.turns = turns;
        this.state = state;
        this.winner = winner;
        theLog.logEvent(new Event("Saved game loaded on turn " + turns + "; players: "
                + player1.getName() + " and " + player2.getName()));
    }

    /**
     * Checks if game is in checkmate.
     *
     * @return {@code false} if both kings are on the board, else {@code true}.
     */
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

        if (!whiteKing || !blackKing) {
            theLog.logEvent(new Event("Check mate! " + winner.getName() + " won in " + turns + " turns"));
            theLeaderboard.addEntry(new Entry(winner.getName(), turns));
        }

        return !(whiteKing && blackKing);
    }

    //Setters

    /**
     * Increments turns by 1.
     */
    public void incrementTurns() {
        turns++;
    }

    /**
     * Moves to next state.
     */
    public void incrementState() {
        if (state >= 3) {
            state = 0;
        } else {
            state++;
        }
    }

    /**
     * Resets state to previous piece-picking state.
     */
    public void resetState() {
        if (state == 1) {
            state = 0;
        } else if (state > 1) {
            state = 2;
        }
    }

    /**
     * @return player 1
     */
    public Player getPlayer1() {
        return player1;
    }

    //Getters

    /**
     * @return player 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return the current turn
     */
    public int getTurns() {
        return turns;
    }

    /**
     * @return the current state
     */
    public int getState() {
        return state;
    }

    /**
     * @return the current winner if any, otherwise {@code null}
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Sets winner to given player.
     *
     * @param player winning player
     */
    public void setWinner(Player player) {
        winner = player;
    }

    /**
     * {@inheritDoc}
     */
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
