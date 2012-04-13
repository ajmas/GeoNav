package osj.geonav;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

//import osj.geonav.iface.GeoDataStream;
import osj.geonav.gpsd.GpsdMessageDispatcher;
import osj.geonav.iface.GeoMessage;
import osj.geonav.iface.GeoMessageDispatcher;
import osj.geonav.iface.GeoMessageListener;
import osj.geonav.iface.Location;
import osj.geonav.inspector.BitmapCompassView;
import osj.geonav.inspector.CompassView;
import osj.geonav.inspector.HeadingCompassView;
import osj.geonav.inspector.MapView;
import osj.geonav.inspector.RAWDataInspector;
import osj.geonav.inspector.SatelliteView;
import osj.geonav.inspector.StatusInspector;
import osj.geonav.inspector.TextDisplayInspector;
import osj.geonav.nmea.NMEAMessageDispatcher;
import osj.geonav.nmea.NMEAMessage;

public class Main {

    static class MessageListener implements GeoMessageListener
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PrintStream out;
        
        MessageListener () throws IOException
        {
            out  = new PrintStream( new FileOutputStream("routelog.txt") ); 
            
        }
        
        public void newMessage(GeoMessage message) {
            if ( message instanceof Location )
            {
                displayLocation(new Date(), (Location)message);
                
                if ( message instanceof NMEAMessage )
                {
                    System.out.println(message);
                }
            }            
        }
        
        public void displayLocation ( Date date, Location location )
        {
            System.out.println("["+dateFormat.format(date)+"]  "
                    + location.getLatitude()  + ", "
                    + location.getLongitude() + ", "
                    + location.getHeight()
                    );
            
            out.println("["+dateFormat.format(date)+"]  "
                    + location.getLatitude()  + ", "
                    + location.getLongitude() + ", "
                    + location.getHeight()
                    ); 
            out.flush();
        }        
    }
    
    
    /**
     * Connect to the serial port specifed by the 'port' parameter, and then
     * returns the InputStream for the connection. 
     * 
     * @param port the port to connect to
     * @return InputStream
     * @throws IOException
     * @throws Exception
     */
    static InputStream connectToSerial ( String port ) throws IOException, Exception
    {
        InputStream in = null; 
        
        CommPortIdentifier portIdentifier = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() ) 
        {
            portIdentifier = (CommPortIdentifier) portEnum.nextElement();
            System.out.println(portIdentifier.getName() + " - " + portIdentifier.getPortType() );
            if ( portIdentifier.getName().endsWith(port) )//"cu.BTGPS-SPPslave-1") )
            {
                break;
            }
        }

        //portIdentifier = CommPortIdentifier.getPortIdentifier("/dev/cu.BTGPS-SPPslave");
        System.out.println("current owner: " + portIdentifier.getCurrentOwner());
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
            return null;
        }

        CommPort commPort = portIdentifier.open("AJ's Garmin tools",2000);
        if ( commPort instanceof SerialPort )
        {
            SerialPort serialPort = (SerialPort) commPort;
            serialPort.setSerialPortParams(4800,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
            in = serialPort.getInputStream();
            
            return in;
        }
        
        throw new IOException("Error: Only serial ports are handled. port requested: " + commPort.getClass());

    }
    
    private static void initInspectors ( GeoMessageDispatcher dispatcher )
    {
        RAWDataInspector inspector = new RAWDataInspector();
        inspector.setObject(dispatcher);
        
        CompassView compass = new CompassView();
        compass.setObject(dispatcher);
                
        BitmapCompassView compass2 = new BitmapCompassView();
        compass2.setObject(dispatcher);
        
        HeadingCompassView compass3 = new HeadingCompassView();
        compass3.setObject(dispatcher);
        
        SatelliteView satview = new SatelliteView();
        satview.setObject(dispatcher);
        
        StatusInspector statusInspector = new StatusInspector();
        statusInspector.setObject(dispatcher);
        
        MapView map = new MapView();
        map.setObject(dispatcher);

        TextDisplayInspector tti = new TextDisplayInspector();
        tti.setObject(dispatcher);   
    }    
    
    public static void main ( String[] args )
    {
        
        InputStream in = null;
        try {
            
//          File f = new File("data/nmea-0014.txt");
//          //System.out.println(f.getAbsolutePath());
//          in = new FileInputStream("data/nmea/nmea-0008.txt");
            
            //in = connectToSerial("cu.HI-406BT-GPSOUTPUT-1");
            in = connectToSerial("cu.PL2303-000012FA");
            
            if (in == null) 
            {
                System.out.println("unable to connect to serial port");
                System.exit(1);
            }
            
            //NMEAInputStream nmeaInputStream = new NMEAInputStream ( in ); 
            GeoMessageDispatcher dispatcher = null;
            dispatcher = new NMEAMessageDispatcher( in, 500 );
            
//            dispatcher = new GpsdMessageDispatcher( );
                        
            MessageListener listener = new MessageListener();
            dispatcher.addGeoMessageListener(listener);
                      
            initInspectors(dispatcher);
            
            Thread.sleep(2000);
            
            dispatcher.start();
                        
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    

    
//    private static voitInitInspector ( Inspector inspector, GeoDataStream stream )
//    {
////        inspector.setObject ( stream );
//        
//    }
}
