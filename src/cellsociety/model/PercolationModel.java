package cellsociety.model;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

    Integer open = 3;
    Integer closed = 0;
    Integer percolated = 2;

    Map<Integer, Consumer<Integer>> intMap = Map.of(closed, integers -> myCell.setMyNextState(closed),
        percolated, integers-> myCell.setMyNextState(percolated),
        open, integers ->  myCell.setMyNextState((myRules.generateNextState(quantityOfPercolatedCells, myCell.getCurrentState()))
    ));

    this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));



  }

}
