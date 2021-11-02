package cellsociety.model;

import cellsociety.controller.Grid;
import cellsociety.model.cellMovement.SchellingSegregationMovement;


import java.util.List;
import java.util.Map;

/**
 * This class extends the CellSocietyModel class to implement
 * the SchellingSegregation game for CellSociety. It overrides the abstract setNextState()
 * method within the abstract class to set up the next state of a cell as a result
 * of applied SchellingSegregation-specific rules.
 */
public class SchellingSegregationModel extends CellSocietyModel{

    private static final String EMPTY = "EMPTY";
    private static final int SCALE_FACTOR = 100;
    private final SchellingSegregationMovement mySchellingSegregationMovement = new SchellingSegregationMovement();

    /**
     * The constructor for the SchellingSegregation Model. It takes in the same variables
     * as the abstract class.
     * @param type: The type of game being played, in this case it is SchellingSegregation.
     * @param parameters: All of the relevant parameters for the SchellingSegregation game. This includes
     *                  the percent of same group neighbors needed for a cell to stay in its position.
     *                  This also includes the neighboring rules for the current game.
     */
    public SchellingSegregationModel(String type, Map<String, String> parameters) { super(type, parameters);}

    /**
     * This method overrides the abstract setNextState method in the abstract
     * CellSocietyModel class. It finds all of the percent same group neighbors of
     * a given cell. If this percent is greater than or equal to the percent specified
     * within the .sim file, the cell does not move. However if the percent is not met,
     * then the cell must move to an empty cell until it finds a neighborhood that has
     * at least the specified .sim file percentage of same group neighbors. This
     * class uses the SchellingSegregationMovement class to deal with cell movement.
     * @param myCell: the current cell being considered in the state-changing process
     * @param row: the row number of the current cell
     * @param col: the column number of the current cell
     * @param myGrid: the grid of all the cells, we use the current cell's row and column
     */
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
