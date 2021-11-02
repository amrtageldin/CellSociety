package cellsociety.controller;

import cellsociety.Errors.ErrorFactory;
import cellsociety.model.Cells;
import com.opencsv.CSVReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * This class initializes all of the cells present in a Grid by parsing a CSV file. It has the
 * functionality to either read each cell's state from the CSV file, or generate random states based
 * on valid states for the current game being played. The final Grid object that it creates is used
 * throughout the program to keep track of every cell within the game.
 */
public class Grid {

  private static final String X = "x";
  private static final String INVALID_GRID = "InvalidGrid";
  private final Cells[][] myGrid;
  private final ErrorFactory myErrorFactory = new ErrorFactory();
  private final ArrayList<Integer> cellStatesList = new ArrayList<>();

  /**
   * This is the constructor for the Grid object. It takes in the row and column count, which were
   * read from a CSV via the GridFactory class.
   *
   * @param rowCount: number of rows that will be in the Grid
   * @param colCount: number of columns that will be in the Grid
   */
  public Grid(int rowCount, int colCount) {
    myGrid = new Cells[rowCount][colCount];
  }

  /**
   * Given a game type, this method gets all of the possible states within that game so that if a
   * CSV file with unknown states is loaded the Grid will be able to self-populate with the
   * knowledge of which states are relevant to which games.
   *
   * @param cellStates: a String that holds the possible states for the game being played, it is
   *                    split into an array and then parsed to an integer and added to an ArrayList
   *                    of possible states.
   */
  public void getCellStates(String cellStates) {
    String[] cellStateStrings = cellStates.split(",");
    for (String s : cellStateStrings) {
      cellStatesList.add(Integer.parseInt(s));
    }
  }

  /**
   * This method is called by the GridFactory to initialize each of the cells within the Grid
   * object. It takes in a CSVReader object, and then parses the CSV for each cell so that it can be
   * set up within the Grid. If the cells are "x", then they are randomly generated using the
   * knowledge of valid cell states for the current game. Otherwise, the cell states are read from
   * the CSV file.
   *
   * @param csvReader: A CSVReader object that can be parsed to get relevant cell state information
   */
  public void initializeCells(CSVReader csvReader) {
    try {
      String[] nextCell;
      int i = 0;
      while ((nextCell = csvReader.readNext()) != null) {
        if (Objects.equals(nextCell[0], X)) {
          initializeRandomCells(i, nextCell);
        } else {
          initializeCSVReadCells(i, nextCell);
        }
        i++;
      }
    } catch (Exception e) {
      myErrorFactory.updateError(INVALID_GRID);
    }
  }

  private void initializeCSVReadCells(int i, String[] nextCell) {
    for (int j = 0; j < nextCell.length; j++) {
      myGrid[i][j] = new Cells(Integer.parseInt(nextCell[j]));
    }
  }

  private void initializeRandomCells(int i, String[] nextCell) {
    Random r = new Random();
    for (int j = 0; j < nextCell.length; j++) {
      myGrid[i][j] = new Cells(cellStatesList.get(r.nextInt(cellStatesList.size())));
    }
  }

  /**
   * This method returns the number of rows in the Grid.
   *
   * @return myGrid.length: the number of rows in the Grid.
   */
  public int rowLength() {
    return myGrid.length;
  }

  /**
   * This method returns the number of columns in the Grid.
   *
   * @return myGrid[0].length: the number of columns in the Grid.
   */
  public int colLength() {
    return myGrid[0].length;
  }

  /**
   * This method returns the cell specified by row i and column j.
   *
   * @param i: the row of the desired cell
   * @param j: the column of the desired cell
   * @return myGrid[i][j]: the cell specified by row i and column j.
   */
  public Cells getCell(int i, int j) {
    return myGrid[i][j];
  }

  /**
   * This method sets a cell in the Grid row i and column j to equal another cell. It is used for
   * testing purposes.
   *
   * @param i:      the row of the cell to be updated
   * @param j:      the column of the cell to be updated
   * @param myCell: the cell to update Grid row i, column j to.
   */
  public void setCell(int i, int j, Cells myCell) {
    myGrid[i][j] = myCell;
  }

  /**
   * This method returns the Grid class's instance of ErrorFactory, which holds information as to
   * whether or not an error exists, and if it does exist - what the error is. It is used by the
   * controller to keep track of any errors that show up while loading the .sim file.
   *
   * @return myErrorFactory: the error information for the Grid class
   */
  public ErrorFactory getMyErrorFactory() {
    return myErrorFactory;
  }
}
