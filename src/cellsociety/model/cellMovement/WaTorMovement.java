package cellsociety.model.cellMovement;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class WaTorMovement implements CellSocietyMovement {
    private static final String FISH = "FISH";
    private static final String SHARK = "SHARK";
    private static final String EMPTY = "EMPTY";
    private static final String SAME = "SAME";
    private static final String MOVE = "MOVE";
    private static final int NO_ENERGY = 0;
    private static final int ENERGY_INCREASE = 1;
    private List<Cells> changedCells;
    private WaTorDeveloper myDevelopmentStage;
    private List<Cells> myNeighbors;
    private ResourceBundle myStatesBundle;

    public WaTorMovement(){
        myDevelopmentStage = new WaTorDeveloper();
        changedCells = new ArrayList<>();
    }

    public void setInitialParameters(Cells cell, Grid grid, List<Cells> neighbors, ResourceBundle statesBundle){
        myNeighbors = neighbors;
        myStatesBundle = statesBundle;
        myDevelopmentStage.setResourceBundle(myStatesBundle);
        myDevelopmentStage.initializeDevelopment(cell);
        myDevelopmentStage.checkDevelopment(cell, myNeighbors);
    }


    public List<Cells> getStep() {
        return changedCells;
    }

    public void checkState(Cells myCell, int state){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(myStatesBundle.getString(SAME)), integers -> keepState(myCell),
                Integer.parseInt(myStatesBundle.getString(MOVE)), integers -> moveState(myCell)
        );
        consumerNextState(state, intMap.get(state));
    }

    public void keepState(Cells cell){
        cell.setMyNextState(cell.getCurrentState());}

    public void moveState(Cells cell){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(myStatesBundle.getString(FISH)),
                integers -> moveCells(myDevelopmentStage.findRightState(cell, myNeighbors, Integer.parseInt(myStatesBundle.getString(EMPTY))),
                        cell),
                Integer.parseInt(myStatesBundle.getString(SHARK)),
                integer -> eatCells(myDevelopmentStage.findRightState(cell, myNeighbors, Integer.parseInt(myStatesBundle.getString(FISH))),
                        cell),
                Integer.parseInt(myStatesBundle.getString(EMPTY)), integer -> {});
        consumerNextState(cell.getCurrentState(), intMap.get(cell.getCurrentState()));
    }

    private void moveCells(Cells c, Cells cell){
        if(c == cell){
            keepState(cell);
            return;
        }
        changeNeighborCells(c, cell);
        myDevelopmentStage.energyVerification(cell, c);
    }

    private void eatCells(Cells c, Cells cell) {
        if(c == cell){
            moveCells(myDevelopmentStage.findRightState(cell, myNeighbors, Integer.parseInt(myStatesBundle.getString(EMPTY))), cell);
            return;
        }
        changeNeighborCells(c, cell);
        myDevelopmentStage.updateEnergy(cell, ENERGY_INCREASE, NO_ENERGY);
    }

    private void changeNeighborCells(Cells c, Cells cell){
        c.setMyNextState(cell.getCurrentState());
        cell.setMyNextState(Integer.parseInt(myStatesBundle.getString(EMPTY)));
        changedCells.add(c);
        myDevelopmentStage.reproductionVerification(cell, c);
    }

    private void consumerNextState(int currentState, Consumer<Integer> consumer){
        consumer.accept(currentState);
    }
}
