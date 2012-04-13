package osj.geonav.inspector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;

import osj.geonav.iface.Heading;
import osj.geonav.nmea.NMEAMessage;

public class CompassView extends AbstractNMEAInspector {

    /** */
    private static final long serialVersionUID = 1L;
    private double headingTrue = Double.NaN;
    private double headingMagnetic = Double.NaN;
    //private long lastUpdate = 0;
    
    //private NMEAMessage mesg;
    
    public CompassView() {
        super(new String[] { "*" });
        init();
        // TODO Auto-generated constructor stub
    }
    
    private void init ()
    {
//        JFrame f = new JFrame("Compass: Fixed North");
//        f.setBounds(100,100,200,200);
//        f.add(this);
//        f.setVisible(true);
    }

    protected void clear( Graphics g )
    {
        super.paintComponent( g );
    }

    void paintCompass( Graphics2D g2d, int radius )
    {
       g2d.drawArc(-radius,-radius,radius*2,radius*2,0,360);
    }
    
    void paintNorthPoint( Graphics2D g2d )
    {
        
    }
    
    public void paintComponent( Graphics g )
    {
        clear( g );
        Graphics2D g2d = (Graphics2D) g;
        
        int maxSize = Math.min(getWidth(),getHeight());
        int halfMaxSize = maxSize / 2;
         
        int arrowBox = (int)( maxSize * 0.08);
        if ( arrowBox < 20 )
        {
            arrowBox = 20;
        }
        double adj = halfMaxSize * 0.7;
        if ( adj < 20 )
        {
            adj = 0;
        }
        
        Shape headingPointer = new Polygon(
            new int[] {-(arrowBox/2),0,(arrowBox/2)}, 
            new int[] {(int)adj,(int)(adj+(arrowBox/2)),
           (int)adj}, 3 );               
        
        g2d.translate( getWidth()/2, getHeight()/2 );
        
        if ( adj > 20 )
        {
            
            int labelWidth = (int)( maxSize * 0.07);
            //g2d.rotate( Math.toRadians(180) );
            
            g2d.translate( -labelWidth/2, - ( halfMaxSize * 0.8 ) );

            g2d.drawLine(0,labelWidth,0,0);
            g2d.drawLine(0,0,labelWidth,labelWidth);
            g2d.drawLine(labelWidth,labelWidth,labelWidth,0);
            
            g2d.translate( labelWidth/2, ( halfMaxSize * 0.8 )  );
            
            //g2d.draw(p);
            
            //g2d.drawString("N",0,(int)(adj+(arrowBox/2)));
        }
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        paintCompass(g2d,(int)(halfMaxSize*0.9));
        
        // Rotate the coordinate system around current
        // origin, which is at the center of the circle.
        g2d.rotate( Math.toRadians(180)) ;
        
        if ( ! Double.isNaN(headingMagnetic) )
        {
            g2d.rotate( Math.toRadians(this.headingMagnetic) );
            g2d.setPaint( Color.LIGHT_GRAY );
            
            g2d.fill(headingPointer);
            g2d.rotate( Math.toRadians(-this.headingMagnetic) );
        }  
        
        if ( ! Double.isNaN(headingTrue) )
        {
            g2d.rotate( Math.toRadians(this.headingTrue) );
            g2d.setPaint( Color.RED );
            
            g2d.fill(headingPointer);
            g2d.rotate( Math.toRadians(-this.headingTrue) );
        }
        
        //System.out.println("headingMagnetic: " + headingMagnetic + ", headingTrue: " + headingTrue);
      
    }    
    
    
    @Override
    public void newMessage(NMEAMessage message) {
        if ( message instanceof Heading )
        {
            Heading  heading = (Heading) message;
            this.headingTrue = heading.getTrueHeading();
            this.headingMagnetic = heading.getMagneticHeading();
            this.repaint(0);
            //lastUpdate = System.currentTimeMillis();
        }
        else
        {
            // TODO start dimming arrow, to indicate lack of data
        }
    }

    @Override
    public void reset() {
        this.headingTrue = Double.NaN;
        this.headingMagnetic = Double.NaN;
        this.repaint(0);        
    }

}
