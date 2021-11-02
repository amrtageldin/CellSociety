package cellsociety.model.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to generate the cells above or below any given cell
 */
public class UpDownNeighbors extends CellSocietyNeighbors {

  /**
   * Super the CellSocietyNeighbors for future flexibility and abstraction
   */
  public UpDownNeighbors() {
    super();
  }

  /**
   * Generate all the cells left or right of our current cell
   *
   * @param row    current row of a Cell
   * @param col    current col of a Cell
   * @param myGrid the entire Grid
   * @return the List of Cells above or below our cell
   */
  public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
    List<Cells> myCells = new ArrayList<>();
    int[] yChanges = new int[]{-1, 1};
    for (int i : yChanges) {
      if (rowIsValid(row + i, myGrid)) {
        myCells.add(myGrid.getCell(row + i, col));
      }
    }
    return myCells;
  }
}
