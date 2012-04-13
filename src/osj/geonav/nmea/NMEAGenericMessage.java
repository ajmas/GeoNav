package osj.geonav.nmea;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/** */
public class NMEAGenericMessage extends Object implements NMEAMessage
{
    private static final String DESCRIPTIONS_FILE="ajmas74.experimental.geonav.nmea.descriptions";
    /**
     * TODO Comment for <code>dataList</code>
     */
    List<String> dataList;
    
    private static ResourceBundle mesgTexts;
    
    /** */
    public NMEAGenericMessage()
    {
        super();
        this.dataList = new ArrayList<String> ();
    }

    public List<String> getData()
    {
        return this.dataList;
    }

    public void setData( List<String> data )
    {
        this.dataList = data;
    }

    public String getTypeCode()
    {
        if ( this.dataList.size() > 0 )
        {
            return this.dataList.get(0);
        }
        return null;
    }

    public String getLongName()
    {
        return "Unhandled message";
    }
    
    public String toNMEAString( boolean includeChecksum )
    {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(this.dataList.get(0));
        for ( int i=1; i< this.dataList.size(); i++ )
        {
            strBuilder.append(',');
            strBuilder.append(this.dataList.get(i));
        }
        
        String str = strBuilder.toString();
         
        if ( includeChecksum )
        {
            str = str + '*' + checksum(str);
        }
        return str;
    }
    
    public static String checksum ( String message )
    {
        if ( message.startsWith("$") )
        {
            message = message.substring(1);
        }
        
        if ( message.lastIndexOf("*") > -1 )
        {
            message = message.substring(0,message.lastIndexOf("*"));
        }
        
        int val = message.charAt(0);
        for ( int i=1; i<message.length(); i++ )
        {            
            val = val ^ message.charAt(i);
        }
        
        String str = Integer.toString(val,16).toUpperCase();
        if ( str.length() == 1 )
        {
            str = '0' + str;
        }
        
        return str;
    }
    
    public String toString()
    {        

        if ( this.dataList.size() > 0 )
        {
            String baseName = this.dataList.get(0).substring(1).toLowerCase();
            if ( getMessageText(baseName + ".name.short") != null )
            {
                StringBuilder strBuilder = new StringBuilder();                
                strBuilder.append("Message: ");
                strBuilder.append(getMessageText(baseName + ".name.short"));
                strBuilder.append(" (" +  getMessageText(baseName + ".name.long") + ")\n");
                for ( int i=1; i<this.dataList.size(); i++ )
                {
                    String fieldName = getMessageText(baseName + ".field."+i+".name");
                    String unitName = getMessageText(baseName + ".field."+i+".unit");
//                    String desc = getMessageText(baseName + ".field."+i+".desc");
                    if ( fieldName == null )
                    {
                        fieldName = "?";
                    }
                    strBuilder.append("  " + fieldName + ": " + this.dataList.get(i));
                    if ( unitName != null && this.dataList.get(i).trim().length() > 0)
                    {
                        strBuilder.append(" " + unitName);
                    }
                    strBuilder.append("\n");
//                    if ( desc != null )
//                    {
//                        strBuilder.append("    " + desc + "\n");
//                    }
                }
                strBuilder.append("  Raw message: " + this.dataList);
                return strBuilder.toString();
            }
        }
        
        return this.dataList.toString();
        
    }
    
    private static String getMessageText ( String key )
    {   String text = null;
        try
        {
            ResourceBundle mesgTexts = getMessageTexts();
            return mesgTexts.getString(key);
        }
        catch ( java.util.MissingResourceException ex )
        {
            //
        }
        return text;
    }
    
    private static ResourceBundle getMessageTexts()
    {
        if ( mesgTexts == null )
        {
            mesgTexts = ResourceBundle.getBundle(DESCRIPTIONS_FILE);
        }
        return mesgTexts;
    }
}
