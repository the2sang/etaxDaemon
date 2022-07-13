/*
 * Created on 2005. 5. 10.
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
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxCountVO;


/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxCountDao {
    
    public void insert(Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START INSERT ETS_TAX_COUNT_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql = 
                " INSERT INTO ETS_TAX_COUNT_INFO_TB (CNT, CNT_DATE) " +
                " VALUES (1, TO_CHAR(SYSDATE, 'YYYYMMDD')) ";
            
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            System.out.println("[END INSERT ETS_TAX_LINE_INFO_TB]");
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
    }
    
    public String isExist(Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START isExist ETS_TAX_COUNT_INFO_TB]");
        String exist = "N";
        PreparedStatement ps = null;
        try{
            String sql = 
                " SELECT  DECODE(COUNT(CNT_DATE), 0, 'N', 'Y')"   +
                "   FROM  ETS_TAX_COUNT_INFO_TB "  +
                "   WHERE CNT_DATE = TO_CHAR(SYSDATE, 'YYYYMMDD') "  ;
            
            ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                exist = rs.getString(1);
            }
            
            ps.close();
            System.out.println("[END isExist ETS_TAX_COUNT_INFO_TB]");
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        return exist;
    }
    
    public void update(Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START UPDATE ETS_TAX_COUNT_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql = 
                "  UPDATE ETS_TAX_COUNT_INFO_TB "   +
                "     SET CNT = CNT + 1 "   +
                "  WHERE CNT_DATE = TO_CHAR(SYSDATE, 'YYYYMMDD') "  ;
            
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE ETS_TAX_COUNT_INFO_TB]");
    }
    
    public TaxCountVO select(Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START select ETS_TAX_COUNT_INFO_TB]");
        PreparedStatement ps = null;
        TaxCountVO vo = new TaxCountVO();
        try{
            String sql = 
                " SELECT  TO_CHAR(SYSDATE, 'YYYYMMDD'),"   +
                "       NVL((SELECT  SUM(CNT) FROM  ETS_TAX_COUNT_INFO_TB WHERE CNT_DATE = TO_CHAR(SYSDATE, 'YYYYMMDD') ),0) TODAY,"   +
                "       NVL((SELECT  SUM(CNT) FROM  ETS_TAX_COUNT_INFO_TB),0) TOT"   +
                " FROM DUAL"  ;
            
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                vo.setDate(rs.getString(1));
                vo.setCnt(rs.getString(2));
                vo.setTotCnt(rs.getString(3));
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END select ETS_TAX_COUNT_INFO_TB]");
        return vo;
    }
    
    public String getSeqId(Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START select getSeqId]");
        PreparedStatement ps = null;
        String id = "";
        try{
            String sql = 
                " SELECT TRIM(TO_CHAR(SEQ_GET_ID.NEXTVAL,'0000000000000000')) FROM DUAL"  ;
            
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getString(1);
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END select getSeqId]");
        return id;
    }
}
