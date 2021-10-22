package cellsociety.ruleStructure;

import cellsociety.rule.Rule;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

abstract public class CellSocietyRules {
    protected List<Rule> myRules;
    protected ResourceBundle ruleBundle;
    protected ResourceBundle translationBundle;
    protected final String ruleResourceBundleBase = "cellsociety.ruleStructure.ruleResources.";

    public CellSocietyRules(){
        myRules = new ArrayList<>();
        initializeRuleBundle();
        initializeMyRules();
    }

    protected abstract void initializeRuleBundle();

    protected void initializeMyRules(){
        translationBundle = initializeBundle(ruleResourceBundleBase, "TranslationRules");

        for (String eachKey : ruleBundle.keySet()){
            String ruleString = ruleBundle.getString(eachKey);
            String[] array = ruleString.split(" ");
            Rule myRule;

            try{
                 myRule = (Rule) Class.forName(String.format("cellsociety.rule.Rule%s", translationBundle.getString(array[0]))).getConstructor().newInstance(array);
                 myRules.add(myRule);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    protected abstract Integer generateNextState(int quantityOfLivingCells, int currentState);

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




}
