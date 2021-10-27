package cellsociety.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleLessThanTest {
    private RuleLessThan rq;
    @BeforeEach
    public void setUp(){
        rq = new RuleLessThan(2, 3);
    }
    @Test
    void generateStateMetTest(){
        assertEquals(rq.generateState(1),3);
    }

    @Test
    void generateStateNullTest(){
        assertNull(rq.generateState(4));
    }
}
