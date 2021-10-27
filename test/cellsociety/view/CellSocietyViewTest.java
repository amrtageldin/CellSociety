package cellsociety.view;

import cellsociety.controller.CellSocietyController;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

/**
 * @author Evelyn Cupil-Garcia
 * <p>
 * Class that does testing on the CellSocietyView class.
 * TODO: Add testing for clicking on cells, testing for incorrect sim file/csv uploads, test about
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
    File csvFile = new File("data/game_of_life/blinkers.csv");
    File simFile = new File("data/game_of_life/blinkers.sim");
    controller.loadFileType(csvFile.toString());
    controller.loadFileType(simFile.toString());
    display = new CellSocietyView(controller, LANGUAGE, stage);
    stage.setScene(display.setupDisplay());
    stage.setTitle(TITLE);
    stage.setFullScreen(true);
    stage.show();

    myStart = lookup("#Play").query();
    myPause = lookup("#Start/Pause").query();
  }


  /**
   * Unit Test that checks that after files are uploaded that a grid appears on the screen.
   */
  @Test
  public void checkGridIsNotEmpty() {
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
    clickOn(myStart);
    clickOn(myPause);
    assertTrue(checkGridMatches());
  }

  private boolean checkGridMatches() {
    Rectangle[][] gridNodes = display.getMyGridView().getMyPaneNodes();

    for (int i = 0; i < controller.getMyGrid().rowLength(); i++) {
      for (int j = 0; j < controller.getMyGrid().colLength(); j++) {
        Rectangle cell = gridNodes[i][j];
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
    ComboBox<String> options = lookup("#DropDownDefault").query();
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
    clickOn(myStart);
    Button step = lookup("Step").query();
    clickOn(step);
    Rectangle[][] firstStep = display.getMyGridView().getMyPaneNodes();
    clickOn(step);
    Rectangle[][] secondStep = display.getMyGridView().getMyPaneNodes();
    assertNotEquals(firstStep, secondStep);
  }


}
