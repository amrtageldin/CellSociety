package cellsociety.ruleStructure;

public class GameOfLifeRules extends CellSocietyRules {
  public GameOfLifeRules(){
    super();
  }

  @Override
  protected void prepBundles() {
    initializeRuleAndValueBundles("GameOfLife");
  }

}
