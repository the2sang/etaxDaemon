package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxCompanyVO;

public class TaxCompanyNewManagerDao {

	public TaxCompanyVO getCompInfoByBizId(String biz_id, String comp_type) throws SQLException, TaxInvoiceException{
        System.out.println("[START getLoginCompInfo]");
        Connection con = null;
        TaxCompanyNewDao dao  = new TaxCompanyNewDao();
        DBConnector dbconn = new DBConnector();
        TaxCompanyVO vo = new TaxCompanyVO();
        try {
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            vo = dao.getCompInfoByBizId(biz_id, comp_type, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        return vo;
    }
	
	public ArrayList selectCompanyList(String biz_id, String comp_name,  String comp_type) throws SQLException, TaxInvoiceException{
        System.out.println("[START getLoginCompInfo]");
        Connection con = null;
        TaxCompanyNewDao dao  = new TaxCompanyNewDao();
        DBConnector dbconn = new DBConnector();
        ArrayList data = new ArrayList();
        try {
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            data = dao.selectCompanyList(biz_id, comp_name, comp_type, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        return data;
    }
    
	public ArrayList selectPWCompanyList(String biz_id, String comp_name,  String comp_type) throws SQLException, TaxInvoiceException{
        System.out.println("[START selectPWCompanyList]");
        Connection con = null;
        TaxCompanyNewDao dao  = new TaxCompanyNewDao();
        DBConnector dbconn = new DBConnector();
        ArrayList data = new ArrayList();
        try {
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            data = dao.selectPWCompanyList(biz_id, comp_name, comp_type, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        return data;
    }	
	
	public ArrayList selectOftenCompanyList(String id) throws SQLException, TaxInvoiceException{
        System.out.println("[START getLoginCompInfo]");
        Connection con = null;
        TaxCompanyNewDao dao  = new TaxCompanyNewDao();
        DBConnector dbconn = new DBConnector();
        ArrayList data = new ArrayList();
        try {
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            data = dao.selectOftenCompanyList(id, con);
            con.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        catch (Exception e)
        {
            System.out.println(e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new TaxInvoiceException(this, e);
        }
        finally {
            dbconn.closeConnection(con);
        }
        return data;
    }
	
//	 KDN ERP연계 관련 사업자번호 관리 
	public boolean getBiznumInfo(String biznum, String comp) throws SQLException, TaxInvoiceException {
		System.out.println("[START getBiznumInfo]");
		Connection con = null;
		TaxCompanyNewDao dao  = new TaxCompanyNewDao();
		boolean adminYN = false;
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			adminYN = dao.getBiznumInfo(biznum, comp, con);
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
		System.out.println("[END getBiznumInfo]");
		return adminYN;
	}
	
	public ArrayList getBiznumInfoList(String comp) throws SQLException, TaxInvoiceException {
		System.out.println("[START getBiznumInfoList]");
		Connection con = null;
		TaxCompanyNewDao dao  = new TaxCompanyNewDao();
		ArrayList adminlist = new ArrayList();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			adminlist = dao.getBiznumInfoList(comp, con);
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
		System.out.println("[END getBiznumInfoList]");
		return adminlist;
	}
	
	public boolean insertBiznumInfo(String biznum, String comp) throws SQLException, TaxInvoiceException {
		System.out.println("[START insertBiznumInfo]");
		Connection con = null;
		TaxCompanyNewDao dao  = new TaxCompanyNewDao();
		DBConnector dbconn = new DBConnector();
		boolean YN = false;
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			
			YN = !dao.getBiznumInfo(biznum, comp, con);
			if (YN) 			
				dao.insertBiznumInfo(biznum, comp, con);
			
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
		System.out.println("[END insertBiznumInfo]");
		return YN;
	}
	
	public void deleteBiznumInfo(String biznum, String comp) throws SQLException, TaxInvoiceException {
		System.out.println("[START deleteBiznumInfo]");
		Connection con = null;
		TaxCompanyNewDao dao  = new TaxCompanyNewDao();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			dao.deleteBiznumInfo(biznum, comp, con);
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
		System.out.println("[END deleteBiznumInfo]");
		
	}
	
	
	
}
