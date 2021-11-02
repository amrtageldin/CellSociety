package cellsociety.model.cellMovement;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * This class implements the CellSocietyMovement for the SchellingSegregation game. It deals with
 * the movement of the cell types A and B depending on the percentage of valid neighbors they have.
 * If the rules class returns that a cell needs to move to a region of different neighbors, this
 * class randomly selects open spots for the cell to move to, and immediately reflects this in the
 * current state of the cell. It utilizes consumers to deal with differing cell types and the
 * actions that they should take.
 */
public class SchellingSegregationMovement implements CellSocietyMovement {

  private static final String EMPTY = "EMPTY";
  private static final String SAME = "SAME";
  private static final String MOVE = "MOVE";
  private static final String A = "A";
  private static final String B = "B";
  private Grid myGrid;
  private ResourceBundle myStatesBundle;

  /**
   * This method sets up initial parameters (after being called by SchellingSegregationModel) to be
   * used throughout the class
   *
   * @param cell:         the current cell being processed to see how it needs to change
   * @param grid:         the grid of all cells with their current states
   * @param neighbors:    the cells that are next to the current cell
   * @param statesBundle: the resource bundle that holds all of the relevant states for the
   *                      Schelling Segregation Game.
   * @param parameters:   the map of all the relevant parameters for the Schelling game as read from
   *                      the .sim file.
   */
  public void setInitialParameters(Cells cell, Grid grid, List<Cells> neighbors,
      ResourceBundle statesBundle,
      Map<String, String> parameters) {
    myGrid = grid;
    myStatesBundle = statesBundle;
  }

  /**
   * This method is called after the relevant game rules have been applied to a cell. It checks to
   * see whether the cell has returned a MOVE key or a SAME key. Depending on the key that was
   * returned following the rule processing for the cell, a consumer is used to call the correct
   * method to set the next state of the cell.
   *
   * @param cell:  the current cell being considered in the state changing process
   * @param state: the state that was returned after processing all of the rules for the game. It
   *               can either be SAME (the cell should not change its current state) or MOVE (the
   *               cell needs to move to a region with more of its own kind of neighbors)
   */
  public void checkState(Cells cell, int state) {
    Map<Integer, Consumer<Integer>> intMap = Map.of(
        Integer.parseInt(myStatesBundle.getString(SAME)), integers -> keepState(cell),
        Integer.parseInt(myStatesBundle.getString(MOVE)), integers -> moveState(cell)
    );
    consumerNextState(state, intMap.get(state));
  }

  /**
   * This method is called when a cell returns the MOVE state after the rules processing. Depending
   * on current state of the cell, it calls the method to either move the cell to a randomly
   * selected empty cell (Cell types A and B are moved). Or, if the cell is already empty, it calls
   * a method to keep the state of the cell as it already is.
   *
   * @param cell: The current cell being considered in the state-changing process.
   */
  public void moveState(Cells cell) {
    Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(myStatesBundle.getString(A)),
        integers -> moveCells(cell),
        Integer.parseInt(myStatesBundle.getString(B)), integer -> moveCells(cell),
        Integer.parseInt(myStatesBundle.getString(EMPTY)), integer -> keepState(cell));
    consumerNextState(cell.getCurrentState(), intMap.get(cell.getCurrentState()));
  }

  private void moveCells(Cells cell) {
    findEmpty(cell);
    cell.setMyNextState(Integer.parseInt(myStatesBundle.getString(EMPTY)));
    cell.updateMyCurrentState();
  }

  /**
   * This method is called when the cell either has enough of its own kind of neighbors, or if the
   * cell is already empty. It keeps the position and state of the cell as it was before the
   * different rules were run on it.
   *
   * @param cell: the current cell being considered in the state-changing process.
   */
  public void keepState(Cells cell) {
    cell.setMyNextState(cell.getCurrentState());
  }

  private void findEmpty(Cells cell) {
    Random r = new Random();
    int randRow = r.nextInt(myGrid.rowLength());
    int randCol = r.nextInt(myGrid.colLength());
    if (myGrid.getCell(randRow, randCol).getCurrentState() == Integer.parseInt(
        myStatesBundle.getString(EMPTY))) {
      myGrid.getCell(randRow, randCol).setMyNextState(cell.getCurrentState());
      myGrid.getCell(randRow, randCol).updateMyCurrentState();
    } else {
      findEmpty(cell);
    }
  }

  private void consumerNextState(int currentState, Consumer<Integer> consumer) {
    consumer.accept(currentState);
  }
}
