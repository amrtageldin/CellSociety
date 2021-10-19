package cellsociety.rule;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RuleGreaterThanTest {
    @Test
    void generateStateMetTest(){
        RuleGreaterThan rq = new RuleGreaterThan(2, 3);
        assertEquals(rq.generateState(4),3);
    }

    @Test
    void generateStateNullTest(){
        RuleGreaterThan rq = new RuleGreaterThan(2, 3);
        assertNull(rq.generateState(1));
    }
}
