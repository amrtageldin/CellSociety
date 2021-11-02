package cellsociety.model.cellMovement;

import cellsociety.model.Cells;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class takes care of all of the reproduction and energy states for the Sharks
 * and Fish in WaTor. It keeps a map for both and takes in the max reproduction and
 * starting energy from the sim file describing the game. For each cell, the reproduction
 * stage is checked and once it equals the max reproduction, the cell reproduces to have
 * one of its neighboring cells also be of the same type. If the current cell is a Shark
 * cell, then the energy map is also checked. If a shark loses energy it immediately dies
 * and becomes empty, but every time it eats a fish cell it gains energy.
 */
public class WaTorDeveloper {
  private static final String FISH = "FISH";
  private static final String SHARK = "SHARK";
  private static final String EMPTY = "EMPTY";
  private static final int REPRODUCTION_INCREASE = 1;
  private static final int REPRODUCTION_INITIAL = 0;
  private static final int NO_ENERGY = 0;
  private static final int ENERGY_DECREASE = -1;
  private static final String REPRODUCTION_KEY = "ReproductionMax";
  private static final String ENERGY_KEY = "EnergyInitial";
  private int REPRODUCTION_MAX;
  private int ENERGY_INITIAL;
  private ResourceBundle statesBundle;
  private final Map<Cells, Integer> reproductionMap = new HashMap<>();
  private final Map<Cells, Integer> energyMap = new HashMap<>();

  /**
   * This method is called when the WaTorDeveloper class is first set up such that
   * it can use the proper resource bundle throughout the class
   * @param bundle: The resource bundle with the relevant states for the WaTor game
   */
  public void setResourceBundle(ResourceBundle bundle){
    statesBundle = bundle;
  }

  /**
   * This method is called to initialize all of the relevant parameters for
   * cell development. The values for initial energy and maximum reproduction are
   * read from the parameters map (which was initiated using the relevant .sim game file)
   * When this method is called, each cell begins its reproduction cycle (at 0), and if
   * the cell is a Shark cell it also starts with its initial energy.
   * @param myCell: the current cell being considered in the state-change process
   * @param parameters: a map of all of the relevant parameters for the WaTor game.
   *                  We need the max reproduction value and initial energy values.
   */
  public void initializeDevelopment(Cells myCell, Map<String, String> parameters){
    REPRODUCTION_MAX = Integer.parseInt(parameters.get(REPRODUCTION_KEY));
    ENERGY_INITIAL = Integer.parseInt(parameters.get(ENERGY_KEY));
    updateReproduction(myCell, REPRODUCTION_INCREASE, REPRODUCTION_INITIAL);
    updateEnergy(myCell, ENERGY_DECREASE, ENERGY_INITIAL);
  }

  private void updateReproduction(Cells myCell, int reproductionIncrease, int reproductionInitial){
    if(myCellStateEquals(myCell.getCurrentState(), SHARK) ||
        myCellStateEquals(myCell.getCurrentState(), FISH) ||
        myCellStateEquals(myCell.getMyNextState(), SHARK) ||
        myCellStateEquals(myCell.getMyNextState(), FISH)) {
      if (!reproductionMap.containsKey(myCell)) {
        reproductionMap.put(myCell, reproductionInitial);
        return;
      }
      reproductionMap.put(myCell, reproductionMap.get(myCell) + reproductionIncrease);
    }
    if(myCellStateEquals(myCell.getMyNextState(), EMPTY)){
      reproductionMap.remove(myCell);
    }
  }

  private boolean myCellStateEquals(int myCellState, String givenState) {
    return myCellState == Integer.parseInt(statesBundle.getString(givenState));
  }

  /**
   * This method is called each time a Shark cell moves. If the shark cell
   * moves to an empty cell, its energy is transferred to that cell (since the
   * empty cell is now a shark cell). If the shark cell moves to a fish cell,
   * its energy is transferred and then increased (since it has eaten the cell).
   * @param cell: the current shark cell
   * @param c: the cell that the shark cell is moving to (can either be empty or a fish)
   * @param energyUpdate: the energy that needs to be added to the shark cell before moving
   *                    (either 0 if moving to an empty cell, or 1 if eating a fish cell)
   */
  public void energyVerification(Cells cell, Cells c, int energyUpdate){
    if(energyMap.containsKey(cell)){
      updateEnergy(cell, energyUpdate, ENERGY_INITIAL);
      updateEnergy(c, NO_ENERGY, energyMap.get(cell));
      energyMap.remove(cell);
    }
  }

