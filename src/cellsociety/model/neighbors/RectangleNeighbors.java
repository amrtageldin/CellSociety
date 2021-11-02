package cellsociety.model.neighbors;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.List;

public class RectangleNeighbors extends CellSocietyNeighbors{

    public RectangleNeighbors(){super();}

    public List<Cells> generateNeighbors(int row, int col, Grid myGrid) {
        UpDownNeighbors myUpDown = new UpDownNeighbors();
        List<Cells> myUpDownCells = myUpDown.generateNeighbors(row,col,myGrid);

        LeftRightNeighbors myLeftRight = new LeftRightNeighbors();
        List<Cells> myLeftRightCells = myLeftRight.generateNeighbors(row,col,myGrid);

        myUpDownCells.addAll(myLeftRightCells);
        return myUpDownCells;
    }
}
