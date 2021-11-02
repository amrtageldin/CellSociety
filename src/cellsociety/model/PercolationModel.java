package cellsociety.model;

import cellsociety.controller.Grid;
import java.util.Map;
import java.util.function.Consumer;
/**
 * This class extends the CellSocietyModel class to implement
 * the Percolation game for CellSociety. It overrides the abstract setNextState()
 * method within the abstract class to set up the next state of a cell as a result
 * of applied Percolation-specific rules.
 */
public class PercolationModel extends CellSocietyModel{

  private static final String PERCOLATED = "PERCOLATED";
  private static final String OPEN = "OPEN";
  private static final String CLOSED = "CLOSED";

  /**
   * The constructor for the Percolation Model. It takes in the same variables
   * as the abstract class.
   * @param type: The type of game being played, in this case it is Percolation.
   * @param parameters: All of the relevant parameters for the Percolation game. This
   *                  includes the neighboring rules for the current game.
   */
  public PercolationModel(String type, Map<String, String> parameters) { super(type, parameters);}

  /**
   * This method overrides the abstract setNextState method in the abstract
   * CellSocietyModel class. It finds all of the Percolated cells within the current
   * cell's list of neighbors. It sends this number of Percolated cells within the neighbors
   * into the CellSocietyRules class to determine what the cell's next state should be.
   * At the end, the cell is either made percolated if it is open and has percolated neighbors,
   * kept closed if it is closed, or kept percolated if it is percolated.
   * @param myCell: the current cell being considered in the state-changing process
   * @param row: the row number of the current cell
   * @param col: the column number of the current cell
   * @param myGrid: the grid of all the cells, we use the current cell's row and column
   */
  @Override
  public void setNextState(Cells myCell, int row, int col, Grid myGrid){
    int initialState = bundleToInteger(PERCOLATED);
    int quantityOfPercolatedCells = quantityOfCellsOfGivenStateInCluster(initialState, neighborGenerator(row,col,myGrid));

    Integer open = bundleToInteger(OPEN);
    Integer closed = bundleToInteger(CLOSED);
    Integer percolated = bundleToInteger(PERCOLATED);

    Map<Integer, Consumer<Integer>> intMap = Map.of(closed, integers -> myCell.setMyNextState(closed),
        percolated, integers-> myCell.setMyNextState(percolated),
        open, integers ->  myCell.setMyNextState((getMyRules().generateNextState(quantityOfPercolatedCells, myCell.getCurrentState()))
        ));

    this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));
  }
}
