package osj.geonav.iface;

public interface Heading extends GeoMessage {
    
    public double getTrueHeading();
    
    public double getMagneticHeading();
}
