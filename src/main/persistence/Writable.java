package persistence;

import org.json.JSONObject;

/**
 * The {@code Writable} interface represents an object that can be written to JSON.
 */
public interface Writable {
    /**
     * Turns writable into a JSONObject
     * @return JSONObject representation of writable
     */
    JSONObject toJson();
}
