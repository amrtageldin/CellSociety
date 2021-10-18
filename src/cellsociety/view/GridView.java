package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.Cells;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * @author Evelyn Cupil-Garcia
 *
 * Class that displays the grid for the games.
 */
public class GridView {
  private CellSocietyController myController;
  private Cells[][] myGrid;

  /**
   * Constructor that initializes the Grid.
   * @param controller
   */
  public GridView(CellSocietyController controller) {
    myController = controller;
    myGrid = myController.getMyGrid();

  }

  /**
   * Function that creates the grid to give to CellSociety
   * @return pane that holds the cells
   */
  public GridPane setupGrid() {
    GridPane pane = new GridPane();
    pane.setId("Grid");
    determineColumnNum(pane);
    determineRowNum(pane);
    pane.setGridLinesVisible(true);
    return pane;
  }

  private void determineColumnNum(GridPane pane) {
    for(int i = 0; i < myGrid[0].length; i++) {
      ColumnConstraints columnNum = new ColumnConstraints();
      pane.getColumnConstraints().add(columnNum);
    }
  }

  private void determineRowNum(GridPane pane) {
    for (int i = 0; i < myGrid.length; i++) {
      RowConstraints rowNum = new RowConstraints();
      pane.getRowConstraints().add(rowNum);
    }
  }






}
