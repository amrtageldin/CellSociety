package cellsociety.model.neighbors;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiagonalNeighborsTest {
  private DiagonalNeighbors myDiagonalNeighbors;
  private Grid myGrid;
  private List<Cells> myNeighbors;

  @BeforeEach
  void setUp() {
    myDiagonalNeighbors = new DiagonalNeighbors();
    myGrid = new Grid(3,3);
  }

  @Test
  void generateNeighborsForCorner() {
    myNeighbors = myDiagonalNeighbors.generateNeighbors(0,0,myGrid);
    assertEquals(1, myNeighbors.size());
  }

  @Test
  void generateNeighborsForEdge() {
    myNeighbors = myDiagonalNeighbors.generateNeighbors(0,1,myGrid);
    assertEquals(2, myNeighbors.size());
  }
  @Test
  void generateNeighborsForInner() {
    myNeighbors = myDiagonalNeighbors.generateNeighbors(1,1,myGrid);
    assertEquals(4, myNeighbors.size());
  }
}