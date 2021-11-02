package cellsociety.model.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;
import java.util.List;

/**
 * A class to generate the cells touching a given cell
 */
public class FullNeighbors extends CellSocietyNeighbors {

  /**
   * Super the CellSocietyNeighbors for future flexibility and abstraction
   */
  public FullNeighbors() {
    super();
  }

  /**
   * Generate all the cells touching our current cell
   *
   * @param row    current row of a Cell
   * @param col    current col of a Cell
   * @param myGrid the entire Grid
   * @return the List of Cells 'neighboring' our cell, meaning any cell connected to our cell in
   * this case
   */
  public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
    RectangleNeighbors myRectangleNeighbors = new RectangleNeighbors();
    List<Cells> myRectangleCells = myRectangleNeighbors.generateNeighbors(row, col, myGrid);

    DiagonalNeighbors myDiagonalNeighbors = new DiagonalNeighbors();
    List<Cells> myDiagonalCells = myDiagonalNeighbors.generateNeighbors(row, col, myGrid);

    myRectangleCells.addAll(myDiagonalCells);
    return myRectangleCells;
  }
}
