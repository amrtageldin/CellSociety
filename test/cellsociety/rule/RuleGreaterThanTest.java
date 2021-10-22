package cellsociety.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleGreaterThanTest {
    private RuleGreaterThan rq;
    @BeforeEach
    public void setUp(){
        rq = new RuleGreaterThan(2, 3);
    }

    @Test
    void generateStateMetTest(){
        assertEquals(rq.generateState(4),3);
    }

    @Test
    void generateStateNullTest(){
        assertNull(rq.generateState(1));
    }
}
