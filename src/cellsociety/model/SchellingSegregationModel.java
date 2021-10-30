package cellsociety.model;

import cellsociety.controller.Grid;
import cellsociety.model.cellMovement.SchellingSegregationMovement;


import java.util.List;


public class SchellingSegregationModel extends CellSocietyModel{

    private static final String EMPTY = "EMPTY";
    private static final int SCALE_FACTOR = 100;
    private SchellingSegregationMovement mySchellingSegregationMovement = new SchellingSegregationMovement();

    public SchellingSegregationModel(String type) { super(type);}

    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        int numSameCells = quantityOfCellsOfGivenStateInCluster(myCell.getCurrentState(), myNeighbors);
        double propSameCells = percentSameNeighbors(numSameCells, myNeighbors);
        int state = getMyRules().generateNextState((int) (
                SCALE_FACTOR*propSameCells), myCell.getCurrentState());
        mySchellingSegregationMovement.setInitialParameters(myCell, myGrid, myNeighbors, getStatesBundle());
        mySchellingSegregationMovement.checkState(myCell, state);
    }

    private double percentSameNeighbors(int sameCells, List<Cells> neighbors){
        neighbors.removeIf(c -> c.getCurrentState() == Integer.parseInt(getStatesBundle().getString(EMPTY)));
        int neighborsLeft = neighbors.size();
        return ((double) sameCells /neighborsLeft);
    }

}
