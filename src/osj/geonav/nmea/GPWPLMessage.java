package osj.geonav.nmea;

import osj.geonav.iface.WayPoint;
import osj.utils.conversion.AngleConverter;
import osj.utils.conversion.UnsupportedConversionException;

/**
 * waypoint load
 * 
 * source: http://www.gpsbabel.org/tips/marine_mac.html
 */
public class GPWPLMessage extends NMEAGenericMessage implements WayPoint
{

    /** 
     * Default Constructor
     */
    public GPWPLMessage()
    {
        super();
    }

    public String getWaypointId()
    {
        return this.getData().get(5);
    }

    /**
     * Value -1 is always returned.
     * 
     * @see osj.geonav.iface.WayPoint#getWaypointIdx()
     */
    public int getWaypointIdx()
    {
        return -1;
    }

    /**
     * No comment returned
     * 
     * @see osj.geonav.iface.WayPoint#getComment()
     */
    public String getComment()
    {
        return null;
    }

    /**
     * No time value returned
     * 
     * @see osj.geonav.iface.Location#getTime()
     */
    public long getTime()
    {       
        return Long.MIN_VALUE;
    }

    public double getLongitude()
    {
        String longitude = this.dataList.get(3);
        double value = convert(longitude);
        
        if ( !Double.isNaN(value) && this.dataList.get(4).equalsIgnoreCase("E") )
        {
            value = value * -1;
        }

        return value;
    }

    public double getLatitude()
    {
        String longitude = this.dataList.get(1);
        double value = convert(longitude);
        
        if ( !Double.isNaN(value) && this.dataList.get(2).equalsIgnoreCase("S") )
        {
            value = value * -1;
        }

        return value;
    }

    /**
     * No height information returned
     * 
     * @see osj.geonav.iface.Location#getHeight()
     */
    public double getHeight()
    {
        return Double.NaN;
    }

    private double convert ( String str )
    {
        if ( str != null && str.length() > 0 )
        {
            try
            {
                AngleConverter.getInstance().convert("ddmm.mmm","degrees",str);
            }
            catch ( UnsupportedConversionException e )
            {
                // ignore
            }
        }

        return Double.NaN;
    }
}
