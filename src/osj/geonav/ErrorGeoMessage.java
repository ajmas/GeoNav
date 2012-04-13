package osj.geonav;

import osj.geonav.iface.GeoMessage;

/**
 * This is a special error message which is generated when 
 * a sub system runs into an error. Using a message like
 * this allows the error handler to be asychnronous. 
 * 
 * @author Andre-John Mas
 */
public class ErrorGeoMessage implements GeoMessage {

    /**
     * The key used to find the translated text.
     */
    private String    errorKey;
    /**
     * The Throwable that caused the error. It may be null.
     */
    private Throwable error;
    /**
     * The error message in english
     */
    private String    errorMessage;
    
    ErrorGeoMessage ( String errorKey, String errorMessage, Throwable error )
    {
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
        this.error = error;
    }
        
    /**
     * @return the errorKey
     */
    public String getErrorKey() {
        return errorKey;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Note, this may return null.
     * 
     * @return the error
     */
    public Throwable getError()
    {
        return this.error;
    }
    
    public String toString()
    {
        return this.errorMessage;
    }
}
