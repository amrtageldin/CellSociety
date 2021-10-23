package cellsociety.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

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
            Method m = cell.getClass().getDeclaredMethod(myResourceMethods.getString(label), null);
            m.setAccessible(true);
            m.invoke(cell, null);
          }
          catch (Exception e) {
            // FIXME: typically make your own custom exception to throw
            throw new RuntimeException("Improper configuration", e);
          }
        }
    );
    //result.setOnAction(handler);
    return setId(label, result);
  }

  private Node setId(String id, Node node) {
    node.setId(id);
    return node;
  }


}
