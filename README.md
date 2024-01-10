# Tic Tac Toe

This project is a Java-based Tic Tac Toe game that can be played in a console. It supports both single-player (against a computer AI) and two-player modes. The AI has three levels of difficulty: easy, medium, and hard.

## Features

- **Single-Player Mode**: Play against the computer with three levels of difficulty:
  - **Easy**: The computer places its token randomly.
  - **Medium**: The computer either blocks the player's winning move or places its token to create two in a row, else places randomly.
  - **Hard**: The computer uses the MiniMax algorithm to calculate the best move.
- **Two-Player Mode**: Two players can play against each other.
- **Customizable Settings**: Choose whether to display row/column numbers on the board.
- **Game Statistics**: Keeps track of the number of wins for each player and the number of ties.

## How to Play

1. **Start the Game**: Run `TicTacToeMain` to start the game.
2. **Choose Mode**: Select between single-player (against AI) and two-player mode.
3. **Gameplay**:
   - In single-player mode, enter your move and the computer will automatically play its turn.
   - In two-player mode, players take turns to enter their moves.
   - Enter row and column numbers to place your token ('X' or 'O') on the board.
4. **End of Game**: The game ends when one player wins or there's a tie. Statistics are displayed at the end of each game.
5. **Play Again**: After each game, choose whether to play another round.

## Installation

1. Clone or download this repository to your local machine.
2. Compile the Java files (`TicTacToeBoard.java` and `TicTacToeMain.java`).
3. Run `TicTacToeMain` to start playing.

