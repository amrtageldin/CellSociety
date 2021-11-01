package cellsociety.model;

import cellsociety.controller.CellSocietyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CellsTest {
    private Cells cell;

    @BeforeEach
    void setUp(){
        cell = new Cells(0);
    }


    @Test
    void setNextStateTest(){
        cell.setCurrentState(2);
        cell.setMyNextState(1);
        assertEquals(cell.getMyNextState(), 1);
    }

    @Test
    void initialStateTest(){
        assertEquals(cell.getCurrentState(), 0);
    }


    @Test
    void updateMyCurrentStateTest(){
        cell.setMyNextState(1);
        cell.updateMyCurrentState();
        assertEquals(cell.getCurrentState(), 1);
    }

    @Test
    void nextCellNullAfterUpdateTest(){
        cell.setMyNextState(1);
        cell.updateMyCurrentState();
        assertEquals(cell.getMyNextState(), 1);
    }

    @Test
    void setCurrentState(){
        cell.setCurrentState(1);
        assertEquals(cell.getCurrentState(), 1);
    }


}
