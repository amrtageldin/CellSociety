package cellsociety.model;

import java.util.ArrayList;
import java.util.List;

public class SchellingSegregationModel extends CellSocietyModel{

    public SchellingSegregationModel(String type) { super(type);}

    @Override
    public int getNextState(Cells myCell) {return myCell.getMyNextState();}

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        int numSameCells = quantityOfCellsOfGivenStateInCluster(myCell.getCurrentState(), myNeighbors);
        double propSameCells = percentSameNeighbors(numSameCells, myNeighbors);
        int state = myRules.generateNextState((int) (10*propSameCells), myCell.getCurrentState());
        checkState(myCell, state, myGrid);
    }

    private double percentSameNeighbors(int sameCells, List<Cells> neighbors){
        neighbors.removeIf(c -> c.getCurrentState() == 0);
        int neighborsLeft = neighbors.size();
        double prop = ((double) sameCells /neighborsLeft);
        return prop;
    }

    private void checkState(Cells cell, int state, Cells[][] grid){
        if(state == 1){
            cell.setMyNextState(cell.getCurrentState());
        }
        if(state == 4 && (cell.getCurrentState() == 2 || cell.getCurrentState() == 3)){
            findEmpty(cell, grid);
            cell.setMyNextState(0);
            cell.updateMyCurrentState();
        }
        else if(state == 4 && cell.getCurrentState() == 0){
            cell.setMyNextState(cell.getCurrentState());
        }
    }

    private void findEmpty(Cells cell, Cells[][] grid) {
        boolean stop = false;
        for (int i = 0; i < grid.length && !stop; i++) {
            for (int j = 0; j < grid[0].length && !stop; j++) {
                if (grid[i][j].getCurrentState() == 0){
                    grid[i][j].setMyNextState(cell.getCurrentState());
                    grid[i][j].updateMyCurrentState();
                    stop = true;
                }
            }
        }
    }
}
