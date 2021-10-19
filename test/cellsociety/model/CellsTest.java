package cellsociety.model;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CellsTest {

    @Test
    void setNextStateTest(){
        Cells cell = new Cells(0);
        cell.setMyNextState(1);
        assertEquals(cell.getMyNextState(), 1);
    }

    @Test
    void initialStateTest(){
        Cells cell = new Cells(0);
        assertEquals(cell.getCurrentState(), 0);
    }

    @Test
    void setNextStateNullTest(){
        Cells cell = new Cells(0);
        cell.setMyNextState(null);
        assertEquals(cell.getMyNextState(), cell.getCurrentState());
    }

    @Test
    void updateMyCurrentStateTest(){
        Cells cell = new Cells(0);
        cell.setMyNextState(1);
        cell.updateMyCurrentState();
        assertEquals(cell.getCurrentState(), 1);
    }

    @Test
    void nextCellNullAfterUpdateTest(){
        Cells cell = new Cells(0);
        cell.setMyNextState(1);
        cell.updateMyCurrentState();
        assertEquals(cell.getMyNextState(), 1);
    }
}
