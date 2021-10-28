package cellsociety.model;

import cellsociety.controller.Grid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class WaTorModel extends CellSocietyModel{
    private static final int INITIAL_REPRODUCTION = 0;
    private static final int REPRODUCTION_INCREASE = 1;
    private static final String FISH = "FISH";
    private static final String SHARK = "SHARK";
    private static final String SAME = "SAME";
    private static final String MOVE = "MOVE";
    private static final int INITIAL_ENERGY = 5;
    private static final int ENERGY_DECREASE = 1;

    private Map<Cells, Integer> reproductionMap = new HashMap<>();
    private Map<Cells, Integer> energyMap = new HashMap<>();

    public WaTorModel(String type) {
        super(type);
    }


    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        checkAnimals(myCell);
        animalConditions(myCell, myNeighbors);
        int movableCells = findMovableCells(myCell, myNeighbors);
        int state;
        if(myCell.getCurrentState() != 0) {
            state = getMyRules().generateNextState(movableCells, myCell.getCurrentState());
            checkState(myCell, state, myNeighbors);
        }
        else{
            myCell.setMyNextState(0);
        }
    }
    private void checkState(Cells myCell, int state, List<Cells> myNeighbors){
        Map<Integer, Consumer<Integer>> intMap = Map.of( Integer.parseInt(getStatesBundle().getString(SAME)), integers -> keepState(myCell),
                Integer.parseInt(getStatesBundle().getString(MOVE)), integers -> moveState(myCell, myNeighbors)
        );
        consumerGenerateNextState(state, intMap.get(state));
    }
    private void keepState(Cells cell){cell.setMyNextState(cell.getCurrentState());}

    private void moveState(Cells cell, List<Cells> myNeighbors){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(getStatesBundle().getString(FISH)), integers -> moveCells(cell, myNeighbors),
                Integer.parseInt(getStatesBundle().getString(SHARK)), integer -> eatCells(cell, myNeighbors));
        consumerGenerateNextState(cell.getCurrentState(), intMap.get(cell.getCurrentState()));
    }
    private void moveCells(Cells cell, List<Cells> myNeighbors){
        Cells c = findRightState(cell, myNeighbors, 0);
        c.setMyNextState(cell.getCurrentState());
        c.updateMyCurrentState();
        cell.setMyNextState(0);
        cell.updateMyCurrentState();
        reproductionMap.put(c, reproductionMap.get(cell));
        reproductionMap.remove(cell);
        if(energyMap.containsKey(cell)){
            energyMap.put(c, energyMap.get(cell));
            energyMap.remove(cell);
        }
    }
    private void eatCells(Cells cell, List<Cells> myNeighbors) {
        Cells c = findRightState(cell, myNeighbors, 1);
        if(c == cell){
            moveCells(cell, myNeighbors);
        }
        else{
            c.setMyNextState(2);
            c.updateMyCurrentState();
            cell.setMyNextState(0);
            cell.updateMyCurrentState();
            reproductionMap.remove(c);
            reproductionMap.put(c, reproductionMap.get(cell));
            reproductionMap.remove(cell);
            energyMap.put(cell, energyMap.get(cell) + 1);
            energyMap.put(c, energyMap.get(cell));
            energyMap.remove(cell);
        }
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
        if(myCell.getCurrentState() == 1){
            movableCells = quantityOfCellsOfGivenStateInCluster(0, myNeighbors);
        }
        else if(myCell.getCurrentState() == 2){
            movableCells = quantityOfCellsOfGivenStateInCluster(1, myNeighbors);
            if(movableCells == 0){
                movableCells = quantityOfCellsOfGivenStateInCluster(0, myNeighbors);
            }
        }
        return movableCells;
    }
    private void checkAnimals(Cells myCell){
        Map<Integer, Consumer<Integer>> animalCheck =
                Map.of(Integer.parseInt(getStatesBundle().getString(SHARK)), integer -> setUpAnimalEnergy(myCell),
                        Integer.parseInt(getStatesBundle().getString(FISH)), integer -> setUpAnimalReproduction(myCell)
        ,0, integer -> {});
        consumerGenerateNextState(myCell.getCurrentState(), animalCheck.get(myCell.getCurrentState()));
    }

    private void setUpAnimalReproduction(Cells myCell){
        if(!reproductionMap.containsKey(myCell)){
            reproductionMap.put(myCell, INITIAL_REPRODUCTION);
        }
        else{
            reproductionMap.put(myCell, reproductionMap.get(myCell) + REPRODUCTION_INCREASE);
        }
    }

    private void setUpAnimalEnergy(Cells myCell){
        if (!energyMap.containsKey(myCell)) {
            energyMap.put(myCell, INITIAL_ENERGY);
        }
        else{
            energyMap.put(myCell, energyMap.get(myCell) - ENERGY_DECREASE);
        }
        setUpAnimalReproduction(myCell);
    }

    private void animalConditions(Cells myCell, List<Cells> myNeighbors){
        Map<Integer, Consumer<Integer>> animalCheck =
                Map.of(Integer.parseInt(getStatesBundle().getString(SHARK)), integer -> checkAnimalEnergy(myCell, myNeighbors),
                        Integer.parseInt(getStatesBundle().getString(FISH)), integer -> checkAnimalReproduction(myCell, myNeighbors),
                        0, integer -> {});
        consumerGenerateNextState(myCell.getCurrentState(), animalCheck.get(myCell.getCurrentState()));
    }

    private void checkAnimalEnergy(Cells myCell, List<Cells> myNeighbors){
        if(energyMap.get(myCell) == 0){
            energyMap.remove(myCell);
            reproductionMap.remove(myCell);
            myCell.setMyNextState(0);
            myCell.updateMyCurrentState();
        }
        checkAnimalReproduction(myCell, myNeighbors);
    }

    private void checkAnimalReproduction(Cells myCell, List<Cells> myNeighbors){
        if(reproductionMap.get(myCell) == 5){
            Cells c = findRightState(myCell, myNeighbors, 0);
            c.setMyNextState(myCell.getCurrentState());
            c.updateMyCurrentState();
            reproductionMap.put(c, INITIAL_REPRODUCTION);
            myCell.setMyNextState(myCell.getCurrentState());
            reproductionMap.remove(myCell);
            myCell.updateMyCurrentState();
        }
    }
}


