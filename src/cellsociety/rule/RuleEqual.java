package cellsociety.rule;

/**
 * The general rule class that will be used for equality comparisons; if we have a number
 * that needs to be exactly equal to another number this class is used
 */
public class RuleEqual extends Rule{

  /**
   * if we are equal to a comparator, return condition result
   * @param comparator the number we need to be exactly equal to
   * @param conditionResult what happens if we are exactly equal to another number
   */
  public RuleEqual(int comparator, Integer conditionResult){
    super(comparator,conditionResult);
  }

  /**
   * Check comparison and return new state or null
   * @param myQuantity the quantity we will compare to the comparator
   * @return mycondition result if true, null otherwise
   */
  @Override
  public Integer generateState(int myQuantity){
    if (myQuantity == getMyComparator()){
      return getMyConditionIsMetResult();
    }
    return null;
  }

}
