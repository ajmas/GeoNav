package osj.geonav.inspector;

import java.util.HashSet;
import java.util.Set;

import osj.geonav.iface.GeoMessage;
import osj.geonav.iface.GeoMessageDispatcher;
import osj.geonav.iface.GeoMessageListener;
import osj.geonav.nmea.NMEAMessage;

public abstract class AbstractNMEAInspector extends javax.swing.JPanel implements GeoMessageListener, Inspector {

   /** */
    private static final long serialVersionUID = -5018423553595713247L;
    
    GeoMessageDispatcher dispatcher;
    Set<String> mesgTypeSet;
    String name;
    
    public AbstractNMEAInspector( String[] mesgTypes) {
        super();
        this.mesgTypeSet = new HashSet<String>();
        for ( int i=0; i< mesgTypes.length; i++ )
        {
            this.mesgTypeSet.add(mesgTypes[i]);
        }
    }

    public void setObject ( GeoMessageDispatcher dispatcher )
    {
        if ( this.dispatcher != null )
        {
            this.dispatcher.removeGeoMessageListener(this);
            this.dispatcher = null;
        }
        
        reset();
        
        if ( dispatcher != null )
        {
            this.dispatcher = dispatcher;
            this.dispatcher.addGeoMessageListener(this);//,this.mesgTypeSet);
        }
    }

    public void setObject ( Object obj )
    {
        if ( ! ( obj instanceof GeoMessageDispatcher ) )
        {
            throw new RuntimeException("unsupported object type");
        }
        setObject((GeoMessageDispatcher)obj);
    }
    
    public void newMessage( GeoMessage message )
    {
        if ( message instanceof NMEAMessage )
        {
            newMessage((NMEAMessage)message);
        }
    }
    
    public abstract void newMessage(NMEAMessage message);
    
    public abstract void reset();
}
