package cellsociety.controller;

import cellsociety.model.CellSocietyModel;

import cellsociety.model.Cells;
import java.util.ResourceBundle;

public class CellSocietyController {
    private CellSocietyModel myModel;
    private ResourceBundle myFileType;
    private GridFactory myGridFactory;
    private GameFactory myGameFactory;
    private Cells[][] myGrid;
    private String myGameType;
    private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.controller.resources.";
    private static final String FILE_TYPE = "FileType";

    /**
     * Constructor for controller within CellSociety. Initializes controller as well as relevant variables
     * to be used within the class.
     * @param model: the CellSociety game model which keeps track of the state for each cell
     */
    public CellSocietyController(CellSocietyModel model){
        myModel = model;
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
    }

    /**
     * This method is called by the CellSocietyView to get the current state of each
     * cell in the grid, so that it can be displayed on the game scene
     * @return myGrid - grid of Cells with current states
     */
    public Cells[][] getMyGrid(){
        return myGrid;
    }

    private CellSocietyModel createSimFromFile(String file){
        try {
            String gameType = myGameFactory.setUpModel(file);
            myModel = (CellSocietyModel) Class.forName(String.format("cellsociety.model.%s", gameType)).getConstructor().newInstance();
            myGameType = gameType;
        }
        catch(Exception e){
            e.printStackTrace();
        }
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

    public void step(){
        // for each cell in step; call the model to run the rules
        // its gonna need the neighbors for the gameofLifemodel;
    }
}
