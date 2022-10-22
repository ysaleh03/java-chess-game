package persistence;

import model.Board;
import model.Position;
import model.pieces.*;
import model.players.*;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.ChessGame;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class FileReader {
    private static final String DIRECTORY = "./data/saves/";

    private final String path;

    // EFFECTS: constructs reader to read from source file
    public FileReader(String fileName) {
        this.path = DIRECTORY + fileName;
    }

    // EFFECT: reads the file at path, returns ChessGame object
    public ChessGame read() throws IOException {
        String jsonData = readFile(path);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseChessGame(jsonObject);
    }

    // EFFECT: reads source file as string and returns it
    // from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private String readFile(String path) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECT: parses JSONObject and returns ChessGame
    private ChessGame parseChessGame(JSONObject json) throws IOException {
        Player player1 = parsePlayer(json.getJSONObject("player1"));
        Player player2 = parsePlayer(json.getJSONObject("player2"));
        Board board = parseBoard(json.getJSONObject("board"));
        int turns = json.getInt("turns");
        return new ChessGame(player1, player2, board, turns); // stub
    }

    // EFFECT: parses JSONObject and returns Player
    private Player parsePlayer(JSONObject json) throws IOException {
        String name = json.getString("name");
        int color = json.getInt("color");

        Player player = new Human(name, color); //for now!!!

        JSONArray jsonCapturedPieces = json.getJSONArray("capturedPieces");
        ArrayList<Piece> capturedPieces = new ArrayList<>();
        if (jsonCapturedPieces.length() > 0) {
            for (int i = 0; i <= jsonCapturedPieces.length(); i++) {
                JSONObject jsonPiece = jsonCapturedPieces.getJSONObject(i);
                capturedPieces.add(parsePiece(jsonPiece));
            }
            player.setCapturedPieces(capturedPieces);
        }

        return player;
    }

    // EFFECT: parses JSONObject and returns Board
    private Board parseBoard(JSONObject json) throws IOException {
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

    // EFFECT: parses JSONObject and returns Position
    private Position parsePosition(JSONObject json) throws IOException {
        int rank = json.getInt("rank");
        int file = json.getInt("file");
        Position position = new Position(rank, file);

        if (!json.isNull("piece")) {
            Piece piece = parsePiece(json.getJSONObject("piece"));
            position.setPiece(piece);
        }

        return position;
    }

    // EFFECT: parses JSONObject and returns Piece
    @SuppressWarnings("methodlength")
    private Piece parsePiece(JSONObject json) throws IOException {
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
                throw new IOException("Can't read piece");
        }
        piece.setMoved(json.getBoolean("moved"));
        return piece;
    }
}
