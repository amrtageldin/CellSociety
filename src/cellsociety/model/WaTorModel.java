package cellsociety.model;

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

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        checkAnimals(myCell);
        animalConditions(myCell);
    }

    private void checkAnimals(Cells myCell){
        Map<Integer, Consumer<Integer>> animalCheck =
                Map.of(Integer.parseInt(statesBundle.getString(SHARK)), integer -> setUpAnimalEnergy(myCell),
                        Integer.parseInt(statesBundle.getString(FISH)), integer -> setUpAnimalReproduction(myCell));
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

    private void animalConditions(Cells myCell){
        Map<Integer, Consumer<Integer>> animalCheck =
                Map.of(Integer.parseInt(statesBundle.getString(SHARK)), integer -> checkAnimalEnergy(myCell),
                        Integer.parseInt(statesBundle.getString(FISH)), integer -> checkAnimalReproduction(myCell));
        consumerGenerateNextState(myCell.getCurrentState(), animalCheck.get(myCell.getCurrentState()));
    }

    private void checkAnimalEnergy(Cells myCell){
        if(energyMap.get(myCell) == 0){
            myCell.setMyNextState(0);
            myCell.updateMyCurrentState();
        }
    }

    private void checkAnimalReproduction(Cells myCell){
        if(reproductionMap.get(myCell) == 5){

        }
    }
}
