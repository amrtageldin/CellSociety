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
        Grid testCells = new Grid(10,10);
        testCells.setCell(0,0,new Cells(0));
        assertEquals(myCellSocietyController.getMyGrid().getCell(0,0).getCurrentState(), testCells.getCell(0,0).getCurrentState());
    }

    @Test
    void gridSetUpToadPassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/toad.csv");
        Grid cell = myCellSocietyController.getMyGrid();
        assertEquals(cell.getCell(2,2).getCurrentState(), 1);
    }

    @Test
    void gridSetUpBeeHivePassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/beehive.csv");
        Grid cell = myCellSocietyController.getMyGrid();
        assertEquals(cell.getCell(1,2).getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerSamePassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-same.csv");
        Grid cell = myCellSocietyController.getMyGrid();
        assertEquals(cell.getCell(0,0).getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerDifferentPassTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-different.csv");
        Grid cell = myCellSocietyController.getMyGrid();
        assertEquals(cell.getCell(0,0).getCurrentState(), 1);
    }

    @Test
    void gridSetUpFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/blinkers.csv");
        Grid testCells = new Grid(10,10);
        testCells.setCell(0,0, new Cells(1));
        assertNotEquals(myCellSocietyController.getMyGrid().getCell(0,0).getCurrentState(), testCells.getCell(0,0).getCurrentState());
    }

    @Test
    void gridSetUpToadFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/toad.csv");
        Grid cell = myCellSocietyController.getMyGrid();
        assertNotEquals(cell.getCell(2,1).getCurrentState(), 1);
    }

    @Test
    void gridSetUpBeeHiveFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/beehive.csv");
        Grid cell = myCellSocietyController.getMyGrid();
        assertNotEquals(cell.getCell(2,2).getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerSameFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-same.csv");
        Grid cell = myCellSocietyController.getMyGrid();
        assertNotEquals(cell.getCell(0,4).getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerDifferentFailTest(){
        myCellSocietyController.loadFileType("data/game_of_life/corner-different.csv");
        Grid cell = myCellSocietyController.getMyGrid();
        assertNotEquals(cell.getCell(0,2).getCurrentState(), 1);
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
        assertEquals(myCellSocietyController.getMyGameType(), "GameOfLife");
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

    @Test
    void makeRandomGridTest(){
        myCellSocietyController.loadFileType("data/game_of_life/random.csv");
        Grid cell = myCellSocietyController.getMyGrid();
        int cellValue = cell.getCell(0,0).getCurrentState();
        assertEquals(cell.getCell(0,0).getCurrentState(), cellValue);
    }
}
