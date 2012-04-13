package osj.geonav.nmea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An 'input stream' for reading the data coming from the GPS.
 * The data is expected to be in NMEA format.
 * 
 * @author ajmas
 *
 */
class NMEAInputStream /*implements GeoNavInputStream*/ {

    private BufferedReader reader;
    
    /**
     * Creates an NMEAInputStream, using an existing InputStream
     * 
     * @param in the active input stream
     */
    public NMEAInputStream ( InputStream in )
    {
        this.reader = new BufferedReader( new InputStreamReader ( in ) );
    }
        
    /**
     * Reads the next response from the GPS device as an NMEAMessage. If
     * an exact implementation of NMEAMessage can not be found, then
     * the generic NMEAGenericMessage implementation is used. If null
     * is returned then the end of the stream is reached.
     * 
     * @return NMEAMessage
     * @throws IOException
     */
    public NMEAMessage readMessage () throws IOException
    {
        String text = null;

        while ( ( text  = readMessageAsString() ) != null && !text.startsWith("$") )
        {
            text = readMessageAsString();
        }
            
        if ( text == null )
        {
            return null;
        }
        
        return NMEAMessageFactory.getInstance().createMessageFromString(text);
    }
    
    /**
     * Reads the next response from the GPS device as a string
     * 
     * @return String
     */
    public String readMessageAsString() throws IOException
    {
        return reader.readLine();
    }
    
    /**
     * Closes the NMEAInputStream
     */
    public void close () throws IOException
    {
        this.reader.close();
    }
}
