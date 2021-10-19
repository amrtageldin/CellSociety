package cellsociety.ruleStructure;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeRulesTest {
    @Test
    void returnEqualTest(){
        GameOfLifeRules g = new GameOfLifeRules();
        assertEquals(g.generateNextState(3), 1);
    }

    @Test
    void returnNullTest(){
        GameOfLifeRules g = new GameOfLifeRules();
        assertNull(g.generateNextState(2));
    }
}
