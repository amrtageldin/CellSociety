package cellsociety.rule;

abstract public class Rule {
  private final int myComparator;
  private final Integer myConditionIsMetResult;

  public Rule(int comparator, Integer conditionResult){
    myComparator = comparator;
    myConditionIsMetResult = conditionResult;
  }

  public int getMyComparator(){ return myComparator;}

  public Integer getMyConditionIsMetResult(){ return myConditionIsMetResult;}

  abstract public Integer generateState(int myQuantity);
}
