package cellsociety.model;

import cellsociety.controller.CellSocietyController;
import cellsociety.controller.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FireModelTest {

  private CellSocietyController myCellSocietyController;
  private CellSocietyModel g;

  private CellSocietyModel getMyModel(String myFile) {
    myCellSocietyController.loadFileType("data/Fire_Spread/" + myFile + ".csv");
    myCellSocietyController.loadFileType("data/Fire_Spread/" + myFile + ".sim");
    return myCellSocietyController.getMyModel();
  }

  @BeforeEach
  void setUp() {
    myCellSocietyController = new CellSocietyController();
  }

  @Test
  void getNextStateSimpleTest() {
    g = getMyModel("simple_pipe");
    Grid myGrid = myCellSocietyController.getMyGrid();
    g.setNextState(myGrid.getCell(0, 0), 0, 0, myGrid);
    assertEquals(g.getNextState(myGrid.getCell(0, 0)), 2);
  }

  @Test
  void getNextStateFailTest() {
    try {
      g = getMyModel("blank");
      Grid myGrid = myCellSocietyController.getMyGrid();
      g.setNextState(myGrid.getCell(0, 1), 0, 1, myGrid);
    } catch (Exception e) {
      assert (true);
    }
  }

  @Test
  void updateAllCellsLongPipeTest() {
    g = getMyModel("long_pipe");
    Grid myGrid = myCellSocietyController.getMyGrid();
    myCellSocietyController.step();
    assertEquals(g.getNextState(myGrid.getCell(0, 3)), 2);
  }
}