package osj.geonav.garmin.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import osj.geonav.garmin.PacketHandler;


public interface GarminProtocol
{
    public void setPacketHandler ( PacketHandler handler );
    
    public void setInputStream( InputStream in );
    
    public void setOutputStream( OutputStream in );
    
    public void send ( Object data ) throws IOException;
    
    public Object receive () throws IOException;
}
