import java.util.ArrayList;

public class Board
{  
  private static ArrayList<String> tileValues = new ArrayList<String>();
  private ArrayList<ArrayList<Tile>> gameboard = new ArrayList<>();
  JFXPanel fxPanel = new JFXPanel();
  /**  
   * Constructor for the game. Creates the 2D gameboard
   * by populating it with card values
   * 
   */
  public Board(int rows, int columns)
  {
    /* int tileCounter = 0;
    for (int i = 0; i < firstTiles.length; i++) {
      tileValues.add(firstTiles[i]);
    } // transfers all values in firstTiles in tileValues
   for (int i = 0; i < gameboard.length; i++) {
     for (int j = 0; j < gameboard[i].length; j++) {
     gameboard[i][j] = new Tile(tileValues.remove((int)(Math.random() * (firstTiles.length - tileCounter))));
     tileCounter++;
     }
   }

   System.out.println("Board created!");
   */
    int tileCounter = 0;

   for (int i = 0; i < columns; i++) {
    gameboard.add(new ArrayList<>());
   }
   
   for (int j = 1; j < (rows * columns) + 1; j++) {
      for (int k = 0; k < 2; k++) {
      tileValues.add("" + j);
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

 /** 
   * Returns a string representation of the board, getting the state of
   * each tile. If the tile is showing, displays its value, 
   * otherwise displays it as hidden.
   * 
   * Precondition: gameboard is populated with tiles
   * 
   * @return a string represetation of the board
   */
  public String toString() {
    String tempBoard = "";
    for (int i = 0; i < gameboard.get(0).size(); i++) {
      for (int j = 0; j < gameboard.size(); j++) {

        if (gameboard.get(j).get(i).isShowingValue() == true) {
          tempBoard += "[" + gameboard.get(j).get(i).getValue() + "]";
        }

        if (gameboard.get(j).get(i).matched() == false) {
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

  /** 
   * Determines if the board is full of tiles that have all been matched,
   * indicating the game is over.
   * 
   * Precondition: gameboard is populated with tiles
   * 
   * @return true if all tiles have been matched, false otherwse
   */
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

  /** 
   * Sets the tile to show its value (like a playing card face up)
   * 
   * Preconditions:
   *   gameboard is populated with tiles,
   *   row values must be in the range of 0 to gameboard.length,
   *   column values must be in the range of 0 to gameboard[0].length
   * 
   * @param row the row value of Tile
   * @param column the column value of Tile
   */
  public void showValue (int row, int column){
   gameboard.get(column).get(row).show();

  }  

  public void hideValue (int row, int column) {
    gameboard.get(column).get(row).hide();
  }

  /** 
   * Checks if the Tiles in the two locations match.
   * 
   * If Tiles match, show Tiles in matched state and return a "matched" message
   * If Tiles do not match, re-hide Tiles (turn face down).
   * 
   * Preconditions:
   *   gameboard is populated with Tiles,
   *   row values must be in the range of 0 to gameboard.length,
   *   column values must be in the range of 0 to gameboard[0].length
   * 
   * @param row1 the row value of Tile 1
   * @param col1 the column value of Tile 1
   * @param row2 the row value of Tile 2
   * @param col2 the column vlue of Tile 2
   * @return a message indicating whether or not a match occured
   */
  public String checkForMatch(int row1, int col1, int row2, int col2)
  {
    String msg = "The tiles do not match, womp womp.";

     if (gameboard.get(col1).get(row1).equals(gameboard.get(col2).get(row2))) {
      msg = "The tiles match!";
      gameboard.get(col1).get(row1).foundMatch();
      gameboard.get(col2).get(row1).foundMatch();
     }
    
     return msg;
  }

  /** 
   * Checks the provided values fall within the range of the gameboard's dimension
   * and that the tile has not been previously matched
   * 
   * @param rpw the row value of Tile
   * @param col the column value of Tile
   * @return true if row and col fall on the board and the row,col tile is unmatched, false otherwise
   */
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

  private static void playRightSound(String sound) {
    URL file = cl.getResource(sound);
    final Media media = new Media(file.toString());
    final MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }
}
