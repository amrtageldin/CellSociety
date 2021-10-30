package cellsociety.model;


import cellsociety.controller.Grid;
import cellsociety.ruleStructure.CellSocietyRules;

import java.util.*;
import java.util.function.Consumer;

public abstract class CellSocietyModel {
  private CellSocietyRules myRules;
  private ResourceBundle statesBundle;
  private Map<String, String> myParametersMap;


  public CellSocietyModel(String myType, Map<String, String> parameters){
    myParametersMap = parameters;
    try{
      Object [] paramValuesSub = {myType, myParametersMap};
      myRules = (CellSocietyRules) Class.forName(String.format("cellsociety.ruleStructure.%sRules", myType)).getConstructor(String.class, Map.class).newInstance(paramValuesSub);
      String modelResourceBundleBase = "cellsociety.model.resources.";
      statesBundle = ResourceBundle.getBundle(String.format("%s%sStates", modelResourceBundleBase, myType));
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }


  public Map<String, String> getMyParameters(){
    return myParametersMap;
  }

  public CellSocietyRules getMyRules(){
    return myRules;
  }

  public ResourceBundle getStatesBundle(){
    return statesBundle;
  }

  public abstract void setNextState(Cells myCell, int row, int col, Grid myGrid);

  protected int quantityOfCellsOfGivenStateInCluster(int state, List<Cells> myRelevantCluster) {
    int runningCountOfState = 0;
    for (Cells eachCell : myRelevantCluster){
      if (eachCell.getCurrentState() == state) {
        runningCountOfState++;
      }
    }
    return runningCountOfState;
  }


  protected List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
    int[] xChanges = new int[]{-1,0,1};
    int[] yChanges = new int[]{-1,0,1};
    List<Cells> myCells = new ArrayList<>();
    for (int i : xChanges){
      if (rowIsValid(row + i, myGrid)){
        for (int j : yChanges){
          if (colIsValid(col + j, myGrid) && !(i == 0 && j == 0)){
            myCells.add(myGrid.getCell(row + i, col + j));
          }
        }

      }
    }
    return myCells;
  }

  protected boolean colIsValid(int col, Grid myGrid) {
    return col >=0 && col < myGrid.colLength();
  }

  protected boolean rowIsValid(int row, Grid myGrid) {
    return row >=0 && row < myGrid.rowLength();
  }


  protected int getNextState(Cells myCell) {return myCell.getMyNextState();}

  protected void consumerGenerateNextState(int currentState, Consumer<Integer> consumer){
    consumer.accept(currentState);
  }

}
