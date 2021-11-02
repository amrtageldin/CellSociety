package cellsociety.model.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.List;

public abstract class CellSocietyNeighbors {

    public abstract List<Cells> generateNeighbors(int row, int col, Grid myGrid);
    protected boolean colIsValid(int col, Grid myGrid) {
        return col >=0 && col < myGrid.colLength();
    }

    protected boolean rowIsValid(int row, Grid myGrid) {
        return row >=0 && row < myGrid.rowLength();
    }
}
