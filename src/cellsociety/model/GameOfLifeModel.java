package cellsociety.model;

import cellsociety.controller.Grid;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GameOfLifeModel extends CellSocietyModel{

    private static final String ALIVE = "ALIVE";
    private static final String DEAD = "DEAD";
    private static final String GAME_ERROR = "GameError";


    public GameOfLifeModel(String type, Map<String, String> parameters) { super(type, parameters);}

    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        try {
            List<Cells> myNeighbors = getMyNeighbors().generateNeighbors(row, col, myGrid);
            int initialState = Integer.parseInt(getStatesBundle().getString(ALIVE));
            int quantityOfLivingCells = quantityOfCellsOfGivenStateInCluster(initialState, myNeighbors);

            Integer alive = Integer.parseInt(getStatesBundle().getString(ALIVE));
            Integer dead = Integer.parseInt(getStatesBundle().getString(DEAD));

            Map<Integer, Consumer<Integer>> intMap = Map.of(alive, integers -> myCell.setMyNextState(getMyRules().generateNextState(quantityOfLivingCells, myCell.getCurrentState())),
                    dead, integers -> myCell.setMyNextState(getMyRules().generateNextState(quantityOfLivingCells, myCell.getCurrentState()))
            );
            this.consumerGenerateNextState(myCell.getCurrentState(), intMap.get(myCell.getCurrentState()));
        }
        catch (Exception e){
            getMyErrorFactory().updateError(GAME_ERROR);
        }
    }

}
