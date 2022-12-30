package persistence;

import model.Board;
import model.Entry;
import model.Position;
import model.pieces.Piece;
import model.players.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class JsonTest {

    protected void checkEntry(String name, int turns, Entry entry) {
        assertEquals(name, entry.getName());
        assertEquals(turns, entry.getTurns());
    }

    protected void checkPlayer(String name, ArrayList<Piece> capturedPieces, Player player) {
        assertEquals(name, player.getName());
        for (int i = 0; i < capturedPieces.size(); i++) {
            Piece expectedPiece = capturedPieces.get(i);
            Piece realPiece = player.getCapturedPieces().get(i);
            checkPiece(expectedPiece.getType(),
                    expectedPiece.getColor(),
                    expectedPiece.hasMoved(),
                    realPiece);
        }
    }

    protected void checkBoard(Position[][] positions, Board board) {
        int r = 0;
        for (Position[] rank : board.getPositions()) {
            int f = 0;
            for (Position p : rank) {
                checkPosition(r, f, p.getPiece(), positions[r][f]);
                f++;
            }
            r++;
        }
    }

    private void checkPosition(int rank, int file, Piece piece, Position position) {
        assertEquals(rank, position.getRank());
        assertEquals(file, position.getFile());
        Piece positionPiece = position.getPiece();
        if (positionPiece != null) {
            checkPiece(positionPiece.getType(),
                    positionPiece.getColor(),
                    positionPiece.hasMoved(), piece);
        }
    }

    private void checkPiece(String type, int color, boolean moved, Piece piece) {
        assertEquals(type, piece.getType());
        assertEquals(color, piece.getColor());
        assertEquals(moved, piece.hasMoved());
    }
}
