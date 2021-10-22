package cellsociety.ruleStructure;

public class SchellingSegregationRules extends CellSocietyRules{

    public SchellingSegregationRules(){
        super();
    }

    protected void prepBundles() {
        initializeRuleAndValueBundles("SchellingSegregation");
    }

    @Override
    public Integer generateNextState(int quantityOfLivingCells, int currentState) {
        return generatedStateRunThroughRules(quantityOfLivingCells, currentState);
    }
}
