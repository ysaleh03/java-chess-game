package ui;

import model.exceptions.IllegalMoveException;
import model.ChessGame;
import model.Position;
import model.pieces.Piece;
import model.Player;
import model.Entry;
import persistence.LeaderBoard;
import ui.exceptions.IllegalPieceException;
import ui.exceptions.InvalidPieceException;
import ui.exceptions.InvalidPositionException;
import ui.tools.MoveTool;

// Represents the user interface
// holds following information:
// - ChessGame being played
// - Players playing game
public class ChessGameApp {
    private final ChessGame chessGame;
    private final Player player1;
    private final Player player2;

    // EFFECTS: Constructs a new ChessGameApp object
    public ChessGameApp(ChessGame chessGame) {
        this.chessGame = chessGame;
        this.player1 = chessGame.getPlayer1();
        this.player2 = chessGame.getPlayer2();
        playGame();
    }

    // MODIFIES: this
    //  EFFECTS: iterates nextTurn until checkMate,
    //           then displays how many turns it took
    private void playGame() {
        int whoseTurn = 1;
        while (!chessGame.checkMate()) {
            chessGame.incrementTurns();
            nextTurn(whoseTurn);
            whoseTurn *= -1;
        }
        for (Player player : new Player[]{player1, player2}) {
            if (player.getColor() == whoseTurn) {
                chessGame.setWinner(player);
                break;
            }
        }
        System.out.println("Check Mate");
        System.out.println(chessGame.getWinner().getName() + " wins!");
        System.out.println("This game lasted " + chessGame.getTurns());
        LeaderBoard lb = new LeaderBoard();
        lb.addEntry(new Entry(chessGame.getWinner().getName(), chessGame.getTurns()));
    }

    // REQUIRES: turn is ±1
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
        MoveTool moveTool = new MoveTool(player, chessGame.getBoard());
        Position pos1;
        Position pos2;
        try {
            pos1 = moveTool.selectPiecePos();
            pos2 = moveTool.selectPosition("Select Position: ");
            player.makeMove(pos1, pos2);
        } catch (InvalidPositionException e) {
            System.out.println("Invalid Position");
            takeMove(player);
        } catch (InvalidPieceException e) {
            System.out.println("There is no piece there");
            takeMove(player);
        } catch (IllegalPieceException e) {
            System.out.println("You cannot move that piece");
            takeMove(player);
        } catch (IllegalMoveException e) {
            System.out.println("Invalid Move");
            takeMove(player);
        }
    }

    // EFFECTS: prints board to console
    private void printBoard() {
        int num = 9;
        int color = -1;
        System.out.println("\n  a b c d e f g h");
        for (Position[] rank : chessGame.getBoard().getPositions()) {
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

    // EFFECTS: prints captured pieces
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
}
