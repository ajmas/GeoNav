package osj.geonav.video;

import java.io.*;
import java.util.Date;

public class TripPlayback implements Runnable {

    String file = "/testfile.kml";
    
    TripPlayback()
    {
//        long time = 0;
//        float longitude = 0.0f;
//        float latitude  = 40.0f;
//        float height    = 0.0f;
//        
//        long startTime = System.currentTimeMillis();
//        while ( System.currentTimeMillis() - startTime < 80000)
//        {
//            latitude += .01f;
//            try {
//                writeCoordinates ( System.currentTimeMillis(), longitude, latitude, height);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            try
//            {
//                Thread.sleep(500);
//            }
//            catch ( InterruptedException ex )
//            {
//                //
//            }
//        }
        Thread thread = new Thread(this);
        thread.start();
    }
    
    private void writeCoordinates ( long time, float longitude, float latitude, float height) throws IOException
    {
        writeCoordinatesToKml(this.file, time, longitude, latitude, height);
//        notifyGoogleEarth (  time, longitude, latitude, height );
    }
    
    // see http://earth.google.com/kml/kml_tut.html
    private void writeCoordinatesToKml ( String file, long time, float longitude, float latitude, float height) throws IOException
    {
        String str = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<kml xmlns=\"http://earth.google.com/kml/2.1\">\n" +
            "    <refreshMode>onChange</refreshMode>\n" +
            "  <Placemark>\n" +

            "    <name>Simple placemark</name>\n" +
            "    <description></description>\n" +
            "    <Point>\n" +
            "      <coordinates>"+longitude+","+latitude+","+height+"</coordinates>\n" +
            "    </Point>\n" +
            "  </Placemark>\n" +
            "</kml>\n";
        
        FileOutputStream fout = new FileOutputStream (file);
        OutputStreamWriter outWriter = new OutputStreamWriter(fout,"UTF-8");
        outWriter.write(str);    
        outWriter.flush();
        outWriter.close();        
    }
    
    // see http://earth.google.com/kml/kml_tut.html
    private void notifyGoogleEarth ( long time, float longitude, float latitude, float height) throws IOException
    {
        String str = 
            "tell application \"Google Earth\"\n" +
            "    activate\n" +
            "    set viewInfo to (GetViewInfo)\n" +
            "    set dest to {latitude:"+latitude+", longitude:-73.4, distance:1.0E+5, tilt:0.0, azimuth:180}\n" +
            "    SetViewInfo dest\n" +
            "end tell\n";
        
        Process proc = Runtime.getRuntime().exec("osascript");
        
        OutputStream out = proc.getOutputStream(); 
        SerialReader sr = new SerialReader( proc.getInputStream() );
        out.write( str.getBytes("ascii") );
//        out.write( 0x04 );
        out.flush();
        System.out.println("--");
        out.close();
//        proc.destroy()
//        TextWriter tw = new TextWriter( str, proc.getOutputStream() );
        try {
          proc.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(proc.exitValue());
//        FileOutputStream fout = new FileOutputStream (file);
//        OutputStreamWriter outWriter = new OutputStreamWriter(fout,"UTF-8");
//        outWriter.write(str);    
//        outWriter.flush();
//        outWriter.close();        
    }
    
    
    public void run ()
    {
        String baseFolder = "/Users/ajmas/Desktop/test.vgps";
        try
        {
            BufferedReader reader = new BufferedReader ( new InputStreamReader ( new FileInputStream ( new File ( baseFolder, "gpsdata") ), "UTF16") );
            String sampleRateStr = reader.readLine();
            long sampleRate = Long.parseLong(sampleRateStr.trim());
            String line = null;
            
            int tripTime = 0;
            while ( (line = reader.readLine() ) != null )
            {
                String[] parts = line.split(",");
                long date = Long.parseLong(parts[0]);
                System.out.println((tripTime/1000f) + " -- " + new Date(date) + " -- " + parts[1] + ", " 
                        + parts[2] + ", " + parts[3]);
                try
                {
                    Thread.sleep(sampleRate);
                }
                catch ( InterruptedException ex )
                {                    
                }     
                tripTime += sampleRate;
            }
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
    }
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
//                FileOutputStream fOut = new FileOutputStream("/tmp/out.txt",true);
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    //fOut.write(buffer,0,len);
                    System.out.print(new String(buffer,0,len,"ascii"));
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
    
    public static void main ( String[] args )
    {
        TripPlayback tp = new TripPlayback();
    }
}
