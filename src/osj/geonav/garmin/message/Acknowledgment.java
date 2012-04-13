package osj.geonav.garmin.message;


/**
 * A message acknowledgement. Only used for positive acknowledgements,
 * since negative acknowledgments are handled by an IOException.
 * It also specifies the type of message be acknowledged.
 */
public class Acknowledgment implements GarminMessage
{
    private static final int MESG_TYPE = 6;
    
    private int mesgTypeAcked;
    
    /**
     * A message acknowledgement
     * 
     * @param mesgTypeAcked the type of message being acknowledged
     */
    public Acknowledgment ( int mesgTypeAcked )
    {
        this.mesgTypeAcked = mesgTypeAcked;
    }
    
    public String toString()
    {
        return "Acknowledgment:[mesgType=" + this.mesgTypeAcked +"]";
    }

    /**
     * Returns the type of message being acknowledged
     *
     * @return int
     */
    public int getMessageTypeAcked ()
    {
        return this.mesgTypeAcked;
    }
    
    public int getType()
    {
        return MESG_TYPE;
    }
}
