package cellsociety.ruleStructure;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FireRules extends CellSocietyRules {

  public FireRules(String myType){
    super(myType);
  }

  protected void probabilityCheck(){
    parameter = "10"; // changeLater
  }




}
