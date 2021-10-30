package cellsociety.model;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class WaTorDeveloper {
    private static final String FISH = "FISH";
    private static final String SHARK = "SHARK";
    private static final String EMPTY = "EMPTY";
    private static final int REPRODUCTION_INCREASE = 1;
    private static final int REPRODUCTION_INITIAL = 0;
    private static final int REPRODUCTION_MAX = 5;
    private static final int NO_ENERGY = 0;

    public void updateReproduction(ResourceBundle statesBundle, Map<Cells, Integer> reproductionMap, Cells myCell, int reproductionIncrease, int reproductionInitial){
        if(myCell.getCurrentState() == Integer.parseInt(statesBundle.getString(SHARK)) ||
                myCell.getCurrentState() == Integer.parseInt(statesBundle.getString(FISH)) ||
                myCell.getMyNextState() == Integer.parseInt(statesBundle.getString(SHARK)) ||
                myCell.getMyNextState() == Integer.parseInt(statesBundle.getString(FISH))) {
            if (!reproductionMap.containsKey(myCell)) {
                reproductionMap.put(myCell, reproductionInitial);
            } else {
                reproductionMap.put(myCell, reproductionMap.get(myCell) + reproductionIncrease);
            }
        }
    }

    public void updateEnergy(ResourceBundle statesBundle, Map<Cells, Integer> energyMap, Cells myCell, int energyFactor, int energyInitial){
        if(!energyMap.containsKey(myCell) && (myCell.getCurrentState() == Integer.parseInt(statesBundle.getString(SHARK))
                || myCell.getMyNextState() == Integer.parseInt(statesBundle.getString(SHARK)))){
            energyMap.put(myCell,energyInitial);
        }
        else if(energyMap.containsKey(myCell)){
            energyMap.put(myCell, energyMap.get(myCell) + energyFactor);
        }
    }

    public void checkEnergy(ResourceBundle statesBundle, Map<Cells, Integer> energyMap, Cells myCell){
        if(energyMap.containsKey(myCell)) {
            if (energyMap.get(myCell) <= NO_ENERGY) {
                myCell.setMyNextState(Integer.parseInt(statesBundle.getString(EMPTY)));
                myCell.updateMyCurrentState();
                energyMap.remove(myCell);
            }
        }
    }

    public void checkReproduction(ResourceBundle statesBundle, Map<Cells, Integer> reproductionMap, Cells myCell, List<Cells> myNeighbors){
        if(reproductionMap.containsKey(myCell)) {
            if (reproductionMap.get(myCell) >= REPRODUCTION_MAX) {
                Cells c = findRightState(myCell, myNeighbors, Integer.parseInt(statesBundle.getString(EMPTY)));
                reproduceCells(c, myCell);
                reproductionMap.remove(myCell);
                updateReproduction(statesBundle, reproductionMap, c, REPRODUCTION_INCREASE, REPRODUCTION_INITIAL);
                updateReproduction(statesBundle,reproductionMap,myCell, REPRODUCTION_INCREASE, REPRODUCTION_INITIAL);
            }
        }
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
