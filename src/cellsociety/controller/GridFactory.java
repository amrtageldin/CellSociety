package cellsociety.controller;

import cellsociety.model.Cells;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GridFactory {
    private Cells[][] myGrid;
    private String rowCount;
    private String colCount;

    public Cells[][] setUpGrid(String file){
        try {
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);

            initializeRowAndColumnCounts(csvReader);
            initializeGrid();
            initializeCells(csvReader);

        }
        catch(FileNotFoundException e){
            e.printStackTrace(); //NEED TO COME BACK AND FIX!!!
        }
        return myGrid;
    }


    private void initializeRowAndColumnCounts(CSVReader csvReader){
        try {
            String[] rowAndColumn = csvReader.readNext();
            rowCount = rowAndColumn[0]; // set variables to remove magic numbers
            colCount = rowAndColumn[1];

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void initializeGrid() {
        myGrid = new Cells[Integer.parseInt(rowCount)][Integer.parseInt(colCount)];
    }


    private void initializeCells(CSVReader csvReader){
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
           e.printStackTrace(); //NEED TO CHANGE LATER
       }

    }
}
