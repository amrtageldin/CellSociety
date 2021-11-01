package cellsociety.model.cellMovement;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class SchellingSegregationMovement implements CellSocietyMovement{
    private static final String EMPTY = "EMPTY";
    private static final String SAME = "SAME";
    private static final String MOVE = "MOVE";
    private static final String A = "A";
    private static final String B = "B";
    private Grid myGrid;
    private ResourceBundle myStatesBundle;

    public void setInitialParameters(Cells cell, Grid grid, List<Cells> neighbors, ResourceBundle statesBundle,
                                     Map<String, String> parameters){
        myGrid = grid;
        myStatesBundle = statesBundle;
    }

    public void checkState(Cells cell, int state){
        Map<Integer, Consumer<Integer>> intMap = Map.of( Integer.parseInt(myStatesBundle.getString(SAME)), integers -> keepState(cell),
                Integer.parseInt(myStatesBundle.getString(MOVE)), integers -> moveState(cell)
        );
        consumerNextState(state, intMap.get(state));
    }

    public void moveState(Cells cell){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(myStatesBundle.getString(A)), integers -> moveCells(cell),
                Integer.parseInt(myStatesBundle.getString(B)), integer -> moveCells(cell),
                Integer.parseInt(myStatesBundle.getString(EMPTY)), integer -> keepState(cell));
        consumerNextState(cell.getCurrentState(), intMap.get(cell.getCurrentState()));
    }

    private void moveCells(Cells cell){
        findEmpty(cell);
        cell.setMyNextState(Integer.parseInt(myStatesBundle.getString(EMPTY)));
        cell.updateMyCurrentState();
    }

    public void keepState(Cells cell){
        cell.setMyNextState(cell.getCurrentState());}

    private void findEmpty(Cells cell) {
        Random r = new Random();
        int randRow = r.nextInt(myGrid.rowLength());
        int randCol = r.nextInt(myGrid.colLength());
        if(myGrid.getCell(randRow,randCol).getCurrentState() == Integer.parseInt(myStatesBundle.getString(EMPTY))){
            myGrid.getCell(randRow,randCol).setMyNextState(cell.getCurrentState());
            myGrid.getCell(randRow,randCol).updateMyCurrentState();
        }
        else{
            findEmpty(cell);
        }
    }

    private void consumerNextState(int currentState, Consumer<Integer> consumer){
        consumer.accept(currentState);
    }
}
