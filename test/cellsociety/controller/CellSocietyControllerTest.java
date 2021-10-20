package cellsociety.controller;

import cellsociety.model.CellSocietyModel;
import cellsociety.model.Cells;
import cellsociety.model.GameOfLifeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CellSocietyControllerTest {
    private CellSocietyController myCellSocietyController;

    @BeforeEach
    void setUp(){
        myCellSocietyController = new CellSocietyController();
    }

    @Test
    void gridSetUpPassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/blinkers.csv");
        Cells[][] testCells = new Cells[10][10];
        testCells[0][0] = new Cells(0);
        assertEquals(myCellSocietyController.getMyGrid()[0][0].getCurrentState(), testCells[0][0].getCurrentState());
    }

    @Test
    void gridSetUpToadPassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/toad.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        assertEquals(cell[2][2].getCurrentState(), 1);
    }

    @Test
    void gridSetUpBeeHivePassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/beehive.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        assertEquals(cell[1][2].getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerSamePassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-same.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        assertEquals(cell[0][0].getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerDifferentPassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-different.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        assertEquals(cell[0][0].getCurrentState(), 1);
    }

    @Test
    void gridSetUpFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/blinkers.csv");
        Cells[][] testCells = new Cells[10][10];
        testCells[0][0] = new Cells(1);
        assertNotEquals(myCellSocietyController.getMyGrid()[0][0].getCurrentState(), testCells[0][0].getCurrentState());
    }

    @Test
    void gridSetUpToadFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/toad.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        assertNotEquals(cell[2][1].getCurrentState(), 1);
    }

    @Test
    void gridSetUpBeeHiveFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/beehive.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        assertNotEquals(cell[2][2].getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerSameFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-same.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        assertNotEquals(cell[0][4].getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerDifferentFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-different.csv");
        Cells[][] cell = myCellSocietyController.getMyGrid();
        assertNotEquals(cell[0][2].getCurrentState(), 1);
    }

    @Test
       void gridSetUpInvalidFileTest(){
        try {
            myCellSocietyController.loadFileType("data/game_of_life/fake.csv");
        }
        catch (Exception e){
            assert(true);
        }
    }

    @Test
    void gridSetUpBlankFileTest(){
        try {
            myCellSocietyController.loadFileType("data/game_of_life/blank.csv");
        }
        catch(Exception e){
            assert(true);
        }
    }

    @Test
    void gridSetUpOnlyRowsAndColumnsTest(){
        try {
            myCellSocietyController.loadFileType("data/game_of_life/only-rows-and-columns.csv");
        }
        catch (NumberFormatException e){
            assert(true);
        }
    }

    @Test
    void gameSetUpPassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/blinkers.sim");
        assertEquals(myCellSocietyController.getMyGameType(), "GameOfLifeModel");
    }

    @Test
    void gameSetUpFailTest(){
        try {
            myCellSocietyController.loadFileType("data/game_of_life/fake.sim");
        }
        catch (Exception e){
            assert(true);
        }

    }
}
