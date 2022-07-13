package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxFinanceContectVO;

public class TaxEaiContractorDao {

	private static Logger logger = Logger.getLogger(TaxEaiContractorDao.class);
	
    public ArrayList getReceiveContractorList(Connection con) throws SQLException, TaxInvoiceException
    {
    	logger.debug("getReceiveContractorList()::"+con);
        PreparedStatement ps = null;
       
        ArrayList data = new ArrayList();
        
        try{
            String sql =            	
        	" select UUID, CONTRACTOR_ID "   +
        	//" from key_poweredi.EAI_TAX_CONTRACT_TB "   +
        	" from EAI_TAX_CONTRACT_TB "   +
        	" where STATUS='N' and rownum < 10 order by EAI_CDATE "   ;
                       
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
            	TaxFinanceContectVO financeVO = new TaxFinanceContectVO();
            	financeVO.setUuid(CommonUtil.justNullToBlank(rs.getString("UUID")));
            	financeVO.setId(CommonUtil.justNullToBlank(rs.getString("CONTRACTOR_ID")));
            	            	
            	data.add(financeVO);
            }
            
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        return data;
    }   
		
    public void updateContractorInfo(TaxFinanceContectVO financeVO, String status, Connection con) throws SQLException, TaxInvoiceException
    {
    	logger.debug("updateContractorInfo()::"+con);
        PreparedStatement ps = null;
       
        try{
            String sql =            	
        	//" update key_poweredi.EAI_TAX_CONTRACT_TB "   +
        	" update EAI_TAX_CONTRACT_TB "   +
        	" set  STATUS=? "   +
        	" where UUID=? and CONTRACTOR_ID=? "   ;
                        
            ps = con.prepareStatement(sql);
            
            ps.setString(1, status);
            ps.setString(2, financeVO.getUuid());
            ps.setString(3, financeVO.getId());            
            
            ps.executeUpdate();
            ps.close();
            
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        
    }  
	
	
}
