package cellsociety.ruleStructure;


import java.util.Map;

public class SchellingSegregationRules extends CellSocietyRules{
    private static final String PERCENT = "Percent";
    private String percent;

    public SchellingSegregationRules(String type, Map<String, String> parameters) { super(type, parameters);}

    protected void percentCheck(){
        parameter = getMyParameters().get(PERCENT);
    }

}
