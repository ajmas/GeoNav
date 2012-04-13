/*
 * $Author:  Mas $
 * $Date: Aug 25, 2006 $
 * $Revision: 1.0 $
 */

package osj.geonav.garmin.message;

/**
 * TODO Description and implementation details.
 */
public interface GarminMessage
{
    /**
     * Returns the message type, as specified in the Garmin
     * specification
     *
     * @return message type
     */
    public int getType();
}
