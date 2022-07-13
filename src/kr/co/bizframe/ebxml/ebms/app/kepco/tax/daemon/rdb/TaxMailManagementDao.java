/*
 * Created on 2005. 8. 29.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.PropertyUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.manager.DBConnector;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util.TaxInvSecurityMgr;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxFinanceContectVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxInvoiceVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxMailVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxMailManagementDao extends Object implements Serializable{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3258133565697765425L;

    public TaxMailManagementDao(){
        //PropertyUtil.loadProperty("E:\\project\\hanjun2005\\tomcat4.1.31\\taxInvoice\\WEB-INF\\");
        //ServerProperties props = ServerProperties.getInstance();
    }
 /*
    public Connection getConnection()  {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@168.78.201.181:1521:ora8i";
        String userid = "mailother";
        String passwd = "mail1357";
        Connection  conn    = null;

        try
        {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userid, passwd);
//            return conn;
        }
        catch(Exception e) {
            e.printStackTrace();
//            throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "���� ���� ���� DATABASE CONNECTION ������ ����");
        }
		return conn;
    }
*/

    public int SendMailByUUID(String uuid, String toMail) throws TaxInvoiceException {
        System.out.println("[START SendMailByUUID]");
        Connection con = null;
        DBConnector dbconn = new DBConnector();
        PreparedStatement pstmt = null;
        int nResult = -1;

        try{
            con = dbconn.getConnection();
            con.setAutoCommit(false);

            String sql =
            		"INSERT INTO WEBADM.IM_DMAIL_INFO_13@CYBER_MAIL_LNK(SEQIDX, SUBJECT, SQL, REJECT_SLIST_IDX, BLOCK_GROUP_IDX, MAILFROM, MAILTO, REPLYTO, "+
            		"	ERRORSTO, HTML, ENCODING, CHARSET, "+
            		"	SDATE, TDATE, "+
            		"	DURATION_SET, CLICK_SET, SITE_SET, ATC_SET, GUBUN, RNAME, MTYPE, U_IDX, G_IDX, MSGFLAG, CONTENT) "+
            		"SELECT WEBADM.IM_DMAIL_SEQ_13.NEXTVAL@CYBER_MAIL_LNK, SUBJECT, ";

            if (toMail.equals("")) {
            	sql = sql + "	SQL, ";
            } else {
            	sql = sql + "'SSV:"+toMail+"', ";
            }

            sql = sql +	"	REJECT_SLIST_IDX, BLOCK_GROUP_IDX, MAILFROM, MAILTO, REPLYTO, "+
            		"	ERRORSTO, HTML, ENCODING, CHARSET, "+
            		"	TO_CHAR(SYSDATE - 60/24/60, 'YYYYMMDDHH24MISS'), TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), "+
            		"	DURATION_SET, CLICK_SET, SITE_SET, ATC_SET, GUBUN, RNAME, MTYPE, U_IDX, G_IDX, MSGFLAG, CONTENT "+
            		"FROM (select * "+
            		"		FROM ETS_TAX_IM_DMAIL_INFO "+
            		"		where uuid = ? "+
            		"		order by SDATE desc) "+
            		"where rownum=1 ";

           // System.out.println("mailSQL:"+sql);

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, uuid);
            nResult = pstmt.executeUpdate();
            pstmt.close();

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
        	 if(pstmt != null)
                 try {
                     pstmt.close();
                     if(con != null) con.close();
                     //dbconn.closeConnection(con);                     
                 } catch (SQLException e1) {
                     e1.printStackTrace();
                 }
        }
        System.out.println("[END SendMailByUUID]");
        return nResult;
    }

    public void insertSendMailInfo(TaxMailVO vo) throws TaxInvoiceException {
        System.out.println("[START insertSendMailInfo]");
        Connection con = null;
        DBConnector dbconn = new DBConnector();
        PreparedStatement pstmt = null;
        try{

            con = dbconn.getConnection();
            con.setAutoCommit(false);

            int nResult = -1;
            StringBuffer mailQuery = new StringBuffer();
            mailQuery.append("INSERT INTO ETS_TAX_IM_DMAIL_INFO(UUID, SUBJECT, SQL, REJECT_SLIST_IDX, BLOCK_GROUP_IDX, MAILFROM, MAILTO, REPLYTO, ");
            mailQuery.append("ERRORSTO, HTML, ENCODING, CHARSET, SDATE, TDATE, DURATION_SET, CLICK_SET, SITE_SET, ATC_SET, GUBUN, RNAME, MTYPE, ");
            mailQuery.append("U_IDX, G_IDX, MSGFLAG, CONTENT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
            mailQuery.append("TO_CHAR(SYSDATE - 60/24/60, 'YYYYMMDDHH24MISS'), TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            int index = 1;
            byte byteContents[] = vo.getContents(vo.getDocUuid());
            Reader data = new InputStreamReader(new ByteArrayInputStream(byteContents));
            pstmt = con.prepareStatement(mailQuery.toString());

            pstmt.setString(index++, vo.getDocUuid());
            pstmt.setString(index++, vo.getTitile());
            pstmt.setString(index++, "SSV:"+vo.getToMail());
            pstmt.setInt(index++, 0);
            pstmt.setInt(index++, 0);
            pstmt.setString(index++, "\""+vo.getFromName()+"\"<"+vo.getFromMail()+">");
            pstmt.setString(index++, "\""+vo.getToName()+"\"<"+vo.getToMail()+">");
            pstmt.setString(index++, "1");
            pstmt.setString(index++, "1");
            pstmt.setInt(index++, 1);
            pstmt.setInt(index++, 0);
            pstmt.setString(index++, "euc-kr");
            pstmt.setInt(index++, 0);
            pstmt.setInt(index++, 0);
            pstmt.setInt(index++, 0);
            pstmt.setInt(index++, 0);
            pstmt.setString(index++, "���ڼ��ݰ�꼭");
            pstmt.setString(index++, "������");
            pstmt.setInt(index++, 0);
            pstmt.setInt(index++, 1);
            pstmt.setInt(index++, 1);
            pstmt.setInt(index++, 0);
            pstmt.setCharacterStream(index++, data, byteContents.length);
            nResult = pstmt.executeUpdate();
            if(nResult > 0) {
            	con.commit();
            	con.setAutoCommit(true);
            }

        }
        /*
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
        */
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
        	 if(pstmt != null)
                 try {
                     pstmt.close();
                     if(con != null) con.close();
                     //dbconn.closeConnection(con);
                 } catch (SQLException e1) {
                     e1.printStackTrace();
                 }
        }

        System.out.println("[END insertSendMailInfo]");
    }


    public String sendMail(TaxMailVO vo) throws TaxInvoiceException{
    	System.out.println("==public String sendMail(TaxMailVO vo) throws TaxInvoiceException=="+ CommonUtil.getCurrentTime());

//    	insertSendMailInfo(vo);

        //Connection mailConn = this.getConnection();
    	Connection con = null;
    	DBConnector DBConn = new DBConnector();
    	
        PreparedStatement pstmt = null;
        String msg = "OK";

        if (DBConn != null) {

	        try {
	        	
	        	con = DBConn.getConnection();
	            
	        	 int nResult = -1;

	            System.out.println(vo.getFromMail());
	            System.out.println(vo.getToMail());
	            StringBuffer mailQuery = new StringBuffer();

	            /*mailQuery.append("INSERT INTO WEBADM.IM_DMAIL_INFO_13(SEQIDX, SUBJECT, SQL, REJECT_SLIST_IDX, BLOCK_GROUP_IDX, MAILFROM, MAILTO, REPLYTO, ");
	            mailQuery.append("ERRORSTO, HTML, ENCODING, CHARSET, SDATE, TDATE, DURATION_SET, CLICK_SET, SITE_SET, ATC_SET, GUBUN, RNAME, MTYPE, ");
	            mailQuery.append("U_IDX, G_IDX, MSGFLAG, CONTENT) VALUES(WEBADM.IM_DMAIL_SEQ_13.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
	            mailQuery.append("TO_CHAR(SYSDATE - 60/24/60, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                */
//	            mailQuery.append("INSERT INTO WEBADM.IM_DMAIL_INFO_13@CYBER_MAIL_LNK(SEQIDX, SUBJECT, SQL, REJECT_SLIST_IDX, BLOCK_GROUP_IDX, MAILFROM, MAILTO, REPLYTO, ");
//	            mailQuery.append("ERRORSTO, HTML, ENCODING, CHARSET, SDATE, TDATE, DURATION_SET, CLICK_SET, SITE_SET, ATC_SET, GUBUN, RNAME, MTYPE, ");
//	            mailQuery.append("U_IDX, G_IDX, MSGFLAG, CONTENT) VALUES(WEBADM.IM_DMAIL_SEQ_13.NEXTVAL@CYBER_MAIL_LNK, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
//	            mailQuery.append("TO_CHAR(SYSDATE - 60/24/60, 'YYYYMMDDHH24MISS'), TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	          //2016.05.12 EMAIL�߼۹�� ����(������ : CDH)	            
	            mailQuery.append("INSERT INTO IF_IM_DMAIL_INFO(ID,SEQIDX, SUBJECT, SQL, REJECT_SLIST_IDX, BLOCK_GROUP_IDX, MAILFROM, MAILTO, REPLYTO, ");
	            mailQuery.append("ERRORSTO, HTML, ENCODING, CHARSET, SDATE, TDATE, DURATION_SET, CLICK_SET, SITE_SET, ATC_SET, GUBUN, RNAME, MTYPE, ");
	            mailQuery.append("U_IDX, G_IDX, MSGFLAG, CONTENT, TABLE_GB,IF_STATUS ,CREATE_ID,CREATE_DT ,UPDATE_ID ,UPDATE_DT) VALUES('M'||IF_IM_DMAIL_INFO_ID.NEXTVAL,'', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
	            mailQuery.append("TO_CHAR(SYSDATE - 60/24/60, 'YYYYMMDDHH24MISS'), TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'13','','TaxMailManagementDao',SYSDATE,'','')");
	            con.setAutoCommit(false);

	            int index = 1;
	            byte byteContents[] = vo.getContents(vo.getDocUuid());
	            
	            Reader data = new InputStreamReader(new ByteArrayInputStream(byteContents));
	            pstmt = con.prepareStatement(mailQuery.toString());

	            pstmt.setString(index++, vo.getTitile());   //SUBJECT
	            
	            /* ip �����о�� */
				String serverIP = InetAddress.getLocalHost().getHostAddress();
				/* Test server */
				
				/*if("168.78.201.224".equals(serverIP)){
					pstmt.setString(index++, "SSV:sock_007@naver.com");
				}else{
					pstmt.setString(index++, "SSV:"+vo.getToMail());
				}
				
	            pstmt.setInt(index++, 0);
	            pstmt.setInt(index++, 0);
	            pstmt.setString(index++, "\""+vo.getFromName()+"\"<"+vo.getFromMail()+">");
	            pstmt.setString(index++, "\""+vo.getToName()+"\"<"+vo.getToMail()+">");
	            pstmt.setString(index++, "\"�赵��\"<kimdy@kdn.com>");
	            pstmt.setString(index++, "\"�赵��\"<kimdy@kdn.com>");
	            pstmt.setInt(index++, 1);
	            pstmt.setInt(index++, 0);
	            pstmt.setString(index++, "euc-kr");
	            pstmt.setInt(index++, 0);
	            pstmt.setInt(index++, 0);
	            pstmt.setInt(index++, 0);
	            pstmt.setString(index++, "���ڼ��ݰ�꼭");
	            pstmt.setString(index++, "������");
	            pstmt.setInt(index++, 0);
	            pstmt.setInt(index++, 1);
	            pstmt.setInt(index++, 1);
	            pstmt.setInt(index++, 0);
	            pstmt.setCharacterStream(index++, data, byteContents.length);*/
		         if ("168.78.201.224".equals(serverIP))
		             pstmt.setString(index++, "SSV:person1697@hanmail.net");  //SQL
		           else {
		             pstmt.setString(index++, "SSV:" + vo.getToMail());   //SQL
		           }
		   
		           pstmt.setInt(index++, 0);     //REJECT_SLIST_IDX
		           pstmt.setInt(index++, 0);     //BLOCK_GROUP_IDX
		           pstmt.setString(index++, "\"" + vo.getFromName() + "\"<" + vo.getFromMail() + ">");  //MAILFROM
		           pstmt.setString(index++, "\"" + vo.getToName() + "\"<" + vo.getToMail() + ">");     //MAILTO
		           pstmt.setString(index++, "1");   //REPLYTO
		           pstmt.setString(index++, "1");   //ERRORSTO
		           pstmt.setInt(index++, 1);        //HTML
		           pstmt.setInt(index++, 0);        //ENCODING
		           pstmt.setString(index++, "euc-kr"); //CHARSET
		           pstmt.setInt(index++, 0);          //DURATION_SET
		           pstmt.setInt(index++, 0);          //CLICK_SET
		           pstmt.setInt(index++, 0);          //SITE_SET
		           pstmt.setInt(index++, 0);          //ATC_SET
		           pstmt.setString(index++, "���ڼ��ݰ�꼭");  //GUBUN
		           pstmt.setString(index++, "������");  // RNAME
		           pstmt.setInt(index++, 0);      //MTYPE
		           pstmt.setInt(index++, 1);      //U_IDX
		           pstmt.setInt(index++, 1);      //G_IDX
		           pstmt.setInt(index++, 0);      //MSGFLAG
		           pstmt.setCharacterStream(index++, data, byteContents.length);  //CONTENT
				
	            nResult = pstmt.executeUpdate();
	//            System.out.println(new String(byteContents));
	            if(nResult > 0) {
	                con.commit();
	                con.setAutoCommit(true);
	                System.out.println("Mail Send OK!!!" + CommonUtil.getCurrentTime());
	            }
	        } catch(Exception e) {
	            msg = "Error";
	            e.printStackTrace();
	            try {
	            	con.rollback();
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	//                throw new TaxInvoiceException(TaxInvoiceException.RDB_EXCEPTION, "���� ���ۼ��� DB�� DATA ISERT�� ����");
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
     * @param invoicer_party_name
     * @param invoicer_party_id
     * @param invoicer_contact_name
     * @param invoicer_contact_phone
     * @param issue_id2
     * @return
     * @throws TaxInvoiceException
     */
    public String tax_seadMail(String invoicer_party_name,
    							String invoicer_party_id,
    							String invoicer_contact_name,
    							String invoicer_contact_phone,
    							String issue_id2,
    							String invoicee_contact_email1,
    							String invoicee_contact_name1)throws TaxInvoiceException{
    	
    	String remsg = null;
    	//Connection conn = this.getConnection();
    	Connection con = null;
    	DBConnector DBConn = new DBConnector();
    	
    	PreparedStatement ps = null;
    	
    	String msg="";
    	String siteUrl = "";
    	String contents ="";
		String enc_taxid;
		TaxInvSecurityMgr SecuMgr = new TaxInvSecurityMgr();
		enc_taxid = SecuMgr.TaxInvEncrypt(issue_id2);
		enc_taxid = SecuMgr.encodeURIComponent(enc_taxid);//POST �ѱ�� ��� ��� ���ڿ� ���� ������ ���� encodeó�� 

    	/* */
    	//String invoicer_party_name = "";		// ���޾�ü���ü��
    	//String invoicer_party_id = "";			// ������ ����ڵ�Ϲ�ȣ
    	//String invoicer_contact_name = "";		// ���޾�ü ����ڸ�
    	//String invoicer_contact_phone = "";		// ���޾�ü ����� ��ȭ��ȣ
    	//String issue_id2 = "";					// ���ι�ȣ
    	//String invoicee_contact_email1 = "";		// ���޹޴¾�ü �����
    	try{
    		
    		con = DBConn.getConnection();
    		
    		String serverIP = InetAddress.getLocalHost().getHostAddress();
    	
    		issue_id2 = CommonUtil.StringCipher(issue_id2);
    		
	    	if("168.78.201.224".equals(serverIP)){
	    		siteUrl = "https://168.78.201.224/kepcobill2";
	    	}else{
	    		siteUrl = "https://cat.kepco.net/kepcobill2";
	    	}
    	
    	// ���޾�ü���ü��
    	// ������ ����ڵ�Ϲ�ȣ
    	// ���޾�ü ����ڸ�
    	// ���޾�ü ����� ��ȭ��ȣ
    	// ���ι�ȣ
    	
    	contents =
			"<html>\n"+
		    	"<head>\n"+
		    	"<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>\n"+
		    	"</head>\n"+
		    	"<body>\n"+
		    	"<table width='720' border='0' cellspacing='0' cellpadding='0'>\n"+
		    	"	<tr>\n"+
		    	"		<td align='CENTER' valign='TOP'><br>\n"+
		    	"			<table width='685' border='0' cellspacing='0' cellpadding='0'>\n"+
		    	"				<tr>\n"+
		    	"					<td align='LEFT' valign='TOP'><img src='"+siteUrl+"/images/tax_img.gif' width='685' height='83' align='ABSMIDDLE'></td>\n"+
		    	"				</tr>\n"+
		    	"			</table><br>\n"+
		    	"			<table width='600' border='0' cellspacing='0' cellpadding='0'>\n"+
		    	"				<tr>\n"+
		    	"					<td align='LEFT' valign='TOP'>\n"+
		    	"						<table width='100%' border='0' cellpadding='1' cellspacing='1' bgcolor='#DCD9D9'>\n"+
		    	"							<tr>\n"+
		    	"								<td height='21' colspan='2' align='CENTER' bgcolor='#C8E0E9'><strong> <font size=2>������ ���ݰ�꼭 �����Դϴ�.</font></strong></td>\n"+
		    	"							</tr><tr>\n"+
		    	"								<td width='35%' height='21' bgcolor='#E9E9E9'>&nbsp;&nbsp;&nbsp;<img src='"+siteUrl+"/images/bullet08.gif' width='14' height='3' align='ABSMIDDLE'><font size=2>�۽�ȸ��� </font></td>\n"+
		    	"								<td width='65%' >&nbsp;<font size=2>"+invoicer_party_name+"</font></td>\n"+
		    	"							</tr><tr>\n"+
		    	"								<td height='21' bgcolor='#E9E9E9'>&nbsp;&nbsp;&nbsp;<img src='"+siteUrl+"/images/bullet08.gif' width='14' height='3' align='ABSMIDDLE'><font size=2>�۽�ȸ�� ����ڹ�ȣ</font></td>\n"+
		    	"								<td  >&nbsp;<font size=2>"+invoicer_party_id+"</font></td>\n"+
		    	"							</tr><tr>\n"+
		    	"								<td height='21' bgcolor='#E9E9E9'>&nbsp;&nbsp;&nbsp;<img src='"+siteUrl+"/images/bullet08.gif' width='14' height='3' align='ABSMIDDLE'><font size=2>�۽�ȸ�� ������̸�</font></td>\n"+
		    	"								<td >&nbsp;<font size=2>"+invoicer_contact_name+"</font></td>\n"+
		    	"							</tr><tr>\n"+
		    	"								<td height='21' bgcolor='#E9E9E9'>&nbsp;&nbsp;&nbsp;<img src='"+siteUrl+"/images/bullet08.gif' width='14' height='3' align='ABSMIDDLE'><font size=2>�۽�ȸ�� ����� ��ȭ��ȣ</font></td>\n"+
		    	"								<td >&nbsp;<font size=2>"+invoicer_contact_phone+"</font></td>\n"+
		    	"							</tr>\n"+
		    	"						</table>\n"+
		    	"						<table width='100%' border='0' cellspacing='0' cellpadding='0'>\n"+
		    	//"							<tr><td height='40' align='CENTER' valign='BOTTOM'><a href='"+siteUrl+"/etax/etax_view.jsp?taxid="+issue_id2+"' target='_blank'><img src='"+siteUrl+"/images/b_taxsee.gif' width='145' height='20' align='ABSMIDDLE' border=0></a></td></tr>\n"+
                //		    	2016.10.20 ��ũ�ּҿ� AES��ȣȭ�� ���� AES��ȣȭ �� �߰�  
		    	"							<tr><td height='40' align='CENTER' valign='BOTTOM'><a href='"+siteUrl+"/etax/etax_view.jsp?taxid="+issue_id2+"&enc_taxid="+enc_taxid+"' target='_blank'><img src='"+siteUrl+"/images/b_taxsee.gif' width='145' height='20' align='ABSMIDDLE' border=0></a></td></tr>\n"+
		    	"						</table><br>\n"+
		    	"					</td>\n"+
		    	"				</tr>\n"+
		    	"			</table>\n"+
		    	"			<table width='100%' border='0' cellspacing='0' cellpadding='0'>\n"+
		    	"				<tr>\n"+
		    	"					<td height='40' valign='BOTTOM'><font size=2>&nbsp;&nbsp;&nbsp;�� [���ݰ�꼭 ������ ����] ��ư�� �۵����� ���� ��� �˾� ������ �Ͽ����� �����Ͻñ� �ٶ��ϴ�.<br>&nbsp;&nbsp;&nbsp;<font color=blue>(���� ��� : �������޴� �� ���� > �˾�����> �˾����� ��� ���� ����)</font></font></td>\n"+
		    	"				</tr><tr>\n"+
		    	"					<td height='40' valign='BOTTOM'><font size=2>�� ���ݰ�꼭�� [���ڼ��ݰ�꼭�ý���]���� ���ŵ� Ȯ���Ͻ� �� �ֽ��ϴ�.</td>\n"+
		    	"				</tr><tr>\n"+
		    	"					<td valign='BOTTOM'>\n"+
		    	"						<font size=2> - ������������ : <font color=blue>https://srm.kepco.net</font> �� �α����Ͽ� �ϴ��� <font color=blue>[���ڼ��ݰ��]</font>�ý��� Ŭ��</font>\n"+
		    	"				</td></tr>\n"+
		    	"			</table>\n"+
		    	"			<table width='685' border='0' cellspacing='0' cellpadding='0'>\n"+
		    	"				<tr><td align='RIGHT' valign='BOTTOM'><img src='"+siteUrl+"/images/copyright.gif' width='416' height='45' align='ABSMIDDLE'></td></tr>\n"+
		    	"			</table>\n"+
		    	"		</td>\n"+
		    	"	</tr>\n"+
		    	"</table>\n"+
		    	"</body>\n"+
		    	"</html>\n";
    			
    			Reader data = new InputStreamReader(new ByteArrayInputStream(contents.getBytes()));
				StringBuffer sb = new StringBuffer()
//		        .append("INSERT INTO WEBADM.IM_DMAIL_INFO_13@CYBER_MAIL_LNK(                                                              \n")
//		        .append("SEQIDX, SUBJECT, SQL, REJECT_SLIST_IDX, BLOCK_GROUP_IDX, MAILFROM,MAILTO, REPLYTO,                \n")
//		        .append("ERRORSTO, HTML, ENCODING, CHARSET, SDATE, TDATE, DURATION_SET, CLICK_SET,                         \n")
//		        .append("SITE_SET, ATC_SET, GUBUN, RNAME, MTYPE, U_IDX, G_IDX, MSGFLAG, CONTENT)                           \n")
//		        .append("VALUES (                                                                                          \n")
//		        .append("WEBADM.IM_DMAIL_SEQ_13.NEXTVAL@CYBER_MAIL_LNK, '���ڼ��� ��꼭', ?, 0, 0, ?, ?, ?, '1', 1, 0, 'euc-kr',         	   \n")
//		        .append("TO_CHAR(SYSDATE - 60/24/60, 'YYYYMMDDHH24MISS'), TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), 0, 0,      \n")
//		        .append("0, 0, '���ڼ��� ��꼭', '������', 0, 1, 1, 0, ?)                                                 	   \n");

		        .append("INSERT INTO IF_IM_DMAIL_INFO(                                                              \n")
		        .append("ID, SEQIDX, SUBJECT, SQL, REJECT_SLIST_IDX, BLOCK_GROUP_IDX, MAILFROM,MAILTO, REPLYTO,                \n")
		        .append("ERRORSTO, HTML, ENCODING, CHARSET, SDATE, TDATE, DURATION_SET, CLICK_SET,                         \n")
		        .append("SITE_SET, ATC_SET, GUBUN, RNAME, MTYPE, U_IDX, G_IDX, MSGFLAG, CONTENT, TABLE_GB,IF_STATUS ,CREATE_ID,CREATE_DT ,UPDATE_ID ,UPDATE_DT)                           \n")
		        .append("VALUES (                                                                                          \n")
		        .append("'M'||IF_IM_DMAIL_INFO_ID.NEXTVAL,'', '���ڼ��ݰ�꼭', ?, 0, 0, ?, ?, ?, '1', 1, 0, 'euc-kr',         	   \n")
		        .append("TO_CHAR(SYSDATE - 60/24/60, 'YYYYMMDDHH24MISS'), TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), 0, 0,      \n")
		        .append("0, 0, '���ڼ��ݰ�꼭', '������', 0, 1, 1, 0, ?,'13','','TaxMailManagementDao',SYSDATE,'','')                                                 	   \n");
				ps = con.prepareStatement(sb.toString());
				
				if("168.78.201.224".equals(serverIP)){
          			ps.setString(1,"SSV:"+"no-betterthan8@kdn.com");
          		}else{
          			ps.setString(1,"SSV:"+invoicee_contact_email1);
          		}
				
				//ps.setString(2,  "���� ���ڼ��ݰ�꼭");
				ps.setString(2,  "\"�������ݰ�꼭[�۽�����]\"<deliver@cat.kepco.net>");
				
				if("168.78.201.224".equals(serverIP)){
					invoicee_contact_name1 = "������";
					invoicee_contact_email1 = "no-betterthan8@kdn.com";
					ps.setString(3,"\""+invoicee_contact_name1+"\"<"+invoicee_contact_email1+">");
					
				}else{
					
					ps.setString(3,"\""+invoicee_contact_name1+"\"<"+invoicee_contact_email1+">");
					
    			}	
				ps.setString(4, "1");
        		ps.setCharacterStream(5, data, contents.getBytes().length);
        		
        		if(ps.executeUpdate() > 0){
        			con.commit();
        			 System.out.println("Mail Send OK!!!" + CommonUtil.getCurrentTime());
        		}      		
    	
    	}catch (Exception e) {
    		msg = "Error";
			// TODO: handle exception
    		e.printStackTrace();
    		try{
    			con.rollback();
    		}catch (SQLException e1) {
				// TODO: handle exception
    			e1.printStackTrace();
			}
		}finally {
            if(ps != null)
                try {
                    ps.close();
                    if(con != null) con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
        }
    	return msg;
    }

/*
    public static void main(String[] args) {
        PropertyUtil.loadProperty("C:\\kepcobill_src\\kepcobill_dev\\taxInvoice\\webapps\\WEB-INF\\");
        TaxMailManagementDao mailDao = new TaxMailManagementDao();
        TaxMailVO vo = new TaxMailVO("0000000000904344","buyer");
        vo.setDocUuid("0000000000904344");
        vo.setTitile("�׽�Ʈ ����");
        vo.setFromMail("test");
        vo.setFromName("�׽���");
        vo.setToMail("pshpsy@nate.com");
        vo.setToName("�ڼ���");
        try {
            mailDao.sendMail(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
}