  private void updateEnergy(Cells myCell, int energyFactor, int energyInitial){
    if(!energyMap.containsKey(myCell) && (
        myCellStateEquals(myCell.getCurrentState(), SHARK)
            || myCellStateEquals(myCell.getMyNextState(), SHARK))){
      energyMap.put(myCell,energyInitial);
    }
    else if(energyMap.containsKey(myCell)){
      energyMap.put(myCell, energyMap.get(myCell) + energyFactor);
    }
  }

  /**
   * This method is called before each cell state is processed. It checks to
   * see if the cell is out of energy (shark cells only), or if the cell
   * needs to reproduce (fish or shark cell).
   * @param myCell: the current cell being considered for state-change
   * @param myNeighbors: the neighbors of the current cell, in case the cell
   *                   needs to reproduce its neighbors are passed in so that
   *                   it can populate one of its empty neighbor cells with its
   *                   own state.
   */
  public void checkDevelopment(Cells myCell, List<Cells> myNeighbors){
    checkEnergy(myCell);
    checkReproduction(myCell, myNeighbors);
  }

  private void checkEnergy(Cells myCell){
    if(energyMap.containsKey(myCell)) {
      if (energyMap.get(myCell) <= NO_ENERGY) {
        outOfEnergy(myCell);
      }
    }
  }

  private void outOfEnergy(Cells myCell) {
    myCell.setMyNextState(Integer.parseInt(statesBundle.getString(EMPTY)));
    myCell.updateMyCurrentState();
    energyMap.remove(myCell);
  }

  /**
   * When a fish or a shark cell moves, it needs to update the cell that it moves
   * to with its current reproduction stage. So this method simply transfers a cell's
   * reproduction level to the cell that it is moving to (the cell that will be the
   * current cell's state in the next iteration).
   * @param cell: the current cell being considered for state-changing
   * @param c: the cell that the current cell is moving to, which needs all of
   *         the current cell's reproduction information.
   */
  public void reproductionVerification(Cells cell, Cells c){
    updateReproduction(c, REPRODUCTION_INITIAL, reproductionMap.get(cell));
    reproductionMap.remove(cell);
  }

  private void checkReproduction(Cells myCell, List<Cells> myNeighbors){
    if(reproductionMap.containsKey(myCell)) {
      if (reproductionMap.get(myCell) >= REPRODUCTION_MAX) {
        reachedReproductionMax(myCell, myNeighbors);
      }
    }
  }

  private void reachedReproductionMax(Cells myCell, List<Cells> myNeighbors) {
    Cells c = findRightState(myCell, myNeighbors, Integer.parseInt(statesBundle.getString(EMPTY)));
    reproduceCells(c, myCell);
    reproductionMap.remove(myCell);
    updateReproduction(c, REPRODUCTION_INCREASE, REPRODUCTION_INITIAL);
    updateReproduction(myCell, REPRODUCTION_INCREASE, REPRODUCTION_INITIAL);
  }

  private void reproduceCells(Cells c, Cells myCell){
    myCell.updateMyCurrentState();
    c.setMyNextState(myCell.getCurrentState());
    c.updateMyCurrentState();
  }

  /**
   * This method finds a valid neighbor of a cell within a given state.
   * For example, if a Shark wants to return all of its fish neighbors, this method
   * will return the first fish cell it finds. If no fish cell neighbors are found,
   * the shark itself is returned.
   * @param cell: the current cell being considered for state-changing
   * @param myNeighbors: a list of all of the surrounding cells of the current cell
   * @param stateWanted: the state that a neighbor cell should be to be returned as a
   *                   valid neighbor
   * @return either c or cell: the valid neighbor cell or the current cell itself
   * in the case of 0 valid neighbors.
   */
  public Cells findRightState(Cells cell, List<Cells> myNeighbors, int stateWanted){
    for(Cells c : myNeighbors){
      if(c.getCurrentState() == stateWanted){
        return c;
      }
    }
    return cell;
  }
}
