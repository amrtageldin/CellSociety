package cellsociety.controller;

import cellsociety.Errors.ErrorFactory;
import cellsociety.model.Cells;
import com.opencsv.CSVReader;
import javafx.scene.control.Cell;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Grid {
  public static final String X = "x";
  private Cells[][] myGrid;
  private static final String INVALID_GRID = "InvalidGrid";
  private ErrorFactory myErrorFactory = new ErrorFactory();
  private ArrayList<Integer> cellStatesList = new ArrayList<>();

  public Grid(int rowCount, int colCount ){
    myGrid = new Cells[rowCount][colCount];
  }

  public void getCellStates(String cellStates){
    String[] cellStateStrings = cellStates.split(",");
    for(String s : cellStateStrings){
      cellStatesList.add(Integer.parseInt(s));
    }
  }

  public void initializeCells(CSVReader csvReader){
    try {
      String[] nextCell;
      int i = 0;
      while ((nextCell = csvReader.readNext()) != null) {
        if (Objects.equals(nextCell[0], X)) {
          initializeRandomCells(i, nextCell);
        } else {
          initializeCSVReadCells(i, nextCell);
        }
        i++;
      }
    }
    catch(Exception e){
      myErrorFactory.updateError(INVALID_GRID);
    }
  }

  private void initializeCSVReadCells(int i, String[] nextCell){
    for (int j = 0; j < nextCell.length; j++) {
      myGrid[i][j] = new Cells(Integer.parseInt(nextCell[j]));
    }
  }

  private void initializeRandomCells(int i, String[] nextCell){
    Random r = new Random();
    for(int j = 0; j < nextCell.length; j++){
      myGrid[i][j] = new Cells(cellStatesList.get(r.nextInt(cellStatesList.size())));
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
