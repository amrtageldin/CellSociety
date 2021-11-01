package cellsociety.controller;


import cellsociety.Errors.ErrorFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class GameFactory {
    public static final String TYPE = "Type";
    private static final String SIM_ERROR = "SimError";
    private Map<String, String> parametersMap = new HashMap<>();
    private ErrorFactory myErrorFactory = new ErrorFactory();

    /**
     * This method takes in the loaded .sim file, and parses it to determine which game type
     * has been loaded up to play. This game type is sent back into the controller, which uses
     * reflection to call the correct Model class for the chosen game type
     * @param file: .sim file with game information
     * @return myType: a string with the current game's title
     */

    public String setUpModel(String file){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null ) {
                String[] parameterInfo = line.split("=");
                parametersMap.put(parameterInfo[0], parameterInfo[1]);
                line = reader.readLine();
            }
        }
        catch (Exception e){
            myErrorFactory.updateError(SIM_ERROR);
        }
        return parametersMap.get(TYPE);
    }

    public Map<String, String> getParametersMap(){
        return parametersMap;
    }

    public ErrorFactory getMyErrorFactory(){
        return myErrorFactory;
    }

}
