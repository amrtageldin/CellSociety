package cellsociety.model;

import cellsociety.controller.Grid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class WaTorModel extends CellSocietyModel{
    private static final int INITIAL_REPRODUCTION = 0;
    private static final int REPRODUCTION_INCREASE = 1;
    private static final String FISH = "FISH";
    private static final String SHARK = "SHARK";
    public static final int INITIAL_ENERGY = 5;
    public static final int ENERGY_DECREASE = 1;
    private Map<Cells, Integer> reproductionMap = new HashMap<>();
    private Map<Cells, Integer> energyMap = new HashMap<>();

    public WaTorModel(String type) {
        super(type);
    }
    //TODO: Still working on this, just mapping out right now

    @Override
    public void setNextState(Cells myCell, int row, int col, Grid myGrid){
        List<Cells> myNeighbors = generateNeighbors(row, col, myGrid);
        checkAnimals(myCell);
        animalConditions(myCell, myNeighbors);
        myCell.setMyNextState(2); //TODO: just adding this rn so no stack traces are printed
    }

    private void checkAnimals(Cells myCell){
        Map<Integer, Consumer<Integer>> animalCheck =
                Map.of(Integer.parseInt(statesBundle.getString(SHARK)), integer -> setUpAnimalEnergy(myCell),
                        Integer.parseInt(statesBundle.getString(FISH)), integer -> setUpAnimalReproduction(myCell)
        ,0, integer -> {});
        consumerGenerateNextState(myCell.getCurrentState(), animalCheck.get(myCell.getCurrentState()));
    }

    private void setUpAnimalReproduction(Cells myCell){
        if(!reproductionMap.containsKey(myCell)){
            reproductionMap.put(myCell, INITIAL_REPRODUCTION);
        }
        else{
            reproductionMap.put(myCell, reproductionMap.get(myCell) + REPRODUCTION_INCREASE);
        }
    }

    private void setUpAnimalEnergy(Cells myCell){
        if (!energyMap.containsKey(myCell)) {
            energyMap.put(myCell, INITIAL_ENERGY);
        }
        else{
            energyMap.put(myCell, energyMap.get(myCell) - ENERGY_DECREASE);
        }
        setUpAnimalReproduction(myCell);
    }

    private void animalConditions(Cells myCell, List<Cells> myNeighbors){
        Map<Integer, Consumer<Integer>> animalCheck =
                Map.of(Integer.parseInt(statesBundle.getString(SHARK)), integer -> checkAnimalEnergy(myCell, myNeighbors),
                        Integer.parseInt(statesBundle.getString(FISH)), integer -> checkAnimalReproduction(myCell, myNeighbors),
                        0, integer -> {});
        consumerGenerateNextState(myCell.getCurrentState(), animalCheck.get(myCell.getCurrentState()));
    }

    private void checkAnimalEnergy(Cells myCell, List<Cells> myNeighbors){
        if(energyMap.get(myCell) == 0){
            energyMap.remove(myCell);
            myCell.setMyNextState(0);
            myCell.updateMyCurrentState();
        }
        checkAnimalReproduction(myCell, myNeighbors);
    }

    private void checkAnimalReproduction(Cells myCell, List<Cells> myNeighbors){
        if(reproductionMap.get(myCell) == 5){
            for(Cells c: myNeighbors){
                if(c.getCurrentState() == 0){
                    c.setMyNextState(myCell.getCurrentState());
                    c.updateMyCurrentState();
                    reproductionMap.put(c, INITIAL_REPRODUCTION);
                    myCell.setMyNextState(myCell.getCurrentState());
                    reproductionMap.remove(myCell);
                    myCell.updateMyCurrentState();
                }
            }
        }
    }

}
