package osj.geonav.nmea;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This class is designed to represent NMEA urls, so that a program can correctly
 * refer to an NMEA device. The URL format would support both local devices and
 * remote devices. The remote devices would have to be supported by a server that
 * emulates a local device. The general URL format is similar to that used by
 * HTTP:
 * 
 *   nmea://&lt;host&gt;:&lt;port&gt;/&lt;path&gt;
 *   
 * Examples, for remote devices, would include:
 * 
 *   nmea://myhost/dev/tty.usbserial
 *   nmea://myhost:8080/com3
 *   
 * and for local devices:
 * 
 *   nmea:///dev/tty.usbserial
 *   nmea:///com3
 *   
 * extra parameters would be passed in the same way as query parameters in an HTTP
 * URL:
 * 
 *    nmea:///dev/tty.usbserial?baud=4200
 *    
 * for a remote device the user/password element is also supported:
 * 
 *    nmea://myuser:mypass@myhost/dev/tty.usbserial
 *
 * To think about:
 *    - ability to list available devices
 *    
 * @author ajmas
 *
 */
public class NmeaUrl {

    public NmeaUrl ( String urlString ) {
        
    }
    
    public static void main ( String[] args ) {
        try {
            URI uri = new URI ( "nmea://myhost/dev/tty.usbserial");
            uri = new URI ( "nmea:///dev/tty.usbserial");
            
            System.out.println( uri.getScheme() );
            System.out.println( uri.getHost() );
            System.out.println( uri.getPort() );
            System.out.println( uri.getPath() );
            
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
