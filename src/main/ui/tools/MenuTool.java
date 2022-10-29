package ui.tools;

import model.ChessGame;
import model.Player;
import model.Entry;
import persistence.LeaderBoard;
import persistence.SaveFileReader;
import persistence.SaveFileWriter;
import ui.ChessGameApp;

import java.io.IOException;
import java.util.*;

// Tool that displays main menu,
// allows user to pick game mode and initialize game
public class MenuTool {

    // EFFECTS: displays main menu
    public static void mainMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1. Two Player");
        System.out.println("2. Load Game");
        System.out.println("3. Leaderboard");
        selectMode();
    }

    // EFFECTS: lets user pick mode
    private static void selectMode() {
        int mode;

        System.out.print("Choose mode: ");

        try {
            Scanner scanner = new Scanner(System.in);
            mode = scanner.nextInt();
        } catch (InputMismatchException | IllegalArgumentException e) {
            mode = -1;
        }

        switch (mode) {
            case 1:
                twoPlayerGame();
                break;
            case 2:
                savedGame();
                break;
            case 3:
                printLeaderBoard();
                break;
            default:
                System.out.println("Oops! I don't know what that means\n");
                selectMode();
        }
    }

    // EFFECTS: starts a new game with two human players
    private static void twoPlayerGame() {
        Scanner scanner;
        System.out.println("\nTwo Player Game\n");
        System.out.print("Enter Player 1's name: ");
        scanner = new Scanner(System.in);
        String player1Name = scanner.nextLine();

        scanner = new Scanner(System.in);
        System.out.print("Enter Player 2's name: ");
        String player2Name = scanner.nextLine();

        Player player1 = new Player(player1Name, 1);
        Player player2 = new Player(player2Name, -1);

        System.out.println("\nWhite gets first move!");

        ChessGame chessGame = new ChessGame(player1, player2);

        try {
            new ChessGameApp(chessGame);
        } catch (RuntimeException e) {
            saveMenu(chessGame);
        }
    }

    private static void savedGame() {
        ChessGame chessGame = null;
        
        System.out.print("Enter save file name: ./data/savefiles/");
        Scanner scanner = new Scanner(System.in);
        String saveFile = scanner.nextLine();
        SaveFileReader fileReader = new SaveFileReader(saveFile);

        try {
            chessGame = fileReader.read();
            new ChessGameApp(chessGame);
        } catch (IOException e) {
            System.out.println("Could not read file");
            mainMenu();
        } catch (RuntimeException e) {
            saveMenu(chessGame);
        }
    }

//    private static ChessGame loadSavedGame() {
//        System.out.print("Enter save file name: ./data/savefiles/");
//        Scanner scanner = new Scanner(System.in);
//        String saveFile = scanner.nextLine();
//        SaveFileReader fileReader = new SaveFileReader(saveFile);
//        ChessGame chessGame = null;
//
//        try {
//            chessGame = fileReader.read();
//        } catch (IOException e) {
//            System.out.println("Could not read file");
//            mainMenu();
//        }
//        return chessGame;
//    }

    private static void saveMenu(ChessGame chessGame) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Y to save game: ");
        String choice = scanner.nextLine();
        if (Objects.equals(choice.toUpperCase(), "Y")) {
            System.out.print("Enter save file name: ");
            String fileName = scanner.nextLine();
            try {
                new SaveFileWriter(chessGame, fileName);
            } catch (IOException e) {
                System.out.println("Unable to save");
            }
        } else {
            System.out.println("Quitting..");
        }
    }

    private static void printLeaderBoard() {
        ArrayList<Entry> leaderboard = LeaderBoard.getLeaderBoard();
        System.out.println("\nLeaderboard\n");
        for (int i = 0; i < leaderboard.size(); i++) {
            String name = leaderboard.get(i).getName();
            int turns = leaderboard.get(i).getTurns();
            System.out.println((i + 1) + ". " + name + "\t\t" + turns);
        }
    }
}

