package cellsociety.ruleStructure;

import cellsociety.rule.RuleGreaterThan;
import java.util.ResourceBundle;

public class PercolationRules extends CellSocietyRules {
  private final int OPEN = 3;
  private final int CLOSED = 0;
  private final int PERCOLATED = 2;

  public PercolationRules(){
    super();
  }

  protected void initializeRuleBundle() {
    ruleBundle = initializeBundle(ruleResourceBundleBase, "PercolationRules");
  }


  @Override
  public Integer generateNextState(int quantityOfPercolatedCells, int currentState) {
    if (currentState != OPEN){
      return currentState;
    }

    return generatedStateRunThroughRules(quantityOfPercolatedCells, currentState);
  }


}
