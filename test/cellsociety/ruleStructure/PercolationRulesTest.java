package cellsociety.ruleStructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PercolationRulesTest {
    private PercolationRules myPercolationRules;

    @BeforeEach
    public void setUp(){
         myPercolationRules = new PercolationRules();
    }

    @Test
    void TestNeverChangesIfClosed(){
        assertEquals(myPercolationRules.generateNextState(0, 0), 0);
        assertEquals(myPercolationRules.generateNextState(1, 0), 0);
        assertEquals(myPercolationRules.generateNextState(2, 0), 0);
    }

    @Test
    void TestNeverChangesIfPercolated(){
        assertEquals(myPercolationRules.generateNextState(0, 2), 2);
        assertEquals(myPercolationRules.generateNextState(1, 2), 2);
        assertEquals(myPercolationRules.generateNextState(2, 2), 2);
    }

    @Test
    void TestIfOpenAndNoOpenNeighbors(){
        assertEquals(myPercolationRules.generateNextState(0, 3), 3);
    }

    @Test
    void TestIfOpenAndOpenNeighbors(){
        assertEquals(myPercolationRules.generateNextState(3, 3), 2);
    }


}
