/*
 * Created on 2006. 3. 20.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.binding.Line;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.PropertyUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxLineVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxLineDao;
//import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxLmsVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxPersonVO;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxSMSManagementDao {
	public static String SMS_SENDER_NAME = "관리자";
	public static String SMS_SENDER_TEL1 = "0619317583";
	public static String SMS_SENDER_TEL2 = "0613451249";
	
    public TaxSMSManagementDao(){
    }

/*
    public Connection getConnection() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@168.78.201.181:1521:ora8i";
        String userid = "smsother";
        String passwd = "sms1357";
        Connection  conn    = null;

        try
        {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userid, passwd);
//            return conn;
        }
        catch(Exception e) {
            e.printStackTrace();
//            throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "SMS DB서버 CONNECTION 생성시 에러 ");
        }
		return conn;
    }
*/
    private String generateMsgId(String compId){
        String id = "";
        int seq = 0;
        Random random = new Random();
        while(seq < 10){
            seq = random.nextInt(100);
        }
        return "KB"+compId+ CommonUtil.getCurrentTimeFormat("hhmmss")+seq;
    }
    
   
    
public String sendSMS_new(String hp, String uuid) throws TaxInvoiceException{
    	
//    	hp = "01046299932";
    	System.out.println("==[sendSMS]==");
    	System.out.println("hp::::::>"+hp);
    	String t_uuid = "";
    	if(uuid.length() >= 16){
    		t_uuid = "("+uuid.substring(9,16)+")";
    	}
   
    	System.out.println("uuid=>"+t_uuid);
        //Connection mailConn = this.getConnection();
    	Connection con = null;
    	DBConnector DBConn = new DBConnector();
    	CommonUtil common = new CommonUtil();                 
        PreparedStatement pstmt = null;
        String msg = "OK";

        if (DBConn != null) {

	        try {
	        	
	        	con = DBConn.getConnection();
	        	con.setAutoCommit(false);
	        	
	        	/* ip 정보읽어옴 */
				String serverIP = InetAddress.getLocalHost().getHostAddress();
				/* Test server */
				if("168.78.201.224".equals(serverIP)){
					hp = "01087348869";
					System.out.println("sendSMS.TEST server 입니다.");
				}else{
					System.out.println("sendSMS.운영입니다. hp="+hp);
				}
				System.out.println("sendSMS.hp=>"+hp);
				
	            int nResult = -1;
	            StringBuffer mailQuery = new StringBuffer();
				//2016.04.26 CDH  변경내용 : SMS 전송 테이블 변경
//	            mailQuery.append(
//	            "  insert into em_tran@CYBER_SMS_LNK( tran_pr, tran_phone, tran_callback, tran_status, tran_date, tran_msg, tran_etc1, tran_etc2, tran_etc3, tran_etc4 ) "+
////	            "  values ( em_tran_pr.nextval@CYBER_SMS_LNK, ?, '0619317583', '1', sysdate, ?, '한전전자세금계산서시스템', '하지언', '0619317583', '5009') "
//	            "  values ( em_tran_pr.nextval@CYBER_SMS_LNK, ?, '"+SMS_SENDER_TEL1+"', '1', sysdate, ?, '한전전자세금계산서시스템', '"+SMS_SENDER_NAME+"', '"+SMS_SENDER_TEL1+"', '5009') "
//	            );            
	            mailQuery.append(
	            "  INSERT INTO IF_EM_TRAN( ID, TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS" +
	            "						 , TRAN_DATE, TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3" +
	            "						 , TRAN_ETC4, TRAN_TYPE, TABLE_GB, IF_STATUS, CREATE_ID" +
	            "						 , CREATE_DT, UPDATE_ID, UPDATE_DT  ) "+
	            "  VALUES ( 'S'||IF_EM_TRAN_ID.NEXTVAL,'', ?, '"+SMS_SENDER_TEL1+"', '1'" +
	            		"	, sysdate, ?, '한전전자세금계산서시스템', '"+SMS_SENDER_NAME+"', '"+SMS_SENDER_TEL1+"'" +
	            		"   , '5009', '4', '', '', 'TaxSMSManagementDao'" +
	            		"	,SYSDATE, '', '') "
	            );                     
	            pstmt = con.prepareStatement(mailQuery.toString());
	            
	            if(!CommonUtil.nullToBlank(hp).equals("")){
	                pstmt.setString(1, hp.replaceAll("-",""));
	                pstmt.setString(2, "한전전자조달(https://srm.kepco.net)에서 귀사의 세금계산서를 확인하시기 바랍니다. "+t_uuid);
	                nResult = pstmt.executeUpdate();
	            }
	            if(nResult > 0) {
	            	con.commit();
	            	con.setAutoCommit(true);
	//                System.out.println("SMS Send OK!!!" + CommonUtil.getCurrentTime());
	//                System.out.println("SMS Send OK!!!" + CommonUtil.getCurrentTime());
	            }

	        } catch(Exception e) {
	            msg = "Error";
	            e.printStackTrace();
	            try {
	            	con.rollback();
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	//                throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "메일 SMS 문자 전송시 에러 ");
	            }
	        } finally {
	            if(pstmt != null)
	                try {
	                    pstmt.close();
	                    if(con != null) con.close();
	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                }
	        }

        }

        return msg;
    }

