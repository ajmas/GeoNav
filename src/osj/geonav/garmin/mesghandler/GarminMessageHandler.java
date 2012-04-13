package osj.geonav.garmin.mesghandler;

import osj.geonav.garmin.Packet;
import osj.geonav.garmin.message.GarminMessage;

public interface GarminMessageHandler
{
    public GarminMessage parse( byte[] data );

    public void build( Packet packt, Object obj );
}
