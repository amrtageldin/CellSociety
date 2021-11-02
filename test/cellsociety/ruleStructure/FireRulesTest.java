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
    void TestWhenTreeBecomesBurningIfAboveThreshold() {
        assertEquals(myFireRules.generateNextState(myThreshold + 1, 2), 1);
    }

    @Test
    void TestWhenTreeStaysTreeIfBelowOrAtThreshold() {
        assertEquals(myFireRules.generateNextState(myThreshold, 2), 2);
        assertEquals(myFireRules.generateNextState(myThreshold - 1, 2), 2);

    }
}
