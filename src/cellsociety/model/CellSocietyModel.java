package cellsociety.model;


import cellsociety.Errors.ErrorFactory;
import cellsociety.controller.Grid;
import cellsociety.model.neighbors.*;
import cellsociety.ruleStructure.CellSocietyRules;

import java.util.*;
import java.util.function.Consumer;

/**
 * This is the central abstract model class for the CellSociety game. It uses
 * reflection to determine which game is being played, and loads the relevant model
 * class for that game. This class takes a cell and determines its next state by using
 * the Rules and ruleStructure packages. The model always has the most up to date data
 * about a cell's current and next states.
 */
public abstract class CellSocietyModel {
  private static final String GAME_ERROR = "GameError";
  private CellSocietyRules myRules;
  private CellSocietyNeighbors myNeighbors;
  private ResourceBundle statesBundle;
  private final Map<String, String> myParametersMap;
  private final ErrorFactory myErrorFactory;


  /**
   * This is the constructor for the abstract CellSocietyModel. It sets up the relevant
   * parameters needed for the game by using the parameters hash map. This map has information
   * about the type of game that is being played, the types of neighbors we want for the game, and
   * any relevant parameters that may be needed for specific games. This constructor
   * uses reflection to initialize the relevant rules and neighbor classes based on the game
   * type and values within the parameters map. It also initializes the error factory and resource
   * bundles for the model.
   * @param myType: The game string with the right game class that needs to be loaded using reflection
   * @param parameters: A map that contains all of the relevant parameters for a given game as read
   *                  from the .sim file in the controller.
   */
  public CellSocietyModel(String myType, Map<String, String> parameters){
    myParametersMap = parameters;
    myErrorFactory = new ErrorFactory();
    try{
      Object [] paramValuesSub = {myType, myParametersMap};
      myRules = (CellSocietyRules) Class.forName(String.format("cellsociety.ruleStructure.%sRules", myType)).getConstructor(String.class, Map.class).newInstance(paramValuesSub);
      myNeighbors = (CellSocietyNeighbors) Class.forName(String.format("cellsociety.model.neighbors.%s", myParametersMap.get("Neighbors"))).getConstructor().newInstance();
      String modelResourceBundleBase = "cellsociety.model.resources.";
      statesBundle = ResourceBundle.getBundle(String.format("%s%sStates", modelResourceBundleBase, myType));
    }
    catch (Exception e){
      myErrorFactory.updateError(GAME_ERROR);
    }
  }


  protected Map<String, String> getMyParameters(){
    return myParametersMap;
  }

  protected CellSocietyNeighbors getMyNeighbors() {return myNeighbors;}

  protected CellSocietyRules getMyRules(){
    if(myRules.getMyErrorFactory().errorExists()){
      myErrorFactory.updateError(myRules.getMyErrorFactory().getErrorKey());
    }
    return myRules;
  }

  protected ResourceBundle getStatesBundle(){
    return statesBundle;
  }

  /**
   * This is an abstract method that is implemented differently in each game specific
   * model class. It takes in the current cell, and runs all of the relevant game rules on
   * it to determine what the cell's next state should be based on its surrounding cells.
   * @param myCell: the current cell being considered in the state-changing process
   * @param row: the row number of the current cell
   * @param col: the column number of the current cell
   * @param myGrid: the grid of all the cells, we use the current cell's row and column
   *              to determine its neighbors from this grid.
   */
  public abstract void setNextState(Cells myCell, int row, int col, Grid myGrid);

  protected int quantityOfCellsOfGivenStateInCluster(int state, List<Cells> myRelevantCluster) {
    int runningCountOfState = 0;
    try {
      for (Cells eachCell : myRelevantCluster) {
        if (eachCell.getCurrentState() == state) {
          runningCountOfState++;
        }
      }
    }
    catch(Exception e){
      myErrorFactory.updateError(GAME_ERROR);
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

  /**
   * This method returns the CellSocietyModel class's instance of ErrorFactory,
   * which holds information as to whether or not an error exists, and if it does
   * exist - what the error is. It is used by the controller to keep track of any errors that
   * show up while determining cell states.
   * @return myErrorFactory: the error information for the CellSocietyModel class
   */
  public ErrorFactory getMyErrorFactory(){
    return myErrorFactory;
  }

  protected List<Cells> neighborGenerator(int row, int col, Grid myGrid) {
    return getMyNeighbors().generateNeighbors(row,col, myGrid);
  }

  protected Integer bundleToInteger(String myString){
    return Integer.parseInt(getStatesBundle().getString(myString));
  }

}
