package cellsociety.model.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.List;

/**
 * The general structure for a class that will deem the valid neighbors given a definition of
 * neighbors
 */
public abstract class CellSocietyNeighbors {

    /**
     * abstractify the way each class deems the 'neighbor' of a cell and generate those neighbors
     * @param row current row
     * @param col current col
     * @param myGrid the entire grid
     * @return the list of cells the class deems to be a 'neighbor' of our cell
     */
    public abstract List<Cells> generateNeighbors(int row, int col, Grid myGrid);

    protected boolean colIsValid(int col, Grid myGrid) {
        return col >=0 && col < myGrid.colLength();
    }

    protected boolean rowIsValid(int row, Grid myGrid) {
        return row >=0 && row < myGrid.rowLength();
    }
}
