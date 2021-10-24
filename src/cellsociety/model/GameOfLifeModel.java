package cellsociety.model;

import cellsociety.ruleStructure.GameOfLifeRules;
import java.util.List;

public class GameOfLifeModel extends CellSocietyModel{

    public static final String ALIVE = "ALIVE";

    public GameOfLifeModel(String type){
        super(type);
    }

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
        int initialState = Integer.parseInt(statesBundle.getString(ALIVE));
        int quantityOfLivingCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);
        myCell.setMyNextState(myRules.generateNextState(quantityOfLivingCells, myCell.getCurrentState()));
    }

}
