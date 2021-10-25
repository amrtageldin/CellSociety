package cellsociety.ruleStructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchellingSegregationRulesTest {
    private SchellingSegregationRules mySchellingSegregationRules;

    @BeforeEach
    public void setUp(){
        mySchellingSegregationRules = new SchellingSegregationRules();
    }

    @Test
    void TestWhenValidProportionOfNeighbors(){
        assertEquals(mySchellingSegregationRules.generateNextState(30, 3), 1);
        assertEquals(mySchellingSegregationRules.generateNextState(75, 2), 1);
    }

    @Test
    void TestWhenInvalidProportionOfNeighbors(){
        assertEquals(mySchellingSegregationRules.generateNextState(10, 3), 4);
        assertEquals(mySchellingSegregationRules.generateNextState(4, 2), 4);
    }

    @Test
    void TestWhenEmptyAndValidTest(){
        assertEquals(mySchellingSegregationRules.generateNextState(80, 0), 1);
        assertEquals(mySchellingSegregationRules.generateNextState(55, 0), 1);
    }

    @Test
    void TestWhenEmptyAndInvalidProportionOfNeighbors(){
        assertEquals(mySchellingSegregationRules.generateNextState(3, 0), 4);
        assertEquals(mySchellingSegregationRules.generateNextState(29, 0), 4);
    }
}
