package cellsociety.model;

import cellsociety.controller.CellSocietyController;
import cellsociety.controller.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameOfLifeModelTest {
    private CellSocietyController myCellSocietyController;
    private CellSocietyModel g;

    private CellSocietyModel getMyModel(String myFile){
        myCellSocietyController.loadFileType("data/game_of_life/" + myFile + ".csv");
        myCellSocietyController.loadFileType("data/game_of_life/" + myFile + ".sim");
        return myCellSocietyController.getMyModel();
    }

    @BeforeEach
    void setUp(){
         myCellSocietyController = new CellSocietyController();
    }

    @Test
    void getNextStateTest() {
        g = getMyModel("test");
        Grid myGrid = myCellSocietyController.getMyGrid();
        g.setNextState(myGrid.getCell(0,1), 0, 1, myGrid);
        assertEquals(g.getNextState(myGrid.getCell(0,1)), 1);
    }

    @Test
    void getNextStateFailTest(){
        try {
            g = getMyModel("blank");
            Grid myGrid = myCellSocietyController.getMyGrid();
            g.setNextState(myGrid.getCell(0,1), 0, 1, myGrid);
        }
        catch (Exception e){
            assert(true);
        }
    }

    @Test
    void updateAllCellsTest(){
            g = getMyModel("test");
        Grid myGrid = myCellSocietyController.getMyGrid();
            myCellSocietyController.step();
            assertEquals(g.getNextState(myGrid.getCell(0,1)), 1);
    }

    @Test
    void updateAllCellsToadTest(){
        g = getMyModel("toad");
        Grid myGrid = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(myGrid.getCell(2,1)), 1);
    }

    @Test
    void updateAllCellsBeeHiveTest(){
        g = getMyModel("beehive");
        Grid cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell.getCell(2,1)), cell.getCell(2,1).getCurrentState());
    }

    @Test
    void updateAllCellsCornerSameTest(){
        g = getMyModel("corner-same");
        Grid cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell.getCell(0,0)), cell.getCell(0,0).getCurrentState());
    }

    @Test
    void updateAllCellsCornerDifferentTest(){
        g = getMyModel("corner-different");
        Grid cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell.getCell(0,0)), 0);
    }
    @Test
    void updateAllCellsFailTest(){
        g = getMyModel("test");
        Grid cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell.getCell(0,1)), 1);
    }

    @Test
    void updateAllCellsToadFailTest(){
        g = getMyModel("toad");
        Grid cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(cell.getCell(2,2)), 1);
    }

    @Test
    void updateAllCellsBeeHiveFailTest(){
        g = getMyModel("beehive");
        Grid cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(cell.getCell(2,1)), 0);
    }

    @Test
    void updateAllCellsCornerSameFailTest(){
        g = getMyModel("corner-same");
        Grid cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(cell.getCell(0,0)), 0);
    }

    @Test
    void updateAllCellsCornerDifferentFailTest(){
        g = getMyModel("corner-different");
        Grid cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(cell.getCell(0,1)), 1);
    }
}
