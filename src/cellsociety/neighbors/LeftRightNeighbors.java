package cellsociety.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;

public class LeftRightNeighbors extends CellSocietyNeighbors{

    public LeftRightNeighbors(){super();}

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
