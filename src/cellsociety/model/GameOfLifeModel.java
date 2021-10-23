package cellsociety.model;

import cellsociety.ruleStructure.GameOfLifeRules;
import java.util.List;

public class GameOfLifeModel extends CellSocietyModel{

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
        int initialState = Integer.parseInt(statesBundle.getString(initialStateString));
        int quantityOfLivingCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);
        myCell.setMyNextState(myRules.generateNextState(quantityOfLivingCells, myCell.getCurrentState()));
    }

}
