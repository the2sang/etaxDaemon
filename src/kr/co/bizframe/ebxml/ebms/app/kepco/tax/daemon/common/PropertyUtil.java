package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Vector;


public class PropertyUtil {

    public static final String PROPS_FILENAME = "poller.properties";

    public static void loadProperty(String Path) {
    	// E:/test/exam/TreadTest/lib/
        try {
            ServerProperties serverProperties = ServerProperties.getInstance();
            System.out.println("user.dir:" +Path + PROPS_FILENAME);
            FileInputStream is = new FileInputStream(Path + PROPS_FILENAME);
            serverProperties.load(is);
            is.close();
        } catch (Exception e) {
            System.err.println("Exception occurs while doing loadProperty()..." + e);
        }
    }
    
    public static void loadProperty() {
        try {
            ServerProperties serverProperties = ServerProperties.getInstance();
            java.net.URL url = ServerProperties.class.getResource("ServerProperties.class");
            String webinf_path = getWebInfFilePath(url.getPath());
            System.out.println("url.getFile():" + url.getFile());
//            System.out.println("url.getFile():" + url.());
            System.out.println("root path:" + webinf_path);
            FileInputStream is = new FileInputStream(webinf_path + PROPS_FILENAME);
            serverProperties.load(is);
            is.close();
        } catch (Exception e) {
            System.err.println("Exception occurs while doing loadProperty()..." + e);
        }
    }

    public static void storeProperty() {
        try {
            ServerProperties serverProperties = ServerProperties.getInstance();
            java.net.URL url = ServerProperties.class.getResource("ServerProperties.class");
            String webinf_path = getWebInfFilePath(url.getPath());
            System.out.println(webinf_path);
            String del_filename = PROPS_FILENAME;

            Vector v = getKeysandValues(serverProperties);
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(webinf_path + PROPS_FILENAME)));
            for (int i = 0; i < v.size(); i++)
                out.write((String) v.elementAt(i));

