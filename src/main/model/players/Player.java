package model.players;

import model.Position;
import model.pieces.Piece;

import java.util.ArrayList;

// Represents a chess player that can make moves
// and return its name, color, and list of captured pieces
public interface Player {
    boolean makeMove(Piece piece, Position pos);

    String getName();

    int getColor();

    ArrayList<Piece> getCaptures();
}
