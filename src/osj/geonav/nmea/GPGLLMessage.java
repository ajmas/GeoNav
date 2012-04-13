package osj.geonav.nmea;

import java.util.ArrayList;
import java.util.List;

import osj.geonav.iface.Location;

import osj.utils.conversion.AngleConverter;
import osj.utils.conversion.UnsupportedConversionException;


public class GPGLLMessage extends NMEAGenericMessage implements Location
{
//    private static final int MSL_ALTITUDE = 9;
    
    private static final String TYPE_STRING = "GPGLL";
    private static final String LONG_NAME = "Geographic position - latitude / longitude";
    private static final int FIELD_COUNT = 12;
    
    /** */
    public GPGLLMessage()
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
        String longitude = this.dataList.get(3);
        double value = convert(longitude);
        
        if ( !Double.isNaN(value) && this.dataList.get(4).equalsIgnoreCase("S") )
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


    private double convert ( String str )
    {
        if ( str.length() > 0 )
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

    public double getHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    public long getTime()
    {
        // field 5
        return 0L;
    }
    
    public String getStatus()
    {
        return getData().get(6);
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
