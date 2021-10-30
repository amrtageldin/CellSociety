package cellsociety.ruleStructure;


import java.util.Map;

public class FireRules extends CellSocietyRules {
  private static final String PERCENT = "Percent";

  public FireRules(String type, Map<String, String> parameters) { super(type, parameters);}

  protected void probabilityCheck(){
    parameter = getMyParameters().get(PERCENT);
  }




}
