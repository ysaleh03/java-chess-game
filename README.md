# Chess
## Chess Engine

This application is a chess game, where users have the
option to play against one another, or the computer. It
can be used by anyone who wants to play a game of chess,
and could also be used by chess strategists to come up
with or test new strategies.

Some additional functionalities include:
- saving the current board
- loading a previously saved board
- an optional timed mode
- a leaderboard

This project is interesting because it needs to deal with
a large variety of user interactions, check them against
the movesets of different pieces, and also *look ahead* and
*backtrack* to pick its own next move.

## User Stories:
1. As a user, I want to be able to play vs another user (done)
2. As a user, I want to be able to play vs the computer
3. As a user, I want to set up a board with pieces on it (done)
4. As a user, I want to select pieces and move them (done)
    * No special moves, unfortunately (sorry)
5. As a user, I want to capture pieces on the board (done)
6. As a user, I want to add captured pieces to the player's captures list (done)
7. As a user, I want to see the pieces I captured (done)
8. As a user, I want to know how many turns my game ended in (done)
9. As a user, I want to add my name to the leaderboard

## TODO:
1. Implement Computer class
2. Implement leaderboard