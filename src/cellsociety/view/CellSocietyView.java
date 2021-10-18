package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import cellsociety.model.CellSocietyModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
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
  private BorderPane root;
  private CellSocietyController myController;
  private CellSocietyModel myModel;
  private File selectedFile;
  private GridView myGridView;

  /**The default size of the window.**/
  public static final int DEFAULT_X = 800;
  public static final int DEFAULT_Y = 600;

  private static final int MAXVALUE = 5000;
  private static final int topButtonPadding = 30;
  private static final int buttonSpacing = 10;


  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  private static final String DEFAULT_STYLESHEET =
      "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/") + "Default.css";

  /**
   * Constructor for the CellSocietyView class that initializes FactoryComponents and retrieves
   * Stage.
   *
   * @param controller CellSocietyController
   * @param language What language property will be used (English or Spanish).
   * @param stage    Stage from Main class to call upon files.
   */
  public CellSocietyView(CellSocietyController controller, CellSocietyModel model, String language,
      Stage stage) {
    myController = controller;
    myModel = model;
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
    int sidePadding = (int) (myStage.getWidth() / 2);
    Node simulationType = myFactoryComponents.makeButton("SimulationType",
        e -> chooseFile(myStage));
    Node initialGrid = myFactoryComponents.makeButton("InitialGrid", e -> chooseFile(myStage));
    Node playButton = myFactoryComponents.makeButton("Play", e -> startGame());
    panel.getChildren().addAll(simulationType, initialGrid, playButton);
    panel.setAlignment(Pos.CENTER);
    panel.setSpacing(buttonSpacing);
    panel.setPadding(new Insets(topButtonPadding, sidePadding, topButtonPadding, sidePadding));
    return panel;
  }

  private void chooseFile(Stage stage) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("data/game_of_life/")); //just adding for test purposes
    selectedFile = fileChooser.showOpenDialog(stage);
    myController.loadFileType(selectedFile.toString());
  }

  private void startGame(){
    myGridView = new GridView(myController);
    root.setCenter(myGridView.setupGrid());
    myController.step();
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

  private Node setupTopText() {
    VBox vbox = new VBox();
    vbox.setId("MainPane");
    vbox.setAlignment(Pos.CENTER);
    Node displayLabel = myFactoryComponents.makeTitle("DisplayLabel");
    displayLabel.getStyleClass().add("textProps");
    vbox.getChildren().addAll(displayLabel, setupGameModePanel());
    vbox.setMaxHeight(myStage.getHeight()/4);
    vbox.getStyleClass().add("topPane");
    return vbox;
  }

  private Node setupGridArea() {
    Label gridArea = new Label("THIS IS WHERE THE GRID WILL BE");
    gridArea.getStyleClass().add("gridPane");
    gridArea.setMinSize(3*myStage.getWidth()/4, 3*myStage.getHeight()/4);
    gridArea.setAlignment(Pos.CENTER);
    gridArea.setMaxSize(MAXVALUE,MAXVALUE);
    return gridArea;
  }

  private Node setupAboutSection() {
    Label bottomText = new Label("Ex: This is Game of Life! Watch the simulation work!");
    bottomText.getStyleClass().add("aboutPane");
    bottomText.setAlignment(Pos.CENTER);
    bottomText.setMaxSize(MAXVALUE,MAXVALUE);
    return bottomText;
  }

}
