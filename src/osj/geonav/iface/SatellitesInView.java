package osj.geonav.iface;

import java.util.List;

public interface SatellitesInView extends GeoMessage
{
    /**
     * Returns the list of the satellites in view
     *
     * @return List
     */
    public List<RelativeSatellitePosition> getSatellites();
    
    public int getMessageNumber();
}
