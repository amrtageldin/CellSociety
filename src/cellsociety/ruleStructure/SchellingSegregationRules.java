package cellsociety.ruleStructure;

import java.lang.reflect.Method;

public class SchellingSegregationRules extends CellSocietyRules{
    private String percent;

    public SchellingSegregationRules(){ super();}

    protected void prepBundles() {
        initializeRuleAndValueBundles("SchellingSegregation");
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
            ruleSet[1] = percent;
            runThroughRules(ruleSet);
        }
    }

    private void percentCheck(){
        percent = "30"; //TODO: once we have input from view this will change
    }
    @Override
    public Integer generateNextState(int propSameCells, int currentState) {
        return generatedStateRunThroughRules(propSameCells, currentState);
    }
}
