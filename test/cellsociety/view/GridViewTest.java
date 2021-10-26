package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.Cells;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import util.DukeApplicationTest;

/**
 * @author Luke Josephy
 * <p>
 * Class that does testing on the GridView class.
 */

public class GridViewTest extends DukeApplicationTest {

  private Cells[][] myGrid;
  private GridView myGridView;
  private CellSocietyController myCellSocietyController;
  private Rectangle[][] myPaneNodes;
  private GridPane pane;
  private ResourceBundle myMagicValues;
  private List<Color> stateColors;
  private CellColors myCellColors;

  public final String gap = "gap";
  public final String screenWidth = "screenWidth";
  public final String screenHeight = "screenHeight";
  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";

  @Override
  public void start(Stage stage) {
    myCellSocietyController = new CellSocietyController();
    myGridView = new GridView(myCellSocietyController);
    myPaneNodes = new Rectangle[myGrid.length][myGrid[0].length];
    myCellColors = new CellColors(myCellSocietyController.getMyGameType());
    stateColors = myCellColors.getColorMap();
    myMagicValues = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "MagicValues");
  }

  @Test
  void setupGrid() {
    String id = "Grid";
    GridPane gridpane = myGridView.setupGrid();
    assertEquals(id, gridpane.getId());
  }
}
