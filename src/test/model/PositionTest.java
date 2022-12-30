package model;

import model.pieces.Pawn;
import model.pieces.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    private Position position;

    @BeforeEach
    void beforeEach() {
        this.position = new Position(3, 3);
    }

    @Test
    void addPieceTest() {
        position.setPiece(new Pawn(1));
        assertTrue(position.getPiece() instanceof Pawn);
    }

    @Test
    void getNameTest() {
        assertEquals("d5", position.getName());
    }

    @Test
    void promoteWhitePawnTest() {
        position = new Position(0, 2);
        position.setPiece(new Pawn(1));
        assertTrue(position.getPiece() instanceof Queen);
        assertEquals(1, position.getPiece().getColor());
    }

    @Test
    void doNotPromoteWhitePawnTest() {
        position = new Position(7, 2);
        position.setPiece(new Pawn(1));
        assertTrue(position.getPiece() instanceof Pawn);
    }

    @Test
    void promoteBlackPawnTest() {
        position = new Position(7, 2);
        position.setPiece(new Pawn(-1));
        assertTrue(position.getPiece() instanceof Queen);
        assertEquals(-1, position.getPiece().getColor());
    }

    @Test
    void doNotPromoteBlackPawnTest() {
        position = new Position(0, 2);
        position.setPiece(new Pawn(-1));
        assertTrue(position.getPiece() instanceof Pawn);
    }

    @Test
    void removePieceTest() {
        position.setPiece(new Pawn(1));
        position.removePiece();
        assertNull(position.getPiece());
    }
}
