package osj.utils.conversion;

/**
 * TODO Description and implementation details.
 */
public interface MeasurementConverter
{
    /**
     * 
     * TODO implementation details
     * 
     * @param fromUnit name, or alias, of unit to convert from
     * @param toUnit name, or alias, of unit to convert to
     * @param value
     * @return converted value
     * @throws UnsupportedConversionException
     */
    public Object convert( String fromUnit, String toUnit, Object value ) throws UnsupportedConversionException;
}
