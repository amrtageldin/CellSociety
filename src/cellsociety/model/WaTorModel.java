package cellsociety.model;

import cellsociety.controller.Grid;
import cellsociety.model.cellMovement.WaTorMovement;

import java.util.ArrayList;
import java.util.List;


public class WaTorModel extends CellSocietyModel{
    private static final String FISH = "FISH";
    private static final String SHARK = "SHARK";
    private static final String EMPTY = "EMPTY";
    private static final int STEP_RESET = 1;
    private static final int STEP_BUFFER = 1;
    private static final int INITIAL_MOVABLE_CELLS = 0;
    private int stepCheck = 0;
    private List<Cells> changedCells = new ArrayList<>();
    private WaTorMovement myWaTorMovement = new WaTorMovement();

    public WaTorModel(String type) {
        super(type);
    }

    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        changedCells = myWaTorMovement.getStep();
        stepCheck++;
        gridCheck(myGrid.colLength() * myGrid.rowLength());
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        myWaTorMovement.setInitialParameters(myCell, myGrid, myNeighbors, getStatesBundle());
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


