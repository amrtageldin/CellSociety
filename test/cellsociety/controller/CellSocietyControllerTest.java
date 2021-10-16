package cellsociety.controller;

import cellsociety.model.Cells;
import cellsociety.model.GameOfLifeModel;
import org.junit.jupiter.api.Test;

import java.util.*;

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
    void gridSetUpFailTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/blinkers.csv");
        Cells[][] testCells = new Cells[10][10];
        testCells[0][0] = new Cells(1);
        assertNotEquals(c.getMyGrid()[0][0].getCurrentState(), testCells[0][0].getCurrentState());
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
