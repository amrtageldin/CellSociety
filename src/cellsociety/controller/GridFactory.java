package cellsociety.controller;

import cellsociety.model.Cells;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GridFactory {
    private Cells[][] myGrid;

    public void setUpGrid(String file){
        try {
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);

            initializeRowAndColumnCounts(csvReader);
            initializeCells(csvReader);

        }
        catch(FileNotFoundException e){
            e.printStackTrace(); //NEED TO COME BACK AND FIX!!!
        }
    }

    private void initializeRowAndColumnCounts(CSVReader csvReader){
        try {
            String[] rowAndColumn = csvReader.readNext();
            for(String c : rowAndColumn){
                System.out.println(c);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void initializeCells(CSVReader csvReader){
       try {
           String nextCell[];
           while ((nextCell = csvReader.readNext()) != null) {
               for (String cell : nextCell) {
                   System.out.print(cell);
               }
               System.out.println();
           }
       }
       catch(Exception e){
           e.printStackTrace(); //NEED TO CHANGE LATER
       }

    }
}
