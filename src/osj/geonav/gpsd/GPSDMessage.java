package osj.geonav.gpsd;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import osj.geonav.iface.GeoMessage;
import osj.geonav.iface.Heading;
import osj.geonav.iface.Location;


/**
 * Explanation of protocol can be found at:
 * <blockquote>
 *   http://gpsd.berlios.de/gpsd.html
 * </blockquote>  
 * Sample messages ( from http://gpsd.davisnetworks.com/bin/view/Main/FreezeBugPL2303):
 * <pre>
 * GPSD,A=?,B=9600 8 N 1,C=?,D=?,E=?,F=/dev/usb/tts/0,G=?,I=?,K=1 /dev/usb/tts/0,L=2 2.30 abcdefgiklmnopqrstuvwxyz,M=0,N=0,O=?,P=?,Q=?,R=1,S=0,T=?,U=?,V=?,W=1,X=946686279.813908,Y=?,Z=1
 * GPSD,A=83.888,B=4800 8 N 1,C=1.00,D=2006-06-11T04:15:11.33Z,E=26.40 9.60 nan,F=/dev/usb/tts/0,G=GPS,I=SiRF-II binary,K=1 /dev/usb/tts/0,L=2 2.30 abcdefgiklmnopqrstuvwxyz,M=3,N=1,O=MID50 1149999311.32 0.005 38.865923 -77.108561   83.89  9.60        ? 306.8048    0.035  0.173 170.6907 19.20        ?,P=38.865923 -77.108561,Q=6 3.30 1.20 2.90 0.00 0.00,R=1,S=1,T=306.8048,U=0.173,V=0.019,W=1,X=946686392.421573,Y=MID50 ? 9:27 73 228 30 1:19 45 46 40 1:8 59 301 42 1:11 50 144 41 1:28 35 297 42 1:3 13 54 0 0:13 5 199 0 0:17 12 235 34 1:29 7 321 0 0:,Z=1
 * </pre>
 */
public class GPSDMessage implements GeoMessage, Heading, Location
{
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ");
    
    private double altitude = Double.NaN;
    @SuppressWarnings("unused")
    private boolean altitudeEstimated = true;
    private long   time = Long.MIN_VALUE;
    private double headingTrue = Double.NaN;
    private double headingMagnetic = Double.NaN;
    private float longitude = Float.NaN;
    private float latitude = Float.NaN;
    
    @SuppressWarnings("unused")
    private String protocolVersion;
    
    public GPSDMessage( )
    {
    }

