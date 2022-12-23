package persistence;

import model.Entry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LeaderboardReaderTest {
    private static final ArrayList<Entry> LEADERBOARD0 = LeaderboardReader.getLeaderBoard(); // original leaderboard

    @Test
    void cantFindLeaderBoard() {
        ArrayList<Entry> entries = LeaderboardReader.getLeaderBoard();
        File leaderboard = new File("./data/leaderboard.json");
        if (leaderboard.delete()) {
            entries = LeaderboardReader.getLeaderBoard();
        } else {
            fail("Expected leaderboard.json");
        }
        assertEquals(0, entries.size());
    }

    @AfterAll
    static void afterAll() {
        LeaderboardWriter.writeLeaderBoard(LEADERBOARD0);
    }
}
