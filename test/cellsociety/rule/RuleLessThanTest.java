package cellsociety.rule;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RuleLessThanTest {
    @Test
    void generateStateMetTest(){
        RuleLessThan rq = new RuleLessThan(2, 3);
        assertEquals(rq.generateState(1),3);
    }

    @Test
    void generateStateNullTest(){
        RuleLessThan rq = new RuleLessThan(2, 3);
        assertNull(rq.generateState(4));
    }
}
