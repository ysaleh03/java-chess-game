package model.players;

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
    private Pawn friendPawn;
    private Pawn enemyPawn;

    @BeforeEach
    void beforeEach() {
        this.human = new Human("Foo", 1);
        this.board = new Board();
        this.friendPawn = new Pawn(1, board, board.getPos(3,3));
        this.enemyPawn = new Pawn(-1, board, board.getPos(2,2));
        this.board.getPos(3,3).setPiece(friendPawn);
        this.board.getPos(2,2).setPiece(enemyPawn);
    }

    @Test
    void newHumanTest() {
        assertEquals("Foo", human.getName());
        assertEquals(1, human.getColor());
        assertEquals(0, human.getCaptures().size());
    }

    @Test
    void makeMoveNoTake() {
        Position oldPos = friendPawn.getPosition();
        Position newPos = board.getPos(2,3);
        assertTrue(human.makeMove(friendPawn, newPos));
        assertNull(oldPos.getPiece());
        assertEquals(friendPawn, newPos.getPiece());
        assertEquals(0, human.getCaptures().size());
    }

    @Test
    void makeMoveAndTake() {
        Position oldPos = friendPawn.getPosition();
        Position newPos = board.getPos(2,2);
        assertTrue(human.makeMove(friendPawn, newPos));
        assertNull(oldPos.getPiece());
        assertEquals(friendPawn, newPos.getPiece());
        assertEquals(1, human.getCaptures().size());
        assertTrue(human.getCaptures().contains(enemyPawn));
    }

    @Test
    void makeInvalidMove() {
        Position oldPos = friendPawn.getPosition();
        Position newPos = board.getPos(4,3);
        assertFalse(human.makeMove(friendPawn, newPos));
    }

}
