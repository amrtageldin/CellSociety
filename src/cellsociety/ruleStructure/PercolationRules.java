package cellsociety.ruleStructure;

public class PercolationRules extends CellSocietyRules {
  public PercolationRules(){
    super();
  }

  @Override
  protected void prepBundles() {
    initializeRuleAndValueBundles("Percolation");
  }


}
