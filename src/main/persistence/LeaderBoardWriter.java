package persistence;

import model.Entry;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

// Represents a writer that writes new entries to the leaderboard at default path
public final class LeaderBoardWriter {
    private static String path = "./data/leaderboard.json";

    private LeaderBoardWriter() {}

    // REQUIRES: turns > 0
    // MODIFIES: this
    //  EFFECTS: if winner made it onto leaderboard, adds them
    //           based on turns, removes 6th entry
    public static void addEntry(Entry entry) {
        ArrayList<Entry> entries = LeaderBoardReader.getLeaderBoard();
        entries.add(entry);
        entries.sort(Comparator.comparingInt(Entry::getTurns));
        if (entries.size() > 5) {
            entries.subList(5, entries.size() - 1).clear();
        }
        writeLeaderBoard(entries);
    }

    // MODIFIES: this
    //  EFFECTS: writes new leaderboard at PATH
    public static void writeLeaderBoard(ArrayList<Entry> entries) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
        JSONArray json = new JSONArray();
        for (Entry e : entries) {
            json.put(e.toJson());
        }
        writer.print(json.toString(5));
        writer.close();
    }

    // FOR TESTING!!!
    public static void setPath(String str) {
        path = str;
    }
}
