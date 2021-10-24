package cellsociety.model;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public class SchellingSegregationModel extends CellSocietyModel{

    public static final int SCALE_FACTOR = 100;
    public static final String EMPTY = "EMPTY";
    public static final String SAME = "SAME";
    public static final String MOVE = "MOVE";
    public static final String A = "A";
    public static final String B = "B";

    public SchellingSegregationModel(String type) { super(type);}

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        int numSameCells = quantityOfCellsOfGivenStateInCluster(myCell.getCurrentState(), myNeighbors);
        double propSameCells = percentSameNeighbors(numSameCells, myNeighbors);
        int state = myRules.generateNextState((int) (
                SCALE_FACTOR *propSameCells), myCell.getCurrentState());
        checkState(myCell, state, myGrid);
    }

    private double percentSameNeighbors(int sameCells, List<Cells> neighbors){
        neighbors.removeIf(c -> c.getCurrentState() == Integer.parseInt(statesBundle.getString(EMPTY)));
        int neighborsLeft = neighbors.size();
        return ((double) sameCells /neighborsLeft);
    }


    private void checkState(Cells cell, int state, Cells[][] grid){

        Map<Integer, Consumer<Integer>> intMap = Map.of( Integer.parseInt(statesBundle.getString(SAME)), integers -> keepState(cell),
            Integer.parseInt(statesBundle.getString(MOVE)), integers -> moveState(cell, grid)
        );
        testNextState(state, intMap.get(state));
    }


    private void moveState(Cells cell, Cells[][] grid){
        if(cell.getCurrentState() == Integer.parseInt(statesBundle.getString(A))
                || cell.getCurrentState() == Integer.parseInt(statesBundle.getString(B))){
            findEmpty(cell, grid);
            cell.setMyNextState(Integer.parseInt(statesBundle.getString(EMPTY)));
            cell.updateMyCurrentState();
        }
        else if(cell.getCurrentState() == Integer.parseInt(statesBundle.getString(EMPTY))){
            keepState(cell);
        }
    }

    private void keepState(Cells cell){
        cell.setMyNextState(cell.getCurrentState());
    }

    private void findEmpty(Cells cell, Cells[][] grid) {
        Random r = new Random();
        int randRow = r.nextInt(grid.length);
        int randCol = r.nextInt(grid[0].length);
        if(grid[randRow][randCol].getCurrentState() == Integer.parseInt(statesBundle.getString(EMPTY))){
            grid[randRow][randCol].setMyNextState(cell.getCurrentState());
            grid[randRow][randCol].updateMyCurrentState();
        }
        else{
            findEmpty(cell, grid);
        }

    }
}
