package cellsociety.controller;

import cellsociety.Errors.ErrorFactory;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.Map;

/**
 * This class takes in a csv file, and parses it to initialize the Grid for the CellSociety game. It
 * creates a Grid object to be used throughout the game to update cells and their states. It assumes
 * a valid CSV file is loaded, but returns errors to the user for any invalid files.
 */
public class GridFactory {

  private static final String FIRE = "Fire_Spread";
  private static final String INVALID_GRID = "InvalidGrid";
  private static final String WA_TOR = "wa_tor";
  private static final String PERCOLATION_STATES = "0,1,2";
  private static final String WA_TOR_STATES = "0,1,2";
  private static final String FIRE_STATES = "0,1,2";
  private static final String GAME_OF_LIFE = "game_of_life";
  private static final String GAME_OF_LIFE_STATES = "0,1";
  private static final String PERCOLATION = "percolation";
  private static final String SCHELLING_SEGREGATION = "schelling_segregation";
  private static final String SCHELLING_SEGREGATION_STATES = "0,2,3";
  private static final int INVALID = 0;
  private final Map<String, String> gameStates;
  private ErrorFactory myErrorFactory = new ErrorFactory();
  private Grid myGrid;
  private String rowCount;
  private String colCount;
  private String myValueSet;

  /**
   * A constructor for the GridFactory class, it sets up the gameStates map which contains a key of
   * each game, and a value of the possible states within that game. It is used by the Grid to
   * initialize cells that are not already specified in a CSV file.
   */
  public GridFactory() {
    gameStates = Map.of(WA_TOR, WA_TOR_STATES,
        FIRE, FIRE_STATES,
        GAME_OF_LIFE, GAME_OF_LIFE_STATES,
        PERCOLATION, PERCOLATION_STATES,
        SCHELLING_SEGREGATION, SCHELLING_SEGREGATION_STATES);
  }

  /**
   * This method initially sets up the grid that has a state value for each cell. It reads in a .csv
   * file to find the number of rows and columns in the grid, and then calls the Grid class to
   * initialize the cells within the grid based on what the initial state of each cell is.
   *
   * @param file: .csv file that contains grid row/column numbers as well as initial state for each
   *              cell
   * @return: myGrid - a Cells[][] object with original states for all cells
   */
  public Grid setUpGrid(String file) {
    try {
      checkGameType(file);
      FileReader fileReader = new FileReader(file);
      CSVReader csvReader = new CSVReader(fileReader);
      initializeRowAndColumnCounts(csvReader);
      initializeGrid();
      myGrid.initializeCells(csvReader);
    } catch (Exception e) {
      myGrid = new Grid(INVALID, INVALID);
      myGrid.getMyErrorFactory().updateError(INVALID_GRID);
      myErrorFactory = myGrid.getMyErrorFactory();
    }
    return myGrid;
  }

  private void checkGameType(String file) {
    for (String k : gameStates.keySet()) {
      if (file.contains(k)) {
        myValueSet = gameStates.get(k);
      }
    }
  }

  private void initializeRowAndColumnCounts(CSVReader csvReader) {
    try {
      String[] rowAndColumn = csvReader.readNext();
      rowCount = rowAndColumn[1];
      colCount = rowAndColumn[0];
    } catch (Exception e) {
      myErrorFactory.updateError(INVALID_GRID);
    }
  }

  private void initializeGrid() {
    myGrid = new Grid(Integer.parseInt(rowCount), Integer.parseInt(colCount));
    myGrid.getCellStates(myValueSet);
  }

  /**
   * This method returns the GridFactory class's instance of ErrorFactory, which holds information
   * as to whether or not an error exists, and if it does exist - what the error is. It is used by
   * the controller to keep track of any errors that show up while loading the .sim file.
   *
   * @return myErrorFactory: the error information for the GridFactory class
   */
  public ErrorFactory getMyErrorFactory() {
    return myErrorFactory;
  }
}
