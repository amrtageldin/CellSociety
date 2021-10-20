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
        assertEquals(myPercolationRules.generateNextState(3, 0), 1);
    }



    @Test
    void TestWhenAliveAndThreeNeighbors(){
        assertEquals(myPercolationRules.generateNextState(3, 1), 1);
    }
    @Test
    void TestWhenAliveAndTwoNeighbors(){
        assertEquals(myPercolationRules.generateNextState(2,1), 1);
    }
    @Test
    void TestWhenAliveAndLessThanTwoNeighbors(){
        assertEquals(myPercolationRules.generateNextState(0,1), 0);
        assertEquals(myPercolationRules.generateNextState(1,1), 0);
    }
    @Test
    void TestWhenAliveAndMoreThanThreeNeighbors(){
        assertEquals(myPercolationRules.generateNextState(4,1), 0);
        assertEquals(myPercolationRules.generateNextState(5,1), 0);
    }

    @Test
    void TestWhenDeadAndThreeNeighbors(){
        assertEquals(myPercolationRules.generateNextState(3, 0), 1);
    }
    @Test
    void TestWhenDeadAndTwoNeighbors(){
        assertEquals(myPercolationRules.generateNextState(2,0), 0);
    }
    @Test
    void TestWhenDeadAndLessThanTwoNeighbors(){
        assertEquals(myPercolationRules.generateNextState(0,0), 0);
        assertEquals(myPercolationRules.generateNextState(1,0), 0);
    }
    @Test
    void TestWhenDeadAndMoreThanThreeNeighbors(){
        assertEquals(myPercolationRules.generateNextState(4,0), 0);
        assertEquals(myPercolationRules.generateNextState(5,0), 0);
    }

}
