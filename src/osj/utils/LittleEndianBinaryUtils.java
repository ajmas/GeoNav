package osj.utils;


public class LittleEndianBinaryUtils
{

//    /**
//     * converts 4 little enddian bytes starting at offset to a big endian int 
//     * 
//     * taken from: newgroups topic "Converting byte[] to a Integer or int"    
//     * 
//     * @param buf 
//     * @param offset 
//     * @return int
//     */
//    public static int byte4ToInt( byte[] buf, int offset )
//    {
//        return (buf[offset+3] << 24) + 
//              ((buf[offset+2] & 0xff) << 16) +
//              ((buf[offset+1] & 0xff) << 8) + 
//               (buf[offset] & 0xff);                                                                                            
//    }

    /**
     * converts 4 little enddian bytes starting at offset to a big endian long 
     * 
     * taken from: newgroups topic "Converting byte[] to a Integer or int"    
     * 
     * @param buf 
     * @param offset 
     * @return int
     */
    public static int byte4ToLong( byte[] buf, int offset )
    {
        return (buf[offset+3] << 24) + 
              ((buf[offset+2] & 0xff) << 16) +
              ((buf[offset+1] & 0xff) << 8) + 
               (buf[offset] & 0xff);                                                                                            
    }
    
    /**
     * converts 2 little enddian bytes starting at offset to a big endian int 
     * 
     * taken from: newgroups topic "Converting byte[] to a Integer or int"    
     * 
     * @param buf 
     * @param offset 
     * @return int
     */    
    public static int byte2ToInt( byte[] buf, int offset )
    {
        return ((buf[offset+1] & 0xff) << 8) + 
               (buf[offset] & 0xff);                                                                                            
    }    
    
    /**
     * converts 4 little endian bytes starting at offset to a big endian float   
     * 
     * length = 4 bytes, IEEE single precision floating point.
     * Least significant byte first.  This format consists of:
     *   1 bit - sign
     *   8 bits - exponent, excess 127
     *  23 bits - mantissa, implied high-order 1
     *
     * @param bytes 
     * @param offset 
     * @return float
     */        
    public static float byte4ToFloat (  byte[] bytes, int offset )
    {
         // INFO: see comp.lang.java.programmer : "How do I convert 4 bytes of float (C/C++) to java Float ?"
         int i = (bytes[offset+0]&0xff) +
                ((bytes[offset+1]&0xff) << 8) + 
                ((bytes[offset+2]&0xff) << 16) + 
                 (bytes[offset+3] << 24);
         return Float.intBitsToFloat( i );
    }
    
    /**
     * converts 8 little endian bytes starting at offset to a big endian double   
     * 
     * 8 bytes, IEEE double precision floating point.
     * Least significant byte first.  This format consists of:
     *   1 bit - sign
     *  11 bits - exponent, excess 1023
     *  52 bits - mantissa, implied high-order 1
     *  
     * @param buf 
     * @param offset 
     * @return double
     */        
    public static double byte8ToDouble (  byte[] bytes, int offset )
    {
        long i = (bytes[offset+0]&0xff) +
                ((bytes[offset+1]&0xff) << 8) + 
                ((bytes[offset+2]&0xff) << 16) +
                ((bytes[offset+3]&0xff) << 24) + 
                ((bytes[offset+4]&0xff) << 32) + 
                ((bytes[offset+5]&0xff) << 40) + 
                ((bytes[offset+6]&0xff) << 48) +
                ((bytes[offset+7]&0xff) << 56);

        return Double.longBitsToDouble( i );         

    }    
}
