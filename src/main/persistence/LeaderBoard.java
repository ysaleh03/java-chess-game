package persistence;

import model.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

// Represents the leaderboard,
// holding the top 5 winners with their turns,
// in ascending order
public class LeaderBoard {
    private static String path = "./data/leaderboard.json";

    // REQUIRES: turns > 0
    // MODIFIES: this
    //  EFFECTS: if winner made it onto leaderboard, adds them
    //           based on turns, removes 6th entry
    public static void addEntry(Entry entry) {
        ArrayList<Entry> entries = getLeaderBoard();
        entries.add(entry);
        entries.sort(Comparator.comparingInt(Entry::getTurns));
        if (entries.size() > 5) {
            entries.subList(5, entries.size() - 1).clear();
        }
        writeLeaderBoard(entries);
    }

    // EFFECTS: returns leaderboard as ArrayList
    public static ArrayList<Entry> getLeaderBoard() {
        JSONArray jsonArray = readLeaderBoard();
        ArrayList<Entry> leaderBoard = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            leaderBoard.add(parseEntry(jsonObject));
        }
        return leaderBoard;
    }

    private static Entry parseEntry(JSONObject json) {
        return new Entry(json.getString("name"), json.getInt("turns"));
    }

    // MODIFIES: this
    //  EFFECTS: reads source file as string and returns it,
    //           if leaderboard cannot be found, creates new one and tries again
    // from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private static JSONArray readLeaderBoard() {
        StringBuilder contentBuilder = new StringBuilder();
        while (true) {
            try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
                stream.forEach(contentBuilder::append);
                break;
            } catch (IOException e) {
                writeLeaderBoard(new ArrayList<>());
            }
        }
        return new JSONArray(contentBuilder.toString());
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

    public static void setPath(String str) {
        path = str;
    }
}
