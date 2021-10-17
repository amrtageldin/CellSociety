package cellsociety.rule;

abstract public class Rule {
  protected int myComparator;
  protected int myConditionIsMetResult;

  public Rule(int comparator, Integer conditionResult){
    myComparator = comparator;
    myConditionIsMetResult = conditionResult;
  }

  abstract public Integer generateState(int myQuantity);
}
