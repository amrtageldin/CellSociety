package cellsociety.model;

import cellsociety.controller.Grid;
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

    private Map<Cells, Integer> reproductionMap = new HashMap<>();
    private Map<Cells, Integer> energyMap = new HashMap<>();

    public WaTorModel(String type) {
        super(type);
    }


    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        int movableCells = findMovableCells(myCell, myNeighbors);
        int state = getMyRules().generateNextState(movableCells, myCell.getCurrentState());
        checkState(myCell, state, myNeighbors);
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
                Integer.parseInt(getStatesBundle().getString(SHARK)), integer -> eatCells(cell, myNeighbors),
                Integer.parseInt(getStatesBundle().getString(EMPTY)), integer -> {});
        consumerGenerateNextState(cell.getCurrentState(), intMap.get(cell.getCurrentState()));
    }
    private void moveCells(Cells cell, List<Cells> myNeighbors){
        Cells c = findRightState(cell, myNeighbors, 0);
        c.setMyNextState(cell.getCurrentState());
        c.updateMyCurrentState();
        cell.setMyNextState(0);
        cell.updateMyCurrentState();
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

}


