package cellsociety.model;

import cellsociety.ruleStructure.GameOfLifeRules;
import java.util.List;

public class GameOfLifeModel extends CellSocietyModel{
    private final int ALIVE = 1;
    private final int DEAD = 0;

    public GameOfLifeModel(String type){
        super(type);
    }


    @Override
    public int getNextState(Cells myCell){
        return myCell.getMyNextState();
    }

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
        int quantityOfLivingCells = quantityOfCellsOfGivenStateInCluster(ALIVE, myNeighbors);
        myCell.setMyNextState(myRules.generateNextState(quantityOfLivingCells, myCell.getCurrentState()));

    }

}
