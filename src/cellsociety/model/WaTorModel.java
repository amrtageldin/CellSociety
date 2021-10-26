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
    private Map<Cells, Integer> reproductionMap = new HashMap<>();
    private Map<Cells, Integer> energyMap = new HashMap<>();

    public WaTorModel(String type) {
        super(type);

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
        setUpAnimalReproduction(myCell);

    }
    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){
        checkAnimals(myCell);
    }

    private void checkAnimals(Cells myCell){
        Map<Integer, Consumer<Integer>> animalCheck =
                Map.of(Integer.parseInt(statesBundle.getString(SHARK)), integer -> setUpAnimalEnergy(myCell),
                        Integer.parseInt(statesBundle.getString(FISH)), integer -> setUpAnimalReproduction(myCell));
        consumerGenerateNextState(myCell.getCurrentState(), animalCheck.get(myCell.getCurrentState()));
    }
}
