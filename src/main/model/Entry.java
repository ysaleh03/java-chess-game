package model;

import model.Player;
import org.json.JSONObject;
import persistence.Writable;

public class Entry implements Writable {
    private final String name;
    private final int turns;

    public Entry(String name, int turns) {
        this.name = name;
        this.turns = turns;
    }

    public String getName() {
        return name;
    }

    public int getTurns() {
        return turns;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("turns", turns);
        return json;
    }
}
