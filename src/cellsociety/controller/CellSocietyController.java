package cellsociety.controller;

import cellsociety.Errors.ErrorFactory;
import cellsociety.model.CellSocietyModel;

import cellsociety.model.Cells;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class CellSocietyController {
    private CellSocietyModel myModel;
    private final ResourceBundle myFileType;
    private final GridFactory myGridFactory;
    private final GameFactory myGameFactory;
    private Grid myGrid;
    private String myGameType;
    private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.controller.resources.";
    private static final String FILE_TYPE = "FileType";
    private String error;
    private boolean errorExists;
    private int[] cellStateCounts;
    private double stepCount;

    /**
     * Constructor for controller within CellSociety. Initializes controller as well as relevant variables
     * to be used within the class.
     */
    public CellSocietyController(){
        myFileType = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + FILE_TYPE);
        myGridFactory = new GridFactory();
        myGameFactory = new GameFactory();
    }

    /**
     * This method gets the loaded file from the view buttons and determines whether
     * it is a .csv file or a .sim file. Depending on which file type the loaded file is, the
     * appropriate method is called to parse through the file appropriately.
     * @param file: a string pointing to the file path
     */
    public void loadFileType(String file){
        String[] fileType = file.split("\\.");
        if(fileType[1].equals(myFileType.getString("csv"))){
            createGridFromFile(file);
        }
        else{
            createSimFromFile(file);
        }
    }

    private void createGridFromFile(String file){
        myGrid = myGridFactory.setUpGrid(file);
        checkErrors(myGridFactory.getMyErrorFactory());
    }

    /**
     * This method is called by the CellSocietyView to get the current state of each
     * cell in the grid, so that it can be displayed on the game scene
     * @return myGrid - grid of Cells with current states
     */
    public Grid getMyGrid(){return myGrid;}

    private void createSimFromFile(String file){
        try {
            String gameType = myGameFactory.setUpModel(file);
            Object [] paramValuesSub = {gameType, myGameFactory.getParametersMap()};
            myModel = (CellSocietyModel) Class.forName(String.format("cellsociety.model.%sModel", gameType)).getConstructor(String.class, Map.class).newInstance(paramValuesSub);
            myGameType = gameType;
        }
        catch(Exception e){
            checkErrors(myGameFactory.getMyErrorFactory());
        }
    }

    public Map<String, String> getMyParametersMap(){
        return myGameFactory.getParametersMap();
    }

    public CellSocietyModel getMyModel(){
        return myModel;
    }

    /**
     * This method is called by the view so that the view knows which game the user has
     * loaded up to play
     * @return myGameType: the title of the current game being played
     */
    public String getMyGameType(){
        return myGameType;
    }

    /**
     * This method is called by the view to actually run the simulation type that is loaded. This step() method
     * goes through each cell within a grid and sets its next state dependent on its neighbors and the applied rules
     * within the model. At the end the grid is updated such that every cell's next state is now its current state.
     */
    public void step(){
        for (int i = 0; i < myGrid.rowLength(); i++){
            for (int j = 0; j < myGrid.colLength(); j++){
                Cells thisCell = myGrid.getCell(i,j);
                myModel.setNextState(thisCell, i, j, myGrid);
                checkErrors(myModel.getMyErrorFactory());
            }
        }
        stepCount++;
        updateGrid();
    }

    private void updateGrid() {
        cellStateCounts = new int[4];
        for (int i = 0; i < myGrid.rowLength(); i++) {
            for (int j = 0; j < myGrid.colLength(); j++) {
                Cells thisCell = myGrid.getCell(i, j);
                thisCell.updateMyCurrentState();
                cellStateCounts[thisCell.getCurrentState()]++;
            }
        }
    }

    public int[] getCellStateCounts() {
        return cellStateCounts;
    }

    public double getStepCount() {
        return stepCount;
    }

    private void checkErrors(ErrorFactory error){
        if(error.errorExists()){
            setErrors(error);
        }
    }

    private void setErrors(ErrorFactory errors){
        errorExists = true;
        error = errors.getErrorKey();
        errors.setErrorNoLongerExists();
    }

    public String getMyError(){
        errorExists = false;
        return error;
    }

    public boolean getErrorExists(){
        return errorExists;
    }
}