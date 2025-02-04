import java.util.ArrayList;

public class Board
{  
  private static ArrayList<String> tileValues = new ArrayList<String>();
  private static String[] firstTiles = {"lion", "lion",
                                        "penguin", "penguin",
                                        "dolphin", "dolphin",
                                        "fox", "fox",
                                        "monkey", "monkey",
                                        "turtle", "turtle"};

  private Tile[][] gameboard = new Tile[3][4];

  /**  
   * Constructor for the game. Creates the 2D gameboard
   * by populating it with card values
   * 
   */
  public Board()
  {
    int tileCounter = 0;
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
    for (int i = 0; i < gameboard.length; i++) {
      for (int j = 0; j < gameboard[i].length; j++) {
        if(gameboard[i][j].isShowingValue() == true) {
          tempBoard += "[" + gameboard[i][j].getValue() + "]";
        }

        else if(gameboard[i][j].matched() == false) {
          tempBoard += "[" + gameboard[i][j].getHidden() + "] ";
        }

        else {
          tempBoard += "[" + gameboard[i][j].getMatched() + "] ";
        }
      }
      tempBoard += "\n";
    }
    return tempBoard;
  }

  public String hideTiles() {
    String tempBoard = "";
    for (int i = 0; i < gameboard.length; i++) {
      for (int j = 0; j < gameboard[i].length; j++) {
        if(gameboard[i][j].matched() == false) {
          tempBoard += "[" + gameboard[i][j].getHidden() + "] ";
        }
        else {
          tempBoard += "[" + gameboard[i][j].getMatched() + "] ";
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
    for (int i = 0; i < gameboard.length; i++) {
      for (int j = 0; j < gameboard[i].length; j++) {
        if (gameboard[i][j].matched() == false) {
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
   gameboard[row][column].show();

  }  

  public void hideValue (int row, int column) {
    gameboard[row][column].hide();
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

     if (gameboard[row1][col1].equals(gameboard[row2][col2])) {
      msg = "The tiles match!";
      gameboard[row1][col1].foundMatch();
      gameboard[row2][col2].foundMatch();
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
    if (row > gameboard.length - 1 || col > gameboard[0].length - 1) {
      return false;
    }

    else if (gameboard[row][col].matched() == true) {
      return false;
    }

    else {
      return true;
    }
  }

}
