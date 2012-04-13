package osj.geonav.gui;

import java.awt.Dimension;

import javax.swing.JTextField;

/**
 * Add documentation
 * 
 * @author Andre-John Mas
 *
 */
public class TextField  extends JTextField implements Field
{
    /** */
    private static final long serialVersionUID = -8647843273648354856L;
    
    String name;
    String value;
    String languageKey;
    
    public TextField( String name, String value, String languageKey )
    {
        super();
        this.name = name;
        this.value = value;
        this.languageKey = languageKey;
        this.setMaximumSize(new Dimension(400,27));
        this.setMinimumSize(new Dimension(300,27));
    }
    
    /**
     * @return Returns the languageKey.
     */
    public String getLanguageKey()
    {
        return this.languageKey;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return Returns the value.
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * @param languageKey The languageKey to set.
     */
    public void setLanguageKey( String languageKey )
    {
        this.languageKey = languageKey;
    }

    /**
     * @param name The name to set.
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * @param value The value to set.
     */
    public void setValue( String value )
    {
        this.value = value;
    }

    



}
