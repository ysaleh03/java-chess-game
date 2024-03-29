package ui;

import exceptions.ExitException;
import model.ChessGame;
import model.Entry;
import model.EventLog;
import model.Leaderboard;
import model.players.Player;
import org.json.JSONException;
import persistence.SaveFileReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

// GUI that displays menus,
// allows user to pick game mode and initialize game.
public final class MenuGUI extends JFrame {
    private static final CardLayout CARD_LAYOUT = new CardLayout();
    private final JPanel contentPane;

    // EFFECTS: constructs a new MenuGUI JFrame allowing access to different menus.
    public MenuGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog();
            }
        });

        contentPane = contentPane();
        add(contentPane);
        setTitle("Main Menu");

        pack();
        centerOnScreen();
        setVisible(true);
    }

    // EFFECTS: creates a JPanel with Card Layout,
    //          with each "card" being a submenu.
    private JPanel contentPane() {
        JPanel cards = new JPanel();
        cards.setLayout(CARD_LAYOUT);


        MainMenu mainMenu = new MainMenu();
        NewGameMenu newGameMenu = new NewGameMenu();
        LeaderboardMenu leaderboardMenu = new LeaderboardMenu();

        cards.add(mainMenu, "Main Menu");
        cards.add(newGameMenu, "New Game");
        cards.add(leaderboardMenu, "Leaderboard");
        return cards;
    }

    private void update() {
        setContentPane(contentPane());
        revalidate();
        repaint();
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
                JOptionPane.showMessageDialog(null, "Unable to read file",
                        "Loading Error", JOptionPane.ERROR_MESSAGE);
                loadGame();
            }
        }
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
        CARD_LAYOUT.show(contentPane, name);
    }

    // EFFECTS: prints log
    private void printLog() {
        for (model.Event e : EventLog.getInstance()) {
            System.out.println(e.toString());
        }
    }

    // FROM LAB 6 SNAKE WITH BUGS
    // MODIFIES: this
    // EFFECTS:  frame is centred on desktop
    private void centerOnScreen() {
        Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scr.width - getWidth()) / 2, (scr.height - getHeight()) / 2);
    }

    // A main menu panel
    private class MainMenu extends JPanel {

        // EFFECTS: constructs a main menu, with buttons that switch to the other menus.
        private MainMenu() {
            this.setLayout(new GridLayout(0, 1));

            JButton newGame = new JButton("New Game");
            newGame.addActionListener(e -> switchMenu("New Game"));

            JButton loadGame = new JButton("Load Game");
            loadGame.addActionListener(e -> loadGame());

            JButton lbButton = new JButton("Leaderboard");
            lbButton.addActionListener(e -> switchMenu("Leaderboard"));

            JButton quit = new JButton();
            quit.setText("Quit");
            quit.addActionListener(e -> {
                dispose();
                printLog();
            });

            this.add(newGame);
            this.add(loadGame);
            this.add(lbButton);
            this.add(quit);
        }
    }

    // A new game panel
    private class NewGameMenu extends JPanel {

        // EFFECTS: constructs a game setup menu, prompting user for the names of
        //          the two players, then launches new game with given players.
        private NewGameMenu() {
            this.setLayout(new GridLayout(0, 2));

            this.add(new JLabel("Player 1 name: "));
            JTextField player1Name = new JTextField();
            this.add(player1Name);

            this.add(new JLabel("Player 2 name: "));
            JTextField player2Name = new JTextField();
            this.add(player2Name);

            JButton start = new JButton("Start");
            start.addActionListener(e -> {
                Player player1 = new Player(player1Name.getText());
                Player player2 = new Player(player2Name.getText());
                launchGame(new ChessGame(player1, player2));
            });

            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(e -> switchMenu("Main Menu"));

            this.add(start);
            this.add(cancel);
        }
    }

    // A leaderboard panel, containing a JList of entries
    private class LeaderboardMenu extends JPanel {
        private final Leaderboard theLeaderboard = Leaderboard.getInstance();

        private LeaderboardMenu() {
            this.setLayout(new BorderLayout());
            setup();
        }

        // EFFECTS: constructs a new leaderboard.
        private void setup() {
            DefaultListModel<String> listModel = new DefaultListModel<>();

            for (Entry e : theLeaderboard) {
                listModel.addElement(e.getName() + "\t\t\t" + e.getTurns() + " turns");
            }

            JList<String> lbJList = new JList<>(listModel);

            DefaultListCellRenderer renderer = (DefaultListCellRenderer) lbJList.getCellRenderer();
            renderer.setHorizontalAlignment(SwingConstants.CENTER);

            this.add(lbJList);
            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(e -> switchMenu("Main Menu"));
            this.add(cancel, BorderLayout.PAGE_END);
        }
    }
}