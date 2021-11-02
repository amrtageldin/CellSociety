package cellsociety.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;

public class DiagonalNeighbors extends CellSocietyNeighbors{
    public DiagonalNeighbors(){super();}

    public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
        int[] xChanges = new int[]{-1,1};
        int[] yChanges = new int[]{-1,1};
        for (int i : xChanges){
            if (rowIsValid(row + i, myGrid)){
                for (int j : yChanges){
                    if (colIsValid(col + j, myGrid)){
                        addToMyCells(myGrid.getCell(row + i, col + j));
                    }
                }

            }
        }
        return getMyCells();
    }
}
