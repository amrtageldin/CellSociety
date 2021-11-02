package cellsociety.ruleStructure;

import java.lang.reflect.Method;

public class SchellingSegregationRules extends CellSocietyRules{
    private String percent;

    public SchellingSegregationRules(String myType){ super(myType);}

    protected void percentCheck(){
        parameter = "30"; //TODO: once we have input from view this will change
    }

}
