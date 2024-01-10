import java.util.*;

public class TicTacToeBoard {
    private boolean singlePlayer; // If singleplayer, player2 is the computer
    private int difficulty;
    private boolean showLabel;
    private String[][] board = new String[3][3];
    private int[][] moveLog = new int[10][2];
    private Map<String, String> playerSymbol;
    private String player1; 
    private String player2;
    private int currentTurn;
    private String currentPlayer;
    private String winner;
    private int[] wins;
    private boolean tie;
    
    // Constructs board and set fields
    // player name inputs cannot be "tie" - fix later
    public TicTacToeBoard (int players, boolean showLabel, String player1, String player2) {
        playerSymbol = new HashMap<>();
        wins = new int[3];
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                board[row][column] = " ";
            }
        }
        if (players == 1) {
            singlePlayer = true;
            Scanner input = new Scanner(System.in);
            System.out.println("Please choose AI difficulty:");
            System.out.println("Easy -> Enter 1");
            System.out.println("Medium -> Enter 2");
            System.out.println("Hard -> Enter 3");
            difficulty = input.nextInt();
        } else {
            singlePlayer = false;
        }
        this.showLabel = showLabel;
        this.player1 = player1;
        this.player2 = player2;
        playerSymbol.put(player1, "X");
        playerSymbol.put(player2, "O");
        currentPlayer = player1;
    }
    
    public void newGame() {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                board[row][column] = " ";
            }
        }
        currentTurn = 1;
        changePlayer();
        tie = false;
    }
    
    // AI makes its move
    public void computerPlay() {
        if (difficulty == 1) {
            placeRandom();
        } else if (difficulty == 2) {
            smartPlace();
        } else {
        	int bestScore = Integer.MIN_VALUE;
        	int[] bestMove = new int[2];
        	for (int i = 0; i < 3; i++) {
        		for (int j = 0; j < 3; j++) {
        			if (!isTaken(i, j)) {
        				board[i][j] = "X";
        				int score = miniMax(false);
            		board[i][j] = " ";
                  System.out.print(score);
            		if (score > bestScore) {
            			bestScore = score;
            			bestMove[0] = i;
            			bestMove[1] = j;
            		}
    			   }
	         }	
        	}
        	placeToken(bestMove[0], bestMove[1]);
        }
    }
    
    // Utilizes a MiniMax AI. For each moves, minimax computes a number. Minimax
    // will choose the highest value to place the token.
    private int miniMax(boolean isMaximizing) {
    	String result = checkWinner();
    	if (result != null) {
    		return scores(result);
    	}
    	if (isMaximizing) {
    		int bestScore = Integer.MIN_VALUE;
    		for (int i = 0; i < 3; i++) {
        		for (int j = 0; j < 3; j++) {
        			if (!isTaken(i, j)) {
	        			board[i][j] = "X";
	        			int score = miniMax(false);
                  System.out.print(" X:" + score);
	        			board[i][j] = " ";
	        			bestScore = Math.max(score, bestScore);
        			}
        		}
    		}
    		return bestScore;
    	} else {
    		int bestScore = Integer.MAX_VALUE;
    		for (int i = 0; i < 3; i++) {
        		for (int j = 0; j < 3; j++) {
        			if (!isTaken(i, j)) {
	        			board[i][j] = "O";
	        			int score = miniMax(true);
                  System.out.print(" O:" + score);
	        			board[i][j] = " ";
	        			bestScore = Math.min(score, bestScore);
        			}
        		}
    		}
    		return bestScore;
    	}	
    }
    
    private int scores(String result) {
    	if (result.equals("X")) {
    		return 1;
    	} else if (result.equals("O")) {
    		return -1;
    	} else {
    		return 0;
    	}
    }
    
    private String checkWinner() {
        for (int row = 0; row < board.length; row++) {
            if (board[row][0].equals(board[row][1]) && board[row][1].equals(board[row][2]) && !board[row][0].equals(" ")) {
                return board[row][0];
            }
        }
        for (int column = 0; column < 3; column++) {
            if (board[0][column].equals(board[1][column]) && board[1][column].equals(board[2][column]) && !board[0][column].equals(" ")) {
                return board[0][column];
            }
        }
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals(" ")) {
            return board[0][0];
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals(" ")) {
            return board[0][2];
        }
        boolean tie = true;
        for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            	if (board[i][j].equals(" ")) {
            		tie = false;
            	}
            }
        }
        if (tie) {
            return "tie";
        }
        	return null;
    }

    //Randomly places token
    private void placeRandom() {
        Random rand = new Random();
        int row = rand.nextInt(3);
        int column = rand.nextInt(3);
        while (isTaken(row, column)) {
            row = rand.nextInt(3);
            column = rand.nextInt(3);
        }
        placeToken(row, column);
    }
    
    // Blocks or adds to tokens that are two in a row
    private void smartPlace() { 
    	// have method where passed a symbol // deal with row/column switch
    	// word on other cases
    	// computer symbol passed first
    	boolean alrPlaced = false;
    	String[][] newBoard = new String[3][3]; 
		for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                newBoard[row][column] = board[column][row];
            }
        }
    	alrPlaced = checkLine(playerSymbol.get("computer"), board, false);
    	if (!alrPlaced) {
    		alrPlaced = checkLine(playerSymbol.get(player1), board, false);
    	}
    	if (!alrPlaced) {
    		alrPlaced = checkLine(playerSymbol.get("computer"), newBoard, true);		
    	} 
    	if (!alrPlaced) {
			alrPlaced = checkLine(playerSymbol.get(player1), newBoard, true);
		}
    	if (!alrPlaced) {
			alrPlaced = checkDiagonal(playerSymbol.get("computer"));			
		}
    	if (!alrPlaced) {
			alrPlaced = checkDiagonal(playerSymbol.get(player1));			
		}
    	if(!alrPlaced) {
    		placeRandom();
    	}
    }
    
    private boolean checkDiagonal(String symbol) {
    	int count = 0;
    	boolean isSpace = false;
    	int coord = 0;
    	for (int i = 0; i < 3; i++) {
    		if (board[i][i] == symbol) {
    			count++;
    		} else if (board[i][i] == " "){
    			isSpace = true;
    			coord = i;
    		}
    	}
    	if (count == 2 && isSpace) {
    		placeToken(coord, coord);
    		return true;
    	}
    	count = 0;
    	isSpace = false;
    	int[] spacePos = new int[2];
    	for (int i = 0; i < 3; i++) {
    		int x;
        	int y;
    		if (i == 0) {
    			x = 0;
    			y = 2;
    		} else if (i == 2) {
    			x = 1;
    			y = 1;
    		} else {
    			x = 2;
    			y = 0;
    		}
    		if (board[x][y] == symbol) {
    			count++;
    		} else if (board[x][y] == " "){
    			isSpace = true;
    			spacePos[0] = x;
    			spacePos[1] = y;
    		}
    	}
       	if (count == 2 && isSpace) {
    		placeToken(spacePos[0], spacePos[1]);
    		return true;
    	}
    	return false;
    }
    
    private boolean checkLine (String symbol, String[][] board, boolean vertical) { // board, columns
    	for (int row = 0; row < 3; row++) {
        	int count = 0;
        	boolean isSpace = false;
        	int[] spacePos = new int[2];
        	for (int column = 0; column < 3; column++) {
        		if (board[row][column] == symbol) {
        			count++;
        		} else if (board[row][column] == " "){
        			isSpace = true;
        			if (!vertical) {
	        			spacePos[0] = row;
	        			spacePos[1] = column;	
        			} else {
        				spacePos[0] = column;
	        			spacePos[1] = row;
        			}
        		}
        	}
        	if (count == 2 && isSpace) {
        		placeToken(spacePos[0],spacePos[1]);
        		return true;
        	}
    	}
    	return false;
    }
    
    public void placeToken(int row, int column) {
        board[row][column] = getSymbol();
        moveLog[currentTurn][0] = row;
        moveLog[currentTurn][1] = column;
        currentTurn++;
        changePlayer();
        // change currentTurn -- also maybe change the variable to string or something else
    }
    
    public void displayBoard () {
        if (showLabel) {
            System.out.println("  1   2   3 ");
        }
        for (int row = 0; row < board.length * 2 - 1; row++) {
            if (row % 2 == 1) {
                System.out.println(" " + repeat("-", board[row / 2].length * 4 - 1));
            } else {
                if (showLabel) {
                    System.out.print(row / 2 + 1);
                }
                System.out.print(" " + board[row / 2][0] + " ");
                for (int column = 1; column < board[row / 2].length; column++) {
                    System.out.print("| " + board[row / 2][column] + " ");
                }
                System.out.println("");
            }
        }
    }
    
    private void changePlayer() {
        if (currentPlayer.equals(player1)) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
    
    public boolean isTaken(int row, int column) {
        if (!board[row][column].equals(" ")) {
            return true;
        }
        return false;
    }
    
    // Add more versatile method later + simplify and reduce redundancies
    public boolean isGameOver() {
        boolean gameOver = false;
        for (int row = 0; row < board.length; row++) {
            if (board[row][0].equals(board[row][1]) && board[row][1].equals(board[row][2]) && !board[row][0].equals(" ")) {
                gameOver = true;
            }
        }
        for (int column = 0; column < 3; column++) {
            if (board[0][column].equals(board[1][column]) && board[1][column].equals(board[2][column]) && !board[0][column].equals(" ")) {
                gameOver = true;
            }
        }
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals(" ")) {
            gameOver = true;
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals(" ")) {
            gameOver = true;
        }
        if (currentTurn == 10 & gameOver == false) {
            tie = true;
            gameOver = true;
        }
        if (gameOver) {
            changePlayer();
            winner = currentPlayer;
            updateStats();
        }
        return gameOver;
    }
    
    private void updateStats() {
        if (tie) {
            wins[2]++;
        } else if (winner.equals(player1)) {
            wins[0]++;
        } else {
            wins[1]++;
        }
    }
    
    public void showStats() {
        if (!tie) {
            System.out.println(winner + " has won the game! Congratulations!");
        } else {
            System.out.println("It's a tie! No one won.");
        }
        System.out.println("");
        System.out.println("Total games: " + (wins[0] + wins[1] + wins[2]));
        System.out.println(player1 + "'s wins: " + wins[0]);
        System.out.println(player2 + "'s wins: " + wins[1]);
        System.out.println("Ties: " + wins[2]);
        System.out.println("");
    }
    
    public boolean isSinglePlayer() {
        return singlePlayer;
    }
    
    public String getSymbol() {
        return playerSymbol.get(currentPlayer);
    }
    
    public String getPlayer() {
        return currentPlayer;
    }
    
    // Repeats symbols
    private static String repeat(String text, int times) {
        String result = "";
        for (int i = 0; i < times; i++) {
            result = result + text;
        }
        return result;
    }
}

// AI method
/*
- easy: randomly place a token
- medium: first block if opponent has 2 consecutive or place if ai has 2 consec, then random move
- hard: big brin if statements


// Add JPanel or DrawingPanel to help with visual after finishing

   private void bigBrain() {
    	if (currentTurn == 1) {// Places top left corner if AI goes first
    		placeToken(1, 1); 
    	} else if(currentTurn == 2) { // Places middle if AI goes second, if taken goes corner
    		if (!isTaken(1, 1)) {
    			placeToken(1, 1); 
    		} else {
    			placeToken(0, 0);
    		}
    	} else if (currentTurn == 3) { 
    		if (moveLog[2][0] == 1 && moveLog[2][1] == 1) { // If human goes center, AI opposite corner from 1st move
    			placeToken(2, 2);
    		} else if (moveLog[2][0] == 2 && moveLog[2][1] == 2) { // If human goes opposite corner
    			placeToken(0, 2);
    		} else {
    			if (moveLog[2][0] - moveLog[2][1] >= 1) {
    				placeToken(0, 2); 
    			} else {
    				placeToken(2, 0);
    			}
    		}
    	} else {
    		smartPlace();
    	}
    }
*/

