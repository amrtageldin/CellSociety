package cellsociety.model;

import java.util.List;

public class PercolationModel extends CellSocietyModel{

  public static final String PERCOLATED = "PERCOLATED";

  public PercolationModel(String type){
    super(type);
  }

  @Override
  public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
    List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
    int initialState = Integer.parseInt(statesBundle.getString(PERCOLATED));
    int quantityOfPercolatedCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);
    myCell.setMyNextState(myRules.generateNextState(quantityOfPercolatedCells, myCell.getCurrentState()));
  }

}
