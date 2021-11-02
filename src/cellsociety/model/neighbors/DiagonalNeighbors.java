package cellsociety.model.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to generate the cells diagonal to any given cell
 */
public class DiagonalNeighbors extends CellSocietyNeighbors{
    /**
     * Super the CellSocietyNeighbors for future flexibility and abstraction
     */
    public DiagonalNeighbors(){super();}

    /**
     * Generate all the cells diagonal to our current cell
     * @param row current row of a Cell
     * @param col current col of a Cell
     * @param myGrid the entire Grid
     * @return the List of Cells 'neighboring' our cell, meaning diagonal to in this case
     */
    public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
        int[] xChanges = new int[]{-1,1};
        int[] yChanges = new int[]{-1,1};
        List<Cells> myCells = new ArrayList<>();
        for (int i : xChanges){
            if (rowIsValid(row + i, myGrid)){
                for (int j : yChanges){
                    if (colIsValid(col + j, myGrid)){
                        myCells.add(myGrid.getCell(row + i, col + j));
                    }
                }

            }
        }
        return myCells;
    }
}
