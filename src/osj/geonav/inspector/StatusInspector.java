package osj.geonav.inspector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import osj.geonav.iface.Location;
import osj.geonav.iface.RelativeSatellitePosition;
import osj.geonav.iface.SatellitesInView;
import osj.geonav.nmea.NMEAMessage;

public class StatusInspector extends  AbstractNMEAInspector {

    JPanel satPositionPanel;
    JPanel signalStrengthPanel;
    JLabel longitudeLabel;
    JLabel latitudeLabel;
    JLabel dateLabel;
    JLabel timeLabel;
    JLabel altitudeLabel;
    public StatusInspector () {
        super(new String[] { "*" });
        init();
    }
    
    private void init () {
        setLayout(null);       
        
        satPositionPanel = new SatellitePositionPanel();
        satPositionPanel.setBounds(0,0,180,180);
        add( satPositionPanel );
        
        signalStrengthPanel = new SignalStrengthPanel();
        signalStrengthPanel.setBounds(0,190,400,80);
        add( signalStrengthPanel );
        
        JLabel label = new JLabel();
        
        Font font = label.getFont();
        Font fontT = new Font (font.getFontName(),Font.BOLD,font.getSize());
        
        int baseX = 190;
        
        label = new JLabel("Date");
        label.setFont(fontT);
        label.setBounds(baseX, 0, 180, 20);
        add( label );
        
        dateLabel = new JLabel("");
        dateLabel.setBounds(baseX+20, 20, 180, 20);
        add( dateLabel );
        
        label = new JLabel("Time");
        label.setFont(fontT);
        label.setBounds(baseX, 40, 180, 20);
        add( label );
        
        timeLabel = new JLabel("23:00:00");
        timeLabel.setBounds(baseX+20, 60, 180, 20);
        add( timeLabel );   
        
        // --------------------------------
        
        label = new JLabel("Longitude");
        label.setFont(fontT);
        label.setBounds(baseX, 80, 180, 20);
        add( label );
        
        longitudeLabel = new JLabel("");
        longitudeLabel.setBounds(baseX+20, 100, 180, 20);
        add( longitudeLabel );
        
        label = new JLabel("Latitude");
        label.setFont(fontT);
        label.setBounds(baseX, 120, 180, 20);
        add( label );
        
        latitudeLabel = new JLabel("");
        latitudeLabel.setBounds(baseX+20, 140, 180, 20);
        add( latitudeLabel );        
                
//        label = new JLabel("DOP: 0.7");
//        label.setBounds(baseX, 160, 180, 20);
//        add( label );
        
        // --------------------------------        
        
        label = new JLabel("Direction:");
        label.setFont(fontT);
        label.setBounds(10, 290, 70, 30);
        add( label );
        
        label = new JLabel("NE 0");
        label.setBounds(80, 290, 70, 30);
        add( label );
        
        label = new JLabel("Altitude:");
        label.setFont(fontT);
        label.setBounds(10, 310, 70, 30);
        add( label );     
        
        altitudeLabel = new JLabel("");
        altitudeLabel.setBounds(80, 310, 70, 30);
        add( altitudeLabel );
        
        label = new JLabel("Speed:");
        label.setFont(fontT);
        label.setBounds(180, 290, 70, 30);
        add( label );
        
        label = new JLabel("0 km/hr");
        label.setBounds(250, 290, 70, 30);
        add( label );
        
        label = new JLabel("PDop:");
        label.setFont(fontT);
        label.setBounds(180, 310, 70, 30);
        add( label );   
        
        label = new JLabel("1.3");
        label.setBounds(250, 310, 70, 30);
        add( label );
        
    }
    
    
    private static class SatellitePositionPanel extends JPanel {
        
        SatellitePositionPanel () {
//            setBackground(Color.BLACK);
//            s(Color.BLACK);
        }
        
        public void paint ( Graphics g ) {
//            g.setColor(getBackground());
//            g.fillRect(0, 0, 180, 180);
            g.setColor(getForeground());
            g.drawArc(50, 50, 80, 80, 0, 360);
            g.drawArc(20, 20, 140, 140, 0, 360);
            
            g.drawLine(0, 90, 180, 90);
            g.drawLine(90, 0, 90, 180);
        }
    }
    
    private static class SignalStrengthPanel extends JPanel {
        
        SignalStrengthPanel () {
//            setBackground(Color.RED);
        }
        
        public void paint ( Graphics g ) {
            int width=27;
            int inset = 2;
            for ( int i=0; i<12; i++ ) {
                Rectangle2D bounds = null;
                double n = Math.random();
                int height = (int)(60 * n);
                int signal = (int)(47 * n);
                
                g.fillRect(width*i+inset, 60-height, width-(inset*2), height);
                
                Font f = getFont();
                FontMetrics fm = getFontMetrics(f);
                
                bounds = fm.getStringBounds(""+signal, g);
                if ( bounds.getHeight() < height ) {
                    g.setColor(Color.WHITE);
                    int x2 = (int)( ( width / 2.0 ) - ( bounds.getWidth() / 2.0 ) );
                    g.drawString(""+signal, (width*i)+x2, (int)(60-5) );
                }
                g.setColor(Color.BLACK);
                
                String satellite = "00";
                bounds = fm.getStringBounds(satellite, g);
                               
                int x2 = (int)( ( width / 2.0 ) - ( bounds.getWidth() / 2.0 ) );
                g.drawString(satellite, (width*i)+x2, 80);
            }
        }
    }

    @Override
    public void newMessage(NMEAMessage message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        if ( message instanceof Location ) {
            Location loc = (Location) message;
            longitudeLabel.setText( "" + loc.getLongitude() );
            latitudeLabel.setText( "" + loc.getLatitude() );
            long time = loc.getTime();
            Date date = new Date(time);
            dateLabel.setText ( dateFormat.format(date) );
            timeLabel.setText ( timeFormat.format(date) );
        }
        else if ( message instanceof SatellitesInView ) {
//            SatellitesInView satsInView = (SatellitesInView) message;
//            List<RelativeSatellitePosition> satPositions = satsInView.getSatellites();
//            for ( RelativeSatellitePosition satPosition : satPositions ) {
//                satPosition.`
//            }
        }
        
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        
    }    
}
