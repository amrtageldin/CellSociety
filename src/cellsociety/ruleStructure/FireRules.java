package cellsociety.ruleStructure;

public class FireRules extends CellSocietyRules {
  public FireRules(){
    super();
  }

  protected void prepBundles() {
    initializeRuleAndValueBundles("Fire");
  }

  @Override
  public Integer generateNextState(int quantityOfRelevantCells, int currentState) {
    if (currentState == Integer.parseInt(valueBundle.getString("EMPTY")) || currentState == Integer.parseInt(valueBundle.getString("BURNING"))){
      return Integer.parseInt(valueBundle.getString("EMPTY"));
    }

    return generatedStateRunThroughRules(quantityOfRelevantCells, currentState);
  }


}
