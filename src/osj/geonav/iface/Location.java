package osj.geonav.iface;

/**
 * Interface to be implemented by classes providing
 * location information.
 * 
 * @author Andre-John Mas
 */
public interface Location extends GeoMessage {
    
    /** 
     * Returns the time, in milliseconds. If no time 
     * information is available then Long.MIN_VALUE
     * is returned -- is that ok?
     *
     * @return double
     */       
    public long   getTime();
    
    /** 
     * Returns the longitude, in degrees. If no longitude 
     * information is available Double.NaN is returned.
     *
     * @return double
     */       
    public double getLongitude();
    
    /** 
     * Returns the latitude, in degrees. If no latitude 
     * information is available Double.NaN is returned.
     *
     * @return double
     */    
    public double getLatitude();
    
    /** 
     * Returns the height, in meters. If no height 
     * information is available Double.NaN is returned.
     *
     * @return double
     */
    public double getHeight();
}
