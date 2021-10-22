package cellsociety.rule;

import cellsociety.ruleStructure.PercolationRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleEqualTest {
    private RuleEqual rq;
    @BeforeEach
    public void setUp(){
        rq = new RuleEqual(2, 3);
    }


    @Test
    void generateStateMetTest(){
        assertEquals(rq.generateState(2),3);
    }

    @Test
    void generateStateNullTest(){
        assertNull(rq.generateState(4));
    }
}
