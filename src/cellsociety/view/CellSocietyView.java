package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
 * Class that displays the UI components for all Cell Society Game types.
 * TODO: Missing double screen functionality, missing different grid types
 *  
 */
public class CellSocietyView {

  private final FactoryComponents myFactoryComponents;
  private final Stage myStage;
  private BorderPane root;
  private final CellSocietyController myController;
  CellSocietyViewComponents myViewComponents;
  private GridView myGridView;
  private Timeline myAnimation;
  private boolean isPlaying;
  private boolean gridLoaded;
  private HBox gridPanel;

  public final String defaultX = "defaultX";
  public final String defaultY = "defaultY";
  public final String secondDelay = "secondDelay";
  public final String speedUpRate = "speedUpRate";
  public final String slowDownRate = "slowDownRate";

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
    myViewComponents = new CellSocietyViewComponents(language, this);
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
    root.setTop(myViewComponents.setupTopText(myStage, root));
    root.setRight(myViewComponents.setupAboutSection());
    root.setId("Main");
    Scene scene = new Scene(root, Integer.parseInt(myMagicValues.getString(defaultX)),
        Integer.parseInt(myMagicValues.getString(defaultY)));
    scene.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    return scene;
  }

  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/")); //just adding for test purposes
    File selectedFile = fileChooser.showOpenDialog(myStage);
    try {
      myController.loadFileType(selectedFile.toString());
    } catch (Exception e) {
      Alert error = myFactoryComponents.createErrorMessage("InvalidFile", "InvalidFileMessage");
      error.show();
    }
    if(myAnimation != null){
      togglePlay();
    }
    errorCheck();
  }

  private void startGame() {
    try {
      if (!gridLoaded) {
        gridPanel = new HBox();
        gridPanel.setId("GridPanel");
        gridPanel.getChildren().add(setupGridSection());
        root.setCenter(gridPanel);
        startSimulation();
        gridLoaded = true;
      }
      else {
        togglePlay();
        gridPanel.getChildren().add(setupGridSection());
        togglePlay();
      }
    } catch (Exception e) {
      Alert error = myFactoryComponents.createErrorMessage("InvalidGame", "InvalidGameMessage");
      error.show();
    }
  }

  private void startSimulation() {
    root.setRight(myViewComponents.populateAboutSection(myController));
    if (myAnimation != null) {
      myAnimation.stop();
    }
    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    myAnimation.getKeyFrames().add(
        new KeyFrame(Duration.seconds(Double.parseDouble(myMagicValues.getString(secondDelay))),
            e -> step()));
    myAnimation.play();
    errorCheck();
    isPlaying = true;
  }

  private void step() {
    if (myController != null) {
      myController.step();
      errorCheck();
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
    myAnimation.setRate(
        myAnimation.getRate() * Double.parseDouble(myMagicValues.getString(speedUpRate)));
    System.out.println("Sped up!");
  }

  private void slowDown() {
    myAnimation.setRate(
        myAnimation.getRate() - Double.parseDouble(myMagicValues.getString(slowDownRate)));
  }

  public GridView getMyGridView() {
    return myGridView;
  }

  private VBox setupGridSection() {
    VBox vbox = new VBox();
    vbox.setId("Grid");
    myGridView = new GridView(myController);
    vbox.getChildren().add(myGridView.setupGrid());
    return vbox;
  }

  private void errorCheck(){
    if(myController.getErrorExists()){
      myFactoryComponents.createErrorMessage("InvalidFile", myController.getMyError());
    }
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
