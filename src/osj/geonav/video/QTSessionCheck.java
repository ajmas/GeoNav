package osj.geonav.video;

import quicktime.*;

public class QTSessionCheck {

    private Thread shutdownHook;
    private static QTSessionCheck instance;
    private QTSessionCheck() throws QTException {
        super();
        // init
        QTSession.open();
        // create shutdown handler
        shutdownHook = new Thread() {
                public void run() {
                    // QTSession.close();
                    QTSession.exitMovies();
                }
            };
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
    private static QTSessionCheck getInstance() throws QTException {
        if (instance == null)
            instance = new QTSessionCheck();
        return instance;
    }
    
    public static void check() throws QTException {
        // gets instance.  if a new one needs to be created,
        // it calls QTSession.open() and creates a shutdown hook
        // to call QTSession.close()
        getInstance();
    }

    public static void main (String[] args) {
        try {
            QTSessionCheck.check();
            System.exit(0);
        } catch (QTException qte) {
            qte.printStackTrace();
        }
    }

}
