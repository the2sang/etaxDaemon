package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnectionUserInfo;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxPersonVO;

public class TaxPersonNewManagerDao {

	
	public Hashtable getOutterInfoForExcelPublish(String bizNO, Hashtable compHt) throws SQLException, TaxInvoiceException {
		System.out.println("[START getCompUserList]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			compHt = dao.getOutterInfoForExcelPublish(bizNO, compHt, con);
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

		System.out.println("[END getCompUserList]");
		return compHt;
	}
	
	public ArrayList getOutCompUserByBizId(String comp_id) throws SQLException, TaxInvoiceException {
		System.out.println("[START getCompUserList]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.getOutCompUserByBizId(comp_id, con);
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

		System.out.println("[END getCompUserList]");
		return data;
	}

	public ArrayList selectHanjunUserByName(String name) throws SQLException, TaxInvoiceException {
		System.out.println("[START selectHanjunUserByName]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.selectHanjunUserByName(name, con);
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

		System.out.println("[END selectHanjunUserByName]");
		return data;
	}
	
	public ArrayList selectHanjunUserById(String id) throws SQLException, TaxInvoiceException {
		System.out.println("[START selectHanjunUserByName]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.selectHanjunUserById(id, con);
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

		System.out.println("[END selectHanjunUserByName]");
		return data;
	}

	public ArrayList selectUserByName(String name, String comp_code) throws SQLException, TaxInvoiceException {
		System.out.println("[START selectUserByName]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.selectUserByName(name, comp_code, con);
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

		System.out.println("[END selectUserByName]");
		return data;
	}	
	
	public ArrayList selectOutterUserByName(String name) throws SQLException, TaxInvoiceException {
		System.out.println("[START selectOutterUserByName]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.selectOutterUserByName(name, con);
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

		System.out.println("[END selectOutterUserByName]");
		return data;
	}

	public void saveOftenUser(TaxPersonVO vo) throws SQLException, TaxInvoiceException {
		System.out.println("[START saveOftenUser]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			if (dao.isExistOftenUser(vo, con).equals("N"))
				dao.insertOftenUser(vo, con);
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
		System.out.println("[END saveOftenUser]");
	}

	public void saveOftenComp(TaxPersonVO vo, String comp_code) throws SQLException, TaxInvoiceException {
		System.out.println("[START saveOftenUser]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			if (dao.isExistOftenUser(vo, con).equals("N"))
				dao.insertOftenComp(vo, comp_code, con);
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
		System.out.println("[END saveOftenUser]");
	}
	
	public ArrayList getSelectOftenUserList(String biz_id, String buyerCompCode) throws SQLException, TaxInvoiceException {
		System.out.println("[START getSelectOftenUserList]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		ArrayList data = new ArrayList();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.getSelectOftenUserList(biz_id, buyerCompCode, con);
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

		System.out.println("[END getSelectOftenUserList]");
		return data;
	}

	public void deleteOftenUser(TaxPersonVO vo) throws SQLException, TaxInvoiceException {
		System.out.println("[START deleteOftenUser]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			dao.deleteOftenUser(vo, con);
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
		System.out.println("[END deleteOftenUser]");
	}

	public TaxPersonVO confirmOftenUser(TaxPersonVO vo) throws SQLException, TaxInvoiceException {
		System.out.println("[START confirmOftenUser]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		TaxPersonVO data = new TaxPersonVO();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			String id = dao.getPersonIdByEmailName(vo.getName(), vo.getEmail(), con);
			System.out.println("confirm user id:" + id);
			data = dao.selectPersonById(id, "K", con);
			System.out.println("confirm user id:" + data.getEmail());
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
		System.out.println("[END confirmOftenUser]");
		return data;
	}

	public TaxPersonVO selectPersonById(String id, String comp_type) throws SQLException, TaxInvoiceException {
		System.out.println("[START confirmOftenUser]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		TaxPersonVO data = new TaxPersonVO();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.selectPersonById(id, comp_type, con);
			System.out.println("confirm user id:" + id);
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
		System.out.println("[END confirmOftenUser]");
		return data;
	}

	public TaxPersonVO selectPersonByIdnCompId(String comp_id, String id, String comp_type) throws SQLException, TaxInvoiceException {
		System.out.println("[START confirmOftenUser]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		TaxPersonVO data = new TaxPersonVO();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.selectPersonByIdnCompId(comp_id, id, comp_type, con);
			System.out.println("confirm user id:" + id);
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
		System.out.println("[END confirmOftenUser]");
		return data;
	}	
	
	public TaxPersonVO selectExUserByLoingId(String id) throws SQLException, TaxInvoiceException {
		System.out.println("[START confirmOftenUser]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		TaxPersonVO data = new TaxPersonVO();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.selectExUserByLoingId(id, con);
			System.out.println("confirm user id:" + id);
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
		System.out.println("[END confirmOftenUser]");
		return data;
	}
	
	
	public TaxPersonVO selectPersonByLoginID(String id, String comp_type) throws SQLException, TaxInvoiceException {
		System.out.println("[START confirmOftenUser]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		TaxPersonVO data = new TaxPersonVO();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			data = dao.selectPersonByLoginID(id, comp_type, con);
			System.out.println("confirm user id:" + id);
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
		System.out.println("[END confirmOftenUser]");
		return data;
	}
	
	public String selectDeckeyByLoginID(String id) throws SQLException, TaxInvoiceException {
		System.out.println("[START selectDeckeyByLoginID]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		String key = null;
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			key = dao.selectDeckeyByLoginID(id, con);
			System.out.println("enc user id:" + id);
			System.out.println("dec key :" + key);
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
		System.out.println("[END selectDeckeyByLoginID]");
		return key;
	}
	
	public boolean getAdminInfo(String id, String compcode) throws SQLException, TaxInvoiceException {
		System.out.println("[START getAdminInfo]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		boolean adminYN = false;
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			adminYN = dao.getAdminInfo(id, compcode, con);
			System.out.println("user id:" + id);
			System.out.println("compcode :" + compcode);
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
		System.out.println("[END getAdminInfo]");
		return adminYN;
	}
	
	public ArrayList getAdminInfoList(String compcode) throws SQLException, TaxInvoiceException {
		System.out.println("[START getAdminInfoList]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		ArrayList adminlist = new ArrayList();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			adminlist = dao.getAdminInfoList(compcode, con);
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
		System.out.println("[END getAdminInfoList]");
		return adminlist;
	}
	
	public boolean insertAdminInfo(String id, String name, String compcode) throws SQLException, TaxInvoiceException {
		System.out.println("[START insertAdminInfo]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		boolean YN = false;
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			
			YN = !dao.getAdminInfo(id, compcode, con);
			if (YN) 			
				dao.insertAdminInfo(id, name, compcode, con);
			
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
		System.out.println("[END insertAdminInfo]");
		return YN;
	}
	
	public void deleteAdminInfo(String id, String compcode) throws SQLException, TaxInvoiceException {
		System.out.println("[START insertAdminInfo]");
		Connection con = null;
		TaxPersonNewDao dao = new TaxPersonNewDao();
		DBConnector dbconn = new DBConnector();
		try {
			con = dbconn.getConnection();
			con.setAutoCommit(false);
			dao.deleteAdminInfo(id, compcode, con);
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
		System.out.println("[END insertAdminInfo]");
		
	}
	
}
