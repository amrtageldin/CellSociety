package cellsociety.ruleStructure;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Consumer;

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
//    if (currentState == Integer.parseInt(valueBundle.getString("EMPTY")) || currentState == Integer.parseInt(valueBundle.getString("BURNING"))){
//      return Integer.parseInt(valueBundle.getString("EMPTY"));
//    }
    Integer empty = Integer.parseInt(valueBundle.getString("EMPTY"));
    Integer burning = Integer.parseInt(valueBundle.getString("BURNING"));
    Map<Integer, Consumer<Integer>> intMap = Map.of(empty, integers -> Integer.parseInt(valueBundle.getString("EMPTY")),
            burning, integers -> Integer.parseInt(valueBundle.getString("EMPTY")));
    this.testNextState(currentState, intMap.get(currentState));
    return generatedStateRunThroughRules(quantityOfRelevantCells, currentState);
  }

  private void testNextState(int currentState, Consumer<Integer> consumer){
    consumer.accept(currentState);
  }

}
