package cellsociety;


import cellsociety.controller.CellSocietyController;
import cellsociety.model.CellSocietyModel;
import cellsociety.view.CellSocietyView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  public static final String TITLE = "Cell Society";
  public static final String LANGUAGE = "English";

  /**
   * Organize display of game in a scene and start the game.
   */
  @Override
  public void start(Stage stage) {
    CellSocietyController controller = new CellSocietyController();
    CellSocietyView display = new CellSocietyView(controller, LANGUAGE, stage);
    stage.setScene(display.setupDisplay());
    stage.setTitle(TITLE);
    stage.show();
  }
}

