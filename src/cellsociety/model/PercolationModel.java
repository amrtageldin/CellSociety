package cellsociety.model;

import cellsociety.ruleStructure.PercolationRules;
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
    int quantityOfPercolatedCells = quantityOfCellsOfGivenStateInCluster(PERCOLATED, myNeighbors);
    myCell.setMyNextState(myRules.generateNextState(quantityOfPercolatedCells, myCell.getCurrentState()));
  }

}
