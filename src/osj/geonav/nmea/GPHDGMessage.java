package osj.geonav.nmea;

import osj.geonav.iface.Heading;

public class GPHDGMessage extends NMEAGenericMessage implements Heading {

    private static final String TYPE_STRING = "GPHDG";
    
    public GPHDGMessage() {
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
    
    public double getTrueHeading() {
        return Double.NaN;
    }
    
    public double getMagneticHeading() {
        if ( this.getData().get(1).trim().length() == 0 )
        {
            return Double.NaN;
        }
        else
        {
            return Double.parseDouble(this.getData().get(1));
        }
    }

}
