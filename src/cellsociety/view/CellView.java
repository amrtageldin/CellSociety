package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Started on trying to do the other grids, not doing very well sadly.
 */
public class CellView {
  private CellColors myCellColors;
  private final List<Color> myStateColors;
  Map<String, Polygon> cellMap;
  CellSocietyController myController;

  private final String TRIANGLE = "Triangle";
  private final String SQUARE = "Square";
  private final String HEXAGON = "Hexagon";

  public CellView(CellSocietyController controller) {
    setupCellMap();
    myCellColors = new CellColors(controller);
    myStateColors = myCellColors.getColorMap();
    myController = controller;
  }

  public Polygon drawCell() {
    if (myController.getMyParametersMap().containsKey("CellShape")) {
      return cellMap.get(myController.getMyParametersMap().get("CellShape"));
    } else {
      return cellMap.get(SQUARE);
    }
  }

  public void setupCellMap() {
    Polygon triangle = new Polygon();
    triangle.getPoints().addAll(50.0, 0.0,  0.0, 50.0,100.0, 50.0);
    Polygon hexagon = new Polygon(100.0, 0.0,120.0, 20.0,120.0,
        40.0,100.0, 60.0,80.0,
        40.0,80.0, 20.0);
    //Rectangle rectangle = new Rectangle();
    cellMap = Map.of(TRIANGLE, triangle, HEXAGON, hexagon);
  }
}
