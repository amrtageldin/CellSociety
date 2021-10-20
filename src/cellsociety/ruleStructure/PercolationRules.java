package cellsociety.ruleStructure;

import cellsociety.rule.Rule;
import cellsociety.rule.RuleGreaterThan;
import java.util.ArrayList;
import java.util.List;

public class PercolationRules extends CellSocietyRules {
  private final int OPEN = 3;
  private final int CLOSED = 0;
  private final int PERCOLATED = 2;
  private List<Rule> myRules;

  public PercolationRules(){
    myRules = new ArrayList<>();
    myRules.add(new RuleGreaterThan(0, PERCOLATED));
  }


  @Override
  public Integer generateNextState(int quantityOfPercolatedCells, int currentState) {
    if (currentState != OPEN){
      return currentState;
    }

    Integer productOfGeneratingState = null;
    for (Rule x : myRules){
      productOfGeneratingState = x.generateState(quantityOfPercolatedCells);
      if (productOfGeneratingState != null){
        break;
      }
    }

    return productOfGeneratingState;
  }
}
