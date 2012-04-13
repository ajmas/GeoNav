package osj.geonav.inspector;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import osj.geonav.nmea.NMEAMessage;

public class RAWDataInspector extends AbstractNMEAInspector {

    /** */
    private static final long serialVersionUID = 1L;
    JTextArea textArea;
    
    public RAWDataInspector() {
        super(new String[] { "*" });
        init();
    }

    void init()
    {
        setLayout( new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        textArea = new JTextArea();
        
        scrollPane.getViewport().setView(textArea);
        this.add(scrollPane);
        
//        JFrame f = new JFrame();
//        f.setBounds(100,400,300,200);
//        f.add(this);
//        f.setVisible(true);
    }
    
    public void newMessage(NMEAMessage message)
    {
        textArea.append(message.toNMEAString(false)+"\n");
    }

    @Override
    public  void reset() {
        // TODO Auto-generated method stub
        
    }

}