    public static GPSDMessage parseMessage( String mesgText )
    {
        GPSDMessage message = new GPSDMessage();
        
        String[] fields = mesgText.split(",");
        if ( !( fields.length > 0 && fields[0].equals("GPSD") ) )
        {
            //TODO Probably throw an exception
            return null;
        }
        
        for ( int i=1; i< fields.length; i++ )
        {
            char c = fields[i].charAt(0);
            String value = fields[i].substring(2);
            switch ( c )
            {
                case 'A': // INFO: altitude
                    if ( ! value.equals("?") )
                    {
                        message.altitude = Double.parseDouble(value);
                    }
                    break;
                case 'B': // INFO: Serial port configuration info
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;   
                case 'C': // INFO: Cycle time in seconds 
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;   
                case 'D': // INFO: UTC time in ISO 8601 Format
                    if ( ! value.equals("?") )
                    {
                        try
                        {
                            message.time = formatter.parse(value).getTime();
                        }
                        catch ( ParseException e )
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    break; 
                case 'E': // INFO: ??
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;                     
                case 'F': // INFO: Device
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break; 
                case 'H': // INFO: Heading
                    if ( ! value.equals("?") )
                    {
                        message.headingTrue = Double.parseDouble(value);
                    }
                    break; 
                case 'I': // INFO: Device Identifier
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;    
                case 'K': // INFO: List of known GPS devices on server
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break; 
                case 'L': // INFO: gpsd version info
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                        String[] subFields = value.split(" ");
                        message.protocolVersion = subFields[0];
                        //gpsd version
                        //accepted letters
                    }
                    break;   
                case 'M': // INFO: NMEA mode
                    if ( ! value.equals("?") )
                    {
                        int mode = Integer.parseInt(value);
                        if ( mode == 3 )
                        {
                            message.altitudeEstimated = false;
                        }
                    }
                    break;                    
                case 'N': // INFO: GPS Driver Mode
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;  
                case 'O': // INFO: Complete time/position/velocity as unit
                    if ( ! value.equals("?") )
                    {
                        String[] subFields = value.split(" ");
                        // not implemented for now
                        //0 - last NMEA message tag                        
                        //1 - timestamp - seconds since the Unix epoch, UTC
                        //2 - time error
                        message.latitude = parseFloat(subFields[3]);
                        message.longitude = parseFloat(subFields[4]);
                        message.altitude = parseFloat(subFields[5]);
                        //6 - horizontal error est.                     
                        //7 - vertical error est.                       
                        message.altitude = parseFloat(subFields[8]);
                        //8 - speed over ground
                        //9 - climb/sink (m/s)
                        //10 - est. error in course over ground
                        //11 - est. error in speed over ground
                        //12 - est. error in climb/sink
                        if ( !subFields[13].equals("?") )
                        {
                            int mode = Integer.parseInt(subFields[13]);
                            if ( mode == 3 )
                            {
                                message.altitudeEstimated = false;
                            }
                        }
                    }
                    break;                      
                case 'P': // INFO: Position
                    if ( ! value.equals("?") )
                    {
                        String[] subFields = value.split(" ");
                        message.latitude = parseFloat(subFields[0]);
                        message.longitude = parseFloat(subFields[1]);
                    }
                    break;   
                case 'R': // INFO: Raw Mode
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;   
                case 'S': // INFO: NMEA Status
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;           
                case 'T': // INFO: Track made good, True North
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break; 
                case 'U': // INFO: Current rate of climb
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;        
                case 'V': // INFO: Current speed over ground
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;       
                case 'W': // INFO: Watcher mode
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;  
                case 'X': // INFO: GPS online/offline info
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;    
                case 'Y': // INFO: Satellite info
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break; 
                case 'Z': // INFO: Daemon profiling information (intended for gpsd developers)
                    if ( ! value.equals("?") )
                    {
                        // not implemented for now
                    }
                    break;                    
            }
        }
        return message;
    }

//    private static Float parseInt ( String value )
//    {
//        try
//        {
//            return Float.parseFloat(value);
//        }
//        catch ( NumberFormatException ex )
//        {
//            return Float.NaN;
//        }
//    }
    
    private static Float parseFloat ( String value )
    {
        try
        {
            return Float.parseFloat(value);
        }
        catch ( NumberFormatException ex )
        {
            return Float.NaN;
        }
    }
    
//    private static Double parseDouble ( String value )
//    {
//        try
//        {
//            return Double.parseDouble(value);
//        }
//        catch ( NumberFormatException ex )
//        {
//            return Double.NaN;
//        }
//    }
    
    public double getTrueHeading()
    {
        // TODO Auto-generated method stub
        return this.headingTrue;
    }

    public double getMagneticHeading()
    {
        // TODO Auto-generated method stub
        return this.headingMagnetic;
    }

    public long getTime()
    {
        return this.time;
    }

    public double getLongitude()
    {
        return  this.longitude;
    }

    public double getLatitude()
    {
        return this.latitude;
    }

    public double getHeight()
    {
        return this.altitude;
    }
    
    public String toString() {
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuffer strBuf = new StringBuffer();
        for ( int i=0; i<fields.length; i++ )
        {
            if ( (fields[i].getModifiers() & Modifier.STATIC) != 0 )
            {
                continue;
            }
            strBuf.append(fields[i].getName());
            strBuf.append("=");
            try
            {
                strBuf.append(fields[i].get(this));
            }
            catch ( Exception e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            strBuf.append(", ");
            System.out.println("x");            
        }
        if ( strBuf.length() > 0 )
        {
            strBuf.setLength(strBuf.length()-2);
        }
        return strBuf.toString();
        
    }
}
