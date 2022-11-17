package ui;

import model.ChessGame;
import model.Entry;
import model.Player;
import org.json.JSONException;
import persistence.LeaderBoardReader;
import persistence.SaveFileReader;
import ui.exceptions.ExitException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

// GUI that displays menus,
// allows user to pick game mode and initialize game.
public final class MenuGUI extends JFrame {
    private static final CardLayout CARD_LAYOUT = new CardLayout();
    private final JPanel cards;

    // EFFECTS: constructs a new MenuGUI JFrame allowing access to different menus.
    public MenuGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        cards = setCards();
        add(cards);
        setTitle("Main Menu");

        pack();
        centerOnScreen();
        setVisible(true);
    }

    // EFFECTS: creates a JPanel with Card Layout,
    //          with each "card" being a submenu.
    private JPanel setCards() {
        JPanel cards = new JPanel();
        cards.setLayout(CARD_LAYOUT);
        cards.setBorder(new EmptyBorder(10, 10, 10, 10));

        cards.add(mainMenu(), "Main Menu");
        cards.add(newGameMenu(), "New Game");
        cards.add(leaderBoard(), "Leaderboard");
        return cards;
    }

    // EFFECTS: creates Main Menu, with buttons that switch to the other menus.
    private JPanel mainMenu() {
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(4,1));

        final JButton newGame = new JButton("New Game");
        newGame.addActionListener(e -> switchMenu("New Game"));

        final JButton loadGame = new JButton("Load Game");
        loadGame.addActionListener(e -> loadGame());

        final JButton leaderboard = new JButton("Leaderboard");
        leaderboard.addActionListener(e -> switchMenu("Leaderboard"));

        final JButton quit = new JButton();
        quit.setText("Quit");
        quit.addActionListener(e -> dispose());


        mainMenu.add(newGame);
        mainMenu.add(loadGame);
        mainMenu.add(leaderboard);
        mainMenu.add(quit);
        mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        return mainMenu;
    }

    // EFFECTS: displays a game setup menu, prompting user for the names of
    //          the two players, then launches new game with given players.
    private JPanel newGameMenu() {
        JPanel newGameMenu = new JPanel();
        newGameMenu.setLayout(new GridLayout(0,2));

        newGameMenu.add(new JLabel("Player 1 name: "));
        JTextField player1Name = new JTextField();
        newGameMenu.add(player1Name);

        newGameMenu.add(new JLabel("Player 2 name: "));
        JTextField player2Name = new JTextField();
        newGameMenu.add(player2Name);

        JButton start = new JButton("Start");
        start.addActionListener(e -> {
            Player player1 = new Player(player1Name.getText());
            Player player2 = new Player(player2Name.getText());
            launchGame(new ChessGame(player1, player2));
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> switchMenu("Main Menu"));

        newGameMenu.add(start);
        newGameMenu.add(cancel);

        return newGameMenu;
    }

    // EFFECTS: displays a file choosing menu, prompting user for a file,
    //          then launches chosen game.
    private void loadGame() {
        JFileChooser chooser = new JFileChooser(new File("./data/saves"));

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON files", "json");

        chooser.setFileFilter(filter);
        chooser.setApproveButtonText("Load");
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                ChessGame chessGame = SaveFileReader.read(file.getName());
                launchGame(chessGame);
            } catch (IOException | JSONException e) {
                JOptionPane.showMessageDialog(null, "Unable to read file", "Loading Error",
                        JOptionPane.ERROR_MESSAGE);
                loadGame();
            }
        }
    }

    // EFFECTS: displays the leaderboard.
    private JPanel leaderBoard() {
        JPanel leaderboard = new JPanel();
        leaderboard.setLayout(new BorderLayout());

        ArrayList<Entry> lb = LeaderBoardReader.getLeaderBoard();
        DefaultListModel<String> lbModel = new DefaultListModel<>();

        for (int i = 0; i < lb.size(); i++) {
            String name = lb.get(i).getName();
            int turns = lb.get(i).getTurns();
            lbModel.add(i,(i + 1) + ". " + name + "\t\t" + turns);
        }

        leaderboard.add(new JList<>(lbModel));
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> switchMenu("Main Menu"));

        leaderboard.add(cancel, BorderLayout.PAGE_END);

        return leaderboard;
    }

    // EFFECTS: launches given ChessGame,
    //          when game ends shows Main Menu again.
    private void launchGame(ChessGame chessGame) {
        try {
            new ChessGameGUI(chessGame);
            setVisible(false);
        } catch (ExitException e) {
            switchMenu("Main Menu");
            setVisible(true);
        }
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            switchMenu("Main Menu");
            setVisible(true);
        });
    }

    // EFFECTS: switches to given menu.
    private void switchMenu(String name) {
        setTitle(name);
        CARD_LAYOUT.show(cards, name);
    }

    // FROM LAB 6 SNAKE WITH BUGS
    // MODIFIES: this
    // EFFECTS:  frame is centred on desktop
    private void centerOnScreen() {
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scr.width - getWidth()) / 2, (scr.height - getHeight()) / 2);
    }
}