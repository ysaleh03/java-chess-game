package persistence;

import model.Entry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class LeaderboardWriterTest extends JsonTest {
    private static final ArrayList<Entry> LEADERBOARD0 = LeaderboardReader.getLeaderBoard(); // original leaderboard
    private static final Entry ENTRY1 = new Entry("Foo", 8);
    private static final Entry ENTRY2 = new Entry("Bar", 9);
    private static final Entry ENTRY3 = new Entry("Baz", 10);
    private static final Entry ENTRY4 = new Entry("Qux", 11);
    private static final Entry ENTRY5 = new Entry("Quux", 15);
    private static final Entry ENTRY6 = new Entry("Corge", 24);
    private static final Entry ENTRY7 = new Entry("Grault", 12);
    private static final Entry ENTRY8 = new Entry("Garply", 10);

    @BeforeEach
    void beforeEach() {
        LeaderboardWriter.writeLeaderBoard(new ArrayList<>()); //creates blank leaderboard
        LeaderboardWriter.addEntry(ENTRY1);
        LeaderboardWriter.addEntry(ENTRY2);
        LeaderboardWriter.addEntry(ENTRY3);
        LeaderboardWriter.addEntry(ENTRY4);
        LeaderboardWriter.addEntry(ENTRY5);
    }

    @Test
    void addEntryTooHighTest() {
        LeaderboardWriter.addEntry(ENTRY6);
        Entry[] expected = {ENTRY1, ENTRY2, ENTRY3, ENTRY4, ENTRY5};
        ArrayList<Entry> lb = LeaderboardReader.getLeaderBoard();
        for (int i = 0; i < lb.size() - 1; i++) {
            checkEntry(expected[i].getName(), expected[i].getTurns(), lb.get(i));
        }
    }

    @Test
    void addEntryInBetweenTest() {
        LeaderboardWriter.addEntry(ENTRY7);
        checkEntry(ENTRY7.getName(), ENTRY7.getTurns(), LeaderboardReader.getLeaderBoard().get(4));
    }

    @Test
    void addEntrySameTurnsTest() {
        LeaderboardWriter.addEntry(ENTRY8);
        checkEntry(ENTRY8.getName(), ENTRY8.getTurns(), LeaderboardReader.getLeaderBoard().get(3));
    }

    @Test
    void addEntryAlreadyExists() {
        LeaderboardWriter.addEntry(ENTRY3);
        Entry[] expected = {ENTRY1, ENTRY2, ENTRY3, ENTRY4, ENTRY5};
        ArrayList<Entry> lb = LeaderboardReader.getLeaderBoard();
        for (int i = 0; i < lb.size() - 1; i++) {
            checkEntry(expected[i].getName(), expected[i].getTurns(), lb.get(i));
        }
    }

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

    @AfterAll
    static void afterAll() {
        LeaderboardWriter.writeLeaderBoard(LEADERBOARD0);
    }
}
