package cellsociety.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;

public abstract class CellSocietyNeighbors {
    private List<Cells> myCells;
    public CellSocietyNeighbors(){
        myCells = new ArrayList<>();
    }

    public abstract List<Cells> generateNeighbors(int row, int col, Grid myGrid);

    protected boolean colIsValid(int col, Grid myGrid) {
        return col >=0 && col < myGrid.colLength();
    }

    protected boolean rowIsValid(int row, Grid myGrid) {
        return row >=0 && row < myGrid.rowLength();
    }

    protected List<Cells> getMyCells(){
        return myCells;
    }

    protected void addToMyCells(Cells newCell){
        myCells.add(newCell);
    }

    protected void addAllToMyCells(List<Cells> newCells){
        myCells.addAll(newCells);
    }


}
