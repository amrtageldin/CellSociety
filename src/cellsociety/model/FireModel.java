package cellsociety.model;

import cellsociety.controller.Grid;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class extends the CellSocietyModel class to implement
 * the Fire game for CellSociety. It overrides the abstract setNextState()
 * method within the abstract class to set up the next state of a cell as a result
 * of applied Fire-specific rules.
 */
public class FireModel extends CellSocietyModel{

  private static final String BURNING = "BURNING";
  private static final String TREE = "TREE";
  private static final String EMPTY = "EMPTY";
  private static final int SCALE_FACTOR = 100;

  /**
   * The constructor for the Fire Model. It takes in the same variables
   * as the abstract class.
   * @param type: The type of game being played, in this case it is Fire.
   * @param parameters: All of the relevant parameters for the Fire game. This includes
   *                  the percent of neighbors needed for the fire to develop.
   *                  This also includes the neighboring rules for the current game.
   */
  public FireModel(String type, Map<String, String> parameters) { super(type, parameters);}

  /**
   * This method overrides the abstract setNextState method in the abstract
   * CellSocietyModel class. It finds all of the burning cells within the current
   * cell's list of neighbors. It determines this as a percent, and sends this percentage
   * into the CellSocietyRules class to determine what the cell's next state should be.
   * At the end, the cell is updated to this new state (or kept the same if no rules are
   * valid).
   * @param myCell: the current cell being considered in the state-changing process
   * @param row: the row number of the current cell
   * @param col: the column number of the current cell
   * @param myGrid: the grid of all the cells, we use the current cell's row and column
   */
  @Override
  public void setNextState(Cells myCell, int row, int col, Grid myGrid){
    int initialState = bundleToInteger(BURNING);
    int quantityOfBurningCells = quantityOfCellsOfGivenStateInCluster(initialState, neighborGenerator(row,col,myGrid));
    int randomlyGeneratedNumber = (int) Math.floor(Math.random() * SCALE_FACTOR) * quantityOfBurningCells;


    Integer empty = bundleToInteger(EMPTY);
    Integer burning = bundleToInteger(BURNING);
    Integer tree = bundleToInteger(TREE);

    Map<Integer, Consumer<Integer>> intMap = Map.of(empty, integers -> myCell.setMyNextState(empty),
        burning, integers-> myCell.setMyNextState(empty),
        tree, integers ->  myCell.setMyNextState(getMyRules().generateNextState(randomlyGeneratedNumber, myCell.getCurrentState()))
    );

    this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));

  }




}
