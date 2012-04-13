package osj.geonav.nmea;

public class GPRMCMessage extends NMEAGenericMessage
{
    
    private static final String TYPE_STRING = "GPRMC";
    private static final String LONG_NAME = "Recommended Minimum Specific GNSS Data";

/*
    1)     UTC time of position fix, hhmmss.sss format.

    2)     Status, A = data valid, V = data not valid.

    3)     Latitude, ddmm.mmmm format.

    4)     Latitude hemisphere, N or S.

    5)     Longitude, dddmmm.mmmm format.

    6)     Longitude hemisphere, E or W.

    7)     Speed over ground, 0.0 to 1851.8 knots.

    8)     Course over ground, 000.0 to 359.9 degrees, true.

    9)     Date, ddmmyy format.

    10) Magnetic variation, 000.0 to 180.O.

    11) Degrees

    12) Checksum.
*/
    
    public GPRMCMessage()
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
    
//    public String toString ()
//    {
//        int i=1;
//        
//        StringBuilder strBuilder = new StringBuilder();
//        
//        strBuilder.append(TYPE_STRING + " [\n");
//        strBuilder.append("UTC time of position fix: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Status: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Latitude: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Latitude hemisphere: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Longitude: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Longitude hemisphere: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Speed over ground: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Course over ground: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Date: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Magnetic Variation: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Degrees: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("]\n");
//        //strBuilder.append("Checksum: " + this.dataList.get(i++) +"\n");
//        
//        return strBuilder.toString();
//    }
}
