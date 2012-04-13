package osj.geonav.datasource;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.*;

import osj.geonav.iface.DataSource;
import osj.geonav.iface.GeoDataStream;

/**
 * Handles data coming from a serial port. Currently only
 * supports reading NMEA.
 * 
 * @deprecated Will create a new class based on GeoMessageDispatcher,
 * specialising in seriall communication
 * 
 */
public class SerialDataSource implements DataSource
{

    public static final String PROTOCOL = "protocol";
    public static final String PORT = "port";
    public static final String BAUD = "baud";
    public static final String STOP_BITS = "stop.bits";
    public static final String DATA_BITS = "data.bits";
    public static final String PARITY = "parity";
    
    private InputStream in;
    private OutputStream out;
    private boolean connected;
    private SerialPort serialPort;
    
    private String port;
    private int baud;
    private int stopBits;
    private int dataBits;
    private int parity;
    private int timeout = 2000;
    
    public SerialDataSource()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public InputStream getInputStream()
    {
        return this.in;
    }

    public OutputStream getOutputStream()
    {
        return this.out;
    }    
    
    public boolean isConnected()
    {
        return this.connected;
    }

    public void connect() throws IOException
    {        
        try
        {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(this.port);
            CommPort commPort = portIdentifier.open(this.getClass().getName(),this.timeout);
            if ( commPort instanceof SerialPort )
            {
                this.serialPort = (SerialPort) commPort;
                this.serialPort.setSerialPortParams(this.baud,this.dataBits,this.stopBits,this.parity);
                this.in = this.serialPort.getInputStream();
                this.out = this.serialPort.getOutputStream();
            }
            else
            {
                throw new IOException(commPort.getName() + " is not a serial port");
            }
        }
        catch ( NoSuchPortException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            disconnect();           
            return;            
        }
        catch ( PortInUseException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            disconnect();           
            return;            
        }
        catch ( UnsupportedCommOperationException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            disconnect();           
            return;            
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            disconnect();           
            return;
        }

        
        this.connected = true;        
    }

    public void disconnect() throws IOException
    {
        if ( this.serialPort != null )
        {
            this.serialPort.close();
        }
        this.connected = false;        
    }
    
    // TODO the return type needs to be less protocol centric
    public GeoDataStream getDataInputStream()
    {
        return null;
    }
}
