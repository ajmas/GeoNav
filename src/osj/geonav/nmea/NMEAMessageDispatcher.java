package osj.geonav.nmea;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import osj.geonav.iface.GeoMessage;
import osj.geonav.iface.GeoMessageDispatcher;
import osj.geonav.iface.GeoMessageListener;

/**
 * This class is designed to handle the reading of messages
 * from a GeoNavInputStream and dispatch the messages to the
 * listeners registered with the class. To start processing
 * call start() and to stop processing call stop().
 * 
 * @author Andre-John Mas
 *
 */
public class NMEAMessageDispatcher implements GeoMessageDispatcher, Runnable {

    private List<GeoMessageListener> listeners = new ArrayList<GeoMessageListener>();
    private Thread thread;
    private boolean run = false;
    private NMEAInputStream in;
    private int interval = 0;
    
    public NMEAMessageDispatcher ()
    {
        
    }
    
    /**
     * Constructor
     * 
     * @param inputStream the input stream
      */    
    public NMEAMessageDispatcher ( InputStream inputStream )
    {
        this(inputStream,0);
    }
    
    /**
     * Constructor
     * 
     * @param inputStream the input stream
     * @param interval interval in milliseconds to wait between reads
     */
    public NMEAMessageDispatcher ( InputStream inputStream, int interval )
    {
        this.in = new NMEAInputStream(inputStream);
        this.interval = interval;
    }    
    
    /**
     * Adds the listener to the dispatcher
     * 
     * @param listener the listener to add
     */    
    public void addGeoMessageListener ( GeoMessageListener listener )
    {
        this.listeners.add(listener);
    }
    
    /**
     * Removes the listener from the dispatcher
     * 
     * @param listener the listener to remove
     */
    public void removeGeoMessageListener ( GeoMessageListener listener )
    {
        this.listeners.remove(listener);
    }    
    
    /**
     * Sets the inputStream
     * 
     * @param in
     */
    public void setInputStream ( InputStream in ) {
        this.in = new NMEAInputStream(in);
    }
    
    /**
     * Sets the interval between reads 
     * 
     * @param interval
     */
    public void setInterval ( int interval ) {
        this.interval = interval;
    }
    
    /**
     * Starts the dispatcher.
     */
    public void start()
    {
        if ( this.thread == null )
        {
            this.run = true;
            this.thread = new Thread(this);
            this.thread.start();
        }        
    }
    
    
    public void run()
    {
        if ( this.in == null ) {
            return;
        }
        
        try {
            
            while ( this.run )
            {
                
                GeoMessage mesg = readNextMessage();
                for (GeoMessageListener listener : listeners )
                {
                    listener.newMessage(mesg);
                }         
                if ( interval > 0 )
                {
                    try {
                        Thread.sleep(this.interval);
                    } catch (InterruptedException e) {
                        //
                    }
                }
    
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ( in != null ) {
                try {
                    in.close();
                } catch (IOException e) {
                }                
            }
        }
        
        this.run = false;
    }
    
    /**
     * Reads the next message from the GeoNavInputStream
     * @return
     * @throws IOException
     */
    GeoMessage readNextMessage() throws IOException
    {
        return in.readMessage();
    }
    
    /**
     * Stops the dispatcher.
     */
    public void stop()
    {
        if ( this.thread != null )
        {
            this.run = false;
            this.thread = null;
        }
    }
    
    public boolean isStarted() {
        return this.run;
    }
}
