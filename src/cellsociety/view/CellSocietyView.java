package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;
import javafx.util.Duration;

/**
 * @author Evelyn Cupil-Garcia
 * @author Luke Josephy
 * <p>
 * Class that sets up the display for all Cell Society Game types.
 * TODO: Missing double screen functionality, Controller connection for the sliders for specific games,
 *  error message handling, about section parsing to display, missing color choosing for cell state.
 *  Would refactor to remove the setup methods into a different class called CellSocietyViewComponents.
 *  
 */
public class CellSocietyView {

  private final FactoryComponents myFactoryComponents;
  private final Stage myStage;
  private BorderPane root;
  private final CellSocietyController myController;
  private GridView myGridView;
  private GridView mySecondGridView;
  private Timeline myAnimation;
  private boolean isPlaying;
  private boolean gridLoaded;
  private HBox gridPanel;
  private HBox multiGridPanel;
  private boolean multiGrid;

  public final String defaultX = "defaultX";
  public final String defaultY = "defaultY";
  public final String maxValue = "maxValue";
  public final String secondDelay = "secondDelay";
  public final String speedUpRate = "speedUpRate";
  public final String slowDownRate = "slowDownRate";
  public final String minPercent = "minPercent";
  public final String maxPercent = "maxPercent";
  public final String incrementValue = "incrementValue";

  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  private static final String DEFAULT_STYLESHEET =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/") + "Default.css";
  private final ResourceBundle myMagicValues;

