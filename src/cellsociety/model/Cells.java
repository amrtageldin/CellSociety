package cellsociety.model;

import java.util.List;

public class Cells {
  public int currentState;
  public int nextState;
  public int myID;
  public Integer myNextState;


  public int getMyNextState() {
    if (myNextState == null){
      return currentState;
    }
    return myNextState;
  }

  public void setMyNextState(int myNextState) {
    this.myNextState = myNextState;
  }


  public Cells( int initialState){
    currentState = initialState;
    //this.myID = ID;
  }

  public int getCurrentState(){
    return currentState;
  }


}
