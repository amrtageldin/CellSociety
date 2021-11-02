package cellsociety.model.neighbors;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.controller.Grid;
import cellsociety.model.Cells;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpDownNeighborsTest {
  private CellSocietyNeighbors myUpDownNeighbors;
  private Grid myGrid;
  private List<Cells> myNeighbors;

  @BeforeEach
  void setUp() {
    myUpDownNeighbors = new UpDownNeighbors();
    myGrid = new Grid(3,3);
  }

  @Test
  void generateNeighborsForCorner() {
    myNeighbors = myUpDownNeighbors.generateNeighbors(0,0,myGrid);
    assertEquals(1, myNeighbors.size());
  }
  @Test
  void generateNeighborsForTopBottomEdge() {
    myNeighbors = myUpDownNeighbors.generateNeighbors(0,1,myGrid);
    assertEquals(1, myNeighbors.size());
  }

  @Test
  void generateNeighborsForSideEdge() {
    myNeighbors = myUpDownNeighbors.generateNeighbors(1,0,myGrid);
    assertEquals(2, myNeighbors.size());
  }
  @Test
  void generateNeighborsForInner() {
    myNeighbors = myUpDownNeighbors.generateNeighbors(1,1,myGrid);
    assertEquals(2, myNeighbors.size());
  }
}