package cellsociety.ruleStructure;

import cellsociety.rule.Rule;
import cellsociety.rule.RuleEqual;
import cellsociety.rule.RuleGreaterThan;
import cellsociety.rule.RuleLessThan;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeRules {
  private final int ALIVE = 1;
  private final int DEAD = 0;
  private List<Rule> myRules;

  public GameOfLifeRules(){
    myRules = new ArrayList<>();
    myRules.add(new RuleLessThan(2, DEAD));
    myRules.add(new RuleEqual(2, null));
    myRules.add(new RuleEqual(3, ALIVE));
    myRules.add(new RuleGreaterThan(3, DEAD));
  }


  public Integer generateNextState(int quantityOfLivingCells) {
    Integer productOfGeneratingState = null;
    for (Rule x : myRules){
      productOfGeneratingState = x.generateState(quantityOfLivingCells);
    }

    return productOfGeneratingState;
  }
}
