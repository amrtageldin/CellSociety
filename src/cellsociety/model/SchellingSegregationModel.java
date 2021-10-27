package cellsociety.model;

import cellsociety.controller.Grid;
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
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
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


    private void checkState(Cells cell, int state, Grid grid){
        Map<Integer, Consumer<Integer>> intMap = Map.of( Integer.parseInt(statesBundle.getString(SAME)), integers -> keepState(cell),
            Integer.parseInt(statesBundle.getString(MOVE)), integers -> moveState(cell, grid)
        );
        consumerGenerateNextState(state, intMap.get(state));
    }


    private void moveState(Cells cell, Grid grid){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(statesBundle.getString(A)), integers -> moveCells(cell, grid),
                Integer.parseInt(statesBundle.getString(B)), integer -> moveCells(cell, grid),
                Integer.parseInt(statesBundle.getString(EMPTY)), integer -> keepState(cell));
        consumerGenerateNextState(cell.getCurrentState(), intMap.get(cell.getCurrentState()));
    }

    private void moveCells(Cells cell, Grid grid){
        findEmpty(cell, grid);
        cell.setMyNextState(Integer.parseInt(getStatesBundle().getString(EMPTY)));
        cell.updateMyCurrentState();
    }
    private void keepState(Cells cell){
        cell.setMyNextState(cell.getCurrentState());
    }

    private void findEmpty(Cells cell, Grid grid) {
        Random r = new Random();
        int randRow = r.nextInt(grid.rowLength());
        int randCol = r.nextInt(grid.colLength());
        if(grid.getCell(randRow,randCol).getCurrentState() == Integer.parseInt(statesBundle.getString(EMPTY))){
            grid.getCell(randRow,randCol).setMyNextState(cell.getCurrentState());
            grid.getCell(randRow,randCol).updateMyCurrentState();
        }
        else{
            findEmpty(cell, grid);
        }
    }
}
