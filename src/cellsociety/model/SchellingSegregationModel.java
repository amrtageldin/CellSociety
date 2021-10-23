package cellsociety.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random r = new Random();
        int randRow = r.nextInt(grid.length);
        int randCol = r.nextInt(grid[0].length);
        if(grid[randRow][randCol].getCurrentState() == 0){
            grid[randRow][randCol].setMyNextState(cell.getCurrentState());
            grid[randRow][randCol].updateMyCurrentState();
        }
        else{
            findEmpty(cell, grid);
        }

    }
}
