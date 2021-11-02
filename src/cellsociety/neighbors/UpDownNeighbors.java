package cellsociety.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;

public class UpDownNeighbors extends CellSocietyNeighbors{

  public UpDownNeighbors(){super();}

  public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
    int[] yChanges = new int[]{-1,1};
    for (int i : yChanges){
      if (rowIsValid(row + i, myGrid)){
        addToMyCells(myGrid.getCell(row + i,col));
      }
    }
    return getMyCells();
  }
}
