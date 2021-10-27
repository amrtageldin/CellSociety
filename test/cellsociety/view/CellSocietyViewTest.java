package cellsociety.view;

import cellsociety.controller.CellSocietyController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import cellsociety.model.Cells;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
import org.mockito.Mockito;
import util.DukeApplicationTest;

/**
 * @author Evelyn Cupil-Garcia
 * <p>
 * Class that does testing on the CellSocietyView class. Note that we need to update testing so that
 * there is mocking for FileChooser, currently I click on the screen.
 */
public class CellSocietyViewTest extends DukeApplicationTest {

  public static final String TITLE = "Cell Society";
  public static final String LANGUAGE = "English";
  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  private final ResourceBundle myMagicValues = ResourceBundle.getBundle(
      DEFAULT_RESOURCE_PACKAGE + "ViewTestValues");

  public static final String lifeFolderX = "lifeFolderX";
  public static final String lifeFolderY = "lifeFolderY";
  public static final String gridY = "gridY";
  public static final String simY = "simY";
  public static final String okX = "okX";
  public static final String okY = "okY";

  private CellSocietyView display;
  private Button mySimulation;
  private Button myInitialGrid;
  private Button myStart;
  private Button myPause;
  private CellSocietyController controller;

  private final List<Color> STATE_COLORS = List.of(
      Color.BLACK,      // dead cell color
      Color.WHITE       // alive cell color
  );

  @Override
  public void start(Stage stage) {
    controller = new CellSocietyController();
    display = new CellSocietyView(controller, LANGUAGE, stage);
    stage.setScene(display.setupDisplay());
    stage.setTitle(TITLE);
    stage.setFullScreen(true);
    stage.show();

    mySimulation = lookup("#SimulationType").query();
    myInitialGrid = lookup("#InitialGrid").query();
    myStart = lookup("#Play").query();
    myPause = lookup("#Start/Pause").query();
  }

