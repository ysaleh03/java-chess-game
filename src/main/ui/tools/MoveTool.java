package ui.tools;

import model.Board;
import model.Position;
import model.pieces.Piece;
import model.players.Player;

import java.util.List;
import java.util.Scanner;

// Tool that takes String input and returns Piece or Position,
// allows user to select Pieces and Positions from board.
public class MoveTool {
    private static final List<Character> FILE_REFERENCES =
            List.of(new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}); // suggested by IntelliJ, from List.java

    private final Player human;
    private final Board board;

    public MoveTool(Player human, Board board) {
        this.human = human;
        this.board = board;
    }

    //  EFFECTS: asks user for a position reference,
    //           if it contains Piece, returns piece,
    //           else returns null
    public Piece selectPiece() {
        Piece piece = selectPosition("Select piece: ").getPiece();

        if (piece == null || piece.getColor() != human.getColor()) {
            piece = selectPiece();
        }
        return piece;
    }

    // REQUIRES: scanner input is 2 characters long (letter, number)
    //  EFFECTS: uses scanner input to find corresponding
    //           Position, returns it
    public Position selectPosition(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);

        String posRef = scanner.nextLine();
        posRef = posRef.toLowerCase();

        int rank = 8 - Character.getNumericValue(posRef.charAt(1)); // from geeksforgeeks.org, linked below
        // https://www.geeksforgeeks.org/java-program-to-convert-char-to-int/#:~:text=In%20Java%2C%20we%20can%20convert,getNumericValue(char)%20method.
        char fileChar = posRef.charAt(0);
        int file = FILE_REFERENCES.indexOf(fileChar);
        return board.getBoard()[rank][file];
    }
}
