package osj.geonav.iface;

/**
 * This interface describes a 'track point'.
 * A track point is a recorded point along a
 * route.
 */
public interface WayPoint extends Location
{
    /**
     * Returns the waypoint id
     *
     * @return String
     */
    public String getWaypointId();
    
    /**
     * Returns the index of the waypoint. If it
     * is undefined, then its value is -1.
     *
     * @return int
     */
    public int getWaypointIdx();
    
    
    /**
     * Returns the comment associated with the waypoint,
     * if any.
     *
     * @return String
     */
    public String getComment();
}
