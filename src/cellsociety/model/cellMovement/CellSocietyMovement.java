package cellsociety.model.cellMovement;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public interface CellSocietyMovement {

    void setInitialParameters(Cells cell, Grid grid, List<Cells> neighbors, ResourceBundle statesBundle,
                              Map<String, String> parameters);

    void checkState(Cells myCell, int state);

    void keepState(Cells cell);

    void moveState(Cells cell);
}
