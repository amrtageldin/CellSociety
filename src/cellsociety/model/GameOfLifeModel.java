package cellsociety.model;

import cellsociety.ruleStructure.GameOfLifeRules;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeModel extends CellSocietyModel{
    private final int ALIVE = 1;
    private final int DEAD = 0;
    private GameOfLifeRules myRules;

    public GameOfLifeModel(){
        System.out.println("yes");
        myRules = new GameOfLifeRules();
    }


    @Override
    public int getNextState(Cells myCell){
        return myCell.getMyNextState();
    }

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        List<Cells> myNeighbors = generateNeighbors(row,col, myGrid);
        int quantityOfLivingCells = relevantClumpQuantityInDesiredState(ALIVE, myNeighbors);
        myCell.setMyNextState(myRules.generateNextState(quantityOfLivingCells));

    }

    private List<Cells> generateNeighbors(int row, int col, Cells[][] myGrid) {
        // square
        int[] xChanges = new int[]{-1,0,1};
        int[] yChanges = new int[]{-1,0,1};
        List<Cells> myCells = new ArrayList<>();
        for (int i : xChanges){
            if (rowIsValid(row + i, myGrid)){
                for (int j : yChanges){
                    if (colIsValid(col + j, myGrid) && !(i == 0 && j == 0)){
                        myCells.add(myGrid[row + i][ col + j]);
                        System.out.println(myGrid[row + i][col + j].getCurrentState());
                    }
                }

            }
        }
        return myCells;
    }

    private boolean colIsValid(int col, Cells[][] myGrid) {
        return col >=0 && col < myGrid[0].length;
    }

    private boolean rowIsValid(int row, Cells[][] myGrid) {
        return row >=0 && row < myGrid.length;
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

}
