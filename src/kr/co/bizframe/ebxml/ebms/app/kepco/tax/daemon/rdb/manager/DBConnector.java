/*
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.rdb.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.run.ServerProperties;

public class DBConnector {

	  public Connection getConnection() throws Exception {
//	        String driver = "oracle.jdbc.driver.OracleDriver";	        
//	        String url = "jdbc:oracle:thin:@168.78.201.50:1521:EDIDEV9I";
//	        String userid = "EXEDITEST";
//	        String passwd = "XEDI1515";
	        
	        ServerProperties sp = ServerProperties.getInstance();
	        String driver       = sp.getProperty("CRON.JDBC.DRIVERCLASSNAME");;
	        String url          = sp.getProperty("CRON.JDBC.URL");
	        String userid       = sp.getProperty("CRON.JDBC.USERNAME");
	        String passwd       = sp.getProperty("CRON.JDBC.PASSWORD");
	       	        
	        
	        
	        Connection  conn    = null;
	        
	        try
	        {           
	            Class.forName(driver);
	            conn = DriverManager.getConnection(url, userid, passwd);            
	            
//	            System.out.println(sp.getProperty("CRON.JDBC.DRIVERCLASSNAME"));
//	            System.out.println(sp.getProperty("CRON.JDBC.URL"));
//	            System.out.println(sp.getProperty("CRON.JDBC.USERNAME"));
//	            System.out.println(sp.getProperty("CRON.JDBC.PASSWORD"));
	            
	            return conn;                
	        }
	        catch(Exception e) { 
	            e.printStackTrace();
	        }
			return conn;
	    }
	  
	  public void closeConnection(Connection con) {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException sqle) {
				System.out.println("SQLException while close Connection :" + sqle.getMessage());
			}
		}

}

*/

package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.PropertyUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxManagementDao;

/**
 *
 * @author  Young-jun Bae
 *
 */

public class DBConnector {
	
	private static Logger logger = Logger.getLogger(DBConnector.class);
	
	public Connection getConnection() throws SQLException {
		Connection con = null;
		if (CommonUtil.getProperty("TAXINVOICE.IS.USE.DBPOOL").equals("Y")) {
			con = getDBPoolConnection();
		} else {
			con = getDataSourceConnection();
		}
		return con;
	}
	//2016.08.11	
	public Connection getDBWASPoolConnection() throws SQLException {
		DataSource ds = null;
		try {
			InitialContext initial = null;
			if (ds == null) {
				System.out.println("lookup dataSource!!");
				initial = new InitialContext();
				ds = (DataSource) initial.lookup("DS_KEPCOBILL");
				System.out.println(ds);
			}
		} catch (NamingException e) {
			System.out.println("Fail to look up DataSource.");
		}
		System.out.println(ds);
		return ds.getConnection();
	}
	public Connection getDBPoolConnection() throws SQLException {
		Connection con = null;
		DBConnectionManager manager = DBConnectionManager.getInstance();
		con = DBConnectionManager.getConnection("TAXINVOICE");
		return con;
	}

	public Connection getDataSourceConnection() throws SQLException {
		// log.debug("class name" + this.getClass().getName());
		DataSource ds = null;
		try {
			InitialContext initial = null;
			if (ds == null) {
				logger.debug("lookup dataSource!!");
				initial = new InitialContext();

				logger.debug(CommonUtil.getProperty("TAXINVOICE.DATASOURCENAME"));
				ds = (DataSource) initial.lookup(CommonUtil.getProperty("TAXINVOICE.DATASOURCENAME"));
				logger.debug(ds);
			}
		} catch (NamingException e) {
			logger.debug("Fail to look up DataSource.");
		}
		logger.debug(ds);
		return ds.getConnection();
	}

	public void closeConnection(Connection con) {

		if (CommonUtil.getProperty("TAXINVOICE.IS.USE.DBPOOL").equals("Y")) {
			closeDBPoolConnection(con);
		} else {
			closeDataSourceConnection(con);
		}
	}

	public void closeDBPoolConnection(Connection con) {
		if (con != null) {
			DBConnectionManager manager = DBConnectionManager.getInstance();
			manager.freeConnection("TAXINVOICE", con);
		}
	}

	public void closeDataSourceConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException sqle) {
			logger.debug("SQLException while close Connection :" + sqle.getMessage());
		}
	}
}