/** 
     * TicTacToeGame --- program that allows a user to play tic tac toe with the computer and saves results to a text file
     * @author Silvia Arjona Garcia
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class TicTacToeGame extends JFrame implements ActionListener {
   private JButton[][] buttons = new JButton[3][3];
   private char[][] board = new char[3][3];
   private boolean playerTurn = true;
   private static final char PLAYER = 'X';
   private static final char COMPUTER = 'O';
   private static final String FILE_NAME = "Tfile.txt";
   
   public TicTacToeGame() {
      setTitle("Tic-Tac-Toe Game");
      setSize(400,400);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(new GridLayout(3, 3)); 
      
      //show instructions to player before game begins
      showInstructions();
      
      //initialize game board and buttons
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            buttons[i][j] = new JButton("");
            buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
            buttons[i][j].setFocusPainted(false);                        
            buttons[i][j].addActionListener(this);
            add(buttons[i][j]);
            board[i][j] = '-';
         }
      }
   }
   
   //method to display game instructions
   private void showInstructions() {
      String instructions = "Welcome to Tic-Tac-Toe!\n\n"
                          + "How to play:\n"
                          + "- You are 'X' and the computer is 'O'.\n"
                          + "- Take turns clicking the empty spots on the grid to place your 'X'.\n"
                          + "- The first to get three in a row (vertically, horizontally, or diagonally) wins!\n"
                          + "- If the grid is full and no one has three in a row, it's a tie.\n\n"
                          + "Good luck and have fun!";
      JOptionPane.showMessageDialog(this, instructions, "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
   }
   
   public void actionPerformed(ActionEvent e) {
      JButton pressedButton = (JButton) e.getSource();
      
      //player move
      if (playerTurn) {
         for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
               if (pressedButton == buttons[i][j] && buttons[i][j].getText().equals("")) {
                  buttons[i][j].setText(String.valueOf(PLAYER));
                  board[i][j] = PLAYER;
                  playerTurn = false;
                  checkGameStatus();
                  computerMove(); //computer makes its move after player
               }
            }
         }
      }
   }
   
   //computer makes a random move
   private void computerMove() {
      if (!playerTurn) {
         Random rand = new Random();
         boolean moveMade = false;
         while (!moveMade) {
            int i = rand.nextInt(3);
            int j = rand.nextInt(3);
            
            if (board[i][j] == '-') {
                  buttons[i][j].setText(String.valueOf(COMPUTER));
                  board[i][j] = COMPUTER;
                  moveMade = true;
                  playerTurn = true;
                  checkGameStatus();
               }
            }
         }
      }
   
   
   //check game status after each move
   private void checkGameStatus() {
      String winner = checkWinner();
      if (winner != null) {
         JOptionPane.showMessageDialog(this, winner + " won the game!");
         saveGameResults(winner);
         resetBoard();
      } else if (isBoardFull()) {
         JOptionPane.showMessageDialog(this, "It's a tie!");
         saveGameResults("Tie");
         resetBoard();
      }
   }
   
   //check for a winner
   private String checkWinner() {
      //check rows, columns, and diagonals
      for (int i = 0; i < 3; i++) {
         if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') {
            return board[i][0] == PLAYER ? "Player" : "Computer";
         }
         if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-') {
            return board[0][i] == PLAYER ? "Player" : "Computer";
         }
      }
      if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
         return board[0][0] == PLAYER ? "Player" : "Computer";
      }
      if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
         return board[0][2] == PLAYER ? "Player" : "Computer";
      }
      return null;
   }
   
   //check if the board is full and no moves are possible
   private boolean isBoardFull() {
      for (int i = 0; i <3; i++) {
         for (int j = 0; j < 3; j++) {
            if (board[i][j] == '-') {
               return false;
            }
         }
      }
      return true;
   }
   
   //save game results to a file
   private void saveGameResults(String result) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
         writer.write("Game Result: " + result + "\n");
         writer.write("Board state:\n");
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               writer.write(" " + board[i][j] + " ");
            }
            writer.write("\n");
         }
         writer.write("-------------------------\n");
         JOptionPane.showMessageDialog(this, "Game results saved to " + FILE_NAME);
      } catch (IOException e) {
      e.printStackTrace();
      }
   }
   
   //reset the board after game is over
   private void resetBoard() {
      for (int i = 0; i < 3; i++) {
          for (int j = 0; j < 3; j++) {
            buttons[i][j].setText("");
            board[i][j] = '-';
          }
      }
      playerTurn = true;
   }
   
   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         TicTacToeGame game = new TicTacToeGame();
         game.setVisible(true);
      });
   }
   
}