package cellsociety.ruleStructure;

public class GameOfLifeRules extends CellSocietyRules {
  public GameOfLifeRules(){
    super();
  }

  protected void prepBundles() {
    initializeRuleAndValueBundles("GameOfLife");
  }

}
