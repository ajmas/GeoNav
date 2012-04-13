package osj.geonav.iface;


/**
 * This class is designed to handle the reading of messages
 * from a GeoNavInputStream and dispatch the messages to the
 * listeners registered with the class. To start processing
 * call start() and to stop processing call stop().
 * 
 * @author Andre-John Mas
 *
 */
public interface GeoMessageDispatcher {
    
    /**
     * Adds the listener to the dispatcher
     * 
     * @param listener the listener to add
     */    
    public void addGeoMessageListener ( GeoMessageListener listener );
    
    /**
     * Removes the listener from the dispatcher
     * 
     * @param listener the listener to remove
     */
    public void removeGeoMessageListener ( GeoMessageListener listener );
    
    
    /**
     * Starts the dispatcher.
     */
    public void start();
    
    /**
     * Stops the dispatcher.
     */
    public void stop();
}
