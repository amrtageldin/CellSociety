package cellsociety.controller;


import java.io.BufferedReader;
import java.io.FileReader;

public class GameFactory {
    private String myType;

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
            String[] gameType = line.split("=");
            myType = String.format("%s",gameType[1]); //TODO: need to fix!
        }
        catch (Exception e){
            e.printStackTrace(); //CHANGE
        }

        return myType;

    }
}
