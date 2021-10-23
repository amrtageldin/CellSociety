package cellsociety.ruleStructure;

public class GameOfLifeRules extends CellSocietyRules {
  public GameOfLifeRules(){
    super();
  }

  protected void prepBundles() {
    initializeRuleAndValueBundles("GameOfLife");
  }

  @Override
  public Integer generateNextState(int quantityOfLivingCells, int currentState) {
    return generatedStateRunThroughRules(quantityOfLivingCells, currentState);
  }
}
