package cellsociety.ruleStructure;

import java.util.Map;

/**
 * GameOfLife Rules; generate the rule that determines if the randomly number we generated is enough
 * to deem our current cell's next state alive or dead
 */

public class GameOfLifeRules extends CellSocietyRules {
  /**
   * Initialize the GameOfLifeRules
   *
   * @param type       use String "GameOfLife" to create the rules
   * @param parameters no relevant parameters are passed
   */

  public GameOfLifeRules(String type, Map<String, String> parameters) { super(type, parameters);}
}
