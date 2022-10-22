package model.players;

import exceptions.InvalidMoveException;
import model.Board;
import model.Position;
import model.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HumanTest {
    // not tested on real humans
    private Human human;
    private Board board;
    private Position friendPos;
    private Position enemyPos;
    private Pawn friendPawn;
    private Pawn enemyPawn;

    @BeforeEach
    void beforeEach() {
        this.human = new Human("Foo", 1);
        this.board = new Board();
        this.human.setBoard(this.board);
        this.friendPawn = new Pawn(1);
        this.enemyPawn = new Pawn(-1);
        this.friendPos = board.getPos(3,3);
        this.enemyPos = this.board.getPos(2,2);

        friendPos.setPiece(friendPawn);
        enemyPos.setPiece(enemyPawn);
    }

    @Test
    void newHumanTest() {
        assertEquals("Foo", human.getName());
        assertEquals(1, human.getColor());
        assertEquals(0, human.getCapturedPieces().size());
    }

    @Test
    void makeMoveNoTake() {
        Position newPos = board.getPos(2,3);
        try {
            human.makeMove(friendPos, newPos);
        } catch (InvalidMoveException e) {
            fail();
        }
        assertNull(friendPos.getPiece());
        assertEquals(friendPawn, newPos.getPiece());
        assertEquals(0, human.getCapturedPieces().size());
    }

    @Test
    void makeMoveAndTake() {
        try {
            human.makeMove(friendPos, enemyPos);
        } catch (InvalidMoveException e) {
            fail();
        }
        assertNull(friendPos.getPiece());
        assertEquals(friendPawn, enemyPos.getPiece());
        assertEquals(1, human.getCapturedPieces().size());
        assertTrue(human.getCapturedPieces().contains(enemyPawn));
    }

    @Test
    void makeInvalidMove() {
        Position newPos = board.getPos(4,3);
        assertThrows(InvalidMoveException.class, () -> human.makeMove(friendPos, newPos));
    }

}
