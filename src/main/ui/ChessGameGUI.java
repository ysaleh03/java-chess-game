package ui;

import model.ChessGame;
import model.Entry;
import model.Player;
import model.Position;
import model.exceptions.IllegalMoveException;
import model.pieces.Piece;
import persistence.LeaderBoardWriter;
import persistence.SaveFileWriter;
import ui.exceptions.ExitException;
import ui.exceptions.IllegalPieceException;
import ui.exceptions.InvalidPieceException;
import ui.exceptions.InvalidPositionException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

// Interactive GUI for playing a game of chess
public final class ChessGameGUI extends JFrame {
    public static final int DIMENSION = 500;
    private final ChessGame chessGame;
    private Position selectedPiecePos;

    // EFFECTS: Constructs a ChessGameGUI frame,
    //          that cannot be resized, and calls saveDialogue upon closing.
    public ChessGameGUI(ChessGame chessGame) {
        this.chessGame = chessGame;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveDialogue();
            }
        });

        add(contentPane());
        pack();
        centerOnScreen();
        setTitle(chessGame.getPlayer1().getName() + "'s turn");

        setVisible(true);
        update();
    }

    // EFFECTS: returns a JPanel containing the chessboard
    //          and panels for each player's captured pieces.
    private JPanel contentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(chessBoard());
        contentPane.add(captures(chessGame.getPlayer1()));
        contentPane.add(captures(chessGame.getPlayer2()));
        return contentPane;
    }

    // EFFECTS: returns a JPanel containing the given player's captured pieces.
    private JPanel captures(Player player) {
        JPanel captures = new JPanel();
        captures.setPreferredSize(new Dimension(DIMENSION / 2, 35));

        for (Piece piece : player.getCapturedPieces()) {
            captures.add(new JLabel(new ImageIcon(piece.getIconPath())));
        }

        captures.setBackground(new Color(20,120,100));

        return captures;
    }

    // EFFECTS: returns a JPanel displaying the chessboard.
    private JPanel chessBoard() {
        JButton[][] squares = setUpSquares();

        JPanel chessBoard = new JPanel();
        chessBoard.setPreferredSize(new Dimension(DIMENSION, DIMENSION));
        chessBoard.setLayout(new GridLayout(0, 10));

        for (String str : new String[]{"", "a", "b", "c", "d", "e", "f", "g", "h", ""}) {
            chessBoard.add(new JLabel(str, SwingConstants.CENTER));
        }

        for (int i = 0; i < 8; i++) {
            chessBoard.add(new JLabel("" + (8 - i), SwingConstants.CENTER));
            for (int j = 0; j < 8; j++) {
                chessBoard.add(squares[i][j]);
            }
            chessBoard.add(new JLabel());
        }

        chessBoard.setBackground(new Color(20,120,100));

        return chessBoard;
    }

    // EFFECTS: returns the squares for the chessboard.
    private JButton[][] setUpSquares() {
        JButton[][] squares = new JButton[8][8];
        Position[][] positions = chessGame.getBoard().getPositions();

        int color = -1;
        for (int i = 0; i < 8; i++) {
            color *= -1;
            for (int k = 0; k < 8; k++) {
                Square square = new Square(positions[i][k]);
                squares[i][k] = square;
                if (positions[i][k].getPiece() != null) {
                    square.setIcon(new ImageIcon(positions[i][k].getPiece().getIconPath()));
                }
                if (color == 1) {
                    squares[i][k].setBackground(new Color(255,225,175));
                } else {
                    squares[i][k].setBackground(new Color(180,110,0));
                }
                color *= -1;
            }
        }
        return squares;
    }

    // A chessboard square
    private class Square extends JButton {

        // EFFECTS: constructs a square.
        private Square(Position position) {
            this.setMargin(new Insets(0, 0, 0, 0));
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.setFocusPainted(false);
            this.setOpaque(true);

            this.addActionListener(a -> movePiece(position));
        }

        // MODIFIES: this
        //  EFFECTS: if state 0: selects piece for player 1
        //           if state 1: selects position for player 1,
        //                       moves selected piece, changes title
        //           if state 2: selects piece for player 2
        //           if state 3: selects position for player 2,
        //                       moves selected piece, changes title
        //           increments state and updates every call.
        private void movePiece(Position position) {
            Position selectedPosition;
            try {
                if (chessGame.getState() == 0) {
                    chessGame.incrementTurns();
                    selectedPiecePos = MoveTool.selectPiecePos(1, position);
                } else if (chessGame.getState() == 1) {
                    selectedPosition = MoveTool.selectPosition(1, position);
                    chessGame.getPlayer1().makeMove(selectedPiecePos, selectedPosition);
                } else if (chessGame.getState() == 2) {
                    selectedPiecePos = MoveTool.selectPiecePos(-1, position);
                } else if (chessGame.getState() == 3) {
                    selectedPosition = MoveTool.selectPosition(-1, position);
                    chessGame.getPlayer2().makeMove(selectedPiecePos, selectedPosition);
                }
                chessGame.incrementState();
                ChessGameGUI.this.update();
            } catch (InvalidPieceException | IllegalPieceException e) {
                // flash red maybe
            } catch (InvalidPositionException | IllegalMoveException e) {
                chessGame.resetState();
            }
        }
    }

    // MODIFIES: this
    //  EFFECTS: updates title and contentPane to reflect changes
    private void update() {
        setContentPane(contentPane());
        revalidate();
        repaint();
        if (chessGame.getState() <= 1) {
            setTitle(chessGame.getPlayer1().getName() + "'s turn");
        } else {
            setTitle(chessGame.getPlayer2().getName() + "'s turn");
        }
        if (chessGame.checkMate()) {
            JOptionPane.showMessageDialog(this,
                    chessGame.getWinner().getName() + " wins!", "Checkmate",
                    JOptionPane.INFORMATION_MESSAGE);
            saveDialogue();
        }
    }

    // EFFECTS: allows user to save game,
    //          if so, asks for filename.
    private void saveDialogue() {
        int input = JOptionPane.showConfirmDialog(this,
                "Do you want to save this game?", "Save Dialogue", JOptionPane.YES_NO_OPTION);
        if (input == JOptionPane.YES_OPTION) {
            String fileName = JOptionPane.showInputDialog(null,
                    "Enter file name: ");
            if (fileName != null) {
                try {
                    chessGame.resetState();
                    SaveFileWriter.write(chessGame, fileName);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Unable to save game", "Saving Error",
                            JOptionPane.ERROR_MESSAGE);
                    saveDialogue();
                }
            }
        }
        dispose();
        throw new ExitException();
    }

    // FROM LAB 6 SNAKE WITH BUGS
    // MODIFIES: this
    // EFFECTS:  frame is centred on desktop
    private void centerOnScreen() {
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scr.width - getWidth()) / 2, (scr.height - getHeight()) / 2);
    }
}
