package model;

import model.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    private Board board;

    @BeforeEach
    void beforeEach() {
        this.board = new Board();
    }

    @Test
    void emptyBoardTest() {
        assertEquals(8, board.getPositions().length);
        assertEquals(8, board.getPositions()[0].length);
        boolean allEmpty = true;
        for (Position[] rank : board.getPositions()) {
            for (Position p : rank) {
                if (p.getPiece() != null) {
                    allEmpty = false;
                    break;
                }
            }
        }
        assertTrue(allEmpty);
    }

    @Test
    void defaultBoardRanks01() {
        board.setDefaultBoard();
        assertTrue(board.getPos(0, 0).getPiece() instanceof Rook
                && board.getPos(0, 0).getPiece().getColor() == -1);
        assertTrue(board.getPos(0, 1).getPiece() instanceof Knight
                && board.getPos(0, 1).getPiece().getColor() == -1);
        assertTrue(board.getPos(0, 2).getPiece() instanceof Bishop
                && board.getPos(0, 2).getPiece().getColor() == -1);
        assertTrue(board.getPos(0, 3).getPiece() instanceof Queen
                && board.getPos(0, 3).getPiece().getColor() == -1);
        assertTrue(board.getPos(0, 4).getPiece() instanceof King
                && board.getPos(0, 4).getPiece().getColor() == -1);
        assertTrue(board.getPos(0, 5).getPiece() instanceof Bishop
                && board.getPos(0, 5).getPiece().getColor() == -1);
        assertTrue(board.getPos(0, 6).getPiece() instanceof Knight
                && board.getPos(0, 6).getPiece().getColor() == -1);
        assertTrue(board.getPos(0, 7).getPiece() instanceof Rook
                && board.getPos(0, 7).getPiece().getColor() == -1);
        boolean allPawns = true;
        for (Position p : board.getPositions()[1]) {
            if (!(p.getPiece() instanceof Pawn) || p.getPiece().getColor() != -1) {
                allPawns = false;
                break;
            }
        }
        assertTrue(allPawns);
    }

    @Test
    void defaultBoardRanks67() {
        board.setDefaultBoard();
        assertTrue(board.getPos(7, 0).getPiece() instanceof Rook
                && board.getPos(7, 0).getPiece().getColor() == 1);
        assertTrue(board.getPos(7, 1).getPiece() instanceof Knight
                && board.getPos(7, 1).getPiece().getColor() == 1);
        assertTrue(board.getPos(7, 2).getPiece() instanceof Bishop
                && board.getPos(7, 2).getPiece().getColor() == 1);
        assertTrue(board.getPos(7, 3).getPiece() instanceof Queen
                && board.getPos(7, 3).getPiece().getColor() == 1);
        assertTrue(board.getPos(7, 4).getPiece() instanceof King
                && board.getPos(7, 4).getPiece().getColor() == 1);
        assertTrue(board.getPos(7, 5).getPiece() instanceof Bishop
                && board.getPos(7, 5).getPiece().getColor() == 1);
        assertTrue(board.getPos(7, 6).getPiece() instanceof Knight
                && board.getPos(7, 6).getPiece().getColor() == 1);
        assertTrue(board.getPos(7, 7).getPiece() instanceof Rook
                && board.getPos(7, 7).getPiece().getColor() == 1);
        boolean allPawns = true;
        for (Position p : board.getPositions()[6]) {
            if (!(p.getPiece() instanceof Pawn) || p.getPiece().getColor() != 1) {
                allPawns = false;
                break;
            }
        }
        assertTrue(allPawns);
    }
}
