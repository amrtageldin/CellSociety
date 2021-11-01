package cellsociety.controller;

import cellsociety.Errors.ErrorFactory;
import cellsociety.model.Cells;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class GridFactory {
    private static final String INVALID_GRID = "InvalidGrid";
    private ErrorFactory myErrorFactory = new ErrorFactory();
    private Grid myGrid;
    private String rowCount;
    private String colCount;
    private String myValueSet;
    private Map<String, String> gameStates;

    public GridFactory(){
        gameStates = Map.of("wa_tor", "0,1,2",
                "Fire_Spread", "0,1,2", "game_of_life", "0,1", "percolation",
                "0,1,2", "schelling_segregation", "0,2,3");
    }
    /**
     * This method initially sets up the grid that has a state value for each cell. It reads in
     * a .csv file to find the number of rows and columns in the grid, and what the initial state
     * of each cell is.
     * @param file: .csv file that contains grid row/column numbers as well as initial state for each
     *            cell
     * @return: myGrid - a Cells[][] object with original states for all cells
     */
    public Grid setUpGrid(String file){
        try {
            checkGameType(file);
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);
            initializeRowAndColumnCounts(csvReader);
            initializeGrid();
            myGrid.initializeCells(csvReader);
        }
        catch(FileNotFoundException e){
           myGrid.getMyErrorFactory().updateError(INVALID_GRID);
           myErrorFactory = myGrid.getMyErrorFactory();
        }
        return myGrid;
    }

    private void checkGameType(String file){
        for(String k : gameStates.keySet()){
            if(file.contains(k)){
                myValueSet = gameStates.get(k);
            }
        }
    }

    private void initializeRowAndColumnCounts(CSVReader csvReader){
        try {
            String[] rowAndColumn = csvReader.readNext();
            rowCount = rowAndColumn[1];
            colCount = rowAndColumn[0];
        }
        catch(Exception e){
           myErrorFactory.updateError(INVALID_GRID);
        }
    }

    private void initializeGrid() {
        myGrid = new Grid(Integer.parseInt(rowCount),Integer.parseInt(colCount));
        myGrid.getCellStates(myValueSet);
    }

    public ErrorFactory getMyErrorFactory(){ return myErrorFactory;}
}
