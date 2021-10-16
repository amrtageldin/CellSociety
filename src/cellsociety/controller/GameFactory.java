package cellsociety.controller;


import java.io.BufferedReader;
import java.io.FileReader;

public class GameFactory {
    private String myType;

    public String setUpModel(String file){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String[] gameType = line.split("=");
            myType = gameType[1] + "Model"; //TODO: need to fix!
        }
        catch (Exception e){
            e.printStackTrace(); //CHANGE
        }

        return myType;

    }
}
