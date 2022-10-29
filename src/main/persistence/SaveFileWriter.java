package persistence;

import model.ChessGame;
import org.json.JSONObject;

import java.io.*;

public class SaveFileWriter {
    private static final String DIRECTORY = "./data/saves/";

    private PrintWriter writer;
    private final String path;

    // EFFECTS: constructs writer to write to destination file
    public SaveFileWriter(ChessGame chessGame, String fileName) throws FileNotFoundException {
        this.path = DIRECTORY + fileName + ".json";
        open();
        write(chessGame);
        close();
        System.out.println("Saved as " + fileName);
    }

    // MODIFIES: this
    //  EFFECTS: opens writer, throws FileNotFoundException if destination file cannot
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
