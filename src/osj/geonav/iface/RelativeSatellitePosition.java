package osj.geonav.iface;

/**
 * 
 * TODO Not really happy with name - find something better
 */
public interface RelativeSatellitePosition
{

    public int getSatelliteId();
    
    public double getElevation();
    
    public double getAzimuth();
    
    public int getSignalToNoiseRatio();
}
