package model.pieces;

import model.Board;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RookTest {
    private Board board;

    @BeforeEach
    void beforeEach() {
        board = new Board();
    }

    @Test
    void iconTest() {
        Rook whiteRook =  new Rook(1);
        Rook blackRook =  new Rook(-1);
        assertEquals("./data/icons/rookw.png", whiteRook.getIconPath());
        assertEquals("./data/icons/rookb.png", blackRook.getIconPath());
    }

    @Test
    void getAvailablePositionsFromCenter() {
        Rook rook =  new Rook(1);
        board.getPos(3, 3).setPiece(rook);
        ArrayList<Position> availPos = rook.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(14, availPos.size());
    }

    @Test
    void getAvailablePositionsFromEdge() {
        Rook rook =  new Rook(1);
        board.getPos(4, 0).setPiece(rook);
        ArrayList<Position> availPos = rook.getAvailablePositions(board, board.getPos(4, 0));
        assertEquals(14, availPos.size());
    }

    @Test
    void getAvailablePositionsFromCorner() {
        Rook rook =  new Rook(1);
        board.getPos(0, 0).setPiece(rook);
        ArrayList<Position> availPos = rook.getAvailablePositions(board, board.getPos(0, 0));
        assertEquals(14, availPos.size());
    }

    @Test
    void getAvailablePositionsWithFriend() {
        Rook rook =  new Rook(1);
        board.getPos(3, 3).setPiece(rook);
        Pawn friendPawn =  new Pawn(1);
        board.getPos(3, 1).setPiece(friendPawn);
        ArrayList<Position> availPos = rook.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(12, availPos.size());
        assertFalse(availPos.contains(board.getPos(3, 0)));
    }

    @Test
    void getAvailablePositionsWithEnemy() {
        Rook rook =  new Rook(1);
        board.getPos(3, 3).setPiece(rook);
        Pawn enemyPawn =  new Pawn(-1);
        board.getPos(1, 3).setPiece(enemyPawn);
        ArrayList<Position> availPos = rook.getAvailablePositions(board, board.getPos(3, 3));
        assertEquals(13, availPos.size());
        assertFalse(availPos.contains(board.getPos(0, 1)));
    }

}
