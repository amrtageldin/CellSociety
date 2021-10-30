package cellsociety.ruleStructure;


public class SchellingSegregationRules extends CellSocietyRules{
    private static final String PERCENT = "Percent";
    private String percent;

    public SchellingSegregationRules(String myType){ super(myType);}

    protected void percentCheck(){
        parameter = getMyParameters().get(PERCENT);
    }

}
