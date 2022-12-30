package persistence;

import model.Leaderboard;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LeaderboardReaderTest {

    @AfterAll
    static void afterAll() {
        LeaderboardWriter.writeLeaderBoard(Leaderboard.getInstance().getEntries());
    }

    @Test
    void cantFindLeaderBoard() {
        File leaderboard = new File("./data/leaderboard.json");
        if (leaderboard.delete()) {
            LeaderboardReader.read();
        } else {
            fail("Expected leaderboard.json");
        }
        assertEquals(0, LeaderboardReader.read().size());
    }
}
