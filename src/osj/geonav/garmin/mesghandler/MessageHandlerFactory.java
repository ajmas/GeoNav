/*
 * $Author:  Mas $
 * $Date: Aug 25, 2006 $
 * $Revision: 1.0 $
 */

package osj.geonav.garmin.mesghandler;

import osj.geonav.garmin.Packet;
import osj.geonav.garmin.message.GarminMessage;

public class MessageHandlerFactory
{
    private static MessageHandlerFactory instance;
    
    public static MessageHandlerFactory getInstance()
    {
        if ( instance == null )
        {
            instance = new MessageHandlerFactory();
        }
        return instance;
    }
    
    public GarminMessageHandler getMessageHandler( int deviceType, int protocol )
    {
        return null;
    }
    
    private GarminMessage parsePacket ( int deviceId, int protocol, Packet packet )
    {
        return null;
    }
}
