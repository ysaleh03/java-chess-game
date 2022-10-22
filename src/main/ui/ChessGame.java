package ui;

import exceptions.InvalidMoveException;
import exceptions.InvalidPieceException;
import exceptions.InvalidPositionException;
import model.Board;
import model.Position;
import model.pieces.Piece;
import model.players.Human;
import model.players.Player;
import org.json.JSONObject;
import persistence.Writable;
import ui.tools.MoveTool;

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
    }

    // EFFECTS: Constructs a ChessGame object from given parameters
    public ChessGame(Player player1, Player player2, Board board, Integer turns) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.player1.setBoard(board);
        this.player2.setBoard(board);
        this.turns = turns;
    }

    // MODIFIES: this
    //  EFFECTS: iterates nextTurn until checkMate,
    //           then displays how many turns it took
    public void playGame() {
        int whoseTurn = 1;
        while (!board.checkMate()) {
            nextTurn(whoseTurn);
            whoseTurn *= -1;
            this.turns += 1;
        }
        for (Player player : new Player[]{player1, player2}) {
            if (player.getColor() == whoseTurn) {
                winner = player;
                break;
            }
        }
        System.out.println("Check Mate");
        System.out.println(winner.getName() + " wins!");
        System.out.println("This game lasted " + turns);
    }

    // REQUIRES: turn is 1 or -1
    // MODIFIES: this
    //  EFFECTS: prints the board and lists of captured pieces,
    //           lets player whose turn it is make a move
    private void nextTurn(int turn) {
        printBoard();
        printCaptures();
        for (Player player : new Player[]{player1, player2}) {
            if (player.getColor() == turn) {
                System.out.println("\n" + player.getName() + "'s move");
                takeMove(player);
            }
        }
    }

    // MODIFIES: this
    //  EFFECTS: takes inputs from human player to make move
    private void takeMove(Player player) {
        if (player instanceof Human) {
            MoveTool moveTool = new MoveTool(player, this.board);
            Position pos1;
            Position pos2;
            try {
                pos1 = moveTool.selectPiecePos();
                pos2 = moveTool.selectPosition("Select Position: ");
                player.makeMove(pos1, pos2);
            } catch (InvalidPieceException e) {
                System.out.println("Invalid Piece");
                takeMove(player);
            } catch (InvalidPositionException e) {
                System.out.println("Invalid Position");
                takeMove(player);
            } catch (InvalidMoveException e) {
                System.out.println("Invalid Move");
                takeMove(player);
            }
        }
    }

    // EFFECTS: prints board to console
    private void printBoard() {
        int num = 9;
        int color = -1;
        System.out.println("\n  a b c d e f g h");
        for (Position[] rank : this.board.getBoard()) {
            num--;
            color *= -1;
            System.out.print(num + " ");
            for (Position p : rank) {
                if (p.getPiece() != null) {
                    System.out.print(p.getPiece().getIcon() + " ");
                } else if (color == 1) {
                    System.out.print("□" + " ");
                } else {
                    System.out.print("■" + " ");
                }
                color *= -1;
            }
            System.out.println();
        }
    }

    private void printCaptures() {
        for (Player player : new Player[]{player1, player2}) {
            if (player.getCapturedPieces().size() > 0) {
                System.out.print(player.getName() + "'s captures: ");
                for (Piece piece : player.getCapturedPieces()) {
                    System.out.print(piece.getIcon() + " ");
                }
                System.out.println();
            }
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player1", player1.toJson());
        json.put("player2", player2.toJson());
        json.put("board", board.toJson());
        json.put("turns", turns);
        if (winner != null) {
            json.put("winner", winner.toJson());
        } else {
            json.put("winner", JSONObject.NULL);
        }
        return json;
    }
}
