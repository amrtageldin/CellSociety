package cellsociety.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CellSocietyViewComponents {
  private final FactoryComponents myFactoryComponents;
  private final ResourceBundle myMagicValues;
  private final CellSocietyView myCellSocietyView;

  public final String maxValue = "maxValue";

  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";

  public CellSocietyViewComponents(String language, CellSocietyView cellSocietyView) {
    myFactoryComponents = new FactoryComponents(language);
    myMagicValues = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "MagicValues");
    myCellSocietyView = cellSocietyView;
  }

  public Node setupGameModePanel(BorderPane root) {
    VBox panel = new VBox();
    panel.getChildren().addAll(setupTopButtonPanel(root), setupBottomButtonPanel());
    return panel;
  }

  public Node setupTopText(Stage stage, BorderPane root) {
    VBox vbox = new VBox();
    Node displayLabel = myFactoryComponents.makeLabel("DisplayLabel");
    vbox.setId("MainPane");
    vbox.getChildren().addAll(displayLabel, setupGameModePanel(root));
    vbox.setMaxHeight(stage.getHeight());
    return vbox;
  }

  public Label setupAboutSection() {
    Label bottomText = myFactoryComponents.makeLabel("StartingAbout");
    bottomText.setId("AboutPane");
    bottomText.setMaxSize(Integer.parseInt(myMagicValues.getString(maxValue)),
        Integer.parseInt(myMagicValues.getString(maxValue)));
    return bottomText;
  }

  private HBox setupTopButtonPanel(BorderPane root) {
    HBox setupPanel = new HBox();
    setupPanel.setId("TopButtonPanel");
    Node simulationType = myFactoryComponents.makeButton("SimulationType", myCellSocietyView);
    Node initialGrid = myFactoryComponents.makeButton("InitialGrid", myCellSocietyView);
    Node playButton = myFactoryComponents.makeButton("Play", myCellSocietyView);
    setupPanel.getChildren().addAll(simulationType, initialGrid, playButton, setupColorOptions(root));
    return setupPanel;
  }

  private HBox setupBottomButtonPanel() {
    HBox livePanel = new HBox();
    livePanel.setId("BottomButtonPanel");
    Node animationButton = myFactoryComponents.makeButton("Start/Pause", myCellSocietyView);
    Node stepButton = myFactoryComponents.makeButton("Step", myCellSocietyView);
    Node speedUpButton = myFactoryComponents.makeButton("SpeedUp", myCellSocietyView);
    Node slowDownButton = myFactoryComponents.makeButton("SlowDown", myCellSocietyView);
    livePanel.getChildren()
        .addAll(animationButton, stepButton, speedUpButton, slowDownButton);
    return livePanel;
  }

  private ComboBox<String> setupColorOptions(BorderPane root) {
    String[] options = {"LightMode", "DarkMode", "BDMode"};
    ComboBox<String> colorOptions = myFactoryComponents.makeDropDownMenu("DropDownDefault",
        options);
    setupDropDownCommands(colorOptions, root);
    return colorOptions;
  }

  private void setupDropDownCommands(ComboBox<String> dropdown, BorderPane root) {
    EventHandler<ActionEvent> event = event1 -> {
      String colorMode = dropdown.getValue();
      colorMode = colorMode.replace(" ", "");
      root.getTop().setId(colorMode + "MainPane");
      root.getRight().setId(colorMode + "AboutPane");
      root.setId(colorMode);
    };
    dropdown.setOnAction(event);
  }
}
