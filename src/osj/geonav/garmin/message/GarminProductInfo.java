package osj.geonav.garmin.message;


public class GarminProductInfo implements GarminMessage
{
    private static final int MESG_TYPE = 255;
    int    productId;
    float  softwareVersion;
    String productDescription;

    /**
     * @return the productDescription
     */
    public String getProductDescription()
    {
        return this.productDescription;
    }

    /**
     * @return the productId
     */
    public int getProductId()
    {
        return this.productId;
    }

    /**
     * @return the softwareVersion
     */
    public float getSoftwareVersion()
    {
        return this.softwareVersion;
    }

    /**
     * @param productDescription the productDescription to set
     */
    public void setProductDescription( String productDescription )
    {
        this.productDescription = productDescription;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId( int productId )
    {
        this.productId = productId;
    }

    /**
     * @param softwareVersion the softwareVersion to set
     */
    public void setSoftwareVersion( float softwareVersion )
    {
        this.softwareVersion = softwareVersion;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder strBuffer = new StringBuilder();
        strBuffer.append( "id=" + this.productId );
        strBuffer.append( ", version=" + this.softwareVersion );
        strBuffer.append( ", description=" + this.productDescription );
        return strBuffer.toString();
    }

    public int getType()
    {
        return MESG_TYPE;
    }


}
