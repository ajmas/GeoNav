package osj.geonav.iface;

import java.io.*;

/**
 * This is a source of Navigation data
 */
public interface DataSource
{

    /**
     * 
     * TODO implementation details
     *
     * @return
     */
    public InputStream getInputStream() throws IOException;
    
    /**
     * 
     * TODO implementation details
     *
     * @return
     */
    public OutputStream getOutputStream() throws IOException;

    public boolean isConnected();
    
    public void connect() throws IOException;
    
    public void disconnect() throws IOException;
}
