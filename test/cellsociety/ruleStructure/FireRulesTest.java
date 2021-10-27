package cellsociety.ruleStructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FireRulesTest {

    private FireRules myFireRules;
    private int myThreshold;

    @BeforeEach
    public void setUp() {
        myFireRules = new FireRules("GameOfLife");
        myThreshold = 10;
//         TREE = 3
//        EMPTY = 0
//        BURNING = 2

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
//
//    @Test
//    void TestWhenTreeBecomesBurningIfAboveOrAtThreshold() {
//        assertEquals(myFireRules.generateNextState(myThreshold + 1, 3), 2);
//        assertEquals(myFireRules.generateNextState(myThreshold, 3), 2);
//    }
//
//    @Test
//    void TestWhenTreeStaysTreeIfBelowThreshold() {
//        assertEquals(myFireRules.generateNextState(myThreshold - 1, 3), 3);
//
//    }
}
