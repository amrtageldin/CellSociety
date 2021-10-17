package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.CellSocietyModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

/**
 * @author Evelyn Cupil-Garcia
 * @author Luke Josephy
 * <p>
 * Class that sets up the display for all Cell Society Game types.
 */
public class CellSocietyView {

  private final FactoryComponents myFactoryComponents;
  private final Stage myStage;
  private CellSocietyController myController;
  private CellSocietyModel myModel;

  private static final int topButtonPadding = 30;
  private static final int buttonSpacing = 10;


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
  // Take out the background color, that needs to be done in CSS
  public Scene setupDisplay(Paint backgroundColor) {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    Node displayLabel = myFactoryComponents.makeTitle("DisplayLabel");
    root.getChildren().addAll(displayLabel, setupGameModePanel());
    Scene scene = new Scene(root, backgroundColor);
    scene.getStylesheets()
        .add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    return scene;
  }

  private Node setupGameModePanel() {
    HBox panel = new HBox();
    int sidePadding = (int) (myStage.getWidth() / 2);
    Node simulationType = myFactoryComponents.makeButton("SimulationType",
        e -> chooseFile(myStage));
    Node initialGrid = myFactoryComponents.makeButton("InitialGrid", e -> chooseFile(myStage));
    panel.getChildren().addAll(simulationType, initialGrid);
    panel.setAlignment(Pos.CENTER);
    panel.setSpacing(buttonSpacing);
    panel.setPadding(new Insets(topButtonPadding, sidePadding, topButtonPadding, sidePadding));
    return panel;
  }

  private void chooseFile(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/game_of_life/")); //just adding for test purposes
    File selectedFile = fileChooser.showOpenDialog(stage);
    myController.loadFileType(selectedFile.toString());
  }


}
