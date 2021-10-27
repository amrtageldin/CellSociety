package cellsociety.model;

import cellsociety.controller.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FireModel extends CellSocietyModel{

  private static final String BURNING = "BURNING";
  private static final String TREE = "TREE";
  private static final String EMPTY = "EMPTY";
  private static final int SCALE_FACTOR = 100;

  public FireModel(String type){
    super(type);
  }

  @Override
  public void setNextState(Cells myCell, int row, int col, Grid myGrid){
    List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
    int initialState = Integer.parseInt(getStatesBundle().getString(BURNING));
    int quantityOfBurningCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);
    int randomlyGeneratedNumber = (int) Math.floor(Math.random() * SCALE_FACTOR) * quantityOfBurningCells;


    Integer empty = Integer.parseInt(getStatesBundle().getString(EMPTY));
    Integer burning = Integer.parseInt(getStatesBundle().getString(BURNING));
    Integer tree = Integer.parseInt(getStatesBundle().getString(TREE));

    Map<Integer, Consumer<Integer>> intMap = Map.of(empty, integers -> myCell.setMyNextState(empty),
        burning, integers-> myCell.setMyNextState(empty),
        tree, integers ->  myCell.setMyNextState(getMyRules().generateNextState(randomlyGeneratedNumber, myCell.getCurrentState()))
    );

    this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));

  }


  @Override
  protected List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
    int[] xChanges = new int[]{-1,1};
    List<Cells> myCells = new ArrayList<>();
    for (int i : xChanges){
      if (rowIsValid(row + i, myGrid)){
            myCells.add(myGrid.getCell(row+i, col));
        }
      }

    int[] yChanges = new int[]{-1,1};
    for (int j : yChanges){
      if (colIsValid(col + j, myGrid)){
        myCells.add(myGrid.getCell(row,col+j));
      }
    }


    return myCells;
  }

}
