package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.awt.Dimension;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

  /**
   * The default size of the window.
   **/
  public static final int DEFAULT_X = 800;
  public static final int DEFAULT_Y = 600;

  private static final int MAXVALUE = 5000;
  private static final double secondDelay = 2;


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
    root.setBottom(setupAboutSection());
    Scene scene = new Scene(root, DEFAULT_X, DEFAULT_Y);
    scene.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    return scene;
  }

  private Node setupGameModePanel() {
    HBox panel = new HBox();
    panel.setId("ButtonPanel");
    Node simulationType = myFactoryComponents.makeButton("SimulationType",
        e -> chooseFile(myStage));
    Node initialGrid = myFactoryComponents.makeButton("InitialGrid", e -> chooseFile(myStage));
    Node playButton = myFactoryComponents.makeButton("Play", e -> startGame());
    Node animationButton = myFactoryComponents.makeButton("Start/Pause", e -> togglePlay());
    Node stepButton = myFactoryComponents.makeButton("Step", e -> pauseAndStep());
    panel.getChildren().addAll(simulationType, initialGrid, playButton, animationButton, stepButton);
    return panel;
  }

  private void chooseFile(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/game_of_life/")); //just adding for test purposes
    selectedFile = fileChooser.showOpenDialog(stage);
    myController.loadFileType(selectedFile.toString());
  }

  private void startGame() {
    myGridView = new GridView(myController);
    root.setCenter(myGridView.setupGrid());
    startSimulation();
  }

  private void startSimulation() {
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
    root.setCenter(myGridView.setupGrid());
  }

  private void pauseAndStep() {
    step();
    myAnimation.stop();
  }

  private void togglePlay() {
    if (isPlaying) {
      myAnimation.stop();
    } else {
      myAnimation.play();
    }
    isPlaying = !isPlaying;
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

//  public Dimension getGridPaneDimensions() {
//    int dim = 0;
//    return dim;
//  }

  private Node setupTopText() {
    VBox vbox = new VBox();
    vbox.setId("MainPane");
    Node displayLabel = myFactoryComponents.makeTitle("DisplayLabel");
    vbox.getChildren().addAll(displayLabel, setupGameModePanel());
    vbox.setMaxHeight(myStage.getHeight() / 4);
    return vbox;
  }

  private Node setupAboutSection() {
    Label bottomText = new Label("Ex: This is Game of Life! Watch the simulation work!");
    bottomText.setId("aboutPane");
    bottomText.setMaxSize(MAXVALUE, MAXVALUE);
    return bottomText;
  }
}
