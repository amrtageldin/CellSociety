package cellsociety.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;

public class FullNeighbors extends CellSocietyNeighbors{

    public FullNeighbors(){
        super();
    }
    public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
        int[] xChanges = new int[]{-1,0,1};
        int[] yChanges = new int[]{-1,0,1};
        List<Cells> myCells = new ArrayList<>();
        for (int i : xChanges){
            if (rowIsValid(row + i, myGrid)){
                for (int j : yChanges){
                    if (colIsValid(col + j, myGrid) && !(i == 0 && j == 0)){
                        myCells.add(myGrid.getCell(row + i, col + j));
                    }
                }

            }
        }
        return myCells;
    }
}
