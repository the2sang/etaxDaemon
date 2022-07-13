package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxDocListVO;

public class TaxStatusInfoDao {

	
	public void insertStatusInfo(String uuid, String status, String comp, Connection con) throws SQLException, TaxInvoiceException
    {
        
        System.out.println("[START insert ETS_TAX_STATUS_INFO_TB] insertStatusInfo");
        PreparedStatement ps = null;
        try{
        String sql = " INSERT INTO ETS_TAX_STATUS_INFO_TB (COMP,UUID,STATUS,CREATE_TIME,COMPLETE_TIME) VALUES (?,?,?,sysdate,sysdate) " ;
	        
        con.setAutoCommit(false);
        ps = con.prepareStatement(sql);
        ps.setString(1, comp);
        ps.setString(2, uuid);
        ps.setString(3, status);
        
        ps.executeUpdate();
        ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END insert ETS_TAX_STATUS_INFO_TB] insertStatusInfo");
    }
  
	public void updateStatusInfo(String uuid, String statusSM, String statusSED, String comp, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START update ETS_TAX_STATUS_INFO_TB] updateStatusInfo");
        PreparedStatement ps = null;
        try{
        String sql = " UPDATE ETS_TAX_STATUS_INFO_TB SET " +
        			" 	STATUS = ? , COMPLETE_TIME = sysdate " +
        			" WHERE UUID=? AND COMP=? AND STATUS=? " ;
	        
        con.setAutoCommit(false);
        ps = con.prepareStatement(sql);
        ps.setString(1, statusSED);
        ps.setString(2, uuid);
        ps.setString(3, comp);
        ps.setString(4, statusSM);
        
        ps.executeUpdate();
        ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END update ETS_TAX_STATUS_INFO_TB] updateStatusInfo");
    }
	
    public boolean getStatusInfo(String uuid, String status, String comp, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("getStatusInfo()");
        PreparedStatement ps = null;
//	        ArrayList data = new ArrayList();
        boolean existYN = false;
        
        try{
            String sql = 
            	" SELECT UUID, STATUS FROM ETS_TAX_STATUS_INFO_TB "   +
            	" WHERE COMP = ? " +
            	" 	AND UUID = ? " +
            	" 	AND STATUS = ? " ;
            
            ps = con.prepareStatement(sql);
            ps.setString(1, comp);
	        ps.setString(2, uuid);
	        ps.setString(3, status);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
//	                String[] rec = new String[2];
//	            	rec[0] = CommonUtil.nullToBlank(rs.getString("UUID"));
//	            	rec[1] = CommonUtil.nullToBlank(rs.getString("STATUS"));
//	            	data.add(rec);
            	existYN = true;
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        return existYN;
    }	  

    public boolean getStatusGroupInfo(String uuid, String status, String comp, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("getStatusGroupInfo()");
        PreparedStatement ps = null;
        boolean existYN = false;
        
        try{
            String sql = 
            	" SELECT UUID, STATUS FROM ETS_TAX_STATUS_INFO_TB "   +
            	" WHERE COMP = ? " +
            	" 	AND UUID = ? " +
            	" 	AND STATUS in (?, ?||'SED', ?||'SM') " ;
            
            ps = con.prepareStatement(sql);
            ps.setString(1, comp);
	        ps.setString(2, uuid);
	        ps.setString(3, status);
	        ps.setString(4, status);
	        ps.setString(5, status);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
            	existYN = true;
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        return existYN;
    }	  
  
    
    public ArrayList getKDNErrList( String sDate, String eDate, String gubun, Connection con) throws SQLException, TaxInvoiceException {
        System.out.println("[START getKDNErrList]");
        PreparedStatement ps = null;
        ArrayList data = new ArrayList();
        try{
            
            String sql =                                     
            	" SELECT b.SUPPLIER_BIZ_ID, a.UUID, STATUS, ERR_MSG, TO_CHAR(CREATE_TIME, 'YYYY-MM-DD'), TO_CHAR(COMPLETE_TIME, 'YYYY-MM-DD') " +
            	" FROM ETS_TAX_STATUS_INFO_TB a, ETS_TAX_MAIN_INFO_TB b "   +
             	" WHERE COMP = ? " +
             	"	AND a.UUID = b.UUID " +
                "	AND STATUS IN ('CFRSM','DELSM','CFSSM','ENDSM','REJSM') " +
//            	"	AND CREATE_TIME BETWEEN TO_DATE(?||'01 000000', 'YYYY-MM-DD HH24MISS') AND LAST_DAY(?||'01')+1 "+	
            	"   AND CREATE_TIME >= TO_DATE(?||' 00:00:00','YYYY-MM-DD HH24:MI:SS')  " +
                "   AND CREATE_TIME <= TO_DATE(?||' 23:59:59','YYYY-MM-DD HH24:MI:SS')  " +
            	" order by CREATE_TIME desc	";
            
            //사업자번호 검색 추가
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, gubun);
            ps.setString(2, sDate);
            ps.setString(3, eDate);
         
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            	TaxDocListVO vo = new TaxDocListVO();
                vo.setOther_comp_id(rs.getString(1));
            	vo.setUuid(rs.getString(2));
                vo.setState(rs.getString(3));
                vo.setStatus_code(rs.getString(4));
                vo.setCreate_date(rs.getString(5));
                vo.setComplete_date(rs.getString(6));
                data.add(vo);
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END getKDNErrList]");
        return data;
    }  
	    
	    
}
