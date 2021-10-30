package cellsociety.ruleStructure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeRulesTest {
    private GameOfLifeRules myGameOfLifeRules;

    @BeforeEach
    public void setUp(){

        myGameOfLifeRules = new GameOfLifeRules("GameOfLife");
    }

    @Test
    void TestWhenAliveAndThreeNeighbors(){
        assertEquals(myGameOfLifeRules.generateNextState(3, 1), 1);
    }
    @Test
    void TestWhenAliveAndTwoNeighbors(){
        assertEquals(myGameOfLifeRules.generateNextState(2,1), 1);
    }
    @Test
    void TestWhenAliveAndLessThanTwoNeighbors(){
        assertEquals(myGameOfLifeRules.generateNextState(0,1), 0);
        assertEquals(myGameOfLifeRules.generateNextState(1,1), 0);
    }
    @Test
    void TestWhenAliveAndMoreThanThreeNeighbors(){
        assertEquals(myGameOfLifeRules.generateNextState(4,1), 0);
        assertEquals(myGameOfLifeRules.generateNextState(5,1), 0);
    }

    @Test
    void TestWhenDeadAndThreeNeighbors(){
        assertEquals(myGameOfLifeRules.generateNextState(3, 0), 1);
    }
    @Test
    void TestWhenDeadAndTwoNeighbors(){
        assertEquals(myGameOfLifeRules.generateNextState(2,0), 0);
    }
    @Test
    void TestWhenDeadAndLessThanTwoNeighbors(){
        assertEquals(myGameOfLifeRules.generateNextState(0,0), 0);
        assertEquals(myGameOfLifeRules.generateNextState(1,0), 0);
    }
    @Test
    void TestWhenDeadAndMoreThanThreeNeighbors(){
        assertEquals(myGameOfLifeRules.generateNextState(4,0), 0);
        assertEquals(myGameOfLifeRules.generateNextState(5,0), 0);
    }

}
