package cellsociety.model;


import java.util.ArrayList;
import java.util.List;

public abstract class CellSocietyModel {
  public CellSocietyModel(){

  }

  public abstract int getNextState(Cells myCell);

  public abstract void setNextState(Cells myCell, int row, int col, Cells[][] myGrid);

  protected int quantityOfCellsOfGivenStateInCluster(int state, List<Cells> myRelevantCluster) {
    int runningCountOfState = 0;
    for (Cells x : myRelevantCluster){
      if (x.getCurrentState() == state) {
        runningCountOfState++;
      }
    }
    return runningCountOfState;
  }


  protected List<Cells> generateNeighbors(int row, int col, Cells[][] myGrid) {
    // square
    int[] xChanges = new int[]{-1,0,1};
    int[] yChanges = new int[]{-1,0,1};
    List<Cells> myCells = new ArrayList<>();
    for (int i : xChanges){
      if (rowIsValid(row + i, myGrid)){
        for (int j : yChanges){
          if (colIsValid(col + j, myGrid) && !(i == 0 && j == 0)){
            myCells.add(myGrid[row + i][ col + j]);
          }
        }

      }
    }
    return myCells;
  }

  private boolean colIsValid(int col, Cells[][] myGrid) {
    return col >=0 && col < myGrid[0].length;
  }

  private boolean rowIsValid(int row, Cells[][] myGrid) {
    return row >=0 && row < myGrid.length;
  }



}
