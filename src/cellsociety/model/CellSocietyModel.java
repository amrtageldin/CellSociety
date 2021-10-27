package cellsociety.model;


import cellsociety.ruleStructure.CellSocietyRules;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public abstract class CellSocietyModel {
  protected CellSocietyRules myRules;
  protected ResourceBundle statesBundle;
  protected final String modelResourceBundleBase = "cellsociety.model.resources.";
  public static final int SCALE_FACTOR = 100;


  public CellSocietyModel(String myType){
    try{
      Class [] paramTypesSub = {String.class};
      Object [] paramValuesSub = {myType};
      myRules = (CellSocietyRules) Class.forName(String.format("cellsociety.ruleStructure.%sRules", myType)).getConstructor(paramTypesSub).newInstance(paramValuesSub);
      statesBundle = ResourceBundle.getBundle(String.format("%s%sStates", modelResourceBundleBase, myType));
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  public abstract void setNextState(Cells myCell, int row, int col, Cells[][] myGrid);

  protected int quantityOfCellsOfGivenStateInCluster(int state, List<Cells> myRelevantCluster) {
    int runningCountOfState = 0;
    for (Cells eachCell : myRelevantCluster){
      if (eachCell.getCurrentState() == state) {
        runningCountOfState++;
      }
    }
    return runningCountOfState;
  }


  protected List<Cells> generateNeighbors(int row, int col, Cells[][] myGrid) {
    int[] xChanges = new int[]{-1,0,1};
    int[] yChanges = new int[]{-1,0,1};
    List<Cells> myCells = new ArrayList<>();
    for (int i : xChanges){
      if (rowIsValid(row + i, myGrid)){
        for (int j : yChanges){
          if (colIsValid(col + j, myGrid) && !(i == 0 && j == 0)){
            myCells.add(myGrid[row + i][ col + j]);
          }
        }

      }
    }
    return myCells;
  }

  protected boolean colIsValid(int col, Cells[][] myGrid) {
    return col >=0 && col < myGrid[0].length;
  }

  protected boolean rowIsValid(int row, Cells[][] myGrid) {
    return row >=0 && row < myGrid.length;
  }


  protected int getNextState(Cells myCell) {return myCell.getMyNextState();}

  protected void consumerGenerateNextState(int currentState, Consumer<Integer> consumer){
    consumer.accept(currentState);
  }

}
