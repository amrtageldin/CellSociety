package cellsociety.model.neighbors;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FullNeighborsTest {
  private CellSocietyNeighbors myFullNeighbors;
  private Grid myGrid;
  private List<Cells> myNeighbors;

  @BeforeEach
  void setUp() {
    myFullNeighbors = new FullNeighbors();
    myGrid = new Grid(3,3);
  }

  @Test
  void generateNeighborsForCorner() {
    myNeighbors = myFullNeighbors.generateNeighbors(0,0,myGrid);
    assertEquals(3, myNeighbors.size());
  }

  @Test
  void generateNeighborsForEdge() {
    myNeighbors = myFullNeighbors.generateNeighbors(0,1,myGrid);
    assertEquals(5, myNeighbors.size());
  }
  @Test
  void generateNeighborsForInner() {
    myNeighbors = myFullNeighbors.generateNeighbors(1,1,myGrid);
    assertEquals(8, myNeighbors.size());
  }
}