package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.PropertyUtil;

/**
 *
 * @author  Young-jun Bae
 *
 */

public class DBConnectionUserInfo {
	public Connection getConnection() throws SQLException {
		Connection con = null;
		if (CommonUtil.getProperty("TAXINVOICE.IS.USE.DBPOOL").equals("Y")) {
			con = getDBPoolConnection();
		} else {
			con = getDataSourceConnection();
		}
		return con;
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
				System.out.println("lookup dataSource!!");
				initial = new InitialContext();

				System.out.println(CommonUtil.getProperty("TAXINVOICE.DATASOURCENAME_TP"));
				ds = (DataSource) initial.lookup(CommonUtil.getProperty("TAXINVOICE.DATASOURCENAME_TP"));
				System.out.println(ds);
			}
		} catch (NamingException e) {
			System.out.println("Fail to look up DataSource.");
		}
		System.out.println(ds);
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
			manager.freeConnection("MSH", con);
		}
	}

	public void closeDataSourceConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException sqle) {
			System.out.println("SQLException while close Connection :" + sqle.getMessage());
		}
	}
}

/*
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.rdb.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.common.TaxInvoiceException;

public class DBConnectionUserInfo {

	  public Connection getConnection() throws TaxInvoiceException {
	        String driver = "oracle.jdbc.driver.OracleDriver";
	        String url = "jdbc:oracle:thin:@168.78.201.12:1521:KEBS2";
	        String userid = "EBIZ_EX";
	        String passwd = "EBIZ_EX";
	        Connection  conn    = null;
	        
	        try
	        {           
	            Class.forName(driver);
	            conn = DriverManager.getConnection(url, userid, passwd);                
	            return conn;                
	        }
	        catch(Exception e) { 
	            e.printStackTrace();
	            throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "SMS DB서버 CONNECTION 생성시 에러 ");
	        }
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