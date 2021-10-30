package cellsociety.model;

import cellsociety.controller.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class WaTorModel extends CellSocietyModel{
    private static final String FISH = "FISH";
    private static final String SHARK = "SHARK";
    private static final String EMPTY = "EMPTY";
    private static final String SAME = "SAME";
    private static final String MOVE = "MOVE";
    private static final int REPRODUCTION_INCREASE = 1;
    private static final int REPRODUCTION_INITIAL = 0;
    private static final int ENERGY_DECREASE = -1;
    private static final int ENERGY_INITIAL = 2;
    private static final int STEP_RESET = 1;
    private static final int STEP_BUFFER = 1;
    private static final int NO_ENERGY = 0;
    private static final int ENERGY_INCREASE = 1;
    private static final int INITIAL_MOVABLE_CELLS = 0;
    private int stepCheck = 0;
    private Map<Cells, Integer> reproductionMap = new HashMap<>();
    private Map<Cells, Integer> energyMap = new HashMap<>();
    private List<Cells> changedCells = new ArrayList<>();
    private WaTorDeveloper myDevelopmentStage = new WaTorDeveloper();

    public WaTorModel(String type) {
        super(type);
    }

    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        stepCheck++;
        myDevelopmentStage.updateReproduction(getStatesBundle(), reproductionMap, myCell, REPRODUCTION_INCREASE, REPRODUCTION_INITIAL);
        myDevelopmentStage.updateEnergy(getStatesBundle(),energyMap, myCell, ENERGY_DECREASE, ENERGY_INITIAL);
        gridCheck(myGrid.colLength() * myGrid.rowLength());
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        myDevelopmentStage.checkEnergy(getStatesBundle(), energyMap, myCell);
        updateNeighbors(myNeighbors);
        myDevelopmentStage.checkReproduction(getStatesBundle(), reproductionMap, myCell, myNeighbors);
        if(!changedCells.contains(myCell)) {
            int movableCells = findMovableCells(myCell, myNeighbors);
            checkState(myCell, getMyRules().generateNextState(movableCells, myCell.getCurrentState()), myNeighbors);
        }
    }

    private void gridCheck(int gridSize){
        if(stepCheck == gridSize + STEP_BUFFER){
            changedCells.clear();
            stepCheck = STEP_RESET;
        }
    }

    private void updateNeighbors(List<Cells> myNeighbors){
        myNeighbors.removeIf(c -> changedCells.contains(c));
    }

    private void checkState(Cells myCell, int state, List<Cells> myNeighbors){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(getStatesBundle().getString(SAME)), integers -> keepState(myCell),
                Integer.parseInt(getStatesBundle().getString(MOVE)), integers -> moveState(myCell, myNeighbors)
        );
        consumerGenerateNextState(state, intMap.get(state));
    }

    private void keepState(Cells cell){
        cell.setMyNextState(cell.getCurrentState());}

    private void moveState(Cells cell, List<Cells> myNeighbors){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(getStatesBundle().getString(FISH)),
                integers -> moveCells(myDevelopmentStage.findRightState(cell, myNeighbors, Integer.parseInt(getStatesBundle().getString(EMPTY))),
                        cell),
                Integer.parseInt(getStatesBundle().getString(SHARK)),
                integer -> eatCells(myDevelopmentStage.findRightState(cell, myNeighbors, Integer.parseInt(getStatesBundle().getString(FISH))),
                        cell, myNeighbors),
                Integer.parseInt(getStatesBundle().getString(EMPTY)), integer -> {});
        consumerGenerateNextState(cell.getCurrentState(), intMap.get(cell.getCurrentState()));
    }

    private void moveCells(Cells c, Cells cell){
        if(c == cell){
            keepState(cell);
        }
        else {
            changeNeighborCells(c, cell);
            if(energyMap.containsKey(cell)){
                myDevelopmentStage.updateEnergy(getStatesBundle(), energyMap, cell, NO_ENERGY, ENERGY_INITIAL);
                myDevelopmentStage.updateEnergy(getStatesBundle(), energyMap, c, NO_ENERGY, energyMap.get(cell));
                energyMap.remove(cell);
            }
        }
    }

    private void eatCells(Cells c, Cells cell, List<Cells> myNeighbors) {
        if(c == cell){
            moveCells(myDevelopmentStage.findRightState(cell, myNeighbors, Integer.parseInt(getStatesBundle().getString(EMPTY))), cell);
        }
        else{
            changeNeighborCells(c, cell);
            myDevelopmentStage.updateEnergy(getStatesBundle(), energyMap, cell, ENERGY_INCREASE, NO_ENERGY);
        }
    }

    private void changeNeighborCells(Cells c, Cells cell){
        c.setMyNextState(cell.getCurrentState());
        cell.setMyNextState(Integer.parseInt(getStatesBundle().getString(EMPTY)));
        changedCells.add(c);
        myDevelopmentStage.updateReproduction(getStatesBundle(), reproductionMap, c, REPRODUCTION_INITIAL, reproductionMap.get(cell));
        reproductionMap.remove(cell);
    }

    private int findMovableCells(Cells myCell, List<Cells> myNeighbors){
        int movableCells = INITIAL_MOVABLE_CELLS;
        if(myCell.getCurrentState() == Integer.parseInt(getStatesBundle().getString(FISH))){
            movableCells = quantityOfCellsOfGivenStateInCluster(Integer.parseInt(getStatesBundle().getString(EMPTY)), myNeighbors);
        }
        else if(myCell.getCurrentState() == Integer.parseInt(getStatesBundle().getString(SHARK))){
            movableCells = myNeighbors.size() - quantityOfCellsOfGivenStateInCluster(Integer.parseInt(getStatesBundle().getString(SHARK)), myNeighbors);
        }
        return movableCells;
    }
}


