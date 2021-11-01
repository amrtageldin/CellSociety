package cellsociety.neighbors;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RectangleNeighborsTest {
  private CellSocietyNeighbors myRectangleNeighbors;
  private Grid myGrid;
  private List<Cells> myNeighbors;

  @BeforeEach
  void setUp() {
    myRectangleNeighbors = new RectangleNeighbors();
    myGrid = new Grid(3,3);
  }

  @Test
  void generateNeighborsForCorner() {
    myNeighbors = myRectangleNeighbors.generateNeighbors(0,0,myGrid);
    assertEquals(2, myNeighbors.size());
  }

  @Test
  void generateNeighborsForEdge() {
    myNeighbors = myRectangleNeighbors.generateNeighbors(0,1,myGrid);
    assertEquals(3, myNeighbors.size());
  }
  @Test
  void generateNeighborsForInner() {
    myNeighbors = myRectangleNeighbors.generateNeighbors(1,1,myGrid);
    assertEquals(4, myNeighbors.size());
  }
}