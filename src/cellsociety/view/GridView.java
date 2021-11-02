package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.Cells;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author Evelyn Cupil-Garcia
 * @author Luke Josephy
 * <p>
 * Class that displays the grid for the games.
 * TODO: Refactor Rectangle to Polygon instead to decide on cell shape.
 */
public class GridView {

  private final Cells[][] myGrid;
  private final Rectangle[][] myPaneNodes;
  private GridPane pane;
  private final ResourceBundle myMagicValues;
  private final List<Color> stateColors;
  private final CellColors myCellColors;

  public final String gap = "gap";
  public final String screenWidth = "screenWidth";
  public final String screenHeight = "screenHeight";
  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";

  /**
   * Constructor that initializes the Grid.
   *
   * @param controller CellSocietyController that provides grid parsed from csv file.
   */
  public GridView(CellSocietyController controller) {
    myGrid = controller.getMyGrid();
    myPaneNodes = new Rectangle[myGrid.length][myGrid[0].length];
    myCellColors = new CellColors(controller.getMyGameType());
    stateColors = myCellColors.getColorMap();
    myMagicValues = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "MagicValues");
  }

  /**
   * Function that creates the grid to give to CellSociety
   *
   * @return pane that holds the cells
   */
  public GridPane setupGrid() {
    pane = new GridPane();
    pane.setId("Grid");
    pane.setVgap(Integer.parseInt(myMagicValues.getString(gap)));
    pane.setHgap(Integer.parseInt(myMagicValues.getString(gap)));
    pane.setGridLinesVisible(true);
    drawGrid();
    return pane;
  }

  private void drawGrid() {
    for (int i = 0; i < myGrid.length; i++) {
      for (int j = 0; j < myGrid[0].length; j++) {
        int currState = myGrid[i][j].getCurrentState();
        Rectangle cell = drawCell(stateColors.get(currState));
        setCellClickAction(cell, i, j);
        myPaneNodes[i][j] = cell;
        pane.add(myPaneNodes[i][j], j, i);
      }
    }
  }

  private void setCellClickAction(Rectangle cell, int i, int j) {
    EventHandler<MouseEvent> event = event1 -> {
      int setState = myCellColors.getRandomCellState(myGrid[i][j].getCurrentState());
      myGrid[i][j].setCurrentState(setState);
      Rectangle newCell = drawCell(stateColors.get(setState));
      myPaneNodes[i][j] = newCell;
      pane.add(myPaneNodes[i][j], j, i);
      setCellClickAction(newCell, i, j);
    };
    cell.setOnMouseClicked(event);
  }

  private Rectangle drawCell(Paint currState) {
    Rectangle cell = new Rectangle(findCellDimension(), findCellDimension());
    cell.setFill(currState);
    return cell;
  }

  private int findCellDimension() {
    int width = Integer.parseInt(myMagicValues.getString(screenWidth)) / myGrid.length;
    int height = Integer.parseInt(myMagicValues.getString(screenHeight)) / myGrid[0].length;
    return Math.min(width, height);
  }

  public Rectangle[][] getMyPaneNodes() {
    return myPaneNodes;
  }
}
