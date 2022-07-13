
/*
 * Copyright (c) 1998 by Gefion software.
 *
 * Permission to use, copy, and distribute this software for
 * NON-COMMERCIAL purposes and without fee is hereby granted
 * provided that this copyright notice appears in all copies.
 *
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.ServerProperties;
import kr.co.bizframe.ebxml.ebms.message.handler.MSHException;

import org.apache.log4j.Logger;

/**
 * This class is a Singleton that provides access to one or many
 * connection pools defined in a Property file. A client gets
 * access to the single instance through the static getInstance()
 * method and can then check-out and check-in connections from a pool.
 * When the client shuts down it should call the release() method
 * to close all open connections and do other clean up.
 */
public class DBConnectionManager {
    static private DBConnectionManager instance;       // The single instance
    static private int clients;
    
    private static Vector drivers = new Vector();
    private static PrintWriter log;
    private static Hashtable pools = new Hashtable();

    private static Logger logger = Logger.getLogger(DBConnectionManager.class);	
    
    /**
     * Returns the single instance, creating one if it's the
     * first time this method is called.
     *
     * @return DBConnectionManager The single instance.
     */
    static synchronized public DBConnectionManager getInstance() {
        try {
            if (instance == null) {
                instance = new DBConnectionManager();
            }
            clients++;
            return instance;
        }catch(Exception e){};
        return null;
    }
    
    static synchronized public DBConnectionManager initPool(Properties props) 
    throws MSHException {

        //        if (instance == null) {
        instance = new DBConnectionManager(props);
        //        }
        clients++;
        return instance;
    }
    
    /**
     * A private constructor since this is a Singleton
     */
    private DBConnectionManager() throws MSHException {
        init(ServerProperties.getInstance());
    }
    
    private DBConnectionManager(Properties props) throws MSHException {
        init(props);
    }
    
    /**
     * Returns a connection to the named pool.
     *
     * @param name The pool name as defined in the properties file
     * @param con The Connection
     */
    public static void freeConnection(String name, Connection con) {
        
        DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        
        if (pool != null) {
            pool.freeConnection(con);
        }
    }
    
    /**
     * Returns an open connection. If no one is available, and the max
     * number of connections has not been reached, a new connection is
     * created.
     *
     * @param name The pool name as defined in the properties file
     * @return Connection The connection or null
     */
    public static Connection getConnection(String name) {
        try {
            DBConnectionPool pool = (DBConnectionPool) pools.get(name);
            if (pool != null) {
                return pool.getConnection();
            }
        }catch(Exception e) {
            logger.debug(e);
        }        
        return null;
    }
    
    /**
     * Returns an open connection. If no one is available, and the max
     * number of connections has not been reached, a new connection is
     * created. If the max number has been reached, waits until one
     * is available or the specified time has elapsed.
     *
     * @param name The pool name as defined in the properties file
     * @param time The number of milliseconds to wait
     * @return Connection The connection or null
     */
    public static Connection getConnection(String name, long time) {
        try {
            DBConnectionPool pool = (DBConnectionPool) pools.get(name);
            if (pool != null) {
                return pool.getConnection(time);
            }
        }catch(Exception e) {
            logger.debug(e);
        }
        
        return null;
    }
    
