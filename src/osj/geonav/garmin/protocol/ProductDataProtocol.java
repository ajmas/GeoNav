package osj.geonav.garmin.protocol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import osj.geonav.garmin.Packet;
import osj.geonav.garmin.mesghandler.GarminMessageHandler;
import osj.geonav.garmin.message.GarminMessage;
import osj.geonav.garmin.message.GarminProductInfo;

import osj.utils.LittleEndianBinaryUtils;

/**
 * 
 * TODO Description and implementation details.
 */
public class ProductDataProtocol extends AbstractGarminProtocol
{

    /**
     * The Product Data Protocol request message is different
     * to the regular request messages, hence the over-riding
     * of the writeRequest() method.
     * 
     * @see osj.geonav.garmin.protocol.AbstractGarminProtocol#writeRequest(int)
     */
    @Override
    protected void writeRequest( int type ) throws IOException
    {
        if ( this.handler == null )
        {
            throw new IOException( "No packet handler is defined" );
        }
        this.dataPacket.setType( type );
        this.dataPacket.setDataLength( 0 );

        this.handler.write( this.out, this.dataPacket );
    }

    @Override
    public Object receive() throws IOException
    {
        // INFO: write request message
        writeRequest( 254 );
        // INFO: read ack (ignore)
        read();
        // INFO: return product info
        return read();
    }

    @Override
    public void send( Object data ) throws IOException
    {
        // TODO
    }

    @Override
    Map<Integer, GarminMessageHandler> getMesgHandlerMap()
    {
        HashMap<Integer, GarminMessageHandler> mesgHandlerMap = new HashMap<Integer, GarminMessageHandler>();
        // mesgHandlerMap.put(254, new ProductMessage());
        mesgHandlerMap.put( 255, new ProductMessage() );
        return mesgHandlerMap;
    }

    /**
     * 
     * TODO Description and implementation details.
     */
    static class ProductMessage implements GarminMessageHandler
    {

        public void build( Packet packt, Object obj )
        {
            // TODO Auto-generated method stub

        }

        public GarminMessage parse( byte[] data )
        {
            GarminProductInfo productInfo = new GarminProductInfo();

            productInfo.setProductId( LittleEndianBinaryUtils.byte2ToInt( data, 0 ) );
            productInfo.setSoftwareVersion( LittleEndianBinaryUtils.byte2ToInt( data, 2 ) / 100.0f );
            try
            {
                productInfo.setProductDescription( readCString( data, 4, data.length ) );
            }
            catch ( UnsupportedEncodingException e )
            {
                productInfo.setProductDescription( e.getMessage() );
            }

            return productInfo;
        }

    }

    /**
     * TODO implementation details
     * 
     * @param data
     * @param offset
     * @param length
     * @return String
     * @throws UnsupportedEncodingException
     */
    static String readCString( final byte[] data, int offset, int length ) throws UnsupportedEncodingException
    {
        int idx = offset + length;

        for ( int i = offset, j = 0; i < idx; i++, j++ )
        {
            if ( data[i] == 0 )
            {
                length = j;
                break;
            }
        }

        return new String( data, offset, length, "ASCII" );
    }
}
