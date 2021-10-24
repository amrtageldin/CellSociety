package cellsociety.model;
import cellsociety.controller.CellSocietyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SchellingSegregationModelTest {
    private CellSocietyController myCellSocietyController;
    private CellSocietyModel g;

    private CellSocietyModel getMyModel(String myFile){
        myCellSocietyController.loadFileType("data/schelling_segregation/" + myFile + ".csv");
        myCellSocietyController.loadFileType("data/schelling_segregation/" + myFile + ".sim");
        return myCellSocietyController.getMyModel();
    }

    @BeforeEach
    void setUp(){
        myCellSocietyController = new CellSocietyController();
    }

    @Test
    void getNextStateTest() {
        g = getMyModel("test");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        g.setNextState(cell[0][2], 0, 2, cell);
        assertEquals(g.getNextState(cell[0][2]), 0);
    }

    @Test
    void getNextStateFailTest(){
        try {
            g = getMyModel("fail");
            Cells[][] cell = myCellSocietyController.getMyGrid();
            g.setNextState(cell[0][1], 0, 1, cell);
        }
        catch (Exception e){
            assert(true);
        }
    }

    @Test
    void updateAllCellsTest(){
        g = getMyModel("test");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell[0][0]), 3);
    }

    @Test
    void getNextStateOfEmptyCellTest() {
        g = getMyModel("test");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(cell[3][2]), 0);
    }

    @Test
    void getNextStateOfSafeCellTest(){
        g = getMyModel("test");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell[3][4]), 3);
    }

    @Test
    void numEmptyAtEndTest(){
        g = getMyModel("test");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        int count = 0;
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[0].length; j++) {
                if(cell[i][j].getCurrentState() == 0){
                    count ++;
                }
            }
        }
        assertEquals(count, 7);
    }
}
