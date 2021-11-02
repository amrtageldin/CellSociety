package cellsociety.ruleStructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PercolationRulesTest {
    private PercolationRules myPercolationRules;

    @BeforeEach
    public void setUp(){
        Map<String, String> parameters = new HashMap<>();
         myPercolationRules = new PercolationRules("Percolation", parameters);
    }

    @Test
    void TestIfOpenAndNoOpenNeighbors(){
        assertEquals(myPercolationRules.generateNextState(0, 2), 2);
    }

    @Test
    void TestIfOpenAndOpenNeighbors(){
        assertEquals(myPercolationRules.generateNextState(3, 2), 1);
    }


}
