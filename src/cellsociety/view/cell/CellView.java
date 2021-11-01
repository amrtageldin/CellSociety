package cellsociety.view.cell;

import cellsociety.controller.CellSocietyController;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.shape.Polygon;


/**
 * @author Evelyn Cupil-Garcia
 *
 * Class that implements the shape type for a cell in a game.
 */
public class CellView {
  CellSocietyController myController;
  ResourceBundle myMagicValues;

  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  public static final String TRIANGLE = "Triangle";
  public static final String SQUARE = "Square";
  public static final String HEXAGON = "Hexagon";
  private final static String r = "r";
  private final static String n = "n";
  private final static String tileWidth = "tileWidth";
  private final static String oneHalf = "half";
  private final static String num = "const";

  /**
   * Constructor that initializes the controller and magic values.
   * @param controller
   */
  public CellView(CellSocietyController controller) {
    myController = controller;
    myMagicValues = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "MagicValues");
  }

  public Polygon drawCell(double x, double y, double r) {
    if (myController.getMyParametersMap().containsKey("CellShape")) {
      return determineCell(myController.getMyParametersMap().get("CellShape"), x, y, r);
    } else {
      return drawSquare(x, y, r);
    }
  }

  public Polygon determineCell(String cellShape, double x, double y, double r) {
    Map<String, Polygon> map = Map.of(HEXAGON, drawHexagon(x, y), SQUARE, drawSquare(x, y, r),
        TRIANGLE, drawTriangle(x, y, r));
    if (map.containsKey(cellShape)) {
      return map.get(cellShape);
    } else {
      return map.get(SQUARE);
    }
  }

  public Polygon drawHexagon(double x, double y) {
    Polygon hexagon = new Polygon();
    double TILE_WIDTH = Double.parseDouble(myMagicValues.getString(tileWidth));
    double radius = Double.parseDouble(myMagicValues.getString(r));
    double innerRadius = Double.parseDouble(myMagicValues.getString(n));
    double var = Double.parseDouble(myMagicValues.getString(num));
    double half = Double.parseDouble(myMagicValues.getString(oneHalf));
    hexagon.getPoints().addAll(x, y,
        x, y + radius,
        x + innerRadius, y + radius * var,
        x + TILE_WIDTH, y + radius,
        x + TILE_WIDTH, y,
        x + innerRadius, y - radius * half);
    return hexagon;
  }

  public Polygon drawSquare(double x, double y, double r) {
    Polygon square = new Polygon();
    square.getPoints().addAll(x, y, x, y + r, x + r, y + r, x + r, y);
    return square;
  }

  public Polygon drawTriangle(double x, double y, double r) {
    double half = Double.parseDouble(myMagicValues.getString(oneHalf));
    Polygon triangle = new Polygon();
    triangle.getPoints().addAll(x, y, x + r * half, y + r * half, x + r , y);
    return triangle;
  }
}
