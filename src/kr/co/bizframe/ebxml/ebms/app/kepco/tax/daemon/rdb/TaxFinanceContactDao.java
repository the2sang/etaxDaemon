/*
 * Created on 2005. 11. 7.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxFinanceContectVO;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxFinanceContactDao{
    
    public void insert(TaxFinanceContectVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START INSERT EST_TAX_FINANCE_CONTACT_TB]");
        PreparedStatement ps = null;
        try{
            String sql = 
                " INSERT INTO EST_TAX_FINANCE_CONTACT_TB ("   +
                " UUID, USER_ID, NAME, TEL, EMAIL"   +
                " ) VALUES ("   +
                "   ? , ? , ?, ?, ?"   +
                " )"  ;
            
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            
            ps.setString(1, vo.getUuid());
            ps.setString(2, vo.getId());
            ps.setString(3, vo.getName());
            ps.setString(4, vo.getTel());
            ps.setString(5, vo.getEmail());
            
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END INSERT EST_TAX_FINANCE_CONTACT_TB]");
    }
    
    public String isExist(String uuid, Connection con) throws SQLException
    {
        System.out.println("[START line dao isExist EST_TAX_FINANCE_CONTACT_TB]");
        PreparedStatement ps = null;
        String exist = "N";
        try{
            String sql = 
                " SELECT  DECODE(COUNT(UUID), 0, 'N', 'Y')"   +
                "    FROM  EST_TAX_FINANCE_CONTACT_TB "  +
                "   WHERE UUID = ? "   ;
            ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                exist = rs.getString(1);
            }
            ps.close();
        }catch(SQLException e){
            if(ps != null) ps.close();
            throw e;
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END line dao isExist EST_TAX_FINANCE_CONTACT_TB]");
        return exist;
    }
    
    
    public void update(TaxFinanceContectVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START UPDATE EST_TAX_FINANCE_CONTACT_TB]");
        PreparedStatement ps = null;
        try{
            String sql = 
                "  UPDATE EST_TAX_FINANCE_CONTACT_TB SET"   +
                "         USER_ID = ?, NAME = ?, TEL = ?, EMAIL =?"   +
                "    WHERE UUID = ? "  ;
            
            
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, vo.getId());
            ps.setString(2, vo.getName());
            ps.setString(3, vo.getTel());
            ps.setString(4, vo.getEmail());
            ps.setString(5, vo.getUuid());
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE EST_TAX_FINANCE_CONTACT_TB]");
    }
    
    public TaxFinanceContectVO select(String uuid, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START SELECT ETS_TAX_LINE_INFO_TB]");
        PreparedStatement ps = null;
        TaxFinanceContectVO vo = new TaxFinanceContectVO();
        try{
            String sql = 
                "  SELECT  UUID, USER_ID, NAME, TEL, EMAIL "   +
                "    FROM EST_TAX_FINANCE_CONTACT_TB "   +
                "   WHERE UUID = ? "   ;
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                vo.setUuid(rs.getString(1));
                vo.setId(rs.getString(2));
                vo.setName(rs.getString(3));
                
                vo.setTel(rs.getString(4));
                vo.setEmail(rs.getString(5));
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END SELECT ETS_TAX_LINE_INFO_TB]");
        return vo;
    }
}
