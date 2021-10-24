package cellsociety.ruleStructure;

import java.lang.reflect.Method;

public class FireRules extends CellSocietyRules {
  private String probPercent;

  public FireRules(){
    super();
  }

  @Override
  protected void initializeMyRules(){
    translationBundle = initializeBundle(ruleResourceBundleBase, "TranslationRules");

    for (String eachKey : ruleBundle.keySet()){
      String ruleString = ruleBundle.getString(eachKey);
      String[]ruleSet = ruleString.split(" ");

      try{
        Method method = this.getClass().getDeclaredMethod(ruleSet[1]);
        method.invoke(this);
      }
      catch (Exception e){
        e.printStackTrace();
      }

      ruleSet[1] = probPercent;
      runThroughRules(ruleSet);
    }
  }

  private void probabilityCheck(){
    probPercent = "30"; // changeLater
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
