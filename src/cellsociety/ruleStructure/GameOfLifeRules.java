package cellsociety.ruleStructure;

import java.util.ResourceBundle;

public class GameOfLifeRules extends CellSocietyRules {
  private final int ALIVE = 1;
  private final int DEAD = 0;

  public GameOfLifeRules(){
    super();
  }

  protected void initializeRuleBundle() {
    ruleBundle = initializeBundle(ruleResourceBundleBase, "GameOfLifeRules");
  }


  @Override
  public Integer generateNextState(int quantityOfLivingCells, int currentState) {
    return generatedStateRunThroughRules(quantityOfLivingCells, currentState);
  }
}
