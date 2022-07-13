/*
 * Created on 2005. 8. 5.
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

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxLineVO;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxLineDao {
    
    public void insert(TaxLineVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START INSERT ETS_TAX_LINE_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql = 
                " INSERT INTO ETS_TAX_LINE_INFO_TB ("   +
                "       UUID, LINE_NUM, TRANS_DATE, "   +
                "       ID, CLASS_ID, NAME, DEFINE_TXT, "   +
                "       LINE_DESC, QUANTITY, SUB_TOT_QUANTITY, "   +
                "       BASIS_AMT, CURRENCY_CODE, AMT, SUB_TOT_AMT, "   +
                "       TAX_AMT, TAX_SUB_TOT_AMT, FORN_CHARGE_AMT, "   +
                "       FORN_CHARGE_SUB_TOT_AMT, EXCHANGE_CURRENCY_RATE"   +
                "       ) VALUES ("   +
                "       ?,?,?,"   +
                "       ?,?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?,?,?,"   +
                "       ?,?,?,"   +
                "       ?,?"   +
                "       )"  ;
            
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            
            ps.setString(1, vo.getUuid());
            ps.setString(2, vo.getLine_num());
            ps.setString(3, vo.getTrans_date());
            
            ps.setString(4, vo.getId());
            ps.setString(5, vo.getClass_id());
            ps.setString(6, vo.getName());
            ps.setString(7, vo.getDefine_txt());
            
            ps.setString(8, vo.getLine_desc());
            ps.setString(9, vo.getQuantity());
            ps.setString(10, vo.getSub_tot_quantity());
            
            ps.setString(11, vo.getBasis_amt());
            ps.setString(12, vo.getCurrency_code());
            ps.setString(13, vo.getAmt());
            ps.setString(14, vo.getSub_tot_amt());
            
            ps.setString(15, vo.getTax_amt());
            ps.setString(16, vo.getTax_sub_tot_amt());
            ps.setString(17, vo.getForn_charge_amt());
            
            ps.setString(18, vo.getForn_charge_sub_tot_amt());
            ps.setString(19, vo.getExchange_currency_rate());
            
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END INSERT ETS_TAX_LINE_INFO_TB]");
    }
    
    public String isExist(TaxLineVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START line dao isExist]");
        PreparedStatement ps = null;
        String exist = "N";
        try{
            String sql = 
                " SELECT  DECODE(COUNT(UUID), 0, 'N', 'Y')"   +
                "    FROM  ETS_TAX_LINE_INFO_TB "  +
                "   WHERE UUID = ? "   +
                "   AND LINE_NUM = ? "  ;
            ps = con.prepareStatement(sql);
            ps.setString(1, vo.getUuid());
            ps.setString(2, vo.getLine_num());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                exist = CommonUtil.justNullToBlank(rs.getString(1));
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END line dao isExist]");
        return exist;
    }
    
    public void update(TaxLineVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START UPDATE ETS_TAX_LINE_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql = 
                " UPDATE ETS_TAX_LINE_INFO_TB SET"   +
                "        TRANS_DATE = ? , "   +
                "        ID = ? ,               CLASS_ID = ? ,        NAME = ? , " +
                "        DEFINE_TXT = ? ,       LINE_DESC = ? ,       QUANTITY = ? ," +
                "        SUB_TOT_QUANTITY = ? , BASIS_AMT = ? ,       CURRENCY_CODE = ? , " +
                "        AMT = ? ,              SUB_TOT_AMT = ? ,     TAX_AMT = ? , " +
                "        TAX_SUB_TOT_AMT = ? ,  FORN_CHARGE_AMT = ? , FORN_CHARGE_SUB_TOT_AMT = ? , " +
                "        EXCHANGE_CURRENCY_RATE = ?   "   +
                "   WHERE UUID = ? "   +
                "   AND LINE_NUM = ? "   ;
            
            
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            
            
            ps.setString(1, vo.getTrans_date());
            
            ps.setString(2, vo.getId());
            ps.setString(3, vo.getClass_id());
            ps.setString(4, vo.getName());
            
            ps.setString(5, vo.getDefine_txt());
            ps.setString(6, vo.getLine_desc());
            ps.setString(7, vo.getQuantity());
            
            ps.setString(8, vo.getSub_tot_quantity());
            ps.setString(9, vo.getBasis_amt());
            ps.setString(10, vo.getCurrency_code());
            
            ps.setString(11, vo.getAmt());
            ps.setString(12, vo.getSub_tot_amt());
            ps.setString(13, vo.getTax_amt());
            
            ps.setString(14, vo.getTax_sub_tot_amt());
            ps.setString(15, vo.getForn_charge_amt());
            ps.setString(16, vo.getForn_charge_sub_tot_amt());
            
            ps.setString(17, vo.getExchange_currency_rate());
            
            
            ps.setString(18, vo.getUuid());
            ps.setString(19, vo.getLine_num());
            
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE ETS_TAX_LINE_INFO_TB]");
    }
    
    public ArrayList select(String uuid, ArrayList data, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START SELECT ETS_TAX_LINE_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql = 
                " SELECT UUID,         LINE_NUM,      TRANS_DATE,        "   +
                "        ID, CLASS_ID, NAME,          DEFINE_TXT,        "   +
                "        LINE_DESC,    QUANTITY,      SUB_TOT_QUANTITY,  "   +
                "        BASIS_AMT,    CURRENCY_CODE, AMT, SUB_TOT_AMT,  "   +
                "        TAX_AMT,      TAX_SUB_TOT_AMT, FORN_CHARGE_AMT, "   +
                "        FORN_CHARGE_SUB_TOT_AMT, EXCHANGE_CURRENCY_RATE "   +
                "   FROM ETS_TAX_LINE_INFO_TB " +
                "  WHERE UUID = ? " ;
            
            ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                TaxLineVO vo = new TaxLineVO();
                vo.setUuid(CommonUtil.justNullToBlank(rs.getString(1)));
                vo.setLine_num(CommonUtil.justNullToBlank(rs.getString(2)));
                vo.setTrans_date(CommonUtil.justNullToBlank(rs.getString(3)));
                
                vo.setId(CommonUtil.justNullToBlank(rs.getString(4)));
                vo.setClass_id(CommonUtil.justNullToBlank(rs.getString(5)));
                vo.setName(CommonUtil.justNullToBlank(rs.getString(6)));
                vo.setDefine_txt(CommonUtil.justNullToBlank(rs.getString(7)));
                
                vo.setLine_desc(CommonUtil.justNullToBlank(rs.getString(8)));
                vo.setQuantity(CommonUtil.justNullToBlank(rs.getString(9)));
                vo.setSub_tot_quantity(CommonUtil.justNullToBlank(rs.getString(10)));
                
                vo.setBasis_amt(CommonUtil.justNullToBlank(rs.getString(11)));
                vo.setCurrency_code(CommonUtil.justNullToBlank(rs.getString(12)));
                vo.setAmt(CommonUtil.justNullToBlank(rs.getString(13)));
                vo.setSub_tot_amt(CommonUtil.justNullToBlank(rs.getString(14)));
                
                vo.setTax_amt(CommonUtil.justNullToBlank(rs.getString(15)));
                vo.setTax_sub_tot_amt(CommonUtil.justNullToBlank(rs.getString(16)));
                vo.setForn_charge_amt(CommonUtil.justNullToBlank(rs.getString(17)));
                
                vo.setForn_charge_sub_tot_amt(CommonUtil.justNullToBlank(rs.getString(18)));
                vo.setExchange_currency_rate(CommonUtil.justNullToBlank(rs.getString(19)));
                data.add(vo);
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END SELECT ETS_TAX_LINE_INFO_TB]");
        return data;
    }
    
    // 2006.06.30 ÀÌÁ¦Áß
    public void delete(String uuid, int no, Connection con) throws SQLException, TaxInvoiceException {
        System.out.println("[START UPDATE ETS_TAX_LINE_INFO_TB]");
        PreparedStatement ps = null;
        try{
        	  con.setAutoCommit(false);
            String sql =  " DELETE FROM ETS_TAX_LINE_INFO_TB WHERE UUID = ?  AND LINE_NUM > ?  ";                                            
            ps = con.prepareStatement(sql);                                   
            ps.setString(1, uuid);
            ps.setInt(2, no);            
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE ETS_TAX_LINE_INFO_TB]");
    }
}
