package persistence;

import model.ChessGame;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of ChessGame to file at path
public final class SaveFileWriter {
    private static final String DIRECTORY = "./data/saves/";
    private static PrintWriter writer;

    private SaveFileWriter() {}

    // EFFECTS: constructs writer to write to destination file
    public static void write(ChessGame chessGame, String fileName) throws FileNotFoundException {
        String path = DIRECTORY + fileName + ".json";
        open(path);
        writeFile(chessGame, path);
        close();
        System.out.println("Saved as " + fileName);
    }

    // MODIFIES: this
    //  EFFECTS: opens writer, throws FileNotFoundException if destination file cannot
    //           be opened for writing
    private static void open(String path) throws FileNotFoundException {
        writer = new PrintWriter(path);
    }

    // MODIFIES: this
    //  EFFECTS: writes JSON -> String representation of workroom to file
    private static void writeFile(ChessGame chessGame, String path) {
        JSONObject json = chessGame.toJson();
        writer.print(json.toString(5));
    }

    // MODIFIES: this
    //  EFFECTS: closes writer
    private static void close() {
        writer.close();
    }
}
