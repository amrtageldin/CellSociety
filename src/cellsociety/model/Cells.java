package cellsociety.model;

import java.util.List;

public class Cells {
  public int currentState;
  public int nextState;
  public int myID;


  public Cells( int initialState){
    currentState = initialState;
    //this.myID = ID;
  }

  public void updateState(List<Cells> myNeighbors){

 }

  public int getNextState(){
    return 1;
  }

  public int getCurrentState(){
    return currentState;
  }


}
