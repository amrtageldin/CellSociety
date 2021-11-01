package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.controller.Grid;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * @author Evelyn Cupil-Garcia
 * @author Luke Josephy
 * <p>
 * Class that displays the grid for the games.
 */
public class GridView {

  private final Grid myGrid;
  private final Shape[][] myPaneNodes;
  private GridPane pane;
  private final ResourceBundle myMagicValues;
  private final List<Color> stateColors;
  private final CellColors myCellColors;
  private final CellView myCellView;
  private final Boolean isGridVisible;

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
    if (controller.getMyParametersMap().containsKey("GridVisible")) {
      isGridVisible = Boolean.getBoolean(controller.getMyParametersMap().get("GridVisible"));
    } else {
      isGridVisible = true;
    }
    myGrid = controller.getMyGrid();
    myPaneNodes = new Polygon[myGrid.rowLength()][myGrid.colLength()];
    myCellColors = new CellColors(controller);
    stateColors = myCellColors.getColorMap();
    myCellView = new CellView(controller);
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
    pane.setGridLinesVisible(isGridVisible);
    drawGrid();
    return pane;
  }

  private void drawGrid() {
    for (int i = 0; i < myGrid.rowLength(); i++) {
      for (int j = 0; j < myGrid.colLength(); j++) {
        int currState = myGrid.getCell(i,j).getCurrentState();
        Polygon cell = myCellView.drawCell(i, j, findCellDimension());
        cell.setFill(stateColors.get(currState));
        setCellClickAction(cell, i, j);
        myPaneNodes[i][j] = cell;
        pane.add(myPaneNodes[i][j], j, i);
      }
    }
  }

  private void setCellClickAction(Polygon cell, int i, int j) {
    EventHandler<MouseEvent> event = event1 -> {
      int setState = myCellColors.getRandomCellState(myGrid.getCell(i,j).getCurrentState());
      myGrid.getCell(i,j).setCurrentState(setState);
      Polygon newCell = myCellView.drawCell(i, j, findCellDimension());
      newCell.setFill(myCellColors.getColorMap().get(setState));
      myPaneNodes[i][j] = newCell;
      pane.add(myPaneNodes[i][j], j, i);
      setCellClickAction(newCell, i, j);
    };
    cell.setOnMouseClicked(event);
  }

  private int findCellDimension() {
    int width = Integer.parseInt(myMagicValues.getString(screenWidth)) / myGrid.rowLength();
    int height = Integer.parseInt(myMagicValues.getString(screenHeight)) / myGrid.colLength();
    return Math.min(width, height);
  }

  public Shape[][] getMyPaneNodes() {
    return myPaneNodes;
  }
}
