package cellsociety.ruleStructure;

import cellsociety.Errors.ErrorFactory;
import cellsociety.rule.Rule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


/**
 * Cell Society Rules; this is the abstract class that uses a resource bundle file to generate the
 * 'rule' attributes associated with a model
 */

abstract public class CellSocietyRules {
  private static final String METHOD_RULE = "METHOD";
  private static final String RULE_ERROR = "RuleError";
  private final List<Rule> myRules;
  private ResourceBundle ruleBundle;
  private ResourceBundle translationBundle;
  private ResourceBundle valueBundle;
  private Map<String, String> myParametersMap;
  private ErrorFactory myErrorFactory;

  private final String ruleResourceBundleBase = "cellsociety.ruleStructure.ruleResources.";

  protected String parameter;


  /**
   * Initialize rules, parameterMap, errorFactory, and value bundles
   *
   * @param myType     the Type of program, ex: "Fire", "GameOfLife"
   * @param parameters store parameters, such as type of neighbors or percent
   */

  public CellSocietyRules(String myType, Map<String, String> parameters){
    myRules = new ArrayList<>();
    myParametersMap = parameters;
    myErrorFactory = new ErrorFactory();
    initializeRuleAndValueBundles(myType);
    initializeMyRules();
  }


  protected Map<String, String> getMyParameters(){
    return myParametersMap;
  }

  protected void initializeMyRules(){
    translationBundle = initializeBundle(ruleResourceBundleBase, "TranslationRules");
    for (String eachKey : ruleBundle.keySet()){
      String ruleString = ruleBundle.getString(eachKey);
      String[]ruleSet = ruleString.split(" ");
      if(eachKey.contains(METHOD_RULE)){
        try{
          Method method = this.getClass().getDeclaredMethod(ruleSet[1]);
          method.invoke(this);
        }
        catch (Exception e){
          myErrorFactory.updateError(RULE_ERROR);
        }
        ruleSet[1] = parameter;
      }
      runThroughRules(ruleSet);
    }
  }

  protected void runThroughRules(String[] ruleSet){
    Class [] paramTypesSub = {int.class, Integer.class};
    Object [] paramValuesSub = {Integer.parseInt(ruleSet[1]), Integer.parseInt(valueBundle.getString(ruleSet[2]))};

    try{
      String ruleBundleBase = "cellsociety.rule.Rule";
      Rule myRule = (Rule) Class.forName(
              String.format("%s%s", ruleBundleBase, translationBundle.getString(ruleSet[0]))).
          getConstructor(paramTypesSub).newInstance(paramValuesSub);
      myRules.add(myRule);
    }
    catch (Exception e){
      myErrorFactory.updateError(RULE_ERROR);
    }

  }

  /**
   * Return the new state for the cell we're looking at
   *
   * @param quantityOfLivingCells the number of cells we've previously deemed meet a condition
   * @param currentState            the current state of the cell
   * @return the new state of the cell
   */

  public Integer generateNextState(int quantityOfLivingCells, int currentState) {
    return generatedStateRunThroughRules(quantityOfLivingCells, currentState);
  }

  protected Integer generatedStateRunThroughRules(int quantityOfPercolatedCells, int currentState) {
    for (Rule x : myRules){
      Integer ruleOutcome = x.generateState(quantityOfPercolatedCells);
      if (ruleOutcome != null){
        return ruleOutcome;
      }
    }
    return currentState;
  }

  protected ResourceBundle initializeBundle(String bundleBase, String packageName) {
    return ResourceBundle.getBundle(String.format("%s%s", bundleBase,packageName));
  }

  protected void initializeRuleAndValueBundles(String base) {
    ruleBundle = initializeBundle(ruleResourceBundleBase, String.format("%sRules", base));
    String modelResourceBundleBase = "cellsociety.model.resources.";
    valueBundle = initializeBundle(modelResourceBundleBase, String.format("%sStates", base));
  }

  /**
   * If an error is reached, use this to call on that error
   *
   * @return The errorFactory/message
   */

  public ErrorFactory getMyErrorFactory(){ return myErrorFactory;}

}
