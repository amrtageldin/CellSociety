package cellsociety.Errors;

/**
 * This class deals with error handling for the backend. If an error is ever caught,
 * it is sent into this class for storing. If the error then is not something that can
 * be handled by the application just not responding to it, it is displayed on the view.
 * Each backend class has its own instance of this error factory which can be accessed by the controller
 * to then be sent to the view.
 */
public class ErrorFactory {
    private boolean errorExists;
    private String currentErrorKey;

    /**
     * This updates the current error key when an error is found. It also sets the errorExists
     * boolean to true so that the controller knows that an error has been found and needs to be returned.
     * @param errorKey: the String key corresponding to the error message to be displayed on the view.
     *                The value of this key is found in the view resource bundles.
     */
    public void updateError(String errorKey){
        errorExists = true;
        currentErrorKey = errorKey;
    }

    /**
     * A boolean method that returns whether or not an error has been found. If it has been found,
     * then the getErrorKey() method is called to retrieve the error.
     * @return errorExists: a boolean that tells whether or not an error has been found.
     */
    public boolean errorExists(){
        return errorExists;
    }

    /**
     * If there is a valid error found, the key corresponding to that error is returned through this message,
     * which is then processed in the controller to be displayed in the view.
     * @return currentErrorKey: the String corresponding to the valid error that needs to be shown in the view.
     */
    public String getErrorKey(){
        return currentErrorKey;
    }

    /**
     * Once the error has been displayed, this method updates the boolean errorExists to ensure
     * that there is no active error within the factory.
     */
    public void setErrorNoLongerExists(){
        errorExists = false;
    }
}
