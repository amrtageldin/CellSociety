package cellsociety.controller;

import cellsociety.Errors.ErrorFactory;
import cellsociety.model.Cells;
import com.opencsv.CSVReader;
import javafx.scene.control.Cell;

public class Grid {
  private Cells[][] myGrid;
  private static final String INVALID_GRID = "InvalidGrid";
  private ErrorFactory myErrorFactory = new ErrorFactory();
  public Grid(int rowCount, int colCount ){
    myGrid = new Cells[rowCount][colCount];
  }


  public void initializeCells(CSVReader csvReader){
    try {
      String nextCell[];
      int i = 0;
      while ((nextCell = csvReader.readNext()) != null) {
        for (int j = 0; j < nextCell.length; j++) {
          myGrid[i][j] = new Cells(Integer.parseInt(nextCell[j]));
        }
        i++;
      }
    }
    catch(Exception e){
      myErrorFactory.updateError(INVALID_GRID);
    }

  }

  public int rowLength(){
    return myGrid.length;
  }

  public int colLength(){
    return myGrid[0].length;
  }

  public Cells getCell(int i, int j) {
    return myGrid[i][j];
  }
  public void setCell(int i, int j, Cells myCell){
    myGrid[i][j] = myCell;
  }

  public ErrorFactory getMyErrorFactory(){
    return myErrorFactory;
  }
}
