package osj.utils.conversion;

public abstract class AbstractMeasurementConverter implements MeasurementConverter
{
    private static final Object[][] METRIC_POWER = new Object [][] {
        {"yotta","Y",24},
        {"zetta","Z",21},
        {"exa","E",18},
        {"peta","P",15},
        {"tera","T",12},
        {"giga","G",9},
        {"mega","M",6},
        {"kilo","k",3},
        {"hecto","h",2},
        {"deka","da",1},
        {"deci","d",-1},
        {"centi","c",-2},
        {"milli","m",-3},
        {"micro","\u03BC",-6},
        {"nano","n",-9},
        {"pico","p",-12},
        {"femto","f",-15},
        {"atto","a",-18},
        {"zepto","z",21},
        {"yocto","y",-24}
    };
    
    public AbstractMeasurementConverter()
    {
        super();
    }

    /**
     * Attempts to convert the specified value to a double value
     *
     * @param value Value to be converted
     * @return double
     * @throws UnsupportedConversionException
     */
    double toDouble ( Object value ) throws UnsupportedConversionException
    {
        if ( value instanceof Number )
        {
            if ( value instanceof Integer )
            {
                return ((Integer)value) * 1.0;
            }
            else if ( value instanceof Float )
            {
                return ((Float)value) * 1.0;
            }
            else if ( value instanceof Short )
            {
                return ((Short)value) * 1.0;
            }
            else if ( value instanceof Double )
            {
                return (Double) value;
            }            
        }
        else if ( value instanceof Double )
        {
            return (Double) value;
        }        
        else if ( value instanceof String )
        {
            return Double.parseDouble((String)value);
        }
        throw new UnsupportedConversionException("Unable to convert to double, from class " + value.getClass());
    }
    
    /**
     * Using the metric power name, returns the associated power value.
     * For example if the name is 'centi' then the returned value is -2
     *
     * @param name metric power prefix
     * @return double
     */
    double powerValue ( String name )
    {
        for ( int i=0; i<METRIC_POWER.length; i++ )
        {
            if ( METRIC_POWER[i][0].equals(name) )
            {
                return (Integer) METRIC_POWER[i][2];
            }
        }
        return 0;
    }
    
    public abstract Object convert( String fromUnit, String toUnit, Object value ) throws UnsupportedConversionException;

}
