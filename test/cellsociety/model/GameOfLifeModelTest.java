package cellsociety.model;

import cellsociety.controller.CellSocietyController;
import javafx.scene.control.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeModelTest {

    @Test
    void getNextStateTest() {
        GameOfLifeModel g = new GameOfLifeModel();
        CellSocietyController c = new CellSocietyController(g);
        c.loadFileType("data/game_of_life/test.csv");
        Cells[][] cell = c.getMyGrid();
        g.setNextState(cell[0][1], 0, 1, cell);
        assertEquals(g.getNextState(cell[0][1]), 1);
    }

    @Test
    void getNextStateFailTest(){
        try {
            GameOfLifeModel g = new GameOfLifeModel();
            CellSocietyController c = new CellSocietyController(g);
            c.loadFileType("data/game_of_life/blank.csv");
            Cells[][] cell = c.getMyGrid();
            g.setNextState(cell[0][1], 0, 1, cell);
        }
        catch (Exception e){
            assert(true);
        }
    }

    @Test
    void updateAllCellsTest(){
            GameOfLifeModel g = new GameOfLifeModel();
            CellSocietyController c = new CellSocietyController(g);
            c.loadFileType("data/game_of_life/test.csv");
            Cells[][] cell = c.getMyGrid();
            c.step();
            assertEquals(g.getNextState(cell[0][1]), 1);
    }
}