public String sendLMS(String phone, //수신번호
		  			  String uuid, //문서번호
		  			  String issueDay, //작서일자
		  			  String comName, //업체명
		  			  String chaAmt, //공급가액
		  			  String taxAmt, //세액
		  			  String graAmt, //총금액
		  			  String email,//수신이메일
		  			  String itemName,//품목명
		  			  //폐기7일전 구분코드 F:처음 발행안내 D:폐기7일전 안내
		  			  String gubun , TaxInvoiceVO taxVO) throws TaxInvoiceException{
	
//	hp = "01046299932";
	
	
	
	System.out.println("==[sendLMS]==");
	//System.out.println("phone::::::::::::::::::::>>>>>"+phone);
	String t_uuid = "";
	String issueDay2="";
	String sms_body1="";
	String sms_body2="";
	String sms_body3="";
	String sms_body="";
	String title="";
	issueDay2 = CommonUtil.getKorFormatDate(issueDay);
	
	
	String sms_yn = taxVO.getSms_yn();
	String sms_name = taxVO.getSms_sender_name();
	String sms_tel =  taxVO.getSms_sender_tel();
	
	String sms_msg = "";
	
	String sms_call_number = "0619317583"; 
	
	System.out.println("===>sms_yn==>>"+sms_yn);
	System.out.println("===>sms_name==>>"+sms_name);
	System.out.println("===>sms_tel==>>"+sms_tel);
	
	
	if(sms_yn.equals("Y") && (!sms_tel.equals(""))){
		sms_call_number = sms_tel;
		sms_msg = "\n - 한전담당자 : "+ sms_name +"\n - 담당자전화번호 : "+sms_call_number+"";
	}
	
	
	
      
	if(uuid.length() >= 16){
		t_uuid = uuid.substring(9,16);
	}

	System.out.println("uuid=>"+t_uuid);
    //Connection mailConn = this.getConnection();
	Connection con = null;
	DBConnector DBConn = new DBConnector();
	CommonUtil common = new CommonUtil();                 
    PreparedStatement pstmt = null;
    String msg = "OK";
    
    if (DBConn != null) {

        try {
        	
        	con = DBConn.getConnection();
        	con.setAutoCommit(false);
			//System.out.println("email============================>"+email);
        	/* ip 정보읽어옴 */
			String serverIP = InetAddress.getLocalHost().getHostAddress();
			/* Test server */
			if("168.78.201.224".equals(serverIP)){
				phone = "01087348869";
				email ="person1697@hanmail.net";
				System.out.println("sendSMS.TEST server 입니다.");
			}else{
				System.out.println("sendLMS.운영입니다. phone="+phone);
			}
			
			
			if(gubun.equals("F")){
            	sms_body1 = comName+" 귀사에 발행된 대금청구서 발행요청 드립니다.";
			}else{
				sms_body1= comName+" 귀사에 발행된 대금청구서 발행요청 드립니다. 미발행시 7일후 자동폐기 됩니다.";
			}			
            sms_body2 = "#세금계산서 내역 \n - 작성일자 : "+issueDay2+"\n - 대상품목 : "+itemName+"\n - 금액 : "+CommonUtil.moneyFormat(graAmt)+"원"+"("+CommonUtil.moneyFormat(chaAmt)+"원"+"+"+CommonUtil.moneyFormat(taxAmt)+"원"+")\n - 문서번호 : "+t_uuid +sms_msg ;
            sms_body3="#세금계산서 발행 방법\n  (2가지중 선택해서 사용하세요)\n - 이메일 발행 방법\n  PC에서 귀하의 "+email+"에 도착한 메일 본문에서 발행\n - 시스템 발행 방법\n  PC에서 한전전자조달(https://srm.kepco.net)>>전자세금계산서>>받은문서함에서 발행\n (받은문서함에서 검색조건 작성일자를 조정한 후 조회 바랍니다.)";
            sms_body = sms_body1+"\n\n"+sms_body2+"\n\n"+sms_body3;
            //System.out.println("sms_body========"+sms_body);
            int nResult = -1;
            System.out.println("-------------------QUREY START----------------------------");
            StringBuffer mailQuery = new StringBuffer();
               		mailQuery.append(
            		" INSERT INTO IF_MMS_MSG  "  +
            		" (PROCESS_DT, PROCESS_ID, MSGKEY, SUBJECT, PHONE,   "  +
            		" CALLBACK, STATUS, REQDATE, MSG,FILE_CNT,   "  +
            		" FILE_CNT_REAL, FILE_PATH1, FILE_PATH1_SIZ,FILE_PATH2, FILE_PATH2_SIZ,   "  +
            		" FILE_PATH3, FILE_PATH3_SIZ,FILE_PATH4, FILE_PATH4_SIZ, FILE_PATH5,   "  +
            		" FILE_PATH5_SIZ, EXPIRETIME, SENTDATE, RSLTDATE, REPORTDATE,   "  +
            		" TERMINATEDDATE, RSLT, REPCNT, TYPE, TELCOINFO,   "  +
            		" ID, POST, ETC1, ETC2,ETC3,   "  +
            		" ETC4, SKT_PATH1, SKT_PATH2, KT_PATH1, KT_PATH2,  "  +
            		" LGT_PATH1, LGT_PATH2, REPLACE_CNT, REPLACE_MSG, TRANS_YN,   "  +
            		" CREATE_DT, TRANS_DT)  "  +
            		" VALUES(TO_CHAR(SYSDATE,'YYYYMMDD') , MMS_MSG_P_ID.NEXTVAL,  MMS_MSG_SEQ.NEXTVAL@CYBER_LMS_LNK, '한국전력공사 전자세금계산서 발행 안내', ?,   "  +
            		"	?, '0', SYSDATE, ?, '0',  "  +
            		" '0', '', '', '', '',  "  +
            		" '', '', '', '', '' ,  "  +
            		" '', '43200', '', '', '',  "  +
            		" '', '', '0', '0', '',  "  +
            		" '', '', ?, '관리자', ?,  "  +
            		" '', '', '', '', '',  "  +
            		" '', '', '0', '', 'N',  "  +
            		" SYSDATE, '' ) " );                  
            pstmt = con.prepareStatement(mailQuery.toString());
            System.out.println(pstmt);
            if(!CommonUtil.nullToBlank(phone).equals("")){
           		pstmt.setString(1,phone);
            	
            	//20180320  유종일  sms 발신표시 동의 한 사람에 한해서 ...
            	pstmt.setString(2, sms_call_number);
            	pstmt.setString(3, sms_body);
            	pstmt.setString(4, "전자세금계산서시스템");
            	pstmt.setString(5, "0619317583");
            nResult = pstmt.executeUpdate();
            }
            if(nResult > 0) {
            	con.commit();
            	con.setAutoCommit(true);
            }

        } catch(Exception e) {
            msg = "Error";
            e.printStackTrace();
            try {
            	con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if(pstmt != null)
                try {
                    pstmt.close();
                    if(con != null) con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
        }

    }

    return msg;
}

    public String sendSMS(String hp) throws TaxInvoiceException{
    	
//    	hp = "01046299932";
    	System.out.println("==[sendSMS]==");
    	System.out.println("hp::::::>"+hp);
        //Connection mailConn = this.getConnection();
    	Connection con = null;
    	DBConnector DBConn = new DBConnector();
    	CommonUtil common = new CommonUtil();                 
        PreparedStatement pstmt = null;
        String msg = "OK";

        if (DBConn != null) {

	        try {
	        	
	        	con = DBConn.getConnection();
	        	con.setAutoCommit(false);
	        	
	        	/* ip 정보읽어옴 */
				String serverIP = InetAddress.getLocalHost().getHostAddress();
				/* Test server */
				if("168.78.201.224".equals(serverIP)){
					hp = "01087348869";
					System.out.println("sendSMS.TEST server 입니다.");
				}else{
					System.out.println("sendSMS.운영입니다. hp="+hp);
				}
				System.out.println("sendSMS.hp=>"+hp);
				
	            int nResult = -1;
	            StringBuffer mailQuery = new StringBuffer();
				//2016.04.26 CDH  변경내용 : SMS 전송 테이블 변경
//	            mailQuery.append(
//	            "  insert into em_tran@CYBER_SMS_LNK( tran_pr, tran_phone, tran_callback, tran_status, tran_date, tran_msg, tran_etc1, tran_etc2, tran_etc3, tran_etc4 ) "+
////	            "  values ( em_tran_pr.nextval@CYBER_SMS_LNK, ?, '0619317583', '1', sysdate, ?, '한전전자세금계산서시스템', '하지언', '0619317583', '5009') "
//	            "  values ( em_tran_pr.nextval@CYBER_SMS_LNK, ?, '"+SMS_SENDER_TEL1+"', '1', sysdate, ?, '한전전자세금계산서시스템', '"+SMS_SENDER_NAME+"', '"+SMS_SENDER_TEL1+"', '5009') "
//	            );            
	            mailQuery.append(
	            "  INSERT INTO IF_EM_TRAN( ID, TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS, TRAN_DATE, TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3, TRAN_ETC4, TRAN_TYPE, TABLE_GB,IF_STATUS,CREATE_ID,CREATE_DT,UPDATE_ID,UPDATE_DT  ) "+
	            "  VALUES ( 'S'||IF_EM_TRAN_ID.NEXTVAL,'', ?, '"+SMS_SENDER_TEL1+"', '1', sysdate, ?, '한전전자세금계산서시스템', '"+SMS_SENDER_NAME+"', '"+SMS_SENDER_TEL1+"', '5009','4','','','TaxSMSManagementDao',SYSDATE,'','') "
	            );                     
	            pstmt = con.prepareStatement(mailQuery.toString());
	            
	            if(!CommonUtil.nullToBlank(hp).equals("")){
	                pstmt.setString(1, hp.replaceAll("-",""));
	                pstmt.setString(2, "한전전자조달(https://srm.kepco.net)에서 귀사의 세금계산서를 확인하시기 바랍니다. ");
	                nResult = pstmt.executeUpdate();
	            }
	            if(nResult > 0) {
	            	con.commit();
	            	con.setAutoCommit(true);
	//                System.out.println("SMS Send OK!!!" + CommonUtil.getCurrentTime());
	//                System.out.println("SMS Send OK!!!" + CommonUtil.getCurrentTime());
	            }

	        } catch(Exception e) {
	            msg = "Error";
	            e.printStackTrace();
	            try {
	            	con.rollback();
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	//                throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "메일 SMS 문자 전송시 에러 ");
	            }
	        } finally {
	            if(pstmt != null)
	                try {
	                    pstmt.close();
	                    if(con != null) con.close();
	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                }
	        }

        }

        return msg;
    }
    
    
    /**
     * 
     * @param hp
     * @return
     * @throws TaxInvoiceException
     */
    public String tax_SendSMS(String phone) throws TaxInvoiceException{
    	
    	String msg = "OK";
    	//Connection conn = this.getConnection();
    	Connection con = null;
    	DBConnector DBConn = new DBConnector();
    	
    	PreparedStatement ps = null;
    	
    	System.out.println("tax_SendSMS=>"+phone);
    	
    	try{
    		String serverIP = InetAddress.getLocalHost().getHostAddress();
    	
    		if (DBConn != null) {
    			
    			con = DBConn.getConnection();
	            con.setAutoCommit(false);
				//2016.04.26 CDH  변경내용 : SMS 전송 테이블 변경
                StringBuffer sb= new StringBuffer()
                .append("INSERT INTO IF_EM_TRAN(                                                                  \n")
                .append("    ID, TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS, TRAN_DATE, TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3, TRAN_ETC4, TRAN_TYPE, TABLE_GB,IF_STATUS,CREATE_ID,CREATE_DT,UPDATE_ID,UPDATE_DT)                            \n")
                .append("VALUES(                                                                               \n")
                .append("    'S'||IF_EM_TRAN_ID.NEXTVAL,'', ?, '0619317583', '1', sysdate, '한전전자조달(https://srm.kepco.net)에서 귀사의 세금계산서를 확인하시기 바랍니다', '한전전자세금계산서시스템', '관리자', '0619317583', '5009','4','','','TaxSMSManagementDao',SYSDATE,'','')  \n");
//                .append("    TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS, TRAN_DATE,                       \n")
//                .append("    TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3, TRAN_ETC4 )                            \n")
//                .append("VALUES(                                                                               \n")
//                .append("    EM_TRAN_PR.NEXTVAL@CYBER_SMS_LNK, ?, '0619317583', '1', SYSDATE,                                          \n")
//                .append("    '한전공급자포탈(http://srm.kepco.net)에서 귀사의 세금계산서를 확인하시기 바랍니다','한전전자세금계산서시스템', '하지언', '0619317583', '5009')  \n");
//                //.append("    EM_TRAN_PR.NEXTVAL, ?, '', '1', SYSDATE,                                          \n")
//                //.append("    '한전공급자포탈(http://srm.kepco.net)에서 귀사의 세금계산서를 확인하시기 바랍니다','한전전자세금계산서시스템 ', '', '', '')  \n");
 
				
                ps = con.prepareStatement(sb.toString());
                if("168.78.201.224".equals(serverIP)){
                	ps.setString(1, "01087348869");
                }else{
                	ps.setString(1, phone.replaceAll("-", ""));
                }
                
                if(ps.executeUpdate() > 0){
                	con.commit();
                	con.setAutoCommit(true);
                }
    		}
    	}catch (Exception e1) {
			// TODO: handle exception
    		e1.printStackTrace();
    		try{
    			con.rollback();
    		}catch (SQLException e) {
				// TODO: handle exception
    			System.out.println(e.toString());
			}
		}finally {
			if(ps != null){
				try{
					ps.close();
					if(con != null ) con.close();
				}catch (SQLException e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			}
		}
    	return msg;
    }
    
//20170213 윤규미    
public String tax_SendSMS(String phone, String uuid) throws TaxInvoiceException{
    	
    	String msg = "OK";
    	//20170213 윤규미 (문서번호추가)
    	String t_uuid ="";
    	//Connection conn = this.getConnection();
    	Connection con = null;
    	DBConnector DBConn = new DBConnector();
    	
    	PreparedStatement ps = null;
    	
    	System.out.println("tax_SendSMS=>"+phone);
    	System.out.println("uuid=>"+uuid.substring(9,15));
    	//20170213 윤규미 (문서번호길이 체크)
    	if (uuid.length()>= 15){
    		t_uuid = uuid.substring(9,15);
    		System.out.println("uuid===============>"+t_uuid);
    	}
    	
    	try{
    		String serverIP = InetAddress.getLocalHost().getHostAddress();
    	
    		if (DBConn != null) {
    			
    			con = DBConn.getConnection();
	            con.setAutoCommit(false);
				//2016.04.26 CDH  변경내용 : SMS 전송 테이블 변경
                StringBuffer sb= new StringBuffer();
                
                sb.append("/n         INSERT INTO IF_EM_TRAN");
                sb.append("/n         (");
                sb.append("/n         ID, TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS");
                sb.append("/n         , TRAN_DATE, TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3");
                sb.append("/n         , TRAN_ETC4, TRAN_TYPE, TABLE_GB, IF_STATUS, CREATE_ID");
                sb.append("/n         , CREATE_DT, UPDATE_ID, UPDATE_DT");
                sb.append("/n         )VALUES(");
                sb.append("/n         'S'||IF_EM_TRAN_ID.NEXTVAL,'', ?, '0619317583', '1'");
                sb.append("/n         , sysdate, ?, '한전전자세금계산서시스템', '관리자', '0619317583'");
                sb.append("/n         , '5009','4','','','TaxSMSManagementDao'");
                sb.append("/n         ,SYSDATE,'','')");
                
               
//                .append("    TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS, TRAN_DATE,                       \n")
//                .append("    TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3, TRAN_ETC4 )                            \n")
//                .append("VALUES(                                                                               \n")
//                .append("    EM_TRAN_PR.NEXTVAL@CYBER_SMS_LNK, ?, '0619317583', '1', SYSDATE,                                          \n")
//                .append("    '한전공급자포탈(http://srm.kepco.net)에서 귀사의 세금계산서를 확인하시기 바랍니다','한전전자세금계산서시스템', '하지언', '0619317583', '5009')  \n");
//                //.append("    EM_TRAN_PR.NEXTVAL, ?, '', '1', SYSDATE,                                          \n")
//                //.append("    '한전공급자포탈(http://srm.kepco.net)에서 귀사의 세금계산서를 확인하시기 바랍니다','한전전자세금계산서시스템 ', '', '', '')  \n");
 
				
                ps = con.prepareStatement(sb.toString());
                if("168.78.201.224".equals(serverIP)){
                	ps.setString(1, "01087348869");
                	//20170213 윤규미 (문서번호 추가)
                	ps.setString(2, "한전전자조달(https://srm.kepco.net)에서 귀사의 세금계산서를 확인하시기 바랍니다"+t_uuid);
                }else{
                	ps.setString(1, phone.replaceAll("-", ""));
                }
                
                if(ps.executeUpdate() > 0){
                	con.commit();
                	con.setAutoCommit(true);
                }
    		}
    	}catch (Exception e1) {
			// TODO: handle exception
    		e1.printStackTrace();
    		try{
    			con.rollback();
    		}catch (SQLException e) {
				// TODO: handle exception
    			System.out.println(e.toString());
			}
		}finally {
			if(ps != null){
				try{
					ps.close();
					if(con != null ) con.close();
				}catch (SQLException e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
			}
		}
    	return msg;
    }
   
public String tax_SendLMS(String phone, //수신번호
		  String uuid, //문서번호
		  String issueDay, //작서일자
		  String comName, //업체명
		  String chaAmt, //공급가액
		  String taxAmt, //세액
		  String graAmt, //총금액
		  String email,//수신이메일
		  String itemName//품목명
		 ) throws TaxInvoiceException{

//hp = "01046299932";



	System.out.println("==[sendLMS]==");
	String t_uuid = "";
	String issueDay2="";
	String sms_body1="";
	String sms_body2="";
	String sms_body3="";
	String sms_body="";
	String title="";
	issueDay2 = CommonUtil.getKorFormatDate(issueDay);



	if(uuid.length() >= 16){
		t_uuid = uuid.substring(9,16);
	}

	System.out.println("uuid=>"+t_uuid);
	//Connection mailConn = this.getConnection();
	Connection con = null;
	DBConnector DBConn = new DBConnector();
	CommonUtil common = new CommonUtil();                 
	PreparedStatement pstmt = null;
	String msg = "OK";

	if (DBConn != null) {

		try {

			con = DBConn.getConnection();
			con.setAutoCommit(false);
			System.out.println("email============================>"+email);
			/* ip 정보읽어옴 */
			String serverIP = InetAddress.getLocalHost().getHostAddress();
			/* Test server */
			if("168.78.201.224".equals(serverIP)){
				phone = "01087348869";
				email ="person1697@hanmail.net";
				System.out.println("sendSMS.TEST server 입니다.");
			}else{
				System.out.println("sendLMS.운영입니다. phone="+phone);
			}



			sms_body1 = comName+" 귀사에 발행된 대금청구서 발행요청 드립니다.";			
			sms_body2 = "#세금계산서 내역 \n - 작성일자 : "+issueDay2+"\n - 대상품목 : "+itemName+"\n - 금액 : "+CommonUtil.moneyFormat(graAmt)+"원"+"("+CommonUtil.moneyFormat(chaAmt)+"원"+"+"+CommonUtil.moneyFormat(taxAmt)+"원"+")\n - 문서번호 : "+t_uuid;
			sms_body3="#세금계산서 발행 방법\n  (2가지중 선택해서 사용하세요)\n - 이메일 발행 방법\n  PC에서 귀하의 "+email+"에 도착한 메일 본문에서 발행\n - 시스템 발행 방법\n  PC에서 한전전자조달(https://srm.kepco.net)>>전자세금계산서>>받은문서함에서 발행\n (받은문서함에서 검색조건 작성일자를 조정한 후 조회 바랍니다.)";
			sms_body = sms_body1+"\n\n"+sms_body2+"\n\n"+sms_body3;
			//System.out.println("sms_body========"+sms_body);
			int nResult = -1;
			System.out.println("=============================QUERY Start==================");
			StringBuffer mailQuery = new StringBuffer();
			mailQuery.append(
					" INSERT INTO IF_MMS_MSG  "  +
					" (PROCESS_DT, PROCESS_ID, MSGKEY, SUBJECT, PHONE,   "  +
					" CALLBACK, STATUS, REQDATE, MSG,FILE_CNT,   "  +
					" FILE_CNT_REAL, FILE_PATH1, FILE_PATH1_SIZ,FILE_PATH2, FILE_PATH2_SIZ,   "  +
					" FILE_PATH3, FILE_PATH3_SIZ,FILE_PATH4, FILE_PATH4_SIZ, FILE_PATH5,   "  +
					" FILE_PATH5_SIZ, EXPIRETIME, SENTDATE, RSLTDATE, REPORTDATE,   "  +
					" TERMINATEDDATE, RSLT, REPCNT, TYPE, TELCOINFO,   "  +
					" ID, POST, ETC1, ETC2,ETC3,   "  +
					" ETC4, SKT_PATH1, SKT_PATH2, KT_PATH1, KT_PATH2,  "  +
					" LGT_PATH1, LGT_PATH2, REPLACE_CNT, REPLACE_MSG, TRANS_YN,   "  +
					" CREATE_DT, TRANS_DT)  "  +
					" VALUES(TO_CHAR(SYSDATE,'YYYYMMDD') , MMS_MSG_P_ID.NEXTVAL,  MMS_MSG_SEQ.NEXTVAL@CYBER_LMS_LNK, '한국전력공사 전자세금계산서 발행 안내', ?,   "  +
					"	?, '0', SYSDATE, ?, '0',  "  +
					" '0', '', '', '', '',  "  +
					" '', '', '', '', '' ,  "  +
					" '', '43200', '', '', '',  "  +
					" '', '', '0', '0', '',  "  +
					" '', '', ?, '관리자', ?,  "  +
					" '', '', '', '', '',  "  +
					" '', '', '0', '', 'N',  "  +
			" SYSDATE, '' ) " );                  
			pstmt = con.prepareStatement(mailQuery.toString());
			System.out.println(pstmt);
			if(!CommonUtil.nullToBlank(phone).equals("")){
			if("168.78.201.224".equals(serverIP)){
				pstmt.setString(1,"01087348869");
			}else{
				pstmt.setString(1,phone);
			}                      
			pstmt.setString(2, "0619317583");
			pstmt.setString(3, sms_body);
			pstmt.setString(4, "전자세금계산서시스템");
			pstmt.setString(5, "0619317583");
			nResult = pstmt.executeUpdate();
			}
			if(nResult > 0) {
				con.commit();
				con.setAutoCommit(true);
			}

		} catch(Exception e) {
			msg = "Error";
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
					if(con != null) con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		}

	}

	return msg;
}

    
    
    
    public static void main(String arg[]){
        PropertyUtil.loadProperty("/export/home/xmledi/jeus42/xmledi_webhome/kepcobill/WEB-INF/");
        TaxSMSManagementDao dao = new TaxSMSManagementDao();
        try {
            dao.sendSMS("00000000000901625");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        String hp = "016-9727-0781";
//        hp.replaceAll("-","");
//        System.out.println(hp.replaceAll("-",""));


    }


}