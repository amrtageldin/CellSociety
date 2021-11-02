package cellsociety.view;

import cellsociety.controller.CellSocietyController;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
  private GridView mySecondGridView;
  private Timeline myAnimation;
  private boolean isPlaying;
  private boolean gridLoaded;
  private HBox gridPanel;
  private HBox multiGridPanel;
  private boolean multiGrid;
  private boolean histogramAdded;
  private boolean barChartAdded;
  private final XYChart.Series<Number, Number> series0 = new XYChart.Series<>();
  private final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
  private final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
  private final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();

  public final String defaultX = "defaultX";
  public final String defaultY = "defaultY";
  public final String gap = "gap";
  public final String secondDelay = "secondDelay";
  public final String speedUpRate = "speedUpRate";
  public final String slowDownRate = "slowDownRate";
  public final String axisStart = "axisStart";
  public final String axisStep = "axisStep";
  public final String state_0 = "state_0";
  public final String state_1 = "state_1";
  public final String state_2 = "state_2";
  public final String state_3 = "state_3";

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
      if (gridLoaded) {
        addGrid();
        multiGrid = true;
      }
      else {
        setupGridPanel();
        startSimulation();
        gridLoaded = true;
      }
    } catch (Exception e) {
      Alert error = myFactoryComponents.createErrorMessage("InvalidGame", "InvalidGameMessage");
      error.show();
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
    try {
      if (myController != null) {
        myController.step();
        errorCheck();
        myAnimation.stop();
      }
      myAnimation.play();
      updateStateSeries();
      if (histogramAdded) { addHistogram();}
      if (barChartAdded) { addBarChart(); }
      setupGridPanel();
    } catch (Exception e) {
      System.out.println(e);
      Alert error = myFactoryComponents.createErrorMessage("InvalidGame", "InvalidGameMessage");
      error.show();
    }
  }

  private void pauseAndStep() {
    try {
      step();
      myAnimation.stop();
      isPlaying = false;
    } catch (Exception e) {
      Alert error = myFactoryComponents.createErrorMessage("InvalidGame", "InvalidGameMessage");
      error.show();
    }
  }

  private void togglePlay() {
    try {
      if (isPlaying) {
        myAnimation.stop();
      } else {
        myAnimation.play();
      }
      isPlaying = !isPlaying;
    } catch (Exception e) {
      Alert error = myFactoryComponents.createErrorMessage("InvalidGame", "InvalidGameMessage");
      error.show();
    }
  }

  private void speedUp() {
    try {
      myAnimation.setRate(
          myAnimation.getRate() * Double.parseDouble(myMagicValues.getString(speedUpRate)));
    } catch (Exception e) {
      Alert error = myFactoryComponents.createErrorMessage("InvalidGame", "InvalidGameMessage");
      error.show();
    }
  }

  private void slowDown() {
    try {
      myAnimation.setRate(
          myAnimation.getRate() - Double.parseDouble(myMagicValues.getString(slowDownRate)));
    } catch (Exception e) {
      Alert error = myFactoryComponents.createErrorMessage("InvalidGame", "InvalidGameMessage");
      error.show();
    }
  }

  private VBox setupBarChart() {
    VBox vbox = new VBox();
    vbox.setId("BarChartPane");
    BarChart<Category, Number> barchart = myFactoryComponents.makeBarChart("CellStateCount", myFactoryComponents.makeCategoryAxis("CellState"), myFactoryComponents.makeBarChartAxis("NumberOfCells"));
    XYChart.Series state0series = new XYChart.Series();
    XYChart.Series state1series = new XYChart.Series();
    XYChart.Series state2series = new XYChart.Series();
    XYChart.Series state3series = new XYChart.Series();
    state0series.getData().add(new XYChart.Data<>(myMagicValues.getString(state_0), myController.getCellStateCounts()[0]));
    state1series.getData().add(new XYChart.Data<>(myMagicValues.getString(state_1), myController.getCellStateCounts()[1]));
    state2series.getData().add(new XYChart.Data<>(myMagicValues.getString(state_2), myController.getCellStateCounts()[2]));
    state3series.getData().add(new XYChart.Data<>(myMagicValues.getString(state_3), myController.getCellStateCounts()[3]));
    barchart.getData().addAll(state0series, state1series, state2series, state3series);
    barchart.setLegendSide(Side.LEFT);
    vbox.getChildren().add(barchart);
    return vbox;
  }

  private void addBarChart() {
    try {
      root.setLeft(setupBarChart());
      barChartAdded = true;
      histogramAdded = false;
    } catch (Exception e) {
      Alert error = myFactoryComponents.createErrorMessage("InvalidGame", "InvalidGameMessage");
      error.show();
    }
  }

  private VBox setupHistogram() {
    VBox vbox = new VBox();
    if (root.getTop().getId()=="MainPane") {
      vbox.setId("HistogramPane");
    }
    LineChart<Number, Number> histogram = myFactoryComponents.makeHistogram("CellStatesOverTime", setupHistogramXAxis(), setupHistogramYAxis());
    histogram.getData().add(series0);
    histogram.getData().add(series1);
    histogram.getData().add(series2);
    histogram.getData().add(series3);
    histogram.setLegendSide(Side.LEFT);
    vbox.getChildren().add(histogram);
    return vbox;
  }

  private NumberAxis setupHistogramXAxis() {
    double axisLowerBound = Double.parseDouble(myMagicValues.getString(axisStart));
    double axisTickMarks = Double.parseDouble(myMagicValues.getString(axisStep));
    int axisGap = Integer.parseInt(myMagicValues.getString(gap));
    return myFactoryComponents.makeAxis("StepCount", axisLowerBound, myController.getStepCount()+axisGap, axisTickMarks);
  }

  private NumberAxis setupHistogramYAxis() {
    double axisLowerBound = Double.parseDouble(myMagicValues.getString(axisStart));
    double axisTickMarks = (double) myGridView.getTotalCells() / myGridView.getColLength();
    return myFactoryComponents.makeAxis("CellStateCount", axisLowerBound, myGridView.getTotalCells(), axisTickMarks);
  }

  private void addHistogram() {
    try {
      root.setLeft(setupHistogram());
      histogramAdded = true;
      barChartAdded = false;
    } catch (Exception e) {
      Alert error = myFactoryComponents.createErrorMessage("InvalidGame", "InvalidGameMessage");
      error.show();
    }
  }

  private void updateStateSeries() {
    double stepCount = myController.getStepCount();
    series0.getData().add(new XYChart.Data<>(stepCount, myController.getCellStateCounts()[0]));
    myFactoryComponents.nameSeries("Series0", series0);
    series1.getData().add(new XYChart.Data<>(stepCount, myController.getCellStateCounts()[1]));
    myFactoryComponents.nameSeries("Series1", series1);
    series2.getData().add(new XYChart.Data<>(stepCount, myController.getCellStateCounts()[2]));
    myFactoryComponents.nameSeries("Series2", series2);
    series3.getData().add(new XYChart.Data<>(stepCount, myController.getCellStateCounts()[3]));
    myFactoryComponents.nameSeries("Series3", series3);
  }

  /**
   * Getter method that returns the GridView.
   *
   * @return GridView.
   */
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
