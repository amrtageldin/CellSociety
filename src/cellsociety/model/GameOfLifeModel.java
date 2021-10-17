package cellsociety.model;

import cellsociety.rules.GameOfLifeRules;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Cell;

public class GameOfLifeModel extends CellSocietyModel{
    private final int ALIVE = 1;
    private final int DEAD = 0;
    private GameOfLifeRules myRules;

    public GameOfLifeModel(){
        System.out.println("heree");
        myRules = new GameOfLifeRules();
    }


    public int getNextState(Cells myCell){
        return myCell.getMyNextState();
    }

    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
        int quantityOfLivingCells = relevantClumpQuantityInDesiredState(ALIVE, myNeighbors);

        myCell.setMyNextState(myRules.generateNextState(quantityOfLivingCells));

    }

    private List<Cells> generateNeighbors(int row, int col, Cells[][] myGrid) {
        int[] xChanges = new int[]{-1,0,1};
        int[] yChanges = new int[]{-1,0,1};
        List<Cells> myCells = new ArrayList<>();
        for (int i : xChanges){
            if (row + i >=0 && row + i < myGrid.length){
                for (int j : yChanges){
                    if (col + j >= 0 && col + j < myGrid[0].length ){
                        myCells.add(myGrid[row + i][ col + j]);
                    }
                }

            }
        }

        return myCells;
    }

    private int relevantClumpQuantityInDesiredState(int state, List<Cells> myRelevantCluster) {
        int runningCountOfState = 0;
        for (Cells x : myRelevantCluster){
            if (x.getCurrentState() == state) {
                runningCountOfState++;
            }
        }
        return runningCountOfState;
    }


    public void updateState(List<Cells> myNeighbors){

    }
}
