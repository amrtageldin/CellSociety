package cellsociety.ruleStructure;

import cellsociety.rule.Rule;
import cellsociety.rule.RuleEqual;
import cellsociety.rule.RuleGreaterThan;
import cellsociety.rule.RuleLessThan;

import java.util.List;

public class GameOfLifeRules extends CellSocietyRules {
  private final int ALIVE = 1;
  private final int DEAD = 0;

  public GameOfLifeRules(){
    super();
  }

  @Override
  protected void initializeMyRules() {
    myRules.addAll(List.of(new RuleLessThan(2, DEAD),
                    new RuleEqual(2, null),
            new RuleEqual(3, ALIVE),new RuleGreaterThan(3, DEAD)));
  }


  @Override
  public Integer generateNextState(int quantityOfLivingCells, int currentState) {
    return generatedStateRunThroughRules(quantityOfLivingCells, currentState);
  }
}
