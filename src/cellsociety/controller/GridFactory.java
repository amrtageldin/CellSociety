package cellsociety.controller;

import cellsociety.Errors.ErrorFactory;
import cellsociety.model.Cells;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class GridFactory {
    private static final String FIRE = "Fire_Spread";
    private static final String INVALID_GRID = "InvalidGrid";
    private static final String WA_TOR = "wa_tor";
    private static final String PERCOLATION_STATES = "0,1,2";
    private static final String WA_TOR_STATES = "0,1,2";
    private static final String FIRE_STATES = "0,1,2";
    private static final String GAME_OF_LIFE = "game_of_life";
    private static final String GAME_OF_LIFE_STATES = "0,1";
    private static final String PERCOLATION = "percolation";
    private static final String SCHELLING_SEGREGATION = "schelling_segregation";
    private static final String SCHELLING_SEGREGATION_STATES = "0,2,3";
    private ErrorFactory myErrorFactory = new ErrorFactory();
    private Grid myGrid;
    private String rowCount;
    private String colCount;
    private String myValueSet;
    private Map<String, String> gameStates;

    public GridFactory(){
        gameStates = Map.of(WA_TOR, WA_TOR_STATES,
                FIRE, FIRE_STATES,
                GAME_OF_LIFE, GAME_OF_LIFE_STATES,
                PERCOLATION, PERCOLATION_STATES,
                SCHELLING_SEGREGATION, SCHELLING_SEGREGATION_STATES);
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
