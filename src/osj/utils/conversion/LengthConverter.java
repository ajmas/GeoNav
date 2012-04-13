package osj.utils.conversion;

/**
 * TODO Description and implementation details.
 */
public class LengthConverter extends AbstractMeasurementConverter
{

    
    /**
     * Default Constructor
     */
    public LengthConverter()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public Object convert( String fromUnit, String toUnit, Object value ) throws UnsupportedConversionException
    {
        return convertFromMeters(toUnit,convertToMeters(fromUnit,value));
    }
        
    private double convertToMeters ( String fromUnit, Object value) throws UnsupportedConversionException
    {
        fromUnit = fromUnit.toLowerCase();
        
        if ( fromUnit.startsWith("meter") || fromUnit.startsWith("metre"))
        {
            return toDouble(value);      
        }
        else if ( fromUnit.startsWith("inch") )
        {
            return toDouble(value) * 0.0254;      
        }
        else if ( fromUnit.equalsIgnoreCase("feet") || fromUnit.equalsIgnoreCase("foot") )
        {
            return toDouble(value) * 0.3048;      
        }
        else if ( fromUnit.indexOf("meter") > -1|| fromUnit.indexOf("metre") > -1 )
        {
            String prefix = fromUnit.substring(0,fromUnit.indexOf("meter"));                       
            return toDouble(value) * Math.pow(10,powerValue(prefix));      
        }
        throw new UnsupportedConversionException("Unable to convert from unit '"+fromUnit+"'");
    }
    
    private double convertFromMeters ( String toUnit, Object value) throws UnsupportedConversionException
    {
        toUnit = toUnit.toLowerCase();
        
        if ( toUnit.startsWith("meter") || toUnit.startsWith("metre"))
        {
            return toDouble(value);      
        }
        else if ( toUnit.startsWith("inch") )
        {
            return toDouble(value) / 0.0254;      
        }
        else if ( toUnit.equalsIgnoreCase("feet") || toUnit.equalsIgnoreCase("foot") )
        {
            return toDouble(value) / 0.3048;      
        }
        else if ( toUnit.indexOf("meter") > -1|| toUnit.indexOf("metre") > -1 )
        {
            String prefix = toUnit.substring(0,toUnit.indexOf("meter"));                       
            return toDouble(value) / Math.pow(10,powerValue(prefix));      
        }
        throw new UnsupportedConversionException("Unable to convert from unit '"+toUnit+"'");
    }    
    

    /**
     * TODO implementation details
     *
     * @param args
     */
    public static void main( String[] args )
    {
        LengthConverter converter = new LengthConverter();
        try
        {
            System.out.println(converter.convert("inch","centimeter",1));
        }
        catch ( UnsupportedConversionException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
