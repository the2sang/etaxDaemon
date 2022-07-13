package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.util.Properties;

/**
 * @author Young-jun Bae
 * 
 */

public class ServerProperties extends Properties {

    private static ServerProperties instance = null;

    public static ServerProperties getInstance() {
    	if (instance == null) {
            instance = new ServerProperties();
        }
        return instance;
    }

    public static void loadProperty() {
        try {
            System.out.println("[ServerProperties] PropertyUtil.loadProperty() ... ");
            
            String path = System.getProperty("taxinvoice.config");
            System.out.println("path::::::::::::::::::::::::::::::::::>"+path);
            PropertyUtil.loadProperty(path);
//            PropertyUtil.loadProperty();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadProperty(String path) {
        try {
            System.out.println("[ServerProperties] PropertyUtil.loadProperty() ... ");
            PropertyUtil.loadProperty(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void storeProperty() {
        try {
            System.out.println("[ServerProperties] PropertyUtil.storeProperty() ... ");
            PropertyUtil.storeProperty();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changePropertyPermision() {
        try {
            System.out.println("[ServerProperties] PropertyUtil.changePropertyPermision() ... ");
            PropertyUtil.changePropertyPermision();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Empty constructor is called for dynamic loaded classes
     */
    private ServerProperties() {
    }

}
