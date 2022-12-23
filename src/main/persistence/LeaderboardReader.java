package persistence;

import model.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads the leaderboard from JSON data stored at default path
public final class LeaderboardReader {
    private static final String PATH = "./data/leaderboard.json";

    private LeaderboardReader() {}

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
            try (Stream<String> stream = Files.lines(Paths.get(PATH), StandardCharsets.UTF_8)) {
                stream.forEach(contentBuilder::append);
                break;
            } catch (IOException e) {
                LeaderboardWriter.writeLeaderBoard(new ArrayList<>());
            }
        }
        return new JSONArray(contentBuilder.toString());
    }
}
