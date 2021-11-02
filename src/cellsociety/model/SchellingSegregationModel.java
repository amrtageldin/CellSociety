package cellsociety.model;

import cellsociety.controller.Grid;
import cellsociety.model.cellMovement.SchellingSegregationMovement;


import java.util.List;
import java.util.Map;


public class SchellingSegregationModel extends CellSocietyModel{

    private static final String EMPTY = "EMPTY";
    private static final int SCALE_FACTOR = 100;
    private SchellingSegregationMovement mySchellingSegregationMovement = new SchellingSegregationMovement();

    public SchellingSegregationModel(String type, Map<String, String> parameters) { super(type, parameters);}

    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        List<Cells> myNeighbors = neighborGenerator(row,col,myGrid);
        int numSameCells = quantityOfCellsOfGivenStateInCluster(myCell.getCurrentState(), neighborGenerator(row,col,myGrid));
        double propSameCells = percentSameNeighbors(numSameCells, myNeighbors);
        int state = getMyRules().generateNextState((int) (
            SCALE_FACTOR*propSameCells), myCell.getCurrentState());
        mySchellingSegregationMovement.setInitialParameters(myCell, myGrid, myNeighbors, getStatesBundle(), getMyParameters());
        mySchellingSegregationMovement.checkState(myCell, state);
    }

    private double percentSameNeighbors(int sameCells, List<Cells> neighbors){
        neighbors.removeIf(c -> c.getCurrentState() == bundleToInteger(EMPTY));
        int neighborsLeft = neighbors.size();
        return ((double) sameCells /neighborsLeft);
    }

}
