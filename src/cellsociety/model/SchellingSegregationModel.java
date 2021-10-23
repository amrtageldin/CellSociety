package cellsociety.model;

import java.util.List;

public class SchellingSegregationModel extends CellSocietyModel{

    public SchellingSegregationModel(String type) { super(type);}

    @Override
    public int getNextState(Cells myCell) {return myCell.getMyNextState();}

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        int numSameCells = quantityOfCellsOfGivenStateInCluster(myCell.getCurrentState(), myNeighbors);
        double propSameCells = percentSameNeighbors(numSameCells, myNeighbors.size());
        int state = myRules.generateNextState((int) (10*propSameCells), myCell.getCurrentState());
        checkState(myCell, state, myGrid);
    }

    private double percentSameNeighbors(int sameCells, int neighbors){
        double prop = ((double) sameCells /neighbors);
        return prop;
    }

    private void checkState(Cells cell, int state, Cells[][] grid){
        if(state == 1){
            cell.setMyNextState(cell.getCurrentState());
        }
        if(state == 4){
            findEmpty(cell, grid);
            cell.setMyNextState(0);
        }
    }

    private void findEmpty(Cells cell, Cells[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].getCurrentState() == 0) {
                    grid[i][j].setMyNextState(cell.getCurrentState());
                    break;
                }
            }
        }
    }
}
