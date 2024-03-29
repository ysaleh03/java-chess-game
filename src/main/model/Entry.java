package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entry that = (Entry) o;
        return this.turns == that.turns && this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, turns);
    }
}
