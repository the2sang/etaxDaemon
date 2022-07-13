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
	public static String SMS_SENDER_NAME = "������";
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
//            throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "SMS DB���� CONNECTION ������ ���� ");
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
	        	
	        	/* ip �����о�� */
				String serverIP = InetAddress.getLocalHost().getHostAddress();
				/* Test server */
				if("168.78.201.224".equals(serverIP)){
					hp = "01087348869";
					System.out.println("sendSMS.TEST server �Դϴ�.");
				}else{
					System.out.println("sendSMS.��Դϴ�. hp="+hp);
				}
				System.out.println("sendSMS.hp=>"+hp);
				
	            int nResult = -1;
	            StringBuffer mailQuery = new StringBuffer();
				//2016.04.26 CDH  ���泻�� : SMS ���� ���̺� ����
//	            mailQuery.append(
//	            "  insert into em_tran@CYBER_SMS_LNK( tran_pr, tran_phone, tran_callback, tran_status, tran_date, tran_msg, tran_etc1, tran_etc2, tran_etc3, tran_etc4 ) "+
////	            "  values ( em_tran_pr.nextval@CYBER_SMS_LNK, ?, '0619317583', '1', sysdate, ?, '�������ڼ��ݰ�꼭�ý���', '������', '0619317583', '5009') "
//	            "  values ( em_tran_pr.nextval@CYBER_SMS_LNK, ?, '"+SMS_SENDER_TEL1+"', '1', sysdate, ?, '�������ڼ��ݰ�꼭�ý���', '"+SMS_SENDER_NAME+"', '"+SMS_SENDER_TEL1+"', '5009') "
//	            );            
	            mailQuery.append(
	            "  INSERT INTO IF_EM_TRAN( ID, TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS" +
	            "						 , TRAN_DATE, TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3" +
	            "						 , TRAN_ETC4, TRAN_TYPE, TABLE_GB, IF_STATUS, CREATE_ID" +
	            "						 , CREATE_DT, UPDATE_ID, UPDATE_DT  ) "+
	            "  VALUES ( 'S'||IF_EM_TRAN_ID.NEXTVAL,'', ?, '"+SMS_SENDER_TEL1+"', '1'" +
	            		"	, sysdate, ?, '�������ڼ��ݰ�꼭�ý���', '"+SMS_SENDER_NAME+"', '"+SMS_SENDER_TEL1+"'" +
	            		"   , '5009', '4', '', '', 'TaxSMSManagementDao'" +
	            		"	,SYSDATE, '', '') "
	            );                     
	            pstmt = con.prepareStatement(mailQuery.toString());
	            
	            if(!CommonUtil.nullToBlank(hp).equals("")){
	                pstmt.setString(1, hp.replaceAll("-",""));
	                pstmt.setString(2, "������������(https://srm.kepco.net)���� �ͻ��� ���ݰ�꼭�� Ȯ���Ͻñ� �ٶ��ϴ�. "+t_uuid);
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
	//                throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "���� SMS ���� ���۽� ���� ");
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

public String sendLMS(String phone, //���Ź�ȣ
		  			  String uuid, //������ȣ
		  			  String issueDay, //�ۼ�����
		  			  String comName, //��ü��
		  			  String chaAmt, //���ް���
		  			  String taxAmt, //����
		  			  String graAmt, //�ѱݾ�
		  			  String email,//�����̸���
		  			  String itemName,//ǰ���
		  			  //���7���� �����ڵ� F:ó�� ����ȳ� D:���7���� �ȳ�
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
		sms_msg = "\n - ��������� : "+ sms_name +"\n - �������ȭ��ȣ : "+sms_call_number+"";
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
        	/* ip �����о�� */
			String serverIP = InetAddress.getLocalHost().getHostAddress();
			/* Test server */
			if("168.78.201.224".equals(serverIP)){
				phone = "01087348869";
				email ="person1697@hanmail.net";
				System.out.println("sendSMS.TEST server �Դϴ�.");
			}else{
				System.out.println("sendLMS.��Դϴ�. phone="+phone);
			}
			
			
			if(gubun.equals("F")){
            	sms_body1 = comName+" �ͻ翡 ����� ���û���� �����û �帳�ϴ�.";
			}else{
				sms_body1= comName+" �ͻ翡 ����� ���û���� �����û �帳�ϴ�. �̹���� 7���� �ڵ���� �˴ϴ�.";
			}			
            sms_body2 = "#���ݰ�꼭 ���� \n - �ۼ����� : "+issueDay2+"\n - ���ǰ�� : "+itemName+"\n - �ݾ� : "+CommonUtil.moneyFormat(graAmt)+"��"+"("+CommonUtil.moneyFormat(chaAmt)+"��"+"+"+CommonUtil.moneyFormat(taxAmt)+"��"+")\n - ������ȣ : "+t_uuid +sms_msg ;
            sms_body3="#���ݰ�꼭 ���� ���\n  (2������ �����ؼ� ����ϼ���)\n - �̸��� ���� ���\n  PC���� ������ "+email+"�� ������ ���� �������� ����\n - �ý��� ���� ���\n  PC���� ������������(https://srm.kepco.net)>>���ڼ��ݰ�꼭>>���������Կ��� ����\n (���������Կ��� �˻����� �ۼ����ڸ� ������ �� ��ȸ �ٶ��ϴ�.)";
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
            		" VALUES(TO_CHAR(SYSDATE,'YYYYMMDD') , MMS_MSG_P_ID.NEXTVAL,  MMS_MSG_SEQ.NEXTVAL@CYBER_LMS_LNK, '�ѱ����°��� ���ڼ��ݰ�꼭 ���� �ȳ�', ?,   "  +
            		"	?, '0', SYSDATE, ?, '0',  "  +
            		" '0', '', '', '', '',  "  +
            		" '', '', '', '', '' ,  "  +
            		" '', '43200', '', '', '',  "  +
            		" '', '', '0', '0', '',  "  +
            		" '', '', ?, '������', ?,  "  +
            		" '', '', '', '', '',  "  +
            		" '', '', '0', '', 'N',  "  +
            		" SYSDATE, '' ) " );                  
            pstmt = con.prepareStatement(mailQuery.toString());
            System.out.println(pstmt);
            if(!CommonUtil.nullToBlank(phone).equals("")){
           		pstmt.setString(1,phone);
            	
            	//20180320  ������  sms �߽�ǥ�� ���� �� ����� ���ؼ� ...
            	pstmt.setString(2, sms_call_number);
            	pstmt.setString(3, sms_body);
            	pstmt.setString(4, "���ڼ��ݰ�꼭�ý���");
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
	        	
	        	/* ip �����о�� */
				String serverIP = InetAddress.getLocalHost().getHostAddress();
				/* Test server */
				if("168.78.201.224".equals(serverIP)){
					hp = "01087348869";
					System.out.println("sendSMS.TEST server �Դϴ�.");
				}else{
					System.out.println("sendSMS.��Դϴ�. hp="+hp);
				}
				System.out.println("sendSMS.hp=>"+hp);
				
	            int nResult = -1;
	            StringBuffer mailQuery = new StringBuffer();
				//2016.04.26 CDH  ���泻�� : SMS ���� ���̺� ����
//	            mailQuery.append(
//	            "  insert into em_tran@CYBER_SMS_LNK( tran_pr, tran_phone, tran_callback, tran_status, tran_date, tran_msg, tran_etc1, tran_etc2, tran_etc3, tran_etc4 ) "+
////	            "  values ( em_tran_pr.nextval@CYBER_SMS_LNK, ?, '0619317583', '1', sysdate, ?, '�������ڼ��ݰ�꼭�ý���', '������', '0619317583', '5009') "
//	            "  values ( em_tran_pr.nextval@CYBER_SMS_LNK, ?, '"+SMS_SENDER_TEL1+"', '1', sysdate, ?, '�������ڼ��ݰ�꼭�ý���', '"+SMS_SENDER_NAME+"', '"+SMS_SENDER_TEL1+"', '5009') "
//	            );            
	            mailQuery.append(
	            "  INSERT INTO IF_EM_TRAN( ID, TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS, TRAN_DATE, TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3, TRAN_ETC4, TRAN_TYPE, TABLE_GB,IF_STATUS,CREATE_ID,CREATE_DT,UPDATE_ID,UPDATE_DT  ) "+
	            "  VALUES ( 'S'||IF_EM_TRAN_ID.NEXTVAL,'', ?, '"+SMS_SENDER_TEL1+"', '1', sysdate, ?, '�������ڼ��ݰ�꼭�ý���', '"+SMS_SENDER_NAME+"', '"+SMS_SENDER_TEL1+"', '5009','4','','','TaxSMSManagementDao',SYSDATE,'','') "
	            );                     
	            pstmt = con.prepareStatement(mailQuery.toString());
	            
	            if(!CommonUtil.nullToBlank(hp).equals("")){
	                pstmt.setString(1, hp.replaceAll("-",""));
	                pstmt.setString(2, "������������(https://srm.kepco.net)���� �ͻ��� ���ݰ�꼭�� Ȯ���Ͻñ� �ٶ��ϴ�. ");
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
	//                throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "���� SMS ���� ���۽� ���� ");
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
				//2016.04.26 CDH  ���泻�� : SMS ���� ���̺� ����
                StringBuffer sb= new StringBuffer()
                .append("INSERT INTO IF_EM_TRAN(                                                                  \n")
                .append("    ID, TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS, TRAN_DATE, TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3, TRAN_ETC4, TRAN_TYPE, TABLE_GB,IF_STATUS,CREATE_ID,CREATE_DT,UPDATE_ID,UPDATE_DT)                            \n")
                .append("VALUES(                                                                               \n")
                .append("    'S'||IF_EM_TRAN_ID.NEXTVAL,'', ?, '0619317583', '1', sysdate, '������������(https://srm.kepco.net)���� �ͻ��� ���ݰ�꼭�� Ȯ���Ͻñ� �ٶ��ϴ�', '�������ڼ��ݰ�꼭�ý���', '������', '0619317583', '5009','4','','','TaxSMSManagementDao',SYSDATE,'','')  \n");
//                .append("    TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS, TRAN_DATE,                       \n")
//                .append("    TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3, TRAN_ETC4 )                            \n")
//                .append("VALUES(                                                                               \n")
//                .append("    EM_TRAN_PR.NEXTVAL@CYBER_SMS_LNK, ?, '0619317583', '1', SYSDATE,                                          \n")
//                .append("    '������������Ż(http://srm.kepco.net)���� �ͻ��� ���ݰ�꼭�� Ȯ���Ͻñ� �ٶ��ϴ�','�������ڼ��ݰ�꼭�ý���', '������', '0619317583', '5009')  \n");
//                //.append("    EM_TRAN_PR.NEXTVAL, ?, '', '1', SYSDATE,                                          \n")
//                //.append("    '������������Ż(http://srm.kepco.net)���� �ͻ��� ���ݰ�꼭�� Ȯ���Ͻñ� �ٶ��ϴ�','�������ڼ��ݰ�꼭�ý��� ', '', '', '')  \n");
 
				
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
    
//20170213 ���Թ�    
public String tax_SendSMS(String phone, String uuid) throws TaxInvoiceException{
    	
    	String msg = "OK";
    	//20170213 ���Թ� (������ȣ�߰�)
    	String t_uuid ="";
    	//Connection conn = this.getConnection();
    	Connection con = null;
    	DBConnector DBConn = new DBConnector();
    	
    	PreparedStatement ps = null;
    	
    	System.out.println("tax_SendSMS=>"+phone);
    	System.out.println("uuid=>"+uuid.substring(9,15));
    	//20170213 ���Թ� (������ȣ���� üũ)
    	if (uuid.length()>= 15){
    		t_uuid = uuid.substring(9,15);
    		System.out.println("uuid===============>"+t_uuid);
    	}
    	
    	try{
    		String serverIP = InetAddress.getLocalHost().getHostAddress();
    	
    		if (DBConn != null) {
    			
    			con = DBConn.getConnection();
	            con.setAutoCommit(false);
				//2016.04.26 CDH  ���泻�� : SMS ���� ���̺� ����
                StringBuffer sb= new StringBuffer();
                
                sb.append("/n         INSERT INTO IF_EM_TRAN");
                sb.append("/n         (");
                sb.append("/n         ID, TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS");
                sb.append("/n         , TRAN_DATE, TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3");
                sb.append("/n         , TRAN_ETC4, TRAN_TYPE, TABLE_GB, IF_STATUS, CREATE_ID");
                sb.append("/n         , CREATE_DT, UPDATE_ID, UPDATE_DT");
                sb.append("/n         )VALUES(");
                sb.append("/n         'S'||IF_EM_TRAN_ID.NEXTVAL,'', ?, '0619317583', '1'");
                sb.append("/n         , sysdate, ?, '�������ڼ��ݰ�꼭�ý���', '������', '0619317583'");
                sb.append("/n         , '5009','4','','','TaxSMSManagementDao'");
                sb.append("/n         ,SYSDATE,'','')");
                
               
//                .append("    TRAN_PR, TRAN_PHONE, TRAN_CALLBACK, TRAN_STATUS, TRAN_DATE,                       \n")
//                .append("    TRAN_MSG, TRAN_ETC1, TRAN_ETC2, TRAN_ETC3, TRAN_ETC4 )                            \n")
//                .append("VALUES(                                                                               \n")
//                .append("    EM_TRAN_PR.NEXTVAL@CYBER_SMS_LNK, ?, '0619317583', '1', SYSDATE,                                          \n")
//                .append("    '������������Ż(http://srm.kepco.net)���� �ͻ��� ���ݰ�꼭�� Ȯ���Ͻñ� �ٶ��ϴ�','�������ڼ��ݰ�꼭�ý���', '������', '0619317583', '5009')  \n");
//                //.append("    EM_TRAN_PR.NEXTVAL, ?, '', '1', SYSDATE,                                          \n")
//                //.append("    '������������Ż(http://srm.kepco.net)���� �ͻ��� ���ݰ�꼭�� Ȯ���Ͻñ� �ٶ��ϴ�','�������ڼ��ݰ�꼭�ý��� ', '', '', '')  \n");
 
				
                ps = con.prepareStatement(sb.toString());
                if("168.78.201.224".equals(serverIP)){
                	ps.setString(1, "01087348869");
                	//20170213 ���Թ� (������ȣ �߰�)
                	ps.setString(2, "������������(https://srm.kepco.net)���� �ͻ��� ���ݰ�꼭�� Ȯ���Ͻñ� �ٶ��ϴ�"+t_uuid);
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
   
public String tax_SendLMS(String phone, //���Ź�ȣ
		  String uuid, //������ȣ
		  String issueDay, //�ۼ�����
		  String comName, //��ü��
		  String chaAmt, //���ް���
		  String taxAmt, //����
		  String graAmt, //�ѱݾ�
		  String email,//�����̸���
		  String itemName//ǰ���
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
			/* ip �����о�� */
			String serverIP = InetAddress.getLocalHost().getHostAddress();
			/* Test server */
			if("168.78.201.224".equals(serverIP)){
				phone = "01087348869";
				email ="person1697@hanmail.net";
				System.out.println("sendSMS.TEST server �Դϴ�.");
			}else{
				System.out.println("sendLMS.��Դϴ�. phone="+phone);
			}



			sms_body1 = comName+" �ͻ翡 ����� ���û���� �����û �帳�ϴ�.";			
			sms_body2 = "#���ݰ�꼭 ���� \n - �ۼ����� : "+issueDay2+"\n - ���ǰ�� : "+itemName+"\n - �ݾ� : "+CommonUtil.moneyFormat(graAmt)+"��"+"("+CommonUtil.moneyFormat(chaAmt)+"��"+"+"+CommonUtil.moneyFormat(taxAmt)+"��"+")\n - ������ȣ : "+t_uuid;
			sms_body3="#���ݰ�꼭 ���� ���\n  (2������ �����ؼ� ����ϼ���)\n - �̸��� ���� ���\n  PC���� ������ "+email+"�� ������ ���� �������� ����\n - �ý��� ���� ���\n  PC���� ������������(https://srm.kepco.net)>>���ڼ��ݰ�꼭>>���������Կ��� ����\n (���������Կ��� �˻����� �ۼ����ڸ� ������ �� ��ȸ �ٶ��ϴ�.)";
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
					" VALUES(TO_CHAR(SYSDATE,'YYYYMMDD') , MMS_MSG_P_ID.NEXTVAL,  MMS_MSG_SEQ.NEXTVAL@CYBER_LMS_LNK, '�ѱ����°��� ���ڼ��ݰ�꼭 ���� �ȳ�', ?,   "  +
					"	?, '0', SYSDATE, ?, '0',  "  +
					" '0', '', '', '', '',  "  +
					" '', '', '', '', '' ,  "  +
					" '', '43200', '', '', '',  "  +
					" '', '', '0', '0', '',  "  +
					" '', '', ?, '������', ?,  "  +
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
			pstmt.setString(4, "���ڼ��ݰ�꼭�ý���");
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