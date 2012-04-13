package osj.geonav.nmea;

public class UnknownMessageTypeException extends Exception {

    /** */
    private static final long serialVersionUID = 3101951412470415025L;

    /** @see java.lang.Exception */
    public UnknownMessageTypeException() {
        super();
    }

    /** @see java.lang.Exception */
    public UnknownMessageTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    /** @see java.lang.Exception */
    public UnknownMessageTypeException(String message) {
        super(message);
    }

    /** @see java.lang.Exception */
    public UnknownMessageTypeException(Throwable cause) {
        super(cause);
    }

    
}
