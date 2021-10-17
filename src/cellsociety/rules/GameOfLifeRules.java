package cellsociety.rules;

import javax.swing.JSpinner.DateEditor;

public class GameOfLifeRules {
  private final int ALIVE = 1;
  private final int DEAD = 0;

  public GameOfLifeRules(){
    System.out.println("2");
  }


  public int generateNextState(int quantityOfLivingCells) {
    if (quantityOfLivingCells < 2){
      return DEAD;
    }
    else if (quantityOfLivingCells == 3){
      return ALIVE;
    }
    else if (quantityOfLivingCells >= 4){
      return DEAD;
    }

  }
}
