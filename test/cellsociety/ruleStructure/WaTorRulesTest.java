package cellsociety.ruleStructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WaTorRulesTest {
    private WaTorRules myWaTorRules;
    @BeforeEach
    public void setUp(){
        Map<String, String> parameters = new HashMap<>();
        myWaTorRules = new WaTorRules("WaTor", parameters);
    }

    @Test
    void TestMoveWhenShark(){
        assertEquals(myWaTorRules.generateNextState(3, 2), 4);
        assertEquals(myWaTorRules.generateNextState(7, 2), 4);
    }

    @Test
    void TestMoveWhenFish(){
        assertEquals(myWaTorRules.generateNextState(10, 1), 4);
        assertEquals(myWaTorRules.generateNextState(4, 1), 4);
    }

    @Test
    void TestMoveWhenEmpty(){
        assertEquals(myWaTorRules.generateNextState(8, 0), 4);
        assertEquals(myWaTorRules.generateNextState(5, 0), 4);
    }

    @Test
    void TestSameWhenShark(){
        assertEquals(myWaTorRules.generateNextState(0, 2), 3);
    }

    @Test
    void TestSameWhenFish(){
        assertEquals(myWaTorRules.generateNextState(0, 1), 3);
    }

    @Test
    void TestSameWhenEmpty(){
        assertEquals(myWaTorRules.generateNextState(0, 0), 3);
    }
}
