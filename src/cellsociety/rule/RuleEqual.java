package cellsociety.rule;

public class RuleEqual extends Rule{

  public RuleEqual(int comparator, Integer conditionResult){
    super(comparator,conditionResult);
  }

  @Override
  public Integer generateState(int myQuantity){
    if (myQuantity == myComparator){
      return myConditionIsMetResult;
    }
    return null;
  }

}
