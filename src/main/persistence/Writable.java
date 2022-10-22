package persistence;

import org.json.JSONObject;

// Represents an object that can be written to a JSON save file
public interface Writable {
    JSONObject toJson();
}
