package model;

import model.pieces.EventTest;
import model.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChessGameTest extends EventTest {
    private final EventLog theLog = EventLog.getInstance();
    private ChessGame chessGame;
    private Player player1;
    private Player player2;
    private Board defBoard;

    @BeforeEach
    void beforeEach() {
        player1 = new Player("Foo");
        player2 = new Player("Bar");
        defBoard = new Board();
        chessGame = new ChessGame(player1, player2);

        theLog.clear();
    }

    @Test
    void newChessGameTest() {
        ChessGame newGame = new ChessGame(player1, player2);
        assertEquals(player1, newGame.getPlayer1());
        assertEquals(player2, newGame.getPlayer2());
        assertNotEquals(defBoard, newGame.getBoard());
        assertEquals(0, newGame.getTurns());
        assertEquals(0, newGame.getState());
        checkEvent(1, "New game created; players: Foo and Bar");
    }

    @Test
    void loadChessGameTest() {
        chessGame = new ChessGame(player1, player2, defBoard, 3, 2, null);
        assertEquals(player1, chessGame.getPlayer1());
        assertEquals(player2, chessGame.getPlayer2());
        assertEquals(defBoard, chessGame.getBoard());
        assertEquals(3, chessGame.getTurns());
        assertEquals(2, chessGame.getState());
        assertNull(chessGame.getWinner());
        checkEvent(1, "Saved game loaded on turn 3; players: Foo and Bar");
    }

    @Test
    void incrementTurnsTest() {
        assertEquals(0, chessGame.getTurns());
        chessGame.incrementTurns();
        assertEquals(1, chessGame.getTurns());
    }

    @Test
    void incrementStateFrom0Test() {
        chessGame.incrementState();
        assertEquals(1, chessGame.getState());
    }

    @Test
    void incrementStateFrom3Test() {
        chessGame.incrementState(); // 1
        chessGame.incrementState(); // 2
        chessGame.incrementState(); // 3
        assertEquals(3, chessGame.getState());
        chessGame.incrementState(); // back to 0
        assertEquals(0, chessGame.getState());
    }

    @Test
    void resetStateFrom1Test() {
        chessGame.incrementState(); // 1
        assertEquals(1, chessGame.getState());
        chessGame.resetState();
        assertEquals(0, chessGame.getState());
    }

    @Test
    void resetStateFrom3Test() {
        chessGame.incrementState(); // 1
        chessGame.incrementState(); // 2
        chessGame.incrementState(); // 3
        assertEquals(3, chessGame.getState());
        chessGame.resetState();
        assertEquals(2, chessGame.getState());
    }

    @Test
    void gameNotOver() {
        assertFalse(chessGame.checkMate());
    }

    @Test
    void gameOverWhiteWins() {
        chessGame.getBoard().getPos(0, 4).removePiece();
        assertTrue(chessGame.checkMate());
        assertEquals(player1, chessGame.getWinner());
        checkEvent(1, "Check mate! Foo won in 0 turns");
    }

    @Test
    void gameOverBlackWins() {
        chessGame.getBoard().getPos(7, 4).removePiece();
        assertTrue(chessGame.checkMate());
        assertEquals(player2, chessGame.getWinner());
        checkEvent(1, "Check mate! Bar won in 0 turns");
    }

    @Test
    void winnerTest() {
        assertNull(chessGame.getWinner());
        chessGame.setWinner(player2);
        assertEquals(player2, chessGame.getWinner());
    }
}
