package cellsociety.rule;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RuleEqualTest {
    @Test
    void generateStateMetTest(){
        RuleEqual rq = new RuleEqual(2, 3);
        assertEquals(rq.generateState(2),3);
    }

    @Test
    void generateStateNullTest(){
        RuleEqual rq = new RuleEqual(2,3);
        assertNull(rq.generateState(4));
    }
}
