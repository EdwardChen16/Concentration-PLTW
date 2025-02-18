import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Board
{  
  private static ArrayList<String> tileValues = new ArrayList<String>();
  private ArrayList<ArrayList<Tile>> gameboard = new ArrayList<>();
  private String emojis = "👍🐶🐱🐭🐹🐰🐻🐼🐨🐯🦁🐮🐷🐸🐵🦊🐺🐗🐴🦄🐝🐛🦋🐌🐞🐜🦗🕷🦂🐢🐍🦎🦖🦕🐙🦑🦀🦞🦐🦪🐠🐟🐡🦈🐬🐳🐋🐊🦭🦦🦥🐆🐅🐃🐂🐄🦬🐘🦣🦏🦛🐪🐫🦙🦒🐖🐎🐐🦌🐕🦮🐩🐈🐓🦃🦤🦚🦜🦢🦩🕊🐇🦔🐁🐀🐿🦫🦎🦨🦘🦡🦇🐉🐲🌵🎄🌲🌳🌴🪵🌱🌿☘🍀🎍🎋🍃🍂🍁🍄🐚🌾💐🌷🌹🥀🌺🌻🌼🌸🌞🌝🌛🌜🌚🌍🌎🌏🪐💫⭐🌟✨⚡🔥🌪🌈☂🌊";
  public Board(int rows, int columns)
  {
    int tileCounter = 0;

   for (int i = 0; i < columns; i++) {
    gameboard.add(new ArrayList<>());
   }
   
   for (int j = 1; j < (rows * columns) + 1; j++) {
      for (int k = 0; k < 2; k++) {
      tileValues.add("  " + emojis.substring(j, j + 1) + "  ");
      }
   }

   for (int outer = 0; outer < columns; outer++) {
    for (int inner = 0; inner < rows; inner++) {
      gameboard.get(outer).add(new Tile(tileValues.remove((int)(Math.random() * ((rows * columns) - tileCounter)))));
      tileCounter++;
    }
   }
   System.out.println("Board Created!");
  }

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
  public String toString() {
    String tempBoard = "";
    for (int i = 0; i < gameboard.get(0).size(); i++) {
      for (int j = 0; j < gameboard.size(); j++) {

        if (gameboard.get(j).get(i).isShowingValue() == true) {
          tempBoard += "[" + gameboard.get(j).get(i).getValue() + "] ";
        }

        else if (gameboard.get(j).get(i).matched() == true) {
          tempBoard += "[" + gameboard.get(j).get(i).getMatched() + "] ";
        }

        else {
          tempBoard += "[" + gameboard.get(j).get(i).getHidden() + "] ";
        }
      }
      tempBoard += "\n";
    }
    return tempBoard;
  }

  public String hideTiles() {
    String tempBoard = "";
    for (int i = 0; i < gameboard.size(); i++) {
      for (int j = 0; j < gameboard.get(0).size(); j++) {
        if(gameboard.get(j).get(i).matched() == false) {
          tempBoard += "[" + gameboard.get(j).get(i).getHidden() + "] ";
        }
        else {
          tempBoard += "[" + gameboard.get(j).get(i).getMatched() + "] ";
        }
      }
      tempBoard += "\n";
    }
    return tempBoard;
  }

  public boolean allTilesMatch()
  {
    for (int i = 0; i < gameboard.size(); i++) {
      for (int j = 0; j < gameboard.get(0).size(); j++) {
        if (gameboard.get(j).get(i).matched() == false) {
          return false;
        }
      }
    }
    return true;
  }

  public void showValue (int row, int column){
   gameboard.get(column).get(row).show();
   playSound("cardFlip.wav");

  }  

  public void hideValue (int row, int column) {
    gameboard.get(column).get(row).hide();
  }


  public String checkForMatch(int row1, int col1, int row2, int col2)
  {
    String msg = "The tiles do not match.";

     if (gameboard.get(col1).get(row1).equals(gameboard.get(col2).get(row2))) {
      msg = "The tiles match!";
      gameboard.get(col1).get(row1).foundMatch();
      gameboard.get(col2).get(row2).foundMatch();
      playSound("tilesMatched.wav");
     }

     else {
      playSound("wrongMatch.wav");
     }
    
     return msg;
  }

  public boolean validateSelection(int row, int col){
    if (row > gameboard.get(0).size() - 1 || col > gameboard.size() - 1) {
      return false;
    }

    else if (gameboard.get(col).get(row).matched() == true) {
      return false;
    }

    else {
      return true;
    }
  }
}

