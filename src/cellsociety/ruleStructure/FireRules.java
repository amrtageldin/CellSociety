package cellsociety.ruleStructure;



public class FireRules extends CellSocietyRules {
  private static final String PERCENT = "Percent";

  public FireRules(String myType){
    super(myType);
  }

  protected void probabilityCheck(){
    parameter = getMyParameters().get(PERCENT);
  }




}
