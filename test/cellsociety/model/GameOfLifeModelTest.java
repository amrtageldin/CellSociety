package cellsociety.model;

import cellsociety.controller.CellSocietyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameOfLifeModelTest {
    private CellSocietyController myCellSocietyController;

    @BeforeEach
    void setUp(){
         myCellSocietyController = new CellSocietyController();
    }

    @Test
    void getNextStateTest() {
        myCellSocietyController.loadFileType("data/game_of_life/test.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        g.setNextState(cell[0][1], 0, 1, cell);
        assertEquals(g.getNextState(cell[0][1]), 1);
    }

    @Test
    void getNextStateFailTest(){
        try {
            myCellSocietyController.loadFileType("data/game_of_life/blank.csv");
            Cells[][] cell = myCellSocietyController.getMyGrid();
            g.setNextState(cell[0][1], 0, 1, cell);
        }
        catch (Exception e){
            assert(true);
        }
    }

    @Test
    void updateAllCellsTest(){
            myCellSocietyController.loadFileType("data/game_of_life/test.csv");
            Cells[][] cell = myCellSocietyController.getMyGrid();
            myCellSocietyController.step();
            assertEquals(g.getNextState(cell[0][1]), 1);
    }

    @Test
    void updateAllCellsToadTest(){
        myCellSocietyController.loadFileType("data/game_of_life/toad.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell[2][1]), 1);
    }

    @Test
    void updateAllCellsBeeHiveTest(){
        myCellSocietyController.loadFileType("data/game_of_life/beehive.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell[2][1]), cell[2][1].getCurrentState());
    }

    @Test
    void updateAllCellsCornerSameTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-same.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell[0][0]), cell[0][0].getCurrentState());
    }

    @Test
    void updateAllCellsCornerDifferentTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-different.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell[0][0]), 0);
    }
    @Test
    void updateAllCellsFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/test.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertEquals(g.getNextState(cell[0][1]), 1);
    }

    @Test
    void updateAllCellsToadFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/toad.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(cell[2][2]), 1);
    }

    @Test
    void updateAllCellsBeeHiveFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/beehive.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(cell[2][1]), 0);
    }

    @Test
    void updateAllCellsCornerSameFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-same.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(cell[0][0]), 0);
    }

    @Test
    void updateAllCellsCornerDifferentFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-different.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        myCellSocietyController.step();
        assertNotEquals(g.getNextState(cell[0][1]), 1);
    }
}
