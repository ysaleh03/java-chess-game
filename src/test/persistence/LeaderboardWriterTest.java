package persistence;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class LeaderboardWriterTest extends JsonTest {
    @Test
    void invalidLeaderBoardPathTest() {
        LeaderboardWriter.setPath("./data/an\0illegal:File$Name");
        try {
            LeaderboardWriter.writeLeaderBoard(new ArrayList<>());
            fail("Expected a RuntimeException");
        } catch (RuntimeException e) {
            //pass
        } finally {
            LeaderboardWriter.setPath("./data/leaderboard.json");
        }
    }
}
