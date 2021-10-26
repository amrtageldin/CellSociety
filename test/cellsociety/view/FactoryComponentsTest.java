package cellsociety.view;

import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import util.DukeApplicationTest;


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
    Label label = myFactoryComponents.makeLabel(id);
    assertEquals(id, label.getId());
  }
}

