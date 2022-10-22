package ui.tools;

import exceptions.EscapeException;
import exceptions.InvalidGameModeException;
import model.players.Human;
import persistence.FileReader;
import persistence.FileWriter;
import ui.ChessGame;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

// Tool that displays main menu,
// allows user to pick game mode and initialize game
public class MenuTool {

    // EFFECTS: displays main menu, lets user pick mode
    public static void mainMenu() {
        System.out.println("Main Menu");
        System.out.println("1. Two Player\n2. Load Game");
        // adding more modes as they are implemented...
        System.out.println("more modes coming soon!");
        System.out.print("Select mode (enter num): ");

        int mode;

        try {
            Scanner scanner = new Scanner(System.in);
            mode = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Oops! I don't know what that means");
            throw new InvalidGameModeException();
        }

        switch (mode) {
            case 1:
                twoPlayerGame();
                break;
            case 2:
                savedGame();
                break;
            default:
                System.out.println("Oops! I don't know what that means");
                throw new InvalidGameModeException();
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

        Human player1 = new Human(player1Name, 1);
        Human player2 = new Human(player2Name, -1);

        System.out.println("\nWhite gets first move!");

        ChessGame chessGame = new ChessGame(player1, player2);

        try {
            chessGame.playGame();
        } catch (EscapeException e) {
            saveMenu(chessGame);
        }
    }

    private static void savedGame() {
        ChessGame chessGame = loadSaved();
        try {
            assert chessGame != null;
            chessGame.playGame();
        } catch (EscapeException e) {
            saveMenu(chessGame);
        }
    }

    private static ChessGame loadSaved() {
        System.out.println("\nLoad Saved Game\n");
        System.out.print("Enter save file name: ./data/savefiles/");
        Scanner scanner = new Scanner(System.in);
        String saveFile = scanner.nextLine();
        FileReader fileReader = new FileReader(saveFile);
        ChessGame chessGame = null;

        try {
            chessGame = fileReader.read();
        } catch (IOException e) {
            System.out.println("Could not read file");
            loadSaved();
        }
        return chessGame;
    }

    private static void saveMenu(ChessGame chessGame) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Would you like to save this game? (y/n) ");
        String choice = scanner.nextLine();
        if (Objects.equals(choice, "y")) {
            System.out.print("Enter save file name: ");
            String fileName = scanner.nextLine();
            new FileWriter(chessGame, fileName);
        }
    }
}
