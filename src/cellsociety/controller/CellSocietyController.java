package cellsociety.controller;

import cellsociety.Errors.ErrorFactory;
import cellsociety.model.CellSocietyModel;

import cellsociety.model.Cells;

import java.util.Map;
import java.util.ResourceBundle;


/**
 * This is the controller for the CellSociety application. The controller is the only connection point
 * between the front-end (view) and back-end (model). The controller takes in the file inputs from the user,
 * and then creates a valid game and grid corresponding to the inputted files. It also serves as the starting
 * point for the animation: the animation in the view calls the step() method in the controller, which connects
 * with the model to update cell states based on given rules (dependent on the game type).
 */
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

    /**
     * This method returns a Map of all of the parameters that exist within a .sim file for a game.
     * This is called by the view so that it can get and display relevant parameters such as
     * cell colors and shapes.
     * @return myGameFactory.getParametersMap() : a map of all of the parameters separated by their
     * keys (i.e. Color=#F0000)
     */
    public Map<String, String> getMyParametersMap(){
        return myGameFactory.getParametersMap();
    }

    /**
     * This method returns the model that has been initiated through the loading of a .sim file.
     * It is used for testing purposes.
     * @return myModel: the current model being played.
     */
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
        try {
            for (int i = 0; i < myGrid.rowLength(); i++) {
                for (int j = 0; j < myGrid.colLength(); j++) {
                    Cells thisCell = myGrid.getCell(i, j);
                    myModel.setNextState(thisCell, i, j, myGrid);
                }
            }
            stepCount++;
            updateGrid();
        }
        catch(Exception e){
            checkErrors(myModel.getMyErrorFactory());
        }
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

    /**
     * This method returns all of the counts for each state type present within the Grid.
     * It is called by the view so a histogram can be created to display cell state counts.
     * @return cellStateCounts: an int array with a list of all of the counts for different
     * state types.
     */
    public int[] getCellStateCounts() {
        return cellStateCounts;
    }

    /**
     * This keeps track of how many times the step() function has been called in the controller.
     * The view uses this method to know when to update the histogram.
     * @return stepCount: the number of calls to step() that have occurred.
     */
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

    /**
     * This method is called by the view to return any errors that come up within the backend.
     * For example, if conflicting game .sim and .csv files are loaded, an error is returned.
     * @return error: the key for the error message to be returned, values are described in the
     * view resource bundles.
     */
    public String getMyError(){
        errorExists = false;
        return error;
    }

    /**
     * This method is called by the view to check to see if the backend threw any errors,
     * if so, the error key is returned using the getMyError() method.
     * @return errorExists: a boolean that tells the view if an error in the backend exists
     * or not.
     */
    public boolean getErrorExists(){
        return errorExists;
    }
}