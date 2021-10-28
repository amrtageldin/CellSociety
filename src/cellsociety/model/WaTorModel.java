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
    private int stepCheck = 0;
    private Map<Cells, Integer> reproductionMap = new HashMap<>();
    private Map<Cells, Integer> energyMap = new HashMap<>();
    private List<Cells> changedCells = new ArrayList<>();

    public WaTorModel(String type) {
        super(type);

    }


    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        stepCheck++;
        updateReproduction(myCell, 1, 0);
        updateEnergy(myCell, -1, 1);
        int gridSize = myGrid.colLength() * myGrid.rowLength();
        if(stepCheck == gridSize + 1){
            changedCells.clear();
            stepCheck = 1;
        }
        if(!changedCells.contains(myCell)) {
            List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
            checkEnergy(myCell);
            int movableCells = findMovableCells(myCell, myNeighbors);
            updateNeighbors(myNeighbors);
            checkReproduction(myCell, myNeighbors);
            int state = getMyRules().generateNextState(movableCells, myCell.getCurrentState());
            checkState(myCell, state, myNeighbors);
        }
    }

    private void updateNeighbors(List<Cells> myNeighbors){
        myNeighbors.removeIf(c -> changedCells.contains(c));
    }

    private void updateReproduction(Cells myCell, int reproductionIncrease, int reproductionInitial){
        if(!reproductionMap.containsKey(myCell)){
            reproductionMap.put(myCell, reproductionInitial);
        }
        else{
            reproductionMap.put(myCell, reproductionMap.get(myCell) + reproductionIncrease);
        }
    }

    private void updateEnergy(Cells myCell, int energyFactor, int energyInitial){
        if(!energyMap.containsKey(myCell) && (myCell.getCurrentState() == Integer.parseInt(getStatesBundle().getString(SHARK))
        || myCell.getMyNextState() == Integer.parseInt(getStatesBundle().getString(SHARK)))){
            energyMap.put(myCell,energyInitial);
        }
        else if(energyMap.containsKey(myCell)){
            energyMap.put(myCell, energyMap.get(myCell) + energyFactor);
        }
    }
    private void checkReproduction(Cells myCell, List<Cells> myNeighbors){
        if(reproductionMap.get(myCell) == 6){
            Cells c = findRightState(myCell, myNeighbors, Integer.parseInt(getStatesBundle().getString(EMPTY)));
            moveCells(c, myCell);
            reproductionMap.remove(myCell);
            updateReproduction(c, 1, 0);
            updateReproduction(myCell, 1, 0);
        }
    }

    private void checkEnergy(Cells myCell){
        if(energyMap.containsKey(myCell)) {
            if (energyMap.get(myCell) <= 0) {
                myCell.setMyNextState(Integer.parseInt(getStatesBundle().getString(EMPTY)));
                myCell.updateMyCurrentState();
                energyMap.remove(myCell);
            }
        }
    }
    private void checkState(Cells myCell, int state, List<Cells> myNeighbors){
        Map<Integer, Consumer<Integer>> intMap = Map.of( Integer.parseInt(getStatesBundle().getString(SAME)), integers -> keepState(myCell),
                Integer.parseInt(getStatesBundle().getString(MOVE)), integers -> moveState(myCell, myNeighbors)
        );
        consumerGenerateNextState(state, intMap.get(state));
    }
    private void keepState(Cells cell){
        cell.setMyNextState(cell.getCurrentState());}

    private void moveState(Cells cell, List<Cells> myNeighbors){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(getStatesBundle().getString(FISH)),
                integers -> moveCells(findRightState(cell, myNeighbors, Integer.parseInt(getStatesBundle().getString(EMPTY))),
                        cell),
                Integer.parseInt(getStatesBundle().getString(SHARK)),
                integer -> eatCells(findRightState(cell, myNeighbors, Integer.parseInt(getStatesBundle().getString(FISH))),
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
                updateEnergy(cell, 0, 1);
                updateEnergy(c, 0, energyMap.get(cell));
                energyMap.remove(cell);
            }

        }

    }
    private void eatCells(Cells c, Cells cell, List<Cells> myNeighbors) {
        if(c == cell){
            moveCells(findRightState(cell, myNeighbors, Integer.parseInt(getStatesBundle().getString(EMPTY))), cell);
        }
        else{
            changeNeighborCells(c, cell);
            updateEnergy(cell, 1, 0);
        }
    }

    private void changeNeighborCells(Cells c, Cells cell){
        c.setMyNextState(cell.getCurrentState());
        cell.setMyNextState(Integer.parseInt(getStatesBundle().getString(EMPTY)));
        changedCells.add(c);
        updateReproduction(c, 0, reproductionMap.get(cell));
        reproductionMap.remove(cell);

    }

    private Cells findRightState(Cells cell, List<Cells> myNeighbors, int stateWanted){
        for(Cells c : myNeighbors){
            if(c.getCurrentState() == stateWanted){
                return c;
            }
        }
        return cell;
    }

    private int findMovableCells(Cells myCell, List<Cells> myNeighbors){
        int movableCells = 0;
        if(myCell.getCurrentState() == Integer.parseInt(getStatesBundle().getString(FISH))){
            movableCells = quantityOfCellsOfGivenStateInCluster(Integer.parseInt(getStatesBundle().getString(EMPTY)), myNeighbors);
        }
        else if(myCell.getCurrentState() == Integer.parseInt(getStatesBundle().getString(SHARK))){
            movableCells = myNeighbors.size() - quantityOfCellsOfGivenStateInCluster(Integer.parseInt(getStatesBundle().getString(SHARK)), myNeighbors);
        }
        return movableCells;
    }




}


