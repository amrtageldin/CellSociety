package cellsociety.neighbors;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeftRightNeighborsTest {
  private LeftRightNeighbors myLeftRightNeighbors;
  private Grid myGrid;
  private List<Cells> myNeighbors;

  @BeforeEach
  void setUp() {
    myLeftRightNeighbors = new LeftRightNeighbors();
    myGrid = new Grid(3,3);
  }

  @Test
  void generateNeighborsForCorner() {
    myNeighbors = myLeftRightNeighbors.generateNeighbors(0,0,myGrid);
    assertEquals(1, myNeighbors.size());
  }

  @Test
  void generateNeighborsForEdge() {
    myNeighbors = myLeftRightNeighbors.generateNeighbors(0,1,myGrid);
    assertEquals(1, myNeighbors.size());
  }
  @Test
  void generateNeighborsForInner() {
    myNeighbors = myLeftRightNeighbors.generateNeighbors(1,1,myGrid);
    assertEquals(2, myNeighbors.size());
  }
}