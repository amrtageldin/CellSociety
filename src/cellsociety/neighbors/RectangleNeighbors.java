package cellsociety.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;

public class RectangleNeighbors extends CellSocietyNeighbors{

    public RectangleNeighbors(){super();}

    public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
        int[] xChanges = new int[]{-1,1};
        List<Cells> myCells = new ArrayList<>();
        for (int i : xChanges){
            if (rowIsValid(row + i, myGrid)){
                myCells.add(myGrid.getCell(row+i, col));
            }
        }

        int[] yChanges = new int[]{-1,1};
        for (int j : yChanges){
            if (colIsValid(col + j, myGrid)){
                myCells.add(myGrid.getCell(row,col+j));
            }
        }

        return myCells;
    }
}
