package cellsociety.model.cellMovement;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
/**
 * This class implements the CellSocietyMovement for the SchellingSegregation game.
 * It deals with the movement of the Sharks and Fish depending on the neighbors
 * they have. If the rules class returns that a cell needs to move to a region of different neighbors,
 * this class selects a neighboring cell for the cell to move to, and immediately reflects this in
 * the current state of the cell. It utilizes consumers to deal with differing cell types and the actions
 * that they should take. It also calls the Developer class to keep track of reproduction and energy
 * states for each cell, and make changes that reflect those states when needed.
 */
public class WaTorMovement implements CellSocietyMovement {
    private static final String FISH = "FISH";
    private static final String SHARK = "SHARK";
    private static final String EMPTY = "EMPTY";
    private static final String SAME = "SAME";
    private static final String MOVE = "MOVE";
    private static final int NO_ENERGY = 0;
    private static final int ENERGY_INCREASE = 1;
    private final List<Cells> changedCells;
    private final WaTorDeveloper myDevelopmentStage;
    private List<Cells> myNeighbors;
    private ResourceBundle myStatesBundle;

    /**
     * This is the constructor for the WaTorMovement class. It sets up an instance
     * of the Developer class that will be used to deal with cell reproduction and energy.
     * It also creates an ArrayList that keeps track of cells that have already been changed
     * (and should not be changed again until the view has reflected these changes).
     */
    public WaTorMovement(){
        myDevelopmentStage = new WaTorDeveloper();
        changedCells = new ArrayList<>();
    }

    /**
     * This method sets up initial parameters (after being called by WaTorModel)
     * to be used throughout the class
     * @param cell: the current cell being processed to see how it needs to change
     * @param grid: the grid of all cells with their current states
     * @param neighbors: the cells that are next to the current cell
     * @param statesBundle: the resource bundle that holds all of the relevant states for the
     *                    WaTor Game.
     * @param parameters: the map of all the relevant parameters for the WaTor game as read
     *                  from the .sim file.
     */
    public void setInitialParameters(Cells cell, Grid grid, List<Cells> neighbors, ResourceBundle statesBundle,
                                     Map<String, String> parameters){
        myNeighbors = neighbors;
        myStatesBundle = statesBundle;
        myDevelopmentStage.setResourceBundle(myStatesBundle);
        myDevelopmentStage.initializeDevelopment(cell, parameters);
        myDevelopmentStage.checkDevelopment(cell, myNeighbors);
    }

    /**
     * This method returns the list of already changed cells to the WaTorModel,
     * so that the rules are not processed on them (so their states are not overwritten).
     * @return changedCells: an ArrayList of already updated cells
     */
    public List<Cells> getStep() {
        return changedCells;
    }

    /**
     * This method is called after the relevant game rules have been applied to a cell. It checks to see
     * whether the cell has returned a MOVE key or a SAME key. Depending on the key that was returned
     * following the rule processing for the cell, a consumer is used to call the correct method to set
     * the next state of the cell.
     * @param myCell: the current cell being considered in the state changing process
     * @param state: the state that was returned after processing all of the rules for the game.
     *             It can either be SAME (the cell should not change its current state) or MOVE
     *             (the cell needs to move to an Empty cell or Fish cell (if it is a shark)).
     */
    public void checkState(Cells myCell, int state){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(myStatesBundle.getString(SAME)), integers -> keepState(myCell),
                Integer.parseInt(myStatesBundle.getString(MOVE)), integers -> moveState(myCell)
        );
        consumerNextState(state, intMap.get(state));
    }

    /**
     * This method is called when the cell cannot move to another state,
     * or if the cell is already empty. It keeps the position and state of the cell as it was
     * before the different rules were run on it.
     * @param cell: the current cell being considered in the state-changing process.
     */
    public void keepState(Cells cell){
        cell.setMyNextState(cell.getCurrentState());}

    /**
     * This method is called when a cell returns the MOVE state after the rules processing. Depending on
     * current state of the cell, it calls the method to either move the cell to an empty cell
     * (FISH state), or to either an empty cell or a fish cell (SHARK state).
     * Or, if the cell is already empty, it keeps the state of
     * the cell as it already is.
     * @param cell: The current cell being considered in the state-changing process.
     */
    public void moveState(Cells cell){
        Map<Integer, Consumer<Integer>> intMap = Map.of(Integer.parseInt(myStatesBundle.getString(FISH)),
                integers -> moveCells(myDevelopmentStage.findRightState(cell, myNeighbors, Integer.parseInt(myStatesBundle.getString(EMPTY))),
                        cell),
                Integer.parseInt(myStatesBundle.getString(SHARK)),
                integer -> eatCells(myDevelopmentStage.findRightState(cell, myNeighbors, Integer.parseInt(myStatesBundle.getString(FISH))),
                        cell),
                Integer.parseInt(myStatesBundle.getString(EMPTY)), integer -> {});
        consumerNextState(cell.getCurrentState(), intMap.get(cell.getCurrentState()));
    }

    private void moveCells(Cells c, Cells cell){
        if(c == cell){
            keepState(cell);
            return;
        }
        changeNeighborCells(c, cell);
        myDevelopmentStage.energyVerification(cell, c, NO_ENERGY);
    }

    private void eatCells(Cells c, Cells cell) {
        if(c == cell){
            moveCells(myDevelopmentStage.findRightState(cell, myNeighbors, Integer.parseInt(myStatesBundle.getString(EMPTY))), cell);
            return;
        }
        changeNeighborCells(c, cell);
        myDevelopmentStage.energyVerification(cell,c, ENERGY_INCREASE);
    }

    private void changeNeighborCells(Cells c, Cells cell){
        c.setMyNextState(cell.getCurrentState());
        cell.setMyNextState(Integer.parseInt(myStatesBundle.getString(EMPTY)));
        changedCells.add(c);
        myDevelopmentStage.reproductionVerification(cell, c);
    }

    private void consumerNextState(int currentState, Consumer<Integer> consumer){
        consumer.accept(currentState);
    }
}
