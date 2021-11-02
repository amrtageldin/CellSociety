package cellsociety.rule;

/**
 * General 'Rule' class, we want this to hold the information that essentially says "if a condition
 * is met based off a comparator, give a condition result"
 */
abstract public class Rule {

  private final int myComparator;
  private final Integer myConditionIsMetResult;

  /**
   * Given the comparator and conditionResult, store it in the rule
   *
   * @param comparator      the int that I am comparing to; for example, 'if < 2'
   * @param conditionResult the result/ state if the condition returns true
   */
  public Rule(int comparator, Integer conditionResult) {
    myComparator = comparator;
    myConditionIsMetResult = conditionResult;
  }

  protected int getMyComparator() {
    return myComparator;
  }

  protected Integer getMyConditionIsMetResult() {
    return myConditionIsMetResult;
  }

  /**
   * the manner in which we dictate if the outcome is met or not
   *
   * @param myQuantity the quantity we will compare to the comparator
   * @return will return null if met, and the conditionResult otherwise
   */
  abstract public Integer generateState(int myQuantity);
}