    /**
     * Closes all open connections and deregisters all drivers.
     */
    public static synchronized void release() throws MSHException {
        
        logger.debug("[==>,DBConnectionManater:release] Num of Pools: " + pools.size());
/*        
        // Wait until called by the last client
        if (--clients != 0) {
            return;
        }
*/        
        Enumeration allPools = pools.elements();
        int count = 0;
        while (allPools.hasMoreElements()) {            
            DBConnectionPool pool = (DBConnectionPool)allPools.nextElement();
            pool.release();            
            logger.debug("[===,DBConnectionManater:release] "+count+"'th POOL release ... ");
        }
        pools = null;
        pools = new Hashtable();
        
        Enumeration allDrivers = drivers.elements();
        while (allDrivers.hasMoreElements()) {
            Driver driver = (Driver) allDrivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.debug("[===,DBConnectionManater:release] Deregistered JDBC driver " + driver.getClass().getName());
            }
            catch (SQLException e) {
                logger.debug("[===,DBConnectionManater:release] Can't deregister JDBC driver: " + driver.getClass().getName());
            }
        }
        logger.debug("[<==,DBConnectionManater:release]");
    }
    
    /**
     * Creates instances of DBConnectionPool based on the properties.
     * A DBConnectionPool can be defined with the following properties:
     * <PRE>
     * &lt;poolname&gt;.url         The JDBC URL for the database
     * &lt;poolname&gt;.user        A database user (optional)
     * &lt;poolname&gt;.password    A database user password (if user specified)
     * &lt;poolname&gt;.maxconn     The maximal number of connections (optional)
     * </PRE>
     *
     * @param props The connection pool properties
     */
    private void createPools(Properties props) throws MSHException {
        logger.debug("[==>,DBConnectionManater:createPools] ");
        
        String dbName    = props.getProperty("CRON.JDBC.DBNAME");
        String poolName  = props.getProperty("CRON.JDBC.POOLNAME");
        String url       = props.getProperty("CRON.JDBC.URL");
        String user      = props.getProperty("CRON.JDBC.USERNAME");
        String password  = props.getProperty("CRON.JDBC.PASSWORD");
        String maxconn   = props.getProperty("CRON.JDBC.MAXACTIVE");
//        String conType   = props.getProperty("TAXINVOICE.JDBC.TYPE");
        
        int max;
        logger.debug("[===,DBConnectionManater:createPools] Initializing POOL [POOLNAME]: " + poolName);
        logger.debug("[==>,DBConnectionManater:createPools] Num of ("+dbName+")Connection : " + maxconn);
        try {
            max = Integer.valueOf(maxconn).intValue();
        }
        catch (NumberFormatException e) {
            throw new MSHException(MSHException.MSH_DATABASE_CONNECTION_NUMBER_FAILURE, "POOL_NUM : '" + maxconn + "' is not number");
        }
        logger.debug("[===,DBConnectionManater:createPools] CreatePool : " + url + "," + user + "," + password );
        DBConnectionPool pool = null;
        
        if(!"DATASOURCE".equals(dbName)){
            pool = new DBConnectionPool(poolName, url, user, password, max);
        }else{
            pool = new DBConnectionPool();
        }
        
        if(poolName == null || poolName.equals(""))
            throw new MSHException(MSHException.MSH_DATABASE_POOLNAME_NULL, "POOL_NAME is NULL");
        
        if(pools.get(poolName)!=null) {
           logger.debug("[===,DBConnectionManater:createPools] '" + poolName +"' is exists ... and destroy this...]");
           DBConnectionPool temp_pool = (DBConnectionPool) pools.get(poolName);
           temp_pool.release();
        }
        if(pool.initPool())	{		            
            pools.put(poolName, pool);            
        }
        logger.debug("[<==,DBConnectionManater:createPools]");
    }
    
    /**
     * Loads properties and initializes the instance with its values.
     */
    private void init() throws MSHException {
        
        ServerProperties sp = ServerProperties.getInstance();
        
        loadDrivers(sp);
        createPools(sp);
    }
    
    private void init(Properties props) throws MSHException {
        
        ServerProperties sp = ServerProperties.getInstance();
        String url  = props.getProperty("CRON.JDBC.DBNAME");
        
       // if(!url.equals("DATASOURCE"))
            loadDrivers(props);
        createPools(props);
    }
    
    /**
     * Loads and registers all JDBC drivers. This is done by the
     * DBConnectionManager, as opposed to the DBConnectionPool,
     * since many pools may share the same driver.
     *
     * @param props The connection pool properties
     */
    private void loadDrivers(Properties props) throws MSHException {
        String driverClasses = props.getProperty("CRON.JDBC.DRIVERCLASSNAME");
//        System.out.println(driverClasses);
        if(driverClasses == null) logger.debug("[<=>,DBConnectionManater:loadDrivers] Registering JDBC driver is NULL ");
        
        StringTokenizer st = new StringTokenizer(driverClasses);
        while (st.hasMoreElements()) {
            String driverClassName = st.nextToken().trim();
            try {
                Driver driver = (Driver)Class.forName(driverClassName).newInstance();
                DriverManager.registerDriver(driver);
                drivers.addElement(driver);
                logger.debug("[<=>,DBConnectionManater:loadDrivers] JDBC driver " + driverClassName);
                
                System.out.println("driver is "+driver);
            }
            catch (ClassNotFoundException e) {
                throw new MSHException(MSHException.MSH_DATABASE_DRIVER_NOT_FOUND, "Can't found [JDBC DRIVER:" + driverClasses + "]" );
            }
            catch (InstantiationException  e) {
                throw new MSHException(MSHException.MSH_DATABASE_DRIVER_INSTANTIATION_FAILURE, "Can't instantiatiate [JDBC DRIVER:" + driverClasses + "]" );
            }
            catch (IllegalAccessException  e) {
                throw new MSHException(MSHException.MSH_DATABASE_DRIVER_ACCESS_FAILURE, "Can't access [JDBC DRIVER:" + driverClasses + "]" );
            }
            catch (SQLException  e) {
                throw new MSHException(MSHException.MSH_DATABASE_SQL_EXCEPTION, "SQLException while loading [JDBC DRIVER:" + driverClasses + "]" + e.getMessage());
            }
        }
    }
    
    
    /**
     * This inner class represents a connection pool. It creates new
     * connections on demand, up to a max number if specified.
     * It also makes sure a connection is still open before it is
     * returned to a client.
     */
    class DBConnectionPool {

        private int checkedOut;
        private Vector freeConnections = new Vector();
        private int maxConn;
        private String name;
        private String password;
        private String URL;
        private String user;
        
        /**
         * Creates new connection pool.
         *
         * @param name The pool name
         * @param URL The JDBC URL for the database
         * @param user The database user, or null
         * @param password The database user password, or null
         * @param maxConn The maximal number of connections, or 0
         *   for no limit
         */
        public DBConnectionPool(String name, String URL, String user, String password, int maxConn) {
           this.name = name;
           this.URL = URL;
           this.user = user;
           this.password = password;
           this.maxConn = maxConn;
        }
        
        public DBConnectionPool() {}
        
        public boolean initPool() throws MSHException {
           for(int i=0; i<maxConn; i++)	{
               Connection con = newConnection();
               if(con == null) {return false;}
               freeConnections.addElement(con);
               logger.debug("[<=>,DBConnectionPool:initPool] " + (i+1) + " nd Connection Created ...");
           }
           return true;
        }
        
        /**
         * Checks in a connection to the pool. Notify other Threads that
         * may be waiting for a connection.
         *
         * @param con The connection to check in
         */
        public synchronized void freeConnection(Connection con) {
           // Put the connection at the end of the Vector
           int before = freeConnections.size();
           freeConnections.addElement(con);
           int after = freeConnections.size();
           logger.debug("[<=>,DBConnectionPool:freeConnection] Before:(" + before + "), After:("+after+")");
           checkedOut--;
           notifyAll();            
        }
        
        /**
         * Checks out a connection from the pool. If no free connection
         * is available, a new connection is created unless the max
         * number of connections has been reached. If a free connection
         * has been closed by the database, it's removed from the pool
         * and this method is called again recursively.
         */
        /*
         public synchronized Connection getConnection() throws MSHException {
         // 2004.09.09 Bae modified
          checkedOut = 0;
          // 2004.09.09 end
           Connection con = null;
           logger.debug("getConnection() - Connection size(before) : " + freeConnections.size());
           if (freeConnections.size() > 0) {
           // Pick the first Connection in the Vector
            // to get round-robin usage
             con = (Connection) freeConnections.firstElement();
             freeConnections.removeElementAt(0);
             try {
             if (con.isClosed()) {
             logger.debug("Removed bad connection from [Pool:" + name + "]");
             // Try again recursively
              con = newConnection();
              freeConnections.addElement(con);
              con = (Connection) freeConnections.firstElement();
              freeConnections.removeElementAt(0);
              }
              }
              catch (SQLException e) {                    
              logger.debug("Removed bad connection from " + name);
              // Try again recursively
               con = getConnection();
               }
               }
               else if (maxConn == 0 || checkedOut < maxConn) {
               con = newConnection();
               }
               if (con != null) {
               checkedOut++;
               }
               logger.debug("getConnection() - Connection size(after) : " + freeConnections.size());
               return con;
               }
               */
        public synchronized Connection getConnection() throws MSHException {
            // 2004.09.09 Bae modified
            checkedOut = 0;
            // 2004.09.09 end  
            Connection con = null;
            int before = freeConnections.size();
            if (freeConnections.size() > 0) {
                // Pick the first Connection in the Vector
                // to get round-robin usage
                con = (Connection) freeConnections.firstElement();
                freeConnections.removeElementAt(0);
                try {
                    if (con.isClosed()) {
                        logger.debug("[<=>,DBConnectionPool:getConnection] Removed Bad Connection from " + name + " and Get New Connection..");
                        con = newConnection();
                    }
                }
                catch (SQLException e) {                    
                    logger.debug("[<=>,DBConnectionPool:getConnection] Removed Bad Connection from " + name + e.toString());
                    // Try again recursively
                    // change getConnection() ==> newConnection();
//                        con = getConnection();
                        con = newConnection();
                }
            }
            else if (maxConn == 0 || checkedOut < maxConn) {
               
                con = newConnection();
            }
            if (con != null) {
                checkedOut++;
            }

            int after = freeConnections.size();
            logger.debug("[<=>,DBConnectionPool:getConnection]  Before:(" + before + "), After:(" + after + ")" );
            return con;
        }
        
        /**
         * Checks out a connection from the pool. If no free connection
         * is available, a new connection is created unless the max
         * number of connections has been reached. If a free connection
         * has been closed by the database, it's removed from the pool
         * and this method is called again recursively.
         * <P>
         * If no connection is available and the max number has been 
         * reached, this method waits the specified time for one to be
         * checked in.
         *
         * @param timeout The timeout value in milliseconds
         */
        public synchronized Connection getConnection(long timeout) throws MSHException {
            long startTime = new Date().getTime();
            Connection con;
            while ((con = getConnection()) == null) {
                try {
                    wait(timeout);
                }
                catch (InterruptedException e) {}
                if ((new Date().getTime() - startTime) >= timeout) {
                    // Timeout has expired
                    return null;
                }
            }
            return con;
        }
        
        /**
         * Closes all available connections.
         */
        public synchronized void release() throws MSHException {
            Enumeration allConnections = freeConnections.elements();
            while (allConnections.hasMoreElements()) {
                Connection con = (Connection) allConnections.nextElement();
                try {
                    con.close();
                    logger.debug("Closed connection for pool " + name);
                }
                catch (SQLException e) {
                    throw new MSHException(MSHException.MSH_DATABASE_SQL_EXCEPTION, "Can't close connection for [POOL:" + name + "]");
                }
            }
            freeConnections.removeAllElements();
        }
        
        /**
         * Creates a new connection, using a userid and password
         * if specified.
         */
        private Connection newConnection() throws MSHException {
            
            Connection con      = null;
            ServerProperties sp = ServerProperties.getInstance();
            String dbName       = sp.getProperty("CRON.JDBC.DBNAME");
//            String jndiName     = sp.getProperty("CRON.JDBC.JNDINAME");
	        
            	if(!dbName.equals("DATASOURCE")){
                	try{
                		
		                if (user == null) {
		                    con = DriverManager.getConnection(URL);
		                }else {
		                	con = DriverManager.getConnection(URL, user, password);
		                }
                	}catch (SQLException e) {
	                    throw new MSHException(MSHException.MSH_DATABASE_SQL_EXCEPTION, 
	                                          "Can't create a new connection for [URL:" + URL + ",USER:" + user + ",PASSWD:" + password +"]");
	                }catch (Exception e){
	                	logger.debug(e); 
	                }
//	            }else{
//                    try {
//                        InitialContext initContext = new javax.naming.InitialContext();
//                        //Context envContext = (Context) initContext.lookup("java:/comp/env");
//    	                DataSource ds = (DataSource) initContext.lookup(jndiName);
//    	                con = ds.getConnection();
//                    } catch (NamingException e1) {
//                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
//                    } catch (SQLException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
	            }
                 
            return con;
        }
    }
}
