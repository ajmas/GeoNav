package osj.geonav.video;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.awt.*;

public class TripCapture implements Runnable
{

    private DataOutputStream gpsOut;
    private String  baseFileName;
    private long    maxTime = 0;
    private boolean run     = true;
    private long    sampleDelay = 500;
    private String  currentDir;
    private File    dataFolder; 
    
    public void start()
    {
        if ( currentDir == null )
        {
            currentDir = System.getProperty("user.dir");
        }
        
        FileDialog fileDialog = new FileDialog(new Frame(),currentDir,FileDialog.SAVE);
        fileDialog.setVisible(true);
        String fileName = fileDialog.getFile();
        currentDir  = fileDialog.getDirectory();
                
        if ( !fileName.endsWith(".vgps") )
        {
            fileName = fileName + ".vgps";
        }        

        this.dataFolder = new File(currentDir,fileName);
        if ( ! this.dataFolder.exists() )
        {
            this.dataFolder.mkdir();
        }
        
        Thread thread = new Thread(this);
        thread.start();
        
    }
    
    public void stop ()
    {
        this.run = false;
    }
    
    // -------------------------------------------------------------
    // Video related stuff
    // -------------------------------------------------------------

    
    // -------------------------------------------------------------
    // GPS related stuff
    // -------------------------------------------------------------
    
    //private long  time;
    private float longitude;
    private float latitude;
    private float height;
    
    private void openGpsDataFile () throws IOException
    {
        FileOutputStream fOut = null;
        try
        {
            fOut = new FileOutputStream( new File ( dataFolder, "gpsdata") );
            this.gpsOut = new DataOutputStream (fOut);
            this.gpsOut.writeChars( sampleDelay + "\n" );
            
//            this.gpsOut.writeLong(1); // reference point
//            this.gpsOut.writeLong(1); // coordinate system            
        }
        finally
        {            
//            if ( fOut != null )
//            {
//                fOut.close();
//                fOut = null;
//                this.gpsOut = null;
//            }
        }
    }
    
    private void closeGpsDataFile () throws IOException
    {
        if ( this.gpsOut != null )
        {
            this.gpsOut.close();
            this.gpsOut = null;
        }
    }
    
    private void writeDataSample () throws IOException
    {
        
//        this.gpsOut.writeLong( System.currentTimeMillis() );
//        this.gpsOut.writeFloat( this.latitude );
//        this.gpsOut.writeFloat( this.longitude );
//        this.gpsOut.writeFloat( this.height );
        System.out.println("writing");
        this.gpsOut.writeChars( System.currentTimeMillis() + "," );
        this.gpsOut.writeChars( this.latitude + "," );
        this.gpsOut.writeChars( this.longitude + "," );
        this.gpsOut.writeChars( this.height + "\n" );
        this.gpsOut.flush();
    }
    
    // -------------------------------------------------------------
    // end GPS related stuff
    // -------------------------------------------------------------
    
    public void run ()
    {
        try
        {
            // TODO: start QT capture
            
            openGpsDataFile();
            
            long startTime = System.currentTimeMillis();
            while ( run && (((System.currentTimeMillis()-startTime) < maxTime ) || maxTime == 0 ) )
            {
                // write next frame
                // write GPS info
                
                if ( this.gpsOut == null )
                {                    
                    break;
                }
                writeDataSample();
                try
                {
                    Thread.sleep(sampleDelay);
                }
                catch ( InterruptedException ex )
                {                    
                }
            }
        }
        catch ( Exception ex )
        {
            System.err.println( " Exception at: " + (new Date()) );
            ex.printStackTrace();
        }
        finally 
        {
            try
            {
                closeGpsDataFile();
                //TODO stop quicktime capture
            }
            catch ( IOException ex )
            {
                //
            }
        }
        System.out.println("finished");
    }
    
    public static void main ( String[] args )
    {
        TripCapture tripCapture  = new TripCapture();
        tripCapture.start();
    }
    
}
