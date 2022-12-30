package model;

import persistence.LeaderboardReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * The {@code Leaderboard} class represents the leaderboard singleton, read from JSON data.
 */
public class Leaderboard implements Iterable<Entry> {
    private static Leaderboard theLeaderboard;
    private final ArrayList<Entry> entries;

    private Leaderboard() {
        entries = LeaderboardReader.read();
    }

    /**
     * If Leaderboard exists, returns it, otherwise creates new Leaderboard instance and returns it.
     *
     * @return the Leaderboard instance
     */
    public static Leaderboard getInstance() {
        if (theLeaderboard == null) {
            theLeaderboard = new Leaderboard();
        }
        return theLeaderboard;
    }

    /**
     * If entry makes it onto leaderboard, adds it based on turns, removes 6th entry.
     *
     * @param entry the winning entry
     */
    public void addEntry(Entry entry) {
        if (!entries.contains(entry)) {
            entries.add(entry);
            entries.sort(Comparator.comparingInt(Entry::getTurns));
            if (entries.size() > 5) {
                entries.remove(5);
            }
        }
    }

    public void clearLeaderboard() {
        entries.clear();
    }

    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public Iterator<Entry> iterator() {
        return entries.iterator();
    }
}
