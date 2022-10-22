package model.pieces;

import model.Board;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class KingTest {
    private Board board;

    @BeforeEach
    void beforeEach() {
        board = new Board();
    }

    @Test
    void iconTest() {
        King whiteKing =  new King(1);
        King blackKing =  new King(-1);
        assertEquals("♔", whiteKing.getIcon());
        assertEquals("♚", blackKing.getIcon());
    }

    @Test
    void getAvailablePositionsFromCenter() {
        King king =  new King(1);
        board.getPos(3, 3).setPiece(king);
        ArrayList<Position> availPos = king.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(8, availPos.size());
    }

    @Test
    void getAvailablePositionsFromEdge() {
        King king =  new King(1);
        board.getPos(4, 0).setPiece(king);
        ArrayList<Position> availPos = king.getAvailablePositions(board, board.getPos(4, 0));
        assertEquals(5, availPos.size());
    }

    @Test
    void getAvailablePositionsFromCorner() {
        King king =  new King(1);
        board.getPos(0, 0).setPiece(king);
        ArrayList<Position> availPos = king.getAvailablePositions(board, board.getPos(0, 0));
        assertEquals(3, availPos.size());
    }

    @Test
    void getAvailablePositionsWithFriend() {
        King king =  new King(1);
        board.getPos(3, 3).setPiece(king);
        Pawn friendPawn =  new Pawn(1);
        board.getPos(2, 2).setPiece(friendPawn);
        ArrayList<Position> availPos = king.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(7, availPos.size());
        assertFalse(availPos.contains(board.getPos(2, 2)));
    }

    @Test
    void getAvailablePositionsWithEnemy() {
        King king =  new King(1);
        board.getPos(3, 3).setPiece(king);
        Pawn enemyPawn =  new Pawn(-1);
        board.getPos(2, 2).setPiece(enemyPawn);
        ArrayList<Position> availPos = king.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(8, availPos.size());
        assertTrue(availPos.contains(board.getPos(2, 2)));
    }

}
