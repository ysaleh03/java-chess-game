package ui;

import exceptions.InvalidGameModeException;
import ui.tools.MenuTool;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Chess");
        try {
            MenuTool.mainMenu();
        } catch (InvalidGameModeException e) {
            main(args);
        }
    }
}
