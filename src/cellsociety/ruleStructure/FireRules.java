package cellsociety.ruleStructure;


import java.util.Map;

/**
 * Fire Rules; generate the rule that determines if the randomly number we generated is enough to
 * deem our current cell's next state burned
 */
public class FireRules extends CellSocietyRules {

  /**
   * Initialize the FireRules
   * @param type use String "Percolation" to create the rules
   * @param parameters parameter to deem threshold for if a fire burns or not
   */
  public FireRules(String type, Map<String, String> parameters) { super(type, parameters);}


}
