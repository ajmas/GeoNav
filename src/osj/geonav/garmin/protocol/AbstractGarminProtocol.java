package osj.geonav.garmin.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import osj.geonav.garmin.Packet;
import osj.geonav.garmin.PacketHandler;
import osj.geonav.garmin.mesghandler.GarminMessageHandler;
import osj.geonav.garmin.message.Acknowledgment;
import osj.geonav.garmin.message.GarminMessage;
import osj.geonav.garmin.message.GenericGarminMessage;


public abstract class AbstractGarminProtocol implements GarminProtocol
{
    static final int ACK          = 0x06;
    static final int NEGATIVE_ACK = 0x15;
    
    PacketHandler handler;
    Packet        dataPacket = new Packet();
    InputStream   in;
    OutputStream  out;
    
    private AckMessageHandler   ackMesgHandler;
    
    AbstractGarminProtocol ()
    {
        this.ackMesgHandler = new AckMessageHandler();
    }    
    
    public abstract Object receive() throws IOException;

    public abstract void send( Object data ) throws IOException;

    abstract Map<Integer,GarminMessageHandler> getMesgHandlerMap();
    
    public void setInputStream( InputStream in )
    {
       this.in = in;
    }

    public void setOutputStream( OutputStream out )
    {
        this.out = out;
    }

    public void setPacketHandler( PacketHandler handler )
    {
        this.handler = handler;
    }

    /**
     * TODO implementation details
     *
     * @return
     * @throws IOException
     */
    protected GarminMessage read( ) throws IOException
    {
        if ( this.handler == null )
        {
            throw new IOException ("No packet handler is defined");
        }
        
        this.handler.read( this.in, this.dataPacket );
        System.out.println("type ... " + this.dataPacket.getType() );
        
        if ( this.dataPacket.getType() == -1 )
        {
            throw new IOException("Data read error (should never get here)");
        }
        else if ( this.dataPacket.getType() == NEGATIVE_ACK )
        {
            throw new IOException("Received negative acknowledgment from device");
        }
        else if (this.dataPacket.getType() == ACK )
        {
            return this.ackMesgHandler.parse(  this.dataPacket.getData() );
        }
        
        GarminMessageHandler messageHandler = getMesgHandlerMap().get( this.dataPacket.getType() );
        if ( messageHandler != null )
        {
            return messageHandler.parse ( this.dataPacket.getData() );
        }
        
        return new GenericGarminMessage( this.dataPacket.getType(),this.dataPacket.getData(),this.dataPacket.getDataLength() );        
    }
    
    /**
     * TODO implementation details
     *
     * @param type
     * @throws IOException
     */
    protected void writeRequest ( int type ) throws IOException
    {
        if ( this.handler == null )
        {
            throw new IOException ("No packet handler is defined");
        }
        this.dataPacket.setType( 10 );
        this.dataPacket.setDataLength( 2 );
        byte[] data = this.dataPacket.getData();
        data[0] = (byte) type;
        data[1] = 0;
        
        this.handler.write( this.out, this.dataPacket );
    }
        
    /**
     * TODO implementation details
     *
     * @param obj
     * @throws IOException
     */
    protected void write( Object obj ) throws IOException
    {
        if ( this.handler == null )
        {
            throw new IOException ("No packet handler is defined");
        }
        GarminMessageHandler message = getMesgHandlerMap().get( this.dataPacket.getType() );
        message.build( this.dataPacket, obj );
        handler.write( this.out, this.dataPacket );
    }
    
    /**
     * 
     * TODO implementation details
     *
     * @param type
     * @throws IOException
     */
    protected void writeAck ( int type ) throws IOException
    {
        if ( this.handler == null )
        {
            throw new IOException ("No packet handler is defined");
        }
        this.dataPacket.setType( 6 );
        this.dataPacket.setDataLength( 2 );
        byte[] data = this.dataPacket.getData();
        data[0] = (byte) type;
        data[1] = 0;
        
        this.handler.write( this.out, this.dataPacket );
    }  
    
    /**
     * 
     * TODO implementation details
     *
     * @param mesg
     * @throws IOException
     */
    protected void writeAck ( GarminMessage mesg ) throws IOException
    {
        writeAck(mesg.getType());
    }  
    
    
    /**
     * TODO Description and implementation details.
     */
    static class AckMessageHandler implements GarminMessageHandler
    {
        
        public void build( Packet packet, Object obj )
        {            
            if ( obj instanceof Packet )
            {
                // INFO: take into account that obj might be the same as packet
                int type = ((Packet)obj).getType();
                // INFO: provide information for acknowledgement
                packet.setType( ACK );
                packet.setDataLength( 2 );
                byte[] data = packet.getData();
                data[0] = (byte) type;
                data[1] = 0x0;
            }
            else
            {
                // TODO what to do?
            }
        }

        
        public GarminMessage parse( byte[] data )
        {
            return new Acknowledgment( data[0] );
        }        
    }
}
