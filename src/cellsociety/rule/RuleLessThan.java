package cellsociety.rule;

public class RuleLessThan extends Rule{

  public RuleLessThan(int comparator, Integer conditionResult){
    super(comparator,conditionResult);
  }

  @Override
  public Integer generateState(int myQuantity){
    if (myQuantity < getMyComparator()){
      return getMyConditionIsMetResult();
    }
    return null;
  }

}
