package persistence;

import model.exceptions.IllegalMoveException;
import model.Board;
import model.ChessGame;
import model.Player;
import model.Position;
import model.pieces.Pawn;
import model.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SaveFileWriterTest extends JsonTest {
    private Player player1;
    private Player player2;
    private ChessGame chessGame;


    @BeforeEach
    void beforeEach() {
        player1 = new Player("Foo");
        player2 = new Player("Bar");
        chessGame = new ChessGame(player1, player2);
    }

    @Test
    void illegalFileTest() {
        try {
            SaveFileWriter.write(chessGame, "an\0illegal:File$Name");
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void turn0Test() {
        try {
            SaveFileWriter.write(chessGame, "turn0Test");

            ChessGame cg = SaveFileReader.read("turn0Test.json");
            Board mtBoard = new Board();

            assertEquals(0, cg.getTurns());
            checkPlayer("Foo", new ArrayList<>() ,cg.getPlayer1());
            checkPlayer("Bar", new ArrayList<>() ,cg.getPlayer2());
            checkBoard(mtBoard.getPositions(), cg.getBoard());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    void turn2Test() {
        try {
            Position[][] board = chessGame.getBoard().getPositions();

            //turn 1
            chessGame.incrementTurns();
            player1.makeMove(board[6][1], board[4][1]);
            player2.makeMove(board[1][2], board[3][2]);

            //turn 2
            chessGame.incrementTurns();
            player1.makeMove(board[4][1], board[3][2]); //captures pawn!

            chessGame.incrementState(); // state 1
            chessGame.incrementState(); // state 2

            SaveFileWriter.write(chessGame, "turn2Test");

            ChessGame cg = SaveFileReader.read("turn2Test.json");
            Board mtBoard = new Board();

            ArrayList<Piece> expectedCaptures = new ArrayList<>();
            expectedCaptures.add(new Pawn(-1));
            expectedCaptures.get(0).setMoved(true);

            assertEquals(2, cg.getTurns());
            assertEquals(2, cg.getState());
            checkPlayer("Foo", expectedCaptures, cg.getPlayer1());
            checkPlayer("Bar", new ArrayList<>(), cg.getPlayer2());
            checkBoard(mtBoard.getPositions(), cg.getBoard());
        } catch (IllegalMoveException e) {
            fail("Unexpected IllegalMoveException");
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

//    This test is no longer supported, saved games do not include winner anymore.

//    @Test
//    void wonGameTest() {
//        chessGame.getBoard().getPos(0,4).removePiece();
//        chessGame.checkMate();
//
//        try {
//            SaveFileWriter.write(chessGame, "wonGameTest");
//            ChessGame cg = SaveFileReader.read("wonGameTest.json");
//            checkPlayer("Foo", new ArrayList<>(), cg.getWinner());
//        } catch (IOException e) {
//            fail("Unexpected IOException");
//        }
//    }
}
