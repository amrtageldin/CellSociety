package cellsociety.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;

public class FullNeighbors extends CellSocietyNeighbors{

    public FullNeighbors(){
        super();
    }
    public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
        RectangleNeighbors myRectangleNeighbors = new RectangleNeighbors();
        List<Cells> myRectangleCells = myRectangleNeighbors.generateNeighbors(row,col,myGrid);

        DiagonalNeighbors myDiagonalNeighbors = new DiagonalNeighbors();
        List<Cells> myDiagonalCells = myDiagonalNeighbors.generateNeighbors(row,col,myGrid);

        myRectangleCells.addAll(myDiagonalCells);
        return myRectangleCells;
    }
}
