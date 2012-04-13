package osj.utils.conversion;

/**
 * This exception is thrown if the conversion can not take place
 * 
 */
public class UnsupportedConversionException extends Exception
{
    /** serialVersionUID */
    private static final long serialVersionUID = 6268522044204442258L;

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     */
    public UnsupportedConversionException()
    {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param   message   the detail message. The detail message is saved for 
     *          later retrieval by the {@link #getMessage()} method.
     */
    public UnsupportedConversionException( String message )
    {
        super( message );
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public UnsupportedConversionException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> (which
     * typically contains the class and detail message of <tt>cause</tt>).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public UnsupportedConversionException( Throwable cause )
    {
        super( cause );
    }

}
