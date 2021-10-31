package cellsociety.model.cellMovement;

import cellsociety.model.Cells;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class WaTorDeveloper {
    private static final String FISH = "FISH";
    private static final String SHARK = "SHARK";
    private static final String EMPTY = "EMPTY";
    private static final int REPRODUCTION_INCREASE = 1;
    private static final int REPRODUCTION_INITIAL = 0;
    private static final int NO_ENERGY = 0;
    private static final int ENERGY_DECREASE = -1;
    public static final String REPRODUCTION_KEY = "ReproductionMax";
    public static final String ENERGY_KEY = "EnergyInitial";
    private int REPRODUCTION_MAX;
    private int ENERGY_INITIAL;
    private ResourceBundle statesBundle;
    private Map<Cells, Integer> reproductionMap = new HashMap<>();
    private Map<Cells, Integer> energyMap = new HashMap<>();

    public void setResourceBundle(ResourceBundle bundle){
        statesBundle = bundle;
    }

    public void initializeDevelopment(Cells myCell, Map<String, String> parameters){
        REPRODUCTION_MAX = Integer.parseInt(parameters.get(REPRODUCTION_KEY));
        ENERGY_INITIAL = Integer.parseInt(parameters.get(ENERGY_KEY));
        updateReproduction(myCell, REPRODUCTION_INCREASE, REPRODUCTION_INITIAL);
        updateEnergy(myCell, ENERGY_DECREASE, ENERGY_INITIAL);
    }

    private void updateReproduction(Cells myCell, int reproductionIncrease, int reproductionInitial){
        if(myCellStateEquals(myCell.getCurrentState(), SHARK) ||
            myCellStateEquals(myCell.getCurrentState(), FISH) ||
            myCellStateEquals(myCell.getMyNextState(), SHARK) ||
            myCellStateEquals(myCell.getMyNextState(), FISH)) {
            if (!reproductionMap.containsKey(myCell)) {
                reproductionMap.put(myCell, reproductionInitial);
                return;
            }
            reproductionMap.put(myCell, reproductionMap.get(myCell) + reproductionIncrease);
        }
        if(myCellStateEquals(myCell.getMyNextState(), EMPTY)){
            reproductionMap.remove(myCell);
        }
    }

    private boolean myCellStateEquals(int myCellState, String givenState) {
        return myCellState == Integer.parseInt(statesBundle.getString(givenState));
    }


    public void energyVerification(Cells cell, Cells c){
        if(energyMap.containsKey(cell)){
            updateEnergy(cell, NO_ENERGY, ENERGY_INITIAL);
            updateEnergy(c, NO_ENERGY, energyMap.get(cell));
            energyMap.remove(cell);
        }
    }

    public void updateEnergy(Cells myCell, int energyFactor, int energyInitial){
        if(!energyMap.containsKey(myCell) && (
            myCellStateEquals(myCell.getCurrentState(), SHARK)
                || myCellStateEquals(myCell.getMyNextState(), SHARK))){
            energyMap.put(myCell,energyInitial);
        }
        else if(energyMap.containsKey(myCell)){
            energyMap.put(myCell, energyMap.get(myCell) + energyFactor);
        }
    }

    public void checkDevelopment(Cells myCell, List<Cells> myNeighbors){
        checkEnergy(myCell);
        checkReproduction(myCell, myNeighbors);
    }

    private void checkEnergy(Cells myCell){
        if(energyMap.containsKey(myCell)) {
            if (energyMap.get(myCell) <= NO_ENERGY) {
                outOfEnergy(myCell);
            }
        }
    }

    private void outOfEnergy(Cells myCell) {
        myCell.setMyNextState(Integer.parseInt(statesBundle.getString(EMPTY)));
        myCell.updateMyCurrentState();
        energyMap.remove(myCell);
    }

    public void reproductionVerification(Cells cell, Cells c){
        updateReproduction(c, REPRODUCTION_INITIAL, reproductionMap.get(cell));
        reproductionMap.remove(cell);
    }

    private void checkReproduction(Cells myCell, List<Cells> myNeighbors){
        if(reproductionMap.containsKey(myCell)) {
            if (reproductionMap.get(myCell) >= REPRODUCTION_MAX) {
                reachedReproductionMax(myCell, myNeighbors);
            }
        }
    }

    private void reachedReproductionMax(Cells myCell, List<Cells> myNeighbors) {
        Cells c = findRightState(myCell, myNeighbors, Integer.parseInt(statesBundle.getString(EMPTY)));
        reproduceCells(c, myCell);
        reproductionMap.remove(myCell);
        updateReproduction(c, REPRODUCTION_INCREASE, REPRODUCTION_INITIAL);
        updateReproduction(myCell, REPRODUCTION_INCREASE, REPRODUCTION_INITIAL);
    }

    public void reproduceCells(Cells c, Cells myCell){
        c.setMyNextState(myCell.getCurrentState());
        c.updateMyCurrentState();
    }

    public Cells findRightState(Cells cell, List<Cells> myNeighbors, int stateWanted){
        for(Cells c : myNeighbors){
            if(c.getCurrentState() == stateWanted){
                return c;
            }
        }
        return cell;
    }
}
