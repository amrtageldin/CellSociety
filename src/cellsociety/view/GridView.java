package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.Cells;
import java.util.ArrayList;
import java.util.List;;
import java.util.Map;
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
 */
public class GridView {

  private final Cells[][] myGrid;
  private final Rectangle[][] myPaneNodes;
  private GridPane pane;
  private static final int GAP = 1;
  private static final int SCREEN_WIDTH = 1500;
  private static final int SCREEN_HEIGHT = 600;
  private static final String GAME_OF_LIFE = "GameOfLife";
  private static final String FIRE = "Fire";
  private static final String PERCOLATION = "Percolation";
  private static final String SCHELLING_SEGREGATION = "SchellingSegregation";

  private final List<Color> LIFE_STATE_COLORS = List.of(
      Color.BLACK,      // dead cell color
      Color.WHITE       // alive cell color
  );

  private final List<Color> FIRE_STATE_COLORS = List.of(
      Color.RED, // fire color
      Color.GREEN, // tree color
      Color.GREEN, // tree color
      Color.LIGHTGREY // empty
  );

  private final List<Color> PERCOLATE_STATE_COLORS = List.of(
      Color.BLUE, // percolated color
      Color.WHITE, // open color
      Color.WHITE, // open color
      Color.BLACK // closed color
  );

  private final List<Color> SS_STATE_COLORS = List.of(
    Color.WHITE, // empty color
    Color.WHITE, // empty color
    Color.PURPLE, // group a color
    Color.ORANGE // group b color
  );


  private List<Color> stateColors;

  /**
   * Constructor that initializes the Grid.
   *
   * @param controller CellSocietyController that provides grid parsed from csv file.
   */
  public GridView(CellSocietyController controller) {
    myGrid = controller.getMyGrid();
    myPaneNodes = new Rectangle[myGrid.length][myGrid[0].length];
    stateColors = initializeGameMap(controller.getMyGameType());
  }

  private List<Color> initializeGameMap(String gameType) {
    Map<String, List<Color>> map = Map.of(GAME_OF_LIFE, LIFE_STATE_COLORS, FIRE, FIRE_STATE_COLORS,
        PERCOLATION, PERCOLATE_STATE_COLORS, SCHELLING_SEGREGATION, SS_STATE_COLORS);
    return map.get(gameType);
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
        Rectangle cell = drawCell(stateColors.get(currState));
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
      Rectangle newCell = drawCell(stateColors.get(setState));
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

  private int findCellDimension() {
    int width = SCREEN_WIDTH / myGrid.length;
    int height = SCREEN_HEIGHT / myGrid[0].length;
    return Math.min(width, height);
  }

  public Rectangle[][] getMyPaneNodes() {
    return myPaneNodes;
  }
}
