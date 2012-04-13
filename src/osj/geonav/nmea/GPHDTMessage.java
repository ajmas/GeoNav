package osj.geonav.nmea;

import osj.geonav.iface.Heading;

public class GPHDTMessage extends NMEAGenericMessage implements Heading {

    private static final String TYPE_STRING = "GPHDT";
    
    public GPHDTMessage() {
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
    
    public double getMagneticHeading() {
        return Double.NaN;
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

}
