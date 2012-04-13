package osj.geonav.nmea;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import osj.geonav.iface.Location;

import osj.utils.conversion.AngleConverter;
import osj.utils.conversion.UnsupportedConversionException;


/**


1)     UTC time of position fix, hhmmss.sss format

2)     Latitude, ddmm.mmmm format.

3)     Latitude hemisphere, N or S.

4)     Longitude, dddmm.mmmm format.

5)     Longitude hemisphere, E or W.

6)     Position Fix Indicator,

        0 = fix not available, or invalid.

        1 = GPS SPS Mode, fix valid.
        
        2 = Differential GPS, SPS Mode, fix valid.
        
        3 = GPS PPS Mode, fix valid.

7)     Number of sate1lites in use, 00 to 12.

8)     Horizontal Dilution of Precision, 0.5 to 99.9.

9)     MSL Altitude, -9999.9 to 99999.9 meters.

10) Geoidal height, -999.9 to 9999.9 meters.

11) Differential GPS (RTCM SC-104) data age, number of seconds since last valid RTCM transmission (nu1l if non-DGPS).

12) Differential Reference Station ID, 0000 to 1023. (null if non-DGPS)

**/

public class GPGGAMessage extends NMEAGenericMessage implements Location
{
//    private static final int MSL_ALTITUDE = 9;
    
    private static final String TYPE_STRING = "GPGGA";
    private static final String LONG_NAME = "Global positioning system fixed data";
    private static final int FIELD_COUNT = 12;
    
    /** */
    public GPGGAMessage()
    {
        super();
        List<String> l = new ArrayList<String>();
        l.add('$'+TYPE_STRING);
        for ( int i=0; i<FIELD_COUNT; i++ )
        {
            l.add("");
        }
        setData(l);
    }

    

    /**
     * @see osj.geonav.nmea.NMEAGenericMessage#setData(java.util.List)
     */
    @Override
    public void setData( List<String> data )
    {
        if ( data.size() == 15 )
        {
            List<String> l = new ArrayList<String>();
            l.addAll(data);
            data = l;
            
            String val = data.remove(9) + "," +data.remove(9);
            data.add(9,val);
            val = data.remove(10) + "," +data.remove(10);
            data.add(10,val);
        }
        
        super.setData( data );
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



    public double getLongitude()
    {
        String longitude = this.dataList.get(4);
        double value = convert(longitude);
        
        if ( !Double.isNaN(value) && this.dataList.get(5).equalsIgnoreCase("S") )
        {
            value = value * -1;
        }

        return value;
    }

    public double getLatitude()
    {
        String longitude = this.dataList.get(2);
        double value = convert(longitude);
        
        if ( !Double.isNaN(value) && this.dataList.get(3).equalsIgnoreCase("S") )
        {
            value = value * -1;
        }

        return value;
    }


    private double convert ( String str )
    {
        if ( str.length() > 0 )
        {
            try
            {
                return (Double) AngleConverter.getInstance().convert("ddmm.mmm","degrees",str);
            }
            catch ( UnsupportedConversionException e )
            {
                // ignore
                e.printStackTrace();
            }
        }

        return Double.NaN;
    }

    public double getHeight() {
        String altitude = this.dataList.get(9);
        String[] parts = altitude.split(",");
        double value = Double.NaN;
        try
        {
            value = new Double( parts[0] );
        } catch ( NumberFormatException ex )
        {
            
        }
        return value;
    }



    public long getTime()
    {
        SimpleDateFormat formater = new SimpleDateFormat("HHmmss.SSS");
        formater.setTimeZone(TimeZone.getTimeZone("UTC"));
        try
        {         
            String str = getData().get(1);

            if ( str.indexOf('.') < 0 )
            {
                str += ".";
            }
            if ( str.substring(str.indexOf('.')).length() < 3 )
            {
                int v = 3 - (str.substring(str.indexOf('.')).length() -1);
                for ( int i=0; i<v; i++ )
                {
                    str += "0";
                }
            }
            return formater.parse(str).getTime();
        }
        catch ( ParseException e )
        {
            return Long.MIN_VALUE;
        }
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
//        
//        strBuilder.append("Latitude hemisphere: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Longitude: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Longitude hemisphere: " + this.dataList.get(i++) +"\n");
//        
//        strBuilder.append("Position Fix Indicator: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Number of satellites in use: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Horizontal Dilution of Precision: " + this.dataList.get(i++) +"\n");
//        
//        strBuilder.append("MSL Altitude: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Geoidal height: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("Differential GPS (RTCM SC-104) data age: " + this.dataList.get(i++) +"\n");
//        
//        strBuilder.append("Differential Reference Station ID: " + this.dataList.get(i++) +"\n");
//        strBuilder.append("]\n");
//        
//        return strBuilder.toString();
//    }
}
