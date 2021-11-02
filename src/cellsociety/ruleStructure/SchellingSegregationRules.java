package cellsociety.ruleStructure;


import java.util.Map;

/**
 * Percolation Rules; generate the rule that determines if the randomly number we generated is
 * enough to deem if our cell will remain the same / if it's satisfied
 */

public class SchellingSegregationRules extends CellSocietyRules{
  private static final String PERCENT = "Percent";

  /**
   * Initialize the SegregationRules
   *
   * @param type       use String "SchellingSegregation" to create the rules
   * @param parameters pass the parameter to deem if a cell is satisfied
   */
  public SchellingSegregationRules(String type, Map<String, String> parameters) { super(type, parameters);}

  protected void percentCheck(){
    parameter = getMyParameters().get(PERCENT);
  }

}
