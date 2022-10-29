package persistence;

import model.Entry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LeaderBoardReaderTest {
    private static final ArrayList<Entry> LEADERBOARD0 = LeaderBoardReader.getLeaderBoard(); // original leaderboard

    @Test
    void cantFindLeaderBoard() {
        ArrayList<Entry> entries = LeaderBoardReader.getLeaderBoard();
        File leaderboard = new File("./data/leaderboard.json");
        if (leaderboard.delete()) {
            entries = LeaderBoardReader.getLeaderBoard();
        } else {
            fail("Expected leaderboard.json");
        }
        assertEquals(0, entries.size());
    }

    @AfterAll
    static void afterAll() {
        LeaderBoardWriter.writeLeaderBoard(LEADERBOARD0);
    }
}
