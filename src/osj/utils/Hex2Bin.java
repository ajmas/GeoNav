package osj.utils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Hex2Bin
{

    public Hex2Bin()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    
    public static String removeSpaces( String text )
    {
        if ( text.indexOf(' ') > -1 )
        {
            StringBuffer strBuf = new StringBuffer();
            for ( int i=0; i<text.length(); i++ ) 
            {
                char c = text.charAt(i);
                if ( c != ' ' )
                {
                    strBuf.append(c);
                }
            }
            text = strBuf.toString();
        } 
        return text;
    }
    
    public static byte[] hexStringToBytes( String hexString )
    {
        byte[] bytes = null;
        
        //hexString = removeSpaces(hexString);        
        if ( hexString.indexOf( " " ) < 0 )
        {
            bytes = new byte[hexString.length()/2];
            for ( int i=0; i<hexString.length(); i+=2 ) 
            {
                bytes[i/2]=(byte) Integer.parseInt( hexString.substring(i,i+2), 16);
            }
        }
        else
        {
            String[] parts = hexString.split(" ");
            bytes = new byte[parts.length];
            for ( int i=0; i<parts.length; i++ ) 
            {
                bytes[i] = (byte) Integer.parseInt( parts[i], 16);
            }
        }
        return bytes;
    }
    
    public static String bytesToHexString( byte[] data, int offset, int count )
    {
        StringBuilder strBuf = new StringBuilder();
        for ( int i=0; i<data.length && i<count; i++ )
        {
            int x = data[i+offset];
            if ( x < 0 )
            {
                x += 256;
            }
            String str = Integer.toString( x, 16 );
            if ( str.length() < 2 )
            {
                strBuf.append("0");
            }
            strBuf.append(str);
            strBuf.append(" ");
        }
        if ( strBuf.length() > 0 )
        {
            strBuf.setLength( strBuf.length() - 1 );
        }
        return strBuf.toString();
    }
    
    /**
     * TODO implementation details
     *
     * @param args
     */
    public static void main( String[] args )
    {
        
        
        
        //byte[] bytes = hexStringToBytes("3C3F786D 6C207665 7273696F 6E3D2231 2E302220 656E636F 64696E67 3D225554 462D3822 3F3E3C73 74726561 6D3A7374 7265616D 2066726F 6D3D2267 6D61696C 2E636F6D 22206964 3D224344 41453842 32323346 43303745 42332220 76657273 696F6E3D 22312E30 2220786D 6C6E733A 73747265 616D3D22 68747470 3A2F2F65 74686572 782E6A61 62626572 2E6F7267 2F737472 65616D73 2220786D 6C6E733D 226A6162 6265723A 636C6965 6E74223E");
        
        byte[] bytes = hexStringToBytes(args[0]);
        
        //for ( int i=0; i< bytes.length; i++ )
        {
            try
            {
                OutputStream fout = new BufferedOutputStream(new FileOutputStream("c:\\out.dat"));
                
                fout.write(bytes);
                
                fout.close();
                
                System.out.println(new String(bytes));
            }
            catch ( Exception e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // TODO Auto-generated method stub

    }

}
