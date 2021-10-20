package cellsociety.model;

import cellsociety.ruleStructure.CellSocietyRules;
import cellsociety.ruleStructure.GameOfLifeRules;
import cellsociety.ruleStructure.PercolationRules;
import java.util.ArrayList;
import java.util.List;

public class PercolationModel extends CellSocietyModel{
  private final int OPEN = 3;
  private final int CLOSED = 0;
  private final int PERCOLATED = 2;
  private PercolationRules myRules;

  public PercolationModel(){
    myRules = new PercolationRules();
  }


  @Override
  public int getNextState(Cells myCell){
    return myCell.getMyNextState();
  }

  @Override
  public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
    List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
    int quantityOfPercolatedCells = relevantClumpQuantityInDesiredState(PERCOLATED, myNeighbors);
    myCell.setMyNextState(myRules.generateNextState(quantityOfPercolatedCells, myCell.getCurrentState()));
  }

  private List<Cells> generateNeighbors(int row, int col, Cells[][] myGrid) {
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

  private int relevantClumpQuantityInDesiredState(int state, List<Cells> myRelevantCluster) {
    int runningCountOfState = 0;
    for (Cells x : myRelevantCluster){
      if (x.getCurrentState() == state) {
        runningCountOfState++;
      }
    }
    return runningCountOfState;
  }

}
