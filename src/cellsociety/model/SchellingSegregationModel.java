package cellsociety.model;

public class SchellingSegregationModel extends CellSocietyModel{

    public SchellingSegregationModel(String type) { super(type);}

    @Override
    public int getNextState(Cells myCell) {return myCell.getMyNextState();}

    @Override
    public void setNextState(Cells myCell, int row, int col, Cells[][] myGrid){

    }
}
