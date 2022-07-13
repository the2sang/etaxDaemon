package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is just a helper class to make it handy to print out debug
 * statements
 */
public final class Debug {

    public static final boolean debuggingOn = false;

    private static Log log = LogFactory.getLog(Debug.class);

    static {
        new Debug();
    }

    public Debug() {
    }

    public static void print(String msg) {
        if (debuggingOn) {
            System.err.print(msg);
        }
        log.debug(msg);
    }

    public static void println(String msg) {
        if (debuggingOn) {
            System.err.println(">>" + msg);
        }
        log.debug(msg);
    }

    public static void print(Exception e, String msg) {
        print((Throwable) e, msg);
    }

    public static void print(Exception e) {
        print(e, null);
    }

    public static void print(Throwable t, String msg) {
        if (debuggingOn) {
            System.out.println("Received throwable with Message: " + t.getMessage());
            if (msg != null)
                System.out.print(msg);
            t.printStackTrace();

        }
//        log.debug(msg);
//        log.debug("  " + t);
        System.out.println(msg);
        java.lang.StackTraceElement st[] = t.getStackTrace();
        for (int i = 0; i < st.length; i++){
//            log.debug("  " + st[i].toString());
            System.out.println("  " + st[i].toString());
        }
    }

    public static void print(Throwable t) {
        print(t, null);
    }
}
