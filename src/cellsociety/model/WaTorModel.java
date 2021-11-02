package cellsociety.model;

import cellsociety.controller.Grid;
import cellsociety.model.cellMovement.WaTorMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class WaTorModel extends CellSocietyModel{
    private static final String FISH = "FISH";
    private static final String SHARK = "SHARK";
    private static final String EMPTY = "EMPTY";
    private static final int STEP_RESET = 1;
    private static final int STEP_BUFFER = 1;
    private int stepCheck = 0;
    private int movableCells = 0;
    private List<Cells> changedCells = new ArrayList<>();
    private WaTorMovement myWaTorMovement = new WaTorMovement();

    public WaTorModel(String type, Map<String, String> parameters) { super(type, parameters);}

    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        changedCells = myWaTorMovement.getStep();
        stepCheck++;
        gridCheck(myGrid.colLength() * myGrid.rowLength());
        List<Cells> myNeighbors = getMyNeighbors().generateNeighbors(row, col, myGrid);
        myWaTorMovement.setInitialParameters(myCell, myGrid, myNeighbors, getStatesBundle(), getMyParameters());
        updateNeighbors(myNeighbors);
        updateWaTorStates(myCell, myNeighbors);
    }

    private void gridCheck(int gridSize){
        if(stepCheck == gridSize + STEP_BUFFER){
            changedCells.clear();
            stepCheck = STEP_RESET;
        }
    }

    private void updateWaTorStates(Cells myCell, List<Cells> myNeighbors) {
        if(!changedCells.contains(myCell)) {
            int movableCells = findMovableCells(myCell, myNeighbors);
            myWaTorMovement.checkState(myCell, getMyRules().generateNextState(movableCells, myCell.getCurrentState()));
        }
    }

    private void updateNeighbors(List<Cells> myNeighbors){
        myNeighbors.removeIf(c -> changedCells.contains(c));
    }


    private int findMovableCells(Cells myCell, List<Cells> myNeighbors){
        Map<Integer, Consumer<Integer>> movableMap =
                Map.of(Integer.parseInt(getStatesBundle().getString(FISH)),
                        integer -> movableCellsForFish(myNeighbors),
                        Integer.parseInt(getStatesBundle().getString(SHARK)),
                        integer -> movableCellsForShark(myNeighbors),
                        Integer.parseInt(getStatesBundle().getString(EMPTY)),
                        integer -> {});
        consumerGenerateNextState(myCell.getCurrentState(), movableMap.get(myCell.getCurrentState()));
        return movableCells;
    }

    private void movableCellsForFish(List<Cells> neighbors){
        movableCells = quantityOfCellsOfGivenStateInCluster(Integer.parseInt(getStatesBundle().getString(EMPTY)), neighbors);
    }

    private void movableCellsForShark(List<Cells> neighbors){
        movableCells = neighbors.size() - quantityOfCellsOfGivenStateInCluster(Integer.parseInt(getStatesBundle().getString(SHARK)), neighbors);
    }
}


