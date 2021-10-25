package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.Cells;
import java.awt.Dimension;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Cell;
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
 */
public class GridView {

  private final Cells[][] myGrid;
  private Rectangle[][] myPaneNodes;
  private GridPane pane;
  private static final int GAP = 1;
  private static final int SCREEN_WIDTH = 1500;
  private static final int SCREEN_HEIGHT = 600;

  private final List<Color> STATE_COLORS = List.of(
      Color.BLACK,      // dead cell color
      Color.WHITE,       // alive cell color
      Color.BLUE,          // water cell color
      Color.GRAY
  );

  /**
   * Constructor that initializes the Grid.
   *
   * @param controller CellSocietyController that provides grid parsed from csv file.
   */
  public GridView(CellSocietyController controller) {
    myGrid = controller.getMyGrid();
    myPaneNodes = new Rectangle[myGrid.length][myGrid[0].length];
  }

  /**
   * Function that creates the grid to give to CellSociety
   *
   * @return pane that holds the cells
   */
  public GridPane setupGrid() {
    pane = new GridPane();
    pane.setId("Grid");
    pane.setVgap(GAP);
    pane.setHgap(GAP);
    pane.setGridLinesVisible(true);
    drawGrid();
    return pane;
  }

  private void drawGrid() {
    for (int i = 0; i < myGrid.length; i++) {
      for (int j = 0; j < myGrid[0].length; j++) {
        int currState = myGrid[i][j].getCurrentState();
        Rectangle cell = drawCell(STATE_COLORS.get(currState));
        setCellClickAction(cell, i, j);
        myPaneNodes[i][j] = cell;
        pane.add(myPaneNodes[i][j], j, i);
      }
    }
  }

  private void setCellClickAction(Rectangle cell, int i, int j) {
    EventHandler<MouseEvent> event = event1 -> {
      int setState = (myGrid[i][j].getCurrentState()==0) ? 1:0;
      myGrid[i][j].setCurrentState(setState);
      Rectangle newCell = drawCell(STATE_COLORS.get(setState));
      myPaneNodes[i][j] = newCell;
      pane.add(myPaneNodes[i][j], j, i);
    };
    cell.setOnMouseClicked(event);
  }

  private Rectangle drawCell(Paint currState) {
    Rectangle cell = new Rectangle(findCellDimension(), findCellDimension());
    cell.setFill(currState);
    return cell;
  }

  private Dimension getGridPaneDimensions() {
    Dimension dim = new Dimension((int) pane.getWidth(), (int) pane.getHeight());
    return dim;
  }

  private int findCellDimension() {
    int width = SCREEN_WIDTH / myGrid.length;
    int height = SCREEN_HEIGHT / myGrid[0].length;
    return Math.min(width, height);
  }

  public Rectangle[][] getMyPaneNodes() {
    return myPaneNodes;
  }
}
