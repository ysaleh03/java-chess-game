package model.pieces;

import model.Board;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {
    private Board board;

    @BeforeEach
    void beforeEach() {
        board = new Board();
    }

    @Test
    void iconTest() {
        Bishop whiteBishop = new Bishop(1);
        Bishop blackBishop = new Bishop(-1);
        assertEquals("./data/icons/bishopw.png", whiteBishop.getIconPath());
        assertEquals("./data/icons/bishopb.png", blackBishop.getIconPath());
    }

    @Test
    void getAvailablePositionsFromCenter() {
        Bishop bishop = new Bishop(1);
        board.getPos(3, 3).setPiece(bishop);
        ArrayList<Position> availPos = bishop.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(13, availPos.size());
    }

    @Test
    void getAvailablePositionsFromEdge() {
        Bishop bishop = new Bishop(1);
        board.getPos(4, 0).setPiece(bishop);
        ArrayList<Position> availPos = bishop.getAvailablePositions(board, board.getPos(4, 0));
        assertEquals(7, availPos.size());
    }

    @Test
    void getAvailablePositionsFromCorner() {
        Bishop bishop = new Bishop(1);
        board.getPos(0, 0).setPiece(bishop);
        ArrayList<Position> availPos = bishop.getAvailablePositions(board, board.getPos(0, 0));
        assertEquals(7, availPos.size());
    }

    @Test
    void getAvailablePositionsWithFriend() {
        Bishop bishop = new Bishop(1);
        board.getPos(3, 3).setPiece(bishop);
        Pawn friendPawn = new Pawn(1);
        board.getPos(2, 2).setPiece(friendPawn);
        ArrayList<Position> availPos = bishop.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(10, availPos.size());
        assertFalse(availPos.contains(board.getPos(2, 2)));
        assertFalse(availPos.contains(board.getPos(1, 1)));
    }

    @Test
    void getAvailablePositionsWithEnemy() {
        Bishop bishop = new Bishop(1);
        board.getPos(3, 3).setPiece(bishop);
        Pawn enemyPawn = new Pawn(-1);
        board.getPos(2, 2).setPiece(enemyPawn);
        ArrayList<Position> availPos = bishop.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(11, availPos.size());
        assertTrue(availPos.contains(board.getPos(2, 2)));
        assertFalse(availPos.contains(board.getPos(1, 1)));
    }

}
