package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;

public class TaxStatusInfoManagementDao {

	public boolean getStatusInfo(String uuid, String status, String comp) throws SQLException, TaxInvoiceException {
		System.out.println("[START getStatusInfo]");
		Connection con = null;
		TaxStatusInfoDao dao  = new TaxStatusInfoDao();
		boolean existYN = false;
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			existYN = dao.getStatusInfo(uuid, status, comp, con);			
			con.commit();
		} catch (SQLException e) {
			System.out.println(e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} catch (Exception e) {
			System.out.println(e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} finally {
			dbconn.closeConnection(con);
		}
		System.out.println("[END getStatusInfo]");
		return existYN;
	}
	
	public void insertStatusInfo(String uuid, String status, String comp) throws SQLException, TaxInvoiceException {
		System.out.println("[START insertStatusInfo]");
		Connection con = null;
		TaxStatusInfoDao dao  = new TaxStatusInfoDao();
		DBConnector dbconn = new DBConnector();
//		boolean YN = false;
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			
//			YN = !dao.getStatusInfo(uuid, status, comp, con);
//			if (YN) 			
//				dao.insertStatusInfo(uuid, status, comp, con);
			
			if ( status.equals("CFR") && (!dao.getStatusGroupInfo(uuid, status, "KDN", con)) ) { //현재상태그룹체크
				dao.insertStatusInfo(uuid, status+"SED", "KDN", con);
			} else if ( status.equals("DEL") 
						&& (dao.getStatusGroupInfo(uuid, "CFR", "KDN", con))  //이전상태그룹체크
						&& (!dao.getStatusGroupInfo(uuid, status, "KDN", con)) //현재상태그룹체크
						) {
				dao.insertStatusInfo(uuid, status+"SED", "KDN", con);
			}
			
			con.commit();
		} catch (SQLException e) {
			System.out.println(e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} catch (Exception e) {
			System.out.println(e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} finally {
			dbconn.closeConnection(con);
		}
		System.out.println("[END insertStatusInfo]");
//		return YN;
	}
	
    public ArrayList getKDNErrList(String startDate, String endDate, String gubun)  throws TaxInvoiceException, SQLException{
        System.out.println("[START getKDNErrList in TaxStatusInfoManagementDao]");
        Connection con = null;
        TaxStatusInfoDao dao  = new TaxStatusInfoDao();
        ArrayList data = new ArrayList();
        DBConnector dbconn = new DBConnector();
        try {           
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            
            data = dao.getKDNErrList(startDate, endDate, gubun, con);
                      
            con.commit();
        } catch (SQLException e) {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } throw new TaxInvoiceException(this, e);
        } catch (Exception e) {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        } finally {
            dbconn.closeConnection(con);
        }
        System.out.println("[END getKDNErrList in TaxStatusInfoManagementDao]");
        return data;
    }
	
	public boolean setErrStatusInfo(String uuid, String statusSM, String comp) throws SQLException, TaxInvoiceException {
		System.out.println("[START setErrStatusInfo]");
		Connection con = null;
		TaxStatusInfoDao dao  = new TaxStatusInfoDao();
		DBConnector dbconn = new DBConnector();
		boolean YN = false;
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			
			YN = dao.getStatusInfo(uuid, statusSM, comp, con);
			if (YN) 			
				dao.updateStatusInfo(uuid, statusSM, statusSM.substring(0,3)+"SED", comp, con);
			
			con.commit();
		} catch (SQLException e) {
			System.out.println(e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} catch (Exception e) {
			System.out.println(e);
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new TaxInvoiceException(this, e);
		} finally {
			dbconn.closeConnection(con);
		}
		System.out.println("[END setErrStatusInfo]");
		return YN;
	}
}
