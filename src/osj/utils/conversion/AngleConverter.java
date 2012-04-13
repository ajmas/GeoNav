package osj.utils.conversion;

/**
 * TODO Description and implementation details.
 */
public class AngleConverter extends AbstractMeasurementConverter
{
    private static AngleConverter instance;
    
    /**
     * Default Constructor
     */
    AngleConverter()
    {
        super();
    }

    public Object convert( String fromUnit, String toUnit, Object value ) throws UnsupportedConversionException
    {
        return convertFromDegrees(toUnit,convertToDegrees(fromUnit,value));
    }
        
    private double convertToDegrees ( String fromUnit, Object value) throws UnsupportedConversionException
    {
        fromUnit = fromUnit.toLowerCase();
        
        if ( fromUnit.startsWith("radian") )
        {
            return Math.toDegrees(toDouble(value));      
        }
        else if ( fromUnit.startsWith("degree") )
        {
            return toDouble(value);      
        }
        else if ( (fromUnit.equals("dddmm.mmm") || fromUnit.equals("ddmm.mmm")) && value instanceof String)
        {
            String text = (String) value; 
            //String degree = text.substring(0,fromUnit.lastIndexOf('d'));
             
            int dotIdx = text.indexOf('.');
            String degree = text.substring(0,dotIdx-2);
            String minutes = text.substring(dotIdx-2);
            
            return Double.parseDouble(degree) + (Double.parseDouble(minutes)/60.0);
        }
        else if ( fromUnit.startsWith("ddd\u00B0mm\"ss") && value instanceof String)
        {
            String text = (String) value;
            double degrees = Double.parseDouble(text.substring(0,text.indexOf('\u00B0')));
            double minutes = Double.parseDouble(text.substring(text.indexOf('\u00B0')+1,text.indexOf('\'')));
            double seconds = Double.parseDouble(text.substring(text.indexOf('\'')+1,text.indexOf('\"')));
           
            return degrees + ( ( minutes + ( seconds / 60.0) ) / 60 );
            
        }
        throw new UnsupportedConversionException("Unable to convert from unit '"+fromUnit+"'");
    }
    
    private Object convertFromDegrees ( String toUnit, double value) throws UnsupportedConversionException
    {
        toUnit = toUnit.toLowerCase();
        
        if ( toUnit.startsWith("radian") )
        {
            return Math.toRadians(value);      
        }
        else if ( toUnit.startsWith("degree") )
        {
            return value;      
        }
        else if ( toUnit.startsWith("ddd\u00B0mm\"ss") )
        {
            // INFO: explanation at: http://geography.about.com/library/howto/htdegrees.htm
            //       'How To Convert a Decimal to Sexagesimal'
            
            int degrees = 0;
            int minutes = 0;
            int seconds = 0;
            
            String result = "";
            degrees = (int) value;
            double b = Math.abs((value - degrees)) * 60;
            
            minutes = (int) b;
            seconds = (int) Math.abs((b - minutes) * 60);
   
            result = degrees + "\u00B0" + minutes + "\'" + seconds + "\"";
            return result;
        }
//        else if ( toUnit.startsWith("ddmmss") )
//        {
//            
//        }
        throw new UnsupportedConversionException("Unable to convert from unit '"+toUnit+"'");
    }    
    

    /**
     * TODO implementation details
     *
     * @param args
     */
    public static void main( String[] args )
    {
        AngleConverter converter = new AngleConverter();
        try
        {
            System.out.println(converter.convert("dddmm.mmm","degree","18012.333"));
            System.out.println(converter.convert("dddmm.mmm","degree","3928.506"));
            System.out.println(converter.convert("dddmm.mmm","ddd\u00B0mm\"ss","3928.506"));
            
            System.out.println(converter.convert("dddmm.mmm","ddd\u00B0mm\"ss","18012.333"));
            System.out.println(converter.convert("degree","ddd\u00B0mm\"ss","45.527"));
            
//            System.out.println(converter.convert("degree","ddd\u00B0mm\"ss",121.135));
//            System.out.println(converter.convert("ddd\u00B0mm\"ss","degree","121�8\'6\""));
            
//            System.out.println(converter.convert("degree","ddd\u00B0mm\"ss",121.135));
//            
//            System.out.println(converter.convert("degree","ddd\u00B0mm\"ss","360.36"));
//            System.out.println(converter.convert("degree","ddd\u00B0mm\"ss","1.36"));
//            System.out.println(converter.convert("degree","ddd\u00B0mm\"ss","-1.36"));
//            System.out.println(converter.convert("degree","ddd\u00B0mm\"ss",-1.36));
        
        }        
        catch ( UnsupportedConversionException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
    
    /**
     * Returns the shared instance of this class
     *
     * @return AngleConverter
     */
    public static AngleConverter getInstance()
    {
        if ( AngleConverter.instance == null )
        {
            AngleConverter.instance = new AngleConverter();
        }
        return AngleConverter.instance;        
    }
}

