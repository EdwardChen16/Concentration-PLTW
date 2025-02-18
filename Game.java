import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Game
{
  private Scanner in = new Scanner(System.in);

  private Board board;
  private int row1, col1;
  private int row2, col2;
  private int numRows = 4;
  private int numCol = 4;
  private int numPlays = 0;
  private int fastestTime = Integer.MAX_VALUE;

  public static void playSound(String filePath) {
    try {
        File soundFile = new File(filePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        Thread.sleep(clip.getMicrosecondLength() / 1000);
    } 
    catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
        e.printStackTrace();
    }
}
  public void play()
  {
    System.out.println(" CCCC  OOO  N   N  CCCC  EEEEE  N   N  TTTTT  RRRR    A   TTTTT III   OOO    N   N");
    System.out.println("C     O   O NN  N C      E      NN  N    T    R   R  A A    T    I   O    O  NN  N");
    System.out.println("C     O   O N N N C      EEEE   N N N    T    RRRR  AAAAA   T    I   O    O  N N N");
    System.out.println("C     O   O N  NN C      E      N  NN    T    R  R  A   A   T    I   O    O  N  NN");
    System.out.println(" CCCC  OOO  N   N  CCCC  EEEEE  N   N    T    R   R A   A   T   III   OOO    N   N");
    System.out.println();
    System.out.println("Welcome to concentration! Type anything to play with default settings. If you would like to customize the dimensions of the board, type 'settings.'");
    String userResp = in.nextLine().toLowerCase();
    if (userResp.equals("settings")) {
      boolean allSettingsCustomized = false;
      while (!allSettingsCustomized) {
        System.out.println("You can currently only change the dimensions of the array. Type 'yes' to do so, or type 'no' to head back to the main menu.");
        userResp = in.nextLine().toLowerCase();
        boolean respCheck1 = false;
        if (userResp.equals("yes")) {
          System.out.println("Please type the number of rows.");
        while (!respCheck1) {
          userResp = in.nextLine();
          if (userResp.matches("\\d+")) {
            if (Integer.parseInt(userResp) <= 20) {
            respCheck1 = true;
            numRows = Integer.parseInt(userResp);
            }
            else if (Integer.parseInt(userResp) > 20) {
              System.out.println("Sorry, the maximum number of rows you can play with is 12. Please enter another value.");
            }
          }
          else {
            getRandomResponse();
          }
        }
        boolean respCheck2 = false;
        System.out.println("Now enter the number of columns.");
        while (!respCheck2) {
        userResp = in.nextLine();
          if (userResp.matches("\\d+")) {
            if (Integer.parseInt(userResp) <= 12) {
            numCol = Integer.parseInt(userResp);
            if (numCol * numRows % 2 > 0) {
              System.out.println("The dimensions you specified has an odd number of tiles. You must enter the dimensions of a board with an even number of tiles. Press enter to return back to the main menu.");
              userResp = in.nextLine();
              play();
            }
            else {
              respCheck2 = true;
              allSettingsCustomized = true;
            } 
          }
        }
          else {
          getRandomResponse();
          }
        }
      }
      else if (userResp.equals("no")){
        play();
      }
    }
  }

    board = new Board(numCol, numRows);
    long startTime = System.currentTimeMillis();
    while (!board.allTilesMatch())
    {
      row1 = -1;
      col1 = -1;
      boolean validTile = false;
      while (!validTile)
      {
        displayBoard();
        System.out.print("First choice (row col): ");
        validTile = getTile(true); 
      }

      board.showValue(row1, col1);

      row2 = -1;
      col2 = -1;
      validTile = false;
      while (!validTile)
      {
        displayBoard();
        System.out.print("Second choice (row col): ");
        validTile = getTile(false);
         
        if ((row1 == row2) && (col1 == col2))
        {
          System.out.println("You mush choose a different second tile");
          wait(2);
          validTile = false;
        }
      }

      board.showValue(row2, col2);
      displayBoard();

      String matched = board.checkForMatch(row1, col1, row2, col2);
      System.out.println(matched);

      wait(2); 
      board.hideValue(row1, col1);
      board.hideValue(row2, col2);
    }

    displayBoard();

    long endTime = System.currentTimeMillis();
    long elapsedTime = endTime - startTime;
    int totalTime = (int) (elapsedTime / 1000);
    ArrayList<Integer> highScores = new ArrayList<>();
    highScores.add(totalTime);
    numPlays++;

    
    for (int i = 0; i < highScores.size(); i++) {
    if (fastestTime > highScores.get(i)) {
        fastestTime = highScores.get(i);
    }
}
    playSound("victory.wav");
    System.out.println("You took " + totalTime + " seconds to beat a " + numRows + " by " + numCol + " board." + " You have played a total of " + numPlays + " times.");
    if (totalTime == fastestTime) {
        System.out.println("Congrats, that was your fastest time!");
    }
    System.out.println("Want to play again? Type 'yes' if you would like to, or type 'quit' to exit the game.");
    boolean respCheck3 = false;
    while (!respCheck3) {
        userResp = in.nextLine().toLowerCase();
        if (userResp.equals("yes")) {
            respCheck3 = true;
            play();
        }
        else if (userResp.equals("quit")) {
            respCheck3 = true;
            System.exit(69);
        }

        else {
        getRandomResponse();
        }
    }
    System.out.println("Thanks for playing!");
  }


  private boolean getTile(boolean firstChoice)
  {
    int num1 = 0;
    int num2 = 0;
        
    if (in.hasNextInt())
      num1 = in.nextInt();
    else
      quitGame();
  
    if (in.hasNextInt())
      num2 = in.nextInt();
     else
      quitGame();

    in.reset(); // ignore any extra input

    if (!board.validateSelection(num1, num2))
    {
      System.out.print("Invalid input, please try again. ");
      wait(2);
      return false;
    }
    if (firstChoice)   
    {
      row1 = num1;
      col1 = num2;
    }
    else 
    {
      row2 = num1;
      col2 = num2;
    }
    return true;
  }

  public void displayBoard()
  {

    for (int x = 0; x < 50; x++) {
      System.out.println();
    }
      System.out.println(board);
  }

  private void wait(int n)
  {
    try
    {
      Thread.sleep(n * 1000);
    } catch (InterruptedException e) { /* do nothing */ }
  }


  private void quitGame() 
  {
    System.out.println("Quit game!");
    System.exit(0);
  }
  public void getRandomResponse() {
    String[] resp = {"I'm sorry, could you try again?", "I didn't get that. Please type your response again.", "Sorry, could you repeat that?"};

    System.out.println(resp[(int) (Math.random() * 3)]);
  }
}

