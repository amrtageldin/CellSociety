package cellsociety.model;

import cellsociety.controller.Grid;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class PercolationModel extends CellSocietyModel{

  private static final String PERCOLATED = "PERCOLATED";
  private static final String OPEN = "OPEN";
  private static final String CLOSED = "CLOSED";


  public PercolationModel(String type, Map<String, String> parameters) { super(type, parameters);}

  @Override
  public void setNextState(Cells myCell, int row, int col, Grid myGrid){
    List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
    int initialState = Integer.parseInt(getStatesBundle().getString(PERCOLATED));
    int quantityOfPercolatedCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);

    Integer open = Integer.parseInt(getStatesBundle().getString(OPEN));
    Integer closed = Integer.parseInt(getStatesBundle().getString(CLOSED));
    Integer percolated = Integer.parseInt(getStatesBundle().getString(PERCOLATED));

    Map<Integer, Consumer<Integer>> intMap = Map.of(closed, integers -> myCell.setMyNextState(closed),
        percolated, integers-> myCell.setMyNextState(percolated),
        open, integers ->  myCell.setMyNextState((getMyRules().generateNextState(quantityOfPercolatedCells, myCell.getCurrentState()))
    ));

    this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));
  }
}
