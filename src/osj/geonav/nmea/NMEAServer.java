package osj.geonav.nmea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NMEAServer implements Runnable {

    private static final int DEFAULT_PORT = 9999;
    
    private int port = DEFAULT_PORT;
    private boolean run = true;
    public NMEAServer () {
        
    }
    
    public void run() {
       
        try {
            ServerSocket serverSocket = new ServerSocket ( this.port );
            
            while ( this.run ) {
                
                Socket socket = serverSocket.accept();
                
                ClientConnection connection = new ClientConnection(socket);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static class ClientConnection implements Runnable {
        
        Socket socket;
        
        public ClientConnection  ( Socket socket ) {
            this.socket = socket; 
            
            (new Thread(this)).start();
        }
        
        public void run() {
            
            // interpret client request
            // return answer
            // if everything is okay start sending data
        }
    }
}
