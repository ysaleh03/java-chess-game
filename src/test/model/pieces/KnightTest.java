package model.pieces;

import model.Board;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
    private Board board;

    @BeforeEach
    void beforeEach() {
        board = new Board();
    }

    @Test
    void iconTest() {
        Knight whiteKnight =  new Knight(1, board, board.getPos(3, 3));
        Knight blackKnight =  new Knight(-1, board, board.getPos(4, 4));
        assertEquals("♘", whiteKnight.getIcon());
        assertEquals("♞", blackKnight.getIcon());
    }

    @Test
    void getAvailablePositionsFromCenter() {
        Knight knight =  new Knight(1, board, board.getPos(3, 3));
        board.getPos(3, 3).setPiece(knight);
        ArrayList<Position> availPos = knight.getAvailablePositions();
        assertEquals(8, availPos.size());
    }

    @Test
    void getAvailablePositionsFromEdge() {
        Knight knight =  new Knight(1, board, board.getPos(4, 0));
        board.getPos(4, 0).setPiece(knight);
        ArrayList<Position> availPos = knight.getAvailablePositions();
        assertEquals(4, availPos.size());
    }

    @Test
    void getAvailablePositionsNearEdge() {
        Knight knight =  new Knight(1, board, board.getPos(3, 1));
        board.getPos(3, 1).setPiece(knight);
        ArrayList<Position> availPos = knight.getAvailablePositions();
        assertEquals(6, availPos.size());
    }

    @Test
    void getAvailablePositionsFromCorner() {
        Knight knight =  new Knight(1, board, board.getPos(0, 0));
        board.getPos(0, 0).setPiece(knight);
        ArrayList<Position> availPos = knight.getAvailablePositions();
        assertEquals(2, availPos.size());
    }

    @Test
    void getAvailablePositionsWithFriend() {
        Knight knight =  new Knight(1, board, board.getPos(3, 3));
        board.getPos(3, 3).setPiece(knight);
        Pawn friendPawn =  new Pawn(1, board, board.getPos(2, 2));
        board.getPos(2, 2).setPiece(friendPawn);
        Rook friendRook = new Rook(1, board, board.getPos(1, 4));
        board.getPos(1, 4).setPiece(friendRook);
        ArrayList<Position> availPos = knight.getAvailablePositions();
        assertEquals(7, availPos.size());
        assertTrue(availPos.contains(board.getPos(1, 2)));
        assertFalse(availPos.contains(board.getPos(1, 4)));
    }

    @Test
    void getAvailablePositionsWithEnemy() {
        Knight knight =  new Knight(1, board, board.getPos(3, 3));
        board.getPos(3, 3).setPiece(knight);
        Pawn enemyPawn =  new Pawn(-1, board, board.getPos(2, 2));
        board.getPos(2, 2).setPiece(enemyPawn);
        Rook enemyRook = new Rook(-1, board, board.getPos(1, 4));
        board.getPos(1, 4).setPiece(enemyRook);
        ArrayList<Position> availPos = knight.getAvailablePositions();
        assertEquals(8, availPos.size());
        assertFalse(availPos.contains(board.getPos(2, 2)));
        assertTrue(availPos.contains(board.getPos(1, 4)));
    }

}
