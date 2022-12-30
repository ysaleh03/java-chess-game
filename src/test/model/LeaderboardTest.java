package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderboardTest {
    private static final Leaderboard theLeaderboard = Leaderboard.getInstance();
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
        theLeaderboard.clearLeaderboard(); //creates blank leaderboard
        theLeaderboard.addEntry(ENTRY1);
        theLeaderboard.addEntry(ENTRY2);
        theLeaderboard.addEntry(ENTRY3);
        theLeaderboard.addEntry(ENTRY4);
        theLeaderboard.addEntry(ENTRY5);
    }

    @Test
    void addEntryTooHighTest() {
        theLeaderboard.addEntry(ENTRY6);
        Entry[] expected = {ENTRY1, ENTRY2, ENTRY3, ENTRY4, ENTRY5};
        List<Entry> lb = theLeaderboard.getEntries();
        for (int i = 0; i < lb.size() - 1; i++) {
            assertEquals(expected[i], lb.get(i));
        }
    }

    @Test
    void addEntryInBetweenTest() {
        theLeaderboard.addEntry(ENTRY7);
        assertEquals(ENTRY7, theLeaderboard.getEntries().get(4));
    }

    @Test
    void addEntrySameTurnsTest() {
        theLeaderboard.addEntry(ENTRY8);
        assertEquals(ENTRY8, theLeaderboard.getEntries().get(3));
    }

    @Test
    void addEntryAlreadyExists() {
        theLeaderboard.addEntry(ENTRY3);
        Entry[] expected = {ENTRY1, ENTRY2, ENTRY3, ENTRY4, ENTRY5};
        List<Entry> lb = theLeaderboard.getEntries();
        for (int i = 0; i < lb.size() - 1; i++) {
            assertEquals(expected[i], theLeaderboard.getEntries().get(i));
        }
    }
}
