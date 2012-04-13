package osj.geonav.video;

import quicktime.*;
import quicktime.io.*;
import quicktime.util.QTPointer;
import quicktime.qd.*;
import quicktime.std.*;
import quicktime.std.movies.*;
import quicktime.std.movies.media.*;
import quicktime.std.sg.SGDeviceList;
import quicktime.std.sg.SGDeviceName;
import quicktime.std.sg.SGVideoChannel;
import quicktime.std.sg.SequenceGrabber;
import quicktime.std.image.*;
import quicktime.util.*;

import java.io.*;
import java.nio.IntBuffer;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Properties;

import osj.geonav.iface.GeoDataStream;
import osj.geonav.iface.GeoMessage;
import osj.geonav.iface.GeoMessageListener;
import osj.geonav.iface.Location;
import osj.geonav.nmea.NMEAMessage;
//import osj.geonav.nmea.NMEAMessageHandler;
////import ajmas74.geovideo.FrameSequenceOutput;
//import ajmas74.geovideo.QTCameraPlayer;

public class VideoSampleBuilder2 { //implements GeoMessageListener {

////    public static final int VIDEO_TRACK_WIDTH = 320;
////    public static final int VIDEO_TRACK_HEIGHT = 240;
//    public static final int VIDEO_TRACK_VOLUME = 0;
//    public static final int KEY_FRAME_RATE = 30;
//
//    String geoNavString = "0,0,0,0";
//    
//    Properties userProps = new Properties();
//    QDRect startRect = null;
//    QDRect endRect = null;
//
//    //-----------------
//    private boolean isSessionOpen = false;
//
//    private SequenceGrabber grabber;
//
//    private QDRect cameraSize;
//
//    private QDGraphics gWorld;
//
//    private int intsPerRow;
//
//    boolean grabbing = true;
//
//    private int width; // frame width
//
//    private int height; // frame height
//
//    private int[] pixelData;
//
//    private IntBuffer pixelBuffer;
//
//    DSequence ds = null;
//
//    SGVideoChannel channel;
//
//    int sourceIdx = -1;
//
////    FrameSequenceOutput frameSequenceOutput;    
//    //-----------------
//    
//    NMEAMessageHandler mesgHandler;
//    boolean firstMessage = false;
//    
//    public VideoSampleBuilder2() throws QTException, IOException {
//
//        initSequenceGrabber();
//        mesgHandler = new NMEAMessageHandler( null );
//        mesgHandler.stream.addGeoDataListener(this);
//        (new Thread(mesgHandler)).start();
//        
//        while ( ! firstMessage )
//        {
//            try
//            {
//                Thread.sleep(500);
//            }
//            catch ( InterruptedException ex )
//            {
//                
//            }
//        }
//        this.width = this.cameraSize.getWidth();
//        this.height = this.cameraSize.getHeight();
//        this.intsPerRow = this.gWorld.getPixMap().getPixelData()
//                .getRowBytes() / 4;
//        this.pixelData = new int[intsPerRow * height];
//
//        // INFO: start capturing
//        // QTFile movieFile = new QTFile(new java.io.File("NoFile"));
//        this.grabber.setDataOutput(null,
//                quicktime.std.StdQTConstants.seqGrabDontMakeMovie);
//        this.grabber.prepare(true, true);
//        this.grabber.startRecord();
//
//        this.pixelBuffer = IntBuffer.wrap(this.pixelData);
//
//        isSessionOpen = true;        
//        
////        /* try to load "videoSampleBuilder.properties" from 
////           current directory.  this contains file.location and
////           start.x/y/width/height and end.x/y/width/height params
////         */
////        try {
////            userProps.load (new FileInputStream (new File ("videosamplebuilder.properties")));
////            System.out.println ("Loaded \"videosamplebuilder.properties\"");
////        } catch (Exception e) {
////            System.out.println ("Couldn't load \"videosamplebuilder.properties");
////        }
//
////        QTCameraPlayer qtCamera = new QTCameraPlayer(-1);
////        int width = qtCamera.getWidth();
////        int height = qtCamera.getHeight();
//        
//        int CODEC_TYPE = QTUtils.toOSType ("SVQ3");
//        // int CODEC_TYPE = QTUtils.toOSType("mp4v");
//
//        // create a new empty movie
//        QTFile movFile = new QTFile (new java.io.File("videotrack.mov"));
//        Movie movie =
//            Movie.createMovieFile(movFile,
//                                  StdQTConstants.kMoviePlayer,
//                                  StdQTConstants.createMovieFileDeleteCurFile |
//                                  StdQTConstants.createMovieFileDontCreateResFile);
//        System.out.println ("Created Movie");
//
//        // now create an empty video track
//        int timeScale = 600; // 100 units per second
//        Track videoTrack = movie.addTrack (width, height,
//                                           VIDEO_TRACK_VOLUME);
//        System.out.println ("Added empty Track");
//
//        // now we need media for this track
//        VideoMedia videoMedia = new VideoMedia(videoTrack,
//                                               timeScale);
//
//        Track geoNavTrack = movie.addTrack ( width, 48, 0 );
//     
//        TextMedia geoNavMedia = new TextMedia (geoNavTrack, timeScale);
//        TextMediaHandler txtHandler = geoNavMedia.getTextHandler();
//        QDRect textBox = new QDRect (0,0,width,48);
//        
//        System.out.println ("Created GWorld, - Bounds are " +
//                gWorld.getBounds());
//
//        // get to work
//        videoMedia.beginEdits();
//        geoNavMedia.beginEdits();
//        
//        // figure out per-frame offsets
//        QDRect gRect = new QDRect (0, 0,
//                                      width,
//                                      height);
//        int frames = 300;
//
//        // reserve an image with enough space to hold compressed image
//        // this is needed by the last arg of CSequence.compressFrame
//        int rawImageSize =
//            QTImage.getMaxCompressionSize (gWorld, 
//                                           gRect, 
//                                           gWorld.getPixMap().getPixelSize(),
//                                           StdQTConstants.codecNormalQuality, 
//                                           CODEC_TYPE,
//                                           // CodecComponent.anyCodec);
//                                           CodecComponent.bestFidelityCodec);
//        QTHandle imageHandle = new QTHandle (rawImageSize, true);
//        imageHandle.lock();
//        RawEncodedImage compressedImage =
//            RawEncodedImage.fromQTHandle(imageHandle);
//
//        // create a CSequence
//        /* see behavior flags at
//http://developer.apple.com/documentation/QuickTime/APIREF/SOURCESI/compresssequencebegin.htm#//apple_ref/c/func/CompressSequenceBegin
//        */
//        CSequence seq = new CSequence (gWorld,
//                                       gRect, 
//                                       gWorld.getPixMap().getPixelSize(),
//                                       CODEC_TYPE,
//                                       CodecComponent.bestFidelityCodec,
//                                       StdQTConstants.codecNormalQuality, 
//                                       StdQTConstants.codecNormalQuality, 
//                                       KEY_FRAME_RATE,
//                                       null,
//                                       0);
//
//        // remember an ImageDescription from this sequence definition
//        ImageDescription imgDesc = seq.getDescription();
//
//        int duration = 40;
//        
//        // loop through the specified number of frames, drawing 
//        // scaled instances into our GWorld and compressing those
//        // to the CSequence
//        for (int i=1; i<frames; i++) {
//            System.out.println ("i==" + i);
//
//            IntBuffer buffer = getNextFrame();          
//
//            // compress a frame
//            /* behavior flags at
//http://developer.apple.com/documentation/QuickTime/APIREF/SOURCESI/compresssequenceframe.htm#//apple_ref/c/func/CompressSequenceFrame
//            */
//            CompressedFrameInfo cfInfo =
//                seq.compressFrame (gWorld, 
//                                   gRect, 
//                                   StdQTConstants.codecFlagUpdatePrevious, 
//                                   compressedImage);
//            System.out.println ("similarity = " + cfInfo.getSimilarity());
//
//            // see http://developer.apple.com/qa/qtmcc/qtmcc20.html
//            // for explanation of mediaSampleNotSync
//            boolean syncSample = (cfInfo.getSimilarity() == 0);
//            int flags = syncSample ? 0 : StdQTConstants.mediaSampleNotSync;
//           
//            // add compressed frame to the video media
//            videoMedia.addSample (imageHandle, 
//                                  0, 
//                                  cfInfo.getDataSize(),
//                                  duration, // time per frame, in timescale
//                                  imgDesc,
//                                  1, // one sample
//                                  flags
//                                  );
//            
//            byte[] text = (geoNavString).getBytes("ascii");
//            QTPointer msgPoint = new QTPointer (text);
//            txtHandler.addTextSample (msgPoint, // text
//                    0, // font number
//                    14, // font size,
//                    QDConstants.bold, // style,
//                    QDColor.yellow, // fg color,
//                    QDColor.black, // bg color,
//                    QDConstants.teCenter,// justification
//                    textBox, // box
//                    0, // displayFlags
//                    0, // scrollDelay
//                    0, // hiliteStart
//                    0, // hiliteEnd
//                    QDColor.white, // rgbHiliteColor
//                    duration // duration
//                    );
//            try
//            {
//                Thread.sleep(50);
//            }
//            catch ( InterruptedException ex )
//            {
//                //
//            }
//        } // for
//
//        // done editing
//        geoNavMedia.endEdits();
//        videoMedia.endEdits();
//
//        // now insert this media into track
//        videoTrack.insertMedia (0, // trackStart
//                                0, // mediaTime
//                                videoMedia.getDuration(), // mediaDuration
//                                1); // mediaRate
//        System.out.println ("inserted media into video track");
//        geoNavTrack.insertMedia(0, 0, geoNavMedia.getDuration(), 1);
//        // save up 
//        System.out.println ("Saving...");
//        OpenMovieFile omf = OpenMovieFile.asWrite (movFile);
//        movie.addResource (omf,
//                           StdQTConstants.movieInDataForkResID,
//                           movFile.getName());
//        System.out.println ("Done");
//
//    }
//
//    /** Gets imageFile from props file, or file-preview if 
//        that doesn't work.
//     */
//    protected QTFile getImageFile () throws QTException {
//        // is it in the props?
//        QTFile imageFile = null;
//        if (userProps.containsKey ("file")) {
//            imageFile = new QTFile (userProps.getProperty("file"));
//            if (! imageFile.exists())
//                imageFile = null;
//        }
//
//        // if not, or if that failed, then use a dialog
//        if (imageFile == null) {
//            int[] types = {};
//            imageFile = QTFile.standardGetFilePreview (types);
//        }
//        return imageFile;
//    }
//
//    public IntBuffer getNextFrame() {
//        if (!this.isSessionOpen)
//            return null;
//
//        try {
//            this.grabber.idle();
//            this.grabber.update(null);
//
//            this.gWorld.getPixMap().getPixelData().copyToArray(0, pixelData, 0,
//                    pixelData.length);
//
//            this.pixelBuffer.rewind();
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return pixelBuffer;
//    }
//    
//    
//    private void initSequenceGrabber() throws StdQTException, QTException {
//
//        this.grabber = new SequenceGrabber();
//        this.channel = new SGVideoChannel(this.grabber);
//
//        // create list of input devices
//        // getDeviceList flags:
//        // http://developer.apple.com/documentation/QuickTime/APIREF/SOURCESIII/sggetchanneldevicelist.htm
//        // Note: to list all devices no matter the availability pass:
//        // StdQTConstants.sgDeviceListDontCheckAvailability);
//        SGDeviceList devices = channel.getDeviceList(0);//
//
//        System.out.println("sourceIdx: " + sourceIdx);
//
//        for (int i = 0; i < devices.getCount(); i++) {
//            SGDeviceName deviceName = devices.getDeviceName(i);
//            // is it available?
//
//            System.out.print(i + " .. " + deviceName.getName() + " .. ");
//            if ((deviceName.getFlags() & StdQTConstants.sgDeviceNameFlagDeviceUnavailable) == 0) {
//                System.out.print("available");
//                if (i == this.sourceIdx) {
//                    System.out.print(" -- selected");
//                    this.channel.setDevice(deviceName.getName());
//                }
//            }
//            System.out.println();
//        }
//
//        this.cameraSize = channel.getSrcVideoBounds();
//
//        this.cameraSize.resize(640, 480);
//
//        this.width = this.cameraSize.getWidth();
//        this.height = this.cameraSize.getHeight();
//
//        if (quicktime.util.EndianOrder.isNativeLittleEndian()) {
//            System.out.println("is little endian");
//            this.gWorld = new QDGraphics(QDConstants.k32BGRAPixelFormat,
//                    cameraSize);
//        } else {
//            this.gWorld = new QDGraphics(QDGraphics.kDefaultPixelFormat,
//                    cameraSize);
//        }
//        this.grabber.setGWorld(this.gWorld, null);
//
//        this.channel.setBounds(this.cameraSize);
//        this.channel.setUsage(quicktime.std.StdQTConstants.seqGrabRecord
//                | quicktime.std.StdQTConstants.seqGrabPreview
//                | quicktime.std.StdQTConstants.seqGrabPlayDuringRecord);
//        this.channel.setFrameRate(0);
//        this.channel
//                .setCompressorType(quicktime.std.StdQTConstants.kComponentVideoCodecType);
//    }    
//
//
//    public static void main (String[] arrrImAPirate) {
//        try {
//            QTSessionCheck.check();
//            new VideoSampleBuilder2();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.exit(0);
//    }
//
////    public void newMessage(NMEAMessage message) {
////        // TODO Auto-generated method stub
////        
////        long time = System.currentTimeMillis();
////        float longitude  = 0;
////        float latitude = 0;
////        float height = 0;
////        
////        geoNavString = time + "," + longitude + "," + latitude + "," + height;
////    }
//
//    public void newMessage(GeoMessage message) {
//        firstMessage = true;
//        if ( message instanceof Location )
//        {
//            Location location = (Location) message;
//            long time = System.currentTimeMillis();
//            geoNavString = time + "," + location.getLongitude() + "," + location.getLatitude() + "," + location.getHeight();
//        }
//    }

}
