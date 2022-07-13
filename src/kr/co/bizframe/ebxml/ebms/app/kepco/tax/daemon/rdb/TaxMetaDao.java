/*
 * Created on 2005. 8. 5.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Map;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxDetailVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxHeaderVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMetaVO;

import org.apache.log4j.Logger;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxMetaDao {

	private static Logger logger = Logger.getLogger(TaxMetaDao.class);

    public void insert(TaxMetaVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START INSERT ETS_TAX_META_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql =
                " INSERT INTO ETS_TAX_META_INFO_TB (                                         " +
                "        UUID,                 CREATE_TIME,              COMPLETE_TIME,      " +
                "        SERVICE_ID,           SIGNATURE,                USER_DN,            " +
                "        SIGNCERT,             DOC_STATE,                SENDER_ID,          " +
                "        RECEIVER_ID,          SENDER_COMP_ID,           RECEIVER_COMP_ID,   " +
                "        EXT_DOC_INS_DATE,     EXT_DOC_RESULT_STATUS,    EXT_DOC_RESULT_MSG, " +
                "        EXT_SYSTEM_TYPE,      EXT_VOUCHER_TYPE,         EXT_PURCHASE_TYPE,  " +
                "        EXT_DECUCTION_STATUS, EXT_DECUCTION_DETAIL,     EXT_BUYER_SABUN,    " +
                "        EXT_VALID_SDATE,      EXT_VALID_EDATE,          WRITE_TYPE,         " +
                "        VOUCHER_BUSEO,        VOUCHER_YEARMONTH,        VOUCHER_SEQ,        "+
                "        STATUS_CODE,          CONTRACTOR_ID,            CONTRACT_NO,        " +
                "        CONSTRUCT_NO,         KISUNG_CHG_NO,            REPORT_TYPE,        " +
                "        INSPECTOR_ID,         DOC_PROCESS_STATUS,		 COMP_CODE            "+
                "        ) "   +
                "        VALUES     "   +
                "        (          "   +
                "         ?,SYSDATE,SYSDATE,    "   +
                "         ?,?,?,    "   +
                "         ?,?,?,    "   +
                "         ?,?,?,    "   +
                "         ?,?,?,    "   +
                "         ?,?,?,    "   +
                "         ?,?,?,    "   +
                "         ?,?,?,    "   +
                "         ?,?,?,    "   +
                "         ?,?,?,    "   +
                "         ?,?,?,    " 	+
                "         ?,?,?     "   +
                "        )          "  ;

            con.setAutoCommit(false);
            System.out.println("21-->>"+sql);
            ps = con.prepareStatement(sql);

            ps.setString(1, vo.getUuid());
            ps.setString(2, vo.getService_id());
            ps.setString(3, vo.getSignature());
            ps.setString(4, vo.getUser_dn());

            ps.setString(5, vo.getSigncert());
            ps.setString(6, vo.getDoc_state());
            ps.setString(7, vo.getSender_id());

            ps.setString(8, vo.getReceiver_id());
            ps.setString(9, vo.getSender_comp_id());
            ps.setString(10, vo.getReceiver_comp_id());

            ps.setString(11, vo.getExt_doc_ins_date());
            ps.setString(12, vo.getExt_doc_result_status());
            ps.setString(13, vo.getExt_doc_result_msg());

            ps.setString(14, vo.getExt_system_type());
            ps.setString(15, vo.getExt_voucher_type());
            ps.setString(16, vo.getExt_purchase_type());

            ps.setString(17, vo.getExt_decuction_status());
            ps.setString(18, vo.getExt_decuction_detail());
            ps.setString(19, vo.getExt_buyer_sabun());

            ps.setString(20, vo.getExt_valid_sdate());
            ps.setString(21, vo.getExt_valid_edate());
            ps.setString(22, vo.getWriter_type());

            ps.setString(23, vo.getExt_voucher_buseo());
            ps.setString(24, vo.getExt_voucher_yearMonth());
            ps.setString(25, vo.getExt_voucher_seq());

            ps.setString(26, vo.getExt_status_code());
            ps.setString(27, vo.getContractor_id());
            ps.setString(28, vo.getContract_no());
            System.out.println(" vo.getContract_no    -->>"+ vo.getContract_no());
            System.out.println(" vo.getConstruct_no() -->>"+vo.getConstruct_no());
            ps.setString(29, vo.getConstruct_no());
            ps.setString(30, vo.getKisung_chg_no());
            ps.setString(31, vo.getReport_type());

            ps.setString(32, vo.getInspector_id());
            ps.setString(33, vo.getExt_process_status_code());
            ps.setString(34, vo.getComp_code()  );
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END INSERT ETS_TAX_META_INFO_TB]");
    }


    public void update(TaxMetaVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START UPDATE ETS_TAX_META_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql =
                " UPDATE ETS_TAX_META_INFO_TB SET   "   +
                "        SERVICE_ID = ? ,            SIGNATURE = ? ,             USER_DN = ? , "   +
                "        SIGNCERT = ? ,              DOC_STATE = ? ,             SENDER_ID = ? , "   +
                "        RECEIVER_ID = ?,            SENDER_COMP_ID = ?,         RECEIVER_COMP_ID = ?, " +
                "        EXT_DOC_INS_DATE = ?,       EXT_DOC_RESULT_STATUS = ?,  EXT_DOC_RESULT_MSG = ?, " +
                "        EXT_SYSTEM_TYPE = ?,        EXT_VOUCHER_TYPE = ?,       EXT_PURCHASE_TYPE = ?,  " +
                "        EXT_DECUCTION_STATUS = ?,   EXT_DECUCTION_DETAIL = ?,   EXT_BUYER_SABUN = ?,    " +
                "        EXT_VALID_SDATE = ?,        EXT_VALID_EDATE  = ?,                               " +
                "        VOUCHER_BUSEO =?,           VOUCHER_YEARMONTH =?,       VOUCHER_SEQ =?,        " +
                "        STATUS_CODE =? ,            CONTRACTOR_ID =?,           CONTRACT_NO =?, " +
                "        CONSTRUCT_NO =?,            KISUNG_CHG_NO =?,           REPORT_TYPE =?,      " +
                "        INSPECTOR_ID =?,            DOC_PROCESS_STATUS =?,      COMPLETE_TIME = SYSDATE, COMP_CODE = ?  " +
                "  WHERE UUID = ?   " ;


            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, vo.getService_id());
            ps.setString(2, vo.getSignature());
            ps.setString(3, vo.getUser_dn());

            ps.setString(4, vo.getSigncert());
            ps.setString(5, vo.getDoc_state());
            ps.setString(6, vo.getSender_id());

            ps.setString(7, vo.getReceiver_id());
            ps.setString(8, vo.getSender_comp_id());
            ps.setString(9, vo.getReceiver_comp_id());

            ps.setString(10, vo.getExt_doc_ins_date());
            ps.setString(11, vo.getExt_doc_result_status());
            ps.setString(12, vo.getExt_doc_result_msg());


            ps.setString(13, vo.getExt_system_type());
            ps.setString(14, vo.getExt_voucher_type());
            ps.setString(15, vo.getExt_purchase_type());

            ps.setString(16, vo.getExt_decuction_status());
            ps.setString(17, vo.getExt_decuction_detail());
            ps.setString(18, vo.getExt_buyer_sabun());

            ps.setString(19, vo.getExt_valid_sdate());
            ps.setString(20, vo.getExt_valid_edate());

            ps.setString(21, vo.getExt_voucher_buseo());
            ps.setString(22, vo.getExt_voucher_yearMonth());
            ps.setString(23, vo.getExt_voucher_seq());

            ps.setString(24, vo.getExt_status_code());
            ps.setString(25, vo.getContractor_id());
            ps.setString(26, vo.getContract_no());

            ps.setString(27, vo.getConstruct_no());
            ps.setString(28, vo.getKisung_chg_no());
            ps.setString(29, vo.getReport_type());

            ps.setString(30, vo.getInspector_id());
            ps.setString(31, vo.getExt_process_status_code());

            ps.setString(32, vo.getComp_code());

            ps.setString(33, vo.getUuid());

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE ETS_TAX_META_INFO_TB]");
    }

    public void setStatus(String uuid, String status, Connection con) throws SQLException, TaxInvoiceException
    {

        System.out.println("[START UPDATE ETS_TAX_META_INFO_TB] setStatus uuid=="+ uuid);
        PreparedStatement ps = null;
        try{
        String sql =
            " UPDATE ETS_TAX_META_INFO_TB SET   "   +
            "        DOC_STATE = ?              "   +
            "  WHERE UUID = ?   "   ;

        if (status.equals("SED")) {	sql = sql + "    AND DOC_STATE = 'CFR' "; }

        sql = sql + "    AND DOC_STATE != 'END' ";

        con.setAutoCommit(false);
        ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ps.setString(2, uuid);

        ps.executeUpdate();
        ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE ETS_TAX_META_INFO_TB] setStatus");
    }

    public void setComplateDate(String uuid, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START UPDATE ETS_TAX_META_INFO_TB] setComplateDate");
        PreparedStatement ps = null;
        try{
            String sql =
                "  UPDATE ETS_TAX_META_INFO_TB SET"   +
                "         COMPLETE_TIME = SYSDATE"   +
                "   WHERE UUID = ?"  ;

            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, uuid);

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE ETS_TAX_META_INFO_TB] setComplateDate");
    }

    public TaxMetaVO select(String uuid, TaxMetaVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("[START SELECT ETS_TAX_META_INFO_TB]");
        PreparedStatement ps = null;
        try{
            String sql =
                " SELECT UUID,                          TO_CHAR(CREATE_TIME ,'YYYYMMDD HH:MM:SS') ,              COMPLETE_TIME, "   +
                "        SERVICE_ID,                    SIGNATURE,                          USER_DN, "   +
                "        SIGNCERT,                      DOC_STATE,                          SENDER_ID, "   +
                "        RECEIVER_ID,                   SENDER_COMP_ID,                     RECEIVER_COMP_ID, " +
                "        EXT_DOC_INS_DATE,              EXT_DOC_RESULT_STATUS,              EXT_DOC_RESULT_MSG, " +
                "        EXT_SYSTEM_TYPE,               EXT_VOUCHER_TYPE,                   EXT_PURCHASE_TYPE,  " +
                "        EXT_DECUCTION_STATUS,          EXT_DECUCTION_DETAIL,               EXT_BUYER_SABUN,    " +
                "        TO_CHAR(SYSDATE ,'YYYYMMDD'),  EXT_VALID_EDATE,                    WRITE_TYPE,      "+
                "        VOUCHER_BUSEO,                 VOUCHER_YEARMONTH,                  VOUCHER_SEQ,         "+
                "        STATUS_CODE,                   CONTRACTOR_ID,                      CONTRACT_NO, " +
                "        CONSTRUCT_NO,                  KISUNG_CHG_NO,                      REPORT_TYPE, " +
                "        INSPECTOR_ID,                  DOC_PROCESS_STATUS,					EXT_DOC_REISSUE_MSG,	COMP_CODE  "+
                "   FROM ETS_TAX_META_INFO_TB  "  +
                "  WHERE UUID = ? ";


            ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                vo.setUuid(CommonUtil.justNullToBlank(rs.getString(1)));
                vo.setCreate_time(CommonUtil.justNullToBlank(rs.getString(2)));
                vo.setComplete_time(CommonUtil.justNullToBlank(rs.getString(3)));

                vo.setService_id(CommonUtil.justNullToBlank(rs.getString(4)));
                vo.setSignature(CommonUtil.justNullToBlank(rs.getString(5)));
                vo.setUser_dn(CommonUtil.justNullToBlank(rs.getString(6)));

                vo.setSigncert(CommonUtil.justNullToBlank(rs.getString(7)));
                vo.setDoc_state(CommonUtil.justNullToBlank(rs.getString(8)));
                vo.setSender_id(CommonUtil.justNullToBlank(rs.getString(9)));

                vo.setReceiver_id(CommonUtil.justNullToBlank(rs.getString(10)));
                vo.setSender_comp_id(CommonUtil.justNullToBlank(rs.getString(11)));
                vo.setReceiver_comp_id(CommonUtil.justNullToBlank(rs.getString(12)));

                vo.setExt_doc_ins_date(CommonUtil.justNullToBlank(rs.getString(13)));
                vo.setExt_doc_result_status(CommonUtil.justNullToBlank(rs.getString(14)));
                vo.setExt_doc_result_msg(CommonUtil.justNullToBlank(rs.getString(15)));

                vo.setExt_system_type(CommonUtil.justNullToBlank(rs.getString(16)));
                vo.setExt_voucher_type(CommonUtil.justNullToBlank(rs.getString(17)));
                vo.setExt_purchase_type(CommonUtil.justNullToBlank(rs.getString(18)));

                vo.setExt_decuction_status(CommonUtil.justNullToBlank(rs.getString(19)));
                vo.setExt_decuction_detail(CommonUtil.justNullToBlank(rs.getString(20)));
                vo.setExt_buyer_sabun(CommonUtil.justNullToBlank(rs.getString(21)));

                vo.setExt_valid_sdate(CommonUtil.justNullToBlank(rs.getString(22)));
                vo.setExt_valid_edate(CommonUtil.justNullToBlank(rs.getString(23)));
                vo.setWriter_type(CommonUtil.justNullToBlank(rs.getString(24)));


                vo.setExt_voucher_buseo(CommonUtil.justNullToBlank(rs.getString(25)));
                vo.setExt_voucher_yearMonth(CommonUtil.justNullToBlank(rs.getString(26)));
                vo.setExt_voucher_seq(CommonUtil.justNullToBlank(rs.getString(27)));

                vo.setExt_status_code(CommonUtil.justNullToBlank(rs.getString(28)));
                vo.setContractor_id(CommonUtil.justNullToBlank(rs.getString(29)));
                vo.setContract_no(CommonUtil.justNullToBlank(rs.getString(30)));

                vo.setConstruct_no(CommonUtil.justNullToBlank(rs.getString(31)));
                vo.setKisung_chg_no(CommonUtil.justNullToBlank(rs.getString(32)));
                vo.setReport_type(CommonUtil.justNullToBlank(rs.getString(33)));

                vo.setInspector_id(CommonUtil.justNullToBlank(rs.getString(34)));
                vo.setExt_process_status_code(CommonUtil.justNullToBlank(rs.getString(35)));
                vo.setExt_doc_reissue_msg(CommonUtil.justNullToBlank(rs.getString(36)));
                vo.setComp_code(CommonUtil.justNullToBlank(rs.getString(37)));
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END SELECT ETS_TAX_META_INFO_TB]");
        return vo;
    }


    public String isExistTax(String uuid, Connection con) throws SQLException, TaxInvoiceException{
        System.out.println("[START isExistTax TaxApplicationDao]");
        PreparedStatement ps = null;
        String isExist = "N";
        try{
            String sql =
                " SELECT DECODE(COUNT(*),0,'N','Y') ISEXIST "   +
                "   FROM ETS_TAX_META_INFO_TB "   +
                "  WHERE UUID = ? "  ;
            ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                isExist = CommonUtil.justNullToBlank(rs.getString(1));
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END isExistTax TaxApplicationDao]");
        return isExist;
    }


    public String isExistSendTax(String uuid, Connection con) throws SQLException, TaxInvoiceException{
        System.out.println("[START isExistSendTax TaxApplicationDao]");
        PreparedStatement ps = null;
        String isExist = "N";
        try{
            String sql =
                " SELECT DECODE(COUNT(*),0,'N','Y') ISEXIST "   +
                "   FROM ETS_TAX_META_INFO_TB "   +
                "  WHERE UUID = ? "  +
            	"      AND DOC_STATE IN ('CFS','REJ', 'END')"  ;

            ps = con.prepareStatement(sql);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                isExist = CommonUtil.justNullToBlank(rs.getString(1));
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END isExistSendTax TaxApplicationDao]");
        return isExist;
    }


    public void setResultMsg(String uuid, String msg, Connection con) throws SQLException, TaxInvoiceException
    {

        System.out.println("[START UPDATE ETS_TAX_META_INFO_TB] setResultMsg");
        PreparedStatement ps = null;
        try{
            String sql =
                " UPDATE ETS_TAX_META_INFO_TB SET   "   +
                "        EXT_DOC_RESULT_MSG = ?              "   +
                "  WHERE UUID = ?   "   ;


            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, msg);
            ps.setString(2, uuid);

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE ETS_TAX_META_INFO_TB] setResultMsg");
    }

    public void setReissueMsg(String uuid, String msg, Connection con) throws SQLException, TaxInvoiceException
    {

        System.out.println("[START UPDATE ETS_TAX_META_INFO_TB] setReissueMsg");
        PreparedStatement ps = null;
        try{
            String sql =
                " UPDATE ETS_TAX_META_INFO_TB SET   "   +
                "        EXT_DOC_REISSUE_MSG = ?    "   +
                "  WHERE UUID = ?            		"   ;

            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, msg);
            ps.setString(2, uuid);

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println("[END UPDATE ETS_TAX_META_INFO_TB] setReissueMsg");
    }

    public String isUniqueVoucherSICode(TaxMetaVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("판매 SI TAXINVOICE 가 UNIQUE 한지 체크");
        String isExist = "Y";
        PreparedStatement ps = null;
        try{
            String sql =
            	"   SELECT DECODE(COUNT(UUID), 0, 'Y', 'N')"   +
            	"     FROM ETS_TAX_META_INFO_TB"   +
            	"    WHERE VOUCHER_BUSEO = ?"   +
            	"      AND VOUCHER_YEARMONTH =? "   +
            	" 	   AND VOUCHER_SEQ = ?      "  +
            	"      AND DOC_STATE NOT IN ('REJ', 'DEL')"  ;
            ps = con.prepareStatement(sql);
            ps.setString(1, vo.getExt_voucher_buseo());
            ps.setString(2, vo.getExt_voucher_yearMonth());
            ps.setString(3, vo.getExt_voucher_seq());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                isExist = CommonUtil.justNullToBlank(rs.getString(1));
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        return isExist;
    }

    public String isUniqueSysTypeInfo(String bizId, String SystemTypeSel, String ConstructNoSel, String ReportTypeSel, String KisungChgNoSel, String uuid, Connection con) throws SQLException, TaxInvoiceException
    {
        System.out.println("업무정보가 UNIQUE 한지 체크");
        String isExist = "Y";
        PreparedStatement ps = null;
        try{
            String sql =
            	"   SELECT DECODE(COUNT(UUID), 0, 'Y', 'N')	"   +
            	"     FROM ETS_TAX_META_INFO_TB				"   +
            	"    WHERE ( SENDER_COMP_ID = ? or RECEIVER_COMP_ID = ? )" 	+
            	"	   AND EXT_SYSTEM_TYPE = ?				"   +
            	"      AND CONSTRUCT_NO =? 					"   +
            	" 	   AND REPORT_TYPE = ?      			"  	+
            	" 	   AND KISUNG_CHG_NO = ?      			"  	;
            if (!uuid.equals("")) {
            	sql = sql + "  AND UUID != ?      					"  	;
            }
            sql = sql + "      AND DOC_STATE NOT IN ('REJ', 'DEL')	"  	;

            ps = con.prepareStatement(sql);

            ps.setString(1, bizId);
            ps.setString(2, bizId);
            ps.setString(3, SystemTypeSel);
            ps.setString(4, ConstructNoSel);
            ps.setString(5, ReportTypeSel);
            ps.setString(6, KisungChgNoSel);

            if (!uuid.equals("")) {
            	ps.setString(7, uuid);
            }

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                isExist = CommonUtil.justNullToBlank(rs.getString(1));
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        return isExist;
    }

    public ArrayList getReceiveHeaderList(Connection con) throws SQLException, TaxInvoiceException, UnknownHostException
    {
    	logger.debug("getReceiveHeaderList()::"+con);
        PreparedStatement ps = null;
        ArrayList data = new ArrayList();

        try{
        	/*
        	String serverIP = InetAddress.getLocalHost().getHostAddress();
        	String sql ="";
        	
        	if("168.78.201.224".equals(serverIP)){
        		sql =
                	" select BIZ_NO, CONS_NO, REQ_NO, PROF_CONS_NO, PUB_YMD, BUYER_BIZ_ID, INSPECTOR_ID, CONTRACTOR_ID, CONS_NM, COMP_NO "   +
                	//추가 200912 KHS
                	//" , SUPPLIER_BIZ_CD " +
                	" , BUYER_BIZ_CD, DOC_TYPE_DETAIL	"+//공급자종사업장번호 //공급받는자종사업장번호 //세금계산서종류(3.0)
                	" , REL_SYSTEM_ID		"+ //연계시스템 ID
                	" from key_powereditest.EAI_TAX_HEADER_INFO_TB "   +
                	" where STATUS='WRM' "+
                	//" AND REL_SYSTEM_ID = 'K1ETAX1022'	" +
                	" AND rownum < 51 order by REQ_NO "   ;
        		//System.out.println("테스트서버  TaxDaemon");
        	}else{
        	*/
            String sql =
        	" select BIZ_NO, CONS_NO, REQ_NO, PROF_CONS_NO, PUB_YMD, BUYER_BIZ_ID, INSPECTOR_ID, CONTRACTOR_ID, CONS_NM, COMP_NO "   +
        	//추가 200912 KHS
        	//" , SUPPLIER_BIZ_CD " +
        	" , BUYER_BIZ_CD, DOC_TYPE_DETAIL	"+//공급자종사업장번호 //공급받는자종사업장번호 //세금계산서종류(3.0)
        	" , REL_SYSTEM_ID,SMS_YN , SMS_SENDER_NAME ,SMS_SENDER_TEL 		"+ //연계시스템 ID  20180320 유종일 SMS 추가 
        	" from EAI_TAX_HEADER_INFO_TB "   +
        	" where STATUS='WRM' "+
        	//" AND REL_SYSTEM_ID = 'K1ETAX1022'	" +
        	" AND rownum < 51 order by REQ_NO "   ;
           // System.out.println("운영서버  TaxDaemon");
        	//}
        	
        		
        		
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
            	TaxHeaderVO headerVO = new TaxHeaderVO();
            	headerVO.setBiz_no(CommonUtil.justNullToBlank(rs.getString("BIZ_NO")));
            	headerVO.setBuyer_biz_id(CommonUtil.justNullToBlank(rs.getString("BUYER_BIZ_ID")));
            	headerVO.setCons_no(CommonUtil.justNullToBlank(rs.getString("CONS_NO")));
            	headerVO.setContractor_id(CommonUtil.justNullToBlank(rs.getString("CONTRACTOR_ID")));
            	headerVO.setInspector_id(CommonUtil.justNullToBlank(rs.getString("INSPECTOR_ID")));
            	headerVO.setProf_cons_no(CommonUtil.justNullToBlank(rs.getString("PROF_CONS_NO")));
            	headerVO.setPub_ymd(CommonUtil.justNullToBlank(rs.getString("PUB_YMD")));
            	headerVO.setReq_no(CommonUtil.justNullToBlank(rs.getString("REQ_NO")));
            	headerVO.setCons_nm(CommonUtil.justNullToBlank(rs.getString("CONS_NM")));
            	headerVO.setComp_no(CommonUtil.justNullToBlank(rs.getString("COMP_NO")));
            	//추가 200912 //공급자종사업장번호 //공급받는자종사업장번호 //세금계산서종류(3.0)
            	//headerVO.setSupplier_biz_cd(CommonUtil.justNullToBlank(rs.getString("SUPPLIER_BIZ_CD")));
            	headerVO.setBuyer_biz_cd(CommonUtil.justNullToBlank(rs.getString("BUYER_BIZ_CD")));
            	headerVO.setDoc_type_detail(CommonUtil.justNullToBlank(rs.getString("DOC_TYPE_DETAIL")));
            	//
            	headerVO.setRel_system_id(CommonUtil.justNullToBlank(rs.getString("REL_SYSTEM_ID")));
            	
            	headerVO.setSms_yn(CommonUtil.justNullToBlank(rs.getString("SMS_YN")));
            	headerVO.setSms_sender_name(CommonUtil.justNullToBlank(rs.getString("SMS_SENDER_NAME")));
            	headerVO.setSms_sender_tel(CommonUtil.justNullToBlank(rs.getString("SMS_SENDER_TEL")));
            	
            	//
            	data.add(headerVO);
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        return data;
    }

    public ArrayList getReceiveDetailList(TaxHeaderVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
    	logger.debug("getReceiveDetailList()::"+con);
        PreparedStatement ps = null;
        ArrayList data = new ArrayList();

        try{
        	
         	String sql =
         		"  select BIZ_NO, CONS_NO, REQ_NO, CONS_KND_CD, ACPTNO, MATRBILL_COMP_AMT, COMP_AMT, " +
//         		"	ACPT_KND_NM, CUSTNM, ADDRESS, OPER_YMD, PROF_COMP_AMT " +
//2015.08.20 vat_amt 추가(ppa시스템 세액계산법이 틀려서 추가함)          		
         		"	ACPT_KND_NM, CUSTNM, ADDRESS, OPER_YMD, PROF_COMP_AMT, VAT_AMT " +
         		//"  from key_poweredi.EAI_TAX_DETAIL_INFO_TB " +
         		"  from EAI_TAX_DETAIL_INFO_TB " +
         		"  where BIZ_NO = ? " +
         		"   and CONS_NO	= ? " +
         		"   and REQ_NO	= ? " +
         		//	"	and REL_SYSTEM_ID	= 'K1ETAX1022'	"+
         		"	AND REL_SYSTEM_ID = ? "+ // 추가
         		"  order by CONS_KND_CD desc " ;

            ps = con.prepareStatement(sql);

            ps.setString(1, vo.getBiz_no());
            ps.setString(2, vo.getCons_no());
            ps.setString(3, vo.getReq_no());
            ps.setString(4, vo.getRel_system_id());// 추가

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
            	TaxDetailVO detailVO = new TaxDetailVO();
            	detailVO.setBiz_no(CommonUtil.justNullToBlank(rs.getString("BIZ_NO")));
            	detailVO.setCons_no(CommonUtil.justNullToBlank(rs.getString("CONS_NO")));
            	detailVO.setReq_no(CommonUtil.justNullToBlank(rs.getString("REQ_NO")));
            	detailVO.setCons_knd_cd(CommonUtil.justNullToBlank(rs.getString("CONS_KND_CD")));
            	detailVO.setAcptno(CommonUtil.justNullToBlank(rs.getString("ACPTNO")));
            	detailVO.setMatrbill_comp_amt(CommonUtil.justNullToBlank(rs.getString("MATRBILL_COMP_AMT")));
            	detailVO.setComp_amt(CommonUtil.justNullToBlank(rs.getString("COMP_AMT")));
            	detailVO.setAcpt_knd_nm(CommonUtil.justNullToBlank(rs.getString("ACPT_KND_NM")));
            	detailVO.setCustnm(CommonUtil.justNullToBlank(rs.getString("CUSTNM")));
            	detailVO.setAddress(CommonUtil.justNullToBlank(rs.getString("ADDRESS")));
            	detailVO.setOper_ymd(CommonUtil.justNullToBlank(rs.getString("OPER_YMD")));
            	detailVO.setProf_comp_amt(CommonUtil.justNullToBlank(rs.getString("PROF_COMP_AMT")));
//2015.08.20 vat_amt 추가(ppa시스템 세액계산법이 틀려서 추가함)
            	detailVO.setVat_amt(CommonUtil.justNullToBlank(rs.getString("VAT_AMT")));
            	
            	data.add(detailVO);
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            if(ps != null) ps.close();
        }
        return data;
    }

    public void setHeaderState(TaxHeaderVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
    	logger.debug("[START UPDATE EAI_TAX_HEADER_INFO_TB] setHeaderState");
        PreparedStatement ps = null;
        try{
        	
         	String sql =
         			//" UPDATE key_poweredi.EAI_TAX_HEADER_INFO_TB SET   "   +
         			" UPDATE EAI_TAX_HEADER_INFO_TB SET   "   +
         			"        STATUS='CFR'   "   +
         			"  where BIZ_NO = ? " +
         			"   and CONS_NO	= ? " +
         			"   and REQ_NO	= ? " ;
         		
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);

            ps.setString(1, vo.getBiz_no());
            ps.setString(2, vo.getCons_no());
            ps.setString(3, vo.getReq_no());

            System.out.println("▶▶▶ UPDATE EAI_TAX_HEADER_INFO_TB SQL : "+ sql);
            System.out.println("▶▶▶ UPDATE input  : vo.getBiz_no"+ vo.getBiz_no()+ "vo.getCons_no "+vo.getCons_no()+"vo.getReq_no"+vo.getReq_no());
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            if(ps != null) ps.close();
        }
        logger.debug("[END UPDATE EAI_TAX_HEADER_INFO_TB] setHeaderState");
    }

    public void insertStatusInfo(TaxHeaderVO vo, String uuid, String errmsg, Connection con) throws SQLException, TaxInvoiceException
    {
    	logger.debug("[START INSERT EAI_TAX_STATUS_INFO_TB] insertStatusInfo");
        PreparedStatement ps = null;
        try{
            String status = "CFR";
        	if (!errmsg.equals("")) { status = "ERR"; uuid = ""; }

         	String sql =
            	//" INSERT INTO key_poweredi.EAI_TAX_STATUS_INFO_TB " +
            	" INSERT INTO EAI_TAX_STATUS_INFO_TB " +
            	" (BIZ_NO, CONS_NO, REQ_NO, UUID, STATUS, STATUS_MSG, SPLY_YMD, REL_SYSTEM_ID ) " +
            	" values " +
            	" (?, ?, ?, ?, ?, ?, '', ?)" ;

            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);

            ps.setString(1, vo.getBiz_no());
            ps.setString(2, vo.getCons_no());
            ps.setString(3, vo.getReq_no());
            ps.setString(4, uuid);
            ps.setString(5, status);
            ps.setString(6, errmsg);
            ps.setString(7, vo.getRel_system_id());

            System.out.println("▶▶▶INSERT EAI_TAX_STATUS_INFO_TB SQL : "+ sql);
            System.out.println("▶▶▶ INPUT : vo.getBiz_no"+ vo.getBiz_no()+"vo.getCons_no "+vo.getCons_no()+"vo.getReq_no"+vo.getReq_no() );
            System.out.println("▶▶▶ INPUT : uuid"+uuid+"status "+status+"status"+status +"errmsg"+errmsg+"vo.getRel_system_id"+vo.getRel_system_id());
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            if(ps != null) ps.close();
        }
        logger.debug("[END INSERT EAI_TAX_STATUS_INFO_TB] insertStatusInfo");
    }

    public ArrayList selectStatusByBasicDate(String basicDate, Connection con) throws SQLException, TaxInvoiceException
    {
    	logger.debug("[START selectStatusByBasicDate] ");
        ArrayList uuidList = new ArrayList();
        PreparedStatement ps = null;
        try{
            String sql =
            " 		   select  a.uuid "   +
            " 		   from ETS_TAX_META_INFO_TB a, ETS_TAX_MAIN_INFO_TB b "   +
            " 		   where  a.uuid = b.uuid "   +
            " 			 and a.DOC_STATE ='CFR' "   +
            " 	  		 and a.WRITE_TYPE = 'K' "   +
            " 			 and a.COMP_CODE = '00' "   +
            " 		  	 and a.EXT_SYSTEM_TYPE = '300' "   +
            " 		   	 and b.DOC_DATE < to_date(?,'yyyymmdd') "   ;

            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, basicDate);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
            	uuidList.add(rs.getString("uuid"));
            }

        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }

        logger.debug("[END selectStatusByBasicDate] ");
        return uuidList;
    }

    public void updateStatusByBasicDate(String basicDate, Connection con) throws SQLException, TaxInvoiceException
    {
    	logger.debug("[START updateStatusByBasicDate] ");
        PreparedStatement ps = null;
        try{
            String sql =
//            	" UPDATE ETS_TAX_META_INFO_TB SET   "   +
//                "        DOC_STATE ='DEL',   "   +
//                "        EXT_DOC_RESULT_MSG ='발행 후 15일 경과로 자동 폐기됨'   "   +
//                "  where DOC_STATE ='CFR' " +
//	            "   and WRITE_TYPE = 'K' " +
//	            "   and COMP_CODE = '00' " +
//	            "   and EXT_SYSTEM_TYPE = '300' " +
//	            "   and CREATE_TIME < to_date(?,'yyyymmdd') " ;

	            " UPDATE ETS_TAX_META_INFO_TB SET    	"   +
	            " 	DOC_STATE ='DEL',               	"   +
	            " 	EXT_DOC_RESULT_MSG ='발행 후 15일 경과로 자동 폐기됨' "   +
	            " where uuid in ( "   +
	            " 		   select  a.uuid "   +
	            " 		   from ETS_TAX_META_INFO_TB a, ETS_TAX_MAIN_INFO_TB b "   +
	            " 		   where  a.uuid = b.uuid "   +
	            " 			 and a.DOC_STATE ='CFR' "   +
	            " 	  		 and a.WRITE_TYPE = 'K' "   +
	            " 			 and a.COMP_CODE = '00' "   +
	            " 		  	 and a.EXT_SYSTEM_TYPE = '300' "   +
	            " 		   	 and b.DOC_DATE < to_date(?,'yyyymmdd') "   +
	            " ) "   ;

            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);

            ps.setString(1, basicDate);

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        logger.debug("[END updateStatusByBasicDate] ");
    }

    public void updateStatusInfo(ArrayList uuidList, Connection con) throws SQLException, TaxInvoiceException
    {
    	logger.debug("[START UPDATE EAI_TAX_STATUS_INFO_TB] updateStatusInfo");
        PreparedStatement ps = null;
        try{
            String whereStr = "";

        	for (int idx=0; idx<uuidList.size(); idx++) {
        		if(idx!=0) whereStr += ",";
        		whereStr += "'" + uuidList.get(idx) + "'";
        		logger.debug(uuidList.get(idx));
        	}

			String sql =
                	" UPDATE EAI_TAX_STATUS_INFO_TB SET " +
                	" 	STATUS = 'DEL' , EAI_STAT = null, " +
                	" 	STATUS_MSG ='발행 후 15일 경과로 자동 폐기됨' "   +
                	" WHERE UUID in ("+whereStr+") " ;
       	
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            if(ps != null) ps.close();
        }
        logger.debug("[END UPDATE EAI_TAX_STATUS_INFO_TB] updateStatusInfo");
    }
    
    
    /* 2009.08.06 */
    public ArrayList selectStatusBySmsDate(String smsDate,Connection con)throws SQLException, TaxInvoiceException{
    	logger.debug("[START selectStatusByBasicDate] ");
        ArrayList receiverList = new ArrayList();
        PreparedStatement ps = null;
        // 확인요청 상태인 내선건을 검색함.수신업체사업자번호,수신자 아이디 return
        // 7일전 날자-1
        try{
            String sql =
            " 		   SELECT  a.RECEIVER_COMP_ID, a.RECEIVER_ID, a.UUID,					\n"   +
            " 		   		   b.CHARGE_AMT, b.TOT_TAX_AMT, b.TOT_AMT,	b.DOC_DATE,			\n"   +
            " 		   		   b.SUPPLIER_NAME, c.NAME, b.SUPPLIER_CONTACTOR_EMAIL			\n"   +
            " 		   FROM ETS_TAX_META_INFO_TB a, ETS_TAX_MAIN_INFO_TB b,					\n"   +
            " 		   		ETS_TAX_LINE_INFO_TB c											\n"   +
            " 		   WHERE  a.UUID = b.UUID 												\n"   +
            " 		   	 AND  a.UUID = c.UUID 												\n"   +
            " 		   	 AND  b.UUID = c.UUID 												\n"   +
            " 			 AND a.DOC_STATE ='CFR' 											\n"   +
            " 	  		 AND a.WRITE_TYPE = 'K' 											\n"   +
            " 			 AND a.COMP_CODE = '00' 											\n"   +
            " 		  	 AND a.EXT_SYSTEM_TYPE = '300' 										\n"   +
            " 		  	 AND c.LINE_NUM = '1' 												\n"   +
            "			 AND b.DOC_DATE = ? "	;
            //"			 AND b.DOC_DATE = TO_DATE(?,'yyyymmdd') 			"	;
            //"			 AND b.DOC_DATE = TO_DATE(?,'yyyymmdd')-1 			"	;
            //" 		 AND b.DOC_DATE < TO_DATE(?,'yyyymmdd') "   ;

            ps = con.prepareStatement(sql);
            ps.setString(1, smsDate);
            //ps.setString(2, smsDate);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
            	Map ResultMap = new HashMap();
            	ResultMap.put("RECEIVER_COMP_ID", 				CommonUtil.nullToBlank(rs.getString("RECEIVER_COMP_ID")));
            	ResultMap.put("RECEIVER_ID",					CommonUtil.nullToBlank(rs.getString("RECEIVER_ID")));
            	ResultMap.put("CHARGE_AMT",						rs.getString("CHARGE_AMT"));
            	ResultMap.put("TOT_TAX_AMT",					rs.getString("TOT_TAX_AMT"));
            	ResultMap.put("TOT_AMT",						rs.getString("TOT_AMT"));
            	ResultMap.put("DOC_DATE",						CommonUtil.nullToBlank(rs.getString("DOC_DATE")));
            	ResultMap.put("SUPPLIER_NAME",					CommonUtil.nullToBlank(rs.getString("SUPPLIER_NAME")));
            	ResultMap.put("NAME",							CommonUtil.nullToBlank(rs.getString("NAME")));
            	ResultMap.put("UUID",							CommonUtil.nullToBlank(rs.getString("UUID")));
            	ResultMap.put("SUPPLIER_CONTACTOR_EMAIL",		CommonUtil.nullToBlank(rs.getString("SUPPLIER_CONTACTOR_EMAIL")));
                       	
            	//receiverList.add(rs.getString("RECEIVER_ID"));
            	receiverList.add(ResultMap);
            }
            rs.close();
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }

        logger.debug("[END selectStatusByBasicDate] ");
        return receiverList;
    }
    /* 2009.08.06 */
    public String selectHpByReceiver(String Receiver_comp_id,String Receiver_id,Connection con)throws SQLException, TaxInvoiceException{
    	logger.debug("[START selectHpByReceiver] ");
		//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
		String BUSI_ENC = CommonUtil.getString("BUSI_ENC");
		String sql = "";
        PreparedStatement ps = null;
    	String hp = "";
    	try{
			if(BUSI_ENC.equals("1")){    		
    		sql =
	    		"	SELECT 	                                    \n" +
	    		"  		A.MOBILE,								\n" +
	    		"		B.MOB_NUMBER							\n" +
	    		//2015.12.02 차세대입찰 관련 작업  CDH
//	    		"	FROM TP_EXUSER_TBL@USER_LINK A, 			\n" +
//  	  		"	 	 TP_SUPPLIER_TBL@USER_LINK B,   		\n" +
//    			"   	 TP_OWNERLST_TBL@USER_LINK C   			\n" +
	    		"	FROM TP_EXUSER_TBL_VIEW A,		 			\n" +
	    		"	 	 TP_SUPPLIER_TBL_VIEW B,		   		\n" +
	    		"   	 TP_OWNERLST_TBL_VIEW C		   			\n" +
	    		"	WHERE A.SUPPLIER_NO = B.SUPPLIER_NO   		\n" +
				//2016.08.17 차세대입찰 관련 작업 CDH
				"   AND C.ID = B.ID																			"   +
	    		"	AND A.SUPPLIER_NO = C.SUPPLIER_NO   		\n" +
	    		//2015.12.02 차세대입찰 관련 작업  CDH
	    		"	AND B.BUSINESS_NO = ENCODE_SF64@D_EDI2SRM(?)\n" +
//	    		"	AND B.BUSINESS_NO = ENCODE_SF_SNM(?)  		\n" +
	    		"   AND A.TAX_SMS_YN = 'Y'						\n" +
	    		"	AND A.USER_ID = ? 							  ";
			}else{
				sql =
				"	SELECT 	                                    \n" +
				"  		A.MOBILE,								\n" +
				"		B.MOB_NUMBER							\n" +
				//2015.12.02 차세대입찰 관련 작업  CDH
//	    		"	FROM TP_EXUSER_TBL@USER_LINK A, 			\n" +
//  	  		"	 	 TP_SUPPLIER_TBL@USER_LINK B,   		\n" +
//    			"   	 TP_OWNERLST_TBL@USER_LINK C   			\n" +
				"	FROM TP_EXUSER_TBL_VIEW A,		 			\n" +
				"	 	 TP_SUPPLIER_TBL_VIEW B,		   		\n" +
				"   	 TP_OWNERLST_TBL_VIEW C		   			\n" +
				"	WHERE A.SUPPLIER_NO = B.SUPPLIER_NO   		\n" +
				//2016.08.17 차세대입찰 관련 작업 CDH
				"   AND C.ID = B.ID																			"   +
				"	AND A.SUPPLIER_NO = C.SUPPLIER_NO   		\n" +
				//2015.12.02 차세대입찰 관련 작업  CDH
				"	AND B.BUSINESS_NO = ?  						\n" +
//	    		"	AND B.BUSINESS_NO = ENCODE_SF_SNM(?)  		\n" +
				"   AND A.TAX_SMS_YN = 'Y'						\n" +
				"	AND A.USER_ID = ? 							  ";
			}
             ps = con.prepareStatement(sql);
             ps.setString(1, Receiver_comp_id);
             ps.setString(2, Receiver_id);
             ResultSet rs = ps.executeQuery();

             //String mobil = "";
             if(rs.next()){
            	 hp = CommonUtil.justNullToBlank(rs.getString("MOBILE"));
            	 if("".equals(hp)) hp = CommonUtil.justNullToBlank(rs.getString("MOB_NUMBER"));
             }
             rs.close();
             ps.close();
    	}catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        logger.debug("[END selectHpByReceiver] ");
    	return hp;
    }
    
   /**
    * 미동의건 검색 
    * @param con
    * @return
    * @throws SQLException
    * @throws TaxInvoiceException
    */
    public ArrayList selectTrunce_F(Connection con)throws SQLException, TaxInvoiceException{
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        try{
        	StringBuffer sql = new StringBuffer();
        	/*
        	sql.append(" SELECT 											\n");
        	sql.append(" 		A.SVC_MANAGE_ID AS MANAGEMENT_ID, --UUID		\n");
        	sql.append(" 		'1' SEQNO, -- 순번							\n");
        	sql.append(" 		'2' JOB_DEFINE, -- 업무구분 1: MM 2 : FI		\n");
        	sql.append(" 		ISSUE_ID, -- 국세청 승인번호					\n");
        	sql.append(" 		'2' NTS_REGIST_YN,							\n");
        	sql.append(" 		'미동의' NTS_REGIST_TXT,						\n");
        	sql.append("		ISSUE_DAY									\n");
        	sql.append(" FROM TB_TAX_BILL_INFO A							\n");
        	sql.append(" WHERE IO_CODE = '2' --한전 기준 1 매출, 2 매입			\n");
        	sql.append(" AND ONLINE_GUB_CODE = '1' -- 온라인 : 1  오프라인 : 2	\n");
        	sql.append(" AND ERP_SND_YN IS NULL	--							\n");
        	sql.append(" AND LENGTH(A.SVC_MANAGE_ID) = 16						\n");
        	sql.append(" AND STATUS_CODE='02'								\n");
        	sql.append(" AND ELECTRONIC_REPORT_YN = 'F'						\n");
        	sql.append(" AND INVOICEE_GUB_CODE = '00'						\n");
        	sql.append(" AND A.BILL_TYPE_CODE IN ('0101', '0102', '0103', '0104', '0105', '0201', '0202', '0203', '0204', '0205')	\n");
        	sql.append("	-- 날자조건 들어가야함 4월1일이후	\n");
        	*/
        	
        	/* sql start */
        	sql.append(" SELECT                                                                   										\n");
        	sql.append("     A.SVC_MANAGE_ID AS MANAGEMENT_ID,      --UUID                        										\n");
        	sql.append("     '1' SEQNO,                          	--순번                        													\n");
        	sql.append("     '2' JOB_DEFINE,                     	--업무구분 1: MM 2 : FI        										\n");
        	sql.append("     A.ISSUE_ID, 						 	--국세청 승인번호														\n");
        	sql.append("     '2' NTS_REGIST_YN,					 	--미동의건 '2'														\n");
        	sql.append("     '미동의' NTS_REGIST_TXT,			 		--사유																\n");
        	sql.append("     A.ISSUE_DAY							--작성일자															\n");
        	sql.append(" FROM TB_TAX_BILL_INFO A,ETS_TAX_META_INFO_TB B,ETS_TAX_MAIN_INFO_TB C 											\n");
        	sql.append(" WHERE A.IO_CODE = '2'	               		--한전 기준 1 매출, 2 매입												\n");
        	sql.append(" AND A.ONLINE_GUB_CODE = '1' 	 			--온라인 : 1  오프라인 : 2 												\n");
        	sql.append(" AND A.STATUS_CODE='02'						--상태 '02'															\n");
        	sql.append(" AND A.ISSUE_DAY BETWEEN TO_CHAR(SYSDATE-190,'YYYYMMDD') AND TO_CHAR(SYSDATE,'YYYYMMDD')	--금일 190일전부터		\n");
        	sql.append(" AND LENGTH(A.SVC_MANAGE_ID) = 16			--세금계산서															\n");
        	sql.append(" AND A.ELECTRONIC_REPORT_YN = 'F' 			--동의안됨'F'															\n");
        	sql.append(" AND A.INVOICEE_GUB_CODE = '00' 																				\n");
        	sql.append(" AND A.ERP_SND_YN IS NULL  					--Xml생성 안된것  														\n");
        	sql.append(" AND A.SVC_MANAGE_ID = B.UUID																					\n");
        	sql.append(" AND A.ISSUE_ID = C.NTS_ISSUE_ID																				\n");
        	sql.append(" AND B.UUID = C.UUID																							\n");
        	sql.append(" AND A.BILL_TYPE_CODE IN ('0101', '0102', '0103', '0104', '0105', '0201', '0202', '0203', '0204', '0205')--세금계산서 종류  \n");
        	sql.append(" AND (B.DOC_STATE='REJ' OR B.DOC_STATE='END')	--취소이거나 완료된건중 												\n");
        	//sql.append(" AND ");
        	//sql.append("");
        	
        	//System.out.println("sql selectTrunce : "+sql.toString());
        	ps = con.prepareStatement(sql.toString());
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()){
        		Map ResultMap = new HashMap();
        		ResultMap.put("MANAGEMENT_ID", 	CommonUtil.nullToBlank(rs.getString("MANAGEMENT_ID")));
        		ResultMap.put("SEQNO", 			CommonUtil.nullToBlank(rs.getString("SEQNO")));
        		ResultMap.put("JOB_DEFINE", 	CommonUtil.nullToBlank(rs.getString("JOB_DEFINE")));
        		ResultMap.put("ISSUE_ID", 		CommonUtil.nullToBlank(rs.getString("ISSUE_ID")));
        		ResultMap.put("NTS_REGIST_YN", 	CommonUtil.nullToBlank(rs.getString("NTS_REGIST_YN")));
        		ResultMap.put("NTS_REGIST_TXT", CommonUtil.nullToBlank(rs.getString("NTS_REGIST_TXT")));
        		ResultMap.put("ISSUE_DAY", 		CommonUtil.nullToBlank(rs.getString("ISSUE_DAY")));
        		list.add(ResultMap);
        	}
        	rs.close();
        	ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }        
        return list;
    }
    
    /**
     * 
     * @param con
     * @return
     * @throws SQLException
     * @throws TaxInvoiceException
     */
    public ArrayList selectTrunce_Y(Connection con)throws SQLException, TaxInvoiceException{
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        try{
        	StringBuffer sql = new StringBuffer();
        	/*
        	sql.append(" SELECT 																				\n");
        	sql.append(" 		A.SVC_MANAGE_ID AS MANAGEMENT_ID, --UUID											\n");
        	sql.append("  		'1' SEQNO, -- 순번																\n");
        	sql.append("  		'2' JOB_DEFINE, -- 업무구분 1: MM 2 : FI											\n");
        	sql.append("  		ISSUE_ID, -- 국세청 승인번호														\n");
        	sql.append("  		(SELECT DECODE(CODE,'99','2','98','2','04','1','97','1') FROM TB_CODE_INFO WHERE CODE_GRP_ID = 'STATUS_CODE' AND CODE = A.STATUS_CODE)NTS_REGIST_YN ,	\n");
        	sql.append("  		(SELECT DECODE(CODE,'98','반려처리','99',CODE_VALUE) FROM TB_CODE_INFO WHERE CODE_GRP_ID = 'STATUS_CODE' AND CODE = A.STATUS_CODE)NTS_REGIST_TXT,		\n");
        	sql.append("  		ELECTRONIC_REPORT_YN, -- F : 전송대상아님 Y : 전송대상							\n");
        	sql.append("  		STATUS_CODE,																	\n");
        	sql.append("		ISSUE_DAY																		\n");
        	sql.append(" FROM TB_TAX_BILL_INFO A 																\n");
        	sql.append(" WHERE IO_CODE = '2' --한전 기준 1 매출, 2 매입 												\n");
        	sql.append(" AND ONLINE_GUB_CODE = '1' -- 온라인 :1 -- 오프라인 : 2 									\n");
        	sql.append(" AND ERP_SND_YN IS NULL 																\n");
        	sql.append(" AND LENGTH(A.SVC_MANAGE_ID) = 16 														\n");
        	sql.append(" AND INVOICEE_GUB_CODE = '00'															\n");
        	sql.append(" AND (ELECTRONIC_REPORT_YN = 'N' OR ELECTRONIC_REPORT_YN = 'Y')							\n");
        	sql.append(" AND A.BILL_TYPE_CODE IN ('0101', '0102', '0103', '0104', '0105', '0201', '0202', '0203', '0204', '0205')	\n");
        	sql.append("	-- 날자조건 들어가야함 4월1일이후	\n");
        	sql.append(" AND ( 																					\n");
        	sql.append("  		STATUS_CODE = '04'  --  국세청전송완료											\n");
        	sql.append("  		OR STATUS_CODE='97' --  국세청 재전송완료											\n");
        	sql.append("  		OR STATUS_CODE='98' -- 취소/반려처리												\n");
        	sql.append("  		OR STATUS_CODE='99' --  전송실패													\n");
        	sql.append("  	  )																					\n");
			*/
        	
        	/* */
        	sql.append(" SELECT                                                                                  \n");
        	sql.append("       A.SVC_MANAGE_ID AS MANAGEMENT_ID, 					-- UUID                          \n");
        	sql.append("       '1' SEQNO, 										-- 순번                                                               	 \n");
        	sql.append("       '2' JOB_DEFINE, 									-- 업무구분  1 : MM, 2 : FI        \n");  
        	sql.append("       A.ISSUE_ID, 										-- 국세청 승인번호                                             \n");    
        	sql.append("       (SELECT DECODE(CODE,'99','2','98','2','04','1','97','1') FROM TB_CODE_INFO WHERE CODE_GRP_ID = 'STATUS_CODE' AND CODE = A.STATUS_CODE)NTS_REGIST_YN ,    \n");
        	sql.append("       (SELECT DECODE(CODE,'98','반려처리','99',CODE_VALUE) FROM TB_CODE_INFO WHERE CODE_GRP_ID = 'STATUS_CODE' AND CODE = A.STATUS_CODE)NTS_REGIST_TXT,         \n");
        	sql.append("       A.ELECTRONIC_REPORT_YN, 							-- F : 전송대상아님 Y : 전송대상        \n");  
        	sql.append("       A.STATUS_CODE,                                   -- 상태코드	                     \n");
        	sql.append("       A.ISSUE_DAY,                                      -- 작성일자	                     \n"); 
        	sql.append("	   TO_CHAR(A.ESERO_FINISH_TS, 'YYYYMMDD') AS ESERO_FINISH_TS	--세금계산서 국세청신고완료일시		\n");
        	sql.append(" FROM TB_TAX_BILL_INFO A, ETS_TAX_META_INFO_TB B, ETS_TAX_MAIN_INFO_TB C                 \n");                                                
        	sql.append(" WHERE A.IO_CODE = '2' 									-- 한전 기준 1 매출, 2 매입                          \n");         
        	sql.append(" AND A.ONLINE_GUB_CODE = '1' 							-- 온라인 :1 -- 오프라인 : 2       \n");     
        	sql.append(" AND A.ERP_SND_YN IS NULL                               -- ERP Null 인것                               	  	 \n"); 
        	sql.append(" AND LENGTH(A.SVC_MANAGE_ID) = 16                                                        \n"); 
        	sql.append(" AND A.INVOICEE_GUB_CODE = '00'                                                          \n");  
        	sql.append(" AND (A.ELECTRONIC_REPORT_YN = 'N' OR A.ELECTRONIC_REPORT_YN = 'Y')                      \n");      
        	sql.append(" AND A.ISSUE_DAY BETWEEN TO_CHAR(SYSDATE-190,'YYYYMMDD') AND TO_CHAR(SYSDATE,'YYYYMMDD')    --금일 190일전부터					    \n");
        	sql.append(" AND A.BILL_TYPE_CODE IN ('0101', '0102', '0103', '0104', '0105', '0201', '0202', '0203', '0204', '0205')  --세금계산서 종류코드    \n");
        	sql.append(" AND A.STATUS_CODE IN ('04','97','98','99')    											 \n");
        	sql.append(" AND A.SVC_MANAGE_ID = B.UUID                                                            \n");
        	sql.append(" AND A.ISSUE_ID = C.NTS_ISSUE_ID                                            			 \n");
        	sql.append(" AND B.UUID = C.UUID                                                                     \n");
        	sql.append(" AND (B.DOC_STATE='REJ' OR B.DOC_STATE='END')    		--취소이거나 완료된건중			 \n");
        	
        	//System.out.println("sql selectTrunce : "+sql.toString());
        	
        	ps = con.prepareStatement(sql.toString());
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()){
        		Map ResultMap = new HashMap();
        		ResultMap.put("MANAGEMENT_ID", 			CommonUtil.nullToBlank(rs.getString("MANAGEMENT_ID")));
        		ResultMap.put("SEQNO", 					CommonUtil.nullToBlank(rs.getString("SEQNO")));
        		ResultMap.put("JOB_DEFINE", 			CommonUtil.nullToBlank(rs.getString("JOB_DEFINE")));
        		ResultMap.put("ISSUE_ID", 				CommonUtil.nullToBlank(rs.getString("ISSUE_ID")));
        		ResultMap.put("NTS_REGIST_YN", 			CommonUtil.nullToBlank(rs.getString("NTS_REGIST_YN")));
        		ResultMap.put("NTS_REGIST_TXT", 		CommonUtil.nullToBlank(rs.getString("NTS_REGIST_TXT")));
        		ResultMap.put("ELECTRONIC_REPORT_YN", 	CommonUtil.nullToBlank(rs.getString("ELECTRONIC_REPORT_YN")));
        		ResultMap.put("STATUS_CODE", 			CommonUtil.nullToBlank(rs.getString("STATUS_CODE")));
        		ResultMap.put("ISSUE_DAY", 				CommonUtil.nullToBlank(rs.getString("ISSUE_DAY")));
        		ResultMap.put("ESERO_FINISH_TS", 		CommonUtil.nullToBlank(rs.getString("ESERO_FINISH_TS")));
        		list.add(ResultMap);
        	}
        	rs.close();
        	ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }        
        return list;
    }
    
    /**
     * 
     * @param issue_id
     * @param con
     * @throws SQLException
     * @throws TaxInvoiceException
     */
    public void UpdateIssue_id(String issue_id, Connection con)throws SQLException, TaxInvoiceException{
    	PreparedStatement ps = null;
    	try{
    		StringBuffer sql = new StringBuffer();
    		
    		sql.append("	UPDATE TB_TAX_BILL_INFO SET ERP_SND_YN = 'Y' 	\n");
    		sql.append("	WHERE ISSUE_ID = ? 								\n");
    		
    		con.setAutoCommit(false);
    		
    		ps = con.prepareStatement(sql.toString());
    		ps.setString(1, issue_id);
    		ps.executeUpdate();
    		ps.close();
    	}catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
    }
    
    public String getvalueCheck(String uuid,Connection con)throws SQLException, TaxInvoiceException{
    	PreparedStatement ps = null;
    	String value = "";
    	System.out.println(" valueCheck : uuid : "+uuid);
    	try{
    		StringBuffer sql = new StringBuffer();
    		sql.append("	SELECT COUNT(UUID)CNT FROM ETS_ERP_XML_INFO_TB WHERE UUID = ? 		\n");
    		ps = con.prepareStatement(sql.toString());
    		ps.setString(1, uuid);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()){
    			value = rs.getString("CNT");
    		}
    		rs.close();
    		ps.close();
    	}catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }
        System.out.println(" value ?????? : "+value);
        return value;
    }
    
    /**
     * 대상 list
     * @param tax_Date
     * @param con
     * @return
     */
    public ArrayList getTax_Sms_Mail_List(String tax_Date,Connection con)throws SQLException, TaxInvoiceException{
    	
    	/* */
    	ArrayList list = new ArrayList();
    	PreparedStatement ps = null;
    	
    	//System.out.println("등록일자 tax_Date : "+tax_Date);
    	//tax_Date = "20100706";
    	//System.out.println("등록일자 tax_Date : "+tax_Date);
    	
    	try{
    		StringBuffer sql = new StringBuffer();
    		sql.append("		\n");
    		sql.append(" SELECT 							\n");
    		sql.append("   A.IO_CODE, 						\n");
    		sql.append("   A.SVC_MANAGE_ID, 				\n");
    		sql.append("   A.BIZ_MANAGE_ID, 				\n");
    		sql.append("   A.INVOICEE_PARTY_ID,				\n"); // 암호화 작업 대상 index
    		sql.append("   A.INVOICER_PARTY_ID,				\n"); 
    		sql.append("   A.INVOICER_PARTY_NAME, 			\n");
    		sql.append("   A.INVOICEE_PARTY_NAME,			\n");
    		sql.append("   A.INVOICEE_CONTACT_PHONE1,		\n");
    		sql.append("   A.INVOICEE_CONTACT_EMAIL1,		\n");
    		sql.append("   A.INVOICEE_CONTACT_NAME1,		\n");
    		sql.append("   A.INVOICEE_CONTACT_PHONE2,		\n");
    		sql.append("   A.INVOICEE_CONTACT_EMAIL2,		\n");
    		sql.append("   A.INVOICEE_CONTACT_NAME2,		\n");
    		sql.append("   A.INVOICER_CONTACT_PHONE,		\n");
    		sql.append("   A.INVOICER_CONTACT_EMAIL,		\n");
    		sql.append("   A.INVOICER_CONTACT_NAME,			\n");
    		sql.append("   A.CHARGE_TOTAL_AMOUNT,			\n");
    		sql.append("   A.TAX_TOTAL_AMOUNT,				\n");
    		sql.append("   A.GRAND_TOTAL_AMOUNT,			\n"); 
    		sql.append("   A.ISSUE_ID,						\n");
    		sql.append("   A.BILL_TYPE_CODE,				\n");
    		sql.append("   A.ISSUE_DAY,						\n");
    		sql.append("   B.ITEM_NAME						\n");
    		sql.append(" FROM TB_TAX_BILL_INFO A,  			\n");
    		sql.append(" TB_TRADE_ITEM_LIST B				\n");
    		sql.append(" WHERE A.STATUS_CODE ='01'			\n");
    		sql.append(" AND A.IO_CODE = '1'				\n");
    		sql.append(" AND A.BIZ_MANAGE_ID = B.BIZ_MANAGE_ID   \n"); 
  			sql.append(" AND A.IO_CODE = B.IO_CODE	\n");
  			sql.append(" AND A.ISSUE_DAY=B.ISSUE_DAY	\n");
    		sql.append(" AND TO_CHAR(A.REGIST_DT,'yyyyMMdd') = ? --TO_CHAR(sysdate-3,'yyyyMMdd') 	\n");

    				
    		
    		System.out.println(" 매출,sms,mail sql ");
    		System.out.println(sql.toString());
    		
    		ps = con.prepareStatement(sql.toString());
    		ps.setString(1, tax_Date);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()){
    			Map ResultMap = new HashMap();
    			ResultMap.put("IO_CODE", 					CommonUtil.nullToBlank(rs.getString("IO_CODE")));						//
    			ResultMap.put("SVC_MANAGE_ID", 					CommonUtil.nullToBlank(rs.getString("SVC_MANAGE_ID")));						//
    			ResultMap.put("BIZ_MANAGE_ID", 				CommonUtil.nullToBlank(rs.getString("BIZ_MANAGE_ID")));					//
    			ResultMap.put("INVOICEE_PARTY_ID", 			CommonUtil.nullToBlank(rs.getString("INVOICEE_PARTY_ID")));				//
    			ResultMap.put("INVOICER_PARTY_ID", 			CommonUtil.nullToBlank(rs.getString("INVOICER_PARTY_ID")));				//
    			ResultMap.put("INVOICER_PARTY_NAME", 		CommonUtil.nullToBlank(rs.getString("INVOICER_PARTY_NAME")));			//
    			ResultMap.put("INVOICEE_PARTY_NAME", 		CommonUtil.nullToBlank(rs.getString("INVOICEE_PARTY_NAME")));			//
    			ResultMap.put("INVOICEE_CONTACT_PHONE1", 	CommonUtil.nullToBlank(rs.getString("INVOICEE_CONTACT_PHONE1")));		//
    			ResultMap.put("INVOICEE_CONTACT_EMAIL1", 	CommonUtil.nullToBlank(rs.getString("INVOICEE_CONTACT_EMAIL1")));		//
    			ResultMap.put("INVOICEE_CONTACT_NAME1", 	CommonUtil.nullToBlank(rs.getString("INVOICEE_CONTACT_NAME1")));		//
    			ResultMap.put("INVOICEE_CONTACT_PHONE2", 	CommonUtil.nullToBlank(rs.getString("INVOICEE_CONTACT_PHONE2")));		//
    			ResultMap.put("INVOICEE_CONTACT_EMAIL2",	CommonUtil.nullToBlank(rs.getString("INVOICEE_CONTACT_EMAIL2")));		//
    			ResultMap.put("INVOICEE_CONTACT_NAME2", 	CommonUtil.nullToBlank(rs.getString("INVOICEE_CONTACT_NAME2")));		//
    			ResultMap.put("INVOICER_CONTACT_PHONE", 	CommonUtil.nullToBlank(rs.getString("INVOICER_CONTACT_PHONE")));		//
    			ResultMap.put("INVOICER_CONTACT_EMAIL", 	CommonUtil.nullToBlank(rs.getString("INVOICER_CONTACT_EMAIL")));		//
    			ResultMap.put("INVOICER_CONTACT_NAME", 		CommonUtil.nullToBlank(rs.getString("INVOICER_CONTACT_NAME")));			//
    			ResultMap.put("ISSUE_ID", 					CommonUtil.nullToBlank(rs.getString("ISSUE_ID")));
    			ResultMap.put("ISSUE_DAY", 					CommonUtil.nullToBlank(CommonUtil.getKorFormatDate(rs.getString("ISSUE_DAY"))));
    			ResultMap.put("CHARGE_TOTAL_AMOUNT", 		CommonUtil.moneyFormatLong(rs.getLong("CHARGE_TOTAL_AMOUNT")));
    			ResultMap.put("TAX_TOTAL_AMOUNT", 			CommonUtil.moneyFormatLong(rs.getLong("TAX_TOTAL_AMOUNT")));
    			ResultMap.put("GRAND_TOTAL_AMOUNT", 		CommonUtil.moneyFormatLong(rs.getLong("GRAND_TOTAL_AMOUNT")));
    			ResultMap.put("ITEM_NAME", 					CommonUtil.nullToBlank(rs.getString("ITEM_NAME")));
    			ResultMap.put("BILL_TYPE_CODE", 			CommonUtil.nullToBlank(rs.getString("BILL_TYPE_CODE")));
    			list.add(ResultMap);
    		}
    		rs.close();
    		ps.close();
    	}catch (SQLException e) {
    		throw new TaxInvoiceException(this, e);
        }finally{
            if(ps != null) ps.close();
        }    	
    	return list;
    }
    
    
    /**
     * 
     * @param con
     * @return
     * @throws SQLException
     * @throws TaxInvoiceException
     */
    public ArrayList getNcisReceiveHeaderList(Connection con) throws SQLException, TaxInvoiceException{
    	System.out.println("getNcisReceiveHeaderList()::"+con);
        PreparedStatement ps = null;
        ArrayList data = new ArrayList();

        try{
            StringBuffer sql = new StringBuffer();
        	     
        	sql.append("	SELECT  BIZ_NO, CONS_NO, REQ_NO, PROF_CONS_NO,  \n");
            sql.append("			PUB_YMD, BUYER_BIZ_ID, INSPECTOR_ID,	\n");
            sql.append("			CONTRACTOR_ID, CONS_NM, COMP_NO, 		\n");
            sql.append("			BUYER_BIZ_CD, DOC_TYPE_DETAIL 			\n");
            sql.append("	FROM EAI_TAX_HEADER_INFO_TB		\n");
            sql.append("	WHERE STATUS='WRM'								\n");
            sql.append("	AND REL_SYSTEM_ID = 'K1ETAX1000'				\n");
            sql.append("	AND  ROWNUM < 51 ORDER BY REQ_NO				\n");
        	
            System.out.println(sql.toString());
            ps = con.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
            	TaxHeaderVO headerVO = new TaxHeaderVO();
            	headerVO.setBiz_no(CommonUtil.justNullToBlank(rs.getString("BIZ_NO")));
            	headerVO.setBuyer_biz_id(CommonUtil.justNullToBlank(rs.getString("BUYER_BIZ_ID")));
            	headerVO.setCons_no(CommonUtil.justNullToBlank(rs.getString("CONS_NO")));
            	headerVO.setContractor_id(CommonUtil.justNullToBlank(rs.getString("CONTRACTOR_ID")));
            	headerVO.setInspector_id(CommonUtil.justNullToBlank(rs.getString("INSPECTOR_ID")));
            	headerVO.setProf_cons_no(CommonUtil.justNullToBlank(rs.getString("PROF_CONS_NO")));
            	headerVO.setPub_ymd(CommonUtil.justNullToBlank(rs.getString("PUB_YMD")));
            	headerVO.setReq_no(CommonUtil.justNullToBlank(rs.getString("REQ_NO")));
            	headerVO.setCons_nm(CommonUtil.justNullToBlank(rs.getString("CONS_NM")));
            	headerVO.setComp_no(CommonUtil.justNullToBlank(rs.getString("COMP_NO")));
            	headerVO.setBuyer_biz_cd(CommonUtil.justNullToBlank(rs.getString("BUYER_BIZ_CD")));
            	headerVO.setDoc_type_detail(CommonUtil.justNullToBlank(rs.getString("DOC_TYPE_DETAIL")));

            	data.add(headerVO);
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            if(ps != null) ps.close();
        }
        return data;
    }
    
    public ArrayList getNcisReceiveDetailList(TaxHeaderVO vo, Connection con) throws SQLException, TaxInvoiceException
    {
    	logger.debug("getNcisReceiveDetailList()::"+con);
        PreparedStatement ps = null;
        ArrayList data = new ArrayList();

        try{
            StringBuffer sql = new StringBuffer();
        	
            sql.append("	SELECT																					\n");
            sql.append("			BIZ_NO, CONS_NO, REQ_NO, CONS_KND_CD, ACPTNO, MATRBILL_COMP_AMT, COMP_AMT,		\n");
            sql.append("			ACPT_KND_NM, CUSTNM, ADDRESS, OPER_YMD, PROF_COMP_AMT							\n");
            sql.append("	FROM EAI_TAX_DETAIL_INFO_TB												\n");
            sql.append("	WHERE BIZ_NO = ''																		\n");
            sql.append("	      AND CONS_NO = ''																	\n");
            sql.append(" 		  AND REQ_NO = '' 																	\n");
            sql.append("		  AND REL_SYSTEM_ID = 'K1ETAX1000'													\n");
            sql.append("	ORDER BY CONS_KND_CD DESC 																\n");
            
            ps = con.prepareStatement(sql.toString());

            ps.setString(1, vo.getBiz_no());
            ps.setString(2, vo.getCons_no());
            ps.setString(3, vo.getReq_no());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
            	TaxDetailVO detailVO = new TaxDetailVO();
            	detailVO.setBiz_no(CommonUtil.justNullToBlank(rs.getString("BIZ_NO")));
            	detailVO.setCons_no(CommonUtil.justNullToBlank(rs.getString("CONS_NO")));
            	detailVO.setReq_no(CommonUtil.justNullToBlank(rs.getString("REQ_NO")));
            	detailVO.setCons_knd_cd(CommonUtil.justNullToBlank(rs.getString("CONS_KND_CD")));
            	detailVO.setAcptno(CommonUtil.justNullToBlank(rs.getString("ACPTNO")));
            	detailVO.setMatrbill_comp_amt(CommonUtil.justNullToBlank(rs.getString("MATRBILL_COMP_AMT")));
            	detailVO.setComp_amt(CommonUtil.justNullToBlank(rs.getString("COMP_AMT")));
            	detailVO.setAcpt_knd_nm(CommonUtil.justNullToBlank(rs.getString("ACPT_KND_NM")));
            	detailVO.setCustnm(CommonUtil.justNullToBlank(rs.getString("CUSTNM")));
            	detailVO.setAddress(CommonUtil.justNullToBlank(rs.getString("ADDRESS")));
            	detailVO.setOper_ymd(CommonUtil.justNullToBlank(rs.getString("OPER_YMD")));
            	detailVO.setProf_comp_amt(CommonUtil.justNullToBlank(rs.getString("PROF_COMP_AMT")));

            	data.add(detailVO);
            }
            ps.close();
        }catch(SQLException e){
            throw new TaxInvoiceException(this, e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            if(ps != null) ps.close();
        }
        return data;
    }
}
