package cellsociety.rule;

/**
 * The general rule class that will be used for less than comparisons; if we have a number that
 * needs to be less than a threshold this class is used
 */
public class RuleLessThan extends Rule {

  /**
   * if we are less than a comparator, return condition result
   *
   * @param comparator      the number we need to be less than
   * @param conditionResult what happens if whatever number we pass later is less than the
   *                        comparator
   */
  public RuleLessThan(int comparator, Integer conditionResult) {
    super(comparator, conditionResult);
  }

  /**
   * Check comparison and return new state or null
   *
   * @param myQuantity the quantity we will compare to the comparator
   * @return mycondition result if true, null otherwise
   */
  @Override
  public Integer generateState(int myQuantity) {
    if (myQuantity < getMyComparator()) {
      return getMyConditionIsMetResult();
    }
    return null;
  }

}
