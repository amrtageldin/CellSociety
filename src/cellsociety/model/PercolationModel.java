package cellsociety.model;

import cellsociety.ruleStructure.PercolationRules;
import java.util.List;

public class PercolationModel extends CellSocietyModel{

  public PercolationModel(String type){
    super(type);
  }


  @Override
  public int getNextState(Cells myCell){
    return myCell.getMyNextState();
  }

  @Override
  public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
    List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
    int initialState = Integer.parseInt(statesBundle.getString(initialStateString));
    int quantityOfPercolatedCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);
    myCell.setMyNextState(myRules.generateNextState(quantityOfPercolatedCells, myCell.getCurrentState()));
  }

}
