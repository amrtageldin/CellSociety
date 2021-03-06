package cellsociety.view;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import util.DukeApplicationTest;

/**
 * @author Luke Josephy
 * <p>
 * Class that does testing on the FactoryComponents class.
 */

public class FactoryComponentsTest extends DukeApplicationTest {

  private FactoryComponents myFactoryComponents;

  @Override
  public void start(Stage stage) {
    String language = "English";
    myFactoryComponents = new FactoryComponents(language);
  }

  /**
   * Test that checks whether the label created by makeLabel has the proper text / ID
   */
  @Test
  void makeLabel() {
    String id = "Start/Pause";
    String id2 = "";
    Label label = myFactoryComponents.makeLabel(id);
    assertEquals(id, label.getId());
    assertNotEquals(id2, label.getId());
  }

  /**
   * Test that checks whether setLabel functions properly in rewriting over the current label
   * text/ID
   */
  @Test
  void setLabel() {
    String id = "Start/Pause";
    String id2 = "Step";
    Label label = myFactoryComponents.makeLabel(id);

    myFactoryComponents.setLabel(label, id2);
    assertEquals(id2, label.getText());
    assertNotEquals(id, label.getText());
  }

  /**
   * Test that checks whether makeSlider creates a Slider with the proper minimum, maximum, default,
   * and increment values. Also checks for proper label
   */
  @Test
  void makeSlider() {
    String label = "Start/Pause";
    int min = 0;
    int max = 5;
    int increment = 1;
    Slider slider = myFactoryComponents.makeSlider(label, min, max, increment);

    assertEquals(min, slider.getMin());
    assertEquals(max, slider.getMax());
    assertEquals(min, slider.getValue());
    assertEquals(increment, (int) slider.getBlockIncrement());
    assertEquals(label, slider.getId());
  }

  /**
   * Test that checks whether makeDropDownMenu creates a ComboBox with the proper label, drop down
   * options, and ID
   */
  @Test
  void makeDropDownMenu() {
    String label = "Start/Pause";
    String[] options = {"LightMode", "DarkMode"};
    ComboBox<String> dropdown = myFactoryComponents.makeDropDownMenu(label, options);

    assertEquals(label, dropdown.getId());
    assertEquals(label, dropdown.getPromptText());
    assertEquals("Light Mode", dropdown.getItems().get(0));
    assertEquals("Dark Mode", dropdown.getItems().get(1));
  }

  /**
   * Test that checks whether makeButton creates a Button with the proper label / ID .
   */
  @Test
  void makeButton() {
    String label = "Start/Pause";
    Node button = myFactoryComponents.makeButton(label, null);
    assertEquals(label, button.getId());
  }
}

