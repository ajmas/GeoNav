package osj.geonav.iface;


/**
 * This interface is used by classes that provide incoming
 * GeoMessages. When new geographic data comes in it is
 * dispatched to all the registered listening classes.
 * 
 */
public interface GeoDataStream
{
    /**
     * Adds the GeoMessageListener 
     *
     * @param l
     */    
    public void addGeoDataListener( GeoMessageListener l );
    
    /**
     * Removes the GeoMessageListener 
     *
     * @param l
     */
    public void removeGeoDataListener ( GeoMessageListener l );
}
