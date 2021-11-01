package cellsociety.model;

import cellsociety.controller.CellSocietyController;
import cellsociety.controller.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WaTorModelTest {
    private CellSocietyController myCellSocietyController;
    private CellSocietyModel g;

    private CellSocietyModel getMyModel(String myFile){
        myCellSocietyController.loadFileType("data/wa_tor/" + myFile + ".csv");
        myCellSocietyController.loadFileType("data/wa_tor/" + myFile + ".sim");
        return myCellSocietyController.getMyModel();
    }

    @BeforeEach
    void setUp(){
        myCellSocietyController = new CellSocietyController();
    }

    @Test
    void getNextStateTest() {
        g = getMyModel("five-reproduction-three-energy");
        Grid myGrid = myCellSocietyController.getMyGrid();
        g.setNextState(myGrid.getCell(0,2), 0, 2, myGrid);
        assertEquals(g.getNextState(myGrid.getCell(0,2)), 0);
    }

    @Test
    void getNextStateFailTest(){
        try {
            g = getMyModel("five-reproduction-three-energy");
            Grid myGrid = myCellSocietyController.getMyGrid();
            g.setNextState(myGrid.getCell(0,1), 0, 1, myGrid);
        }
        catch (Exception e){
            assert(true);
        }
    }

    @Test
    void updateAllCellsTest(){
        g = getMyModel("five-reproduction-three-energy");
        Grid myGrid = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(myGrid.getCell(0,1)), 2);
    }

    @Test
    void getNextStateOfEmptyCellTest() {
        g = getMyModel("five-reproduction-three-energy");
        Grid myGrid = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(myGrid.getCell(3,1)), 0);
    }

    @Test
    void getNextStateOfSameCellTest(){
        g = getMyModel("eight-reproduction-one-energy");
        Grid myGrid = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(myGrid.getCell(0,0)), 1);
    }

    @Test
    void numEmptyAtEndOfFirstStepTest(){
        g = getMyModel("five-reproduction-three-energy");
        Grid myGrid = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        int count = 0;
        for (int i = 0; i < myGrid.rowLength(); i++) {
            for (int j = 0; j < myGrid.colLength(); j++) {
                if(myGrid.getCell(i,j).getCurrentState() == 0){
                    count ++;
                }
            }
        }
        assertEquals(count, 7);
    }

    @Test void stepTwiceAndCheckStatesTest(){
        g = getMyModel("eight-reproduction-one-energy");
        Grid myGrid = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        myCellSocietyController.step();
        assertEquals(g.getNextState(myGrid.getCell(1,1)), 0);
    }
}
