package ui.tools;

import model.players.Human;
import ui.ChessGame;

import java.util.Scanner;

// Tool that displays main menu,
// allows user to pick game mode and initialize game
public class MenuTool {

    // EFFECTS: displays main menu, lets user pick mode
    public static void mainMenu() {
        System.out.println("Main.java Menu");
        System.out.println("1. Two Player");
        // adding more modes as they are implemented...
        System.out.println("more modes coming soon!");
        System.out.print("Select mode (enter num): ");

        Scanner scanner = new Scanner(System.in);
        int mode = scanner.nextInt();

        if (mode == 1) {
            twoPlayerGame();
        } else {
            System.out.println("Oops! I don't know what that means");
        }
    }

    // EFFECTS: starts a new game with two human players
    private static void twoPlayerGame() {
        System.out.print("Enter Player 1's name: ");
        Scanner scanner1 = new Scanner(System.in);
        String player1Name = scanner1.nextLine();

        System.out.print("Enter Player 2's name: ");
        Scanner scanner2 = new Scanner(System.in);
        String player2Name = scanner2.nextLine();

        Human player1 = new Human(player1Name, 1);
        Human player2 = new Human(player2Name, -1);

        System.out.println("\nWhite gets first move!");

        ChessGame game = new ChessGame(player1, player2);
    }
}
