package cellsociety.ruleStructure;

import cellsociety.rule.Rule;
import java.util.ArrayList;
import java.util.List;

abstract public class CellSocietyRules {
    protected List<Rule> myRules;
    
    public CellSocietyRules(){
        myRules = new ArrayList<>();
        initializeMyRules();
    }

    protected abstract void initializeMyRules();

    protected abstract Integer generateNextState(int quantityOfLivingCells, int currentState);

    protected Integer generatedStateRunThroughRules(int quantityOfPercolatedCells) {
        Integer productOfGeneratingState = null;
        for (Rule x : myRules){
            productOfGeneratingState = x.generateState(quantityOfPercolatedCells);
            if (productOfGeneratingState != null){
                break;
            }
        }

        return productOfGeneratingState;
    }

}
