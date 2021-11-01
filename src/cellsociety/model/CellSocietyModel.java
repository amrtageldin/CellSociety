package cellsociety.model;


import cellsociety.Errors.ErrorFactory;
import cellsociety.controller.Grid;
import cellsociety.neighbors.CellSocietyNeighbors;
import cellsociety.ruleStructure.CellSocietyRules;

import java.util.*;
import java.util.function.Consumer;

public abstract class CellSocietyModel {
  private static final String GAME_ERROR = "GameError";
  private CellSocietyRules myRules;
  private CellSocietyNeighbors myNeighbors;
  private ResourceBundle statesBundle;
  private Map<String, String> myParametersMap;
  private ErrorFactory myErrorFactory;


  public CellSocietyModel(String myType, Map<String, String> parameters){
    myParametersMap = parameters;
    myErrorFactory = new ErrorFactory();
    try{
      Object [] paramValuesSub = {myType, myParametersMap};
      myRules = (CellSocietyRules) Class.forName(String.format("cellsociety.ruleStructure.%sRules", myType)).getConstructor(String.class, Map.class).newInstance(paramValuesSub);
      myNeighbors = (CellSocietyNeighbors) Class.forName(String.format("cellsociety.neighbors.%s", myParametersMap.get("Neighbors"))).getConstructor().newInstance();
      String modelResourceBundleBase = "cellsociety.model.resources.";
      statesBundle = ResourceBundle.getBundle(String.format("%s%sStates", modelResourceBundleBase, myType));
    }
    catch (Exception e){
      myErrorFactory.updateError(GAME_ERROR);
    }
  }


  public Map<String, String> getMyParameters(){
    return myParametersMap;
  }

  public CellSocietyNeighbors getMyNeighbors() {return myNeighbors;}

  public CellSocietyRules getMyRules(){
    if(myRules.getMyErrorFactory().errorExists()){
      myErrorFactory.updateError(myRules.getMyErrorFactory().getErrorKey());
    }
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


  protected int getNextState(Cells myCell) {return myCell.getMyNextState();}

  protected void consumerGenerateNextState(int currentState, Consumer<Integer> consumer){
    try {
      consumer.accept(currentState);
    }
    catch (NullPointerException e){
      myErrorFactory.updateError(GAME_ERROR);
    }
  }

  public ErrorFactory getMyErrorFactory(){
    return myErrorFactory;
  }

}
