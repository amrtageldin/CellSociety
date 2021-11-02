package cellsociety.model.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.List;

/**
 * A class to generate the cells left, right, above, or below any given cell
 */
public class RectangleNeighbors extends CellSocietyNeighbors{

    /**
     * Super the CellSocietyNeighbors for future flexibility and abstraction
     */
    public RectangleNeighbors(){super();}

    /**
     * Generate all the cells left or right of our current cell
     * @param row current row of a Cell
     * @param col current col of a Cell
     * @param myGrid the entire Grid
     * @return the List of Cells left, right, below, or above our cell
     */
    public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
        UpDownNeighbors myUpDown = new UpDownNeighbors();
        List<Cells> myUpDownCells = myUpDown.generateNeighbors(row,col,myGrid);

        LeftRightNeighbors myLeftRight = new LeftRightNeighbors();
        List<Cells> myLeftRightCells = myLeftRight.generateNeighbors(row,col,myGrid);

        myUpDownCells.addAll(myLeftRightCells);
        return myUpDownCells;
    }
}
