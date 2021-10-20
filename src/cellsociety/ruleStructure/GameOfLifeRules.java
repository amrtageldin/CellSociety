package cellsociety.ruleStructure;

import cellsociety.rule.Rule;
import cellsociety.rule.RuleEqual;
import cellsociety.rule.RuleGreaterThan;
import cellsociety.rule.RuleLessThan;

public class GameOfLifeRules extends CellSocietyRules {
  private final int ALIVE = 1;
  private final int DEAD = 0;

  public GameOfLifeRules(){
    super();
  }

  @Override
  protected void initializeMyRules() {
    myRules.add(new RuleLessThan(2, DEAD));
    myRules.add(new RuleEqual(2, null));
    myRules.add(new RuleEqual(3, ALIVE));
    myRules.add(new RuleGreaterThan(3, DEAD));
  }


  @Override
  public Integer generateNextState(int quantityOfLivingCells, int currentState) {
    return generatedStateRunThroughRules(quantityOfLivingCells, currentState);
  }
}
