package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.util.List;
import javafx.scene.paint.Color;

public class CellView {
  private CellColors myCellColors;
  private final List<Color> myStateColors;

  public CellView(CellSocietyController controller) {
    myCellColors = new CellColors(controller);
    myStateColors = myCellColors.getColorMap();
  }

}
