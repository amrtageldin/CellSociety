package cellsociety.view;

import java.lang.reflect.Method;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
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
   * Constructor for the FactoryComponents class that initializes its resource bundle.
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
          String meth = myResourceMethods.getString(label);
          String[] methods = meth.split(",");
          for (String a : methods) {
            try {
              Method m = cell.getClass().getDeclaredMethod(a);
              m.setAccessible(true);
              m.invoke(cell);
            }
            catch (Exception e) {
              return;
            }
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

  public LineChart<Number, Number> makeHistogram(String label, NumberAxis xAxis, NumberAxis yAxis) {
    LineChart<Number, Number> histogram = new LineChart<>(xAxis, yAxis);
    histogram.setId(label);
    histogram.setTitle(myResources.getString(label));
    return histogram;
  }

  public NumberAxis makeAxis(String label, double lowerBound, double upperBound, double tickSteps) {
    NumberAxis axis = new NumberAxis(lowerBound, upperBound, tickSteps);
    axis.setLabel(myResources.getString(label));
    axis.setId(label);
    return axis;
  }

  public Alert createErrorMessage(String id, String content) {
    Alert error = new Alert(Alert.AlertType.ERROR);
    error.setHeaderText(myResources.getString(id));
    error.setContentText(myResources.getString(content));
    error.show();
    return error;
  }

  private Node setId(String id, Node node) {
    node.setId(id);
    return node;
  }


}
