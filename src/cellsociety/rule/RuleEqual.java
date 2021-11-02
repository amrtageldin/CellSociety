package cellsociety.rule;

/**
 * The general rule class that will be used for equality comparisons; if we have a number
 * that needs to be exactly equal
 */
public class RuleEqual extends Rule{

  public RuleEqual(int comparator, Integer conditionResult){
    super(comparator,conditionResult);
  }

  @Override
  public Integer generateState(int myQuantity){
    if (myQuantity == getMyComparator()){
      return getMyConditionIsMetResult();
    }
    return null;
  }

}
