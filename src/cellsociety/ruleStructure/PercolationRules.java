package cellsociety.ruleStructure;

public class PercolationRules extends CellSocietyRules {
  public PercolationRules(){
    super();
  }

  protected void prepBundles() {
    initializeRuleAndValueBundles("Percolation");
  }

  @Override
  public Integer generateNextState(int quantityOfPercolatedCells, int currentState) {
    if (currentState != Integer.parseInt(valueBundle.getString("OPEN"))){
      return currentState;
    }

    return generatedStateRunThroughRules(quantityOfPercolatedCells, currentState);
  }


}
