package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import util.DukeApplicationTest;

/**
 * @author Luke Josephy
 * <p>
 * Class that does testing on the CellColors class.
 */

public class CellColorsTest extends DukeApplicationTest {

  public static final List<Color> LIFE_STATE_COLORS = List.of(
      Color.BLACK,      // dead cell color
      Color.WHITE       // alive cell color
  );

  private CellColors myCellColors;

  @Override
  public void start(Stage stage) {
    CellSocietyController controller = new CellSocietyController();
    controller.loadFileType("data/game_of_life/blinkers.csv");
    controller.loadFileType("data/game_of_life/blinkers.sim");
    myCellColors = new CellColors(controller);
  }

  /**
   * Test that checks whether the colorMap returned for a specific game is the proper color map
   * defined above
   */
  @Test
  void getColorMap() {
    List<Color> colorMap = myCellColors.getColorMap();
    assertEquals(colorMap, LIFE_STATE_COLORS);
  }

  /**
   * Test that ensures that getRandomCellState does not return the same color as the current state
   */
  @Test
  void getRandomCellState() {
    int randomColor = myCellColors.getRandomCellState(0);

    assertNotEquals(randomColor, 0);
    assertNotEquals(myCellColors.getColorMap().get(randomColor), myCellColors.getColorMap().get(0));
  }
}