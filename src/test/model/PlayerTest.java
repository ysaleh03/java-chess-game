package model;

import model.exceptions.IllegalMoveException;
import model.pieces.EventTest;
import model.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest extends EventTest {
    private Player player;
    private Board board;
    private Position friendPos;
    private Position enemyPos;
    private Pawn friendPawn;
    private Pawn enemyPawn;

    private final EventLog theLog = EventLog.getInstance();

    @BeforeEach
    void beforeEach() {
        this.player = new Player("Foo");
        this.board = new Board();
        this.player.setBoard(this.board);
        this.friendPawn = new Pawn(1);
        this.enemyPawn = new Pawn(-1);
        this.friendPos = board.getPos(3,3);
        this.enemyPos = this.board.getPos(2,2);

        friendPos.setPiece(friendPawn);
        enemyPos.setPiece(enemyPawn);

        theLog.clear();
    }

    @Test
    void newHumanTest() {
        assertEquals("Foo", player.getName());
        assertEquals(0, player.getCapturedPieces().size());
    }

    @Test
    void makeMoveNoTakeTest() {
        Position newPos = board.getPos(2,3);
        try {
            player.makeMove(friendPos, newPos);
        } catch (IllegalMoveException e) {
            fail("Unexpected IllegalMoveException");
        }
        assertNull(friendPos.getPiece());
        assertEquals(friendPawn, newPos.getPiece());
        assertEquals(0, player.getCapturedPieces().size());

        checkEvent(1, "Foo moved d5 to d6");
    }

    @Test
    void makeMoveAndTakeTest() {
        try {
            player.makeMove(friendPos, enemyPos);
        } catch (IllegalMoveException e) {
            fail("Unexpected IllegalMoveException");
        }
        assertNull(friendPos.getPiece());
        assertEquals(friendPawn, enemyPos.getPiece());
        assertEquals(1, player.getCapturedPieces().size());
        assertTrue(player.getCapturedPieces().contains(enemyPawn));

        checkEvent(1,"Foo moved d5 to c6; captured piece (added to their capturedPieces)");
    }

    @Test
    void makeInvalidMoveTest() {
        Position newPos = board.getPos(4, 3);
        try {
            player.makeMove(friendPos, newPos);
            fail("Expected IllegalMoveException");
        } catch (IllegalMoveException e) {
            //pass
        }
    }

}
