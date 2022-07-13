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
import java.util.ArrayList;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxReceiptVO;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxReceiptDao{
    
    public void insert(TaxReceiptVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START INSERT ETS_TAX_RECEIPT_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql = 
                " INSERT INTO ETS_TAX_RECEIPT_INFO_TB ("   +
                " UUID,BIZ_ID,AMT1,AMT2,RECEIPT_NO,RECEIPT_KIND,CUST_NAME,ADDRESS,CONST_DATE,CONST_NO,PROF_CONS_NO, PROF_COMP_AMT, MATRBILL_COMP_AMT, COMP_AMT  , AMT3 "+
                " ) VALUES ("   +
                "   ? , ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? ,? "   +
                " )"  ;
            
            
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);

            if(!"".equals(vo.getAmt3())) vo.setAmt1(""); //20101006 Ãß°¡
            
            ps.setString(1, vo.getUuid());
            ps.setString(2, vo.getBiz_id());
            ps.setString(3, vo.getAmt1());
            ps.setString(4, vo.getAmt2());
            ps.setString(5, vo.getReceipt_no());            
            ps.setString(6, vo.getReceipt_kind());
            ps.setString(7, vo.getCust_name());
            ps.setString(8, vo.getAddress());
            ps.setString(9, vo.getConst_date());
            ps.setString(10, vo.getConst_no());
            
            ps.setString(11, vo.getProf_cons_no());
            ps.setString(12, vo.getProf_comp_amt());
            ps.setString(13, vo.getMatrbill_comp_amt());
            ps.setString(14, vo.getComp_amt());
            ps.setString(15, vo.getAmt3());
            
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END INSERT ETS_TAX_RECEIPT_INFO_TB]");
    }

    public ArrayList select(String uuid, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START SELECT ETS_TAX_RECEIPT_INFO_TB]");
        PreparedStatement ps = null;
        ArrayList data = new ArrayList();
        try{
            String sql = 
                "  SELECT UUID,BIZ_ID,AMT1,AMT2,RECEIPT_NO,RECEIPT_KIND,CUST_NAME,ADDRESS,CONST_DATE,CONST_NO,PROF_CONS_NO, PROF_COMP_AMT, MATRBILL_COMP_AMT, COMP_AMT "   +
                "    FROM ETS_TAX_RECEIPT_INFO_TB "   +
                "   WHERE UUID = ? "   ;
            ps = con.prepareStatement(sql);
            
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
            	TaxReceiptVO vo = new TaxReceiptVO();
                vo.setUuid(rs.getString(1));
                vo.setBiz_id(rs.getString(2));
                vo.setAmt1(rs.getString(3));
                vo.setAmt2(rs.getString(4));
                vo.setReceipt_no(rs.getString(5));
                vo.setReceipt_kind(rs.getString(6));
                vo.setCust_name(rs.getString(7));
                vo.setAddress(rs.getString(8));
                vo.setConst_date(rs.getString(9));
                vo.setConst_no(rs.getString(10));
                
                vo.setProf_cons_no(rs.getString(11));
                vo.setProf_comp_amt(rs.getString(12));
                vo.setMatrbill_comp_amt(rs.getString(13));
                vo.setComp_amt(rs.getString(14));
                
                data.add(vo);
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END SELECT ETS_TAX_RECEIPT_INFO_TB]");
        return data;
    }
}
