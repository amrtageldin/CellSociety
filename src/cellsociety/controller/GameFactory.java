package cellsociety.controller;


import cellsociety.Errors.ErrorFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class reads the loaded .sim file and parses it to create a Map of all the relevant
 * parameters for a game. It is called by the controller to initialize the game type so that the
 * correct Model can be loaded to run the intended game. It assumes that the user inserts a valid
 * file, but if an invalid file is loaded it returns an error to the user.
 */
public class GameFactory {

  private static final String TYPE = "Type";
  private static final String SIM_ERROR = "SimError";
  private final Map<String, String> parametersMap = new HashMap<>();
  private final ErrorFactory myErrorFactory = new ErrorFactory();

  /**
   * This method takes in the loaded .sim file, and parses it to split every parameter into a
   * HashMap with its key being the String before the "=" in the .sim file. It then looks at the
   * first entry to see which game has been loaded up to play. This game type is sent back into the
   * controller, which uses reflection to call the correct Model class for the chosen game type.
   *
   * @param file: .sim file with game information
   * @return parametersMap.get(TYPE): a string with the current game's title
   */

  public String setUpModel(String file) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine();
      while (line != null) {
        String[] parameterInfo = line.split("=");
        parametersMap.put(parameterInfo[0], parameterInfo[1]);
        line = reader.readLine();
      }
    } catch (Exception e) {
      myErrorFactory.updateError(SIM_ERROR);
    }
    return parametersMap.get(TYPE);
  }

  /**
   * This method returns all of the parameters that were specified in the .sim file that was loaded
   * by the user. This is sent to the controller and used by the view as well as the model to keep
   * track or necessary game parameters.
   *
   * @return parametersMap: a map of all of the relevant parameters in a game.
   */
  public Map<String, String> getParametersMap() {
    return parametersMap;
  }

  /**
   * This method returns the GameFactory class's instance of ErrorFactory, which holds information
   * as to whether or not an error exists, and if it does exist - what the error is. It is used by
   * the controller to keep track of any errors that show up while loading the .sim file.
   *
   * @return myErrorFactory: the error information for the GameFactory class
   */
  public ErrorFactory getMyErrorFactory() {
    return myErrorFactory;
  }

}
