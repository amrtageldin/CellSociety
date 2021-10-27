package cellsociety.rule;

public class RuleGreaterThan extends Rule {

  public RuleGreaterThan(int comparator, Integer conditionResult){
    super(comparator,conditionResult);
  }

  @Override
  public Integer generateState(int myQuantity){
    if (myQuantity > getMyComparator()){
      return getMyConditionIsMetResult();
    }
    return null;
  }


}
