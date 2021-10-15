package cellsociety.controller;

import cellsociety.model.CellSocietyModel;
import cellsociety.view.CellSocietyView;

public class CellSocietyController {
    private CellSocietyView myView;
    private CellSocietyModel myModel;

    public CellSocietyController(CellSocietyModel model){
        myModel = model;
    }

    public void createGridFromFile(String file){
        System.out.println(file);
    }
}
