package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.CellSocietyModel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

/**
 * @author Evelyn Cupil-Garcia
 * <p>
 * Class that sets up the display for all Cell Society Game types.
 */
public class CellSocietyView {

  private final FactoryComponents myFactoryComponents;
  private final Stage myStage;
  private CellSocietyController myController;
  private CellSocietyModel myModel;


  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  private static final String DEFAULT_STYLESHEET =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/") + "Default.css";

  /**
   * Constructor for the CellSocietyView class that initializes FactoryComponents and retrieves
   * Stage.
   *
   * @param language What language property will be used (English or Spanish).
   * @param stage    Stage from Main class to call upon files.
   */
  public CellSocietyView(CellSocietyController controller, CellSocietyModel model, String language, Stage stage) {
    this.myController = controller;
    this.myModel = model;
    myFactoryComponents = new FactoryComponents(language);
    myStage = stage;
  }

  /**
   * Method that sets up the Display.
   *
   * @return scene that contains the file buttons for the user to choose a game.
   */
  public Scene setupDisplay() {
    VBox root = new VBox();
    Node displayLabel = myFactoryComponents.makeLabel("DisplayLabel");
    root.getChildren().addAll(displayLabel, setupGameModePanel());
    Scene scene = new Scene(root);
    scene.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    return scene;
  }

  private Node setupGameModePanel() {
    HBox panel = new HBox();
    Node simulationType = myFactoryComponents.makeButton("SimulationType",
        e -> chooseFile(myStage));
    Node initialGrid = myFactoryComponents.makeButton("InitialGrid", e -> chooseFile(myStage));
    panel.getChildren().addAll(simulationType, initialGrid);
    return panel;
  }

  private void chooseFile(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/game_of_life/")); //just adding for test purposes
    File selectedFile = fileChooser.showOpenDialog(stage);
    myController.createGridFromFile(selectedFile.toString());
    // would need to send selectedFile to Controller.
  }

  public void setMyController(CellSocietyController controller){
    myController = controller;
  }


}
