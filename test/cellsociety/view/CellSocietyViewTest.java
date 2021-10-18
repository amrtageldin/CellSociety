package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.CellSocietyModel;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * @author Evelyn Cupil-Garcia
 * <p>
 * Class that does testing on the CellSocietyView class.
 */
public class CellSocietyViewTest extends DukeApplicationTest {

  public static final String TITLE = "Cell Society";
  public static final String LANGUAGE = "English";
  public static final int FILE_X = 200;
  public static final int SIM_Y = 155;
  public static final int GRID_Y = 190;
  public static final int OK_X = 480;
  public static final int OK_Y = 330;
  private CellSocietyView display;
  private Button mySimulation;
  private Button myInitialGrid;

  @Override
  public void start(Stage stage) {
    CellSocietyModel model = new CellSocietyModel();
    CellSocietyController controller = new CellSocietyController(model);
    display = new CellSocietyView(controller, model, LANGUAGE, stage);
    stage.setScene(display.setupDisplay());
    stage.setTitle(TITLE);
    stage.setFullScreen(true);
    stage.show();

    mySimulation = lookup("#SimulationType").query();
    myInitialGrid = lookup("#InitialGrid").query();
  }

  /**
   * Unit test that checks when the upload simulation type file button is pressed, that the file
   * explorer appears.
   */
  @Test
  public void simulationTypeAction() {
    File expectedFile = new File("data/game_of_life/blinkers.csv");
    clickOn(mySimulation);
    clickOn(FILE_X, SIM_Y, MouseButton.PRIMARY);
    clickOn(OK_X, OK_Y, MouseButton.PRIMARY);
    File actualFile = display.getMyFile();
    try {
      assertEquals(expectedFile.getCanonicalPath(), actualFile.getCanonicalPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Unit test that checks when the upload initial grid file button is pressed, that the file
   * explorer appears.
   */
  @Test
  public void initialGridAction() {
    File expectedFile = new File("data/game_of_life/blinkers.sim");
    clickOn(myInitialGrid);
    clickOn(FILE_X, GRID_Y, MouseButton.PRIMARY);
    clickOn(OK_X, OK_Y, MouseButton.PRIMARY);
    File actualFile = display.getMyFile();
    try {
      assertEquals(expectedFile.getCanonicalPath(), actualFile.getCanonicalPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
