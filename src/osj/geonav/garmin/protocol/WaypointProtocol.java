package osj.geonav.garmin.protocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import osj.geonav.garmin.mesghandler.GarminMessageHandler;
import osj.geonav.garmin.message.Acknowledgment;
import osj.geonav.garmin.message.GarminMessage;
import osj.geonav.garmin.message.GarminProductInfo;


public class WaypointProtocol extends AbstractGarminProtocol
{
    HashMap<Integer, GarminMessageHandler> mesgHandlerMap;
    
    public WaypointProtocol ()
    {
        this.mesgHandlerMap = new HashMap<Integer, GarminMessageHandler>();
    }
    @Override
    Map<Integer, GarminMessageHandler> getMesgHandlerMap()
    {        
        return this.mesgHandlerMap;
    }

    @Override
    public Object receive() throws IOException
    {
        ProductDataProtocol ppd = new ProductDataProtocol();
        ppd.setPacketHandler( this.handler );
        GarminProductInfo productInfo = (GarminProductInfo) ppd.receive();
        
        writeRequest( 7 );
        
        assertAck(read());
        
//        BeginTransferRequest request = (BeginTransferRequest) read();
        
        GarminMessage request = (GarminMessage) read();
        
        writeAck( request );
        
        while ( true )
        {
            GarminMessage mesg = read();
            System.out.println(mesg);
            writeAck( mesg );
            if ( mesg.getType() == 0x0C )
            {
                break;
            }
        }
        
        return null;
    }

    @Override
    public void send( Object data ) throws IOException
    {
        // TODO Auto-generated method stub
        
    }

    boolean assertAck ( Object message ) throws IOException
    {
        return ( message instanceof Acknowledgment );
    }

}
