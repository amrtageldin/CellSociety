package cellsociety.ruleStructure;

import java.util.ResourceBundle;

public class GameOfLifeRules extends CellSocietyRules {
  public GameOfLifeRules(){
    super();
  }

  protected void initializeRuleBundle() {
    ruleBundle = initializeBundle(ruleResourceBundleBase, "GameOfLifeRules");
    valueBundle = initializeBundle(ruleResourceBundleBase, "GameOfLifeValues");
  }


  @Override
  public Integer generateNextState(int quantityOfLivingCells, int currentState) {
    return generatedStateRunThroughRules(quantityOfLivingCells, currentState);
  }
}
