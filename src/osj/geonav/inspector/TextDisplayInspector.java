package osj.geonav.inspector;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import osj.utils.conversion.AngleConverter;
import osj.utils.conversion.UnsupportedConversionException;
import osj.geonav.iface.Location;
import osj.geonav.nmea.NMEAMessage;

public class TextDisplayInspector extends AbstractNMEAInspector {

    /** */
    private static final long serialVersionUID = 1L;
    
    SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss.SSS");
    
    long time;
    double latitude;
    double longitude;
    double height;
    JTextArea textArea;
    
    public TextDisplayInspector() {
        super(new String[] { "*" });
        init();
    }

    void init()
    {
        formater.setTimeZone(TimeZone.getTimeZone("UTC"));
        setLayout( new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        textArea = new JTextArea();
        textArea.setFont(new Font("Courier new",Font.PLAIN,12));
        scrollPane.getViewport().setView(textArea);
        this.add(scrollPane);
        
        JFrame f = new JFrame();
        f.setBounds(100,400,300,200);
        f.add(this);
        f.setVisible(true);
    }
    
    public void newMessage(NMEAMessage message)
    {
        if ( message instanceof Location )
        {
            Location loc = (Location) message;
            time = loc.getTime();
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
            height = loc.getHeight();
            
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("Time ........ : " + formater.format(time) +"\n");
            try
            {
                strBuf.append("Latitude .... : " + AngleConverter.getInstance().convert("degrees","ddd\u00B0mm\"ss",latitude) +"\n");
            }
            catch ( UnsupportedConversionException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try
            {
                strBuf.append("Longitude ... : " + AngleConverter.getInstance().convert("degrees","ddd\u00B0mm\"ss",longitude) +"\n");
            }
            catch ( UnsupportedConversionException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            strBuf.append("Height ...... : " + height +"\n"); 
            
            textArea.setText(strBuf.toString());
        }
//        else
//        {
//            Class[] interfaces = message.getClass().getInterfaces();
//            for ( Class iface : interfaces )
//            {
//                System.out.println(iface.getName() + " ,");
//            }
//        }
        

        
        
 
    }


    
    
    @Override
    public void reset() {
        // TODO Auto-generated method stub
        
    }

}
