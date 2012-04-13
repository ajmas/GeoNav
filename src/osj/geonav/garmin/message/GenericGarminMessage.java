/*
 * $Author:  Mas $
 * $Date: Aug 25, 2006 $
 * $Revision: 1.0 $
 */

package osj.geonav.garmin.message;

import osj.utils.Hex2Bin;

/**
 * Used for messages who do not have an actual class
 * associated with them.
 */
public class GenericGarminMessage implements GarminMessage
{
    private int type;
    private byte[] data;
    
    /**
     * @param type
     * @param data 
     * @param length 
     */
    public GenericGarminMessage ( int type, byte[] data, int length )
    {
        this.type = type;
        this.data = new byte[length];
        System.arraycopy( data, 0, this.data, 0, length );
    }
    
    public int getType()
    {
        // TODO Auto-generated method stub
        return this.type;
    }

    public String toString ()
    {
        return "GenericGarminMessage:[type="+this.type+",data="+Hex2Bin.bytesToHexString( this.data, 0, this.data.length )+"]";    
    }
}
