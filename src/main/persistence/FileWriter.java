package persistence;

import org.json.JSONObject;
import ui.ChessGame;

import java.io.*;

public class FileWriter {
    private static final String DIRECTORY = "./data/saves/";

    private PrintWriter writer;
    private final String path;

    // EFFECTS: constructs writer to write to destination file
    public FileWriter(ChessGame chessGame, String fileName) {
        this.path = DIRECTORY + fileName;
        try {
            open();
            write(chessGame);
            close();
            System.out.println("Saved as " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save");
        }
    }

    // MODIFIES: this
    //  EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    //           be opened for writing
    private void open() throws FileNotFoundException {
        writer = new PrintWriter(path);
    }

    // MODIFIES: this
    //  EFFECTS: writes JSON -> String representation of workroom to file
    private void write(ChessGame chessGame) {
        JSONObject json = chessGame.toJson();
        writer.print(json.toString(5));
    }

    // MODIFIES: this
    //  EFFECTS: closes writer
    private void close() {
        writer.close();
    }
}
