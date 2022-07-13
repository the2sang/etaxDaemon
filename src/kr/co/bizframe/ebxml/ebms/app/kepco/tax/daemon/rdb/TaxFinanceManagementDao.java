/*
 * Created on 2005. 11. 7.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.SQLException;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxFinanceContectVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxFinanceManagementDao {
    
    public TaxFinanceContectVO save(TaxFinanceContectVO vo) throws TaxInvoiceException{
        System.out.println("[START saveTaxinvoice]");
        Connection con = null;
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            TaxFinanceContactDao dao = new TaxFinanceContactDao();
            if(dao.isExist(vo.getUuid(), con).equals("Y")){
                dao.update(vo, con);
            }else{
                dao.insert(vo, con);
            }
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
        System.out.println("[END saveTaxinvoice]");
        return vo;
    }
    
    public TaxFinanceContectVO initializeFinance(TaxFinanceContectVO vo, TaxInvoiceVO taxVO) throws TaxInvoiceException{
        System.out.println("[START saveTaxinvoice]");
        Connection con = null;
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            TaxFinanceContactDao dao = new TaxFinanceContactDao();
            TaxMetaDao metaDao = new TaxMetaDao();
            System.out.println("isExist:"+dao.isExist(vo.getUuid(), con));
            vo = dao.select(taxVO.getMain().getUuid(), con);
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
        System.out.println("[END saveTaxinvoice]");
        return vo;
    }
    
    public TaxFinanceContectVO select(String uuid) throws TaxInvoiceException{
        System.out.println("[START getTaxInvoiceVO]");
        Connection con = null;
        TaxFinanceContectVO vo = new TaxFinanceContectVO();
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            TaxFinanceContactDao dao = new TaxFinanceContactDao();
            vo = dao.select(uuid, con);
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
        System.out.println("[END getTaxInvoiceVO]");
        return vo;
    }
}
