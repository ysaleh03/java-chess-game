package model.pieces;

import model.Board;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {
    private Board board;

    @BeforeEach
    void beforeEach() {
        board = new Board();
    }

    @Test
    void iconTest() {
        Queen whiteQueen = new Queen(1);
        Queen blackQueen = new Queen(-1);
        assertEquals("./data/icons/queenw.png", whiteQueen.getIconPath());
        assertEquals("./data/icons/queenb.png", blackQueen.getIconPath());
    }

    @Test
    void getAvailablePositionsFromCenter() {
        Queen queen = new Queen(1);
        board.getPos(3, 3).setPiece(queen);
        ArrayList<Position> availPos = queen.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(27, availPos.size());
    }

    @Test
    void getAvailablePositionsFromEdge() {
        Queen queen = new Queen(1);
        board.getPos(4, 0).setPiece(queen);
        ArrayList<Position> availPos = queen.getAvailablePositions(board, board.getPos(4, 0));
        assertEquals(21, availPos.size());
    }

    @Test
    void getAvailablePositionsFromCorner() {
        Queen queen = new Queen(1);
        board.getPos(0, 0).setPiece(queen);
        ArrayList<Position> availPos = queen.getAvailablePositions(board, board.getPos(0, 0));
        assertEquals(21, availPos.size());
    }

    @Test
    void getAvailablePositionsWithTwoFriends() {
        Queen queen = new Queen(1);
        board.getPos(3, 3).setPiece(queen);
        Pawn friendPawn = new Pawn(1);
        board.getPos(2, 2).setPiece(friendPawn);
        Rook friendRook = new Rook(1);
        board.getPos(3, 6).setPiece(friendRook);
        ArrayList<Position> availPos = queen.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(22, availPos.size());
        assertFalse(availPos.contains(board.getPos(1, 1)));
        assertFalse(availPos.contains(board.getPos(5, 6)));
    }

    @Test
    void getAvailablePositionsWithEnemy() {
        Queen queen = new Queen(1);
        board.getPos(3, 3).setPiece(queen);
        Rook enemyRook = new Rook(-1);
        board.getPos(3, 5).setPiece(enemyRook);
        ArrayList<Position> availPos = queen.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(25, availPos.size());
        assertTrue(availPos.contains(board.getPos(3, 5)));
    }

}
