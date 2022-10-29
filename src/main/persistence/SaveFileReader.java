package persistence;

import model.Board;
import model.ChessGame;
import model.Player;
import model.Position;
import model.pieces.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads ChessGame from JSON data stored at path
public final class SaveFileReader {
    private static final String DIRECTORY = "./data/saves/";

    private SaveFileReader() {}

    // EFFECTS: reads the file at path, returns ChessGame object
    public static ChessGame read(String fileName) throws IOException {
        String jsonData = readFile(DIRECTORY + fileName + ".json");
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseChessGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    // from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private static String readFile(String path) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses JSONObject and returns ChessGame
    private static ChessGame parseChessGame(JSONObject json) throws IOException {
        Player player1 = parsePlayer(json.getJSONObject("player1"));
        Player player2 = parsePlayer(json.getJSONObject("player2"));
        Board board = parseBoard(json.getJSONObject("board"));
        int turns = json.getInt("turns");
        Player winner = null;
        if (json.has("winner")) {
            winner = parsePlayer(json.getJSONObject("winner"));
        }
        return new ChessGame(player1, player2, board, turns, winner); // stub
    }

    // EFFECTS: parses JSONObject and returns Player
    private static Player parsePlayer(JSONObject json) throws IOException {
        String name = json.getString("name");
        int color = json.getInt("color");

        JSONArray jsonCapturedPieces = json.getJSONArray("capturedPieces");
        ArrayList<Piece> capturedPieces = new ArrayList<>();

        if (jsonCapturedPieces.length() > 0) {
            for (int i = 0; i < jsonCapturedPieces.length(); i++) {
                JSONObject jsonPiece = jsonCapturedPieces.getJSONObject(i);
                capturedPieces.add(parsePiece(jsonPiece));
            }
        }
        return new Player(name, color, capturedPieces);
    }

    // EFFECTS: parses JSONObject and returns Board
    private static Board parseBoard(JSONObject json) throws IOException {
        Position[][] board = new Position[8][8];
        JSONArray jsonBoard = json.getJSONArray("board");

        for (int r = 0; r < 8; r++) {
            JSONArray jsonRank = jsonBoard.getJSONArray(r);
            Position[] rank = new Position[8];
            for (int p = 0; p < 8; p++) {
                JSONObject jsonPos = jsonRank.getJSONObject(p);
                Position position = parsePosition(jsonPos);
                rank[p] = position;
            }
            board[r] = rank;
        }
        return new Board(board);
    }

    // EFFECTS: parses JSONObject and returns Position
    private static Position parsePosition(JSONObject json) throws IOException {
        int rank = json.getInt("rank");
        int file = json.getInt("file");
        Position position = new Position(rank, file);

        if (!json.isNull("piece")) {
            Piece piece = parsePiece(json.getJSONObject("piece"));
            position.setPiece(piece);
        }
        return position;
    }

    // EFFECTS: parses JSONObject and returns Piece
    @SuppressWarnings("methodlength")
    private static Piece parsePiece(JSONObject json) throws IOException {
        Piece piece;
        String type = json.getString("type");
        int color = json.getInt("color");
        switch (type) {
            case "Bishop":
                piece = new Bishop(color);
                break;
            case "King":
                piece = new King(color);
                break;
            case "Knight":
                piece = new Knight(color);
                break;
            case "Pawn":
                piece = new Pawn(color);
                break;
            case "Queen":
                piece = new Queen(color);
                break;
            case "Rook":
                piece = new Rook(color);
                break;
            default:
                throw new IOException("Could not read piece");
        }
        piece.setMoved(json.getBoolean("moved"));
        return piece;
    }
}
