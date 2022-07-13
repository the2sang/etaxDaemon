/*
 * Created on 2005. 11. 7.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxReceiptVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxReceiptManagementDao {
 
    public void insert(TaxReceiptVO vo) throws TaxInvoiceException{
        System.out.println("[START TaxReceiptManagementDao]");
        Connection con = null;
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            TaxReceiptDao dao = new TaxReceiptDao();
            dao.insert(vo, con);
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
        System.out.println("[END TaxReceiptManagementDao]");
  
    }
    
    public ArrayList select(String uuid) throws TaxInvoiceException{
        System.out.println("[START TaxReceiptManagementDao]");
        Connection con = null;
        TaxReceiptVO vo = new TaxReceiptVO();
        ArrayList data = new ArrayList();
        DBConnector dbconn = new DBConnector();
        try {
            con = dbconn.getConnection();   
            con.setAutoCommit(false);
            TaxReceiptDao dao = new TaxReceiptDao();
            data = dao.select(uuid, con);
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
        System.out.println("[END TaxReceiptManagementDao]");
        return data;
    }
}
