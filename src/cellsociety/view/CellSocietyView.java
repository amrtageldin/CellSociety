package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.awt.Dimension;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
 */
public class CellSocietyView {

  private final FactoryComponents myFactoryComponents;
  private final Stage myStage;
  private BorderPane root;
  private final CellSocietyController myController;
  private File selectedFile;
  private GridView myGridView;
  private Timeline myAnimation;
  private boolean isPlaying;
  private Slider speed;

  /**
   * The default size of the window.
   **/
  public static final int DEFAULT_X = 800;
  public static final int DEFAULT_Y = 600;

  private static final int MAXVALUE = 5000;
  private static final double secondDelay = 2.0;
  private static final double speedUpRate = 1.25;
  private static final double slowDownRate = 0.75;

  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  private static final String DEFAULT_STYLESHEET =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/") + "Default.css";

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
    Scene scene = new Scene(root, DEFAULT_X, DEFAULT_Y);
    scene.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    return scene;
  }

  private Node setupGameModePanel() {
    VBox panel = new VBox();
    panel.getChildren().addAll(setupTopButtonPanel(),setupBottomButtonPanel());
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
    livePanel.getChildren().addAll(animationButton, stepButton, speedUpButton, slowDownButton, setupFirePanel(), setupCellStatePanel());
    return livePanel;
  }

  private VBox setupFirePanel() {
    VBox panel = new VBox();
    panel.setId("FirePanel");
    Node fireLabel = myFactoryComponents.makeLabel("FireLabel");
    Slider fireSlider = myFactoryComponents.makeSlider("FireSlider", 0, 100, 10);
    panel.getChildren().addAll(fireLabel, fireSlider);
    return panel;
  }

  private VBox setupCellStatePanel() {
    VBox panel = new VBox();
    panel.setId("CellStatePanel");
    Node cellStateLabel = myFactoryComponents.makeLabel("CellStateLabel");
    Slider cellStateSlider = myFactoryComponents.makeSlider("CellStateSlider", 0, 100, 10);
    panel.getChildren().addAll(cellStateLabel, cellStateSlider);
    return panel;
  }

  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/game_of_life/")); //just adding for test purposes
    selectedFile = fileChooser.showOpenDialog(myStage);
    myController.loadFileType(selectedFile.toString());
  }

  private void startGame() {
    root.setCenter(setupGridSection());
    startSimulation();
  }

  private void startSimulation() {
    myFactoryComponents.setLabel((Label) root.getRight(), myController.getMyGameType());
    if (myAnimation != null) {
      myAnimation.stop();
    }
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(secondDelay), e -> step()));
    myAnimation.play();
    isPlaying = true;
  }

  private void step() {
    if (myController != null) {
      myController.step();
      myAnimation.stop();
    }
    myAnimation.play();
    root.setCenter(setupGridSection());
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
    myAnimation.setRate(myAnimation.getRate()*speedUpRate);
    System.out.println(myAnimation.getRate());
  }

  private void slowDown() {
    myAnimation.setRate(myAnimation.getRate()-slowDownRate);
    System.out.println(myAnimation.getRate());
  }

  /**
   * Getter that returns file that was chosen from FileChooser. Need to find a better way to test,
   * hunted through the internet but didn't have much luck on something better.
   *
   * @return selectedFile from FileChooser.
   */
  public File getMyFile() {
    return selectedFile;
  }

  public GridView getMyGridView() {
    return myGridView;
  }

  private Node setupTopText() {
    VBox vbox = new VBox();
    Node displayLabel = myFactoryComponents.makeLabel("DisplayLabel");
    vbox.setId("MainPane");
    vbox.getChildren().addAll(displayLabel, setupGameModePanel());
    vbox.setMaxHeight(myStage.getHeight() / 4);
    return vbox;
  }

  private Label setupAboutSection() {
    Label bottomText = myFactoryComponents.makeLabel("StartingAbout");
    bottomText.setId("AboutPane");
    bottomText.setMaxSize(MAXVALUE, MAXVALUE);
    return bottomText;
  }

  private HBox setupGridSection() {
    HBox gridPanel = new HBox();
    VBox vbox = new VBox();
    gridPanel.setId("GridPanel");
    vbox.setId("GridPanel");
    myGridView = new GridView(myController);
    vbox.getChildren().add(myGridView.setupGrid());
    gridPanel.getChildren().add(vbox);
    return gridPanel;
  }

  public Dimension getGridSectionDimensions() {
    int width = (int) setupGridSection().getWidth();
    int height = (int) setupGridSection().getHeight();
    Dimension gridSectionSize = new Dimension(width, height);
    return gridSectionSize;
  }

  private ComboBox setupColorOptions() {
    String[] options = {"LightMode", "DarkMode", "BDMode"};
    ComboBox colorOptions = myFactoryComponents.makeDropDownMenu("DropDownDefault", options);
    setupDropDownCommands(colorOptions);
    return colorOptions;
  }

  private void setupDropDownCommands(ComboBox dropdown) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        String colorMode = (String) dropdown.getValue();
        colorMode = colorMode.replace(" ","");
        root.getTop().setId(colorMode+"MainPane");
        root.getRight().setId(colorMode+"AboutPane");
        root.setId(colorMode);
      }
    };
    dropdown.setOnAction(event);
  }
}
