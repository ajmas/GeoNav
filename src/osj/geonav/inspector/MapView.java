package osj.geonav.inspector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import osj.geonav.iface.Location;
import osj.geonav.nmea.NMEAMessage;

/**
 * Add documentation
 * 
 * @author Andre-John Mas
 *
 */
public class MapView extends AbstractNMEAInspector
{
    /** */
    private static final long serialVersionUID = 1L;
//    private static final double DEFAULT_LEFT   = -180.0;
//    private static final double DEFAULT_RIGHT  = 180.0;
//    
//    private static final double DEFAULT_TOP    = 90.0;
//    private static final double DEFAULT_BOTTOM = -90.0;
    
//    private Point2D  centerCoordinate = null;
    //private GeneralPath  route;
    private List<Point2D> route;
    private boolean       routeEmpty;
    
    double left, right, top, bottom;
    
    //Rectangle2D viewRect;
    
    /** Default constructor */
    public MapView(  )
    {
        super( new String[] { "*" } );
        // TODO Auto-generated constructor stub
        init();
    }

    private void init()
    {        
//        this.centerCoordinate = new Point2D.Double(0.0,0.0);
        
//        setLayout( new BorderLayout());
//        JScrollPane scrollPane = new JScrollPane();
//        textArea = new JTextArea();
//        
//        scrollPane.getViewport().setView(textArea);
//        this.add(scrollPane);
        
        JFrame f = new JFrame();
        f.setBounds(700,100,360,180+30);
        f.getContentPane().add(this);
        f.setVisible(true);
    }
    
    
//    private double calculateDistance( double angleA, double angleB )
//    {
//        double a = 360 - angleA;
//        double b = 360 - angleB;
//        
//        return Math.max(a,b) - Math.min(a,b);
//    }
    
    @Override
    public void newMessage( NMEAMessage message )
    {
        if (message instanceof Location )
        {
            Location loc = (Location) message;
            if ( this.routeEmpty )
            {
                //this.route.moveTo((float)loc.getLongitude(),(float)loc.getLatitude());
                this.routeEmpty = false;
            }
            else
            {
                //this.route.lineTo((float)loc.getLongitude(),(float)loc.getLatitude());
            }
            this.route.add(new Point2D.Double(loc.getLongitude(),loc.getLatitude()));
            this.repaint(0);
            
//            System.out.println("Long: " + loc.getLongitude() +", Lat: " + loc.getLatitude());
        }
        // TODO Auto-generated method stub

    }

    @Override
    public  void reset()
    {
        // TODO Auto-generated method stub
        //this.viewRect = new Rectangle2D.Double(-180.0,-90,360.0,180.0);
//        this.left   = DEFAULT_LEFT;
//        this.right  = DEFAULT_RIGHT;
//        this.top    = DEFAULT_TOP;
//        this.bottom = DEFAULT_BOTTOM;
        
        this.left   = 24;
        this.right  = 25;
        this.top    = 40;
        this.bottom = 41;
        
        //this.route = new GeneralPath();
        this.route = new ArrayList<Point2D>();
        this.routeEmpty = true;
    }
    
    
    protected void clear( Graphics g )
    {
        super.paintComponent( g );
    }

    
    public void paintComponent( Graphics g )
    {
        clear( g );
        Graphics2D g2d = (Graphics2D) g;
        
        double width = 360;
        double height = 180;
        
        double xScale =  100;//; calculateDistance(left,right) / 360;
        double yScale =  100;//;calculateDistance(top,bottom) / 360;
        
        
        g2d.translate( width/2, height/2 );
        
        g2d.setColor(Color.RED);
        g2d.draw(new Rectangle2D.Double(-width/2,-height/2, width, height));
        
        g2d.setColor(Color.BLACK);
        //g2d.draw(this.route);

        //g2d.translate( -width/2, -height/2 );
        
        //System.out.println("size="+this.route.size());
        for ( int i=0; i<this.route.size()-1; i++ )
        {
            Point2D p1 = this.route.get(i);
            Point2D p2 = this.route.get(i+1);

            double x1 = (p1.getX() - left) * xScale;
            double x2 = (p2.getX() - left) * xScale;
            
            double y1 = (p1.getY() - top) * yScale;
            double y2 = (p2.getY() - top) * yScale;
            
//            System.out.println(i + " -- " + p1 + " - " + p2);
//            System.out.println(i + " -- " + x1 + "," + y1 + " - " +  x2 + "," + y2);
            
            g2d.drawLine((int)x1,(int)y1,(int)y1,(int)x2);
        }
    }
    
//    private double getLeftLongitude()
//    {
//        return this.viewRect.getX();
//    }
//
//    private double getRightLongitude()
//    {
//        return this.viewRect.getWidth();
//    }
//    
    /**
     * TODO implementation details
     *
     * @param args
     */
    public static void main( String[] args )
    {
        new MapView();

    }

}
