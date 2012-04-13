package osj.geonav.gui;

import java.awt.BorderLayout;

import javax.swing.*;

import osj.geonav.inspector.CompassView;

/**
 * Add documentation
 * 
 * @author Andre-John Mas
 *
 */
public class GeoNavBasic extends JPanel
{
    /** */
    private static final long serialVersionUID = -6449428419010433621L;
    
    JFrame      frame;
    JTabbedPane tabbedPane;
    
    public GeoNavBasic()
    {
        super();
        // TODO Auto-generated constructor stub
        init();
    }

    private void init ()
    {
        this.frame = new JFrame("GeoNav Basic");
        this.frame.setLocation(100,100);
        this.frame.setSize(250,350);
        this.frame.setVisible(true);
        this.frame.add(this);
        
        setLayout(new BorderLayout());
        
        this.tabbedPane = new JTabbedPane(SwingConstants.BOTTOM);
        add(this.tabbedPane);

        initConfigView();
        
        initNavigationView();
        
        initSatelliteView();        
    }
    
    private void initConfigView()
    {
//        JPanel configPanel = new JPanel();
        this.tabbedPane.insertTab(getText("Config"),null,new ConfigPanel(),"",0);
        
//        // INFO: see http://java.sun.com/docs/books/tutorial/uiswing/layout/gridbag.html for reference
//        configPanel.setLayout(new GridBagLayout());       
//        GridBagConstraints c = new GridBagConstraints();
//                
//        RAWDataInspector rawView = new RAWDataInspector();
//        //rawView.setSize(200,200);
//        //int x = (int) ((this.frame.getWidth()/2.0)-(rawView.getWidth()/2.0));
//        //rawView.setLocation(x,0);
//        
//        //c.ipady = 40;      //make this component tall
//        c.weightx = 1.0;
//        c.gridwidth = 3;
//        c.gridx = 1;
//        c.gridy = 1;
//
//        configPanel.add(rawView,c);        
    }
    
    private void initNavigationView()
    {
        JPanel compassPanel = new JPanel();
        this.tabbedPane.insertTab(getText("Navigation"),null,compassPanel,"",1);
        
        compassPanel.setLayout(null);
        CompassView compassView = new CompassView();
        compassView.setSize(200,200);
        int x = (int) ((this.frame.getWidth()/2.0)-(compassView.getWidth()/2.0));
        compassView.setLocation(x,0);
        compassPanel.add(compassView);
    }
    
    private void initSatelliteView()
    {
        JPanel satInfoPanel = new JPanel();
        this.tabbedPane.insertTab(getText("Satellites"),null,satInfoPanel,"",2);
    }
    
    private String getText(String id)
    {
        //TODO
        return id;
    }
    /**
     * TODO implementation details
     *
     * @param args
     */
    public static void main( String[] args )
    {
        GeoNavBasic geoNavBasic = new GeoNavBasic();

    }

}
