package osj.geonav.nmea;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class NMEAMessageFactory {

    private Map<String,Class> messageTypeMap = new HashMap<String,Class>();
    private Map<String,NMEAMessage> instanceMap = new HashMap<String, NMEAMessage>();

    private static NMEAMessageFactory sharedInstance;
    
    public static NMEAMessageFactory getInstance()
    {
        if ( sharedInstance == null )
        {
            NMEAMessageFactory.sharedInstance = new NMEAMessageFactory();
        }
        return NMEAMessageFactory.sharedInstance;
    }
    
    
    private NMEAMessageFactory ()
    {
        initialize();
    }
    
    private void initialize ()
    {
        this.messageTypeMap.put("$GPRMC",GPRMCMessage.class);
        this.messageTypeMap.put("$GPGGA",GPGGAMessage.class);
        this.messageTypeMap.put("$GPVTG",GPVTGMessage.class);
        this.messageTypeMap.put("$GPHDG",GPHDGMessage.class);
        this.messageTypeMap.put("$GPHDT",GPHDTMessage.class);
        this.messageTypeMap.put("$GPGSV",GPGSVMessage.class);
        this.messageTypeMap.put("$GPGLL",GPGLLMessage.class);        
    }
    
    /**
     * This creates an NMEAMessage instance using the text passed as input.
     * TODO this currently does not handle error situations correctly, correct !!!!! 
     * 
     * @param nmeaMessageText
     * @return
     */
    public NMEAMessage createMessageFromString ( String nmeaMessageText )
    {
        String tempStr = nmeaMessageText;
        if ( tempStr.indexOf("$") > 1 )
        {
            //logText(tempStr.substring(0,tempStr.indexOf("$")));
            tempStr = tempStr.substring(tempStr.indexOf("$"));
        } else if ( tempStr.indexOf("$") < 0 )
        {
            return null; // TODO temporary!!!
        }
        
        // ignore checksum
        if ( tempStr.indexOf('*') > -1 )
        {
            tempStr = tempStr.substring(0,tempStr.indexOf('*'));
        }
        
        String[] elements = tempStr.split(",");
        
        
        NMEAMessage mesg;
        try {
            mesg = createMessageFromType(elements[0]);
        } catch (Exception e) {
            mesg = new NMEAGenericMessage();
        }
        
        mesg.setData(Arrays.asList(elements));
        
        return mesg;
    }
    
    /**
     * Given an NMEA message type this will create
     * @param nmeaMessageType
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public NMEAMessage createMessageFromType ( String nmeaMessageType ) throws UnknownMessageTypeException
    {
        try {
            NMEAMessage mesg = null;
            if ( ( mesg = instanceMap.get(nmeaMessageType) ) == null )
            {
                Class mesgClass = this.messageTypeMap.get(nmeaMessageType);
                if ( mesgClass == null )
                {
                    mesgClass = NMEAGenericMessage.class;
                }    
                mesg = (NMEAMessage) mesgClass.newInstance();
                
                instanceMap.put(nmeaMessageType, mesg);
            }
            return mesg;
        } catch (InstantiationException e) {
            throw new UnknownMessageTypeException ( e );
        } catch (IllegalAccessException e) {
            throw new UnknownMessageTypeException ( e );
        }
    }    
    
    
}
