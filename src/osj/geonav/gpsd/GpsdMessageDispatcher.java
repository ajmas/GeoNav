package osj.geonav.gpsd;

import java.io.IOException;

import osj.geonav.iface.GeoMessageDispatcher;
import osj.geonav.iface.GeoMessageListener;

public class GpsdMessageDispatcher implements GeoMessageDispatcher {

    GpsdDataSource dataSource;
    
    public GpsdMessageDispatcher() {
        this.dataSource = new GpsdDataSource();
    }

    public GpsdMessageDispatcher( String host, int port ) {
        this.dataSource = new GpsdDataSource(host,port,-1);
    }
    
    public GpsdMessageDispatcher( String host, int port, int interval ) {
        this.dataSource = new GpsdDataSource(host,port,interval);
    }
    
    public void start()
    {
        //super.start();
        try {
            this.dataSource.connect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
   
    
    public void stop()
    {
        try {
            this.dataSource.disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }

    public void addGeoMessageListener(GeoMessageListener listener) {
        this.dataSource.addGeoDataListener(listener);
    }

    public void removeGeoMessageListener(GeoMessageListener listener) {
        this.dataSource.removeGeoDataListener(listener);
    }

    
}
