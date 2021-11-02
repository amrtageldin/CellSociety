package cellsociety.model;

import cellsociety.controller.Grid;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class extends the CellSocietyModel class to implement the GameOfLife game for CellSociety.
 * It overrides the abstract setNextState() method within the abstract class to set up the next
 * state of a cell as a result of applied GameOfLife-specific rules.
 */
public class GameOfLifeModel extends CellSocietyModel {

  private static final String ALIVE = "ALIVE";
  private static final String DEAD = "DEAD";


  /**
   * The constructor for the Fire Model. It takes in the same variables as the abstract class.
   *
   * @param type:       The type of game being played, in this case it is GameOfLife.
   * @param parameters: All of the relevant parameters for the GameOfLife game. This includes the
   *                    neighboring rules for the current game.
   */
  public GameOfLifeModel(String type, Map<String, String> parameters) {
    super(type, parameters);
  }

  /**
   * This method overrides the abstract setNextState method in the abstract CellSocietyModel class.
   * It finds all of the Alive cells within the current cell's list of neighbors. It sends this
   * number of Alive cells within the neighbors into the CellSocietyRules class to determine what
   * the cell's next state should be. At the end, the cell is either made alive if enough neighbor
   * cells are alive, made dead if too many cells are alive or too many neighbor cells are dead, or
   * not changed at all.
   *
   * @param myCell: the current cell being considered in the state-changing process
   * @param row:    the row number of the current cell
   * @param col:    the column number of the current cell
   * @param myGrid: the grid of all the cells, we use the current cell's row and column
   */
  @Override
  public void setNextState(Cells myCell, int row, int col, Grid myGrid) {
    int initialState = bundleToInteger(ALIVE);
    int quantityOfLivingCells = quantityOfCellsOfGivenStateInCluster(initialState,
        neighborGenerator(row, col, myGrid));

    Integer alive = bundleToInteger(ALIVE);
    Integer dead = bundleToInteger(DEAD);

    Map<Integer, Consumer<Integer>> intMap = Map.of(alive, integers -> myCell.setMyNextState(
            getMyRules().generateNextState(quantityOfLivingCells, myCell.getCurrentState())),
        dead, integers -> myCell.setMyNextState(
            getMyRules().generateNextState(quantityOfLivingCells, myCell.getCurrentState()))
    );
    this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));
  }


}
