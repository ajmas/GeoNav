package osj.geonav.nmea;

import java.util.ArrayList;
import java.util.List;

import osj.geonav.iface.RelativeSatellitePosition;
import osj.geonav.iface.SatellitesInView;


public class GPGSVMessage extends NMEAGenericMessage implements SatellitesInView
{
    private static final String TYPE_STRING = "GPGSV";
    
    public GPGSVMessage()
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
    
    public int getMessageNumber()
    {
       return Integer.parseInt(getData().get(2));
    }
    
    public int getMessageTotal()
    {
        return Integer.parseInt(getData().get(1));
    }
    
    public int getSatellitesInView()
    {
        return Integer.parseInt(getData().get(3));
    }
    
    public List<RelativeSatellitePosition> getSatellites()
    {
        List<RelativeSatellitePosition> list = new ArrayList<RelativeSatellitePosition>();
        
        int len = getData().size();
        for ( int i=4; i<len; i+=4 )
        {
            if ( getData().get(i) != null && getData().get(i).trim().length() > 0 )
            {
                try {
                    SatelliteInfo satInfo = new SatelliteInfo();
                    satInfo.id = Integer.parseInt(getData().get(i));
                    satInfo.elevation = Double.parseDouble(getData().get(i+1));
                    satInfo.azimuth = Double.parseDouble(getData().get(i+2));
                    
                    if ( getData().size() > 19 && getData().get(i+3).trim().length() > 0)
                        satInfo.snr = Integer.parseInt(getData().get(i+3));
                    else
                        satInfo.snr = -1;   
                    list.add(satInfo);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println( "getData().size(): " + getData().size() );
                    System.err.println( "i: " + i );
//                    throw e;
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
    
    static class SatelliteInfo implements RelativeSatellitePosition
    {
        private int id;
        private double elevation;
        private double azimuth;
        private int snr;
        
        public int getSatelliteId()
        {
            return this.id;
        }

        public double getElevation()
        {
            return this.elevation;
        }

        public double getAzimuth()
        {
            return this.azimuth;
        }

        public int getSignalToNoiseRatio()
        {
            return this.snr;
        }
        
    }
    
}
