package cellsociety.model;

import cellsociety.controller.Grid;
import cellsociety.model.cellMovement.WaTorMovement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class extends the CellSocietyModel class to implement the WaTor game for CellSociety. It
 * overrides the abstract setNextState() method within the abstract class to set up the next state
 * of a cell as a result of applied WaTor-specific rules.
 */
public class WaTorModel extends CellSocietyModel {

  private static final String FISH = "FISH";
  private static final String SHARK = "SHARK";
  private static final String EMPTY = "EMPTY";
  private static final int STEP_RESET = 1;
  private static final int STEP_BUFFER = 1;
  private final WaTorMovement myWaTorMovement = new WaTorMovement();
  private int stepCheck = 0;
  private int movableCells = 0;
  private List<Cells> changedCells = new ArrayList<>();

  /**
   * The constructor for the WaTor Model. It takes in the same variables as the abstract class.
   *
   * @param type:       The type of game being played, in this case it is WaTor.
   * @param parameters: All of the relevant parameters for the SchellingSegregation game. This
   *                    includes the max reproduction required for cells to reproduce, as well as
   *                    the starting energy levels for Shark cells. This also includes the
   *                    neighboring rules for the current game.
   */
  public WaTorModel(String type, Map<String, String> parameters) {
    super(type, parameters);
  }

  /**
   * This method overrides the abstract setNextState method in the abstract CellSocietyModel class.
   * It moves the Fish and Shark cells to open spots (empty cells for fish and empty or fish cells
   * for sharks). It also uses the WaTorMovement class to move these cells as well as keep track of
   * the reproduction and energy stages each relevant cell is at. Empty cells are not changed unless
   * a shark or fish cell moves into them.
   *
   * @param myCell: the current cell being considered in the state-changing process
   * @param row:    the row number of the current cell
   * @param col:    the column number of the current cell
   * @param myGrid: the grid of all the cells, we use the current cell's row and column
   */
  @Override
  public void setNextState(Cells myCell, int row, int col, Grid myGrid) {
    changedCells = myWaTorMovement.getStep();
    stepCheck++;
    gridCheck(myGrid.colLength() * myGrid.rowLength());
    List<Cells> myNeighbors = neighborGenerator(row, col, myGrid);
    myWaTorMovement.setInitialParameters(myCell, myGrid, myNeighbors, getStatesBundle(),
        getMyParameters());
    updateNeighbors(myNeighbors);
    updateWaTorStates(myCell, myNeighbors);
  }

  private void gridCheck(int gridSize) {
    if (stepCheck == gridSize + STEP_BUFFER) {
      changedCells.clear();
      stepCheck = STEP_RESET;
    }
  }

  private void updateWaTorStates(Cells myCell, List<Cells> myNeighbors) {
    if (!changedCells.contains(myCell)) {
      int movableCells = findMovableCells(myCell, myNeighbors);
      myWaTorMovement.checkState(myCell,
          getMyRules().generateNextState(movableCells, myCell.getCurrentState()));
    }
  }

  private void updateNeighbors(List<Cells> myNeighbors) {
    myNeighbors.removeIf(c -> changedCells.contains(c));
  }


  private int findMovableCells(Cells myCell, List<Cells> myNeighbors) {
    Map<Integer, Consumer<Integer>> movableMap =
        Map.of(bundleToInteger(FISH),
            integer -> movableCellsForFish(myNeighbors),
            bundleToInteger(SHARK),
            integer -> movableCellsForShark(myNeighbors),
            bundleToInteger(EMPTY),
            integer -> {
            });
    consumerGenerateNextState(myCell.getCurrentState(), movableMap.get(myCell.getCurrentState()));
    return movableCells;
  }

  private void movableCellsForFish(List<Cells> neighbors) {
    movableCells = quantityOfCellsOfGivenStateInCluster(bundleToInteger(EMPTY), neighbors);
  }

  private void movableCellsForShark(List<Cells> neighbors) {
    movableCells =
        neighbors.size() - quantityOfCellsOfGivenStateInCluster(bundleToInteger(SHARK), neighbors);
  }
}


