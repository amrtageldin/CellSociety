package cellsociety.model;

import cellsociety.ruleStructure.GameOfLifeRules;
import java.util.ArrayList;
import java.util.List;

public class FireModel extends CellSocietyModel{

  public static final String BURNING = "BURNING";
  public static final String TREE = "TREE";
  public static final String EMPTY = "EMPTY";

  public FireModel(String type){
    super(type);
  }

  @Override
  public int getNextState(Cells myCell){
    return myCell.getMyNextState();
  }

  @Override
  public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
    List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
    int initialState = Integer.parseInt(statesBundle.getString(BURNING));
    int quantityOfBurningCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);
    int randomlyGeneratedNumber = (int) Math.floor(Math.random() * 100) * quantityOfBurningCells;
    myCell.setMyNextState(myRules.generateNextState(randomlyGeneratedNumber, myCell.getCurrentState()));
  }

  @Override
  protected List<Cells> generateNeighbors(int row, int col, Cells[][] myGrid) {
    int[] xChanges = new int[]{-1,1};
    List<Cells> myCells = new ArrayList<>();
    for (int i : xChanges){
      if (rowIsValid(row + i, myGrid)){
            myCells.add(myGrid[row + i][col]);
        }
      }

    int[] yChanges = new int[]{-1,1};
    for (int j : yChanges){
      if (colIsValid(col + j, myGrid)){
        myCells.add(myGrid[row][col + j]);
      }
    }


    return myCells;
  }

}
