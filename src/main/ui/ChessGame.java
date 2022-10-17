package ui;

import model.Board;
import model.Position;
import model.pieces.Piece;
import model.players.Human;
import model.players.Player;
import ui.tools.MoveTool;

// Represents a game of chess,
// holds following information:
// - Board game is being played on
// - Players playing game
// - Num of turns elapsed
// - Winner of game (set when game over)
public class ChessGame {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private int turns;
    private Player winner;

    // EFFECTS: Constructs a new ChessGame instance
    public ChessGame(Player player1, Player player2) {
        this.board = new Board();
        this.board.setDefaultBoard();
        this.player1 = player1;
        this.player2 = player2;
        this.turns = 0;
        playGame();
    }

    // MODIFIES: this
    //  EFFECTS: iterates nextTurn until checkMate,
    //           then displays how many turns it took
    private void playGame() {
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
                if (player instanceof Human) {
                    MoveTool moveTool = new MoveTool(player, this.board);
                    Piece piece;
                    Position position;
                    do {
                        piece = moveTool.selectPiece();
                        position = moveTool.selectPosition("Select Position: ");
                    } while (!player.makeMove(piece, position));
                }
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
            if (player.getCaptures().size() > 0) {
                System.out.print(player.getName() + "'s captures: ");
                for (Piece piece : player.getCaptures()) {
                    System.out.print(piece.getIcon() + " ");
                }
                System.out.println();
            }
        }
    }
}
