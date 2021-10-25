package cellsociety.ruleStructure;

import java.lang.reflect.Method;

public class SchellingSegregationRules extends CellSocietyRules{
    private String percent;

    public SchellingSegregationRules(){ super();}

    @Override
    protected void prepBundles() {
        initializeRuleAndValueBundles("SchellingSegregation");
    }

    protected void percentCheck(){
        parameter = "30"; //TODO: once we have input from view this will change
    }

}
