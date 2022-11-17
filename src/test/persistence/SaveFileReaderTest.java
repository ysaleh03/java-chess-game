package persistence;

import model.Board;
import model.ChessGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SaveFileReaderTest extends JsonTest {

    @Test
    void readNonExistentTest() {
        try {
            SaveFileReader.read("doesNotExist");
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void readFooBarTurn0Test() {
        Board board = new Board();
        board.setDefaultBoard();

        try {
            ChessGame chessGame = SaveFileReader.read("FooBarTurn0.json");
            checkPlayer("Foo", new ArrayList<>(), chessGame.getPlayer1());
            checkPlayer("Bar", new ArrayList<>(), chessGame.getPlayer2());
            checkBoard(board.getPositions(), chessGame.getBoard());
            assertEquals(0, chessGame.getTurns());
            assertNull(chessGame.getWinner());
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    void readCorruptedFileTest() {
        try {
            SaveFileReader.read("corruptedFileTest.json");
            fail("Expected an IOException");
        } catch (IOException e) {
            //pass
        }
    }
}
