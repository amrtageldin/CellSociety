package cellsociety.ruleStructure;

public class WaTorRules extends CellSocietyRules{
    public WaTorRules(){
        super();
    }

    protected void prepBundles() {
        initializeRuleAndValueBundles("WaTor");
    }

    @Override
    public Integer generateNextState(int quantityOfLivingCells, int currentState) {
        return generatedStateRunThroughRules(quantityOfLivingCells, currentState);
    }
}
