package cellsociety.model;


public class Cells {

  /**
   * This class keeps track of each cell within the game. It holds information about the cells
   * current state, as well as the state it will become in the next iteration.
   */
  private static final int INITIAL_NEXT_STATE = 0;
  private int currentState;
  private Integer myNextState;

  /**
   * Constructor for cells, each cell is initialized with an initial state that is read from the csv
   * file at the start of the game
   *
   * @param initialState: read from the CSV file, holds the integer that translates to a state for
   *                      example; 0 can read dead for game of life, or tree for fire
   */
  public Cells(int initialState) {
    currentState = initialState;
    myNextState = INITIAL_NEXT_STATE;
  }

  /**
   * Method to get the next state of a cell after it has been determined through looking at its
   * neighbors. If the next state is null, this means that the current state was just updated so the
   * current state is returned.
   *
   * @return myNextState - the state that the cell will either be updated to or just has been
   * updated to
   */
  public int getMyNextState() {
    return myNextState;
  }

  /**
   * This method is called for each cell after its next state is determined as a result of the
   * applied rules. The rules will return the integer value of the state and this method takes it in
   * to set the cell's next state.
   *
   * @param myNextState: Integer value of the cell's next state (either ALIVE:1 or DEAD:0)
   */
  public void setMyNextState(Integer myNextState) {
    this.myNextState = myNextState;
  }

  /**
   * Once the next states of a cell have been determined such that we know what each cell will be in
   * the next stage of the simulation (basically once the step function traverses through every cell
   * in the grid once), this method is called to update the current state of a cell to what its next
   * state was found to be.
   */
  public void updateMyCurrentState() {
    currentState = myNextState;
  }

  /**
   * This method returns the current state of a cell
   *
   * @return: currentState, whether the cell is ALIVE or DEAD
   */
  public int getCurrentState() {
    return currentState;
  }

  /**
   * This method sets the current state of a cell
   *
   * @param myCurrentState: Integer value of the cell's current state (either ALIVE:1 or DEAD:0)
   */
  public void setCurrentState(Integer myCurrentState) {
    currentState = myCurrentState;
  }

}
