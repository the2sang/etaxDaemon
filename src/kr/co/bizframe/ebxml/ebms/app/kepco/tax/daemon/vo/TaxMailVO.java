/*
 * Created on 2005. 8. 29.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo;

import java.io.Serializable;
import java.net.InetAddress;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb.TaxManagementDao;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.util.TaxInvSecurityMgr;

/**
 * @author shin sung uk
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TaxMailVO extends Object implements Serializable {
	private String toMail = "";

	private String fromMail = "";

	private String titile = "";

	private String toName = "";

	
	private String fromName = "";

	private String contents = "";

	private String docUuid = "";
	
	
	private String lineName = "";

	public TaxMailVO(String uuid, String sendTo, String doc_date, String names, String email, String phone) {
		String jspPage = "";
		String siteUrl = "https://cat.kepco.net/kepcobill";
		String url = "";
		String isHanjun = "N";
		String isSender = "N";

		String name = "";
		String biz_id = "";
		String contactor_name = "";
		String contactor_tel = "";
		String mail = "";
		String enc_uuid;
		TaxInvSecurityMgr SecuMgr = new TaxInvSecurityMgr();
		enc_uuid = SecuMgr.TaxInvEncrypt(uuid);
		enc_uuid = SecuMgr.encodeURIComponent(enc_uuid);//POST 넘기는 경우 몇몇 문자열 깨짐 방지를 위한 encode처리 

		try{
    		/* ip 정보읽어옴 */
    		String serverIP = InetAddress.getLocalHost().getHostAddress();
    		/* Test server */
			if("168.78.201.224".equals(serverIP)){
				siteUrl = "https://dev-cat.kepco.net/kepcobill";
			}
    	}catch (Exception e) {
			// TODO: handle exception
    		System.out.println(e.toString());
		}

		int date = Integer.parseInt(doc_date.substring(0, 4)); // 날짜구분위해 추가20100101
    	String ee = "N";
    	if(date >= 2010){ ee = "Y";}
    	System.out.println("doc_date ::: "+doc_date);
    	System.out.println("ee ::: "+ee);

		TaxManagementDao taxDao = new TaxManagementDao();
		TaxInvoiceVO vo = new TaxInvoiceVO();
		try {
			vo = taxDao.getTaxInvoiceVO(uuid);
			
			System.out.println("VoSize ===============> "+vo.getLine().size());
			
			if(vo.getLine().size()> 0 ){
				TaxLineVO temp_vo = new TaxLineVO();
				temp_vo = (TaxLineVO) vo.getLine().get(0);
				lineName = temp_vo.getName();
			}
			
			
		} catch (TaxInvoiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String buyer_id = vo.getMain().getBuyer_id();
		String receiver_comp_id = vo.getMeta().getReceiver_comp_id();
		String buyer_comp_id = vo.getMain().getBuyer_biz_id();
		String sup_id = vo.getMain().getSupplier_id();
		String sender_comp_id = vo.getMeta().getSender_comp_id();
		String sup_comp_id = vo.getMain().getSupplier_biz_id();

		if (sendTo.equals("supplier")) {
			jspPage = "IncludeSupplierMail.jsp";

			// if(sup_id.length() == 8)
			// isHanjun = "Y";
			if (sender_comp_id.equals(sup_comp_id))
				isSender = "Y";

			name = vo.getMain().getBuyer_name();
			biz_id = vo.getMain().getBuyer_biz_id();
			//
//2015.12.07 내선계기 메일보낸이를 계약자로 변경 CDH
//			contactor_name = vo.getMain().getBuyer_contactor_name();
//			contactor_tel = vo.getMain().getBuyer_contactor_tel();
//			mail = vo.getMain().getBuyer_contactor_email();
			contactor_name = names;
			contactor_tel = phone;
			mail = email;
			System.out.println("############################names "+ names+ " phone :  "+phone+" email :  "+email);
		} else if (sendTo.equals("buyer")) {
			jspPage = "IncludeBuyerMail.jsp";

			if (buyer_id.length() == 8)
				isHanjun = "Y";
			if (receiver_comp_id.equals(buyer_comp_id))
				isSender = "N";

			name = vo.getMain().getSupplier_name();
			biz_id = vo.getMain().getSupplier_biz_id();
			contactor_name = vo.getMain().getSupplier_contactor_name();
			contactor_tel = vo.getMain().getSupplier_contactor_tel();
			mail = vo.getMain().getBuyer_contactor_email();
		}
		
System.out.println("############################2222isHanjun "+ isHanjun+ " isSender :  "+isSender);
System.out.println("############################2222buyer_id "+ buyer_id+ " receiver_comp_id :  "+receiver_comp_id );
System.out.println("############################2222buyer_comp_id "+ buyer_comp_id+ " sup_id :  "+sup_id );
System.out.println("############################2222sender_comp_id "+ sender_comp_id+ " sup_comp_id :  "+sup_comp_id );

		if (isHanjun.equals("Y")) {
			if (isSender.equals("Y")) {
				if(ee.equals("N")){
					url = siteUrl + "/docView/TaxInvHanjunSendView.jsp?uuid="
						+ uuid + "&enc_uuid="+enc_uuid;
				}else{
					url = siteUrl +"/docView/ViewHanjunSand.jsp?uuid="
						+ uuid + "&enc_uuid="+enc_uuid; // 신규
				}
			} else {
				if(ee.equals("N")){
					url = siteUrl + "/docView/TaxInvHanjunReceiveView.jsp?uuid="
						+ uuid + "&enc_uuid="+enc_uuid;
				}else{
					url = siteUrl +"/docView/ViewHanjunReceive.jsp?uuid="
						+uuid + "&enc_uuid="+enc_uuid; // 신규
				}
			}
		} else {
			if (isSender.equals("Y")) {
				if(ee.equals("N")){
					url = siteUrl + "/docView/TaxInvOutterSendView.jsp?uuid="
						+ uuid + "&enc_uuid="+enc_uuid;
				}else{
					url = siteUrl +"/index_EP.jsp?menu=mail&isHanjun=N&uuid="+uuid+"&enc_uuid="+enc_uuid; // 신규
				}
			} else {
				if(ee.equals("N")){
					url = siteUrl + "/docView/TaxInvOutterReceiveView.jsp?uuid="
						+ uuid + "&enc_uuid="+enc_uuid;
				}else{
					url = siteUrl +"/index_EP.jsp?menu=mail&isHanjun=N&uuid="+uuid+"&enc_uuid="+enc_uuid; // 신규
				}
			}
		}
		System.out.println("url============================================================>"+url);
		docUuid = uuid;

		contents = "<html>\n"
				+ "<head>\n"
				+ "<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>\n"
				+

				// link,script,iframe,object,style 등등 특정메일에서는 적용안됨
				// "<link href='"+siteUrl+"/css/ur_ie6.css' rel='stylesheet'
				// type='text/css'>\n"+
				// "<script language='javascript'>\n"+
				// " function viewDoc(uuid){\n"+
				// "
				// window.open('"+url+"','viewDoc','toolbar=no,location=no,directories=no,menubar=no,resizable=yes,scrollbars=yes,status=yes,width=750,height=870,top=0,left=0');\n"+
				// " }\n"+
				// "</script>\n"+
				// "<style type='text/css'>\n"+
				// " .urCellBgHeader{background-color:#C8E0E9;font-family:
				// '굴림';font-size: 12px;font-style: normal;font-weight:
				// normal;color: #666666;text-decoration: none;}\n"+
				// " .urCellBgFill2{background-color:#FFFFFF;font-family:
				// '돋움';font-size: 12px;font-style: normal;font-weight:
				// normal;color: #666666;text-decoration: none;}\n"+
				// " .urTxtStd{font-family:Arial, Helvetica,
				// sans-serif;font-size:12px;font-style:normal;color:#666666;font-weight:normal}\n"+
				// " .s{background-color:#E9E9E9}\n"+
				// " .urEdf2TxtEnbl{border:none; padding:1px 6px 2px 6px;
				// !important;width:100%; background-color:transparent}\n"+
				// "</style>\n"+

				"</head>\n"
				+ "<body>\n"
				+ "<table width='720' border='0' cellspacing='0' cellpadding='0'>\n"
				+ "	<tr>\n"
				+ "		<td align='CENTER' valign='TOP'><br>\n"
				+ "			<table width='685' border='0' cellspacing='0' cellpadding='0'>\n"
				+ "				<tr>\n"
				+ "					<td align='LEFT' valign='TOP'><img src='"
				+ siteUrl
				+ "/images/tax_img.gif' width='685' height='83' align='ABSMIDDLE'></td>\n"
				+ "				</tr>\n"
				+ "			</table><br>\n"
				+ "			<table width='600' border='0' cellspacing='0' cellpadding='0'>\n"
				+ "				<tr>\n"
				+ "					<td align='LEFT' valign='TOP'>\n"
				+ "						<table width='100%' border='0' cellpadding='1' cellspacing='1' bgcolor='#DCD9D9'>\n"
				+ "							<tr>\n"
				+ "								<td height='21' colspan='2' align='CENTER' bgcolor='#C8E0E9'><strong> <font size=2>수신한 세금계산서 정보입니다.</font></strong></td>\n"
				+ "							</tr><tr>\n"
				+ "								<td width='35%' height='21' bgcolor='#E9E9E9'>&nbsp;&nbsp;&nbsp;<img src='"
				+ siteUrl
				+ "/images/bullet08.gif' width='14' height='3' align='ABSMIDDLE'><font size=2>송신회사명 </font></td>\n"
				+ "								<td width='65%' bgcolor='#FFFFFF'>&nbsp;<font size=2>"
				+ name
				+ "</font>\n"
				+ "								</td>\n"
				+ "							</tr><tr>\n"
				+ "								<td height='21' bgcolor='#E9E9E9'>&nbsp;&nbsp;&nbsp;<img src='"
				+ siteUrl
				+ "/images/bullet08.gif' width='14' height='3' align='ABSMIDDLE'><font size=2>송신회사 사업자번호</font></td>\n"
				+ "								<td  bgcolor='#FFFFFF'>&nbsp;<font size=2>"
				+ biz_id
				+ "</font>\n"
				+ "								</td>\n"
				+ "							</tr><tr>\n"
				+ "								<td height='21' bgcolor='#E9E9E9'>&nbsp;&nbsp;&nbsp;<img src='"
				+ siteUrl
				+ "/images/bullet08.gif' width='14' height='3' align='ABSMIDDLE'><font size=2>송신회사 담당자이름</font></td>\n"
				+ "								<td bgcolor='#FFFFFF'>&nbsp;<font size=2>"
				+ contactor_name
				+ "</font>\n"
				+ "								</td>\n"
				+ "							</tr><tr>\n"
				+ "								<td height='21' bgcolor='#E9E9E9'>&nbsp;&nbsp;&nbsp;<img src='"
				+ siteUrl
				+ "/images/bullet08.gif' width='14' height='3' align='ABSMIDDLE'><font size=2>송신회사 담당자 전화번호</font></td>\n"
				+ "								<td bgcolor='#FFFFFF'>&nbsp;<font size=2>"
				+ contactor_tel
				+ "</font>\n"
				+ "								</td>\n"
				+ "							</tr>\n"
				+ "						</table>\n"
				+ "						<table width='100%' border='0' cellspacing='0' cellpadding='0'>\n"
				+ "							<tr><td height='40' align='CENTER' valign='BOTTOM'><a href='"
				+ url
				+ "' target='_blank'><img src='"
				+ siteUrl
				+ "/images/b_taxsee.gif' width='145' height='20' align='ABSMIDDLE' border=0></a></td></tr>\n"
				+ "						</table>\n"
				+ "						<br>\n"
				+ "					</td>\n"
				+ "				</tr>\n"
				+ "			</table>\n"
				+ " 		<br>\n"
				+ " 		<table width='600' height='112' border='0' background='"
				+ siteUrl
				+"/images/background_mail.gif'>\n"
				+ " 			<tr>\n"
				+ "					<td width='60'>&nbsp;</td>\n"
				+ "					<td><strong><font size='4'>Clean Power KEPCO 9.9</font></strong></td></tr>\n"
				+ "					<td width='30'>&nbsp;</td>\n"
				+ "				</tr>\n"
				+ " 			<tr>\n"
				+ "					<td>&nbsp;</td>\n"
				+ "					<td><font size='3'>\n"
				+ name
				+ " , 더욱 청렴해지도록 노력하겠습니다.</font></td>\n"
				+ "					<td>&nbsp;</td>\n"
				+ "				</tr>\n"
				+ "				<tr>\n"
				+ " 				<td>&nbsp;</td>\n"
				+ " 				<td align=''><font color='red' size='3'>이의제기 및 정보공개</font> <font size='3'>전   화 :"
				+ contactor_tel
				+ " 					</font></td>\n"
				+ "					<td>&nbsp;</td>\n"
				+ "				</tr>\n"
				+ "				<tr>\n"
				+ "					<td>&nbsp;</td>\n"
				+ " 				<td align='right'><font size='2'>E-mail :"
				+ mail
				+ " 					</font></td>\n"
				+ "					<td>&nbsp;</td>\n"
				+ "				</tr>\n"
				+ "			</table>\n"
				+ "			<br>\n"
				+ "			<table width='100%' border='0' cellspacing='0' cellpadding='0'>\n"
				+ "				<tr>\n"
				+ "					<td height='40' valign='BOTTOM'><font size=2>&nbsp;&nbsp;&nbsp;※ [세금계산서 상세정보 보기] 버튼이 작동하지 않을 경우 팝업 차단을 하용으로 변경하시기 바랍니다.<br>&nbsp;&nbsp;&nbsp;<font color=blue>(설정 방법 : 브라우저메뉴 중 도구 > 팝업차단> 팝업차단 사용 안함 선택)</font></font></td>\n"
				+ "				</tr><tr>\n"
				+ "					<td height='40' valign='BOTTOM'><font size=2>※ 세금계산서는 [전자세금계산서시스템]으로 들어가셔도 확인하실 수 있습니다.</td>\n"
				+ "				</tr><tr>\n"
				+ "					<td valign='BOTTOM'>\n"
				+ "						<font size=2> - 한전전자조달 : <font color=blue>https://srm.kepco.net</font> 로 로그인하여 하단의 <font color=blue>[전자세금계산]</font>시스템 클릭</font><br>\n";

		if (isHanjun.equals("Y")) {
			contents = contents
					+ "<font size=2> - ERP Powerpia : <font color=blue>ERP Powerpia -> Workplace -> 재무 -> 일반회계 -> 전자세금계산서시스템</font> 클릭\n";
		}

		contents = contents
				+ "</td></tr>\n"
				+ "			</table>\n"
				+ "			<table width='685' border='0' cellspacing='0' cellpadding='0'>\n"
				+ "				<tr>\n"
				+ "					<td align='RIGHT' valign='BOTTOM'><img src='"
				+ siteUrl
				+ "/images/copyright.gif' width='416' height='45' align='ABSMIDDLE'></td>\n"
				+ "				</tr>\n" + "			</table>\n" + "		</td>\n" + "	</tr>\n"
				+ "</table>\n" + "</body>\n" + "</html>\n";

		/*
		 * contents = " <html> " + " <head> "+ " <meta http-equiv='Content-Type'
		 * content='text/html; charset=euc-kr'/>" + " <title>KDN</title>" + "
		 * </head> "+ " <body>" + " <table cellspacing=0 cellpadding=0
		 * width=750> " + " <tr> " + " <td width=704 height=82> " + " <div
		 * align=right>" + " <iframe
		 * src='http://cat.kepco.net/kepcobill/mail/"+jspPage+"?uuid="+uuid+"'
		 * frameborder='0' " + " width='800' height='400' marginwidth='0'
		 * marginheight='0' scrolling='no'></iframe></div>" + " </td>" + "
		 * </tr>" + " </table>" + " </body>" + " </html>" ;
		 */
		/*
		 * contents = " <html>\n" + " <head>\n"+ " <meta
		 * http-equiv='Content-Type' content='text/html; charset=euc-kr'/>\n" + "
		 * <title>세금계산서</title>\n" + " </head> \n"+ " <body>\n" + " <table
		 * cellspacing=0 cellpadding=0 width=750>\n" + " <tr> \n" + "
		 * <td width=704 height=82> \n" + " <div align=right>\n" + // " <iframe
		 * src='http://cat.kepco.net/kepcobill/mail/"+jspPage+"?uuid="+uuid+"'
		 * frameborder='0' \n" + // " width='800' height='400' marginwidth='0'
		 * marginheight='0' scrolling='no'></iframe>\n" + " <object
		 * id='kepcotaxmail' width='800' height='400' type='text/x-scriptlet'>
		 * \n" + " <param name='url'
		 * value='http://cat.kepco.net/kepcobill/mail/"+jspPage+"?uuid="+uuid+"'>
		 * \n" + " <param name='selectablecontent' value='0'> \n" + " <param
		 * name='scrollbar' value='1'>\n" + " </object> \n" + " </div></td>\n" + "
		 * </tr>\n" + " </table>\n" + " </body>\n" + " </html>\n" ;
		 */

	}

	/**
	 * @return Returns the contents.
	 */
	public byte[] getContents(String uuid) {
		// URLConnection url;
		// byte[] data = null;
		// try {
		// url = new
		// URL("http://localhost:8000/kepcobill/mail/IncludeMail.jsp?uuid="+uuid).openConnection();
		// BufferedInputStream is=new BufferedInputStream(url.getInputStream());
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// byte[] buf = new byte[4096];
		// int count = 0;
		// while ((count = is.read(buf)) != -1) {
		// baos.write(buf, 0, count);
		// }
		// is.close();
		// data = baos.toByteArray();
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		byte[] data = this.contents.getBytes();
		return data;
	}
	
	/**
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * @param lineName the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}


	/**
	 * @param contents
	 *            The contents to set.
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return Returns the docUuid.
	 */
	public String getDocUuid() {
		return docUuid;
	}

	/**
	 * @param docUuid
	 *            The docUuid to set.
	 */
	public void setDocUuid(String docUuid) {
		this.docUuid = docUuid;
	}

	/**
	 * @return Returns the fromMail.
	 */
	public String getFromMail() {
		return fromMail;
	}

	/**
	 * @param fromMail
	 *            The fromMail to set.
	 */
	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	/**
	 * @return Returns the fromName.
	 */
	public String getFromName() {
		return fromName;
	}

	/**
	 * @param fromName
	 *            The fromName to set.
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * @return Returns the titile.
	 */
	public String getTitile() {
		return titile;
	}

	/**
	 * @param titile
	 *            The titile to set.
	 */
	public void setTitile(String titile) {
		this.titile = titile;
	}

	/**
	 * @return Returns the toMail.
	 */
	public String getToMail() {
		return toMail;
	}

	/**
	 * @param toMail
	 *            The toMail to set.
	 */
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	/**
	 * @return Returns the toName.
	 */
	public String getToName() {
		return toName;
	}

	/**
	 * @param toName
	 *            The toName to set.
	 */
	public void setToName(String toName) {
		this.toName = toName;
	}

}
