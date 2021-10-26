package cellsociety.view;

import java.util.ResourceBundle;
import java.util.function.BooleanSupplier;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
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
  private ResourceBundle myResources;
  private ResourceBundle myResourceMethods;
  private String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  private String language = "English";

  @Override
  public void start(Stage stage) {
    myFactoryComponents = new FactoryComponents(language);
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    myResourceMethods = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Methods");
  }

  @Test
  void makeLabel() {
    String id = "Start/Pause";
    String id2 = "";
    Label label = myFactoryComponents.makeLabel(id);
    assertEquals(id, label.getId());
    assertNotEquals(id2, label.getId());
  }

  @Test
  void setLabel() {
    String id = "Start/Pause";
    String id2 = "Step";
    Label label = myFactoryComponents.makeLabel(id);

    myFactoryComponents.setLabel(label, id2);
    assertEquals(id2, label.getText());
    assertNotEquals(id, label.getText());
  }

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

  @Test
  void makeDropDownMenu() {
    String label = "Start/Pause";
    String[] options = {"LightMode", "DarkMode"};
    ComboBox dropdown = myFactoryComponents.makeDropDownMenu(label, options);

    assertEquals(label, dropdown.getId());
    assertEquals(label, dropdown.getPromptText());
    assertEquals("Light Mode", dropdown.getItems().get(0));
    assertEquals("Dark Mode", dropdown.getItems().get(1));
  }

  @Test
  void makeButton() {
    String label = "Start/Pause";
    Node button = myFactoryComponents.makeButton(label, null);
    assertEquals(label, button.getId());
  }
}

