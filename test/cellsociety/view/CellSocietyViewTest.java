package cellsociety.view;

import cellsociety.controller.CellSocietyController;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

import java.io.File;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.base.NodeMatchers;
import util.DukeApplicationTest;

/**
 * @author Evelyn Cupil-Garcia
 * <p>
 * Class that does testing on the CellSocietyView class.
 * TODO: Add testing for clicking on cells, test about
 *  section for each game.
 */
public class CellSocietyViewTest extends DukeApplicationTest {

  public static final String TITLE = "Cell Society";
  public static final String LANGUAGE = "English";

  private CellSocietyView display;
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

    myStart = lookup("#Play").query();
    myPause = lookup("#Start/Pause").query();
  }

  private void loadFiles() {
    File csvFile = new File("data/game_of_life/blinkers.csv");
    File simFile = new File("data/game_of_life/blinkers.sim");
    controller.loadFileType(csvFile.toString());
    controller.loadFileType(simFile.toString());
  }


  /**
   * Unit Test that checks that after files are uploaded that a grid appears on the screen.
   */
  @Test
  public void checkGridIsNotEmpty() {
    loadFiles();
    clickOn(myStart);
    clickOn(myPause);
    assertTrue(assertGridViewExists());
  }

  private boolean assertGridViewExists() {
    VBox grid = lookup("#Grid").query();
    return grid.getChildren() != null;
  }

  /**
   * Test that checks that initial grid matches Grid information received from Controller.
   */
  @Test
  public void checkGridInitialization() {
    loadFiles();
    clickOn(myStart);
    clickOn(myPause);
    assertTrue(checkGridMatches());
  }

  private boolean checkGridMatches() {
    Shape[][] gridNodes = display.getMyGridView().getMyPaneNodes();

    for (int i = 0; i < controller.getMyGrid().rowLength(); i++) {
      for (int j = 0; j < controller.getMyGrid().colLength(); j++) {
        Shape cell = gridNodes[i][j];
        int color = controller.getMyGrid().getCell(i,j).getCurrentState();
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
    loadFiles();
    clickOn(myStart);
    clickOn(myPause);
    Node histogramButton = lookup("#CheckHistogram").query();
    clickOn(histogramButton);
    ComboBox<String> options = lookup("#DropDownDefault").query();
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
    loadFiles();
    clickOn(myStart);
    clickOn(myPause);
    Node histogramButton = lookup("#CheckHistogram").query();
    clickOn(histogramButton);
    ComboBox<String> options = lookup("#DropDownDefault").query();
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
    loadFiles();
    clickOn(myStart);
    clickOn(myPause);
    Node histogramButton = lookup("#CheckHistogram").query();
    clickOn(histogramButton);
    ComboBox<String> options = lookup("#DropDownDefault").query();
    BorderPane pane = lookup("#Main").query();
    final String CSS_CLASS = "LightMode";
    select(options, "Light Mode");
    assertEquals(pane.getId(), CSS_CLASS);
  }

  /**
   * Test that checks that the animation speed went down after clicking the slow-down button.
   */
  @Test
  public void checkAnimationSpeedSlowedDown() {
    loadFiles();
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
    loadFiles();
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
    loadFiles();
    clickOn(myStart);
    Button step = lookup("Step").query();
    clickOn(step);
    Shape[][] firstStep = display.getMyGridView().getMyPaneNodes();
    clickOn(step);
    Shape[][] secondStep = display.getMyGridView().getMyPaneNodes();
    assertNotEquals(firstStep, secondStep);
  }

  /**
   * Error checking test when we do not hit start and hit other buttons such as step, pause, and
   * speed buttons.
   */
  @Test
  public void checkStepError() {
    Button step = lookup("Step").query();
    clickOn(step);
    verifyThat("OK", NodeMatchers.isVisible());
  }


  /**
   * Error checking test when we do not hit start and hit other buttons such as step, pause, and
   * speed buttons.
   */
  @Test
  public void checkSlowDownError() {
    Button speedUp = lookup("SlowDown").query();
    clickOn(speedUp);
    verifyThat("OK", NodeMatchers.isVisible());
  }

  /**
   * Error checking test when we do not hit start and hit other buttons such as step, pause, and
   * speed buttons.
   */
  @Test
  public void checkSpeedUpError() {
    Button speedUp = lookup("SpeedUp").query();
    clickOn(speedUp);
    verifyThat("OK", NodeMatchers.isVisible());
  }

  /**
   * Error checking test when we do not hit start and hit other buttons such as step, pause, and
   * speed buttons.
   */
  @Test
  public void checkPauseError() {
    clickOn(myPause);
    verifyThat("OK", NodeMatchers.isVisible());
  }

  /**
   * Error checking test when we do not upload files, and we hit start to initialize the game.
   */
  @Test
  public void checkStartError() {
    clickOn(myStart);
    verifyThat("OK", NodeMatchers.isVisible());
  }

  /**
   * Test that checks that the About section is populated.
   */
  @Test
  public void checkAboutSection() {
    loadFiles();
    clickOn(myStart);
    Node myAboutPane = lookup("#AboutPane").query();
    assertTrue(myAboutPane.isVisible());
  }

  /**
   * Testing that color on a grid is not the default if the sim file has its own colors.
   */
  @Test
  public void checkCellColor() {
    File csvFile = new File("data/game_of_life/beehive.csv");
    File simFile = new File("data/game_of_life/beehive.sim");
    controller.loadFileType(csvFile.toString());
    controller.loadFileType(simFile.toString());
    clickOn(myStart);
    clickOn(myPause);
    Shape[][] grid = display.getMyGridView().getMyPaneNodes();
    Paint cellColor = grid[0][0].getFill();
    CellColors myCellColors = new CellColors(controller);
    Paint defaultCellColor = myCellColors.getDefaultColorMap().get(controller.getMyGrid().getCell(0,0).getCurrentState());
    assertNotEquals(cellColor, defaultCellColor);
  }

  /**
   * Testing that the shape of the grid is different based on parameter.
   */
  @Test
  public void checkCellShape() {
    loadFiles();
    clickOn(myStart);
    clickOn(myPause);
    Shape[][] grid = display.getMyGridView().getMyPaneNodes();
    CellView myCellView = new CellView(controller);
    Shape actualCell = grid[0][0];
    Shape expectedCell = myCellView.drawCell(0, 0, Math.min(grid.length, grid[0].length));
    assertEquals(actualCell.toString(), expectedCell.toString());
  }

}
