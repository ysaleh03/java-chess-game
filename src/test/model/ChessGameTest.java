package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChessGameTest {
    private ChessGame chessGame;
    private Player player1;
    private Player player2;
    private Board defBoard;

    @BeforeEach
    void beforeEach() {
        player1 = new Player("Foo", 1);
        player2 = new Player("Bar", -1);
        defBoard = new Board();
        chessGame = new ChessGame(player1, player2);
    }

    @Test
    void newChessGameTest() {
        assertEquals(player1, chessGame.getPlayer1());
        assertEquals(player2, chessGame.getPlayer2());
        assertNotEquals(defBoard, chessGame.getBoard());
        assertEquals(0, chessGame.getTurns());
    }

    @Test
    void loadChessGameTest() {
        chessGame = new ChessGame(player1, player2, defBoard, 3, null);
        assertEquals(player1, chessGame.getPlayer1());
        assertEquals(player2, chessGame.getPlayer2());
        assertEquals(defBoard, chessGame.getBoard());
        assertEquals(3, chessGame.getTurns());
        assertNull(chessGame.getWinner());
    }

    @Test
    void incrementTurnsTest() {
        assertEquals(0, chessGame.getTurns());
        chessGame.incrementTurns();
        assertEquals(1, chessGame.getTurns());
    }

    @Test
    void gameNotOver() {
        assertFalse(chessGame.checkMate());
    }

    @Test
    void gameOverWhiteWins() {
        chessGame.getBoard().getPos(0,4).removePiece();
        assertTrue(chessGame.checkMate());
        assertEquals(player1, chessGame.getWinner());
    }

    @Test
    void gameOverBlackWins() {
        chessGame.getBoard().getPos(7,4).removePiece();
        assertTrue(chessGame.checkMate());
        assertEquals(player2, chessGame.getWinner());
    }

    @Test
    void winnerTest() {
        assertNull(chessGame.getWinner());
        chessGame.setWinner(player2);
        assertEquals(player2, chessGame.getWinner());
    }
}
