# Chess Game

This project, as the name suggests, is a chess game.

Other than being able to play chess, some additional functionalities include:
- saving the game
- loading a previous game
- a leaderboard

## User Stories:
1. As a user, I want to be able to play vs another user (done)
2. As a user, I want to set up a board with pieces on it (done)
3. As a user, I want to select pieces and move them (done)
    * TODO: special moves
4. As a user, I want to capture pieces on the board (done)
5. As a user, I want to add captured pieces to the player's captures list (done)
6. As a user, I want to see the pieces I captured (done)
7. As a user, I want to know how many turns my game ended in (done)
8. As a user, I want to save my game for later (done)
9. As a user, I want to load a game from earlier (done)
10. As a user, I want to add my name to the leaderboard (done)

## Instructions for Testing
1. Select "Load Game"
2. Select "FooBarTurn0.json"

3. As player1, select the white pawn at c2 by clicking on it
4. As player1, move the selected piece two squares forward by clicking on c4
5. As player2, select the black pawn at d7 by clicking on it
6. As player2, move the selected piece two squares forward by clicking on d5
7. As player1, move the white pawn now at c4 to d5, taking the black pawn there
8. Similarly, as player2, take that pawn with the black queen
   * The taken pawns should appear at the bottom of the frame as they are taken

9. Keep playing until you win, or close the window
10. A small dialogue box should pop up, prompting you to save. Click "Yes".
11. Choose a name for the save, then click "Save"
12. Click Quit

## Log example
>Sun Nov 27 21:27:53 PST 2022 </br>
Saved game loaded on turn 0; players: Foo and Bar </br>
Sun Nov 27 21:28:06 PST 2022 </br>
Foo moved c2 to c4 </br>
Sun Nov 27 21:28:13 PST 2022 </br>
Bar moved d7 to d5 </br>
Sun Nov 27 21:28:18 PST 2022 </br>
Foo moved c4 to d5; captured piece (added to their capturedPieces) </br>
Sun Nov 27 21:28:27 PST 2022 </br>
Bar moved d8 to d5; captured piece (added to their capturedPieces) </br> 

## TODO
- Get rid of the Writable interface
   * implemented toJSON separately anyway, Writable is never passed as a parameter.
- Move exceptions into one package in the root folder; split them inside.
- Fix inconsistency with the naming of leaderboard-related things (Leaderboard/LeaderBoard).
- Move MoveTool to model.
