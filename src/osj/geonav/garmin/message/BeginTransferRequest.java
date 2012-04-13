/*
 * $Author:  Mas $
 * $Date: Aug 25, 2006 $
 * $Revision: 1.0 $
 */

package osj.geonav.garmin.message;



/**
 * TODO Description and implementation details.
 */
public class BeginTransferRequest implements GarminMessage
{
    private static final int MESG_TYPE = 0x1B;
    private int packetCount;
    
    /**
     * @param packetCount
     */
    public BeginTransferRequest ( int packetCount )
    {
        this.packetCount = packetCount;
    }
    
    /**
     * @return the packetCount
     */
    public int getPacketCount()
    {
        return this.packetCount;
    }


    /**
     * @param packetCount the packetCount to set
     */
    public void setPacketCount( int packetCount )
    {
        this.packetCount = packetCount;
    }


    public int getType()
    {
        // TODO Auto-generated method stub
        return MESG_TYPE;
    }
    

}
