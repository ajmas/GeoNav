package osj.geonav.nmea;

import java.util.List;

import osj.geonav.iface.GeoMessage;

/**
 * 
 * TODO Description and implementation details.
 */
public interface NMEAMessage extends GeoMessage
{

    /**
     * Returns the data as a list, as if it had been in the nmea message.
     * It should include all the fields, minus the commas and start '$'.
     *
     * @return java.util.List
     */
    public List<String>  getData ();
    
    /**
     * 
     * TODO implementation details
     *
     * @param data
     */
    public void setData ( List<String>  data );

    /**
     * @return java.lang.String
     */
    public String getTypeCode();
    
    
    public String getLongName();
    
    /** */
    public String toNMEAString( boolean includeChecksum );
    
}
