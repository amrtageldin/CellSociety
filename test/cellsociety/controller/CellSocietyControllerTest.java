package cellsociety.controller;

import cellsociety.model.Cells;
import cellsociety.model.GameOfLifeModel;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CellSocietyControllerTest {

    @Test
    void gridSetUpPassTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/blinkers.csv");
        Cells[][] testCells = new Cells[10][10];
        testCells[0][0] = new Cells(0);
        assertEquals(c.getMyGrid()[0][0].getCurrentState(), testCells[0][0].getCurrentState());
    }

    @Test
    void gridSetUpToadPassTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/toad.csv");
        Cells[][] cell = c.getMyGrid();
        assertEquals(cell[2][2].getCurrentState(), 1);
    }

    @Test
    void gridSetUpBeeHivePassTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/beehive.csv");
        Cells[][] cell = c.getMyGrid();
        assertEquals(cell[1][2].getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerSamePassTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/corner-same.csv");
        Cells[][] cell = c.getMyGrid();
        assertEquals(cell[0][0].getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerDifferentPassTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/corner-different.csv");
        Cells[][] cell = c.getMyGrid();
        assertEquals(cell[0][0].getCurrentState(), 1);
    }

    @Test
    void gridSetUpFailTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/blinkers.csv");
        Cells[][] testCells = new Cells[10][10];
        testCells[0][0] = new Cells(1);
        assertNotEquals(c.getMyGrid()[0][0].getCurrentState(), testCells[0][0].getCurrentState());
    }

    @Test
    void gridSetUpToadFailTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/toad.csv");
        Cells[][] cell = c.getMyGrid();
        assertNotEquals(cell[2][1].getCurrentState(), 1);
    }

    @Test
    void gridSetUpBeeHiveFailTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/beehive.csv");
        Cells[][] cell = c.getMyGrid();
        assertNotEquals(cell[2][2].getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerSameFailTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/corner-same.csv");
        Cells[][] cell = c.getMyGrid();
        assertNotEquals(cell[0][4].getCurrentState(), 1);
    }

    @Test
    void gridSetUpCornerDifferentFailTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/corner-different.csv");
        Cells[][] cell = c.getMyGrid();
        assertNotEquals(cell[0][2].getCurrentState(), 1);
    }

    @Test
       void gridSetUpInvalidFileTest(){
        try {
            GameOfLifeModel g = new GameOfLifeModel();
            CellSocietyController c = new CellSocietyController(g);
            c.loadFileType("data/game_of_life/fake.csv");
        }
        catch (Exception e){
            assert(true);
        }
    }

    @Test
    void gridSetUpBlankFileTest(){
        try {
            GameOfLifeModel g = new GameOfLifeModel();
            CellSocietyController c = new CellSocietyController(g);
            c.loadFileType("data/game_of_life/blank.csv");
        }
        catch(Exception e){
            assert(true);
        }
    }

    @Test
    void gridSetUpOnlyRowsAndColumnsTest(){
        try {
            GameOfLifeModel g = new GameOfLifeModel();
            CellSocietyController c = new CellSocietyController(g);
            c.loadFileType("data/game_of_life/only-rows-and-columns.csv");
        }
        catch (NumberFormatException e){
            assert(true);
        }
    }

    @Test
    void gameSetUpPassTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/blinkers.sim");
        assertEquals(c.getMyGameType(), "GameOfLifeModel");
    }

    @Test
    void gameSetUpFailTest(){
        try {
            GameOfLifeModel g = new GameOfLifeModel();
            CellSocietyController c = new CellSocietyController(g);
            c.loadFileType("data/game_of_life/fake.sim");
        }
        catch (Exception e){
            assert(true);
        }

    }
}
