package osj.geonav.gpsd;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import osj.geonav.iface.GeoMessageListener;

/**
 * This is returns data from a GPSD data source. It
 * only knows about the GPSD data source.
 * 
 * 
 */
public class GpsdDataSource //implements DataSource
{
    /** default GPSD port, as assigned by IANA */
    public static final int DEFAULT_GPSD_PORT = 2947;
    /** default RTCM port, as assigned by IANA */    
    public static final int DEFAULT_RTCM_PORT = 2101;
       
    public static final int DEFAULT_INTERVAL = 1000;
    
    private InputStream in;
    private boolean connected;
    
    private String host = "localhost";
    private int    port = DEFAULT_GPSD_PORT;
    @SuppressWarnings("unused")
    private GpsdDataStream geoDataStream;
    private int    interval = DEFAULT_INTERVAL;
    
    List<GeoMessageListener> listeners;
    
    public GpsdDataSource()
    {
        listeners = new ArrayList<GeoMessageListener>();
    }
    
    public GpsdDataSource( String host, int port, int interval )
    {
        this.host = host;
        this.port = port;

        this.interval = interval;
        
        if ( interval <=0 )
        {
            this.interval  = DEFAULT_INTERVAL;
        }

    }
    
    public InputStream getInputStream()
    {
        return this.in;
    }

    /** 
     * NOT IMPLEMENTED
     * 
     * @see osj.geonav.iface.DataSource#getOutputStream()
     */    
    public OutputStream getOutputStream()
    {
        return null;//this.out;
    }    
    
    public boolean isConnected()
    {
        return this.connected;
    }

    public void connect() throws IOException
    {   
        Socket socket = new Socket(this.host, this.port);
        this.geoDataStream = new GpsdDataStream(socket);
    }

    public void disconnect() throws IOException
    {
        if ( this.in != null )
        {
            this.in.close();
        }
        this.connected = false;        
    }  
    
    class GpsdDataStream implements Runnable 
    {
        Socket socket;
        InputStream in;
        OutputStream out;
        BufferedReader reader;
        boolean run;
        
        GpsdDataStream ( Socket socket ) throws IOException
        {
            this.socket = socket;
            
            System.out.println(this.socket.getPort());
            this.in = this.socket.getInputStream();
            this.out = this.socket.getOutputStream();
            this.reader = new BufferedReader(new InputStreamReader(this.in));
            (new Thread(this)).start();
        }
        
        public void run()
        {
            System.out.println("in run");
            String line = null;
            try
            {
                while ( true )//( line = this.reader.readLine()) != null )
                {
                    System.out.println("send");
                    this.out.write( "ap\r\n".getBytes());
                    if ( ( line = this.reader.readLine()) == null )
                    {
                       break;   
                    }
                    System.out.println("response: " + line);
                    GPSDMessage message = GPSDMessage.parseMessage(line);
                    if ( message != null )
                    {
                        for ( GeoMessageListener listener : GpsdDataSource.this.listeners )
                        {
                            listener.newMessage(message); 
                        }
                    }
                    System.out.println("GPSDMessage--->"+message);
                    
                    try 
                    {
                       Thread.sleep( GpsdDataSource.this.interval);   
                    }
                    catch ( InterruptedException ex )
                    {
                        //
                    }
                }
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("leaving");
        }
        
    }
    
    public void addGeoDataListener( GeoMessageListener l )
    {
        this.listeners.add(l);            
    }

    public void removeGeoDataListener( GeoMessageListener l )
    {
        this.listeners.remove(l);    
    }
    
//    public static void main ( String[] args )
//    {
//        GpsdDataSource dataSource = new GpsdDataSource();
//        try
//        {
//            dataSource.connect();
//        }
//        catch ( IOException e )
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
