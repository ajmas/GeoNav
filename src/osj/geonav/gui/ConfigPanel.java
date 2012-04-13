package osj.geonav.gui;

import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;

import osj.geonav.inspector.RAWDataInspector;

/**
 * TODO Description and implementation details.
 */
public class ConfigPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private RAWDataInspector RAWDataInspector = null;
    private JLabel jLabel = null;
    private JComboBox jComboBox = null;
    private JLabel jLabel1 = null;
    private JComboBox jComboBox1 = null;
    private JButton jButton = null;

    /**
     * This is the default constructor
     */
    public ConfigPanel()
    {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
        gridBagConstraints5.gridx = 2;
        gridBagConstraints5.anchor = GridBagConstraints.EAST;
        gridBagConstraints5.insets = new Insets(3, 0, 3, 10);
        gridBagConstraints5.fill = GridBagConstraints.NONE;
        gridBagConstraints5.gridy = 2;
        GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
        gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints4.gridy = 1;
        gridBagConstraints4.weightx = 1.0;
        gridBagConstraints4.insets = new Insets(3, 10, 3, 10);
        gridBagConstraints4.ipadx = 0;
        gridBagConstraints4.ipady = 0;
        gridBagConstraints4.gridheight = 1;
        gridBagConstraints4.gridx = 2;
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.insets = new Insets(0, 10, 0, 0);
        gridBagConstraints.gridy = 1;
        jLabel1 = new JLabel();
        jLabel1.setText("Baud");
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.fill = GridBagConstraints.BOTH;
        gridBagConstraints3.gridy = 0;
        gridBagConstraints3.weightx = 1.0;
        gridBagConstraints3.insets = new Insets(3, 10, 3, 10);
        gridBagConstraints3.gridx = 2;
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.gridx = 1;
        gridBagConstraints2.insets = new Insets(0, 10, 0, 0);
        gridBagConstraints2.gridy = 0;
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.fill = GridBagConstraints.BOTH;
        gridBagConstraints1.ipadx = 0;
        gridBagConstraints1.insets = new Insets(10, 10, 10, 10);
        gridBagConstraints1.anchor = GridBagConstraints.CENTER;
        gridBagConstraints1.gridwidth = 2;
        gridBagConstraints1.weighty = 0.5;
        gridBagConstraints1.gridy = 3;
        this.setSize(530, 347);
        this.setLayout( new GridBagLayout() );
        this.add(getRAWDataInspector(), gridBagConstraints1);
        this.add(getJLabel(), gridBagConstraints2);
        this.add(getJComboBox(), gridBagConstraints3);
        this.add(jLabel1, gridBagConstraints);
        this.add(getJComboBox1(), gridBagConstraints4);
        this.add(getJButton(), gridBagConstraints5);
    }

    /**
     * This method initializes RAWDataInspector	
     * 	
     * @return ajmas74.experimental.nmea.inspector.RAWDataInspector	
     */
    private RAWDataInspector getRAWDataInspector()
    {
        if ( RAWDataInspector == null )
        {
            RAWDataInspector = new RAWDataInspector();
        }
        return RAWDataInspector;
    }

    /**
     * This method initializes jLabel	
     * 	
     * @return javax.swing.JLabel	
     */
    private JLabel getJLabel()
    {
        if ( jLabel == null )
        {
            jLabel = new JLabel();
            jLabel.setText("Port");
        }
        return jLabel;
    }

    /**
     * This method initializes jComboBox	
     * 	
     * @return javax.swing.JComboBox	
     */
    private JComboBox getJComboBox()
    {
        if ( jComboBox == null )
        {
            jComboBox = new JComboBox();
        }
        return jComboBox;
    }

    /**
     * This method initializes jComboBox1	
     * 	
     * @return javax.swing.JComboBox	
     */
    private JComboBox getJComboBox1()
    {
        if ( jComboBox1 == null )
        {
            jComboBox1 = new JComboBox();
        }
        return jComboBox1;
    }

    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getJButton()
    {
        if ( jButton == null )
        {
            jButton = new JButton("OK");
        }
        return jButton;
    }

    public static void main ( String[] args )
    {
        JFrame frame = new JFrame();
        frame.setBounds(50,50,300,250);
        frame.add( new ConfigPanel());
        frame.setVisible(true);
    }
    
}  //  @jve:decl-index=0:visual-constraint="54,41"
