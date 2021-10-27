package cellsociety.model;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public class SchellingSegregationModel extends CellSocietyModel{

    private static final String EMPTY = "EMPTY";
    private static final String SAME = "SAME";
    private static final String MOVE = "MOVE";
    private static final String A = "A";
    private static final String B = "B";
    private static final int SCALE_FACTOR = 100;

    public SchellingSegregationModel(String type) { super(type);}

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        int numSameCells = quantityOfCellsOfGivenStateInCluster(myCell.getCurrentState(), myNeighbors);
        double propSameCells = percentSameNeighbors(numSameCells, myNeighbors);
        int state = getMyRules().generateNextState((int) (
                SCALE_FACTOR *propSameCells), myCell.getCurrentState());
        checkState(myCell, state, myGrid);
    }

    private double percentSameNeighbors(int sameCells, List<Cells> neighbors){
        neighbors.removeIf(c -> c.getCurrentState() == Integer.parseInt(getStatesBundle().getString(EMPTY)));
        int neighborsLeft = neighbors.size();
        return ((double) sameCells /neighborsLeft);
    }


    private void checkState(Cells cell, int state, Cells[][] grid){
        Map<Integer, Consumer<Integer>> intMap = Map.of( Integer.parseInt(getStatesBundle().getString(SAME)), integers -> keepState(cell),
            Integer.parseInt(getStatesBundle().getString(MOVE)), integers -> moveState(cell, grid)
        );
        consumerGenerateNextState(state, intMap.get(state));
    }


    private void moveState(Cells cell, Cells[][] grid){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(getStatesBundle().getString(A)), integers -> moveCells(cell, grid),
                Integer.parseInt(getStatesBundle().getString(B)), integer -> moveCells(cell, grid),
                Integer.parseInt(getStatesBundle().getString(EMPTY)), integer -> keepState(cell));
        consumerGenerateNextState(cell.getCurrentState(), intMap.get(cell.getCurrentState()));
    }

    private void moveCells(Cells cell, Cells[][] grid){
        findEmpty(cell, grid);
        cell.setMyNextState(Integer.parseInt(getStatesBundle().getString(EMPTY)));
        cell.updateMyCurrentState();
    }
    private void keepState(Cells cell){
        cell.setMyNextState(cell.getCurrentState());
    }

    private void findEmpty(Cells cell, Cells[][] grid) {
        Random r = new Random();
        int randRow = r.nextInt(grid.length);
        int randCol = r.nextInt(grid[0].length);
        if(grid[randRow][randCol].getCurrentState() == Integer.parseInt(getStatesBundle().getString(EMPTY))){
            grid[randRow][randCol].setMyNextState(cell.getCurrentState());
            grid[randRow][randCol].updateMyCurrentState();
        }
        else{
            findEmpty(cell, grid);
        }
    }
}
