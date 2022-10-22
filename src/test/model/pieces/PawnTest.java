package model.pieces;

import model.Board;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {
    private Board board;

    @BeforeEach
    void beforeEach() {
        board = new Board();
    }

    @Test
    void iconTest() {
        Pawn whitePawn =  new Pawn(1);
        Pawn blackPawn =  new Pawn(-1);
        assertEquals("♙", whitePawn.getIcon());
        assertEquals("♟", blackPawn.getIcon());
    }

    @Test
    void getAvailablePositionLeftEdge() {
        Pawn pawn =  new Pawn(1);
        board.getPos(3, 0).setPiece(pawn);
        ArrayList<Position> availPos = pawn.getAvailablePositions(board, board.getPos(3, 0));
        assertEquals(2, availPos.size());
    }

    @Test
    void getAvailablePositionRightEdge() {
        Pawn pawn =  new Pawn(1);
        board.getPos(3, 7).setPiece(pawn);
        ArrayList<Position> availPos = pawn.getAvailablePositions(board, board.getPos(3, 7));
        assertEquals(2, availPos.size());
    }

    @Test
    void getAvailablePositionsWhiteNeverMoved() {
        Pawn pawn =  new Pawn(1);
        board.getPos(6, 3).setPiece(pawn);
        ArrayList<Position> availPos = pawn.getAvailablePositions(board, board.getPos(6, 3));
        assertEquals(2, availPos.size());
        assertTrue(availPos.contains(board.getPos(5, 3)));
        assertTrue(availPos.contains(board.getPos(4, 3)));
    }

    @Test
    void getAvailablePositionsBlackNeverMoved() {
        Pawn pawn =  new Pawn(-1);
        board.getPos(1, 3).setPiece(pawn);
        ArrayList<Position> availPos = pawn.getAvailablePositions(board, board.getPos(1, 3));
        assertEquals(2, availPos.size());
        assertTrue(availPos.contains(board.getPos(2, 3)));
        assertTrue(availPos.contains(board.getPos(3, 3)));
    }

    @Test
    void getAvailablePositionsAlreadyMoved() {
        Pawn pawn =  new Pawn(1);
        pawn.setMoved(true);
        board.getPos(6, 3).setPiece(pawn);
        ArrayList<Position> availPos = pawn.getAvailablePositions(board, board.getPos(6, 3));
        assertEquals(1, availPos.size());
        assertTrue(availPos.contains(board.getPos(5, 3)));
    }

    @Test
    void getAvailablePositionsWithFriend() {
        Pawn pawn =  new Pawn(1);
        board.getPos(3, 3).setPiece(pawn);
        Rook friendRook =  new Rook(1);
        board.getPos(2, 3).setPiece(friendRook);
        ArrayList<Position> availPos = pawn.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(0, availPos.size());
    }

    @Test
    void getAvailablePositionsCantTakeFriend() {
        Pawn pawn =  new Pawn(1);
        board.getPos(3, 3).setPiece(pawn);
        Rook friendRook =  new Rook(1);
        board.getPos(2, 2).setPiece(friendRook);
        ArrayList<Position> availPos = pawn.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(2, availPos.size());
        assertFalse(availPos.contains(board.getPos(2, 2)));
    }

    @Test
    void getAvailablePositionsWithEnemy() {
        Pawn pawn =  new Pawn(1);
        board.getPos(3, 3).setPiece(pawn);
        Rook enemyRook =  new Rook(-1);
        board.getPos(2, 2).setPiece(enemyRook);
        ArrayList<Position> availPos = pawn.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(3, availPos.size());
        assertTrue(availPos.contains(board.getPos(2, 3)));
        assertTrue(availPos.contains(board.getPos(1, 3)));
        assertTrue(availPos.contains(board.getPos(2, 2)));
    }

    @Test
    void getAvailablePositionsWithFriendAndEnemy() {
        Pawn pawn =  new Pawn(1);
        board.getPos(3, 3).setPiece(pawn);
        Rook friendRook =  new Rook(1);
        board.getPos(2, 3).setPiece(friendRook);
        Rook enemyRook =  new Rook(-1);
        board.getPos(2, 2).setPiece(enemyRook);
        ArrayList<Position> availPos = pawn.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(1, availPos.size());
        assertTrue(availPos.contains(board.getPos(2, 2)));
    }

}
