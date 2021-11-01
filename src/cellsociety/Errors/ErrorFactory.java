package cellsociety.Errors;

public class ErrorFactory {
    private boolean errorExists;
    private String currentErrorKey;

    public void updateError(String errorKey){
        errorExists = true;
        currentErrorKey = errorKey;
    }

    public boolean errorExists(){
        return errorExists;
    }

    public String getErrorKey(){
        return currentErrorKey;
    }

    public void setErrorNoLongerExists(){
        errorExists = false;
    }
}
