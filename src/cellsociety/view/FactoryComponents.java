package cellsociety.view;

import java.lang.reflect.Method;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.ResourceBundle;
import javafx.scene.control.Slider;

/**
 * Class that creates individual components for the View.
 *
 * @author Evelyn Cupil-Garcia
 * @author Luke Josephy
 */
public class FactoryComponents {

  private final ResourceBundle myResources;
  private final ResourceBundle myResourceMethods;

  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";

  /**
   * Constructor for the FactoryComponents class that initializes it's resource bundle.
   *
   * @param language What language property will be used (English or Spanish).
   */
  public FactoryComponents(String language) {
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    myResourceMethods = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Methods");

  }

  /**
   * Method that creates a Label.
   *
   * @param id Label identifier.
   * @return Label with identifier.
   */
  public Label makeLabel(String id) {
    Label label = new Label(myResources.getString(id));
    return (Label) setId(id, label);
  }

  /**
   * Method that edits text within a Label
   *
   * @param id Label identifier.
   *
   */
  public void setLabel(Label label, String id) {
    label.setText(myResources.getString(id));
  }

  /**
   * Method that creates a Button.
   *
   * @param label   Button identifier for id/text.
   * @return Node that has id as label and the button itself.
   */
  public Node makeButton(String label, CellSocietyView cell) {
    Button result = new Button();
    result.setText(myResources.getString(label));
    result.setOnAction(handler -> {
          try {
            Method m = cell.getClass().getDeclaredMethod(myResourceMethods.getString(label));
            m.setAccessible(true);
            m.invoke(cell);
          }
          catch (Exception e) {
            throw new RuntimeException("Improper configuration", e);
          }
        }
    );
    return setId(label, result);
  }

  /**
   * Method that creates a slider.
   *
   * @param label Slider identifier.
   * @param min min number on slider.
   * @param max max number on slider.
   * @return slider component.
   */
  public Slider makeSlider(String label, int min, int max, int increment) {
    Slider slider = new Slider();
    slider.setMin(min);
    slider.setMax(max);
    slider.setValue(min);
    slider.setBlockIncrement(increment);
    slider.setSnapToTicks(true);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    return (Slider) setId(label, slider);
  }

  public ComboBox<String> makeDropDownMenu(String id, String[] labels) {
    ComboBox<String> dropdown = new ComboBox<>();
    dropdown.setId(id);
    dropdown.setPromptText(myResources.getString(id));
    for (String label : labels) {
      dropdown.getItems().add(myResources.getString(label));
    }
    return dropdown;
  }

  private Node setId(String id, Node node) {
    node.setId(id);
    return node;
  }


}
