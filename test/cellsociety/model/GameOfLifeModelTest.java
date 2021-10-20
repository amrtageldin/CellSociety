//package cellsociety.model;
//
//import cellsociety.controller.CellSocietyController;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GameOfLifeModelTest {
//
//    @Test
//    void getNextStateTest() {
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/test.csv");
//        Cells[][] cell = c.getMyGrid();
//        g.setNextState(cell[0][1], 0, 1, cell);
//        assertEquals(g.getNextState(cell[0][1]), 1);
//    }
//
//    @Test
//    void getNextStateFailTest(){
//        try {
//            GameOfLifeModel g = new GameOfLifeModel();
//            CellSocietyController c = new CellSocietyController(g);
//            c.loadFileType("data/game_of_life/blank.csv");
//            Cells[][] cell = c.getMyGrid();
//            g.setNextState(cell[0][1], 0, 1, cell);
//        }
//        catch (Exception e){
//            assert(true);
//        }
//    }
//
//    @Test
//    void updateAllCellsTest(){
//            GameOfLifeModel g = new GameOfLifeModel();
//            CellSocietyController c = new CellSocietyController(g);
//            c.loadFileType("data/game_of_life/test.csv");
//            Cells[][] cell = c.getMyGrid();
//            c.step();
//            assertEquals(g.getNextState(cell[0][1]), 1);
//    }
//
//    @Test
//    void updateAllCellsToadTest(){
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/toad.csv");
//        Cells[][] cell = c.getMyGrid();
//        c.step();
//        assertEquals(g.getNextState(cell[2][1]), 1);
//    }
//
//    @Test
//    void updateAllCellsBeeHiveTest(){
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/beehive.csv");
//        Cells[][] cell = c.getMyGrid();
//        c.step();
//        assertEquals(g.getNextState(cell[2][1]), cell[2][1].getCurrentState());
//    }
//
//    @Test
//    void updateAllCellsCornerSameTest(){
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/corner-same.csv");
//        Cells[][] cell = c.getMyGrid();
//        c.step();
//        assertEquals(g.getNextState(cell[0][0]), cell[0][0].getCurrentState());
//    }
//
//    @Test
//    void updateAllCellsCornerDifferentTest(){
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/corner-different.csv");
//        Cells[][] cell = c.getMyGrid();
//        c.step();
//        assertEquals(g.getNextState(cell[0][0]), 0);
//    }
//    @Test
//    void updateAllCellsFailTest(){
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/test.csv");
//        Cells[][] cell = c.getMyGrid();
//        c.step();
//        assertEquals(g.getNextState(cell[0][1]), 1);
//    }
//
//    @Test
//    void updateAllCellsToadFailTest(){
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/toad.csv");
//        Cells[][] cell = c.getMyGrid();
//        c.step();
//        assertNotEquals(g.getNextState(cell[2][2]), 1);
//    }
//
//    @Test
//    void updateAllCellsBeeHiveFailTest(){
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/beehive.csv");
//        Cells[][] cell = c.getMyGrid();
//        c.step();
//        assertNotEquals(g.getNextState(cell[2][1]), 0);
//    }
//
//    @Test
//    void updateAllCellsCornerSameFailTest(){
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/corner-same.csv");
//        Cells[][] cell = c.getMyGrid();
//        c.step();
//        assertNotEquals(g.getNextState(cell[0][0]), 0);
//    }
//
//    @Test
//    void updateAllCellsCornerDifferentFailTest(){
//        GameOfLifeModel g = new GameOfLifeModel();
//        CellSocietyController c = new CellSocietyController(g);
//        c.loadFileType("data/game_of_life/corner-different.csv");
//        Cells[][] cell = c.getMyGrid();
//        c.step();
//        assertNotEquals(g.getNextState(cell[0][1]), 1);
//    }
//}
