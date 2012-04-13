package osj.geonav.nmea;

import osj.geonav.iface.GroundSpeed;
import osj.geonav.iface.Heading;

/** 
 * Course Over Ground and Ground Speed
 * <pre>
 * $GPVTG,&lt;1&gt;,T,&lt;2&gt;,M,&lt;3&gt;,N,&lt;4&gt;,K,&lt;5&gt;&lt;CR&gt;&lt;LF&gt;
 * </pre>
 *  <ol>
 *    <li>  True course over ground, 000 to 359 degrees.
 *    <li>  Magnetic course over ground, 000 to 359 degrees.
 *    <li>  Speed over ground, 00.0 to 999.9 knots.
 *    <li>  Speed over ground, 00.0 to 1851.8 ko/hr.
 *  </ol>    
 * 
 */
public class GPVTGMessage extends NMEAGenericMessage implements Heading, GroundSpeed
{

    private static final String TYPE_STRING = "GPVTG";
    private static final String LONG_NAME = "Course Over Ground and Ground Speed";

    /** */
    public GPVTGMessage()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    
    /**
     * @see osj.geonav.nmea.NMEAGenericMessage#getTypeCode()
     */
    @Override
    public String getTypeCode()
    {
        return TYPE_STRING;
    }


    /**
     * @see osj.geonav.nmea.NMEAGenericMessage#getLongName()
     */
    @Override
    public String getLongName()
    {
        return LONG_NAME;
    }
    
    public String toString ()
    {
        int i=1;
        
        StringBuilder strBuilder = new StringBuilder();
        
        strBuilder.append(TYPE_STRING + " [\n");
        strBuilder.append("True course over ground: " + this.dataList.get(i++) +"\n");
        strBuilder.append("Magnetic course over ground: " + this.dataList.get(i++) +"\n");
        strBuilder.append("Speed over ground: " + this.dataList.get(i++) +"\n");
        
        strBuilder.append("Speed over ground: " + this.dataList.get(i++) +"\n");
        strBuilder.append("]\n");
        
        return strBuilder.toString();
    }


    public double getMagneticHeading() {
        if ( this.getData().get(3).trim().length() == 0 )
        {
            return Double.NaN;
        }
        else
        {
            return Double.parseDouble(this.getData().get(3));
        }
    }
    
    public double getTrueHeading() {
        if ( this.getData().get(1).trim().length() == 0 )
        {
            return Double.NaN;
        }
        else
        {
            return Double.parseDouble(this.getData().get(1));
        }
    }


    public double getGroundSpeedKph() {
        if ( this.getData().get(3).trim().length() == 0 )
        {
            return Double.NaN;
        }
        else
        {
            return Double.parseDouble(this.getData().get(4));
        }
    }


    public double getGroundSpeedKnots() {
        if ( this.getData().get(3).trim().length() == 0 )
        {
            return Double.NaN;
        }
        else
        {
            return Double.parseDouble(this.getData().get(5));
        }
    }
    
    
}
