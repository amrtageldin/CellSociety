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
    void gameSetUpPassTest(){
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/blinkers.sim");
        assertEquals(c.getMyGameType(), "GameOfLifeModel");
    }
}
