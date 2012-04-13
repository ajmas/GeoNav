package osj.geonav.inspector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JFrame;

import osj.geonav.iface.Heading;
import osj.geonav.nmea.NMEAMessage;

public class BitmapCompassView extends AbstractNMEAInspector {

    /** */
    private static final long serialVersionUID = 1L;
    private double headingTrue = Double.NaN;
    private double headingMagnetic = Double.NaN;
    
    Image compassImage;
    Image compassArrowImage;
    Image compassArrow2Image;
    
    //private long lastUpdate = 0;
    
    //private NMEAMessage mesg;
    
    public BitmapCompassView() {
        super(new String[] { "*" });
        init();
        // TODO Auto-generated constructor stub
    }
    
    private void init ()
    {
        compassImage = Toolkit.getDefaultToolkit().createImage("data/compass2.png");
        compassArrowImage = Toolkit.getDefaultToolkit().createImage("data/compass-arrow.png");
        compassArrow2Image = Toolkit.getDefaultToolkit().createImage("data/compass-arrow2.png");
        
        JFrame f = new JFrame();
        f.setBounds(500,300,200,200);
        f.add(this);
        f.setVisible(true);
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
        boolean rotateCompass = true;
        
        clear( g );
        Graphics2D g2d = (Graphics2D) g;
        
        int width = compassImage.getWidth(null);
        int height = compassImage.getHeight(null);
        
        if ( !rotateCompass )
        {
            g2d.drawImage(compassImage,0,0,width,height,null);
        }
        g2d.translate( width/2, height/2 );       
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);        
        
        if ( !rotateCompass )
        {
            // Rotate the coordinate system around current
            // origin, which is at the center of the circle.
            g2d.rotate( Math.toRadians(180)) ;
            
            if ( ! Double.isNaN(headingMagnetic) )
            {
                g2d.rotate( Math.toRadians(this.headingMagnetic) );
                
                g2d.drawImage(compassArrow2Image,0-(compassArrow2Image.getWidth(null)/2),0,compassArrow2Image.getWidth(null),compassArrow2Image.getHeight(null),null);
                
                g2d.rotate( Math.toRadians(-this.headingMagnetic) );
            }  
            
            if ( ! Double.isNaN(headingTrue) )
            {
                g2d.rotate( Math.toRadians(this.headingTrue) );
                
                g2d.drawImage(compassArrowImage,0-(compassArrowImage.getWidth(null)/2),0,compassArrowImage.getWidth(null),compassArrowImage.getHeight(null),null);
                
                g2d.rotate( Math.toRadians(-this.headingTrue) );
            }
        }
        else
        {
            g2d.rotate( Math.toRadians(this.headingTrue) );
            g2d.drawImage(compassImage,-(width/2),-(height/2),width,height,null);
            g2d.rotate( Math.toRadians(this.headingTrue) );
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
