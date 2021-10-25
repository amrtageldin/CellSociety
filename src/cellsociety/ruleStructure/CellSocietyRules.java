package cellsociety.ruleStructure;

import cellsociety.rule.Rule;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

abstract public class CellSocietyRules {
    public static final String METHOD_RULE = "METHOD";
    protected List<Rule> myRules;
    protected ResourceBundle ruleBundle;
    protected ResourceBundle translationBundle;
    protected ResourceBundle valueBundle;

    protected final String ruleResourceBundleBase = "cellsociety.ruleStructure.ruleResources.";
    protected final String ruleBundleBase = "cellsociety.rule.Rule";
    protected final String modelResourceBundleBase = "cellsociety.model.resources.";

    protected String parameter;

    public CellSocietyRules(){
        myRules = new ArrayList<>();
        prepBundles();
        initializeMyRules();
    }

    protected abstract void prepBundles();

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
                    e.printStackTrace();
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
            Rule myRule = (Rule) Class.forName(
                            String.format("%s%s",ruleBundleBase, translationBundle.getString(ruleSet[0]))).
                    getConstructor(paramTypesSub).newInstance(paramValuesSub);
            myRules.add(myRule);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

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
        valueBundle = initializeBundle(modelResourceBundleBase, String.format("%sStates", base));
    }



}
