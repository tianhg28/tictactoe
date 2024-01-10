import java.util.*;

public class TicTacToeMain {
   public static void main(String[] args){
      Scanner console = new Scanner(System.in);
      printIntro();
      int playerNum = getPlayers(console);
      String[] names = getNames(console, playerNum);
      boolean showLabel = getResponse(console, "Would you like to see row/column numberings? (y or n)");
      TicTacToeBoard tBoard = new TicTacToeBoard(playerNum, showLabel, names[0], names[1]);
      boolean playAgain = true;
      while (playAgain) {
         tBoard.newGame(); // redundancy: clearing board two times -> fix later
         playGame(tBoard, console);
         tBoard.showStats();
         playAgain = getResponse(console, "Do you want to play again? (y or n)");
      }
      console.close();
   }
   
   public static void playGame(TicTacToeBoard tBoard, Scanner console) {
      System.out.println("");
      if (!tBoard.isSinglePlayer()) {
         tBoard.displayBoard();
      }
      System.out.println("");
      while (!tBoard.isGameOver()) {
         System.out.println("It's " + tBoard.getPlayer() + "'s turn to go this time!");
         if (tBoard.getPlayer().equals("computer") && tBoard.isSinglePlayer()) {
            System.out.println("The computer is thinking...");
            try {
               Thread.sleep(1500);
            } catch(Exception e) {
               System.out.println(e);
            }
            tBoard.computerPlay();  
         } else {
            boolean spaceTaken = true;
            while (spaceTaken) {
               System.out.print("Please enter the row number to place your \"" + tBoard.getSymbol() + "\": ");
               int row = console.nextInt();
               while (row < 1 || row > 3) {
                  System.out.print("Please enter a number between 1 and 3: ");
                  row = console.nextInt();
               } // add a method to simplify -> for later
               System.out.print("Please enter the column number to place your \"" + tBoard.getSymbol() + "\": ");
               int column = console.nextInt();
               while (column < 1 || column > 3) {
                  System.out.print("Please enter a number between 1 and 3: ");
                  column = console.nextInt();
               }
               spaceTaken = tBoard.isTaken(row - 1, column - 1);
               if (spaceTaken) {
                  System.out.println("");
                  System.out.println("The location you chose is already taken.");
                  System.out.println("Please choose another row/column pair.");
                  System.out.println("");
               } else {
                  tBoard.placeToken(row - 1, column - 1);
               }
            }
         }
         System.out.println("");
         tBoard.displayBoard();
         System.out.println("");
      }
   }
   
   public static void printIntro() {
      System.out.println("Welcome to Tik Tak Toe!");
      System.out.println("Let's get started!");
      System.out.println("");
   }
   
   public static int getPlayers(Scanner console) {
      int players = 0;
      System.out.println("How many players? ");
      while (players != 2 && players != 1) {
         System.out.println("Please enter a number between 1 and 2.");
         System.out.println("How many players? ");
         players = console.nextInt();
      }
      return players;
   }
   
   public static boolean getResponse(Scanner console, String phrase) {
      System.out.println(phrase);
      String answer = console.next();
      if (answer.toLowerCase().charAt(0) == 'y') {
         return true;
      } else if (answer.toLowerCase().charAt(0) == 'n'){
         return false;
      } else {
         return getResponse(console, phrase);
      }
   }
   
   public static String[] getNames(Scanner console, int players) {      
      String[] names = new String[2];
      if (players == 1) {
         System.out.println("Your name? ");
         names[0] = console.next();
         names[1] = "computer";
      }
      if (players == 2) {
         System.out.println("Player 1 name? ");
         names[0] = console.next();
         System.out.println("Player 2 name? ");
         names[1] = console.next();
      }
      return names;
   }
}

// Add JPanel or DrawingPanel to help with visual after finishing
