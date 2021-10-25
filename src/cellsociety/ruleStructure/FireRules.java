package cellsociety.ruleStructure;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FireRules extends CellSocietyRules {
  private String probPercent;

  public FireRules(String myType){
    super(myType);
  }

  protected void probabilityCheck(){
    parameter = "30"; // changeLater
  }




}
