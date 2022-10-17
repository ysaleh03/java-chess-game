package model;

import model.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    private final Board board = new Board();
    private Position position;

    @BeforeEach
    void beforeEach() {
        this.position = new Position(3,3);
    }

    @Test
    void addPieceTest() {
        position.setPiece(new Pawn(1, board, position));
        assertTrue(position.getPiece() instanceof Pawn);
    }

    @Test
    void removePieceTest() {
        position.setPiece(new Pawn(1, board, position));
        position.removePiece();
        assertNull(position.getPiece());
    }
}
