package cellsociety.model;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GameOfLifeModel extends CellSocietyModel{

    private static final String ALIVE = "ALIVE";
    private static final String DEAD = "DEAD";


    public GameOfLifeModel(String type){
        super(type);
    }

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
        int initialState = Integer.parseInt(getStatesBundle().getString(ALIVE));
        int quantityOfLivingCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);

        Integer alive = Integer.parseInt(getStatesBundle().getString(ALIVE));
        Integer dead = Integer.parseInt(getStatesBundle().getString(DEAD));

        Map<Integer, Consumer<Integer>> intMap = Map.of(alive, integers -> myCell.setMyNextState(getMyRules().generateNextState(quantityOfLivingCells, myCell.getCurrentState())),
            dead, integers -> myCell.setMyNextState(getMyRules().generateNextState(quantityOfLivingCells, myCell.getCurrentState()))
        );

        this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));
    }

}
