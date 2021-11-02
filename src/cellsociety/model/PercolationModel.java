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
    int initialState = bundleToInteger(PERCOLATED);
    int quantityOfPercolatedCells = quantityOfCellsOfGivenStateInCluster(initialState, neighborGenerator(row,col,myGrid));

    Integer open = bundleToInteger(OPEN);
    Integer closed = bundleToInteger(CLOSED);
    Integer percolated = bundleToInteger(PERCOLATED);

    Map<Integer, Consumer<Integer>> intMap = Map.of(closed, integers -> myCell.setMyNextState(closed),
        percolated, integers-> myCell.setMyNextState(percolated),
        open, integers ->  myCell.setMyNextState((getMyRules().generateNextState(quantityOfPercolatedCells, myCell.getCurrentState()))
        ));

    this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));
  }
}
