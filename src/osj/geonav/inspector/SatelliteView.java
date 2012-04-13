package osj.geonav.inspector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import osj.geonav.iface.RelativeSatellitePosition;
import osj.geonav.iface.SatellitesInView;
import osj.geonav.nmea.NMEAMessage;

public class SatelliteView extends AbstractNMEAInspector implements Runnable{

    /** */
    private static final long serialVersionUID = 1L;
    private double adjust = 0;
    private long   t=0;
    List<RelativeSatellitePosition> satelliteList;
    
    //private long lastUpdate = 0;
    
    //private NMEAMessage mesg;
    
    public SatelliteView() {
        super(new String[] { "*" });
        init();
        this.satelliteList = new ArrayList<RelativeSatellitePosition>();
        //(new Thread(this)).start();
        // TODO Auto-generated constructor stub
    }
    
    private void init ()
    {
        JFrame f = new JFrame("Compass: Fixed North");
        f.setBounds(100,100,200,200);
        f.add(this);
        f.setVisible(true);
    }

    protected void clear( Graphics g )
    {
        super.paintComponent( g );
    }

    void paintChart( Graphics2D g2d, double scale )
    {
        double radius = 45.0 * scale;        
        Ellipse2D ellipse = new Ellipse2D.Double(-radius,-radius,radius*2,radius*2);
        g2d.draw(ellipse);
        
        radius = 90.0 * scale;    
        ellipse = new Ellipse2D.Double(-radius,-radius,radius*2,radius*2);
        g2d.draw(ellipse);  
    }
    
    private Color getSNRColor( int signalToNoiseRatio )
    {
        if ( signalToNoiseRatio != -1 )
            return Color.getHSBColor(signalToNoiseRatio/100.0f,1.0f,1.0f);
        else
            return Color.LIGHT_GRAY;
    }
    
    public void paintComponent( Graphics g )
    {
        clear( g );
        Graphics2D g2d = (Graphics2D) g;
        
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
      RenderingHints.VALUE_ANTIALIAS_ON);
                     
        
        g2d.translate( getWidth()/2, getHeight()/2 );
        
        double scale = 1.5;
        paintChart(g2d,scale);
        
        //Elevation (0-90), Azimuth (0-359), Signal to noise ration in dBHZ (0-99)
//        double[][] satelliteList  = new double[][] {
//            {45,0+adjust,50}, {90,90+adjust,0}
//        };
       
        double radius = 5.0 * scale;
        Ellipse2D ellipse = new Ellipse2D.Double(-radius*scale,-radius*scale,radius*scale*2,radius*scale*2);
               
        for ( RelativeSatellitePosition satellitePos : this.satelliteList )
        {
            double elevation = satellitePos.getElevation();
            double azimuth = satellitePos.getAzimuth();
            int satelliteId = satellitePos.getSatelliteId();
            
            g2d.rotate( Math.toRadians(azimuth) );
            g2d.translate( 0, elevation * scale);
            g2d.setColor(getSNRColor((int) satellitePos.getSignalToNoiseRatio()));
            g2d.fill(ellipse);
            g2d.setColor(Color.BLACK);
            g2d.draw(ellipse);
            
            g2d.rotate( -Math.toRadians(azimuth) );
            String text = satelliteId+"";
            Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(text,g2d);            
            g2d.drawString(text,(float)(-bounds.getWidth()/2.0f),(float)(bounds.getHeight()/2.0f));
            g2d.rotate( Math.toRadians(azimuth) );
            
            g2d.translate( 0, -elevation * scale );
            g2d.rotate( -Math.toRadians(azimuth) );
        }  
        
        adjust++;
    }    
    
    public void run ()
    {
        while ( true)
        {
            long newT = System.currentTimeMillis(); 
            if ( newT-this.t > 200 )
            {
                this.t = newT;                
                this.repaint(0);
//                System.out.println("x");
            }
        }
    }
    
    @Override
    public void newMessage(NMEAMessage message) {
        if ( message instanceof SatellitesInView )
        {
            if ( ((SatellitesInView)message).getMessageNumber() <= 1 )
            {
                this.satelliteList.clear();
            }
            this.satelliteList.addAll( ((SatellitesInView)message).getSatellites() );
            this.repaint(0);
//            System.out.println("xxx");
        }
        
    }

    @Override
    public void reset() {
        this.satelliteList.clear();
              
    }
    
    public static void main ( String[] args )
    {
        new SatelliteView();
    }

}