  /**
   * Constructor for the CellSocietyView class that initializes FactoryComponents and retrieves
   * Stage.
   *
   * @param controller CellSocietyController
   * @param language   What language property will be used (English or Spanish).
   * @param stage      Stage from Main class to call upon files.
   */
  public CellSocietyView(CellSocietyController controller, String language,
      Stage stage) {
    myController = controller;
    myFactoryComponents = new FactoryComponents(language);
    myStage = stage;
    myMagicValues = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "MagicValues");
  }

  /**
   * Method that sets up the Display.
   *
   * @return scene that contains the file buttons for the user to choose a game.
   */
  public Scene setupDisplay() {
    root = new BorderPane();
    root.setTop(setupTopText());
    root.setRight(setupAboutSection());
    root.setId("Main");
    Scene scene = new Scene(root, Integer.parseInt(myMagicValues.getString(defaultX)),
        Integer.parseInt(myMagicValues.getString(defaultY)));
    scene.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    return scene;
  }

  private Node setupGameModePanel() {
    VBox panel = new VBox();
    panel.getChildren().addAll(setupTopButtonPanel(), setupBottomButtonPanel());
    return panel;
  }

  private HBox setupTopButtonPanel() {
    HBox setupPanel = new HBox();
    setupPanel.setId("TopButtonPanel");
    Node simulationType = myFactoryComponents.makeButton("SimulationType", this);
    Node initialGrid = myFactoryComponents.makeButton("InitialGrid", this);
    Node playButton = myFactoryComponents.makeButton("Play", this);
    setupPanel.getChildren().addAll(simulationType, initialGrid, playButton, setupColorOptions());
    return setupPanel;
  }

  private HBox setupBottomButtonPanel() {
    HBox livePanel = new HBox();
    livePanel.setId("BottomButtonPanel");
    Node animationButton = myFactoryComponents.makeButton("Start/Pause", this);
    Node stepButton = myFactoryComponents.makeButton("Step", this);
    Node speedUpButton = myFactoryComponents.makeButton("SpeedUp", this);
    Node slowDownButton = myFactoryComponents.makeButton("SlowDown", this);
    livePanel.getChildren()
        .addAll(animationButton, stepButton, speedUpButton, slowDownButton, setupFirePanel(),
            setupCellStatePanel());
    return livePanel;
  }

  private VBox setupFirePanel() {
    VBox panel = new VBox();
    panel.setId("FirePanel");
    Node fireLabel = myFactoryComponents.makeLabel("FireLabel");
    Slider fireSlider = myFactoryComponents.makeSlider("FireSlider",
        Integer.parseInt(myMagicValues.getString(minPercent)),
        Integer.parseInt(myMagicValues.getString(maxPercent)),
        Integer.parseInt(myMagicValues.getString(incrementValue)));
    panel.getChildren().addAll(fireLabel, fireSlider);
    return panel;
  }

  private VBox setupCellStatePanel() {
    VBox panel = new VBox();
    panel.setId("CellStatePanel");
    Node cellStateLabel = myFactoryComponents.makeLabel("CellStateLabel");
    Slider cellStateSlider = myFactoryComponents.makeSlider("CellStateSlider",
        Integer.parseInt(myMagicValues.getString(minPercent)),
        Integer.parseInt(myMagicValues.getString(maxPercent)),
        Integer.parseInt(myMagicValues.getString(incrementValue)));
    panel.getChildren().addAll(cellStateLabel, cellStateSlider);
    return panel;
  }

  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/")); //just adding for test purposes
    File selectedFile = fileChooser.showOpenDialog(myStage);
    myController.loadFileType(selectedFile.toString());
  }

  private void startGame() {
    if (gridLoaded) {
      addGrid();
      multiGrid = true;
    }
    else {
      setupGridPanel();
      startSimulation();
      gridLoaded = true;
    }
  }

  private void setupGridPanel() {
    if (multiGrid) {
      addGrid();
    }
    else {
      gridPanel = new HBox();
      gridPanel.setId("GridPanel");
      root.setCenter(gridPanel);
      gridPanel.getChildren().add(setupGridSection());
    }
  }

  private void addGrid() {
    if (!gridLoaded) {
      multiGridPanel = new HBox();
      multiGridPanel.setId("GridPanel");
      multiGridPanel.getChildren().addAll(setupFirstGridSection(), setupSecondGridSection());
      root.setCenter(multiGridPanel);
    } else {
      multiGridPanel = gridPanel;
      multiGridPanel.setId("GridPanel");
      multiGridPanel.getChildren().add(setupSecondGridSection());
      root.setCenter(multiGridPanel);
      gridLoaded = false;
    }
  }

  private void startSimulation() {
    myFactoryComponents.setLabel((Label) root.getRight(), myController.getMyGameType());
    if (myAnimation != null) {
      myAnimation.stop();
    }
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(
        new KeyFrame(Duration.seconds(Double.parseDouble(myMagicValues.getString(secondDelay))),
            e -> step()));
    myAnimation.play();
    isPlaying = true;
  }

  private void step() {
    if (myController != null) {
      myController.step();
      myAnimation.stop();
    }
    myAnimation.play();
    setupGridPanel();
  }

  private void pauseAndStep() {
    step();
    myAnimation.stop();
    isPlaying = false;
  }

  private void togglePlay() {
    if (isPlaying) {
      myAnimation.stop();
    } else {
      myAnimation.play();
    }
    isPlaying = !isPlaying;
  }

  private void speedUp() {
    myAnimation.setRate(
        myAnimation.getRate() * Double.parseDouble(myMagicValues.getString(speedUpRate)));
  }

  private void slowDown() {
    myAnimation.setRate(
        myAnimation.getRate() - Double.parseDouble(myMagicValues.getString(slowDownRate)));
  }

  public GridView getMyGridView() {
    return myGridView;
  }

  private Node setupTopText() {
    VBox vbox = new VBox();
    Node displayLabel = myFactoryComponents.makeLabel("DisplayLabel");
    vbox.setId("MainPane");
    vbox.getChildren().addAll(displayLabel, setupGameModePanel());
    vbox.setMaxHeight(myStage.getHeight());
    return vbox;
  }

  private Label setupAboutSection() {
    Label bottomText = myFactoryComponents.makeLabel("StartingAbout");
    bottomText.setId("AboutPane");
    bottomText.setMaxSize(Integer.parseInt(myMagicValues.getString(maxValue)),
        Integer.parseInt(myMagicValues.getString(maxValue)));
    return bottomText;
  }

  private VBox setupGridSection() {
    VBox vbox = new VBox();
    vbox.setId("Grid");
    myGridView = new GridView(myController);
    vbox.getChildren().add(myGridView.setupGrid());
    return vbox;
  }

  private VBox setupFirstGridSection() {
    VBox vbox = new VBox();
    vbox.setId("Grid");
    vbox.getChildren().add(myGridView.setupGrid());
    return vbox;
  }

  private VBox setupSecondGridSection() {
    VBox vbox = new VBox();
    vbox.setId("Grid");
    mySecondGridView = new GridView(myController);
    vbox.getChildren().add(mySecondGridView.setupGrid());
    return vbox;
  }

  private ComboBox<String> setupColorOptions() {
    String[] options = {"LightMode", "DarkMode", "BDMode"};
    ComboBox<String> colorOptions = myFactoryComponents.makeDropDownMenu("DropDownDefault",
        options);
    setupDropDownCommands(colorOptions);
    return colorOptions;
  }

  private void setupDropDownCommands(ComboBox<String> dropdown) {
    EventHandler<ActionEvent> event = event1 -> {
      String colorMode = dropdown.getValue();
      colorMode = colorMode.replace(" ", "");
      root.getTop().setId(colorMode + "MainPane");
      root.getRight().setId(colorMode + "AboutPane");
      root.setId(colorMode);
    };
    dropdown.setOnAction(event);
  }

  /**
   * Getter method that returns the animation.
   *
   * @return animation of the game.
   */
  public Animation getAnimation() {
    return myAnimation;
  }
}
