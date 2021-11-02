package cellsociety.model.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to generate the cells left or right of a given cell
 */
public class LeftRightNeighbors extends CellSocietyNeighbors{

    /**
     * Super the CellSocietyNeighbors for future flexibility and abstraction
     */
    public LeftRightNeighbors(){super();}

    /**
     * Generate all the cells left or right of our current cell
     * @param row current row of a Cell
     * @param col current col of a Cell
     * @param myGrid the entire Grid
     * @return the List of Cells left or right of our cell
     */
    public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
        List<Cells> myCells = new ArrayList<>();
        int[] yChanges = new int[]{-1,1};
        for (int j : yChanges){
            if (colIsValid(col + j, myGrid)){
                myCells.add(myGrid.getCell(row,col+j));
            }
        }
        return myCells;
    }
}
