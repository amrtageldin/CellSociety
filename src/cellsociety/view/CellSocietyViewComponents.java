package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.util.HashMap;
import java.util.Map;
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

/**
 * @author Evelyn Cupil-Garcia
 * <p>
 * Class that uses FactoryComponents class to create the panels and UI components that are displayed
 * in the game.
 */
public class CellSocietyViewComponents {

  private final FactoryComponents myFactoryComponents;
  private final ResourceBundle myMagicValues;
  private final CellSocietyView myCellSocietyView;
  private VBox myAboutPanel;

  public final String maxValue = "maxValue";

  public final Map<String, String> colorMap = Map.of("Modooscuro", "DarkMode", "Modobajo",
      "LightMode",
      "MododelDiabloAzul", "BlueDevilMode");

  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";

  /**
   * Constructor method that initializes the class.
   *
   * @param language        that the game is in.
   * @param cellSocietyView takes in CellSocietyView for methods related to UI components.
   */
  public CellSocietyViewComponents(String language, CellSocietyView cellSocietyView) {
    myFactoryComponents = new FactoryComponents(language);
    myMagicValues = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "MagicValues");
    myCellSocietyView = cellSocietyView;
  }

  /**
   * Creates the Game Mode Panel to initialize the game that contains the UI buttons.
   *
   * @param root so we can add to the correct area.
   * @return panel with all components in it.
   */
  public Node setupGameModePanel(BorderPane root) {
    VBox panel = new VBox();
    panel.getChildren().addAll(setupTopButtonPanel(root), setupBottomButtonPanel());
    return panel;
  }

  /**
   * Creates the Main Pane with the GameMode Panel and Display text.
   *
   * @param stage for height of the pane.
   * @param root  to have access to panes.
   * @return Main pane.
   */
  public Node setupTopText(Stage stage, BorderPane root) {
    VBox vbox = new VBox();
    Node displayLabel = myFactoryComponents.makeLabel("DisplayLabel");
    vbox.setId("MainPane");
    vbox.getChildren().addAll(displayLabel, setupGameModePanel(root));
    vbox.setMaxHeight(stage.getHeight());
    return vbox;
  }

  /**
   * Creates the general about section before a game is loaded.
   *
   * @return general label for about section.
   */
  public Label setupAboutSection() {
    Label bottomText = myFactoryComponents.makeLabel("StartingAbout");
    bottomText.setId("AboutPane");
    bottomText.setMaxSize(Integer.parseInt(myMagicValues.getString(maxValue)),
        Integer.parseInt(myMagicValues.getString(maxValue)));
    return bottomText;
  }

  /**
   * Creates the about section after a game is loaded.
   *
   * @param controller to check if certain parameters exist for the game to display.
   * @return updated about pane.
   */
  public VBox populateAboutSection(CellSocietyController controller) {
    myAboutPanel = new VBox();
    myAboutPanel.setId("AboutPane");
    Label gameType = myFactoryComponents.makeLabel("gameType");
    gameType.setText(controller.getMyGameType());
    myAboutPanel.getChildren().add(gameType);
    checkParameter("Author", controller);
    checkParameter("Title", controller);
    checkParameter("Description", controller);
    checkParameter("StateColors", controller);
    return myAboutPanel;
  }

  private void checkParameter(String param, CellSocietyController controller) {
    if (controller.getMyParametersMap().containsKey(param)) {
      Label myParam = myFactoryComponents.makeLabel(param);
      myParam.setText(myParam.getText() + controller.getMyParametersMap().get(param));
      myAboutPanel.getChildren().add(myParam);
    }
  }

  private HBox setupTopButtonPanel(BorderPane root) {
    HBox setupPanel = new HBox();
    setupPanel.setId("TopButtonPanel");
    Node simulationType = myFactoryComponents.makeButton("SimulationType", myCellSocietyView);
    Node initialGrid = myFactoryComponents.makeButton("InitialGrid", myCellSocietyView);
    Node playButton = myFactoryComponents.makeButton("Play", myCellSocietyView);
    setupPanel.getChildren()
        .addAll(simulationType, initialGrid, playButton, setupColorOptions(root));
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
      if (colorMap.containsKey(colorMode)) {
        colorMode = colorMap.get(colorMode);
      }
      root.getTop().setId(colorMode + "MainPane");
      System.out.println(root.getTop().getId());
      root.getRight().setId(colorMode + "AboutPane");
      root.setId(colorMode);
    };
    dropdown.setOnAction(event);
  }
}