            // FileOutputStream fo = new
            // FileOutputStream(webinf_path+PROPS_FILENAME);
            // serverProperties.store(fo, "EBMS CONFIGURATIONS MODIFIED AT ...
           // ");
            out.close();

        } catch (Exception e) {
            System.out.println("Exception occurs while doing saveProperty()..." + e);
        }
    }

    public static void changePropertyPermision() {
        try {
            ServerProperties serverProperties = ServerProperties.getInstance();
            java.net.URL url = ServerProperties.class.getResource("ServerProperties.class");
            String webinf_path = getWebInfFilePath(url.getPath());
            setExecuteMode(webinf_path);
        } catch (Exception e) {
            System.out.println("Exception occurs while doing changePropertyPermision()..." + e);
        }
    }

    public static String getWebInfFilePath(String path) {

        if (path == null)
            return path;

        // ���� ���α׷��� ȣ���Ѵ�.
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/')
                sb.append(File.separator);
            else
                sb.append(path.charAt(i));
        }
        path = sb.toString();
        if (path.indexOf("file:") != -1) {
            path = path.substring(path.indexOf("file:") + 6, path.length());
        }

        String os_name = System.getProperty("os.name");
        if (os_name.toUpperCase().indexOf("WINDOW") == -1) {
            path = File.separator + path;
        }

        if (path.indexOf("lib") != -1) {
            path = path.substring(0, path.indexOf("lib"));
        }
        System.out.println("[PropertyUtil][getWebInfFilePath] taxinvoice.properties Path : " + path + " (os_name:" + os_name + ")");
        return path;
    }

    public static void setExecuteMode(String file) {
        String os_name = System.getProperty("os.name");
        System.out.println("[JarUtil][setExecuteMode] OS.NAME : " + os_name);
        if (os_name.toUpperCase().indexOf("WINDOW") == -1) {
            try {
                System.out.println("********** Execute [ chmod 744 " + file + " ]");
                // ���� ���α׷��� ȣ���Ѵ�.
                Runtime rt1 = Runtime.getRuntime();
                String envs[] = null;

                rt1.exec("chmod 744 " + file, envs);

                // ��ɾ ����ɶ����� �ð��� ���� �ʿ��ϴ�.
                // ���� ��� sleep �Ѵ�...
                Thread.sleep(80);

            } catch (Exception e) {
                System.out.println("[JarUtil][setExecuteMode] Cannot execute [ chmod 744 " + file + " ]");
            }
        }
    }

    private static Vector getKeysandValues(ServerProperties props) {
        Vector v = new Vector();

        Calendar cal = Calendar.getInstance();
        // cal.setTimeInMillis(System.currentTimeMillis());
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // sdf.setCalendar(cal);

        // v.addElement("#Modified At ... " + sdf.format(cal.getTime()) +
        // "\n\n");
        v.addElement("#Modified At ... " + "\n\n");

        v.addElement("#MSH ADMINISTRATOR ###################################################\n");
        v.addElement("MSH.ADMINISTRATOR.PASSWD=" + props.getProperty("MSH.ADMINISTRATOR.PASSWD", " ") + "\n");
        v.addElement("#END MSH ADMINITRATOR ################################################\n\n");

        v.addElement("#MSI AUTHENTICATE  ###################################################\n");
        v.addElement("MSH.MSI.AUTHENTICATE.DISABLED=" + props.getProperty("MSH.MSI.AUTHENTICATE.DISABLED", " ") + "\n");
        v.addElement("#END MSI AUTHENTICATE ################################################\n\n");

        v.addElement("#MESSAGE CONSUMER ####################################################\n");
        v.addElement("# MSH.CONSUMER.TYPE => LOCAL or REMOTE" + "################################\n");
        v.addElement("MSH.CONSUMER.TYPE=" + props.getProperty("MSH.CONSUMER.TYPE", " ") + "\n");
        v.addElement("MSH.CONSUMER.LOCAL.TYPE=" + props.getProperty("MSH.CONSUMER.LOCAL.TYPE", " ") + "\n");
        v.addElement("MSH.CONSUMER.LOCAL.JAVA.CLASSNAME=" + props.getProperty("MSH.CONSUMER.LOCAL.JAVA.CLASSNAME", " ") + "\n");
        v.addElement("MSH.CONSUMER.REMOTE.DAEMON.TYPE=" + props.getProperty("MSH.CONSUMER.REMOTE.DAEMON.TYPE", " ") + "\n");
        v.addElement("MSH.CONSUMER.REMOTE.DAEMON.IP=" + props.getProperty("MSH.CONSUMER.REMOTE.DAEMON.IP", " ") + "\n");
        v.addElement("MSH.CONSUMER.REMOTE.DAEMON.PORT=" + props.getProperty("MSH.CONSUMER.REMOTE.DAEMON.PORT", " ") + "\n");
        v.addElement("#END MESSAGE CONSUMER ################################################\n\n");

        v.addElement("#LOG #################################################################\n");
        v.addElement("MSH.LOG.VERBOSE=" + props.getProperty("MSH.LOG.VERBOSE", " ") + "\n");
        v.addElement("MSH.LOG.FILE.LEVEL=" + props.getProperty("MSH.LOG.FILE.LEVEL", " ") + "\n");
        v.addElement("MSH.LOG.FILE.DIR=" + props.getProperty("MSH.LOG.FILE.DIR", " ") + "\n");
        v.addElement("MSH.LOG.FILE.FILENAME=" + props.getProperty("MSH.LOG.FILE.FILENAME", " ") + "\n");
        v.addElement("MSH.LOG.FILE.MAXSIZE=" + props.getProperty("MSH.LOG.FILE.MAXSIZE", " ") + "\n");
        v.addElement("MSH.LOG.CONSOLE.LEVEL=" + props.getProperty("MSH.LOG.CONSOLE.LEVEL", " ") + "\n");
        v.addElement("#END LOG #############################################################\n\n");

        v.addElement("\n");
        v.addElement("#HTTP ################################################################\n");
        v.addElement("MSH.HTTP.CONNECTION.TIMEOUT=" + props.getProperty("MSH.HTTP.CONNECTION.TIMEOUT", " ") + "\n");
        v.addElement("MSH.HTTP.TIMEOUT=" + props.getProperty("MSH.HTTP.TIMEOUT", " ") + "\n");
        v.addElement("#END HTTP ############################################################\n\n");

        v.addElement("\n");
        v.addElement("#SERVER ################################################################\n");
        v.addElement("MSH.SERVER.IP=" + props.getProperty("MSH.SERVER.IP", " ") + "\n");
        v.addElement("MSH.SERVER.PORT=" + props.getProperty("MSH.SERVER.PORT", " ") + "\n");
        v.addElement("#END HTTP ############################################################\n\n");

        v.addElement("#POP3 ################################################################\n");
        v.addElement("MSH.POP3.ENABLED=" + props.getProperty("MSH.POP3.ENABLED", " ") + "\n");
        v.addElement("MSH.POP3.HOST=" + props.getProperty("MSH.POP3.HOST", " ") + "\n");
        v.addElement("MSH.POP3.PORT=" + props.getProperty("MSH.POP3.PORT", " ") + "\n");
        v.addElement("MSH.POP3.FOLDERNAME=" + props.getProperty("MSH.POP3.FOLDERNAME", " ") + "\n");
        v.addElement("MSH.POP3.USERNAME=" + props.getProperty("MSH.POP3.USERNAME", " ") + "\n");
        v.addElement("MSH.POP3.PASSWORD=" + props.getProperty("MSH.POP3.PASSWORD", " ") + "\n");
        v.addElement("MSH.POP3.INTERVAL=" + props.getProperty("MSH.POP3.INTERVAL", " ") + "\n");
        v.addElement("#END POP3 ###########################################################\n\n");

        v.addElement("#SMTP ###############################################################\n");
        v.addElement("MSH.SMTP.HOST=" + props.getProperty("MSH.SMTP.HOST", " ") + "\n");
        v.addElement("MSH.SMTP.USERNAME=" + props.getProperty("MSH.SMTP.USERNAME", " ") + "\n");
        v.addElement("MSH.SMTP.PASSWORD=" + props.getProperty("MSH.SMTP.PASSWORD", " ") + "\n");
        v.addElement("#END SMTP ###########################################################\n\n");

        v.addElement("#KEYSTORE ###########################################################\n");
        v.addElement("MSH.KEYSTORE.TYPE=" + props.getProperty("MSH.KEYSTORE.TYPE", " ") + "\n");
        v.addElement("MSH.KEYSTORE.FILE=" + props.getProperty("MSH.KEYSTORE.FILE", " ") + "\n");
        v.addElement("MSH.KEYSTORE.PASSWD=" + props.getProperty("MSH.KEYSTORE.PASSWD", " ") + "\n");
        v.addElement("#END KEYSTORE #######################################################\n\n");

        v.addElement("#XML DSIG ###########################################################\n");
        v.addElement("MSH.SIGN.PRIVATEKEY.ALIAS=" + props.getProperty("MSH.SIGN.PRIVATEKEY.ALIAS", " ") + "\n");
        v.addElement("MSH.SIGN.PRIVATEKEY.PASSWD=" + props.getProperty("MSH.SIGN.PRIVATEKEY.PASSWD", " ") + "\n");
        v.addElement("#END XML DSIG #######################################################\n\n");

        v.addElement("#MSH MONITOR #########################################################\n");
        v.addElement("MSH.MONITOR.ENABLED=" + props.getProperty("MSH.MONITOR.ENABLED", " ") + "\n");
        v.addElement("MSH.MONITOR.IP=" + props.getProperty("MSH.MONITOR.IP", " ") + "\n");
        v.addElement("MSH.MONITOR.PORT=" + props.getProperty("MSH.MONITOR.PORT", " ") + "\n");
        v.addElement("#END MSH MONITOR #####################################################\n\n");

        v.addElement("#MSH PAGING #########################################################\n");
        v.addElement("MSH.PAGE.COUNT=" + props.getProperty("MSH.PAGE.COUNT", " ") + "\n");
        v.addElement("MSH.PAGE.ITEM.COUNT=" + props.getProperty("MSH.PAGE.ITEM.COUNT", " ") + "\n");
        v.addElement("#END MSH MSH PAGING #####################################################\n\n");

        v.addElement("#JDBC ###############################################################\n");
        v.addElement("MSH.JDBC.DBNAME=" + props.getProperty("MSH.JDBC.DBNAME", " ") + "\n");
        v.addElement("MSH.JDBC.DRIVERCLASSNAME=" + props.getProperty("MSH.JDBC.DRIVERCLASSNAME", " ") + "\n");
        v.addElement("MSH.JDBC.URL=" + props.getProperty("MSH.JDBC.URL", " ") + "\n");
        v.addElement("MSH.JDBC.USERNAME=" + props.getProperty("MSH.JDBC.USERNAME", " ") + "\n");
        v.addElement("MSH.JDBC.PASSWORD=" + props.getProperty("MSH.JDBC.PASSWORD", " ") + "\n");
        v.addElement("MSH.JDBC.MAXACTIVE=" + props.getProperty("MSH.JDBC.MAXACTIVE", " ") + "\n");
        v.addElement("MSH.JDBC.MAXIDLE=" + props.getProperty("MSH.JDBC.MAXIDLE", " ") + "\n");
        v.addElement("MSH.JDBC.MAXWAIT=" + props.getProperty("MSH.JDBC.MAXWAIT", " ") + "\n");
        v.addElement("MSH.JDBC.POOLNAME=" + props.getProperty("MSH.JDBC.POOLNAME", " ") + "\n");
        v.addElement("#END POP3 ###########################################################\n\n");

        return v;
    }

   
   
}
