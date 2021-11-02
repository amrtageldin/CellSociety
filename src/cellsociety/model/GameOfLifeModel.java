package cellsociety.model;

import cellsociety.controller.Grid;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GameOfLifeModel extends CellSocietyModel{

    private static final String ALIVE = "ALIVE";
    private static final String DEAD = "DEAD";


    public GameOfLifeModel(String type, Map<String, String> parameters) { super(type, parameters);}

    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        int initialState = bundleToInteger(ALIVE);
        int quantityOfLivingCells = quantityOfCellsOfGivenStateInCluster(initialState, neighborGenerator(row,col,myGrid));

        Integer alive = bundleToInteger(ALIVE);
        Integer dead = bundleToInteger(DEAD);

        Map<Integer, Consumer<Integer>> intMap = Map.of(alive, integers -> myCell.setMyNextState(getMyRules().generateNextState(quantityOfLivingCells, myCell.getCurrentState())),
            dead, integers -> myCell.setMyNextState(getMyRules().generateNextState(quantityOfLivingCells, myCell.getCurrentState()))
        );
        this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));
    }


}
