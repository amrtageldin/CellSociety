package cellsociety.model;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class PercolationModel extends CellSocietyModel{

  public static final String PERCOLATED = "PERCOLATED";
  public static final String OPEN = "OPEN";
  public static final String CLOSED = "CLOSED";


  public PercolationModel(String type){
    super(type);
  }

  @Override
  public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
    List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
    int initialState = Integer.parseInt(statesBundle.getString(PERCOLATED));
    int quantityOfPercolatedCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);

    Integer open = Integer.parseInt(statesBundle.getString(OPEN));
    Integer closed = Integer.parseInt(statesBundle.getString(CLOSED));
    Integer percolated = Integer.parseInt(statesBundle.getString(PERCOLATED));

    Map<Integer, Consumer<Integer>> intMap = Map.of(closed, integers -> myCell.setMyNextState(closed),
        percolated, integers-> myCell.setMyNextState(percolated),
        open, integers ->  myCell.setMyNextState((myRules.generateNextState(quantityOfPercolatedCells, myCell.getCurrentState()))
    ));

    this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));
  }
}
