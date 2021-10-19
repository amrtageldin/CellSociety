package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.Cells;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author Evelyn Cupil-Garcia
 * <p>
 * Class that displays the grid for the games.
 */
public class GridView {

  private CellSocietyController myController;
  private Cells[][] myGrid;
  private GridPane pane;
  private static final int GAP = 1;
  private static final int SCREEN_WIDTH = 1500;
  private static final int SCREEN_HEIGHT = 600;

  private final List<Color> STATE_COLORS = List.of(
      Color.BLACK,      // dead cell color
      Color.WHITE       // alive cell color
  );

  /**
   * Constructor that initializes the Grid.
   *
   * @param controller
   */
  public GridView(CellSocietyController controller) {
    myController = controller;
    myGrid = myController.getMyGrid();

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
        pane.add(cell, j, i);
      }
    }
  }

  private Rectangle drawCell(Paint currState) {
    Rectangle cell = new Rectangle(findCellDimension(), findCellDimension());
    cell.setFill(currState);
    return cell;
  }

  private int findCellDimension() {
    int width = SCREEN_WIDTH / myGrid.length;
    int height = SCREEN_HEIGHT / myGrid[0].length;
    if (width > height) {
      return height;
    } else {
      return width;
    }
  }
}
