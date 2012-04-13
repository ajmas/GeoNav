package osj.geonav.iface;

import java.util.List;

/**
 * A route, consisting of trackpoint and waypoints
 * 
 */
public interface Route
{
    /**
     * Returns the id of the route
     *
     * @return int
     */
    public int getRouteId();
    
    /**
     * Returns the name of the route
     *
     * @return String
     */
    public String getName();
    
    /**
     * Returns a description of the route
     *
     * @return String
     */
    public String getDescription();
    
    /**
     * Returns the waypoints associated with this route
     *
     * @return List of waypoints
     */
    public List<WayPoint> getWayPoints();
    
    /**
     * Returns the trackpoints associated with this route
     *
     * @return List of trackpoints
     */
    public List<TrackPoint> getTrackPoints();    
}
