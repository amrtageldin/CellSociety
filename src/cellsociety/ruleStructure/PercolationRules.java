package cellsociety.ruleStructure;

import java.util.Map;

/**
 * Percolation Rules; generate the rule that determines if the randomly number we generated is
 * enough to deem our current cell's next state percolated, open, or closed
 */
public class PercolationRules extends CellSocietyRules {

  /**
   * Initialize the PercolationRules
   * @param type use String "Percolation" to create the rules
   * @param parameters no relevant parameters are passed
   */
  public PercolationRules(String type, Map<String, String> parameters) { super(type, parameters);}

}