  /**
   * Unit test that checks when the upload simulation type file button is pressed, that the file
   * explorer appears.
   */
  @Test
  public void initialGridAction() {
    File expectedFile = new File("data/game_of_life/blinkers.csv");
    clickOn(myInitialGrid);
    clickOn(Integer.parseInt(myMagicValues.getString(lifeFolderX)),
        Integer.parseInt(myMagicValues.getString(lifeFolderY))
        , MouseButton.PRIMARY);
    clickOn(Integer.parseInt(myMagicValues.getString(lifeFolderX)),
        Integer.parseInt(myMagicValues.getString(lifeFolderY))
        , MouseButton.PRIMARY);
    clickOn(Integer.parseInt(myMagicValues.getString(lifeFolderX)),
        Integer.parseInt(myMagicValues.getString(gridY))
        , MouseButton.PRIMARY);
    clickOn(Integer.parseInt(myMagicValues.getString(okX)),
        Integer.parseInt(myMagicValues.getString(okY)), MouseButton.PRIMARY);
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
  public void simulationTypeAction() {
    File expectedFile = new File("data/game_of_life/blinkers.sim");
    clickOn(mySimulation);
    clickOn(Integer.parseInt(myMagicValues.getString(lifeFolderX)),
        Integer.parseInt(myMagicValues.getString(lifeFolderY))
        , MouseButton.PRIMARY);
    clickOn(Integer.parseInt(myMagicValues.getString(lifeFolderX)),
        Integer.parseInt(myMagicValues.getString(lifeFolderY))
        , MouseButton.PRIMARY);
    clickOn(Integer.parseInt(myMagicValues.getString(lifeFolderX)),
        Integer.parseInt(myMagicValues.getString(simY))
        , MouseButton.PRIMARY);
    clickOn(Integer.parseInt(myMagicValues.getString(okX)),
        Integer.parseInt(myMagicValues.getString(okY)), MouseButton.PRIMARY);
    File actualFile = display.getMyFile();
    try {
      assertEquals(expectedFile.getCanonicalPath(), actualFile.getCanonicalPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Unit Test that checks that after files are uploaded that a grid appears on the screen.
   */
  @Test
  public void checkGridIsNotEmpty() {
    initialGridAction();
    simulationTypeAction();
    clickOn(myStart);
    clickOn(myPause);
    assertTrue(assertGridViewExists());
  }

  private boolean assertGridViewExists() {
    GridPane grid = lookup("#Grid").query();
    return grid.getChildren() != null;
  }

  /**
   * Test that checks that initial grid matches Grid information received from Controller.
   */
  @Test
  public void checkGridInitialization() {
    initialGridAction();
    simulationTypeAction();
    clickOn(myStart);
    clickOn(myPause);
    assertTrue(checkGridMatches());
  }

  private boolean checkGridMatches() {
    Rectangle[][] gridNodes = display.getMyGridView().getMyPaneNodes();

    for (int i = 0; i < controller.getMyGrid().length; i++) {
      for (int j = 0; j < controller.getMyGrid()[0].length; j++) {
        Rectangle cell = gridNodes[i][j];
        int color = controller.getMyGrid()[i][j].getCurrentState();
        if (cell.getFill() != STATE_COLORS.get(color)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Test that checks that the background color changed to Blue Devil Mode.
   */
  @Test
  public void checkBlueDevilBackgroundMode() {
    ComboBox options = lookup("#DropDownDefault").query();
    BorderPane pane = lookup("#Main").query();
    final String CSS_CLASS = "BlueDevilMode";
    select(options, "Blue Devil Mode");
    assertEquals(pane.getId(), CSS_CLASS);
  }

  /**
   * Test that checks that the background color changed to Dark Mode.
   */
  @Test
  public void checkDarkBackgroundMode() {
    ComboBox options = lookup("#DropDownDefault").query();
    BorderPane pane = lookup("#Main").query();
    final String CSS_CLASS = "DarkMode";
    select(options, "Dark Mode");
    assertEquals(pane.getId(), CSS_CLASS);
  }

  /**
   * Test that checks that background color changed to Light Mode.
   */
  @Test
  public void checkLightBackgroundMode() {
    ComboBox options = lookup("#DropDownDefault").query();
    BorderPane pane = lookup("#Main").query();
    final String CSS_CLASS = "LightMode";
    select(options, "Light Mode");
    assertEquals(pane.getId(), CSS_CLASS);
  }

  /**
   * Test that checks that the animation speed went down after clicking the slow down button.
   */
  @Test
  public void checkAnimationSpeedSlowedDown() {
    initialGridAction();
    simulationTypeAction();
    clickOn(myStart);
    double startSpeed = display.getAnimation().getRate();
    Button speedUp = lookup("SlowDown").query();
    clickOn(speedUp);
    double endSpeed = display.getAnimation().getRate();
    assertTrue(startSpeed > endSpeed);
  }

  /**
   * Test that checks that the animation speed went up after clicking the speedup button.
   */
  @Test
  public void checkAnimationSpeedSpedUp() {
    initialGridAction();
    simulationTypeAction();
    clickOn(myStart);
    double startSpeed = display.getAnimation().getRate();
    Button speedUp = lookup("SpeedUp").query();
    clickOn(speedUp);
    double endSpeed = display.getAnimation().getRate();
    assertTrue(startSpeed < endSpeed);
  }

  /**
   * Test that checks that the step method updates the grid after each click.
   */
  @Test
  public void checkStepMethod() {
    initialGridAction();
    simulationTypeAction();
    clickOn(myStart);
    Button step = lookup("Step").query();
    clickOn(step);
    Rectangle[][] firstStep = display.getMyGridView().getMyPaneNodes();
    clickOn(step);
    Rectangle[][] secondStep = display.getMyGridView().getMyPaneNodes();
    assertNotEquals(firstStep, secondStep);
  }


}
