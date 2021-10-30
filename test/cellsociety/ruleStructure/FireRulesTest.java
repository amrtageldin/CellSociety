package cellsociety.ruleStructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FireRulesTest {

    private FireRules myFireRules;
    private int myThreshold;

    @BeforeEach
    public void setUp() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Percent","10");
        myFireRules = new FireRules("Fire", parameters);
        myThreshold = 10;

    }


    @Test
    void TestWhenEmptyStaysEmpty() {
        assertEquals(myFireRules.generateNextState(myThreshold + 1, 0), 0);
        assertEquals(myFireRules.generateNextState(myThreshold, 0), 0);
        assertEquals(myFireRules.generateNextState(myThreshold - 1, 0), 0);
    }


    @Test
    void TestWhenBurningBecomesEmpty() {
        assertEquals(myFireRules.generateNextState(myThreshold + 1, 2), 0);
        assertEquals(myFireRules.generateNextState(myThreshold, 2), 0);
        assertEquals(myFireRules.generateNextState(myThreshold - 1, 2), 0);
    }

    @Test
    void TestWhenTreeBecomesBurningIfAboveThreshold() {
        assertEquals(myFireRules.generateNextState(myThreshold + 1, 3), 2);
    }

    @Test
    void TestWhenTreeStaysTreeIfBelowOrAtThreshold() {
        assertEquals(myFireRules.generateNextState(myThreshold, 3), 3);
        assertEquals(myFireRules.generateNextState(myThreshold - 1, 3), 3);

    }
}
