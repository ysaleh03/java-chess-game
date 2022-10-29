package persistence;

import model.Entry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LeaderBoardTest extends JsonTest {
    private static final ArrayList<Entry> LEADERBOARD0 = LeaderBoard.getLeaderBoard(); //saves original leaderboard
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
        LeaderBoard.writeLeaderBoard(new ArrayList<>()); //creates blank leaderboard
        LeaderBoard.addEntry(ENTRY1);
        LeaderBoard.addEntry(ENTRY2);
        LeaderBoard.addEntry(ENTRY3);
        LeaderBoard.addEntry(ENTRY4);
        LeaderBoard.addEntry(ENTRY5);
    }

    @Test
    void cantFindLeaderBoard() {
        ArrayList<Entry>  entries = LeaderBoard.getLeaderBoard();
        File leaderboard = new File("./data/leaderboard.json");
        if (leaderboard.delete()) {
            entries = LeaderBoard.getLeaderBoard();
        } else {
            fail("Expected leaderboard.json");
        }

        assertEquals(0, entries.size());
    }

    @Test
    void addEntryTooHighTest() {
        LeaderBoard.addEntry(ENTRY6);
        Entry[] expected = {ENTRY1, ENTRY2, ENTRY3, ENTRY4, ENTRY5};
        ArrayList<Entry> lb = LeaderBoard.getLeaderBoard();
        for (int i = 0; i < lb.size() - 1; i++) {
            checkEntry(expected[i].getName(), expected[i].getTurns(), lb.get(i));
        }
    }

    @Test
    void addEntryInBetweenTest() {
        LeaderBoard.addEntry(ENTRY7);
        checkEntry(ENTRY7.getName(), ENTRY7.getTurns(), LeaderBoard.getLeaderBoard().get(4));
    }

    @Test
    void addEntrySameTurnsTest() {
        LeaderBoard.addEntry(ENTRY8);
        checkEntry(ENTRY8.getName(), ENTRY8.getTurns(), LeaderBoard.getLeaderBoard().get(3));
    }

    @Test
    void invalidLeaderBoardPathTest() {
        LeaderBoard.setPath("./data/an\0illegal:File$Name");
        try {
            LeaderBoard.writeLeaderBoard(new ArrayList<>());
            fail("Expected a RuntimeException");
        } catch (RuntimeException e) {
            //pass
        } finally {
            LeaderBoard.setPath("./data/leaderboard.json");
        }

    }

    @AfterAll
    static void afterAll() {
        LeaderBoard.writeLeaderBoard(LEADERBOARD0);
    }
}
