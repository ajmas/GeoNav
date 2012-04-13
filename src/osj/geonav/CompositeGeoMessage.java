package osj.geonav;

import java.util.ArrayList;
import java.util.List;

import osj.geonav.iface.GeoMessage;

public class CompositeGeoMessage implements GeoMessage {

    private List<GeoMessage> messageList = new ArrayList<GeoMessage>();
    
    public List getMessages()
    {
        return this.messageList;
    }
    
    public void setMessage ( List<GeoMessage> messages )
    {
        this.messageList = messages;
    }
}
