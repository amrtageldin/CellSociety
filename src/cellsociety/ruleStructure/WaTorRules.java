package cellsociety.ruleStructure;


import java.util.Map;

/**
 * WaTor Rules; generate the rule that determines if the randomly number we generated is
 * enough to deem if our cell will move or stay the same
 */
public class WaTorRules extends CellSocietyRules{

    /**
     * Initialize the WatorRules
     * @param type use String "WaTor" to create the rules
     * @param parameters pass the parameter number to deem if a cell will move or stay the same
     */
    public WaTorRules(String type, Map<String, String> parameters) { super(type, parameters);}
    }


