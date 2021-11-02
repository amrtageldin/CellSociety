package cellsociety.view;

import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * @author Evelyn Cupil-Garcia
 * @author Luke Josephy
 * <p>
 * Class that grabs the required colors for each cell for a specific CellSociety Game.
 */
public class CellColors {

  public static final List<Color> LIFE_STATE_COLORS = List.of(
      Color.BLACK,      // dead cell color
      Color.WHITE       // alive cell color
  );

  public static final List<Color> FIRE_STATE_COLORS = List.of(
      Color.LIGHTGREY, // empty color
      Color.LIGHTGREY, // empty color
      Color.RED, // burning color
      Color.GREEN // tree color
  );

  public static final List<Color> PERCOLATE_STATE_COLORS = List.of(
      Color.BLACK, // closed color
      Color.BLACK, // closed color
      Color.BLUE, // percolated color
      Color.WHITE // open color
  );

  public static final List<Color> SS_STATE_COLORS = List.of(
      Color.WHITE, // empty color
      Color.WHITE, // empty color
      Color.PURPLE, // group a color
      Color.ORANGE // group b color
  );

  public static final List<Color> WATOR_COLORS = List.of(
      Color.WHITE, // empty color
      Color.ORANGE, // fish color
      Color.DARKGREY // shark color
  );

  private static final String GAME_OF_LIFE = "GameOfLife";
  private static final String FIRE = "Fire";
  private static final String PERCOLATION = "Percolation";
  private static final String SCHELLING_SEGREGATION = "SchellingSegregation";
  private static final String WA_TOR = "WaTor";

  private final List<Color> colorMap;

  /**
   * Constructor that initializes the colorMap for a specific game given the game type.
   *
   * @param gameType String that represents the game type.
   */
  public CellColors(String gameType) {
    Map<String, List<Color>> map = Map.of(GAME_OF_LIFE, LIFE_STATE_COLORS, FIRE, FIRE_STATE_COLORS,
        PERCOLATION, PERCOLATE_STATE_COLORS, SCHELLING_SEGREGATION, SS_STATE_COLORS, WA_TOR,
        WATOR_COLORS);
    colorMap = map.get(gameType);
  }

  /**
   * Getter that returns the color map for a specific game.
   *
   * @return map of cell colors.
   */
  public List<Color> getColorMap() {
    return colorMap;
  }

  /**
   * Method that randomly returns a state from the possible states for a specific game.
   *
   * @return integer representing the index of the cell color's state.
   */
  public int getRandomCellState(int currState) {
    Random rand = new Random();
    int upperbound = colorMap.size();
    int result = rand.nextInt(upperbound);
    if (result != currState) {
      return result;
    } else {
      return getRandomCellState(currState);
    }
  }

}
