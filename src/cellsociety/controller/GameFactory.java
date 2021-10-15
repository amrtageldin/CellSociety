package cellsociety.controller;

import cellsociety.model.*;

import java.io.BufferedReader;
import java.io.FileReader;

public class GameFactory {
    private String myType;
    private CellSocietyModel myModel;

    public CellSocietyModel setUpModel(String file){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String[] gameType = line.split("=");
            myType = gameType[1] + "Model";
            myModel = (CellSocietyModel) Class.forName(String.format("cellsociety.model.%s", myType)).getConstructor().newInstance();
            System.out.println(myModel);
        }
        catch (Exception e){
            e.printStackTrace(); //CHANGE
        }

        return myModel;

    }
}
