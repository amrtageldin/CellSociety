package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.io.File;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import util.DukeApplicationTest;

/**
 * @author Luke Josephy, Evelyn Cupil-Garcia
 * <p>
 * Class that does testing on the GridView class.
 */

public class GridViewTest extends DukeApplicationTest {

  private GridView myGridView;

  @Override
  public void start(Stage stage) {
    CellSocietyController myCellSocietyController = new CellSocietyController();
    File csvFile = new File("data/game_of_life/blinkers.csv");
    File simFile = new File("data/game_of_life/blinkers.sim");
    myCellSocietyController.loadFileType(csvFile.toString());
    myCellSocietyController.loadFileType(simFile.toString());
    myGridView = new GridView(myCellSocietyController);
  }

  @Test
  void setupGrid() {
    String id = "Grid";
    GridPane gridpane = myGridView.setupGrid();
    assertEquals(id, gridpane.getId());
  }
}
