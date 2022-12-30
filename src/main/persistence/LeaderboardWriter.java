package persistence;

import model.Entry;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

// Represents a writer that writes new entries to the leaderboard at default path
public final class LeaderboardWriter {
    private static String path = "./data/leaderboard.json";

    private LeaderboardWriter() {
    }

    // MODIFIES: this
    //  EFFECTS: writes new leaderboard at PATH
    public static void writeLeaderBoard(List<Entry> entries) {
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
