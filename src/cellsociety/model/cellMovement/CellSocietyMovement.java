package cellsociety.model.cellMovement;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This interface is used by the Wa-Tor and Schelling Segregation games as they require movement of
 * cells instantly within the game. They both have similar functionality regarding this movement,
 * but implement the rules differently. The interface allows them both to implement their movement
 * rules as needed, and allows for concurrent updating of relevant cell states. The other games do
 * not implement this because they are updated with every step() call in the controller, while
 * Wa-Tor and Schelling need to be updated instantly so that other cells can react to changes within
 * any cell immediately.
 */
public interface CellSocietyMovement {

  void setInitialParameters(Cells cell, Grid grid, List<Cells> neighbors,
      ResourceBundle statesBundle,
      Map<String, String> parameters);

  void checkState(Cells myCell, int state);

  void keepState(Cells cell);

  void moveState(Cells cell);
}
