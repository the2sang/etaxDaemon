package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.rdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.CommonUtil;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common.TaxInvoiceException;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxCompanyVO;
import kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.vo.TaxPersonVO;

public class TaxPersonNewDao {

	public Hashtable getOutterInfoForExcelPublish(String bizNO, Hashtable compHt, Connection con) throws SQLException, TaxInvoiceException
	{
		System.out.println("[START getAdminSupplierList in TaxSupplierDao]");
		//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
		String BUSI_ENC = CommonUtil.getString("BUSI_ENC");
		String sql = "";
		PreparedStatement ps = null;
		try{
//			String whereIn = "";
//			for(int i=0;i<bizList.size();i++){
//				if(i!=0) whereIn += ",";
//				whereIn += "ENCODE_SF@USER_LINK('"+(String)bizList.get(i)+"')";
//			}
			//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH			
			if(BUSI_ENC.equals("1")){  
				sql =
				//2015.12.02 차세대입찰 관련 작업  CDH
				"    SELECT DECODE_SF64@D_EDI2SRM(BUSINESS_NO) BIZ_ID, KORNAME,  OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 AS ADDR,	"   +
//				"    SELECT DECODE_SF_SNM(BUSINESS_NO) BIZ_ID, KORNAME,  OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 AS ADDR,  			"   +
				"    BUSINESS_STATUS, INDUSTRY_TYPE , c.USER_ID ,   c.USER_NAME,       c.TEL_NO,     c.EMAIL 					"   +
				"    , A.TEL_NO AS TEL, A.MOB_NUMBER , A.EMAIL AS MAIL															"	+
				//2015.12.02 차세대입찰 관련 작업  CDH
//				"    FROM TP_SUPPLIER_TBL@USER_LINK A, TP_OWNERLST_TBL@USER_LINK B   ,TP_EXUSER_TBL@USER_LINK C       			"   +
				"    FROM TP_SUPPLIER_TBL_VIEW A, TP_OWNERLST_TBL_VIEW B   ,TP_EXUSER_TBL_VIEW C 				      			"   +
//				"	 , TP_EXUSER_TBL_20101001@USER_LINK D																		"	+
				//2015.12.02 차세대입찰 관련 작업  CDH
				"    WHERE A.BUSINESS_NO = ENCODE_SF64@D_EDI2SRM(?)																"   +
//				"    WHERE A.BUSINESS_NO = ENCODE_SF_SNM(?)																		"   +
				"    AND A.SUPPLIER_NO = B.SUPPLIER_NO  																		"   +
				"    AND A.SUPPLIER_NO = c.SUPPLIER_NO																			"   +
				"    AND B.SUPPLIER_NO = c.SUPPLIER_NO  																		"   +
//				"	 AND C.SUPPLIER_NO = D.SUPPLIER_NO  																		"	+
						//2016.08.17 차세대입찰 관련 작업 CDH
				"     AND B.ID = A.ID																			"   +
				"    AND OWNER_PRIOR = '1'																						"	+
				"	 AND A.STATUS != 'D' 																						"	+
				"	 AND C.STATUS != 'D' 																						"	+
				" 	 order by  c.tax_sms_yn asc																					" ;
			}else{
				sql =
				//2015.12.02 차세대입찰 관련 작업  CDH
				"    SELECT BUSINESS_NO BIZ_ID, KORNAME,  OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 AS ADDR,  						"   +
//				"    SELECT DECODE_SF_SNM(BUSINESS_NO) BIZ_ID, KORNAME,  OWNER_NAME, A.ADDR1 ||' '|| A.ADDR2 AS ADDR,  			"   +
				"    BUSINESS_STATUS, INDUSTRY_TYPE , c.USER_ID ,   c.USER_NAME,       c.TEL_NO,     c.EMAIL 					"   +
				"    , A.TEL_NO AS TEL, A.MOB_NUMBER , A.EMAIL AS MAIL															"	+
				//2015.12.02 차세대입찰 관련 작업  CDH
//				"    FROM TP_SUPPLIER_TBL@USER_LINK A, TP_OWNERLST_TBL@USER_LINK B   ,TP_EXUSER_TBL@USER_LINK C       			"   +
				"    FROM TP_SUPPLIER_TBL_VIEW A, TP_OWNERLST_TBL_VIEW B   ,TP_EXUSER_TBL_VIEW C 				      			"   +
//				"	 , TP_EXUSER_TBL_20101001@USER_LINK D																		"	+
				//2015.12.02 차세대입찰 관련 작업  CDH
				"    WHERE A.BUSINESS_NO = ?																					"   +
//				"    WHERE A.BUSINESS_NO = ENCODE_SF_SNM(?)																		"   +
				//2016.08.17 차세대입찰 관련 작업 CDH
				"    AND B.ID = A.ID																			"   +
				"    AND A.SUPPLIER_NO = B.SUPPLIER_NO  																		"   +
				"    AND A.SUPPLIER_NO = c.SUPPLIER_NO																			"   +
				"    AND B.SUPPLIER_NO = c.SUPPLIER_NO  																		"   +
//				"	 AND C.SUPPLIER_NO = D.SUPPLIER_NO  																		"	+
				"    AND OWNER_PRIOR = '1'																						"	+
				"	 AND A.STATUS != 'D' 																						"	+
				"	 AND C.STATUS != 'D' 																						"	+
				" 	 order by  c.tax_sms_yn asc																					" ;		
			}
			ps = con.prepareStatement(sql);
//			System.out.println(sql);

			ps.setString(1, bizNO);
			ResultSet rs = ps.executeQuery();

			Object[] object = new Object[2];
			TaxCompanyVO comp = null;
			TaxPersonVO person = null;

			while(rs.next()){
				comp = new TaxCompanyVO();
				person = new TaxPersonVO();

				comp.setBiz_id(CommonUtil.justNullToBlank(rs.getString(1)));
				comp.setName(CommonUtil.justNullToBlank(rs.getString(2)));
				comp.setPresident_name(CommonUtil.justNullToBlank(rs.getString(3)));

				comp.setAddr(CommonUtil.justNullToBlank(rs.getString(4)));
				comp.setBiz_type(CommonUtil.justNullToBlank(rs.getString(5)));
				comp.setBiz_class(CommonUtil.justNullToBlank(rs.getString(6)));

				person.setId(CommonUtil.justNullToBlank(rs.getString(7)));
				person.setName(CommonUtil.justNullToBlank(rs.getString(8)));

				if(!"".equals(CommonUtil.justNullToBlank(rs.getString(9)))){
					person.setTel(CommonUtil.justNullToBlank(rs.getString(9)));
				}else{
					person.setTel(CommonUtil.justNullToBlank(rs.getString(11)));
				}

				if(!"".equals(CommonUtil.justNullToBlank(rs.getString(10)))){
					person.setEmail(CommonUtil.justNullToBlank(rs.getString(10)));
				}else{
					person.setEmail(CommonUtil.justNullToBlank(rs.getString(13)));
				}


				object = new Object[2];
				object[0] = comp;
				object[1] = person;

				compHt.put(comp.getBiz_id(), object);
			}
			ps.close();
			System.out.println("[END getAdminSupplierList in TaxSupplierDao]");
		}catch(SQLException e){
			throw new TaxInvoiceException(this, e);
		}finally{
			if(ps != null) ps.close();
		}
		return compHt;
	}

	public ArrayList getOutCompUserByBizId(String biz_id, Connection con) throws SQLException, TaxInvoiceException {
		System.out.println("[CompUserList]");
		ArrayList data = new ArrayList();
		//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
		String BUSI_ENC = CommonUtil.getString("BUSI_ENC");
		String sql = "";
		PreparedStatement ps = null;
		try {
			if(BUSI_ENC.equals("1")){
				sql =
//					"  SELECT DECODE_SF@USER_LINK(A.REGISTER_NO), DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME, 	"	+
					//2015.12.02 차세대입찰 관련 작업  CDH
					"  SELECT A.USER_ID, DECODE_SF64@D_EDI2SRM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME, 							"	+
//					"  SELECT A.USER_ID, DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME, 									"	+
					"         A.DEPT, A.TEL_NO,	A.MOBILE, 																			"	+
					"         A.EMAIL, B.KORNAME, A.SUPPLIER_NO,																	"   +
					//2015.12.02 차세대입찰 관련 작업  CDH  추가 상항 : 주민번호 빼기
//					" 		  'N' COMP_TYPE, DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM, A.tax_sms_yn 							"   +
//					" 		  'N' COMP_TYPE, DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM, A.tax_sms_yn 									"   +
					" 		  'N' COMP_TYPE, A.tax_sms_yn 																			"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
//					"    FROM TP_EXUSER_TBL@USER_LINK A,																			"   +
//					"         TP_SUPPLIER_TBL@USER_LINK B,																			"   +
//					"  	   	 TP_OWNERLST_TBL@USER_LINK C																			"   +
					"    FROM TP_EXUSER_TBL_VIEW A,																					"   +
					"         TP_SUPPLIER_TBL_VIEW B,																				"   +
					"  	   	  TP_OWNERLST_TBL_VIEW C																				"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
					"   WHERE BUSINESS_NO = ENCODE_SF64@D_EDI2SRM(?)																"   +
//					"   WHERE BUSINESS_NO = ENCODE_SF_SNM(?)																		"   +
					//2016.08.17 차세대입찰 관련 작업 CDH
					"     AND C.ID = B.ID																			"   +
					"     AND A.SUPPLIER_NO = B.SUPPLIER_NO																			"   +
					"     AND A.SUPPLIER_NO = C.SUPPLIER_NO																			"	+
					"    AND OWNER_PRIOR = '1'																						"	+
					"	 AND A.STATUS != 'D' 																						"	+
					"	 AND B.STATUS != 'D' 																						"	+
					" 	 order by  tax_sms_yn asc																					" ;
			}else{
				sql =
//					"  SELECT DECODE_SF@USER_LINK(A.REGISTER_NO), DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,	"	+
					//2015.12.02 차세대입찰 관련 작업  CDH
					"  SELECT A.USER_ID, BUSINESS_NO AS BUSINESS_NO,  A.USER_NAME, 													"	+
//					"  SELECT A.USER_ID, DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME, 									"	+
					"         A.DEPT, A.TEL_NO,	A.MOBILE, 																			"	+
					"         A.EMAIL, B.KORNAME, A.SUPPLIER_NO,																	"	+
					//2015.12.02 차세대입찰 관련 작업  CDH  추가 상항 : 주민번호 빼기
//					" 		  'N' COMP_TYPE, DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM, A.tax_sms_yn 							"   +
//					" 		  'N' COMP_TYPE, DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM, A.tax_sms_yn 									"   +
					" 		  'N' COMP_TYPE, A.tax_sms_yn 																			"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
//					"    FROM TP_EXUSER_TBL@USER_LINK A,																			"   +
//					"         TP_SUPPLIER_TBL@USER_LINK B,																			"   +
//					"  	   	 TP_OWNERLST_TBL@USER_LINK C																			"   +
					"    FROM TP_EXUSER_TBL_VIEW A,																					"   +
					"         TP_SUPPLIER_TBL_VIEW B,																				"   +
					"  	   	  TP_OWNERLST_TBL_VIEW C																				"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
					"   WHERE BUSINESS_NO = ?																						"   +
//					"   WHERE BUSINESS_NO = ENCODE_SF_SNM(?)																		"   +
					//2016.08.17 차세대입찰 관련 작업 CDH
					"     AND C.ID = B.ID																			"   +
					"     AND A.SUPPLIER_NO = B.SUPPLIER_NO																			"   +
					"     AND A.SUPPLIER_NO = C.SUPPLIER_NO																			" 	+
					"    AND OWNER_PRIOR = '1'																						"	+
					"	 AND A.STATUS != 'D' 																						"	+
					"	 AND B.STATUS != 'D' 																						"	+
					" 	 order by  tax_sms_yn asc																					" ;
			}
				ps = con.prepareStatement(sql);
				ps.setString(1, biz_id);
//			System.out.println(sql + biz_id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TaxPersonVO vo = new TaxPersonVO();
				System.out.println("start search get data");
				vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
				vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
				vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

				vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
				vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));
				vo.setHp(CommonUtil.justNullToBlank(rs.getString(6)));

				vo.setEmail(CommonUtil.justNullToBlank(rs.getString(7)));

				vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
//  추가 상항 : 주민번호 빼기
//				vo.setJumin_num(CommonUtil.justNullToBlank(rs.getString(11)));
//				vo.setTax_smsYN(CommonUtil.justNullToBlank(rs.getString(12)));
				vo.setTax_smsYN(CommonUtil.justNullToBlank(rs.getString(11)));

				data.add(vo);
			}
			ps.close();
		} catch (SQLException e) {
			throw new TaxInvoiceException(this, e);
		} finally {
			if (ps != null)
				ps.close();
		}
		System.out.println("[getCompUserList]");
		return data;
	}

	public ArrayList selectHanjunUserByName(String name, Connection con) throws SQLException, TaxInvoiceException {
		System.out.println("[selectHanjunUserByName]");
		ArrayList data = new ArrayList();

		PreparedStatement ps = null;
		try {
			String sql =
			//	" SELECT SUBSTR(SABUN, 3,10),  '사업자번호', NAME, "   +
			//	"        OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3 , OFC_TEL2,  HP, "   +
			//	" 	     E_MAIL,                '사업소명', '사업자번호',"   +
			//	" 	     'K',                    JUMIN_NO"   +
			//	"   FROM POMASTT1@USER_LINK A,"   +
			//	"        POOFCDT4@USER_LINK B"   +
			//	"  WHERE A.SOSOK_CD = B.OF_CODE"   +
			//	"	 AND (JIKGUB > '30' or JIKGUB is null)	"	+
			//	"    AND NAME = ?"  ;

			//2014.07.11
			"SELECT SUBSTR(SABUN, 3,10), '사업자번호', NAME, " +
			"		OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3, " +
			"		OFC_TEL2,  HP, E_MAIL, '사업소명', '사업자번호', 'K', '' JUMIN_NO " +
			"	FROM EAI_KEPCO_EMP_VIEW A, " +
			"		 EAI_KEPCO_DEP_VIEW B  " +
			"	WHERE A.SOSOK_CD = B.OF_CODE " +
			"		AND (JIKGUB > '30' or JIKGUB is null) " +
			"		AND NAME = ? ";

			System.out.println(sql);
				ps = con.prepareStatement(sql);
				ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TaxPersonVO vo = new TaxPersonVO();
//				System.out.println("start search get data");
				vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
				vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
				vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

				vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
				vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));
				vo.setHp(CommonUtil.justNullToBlank(rs.getString(6)));

				vo.setEmail(CommonUtil.justNullToBlank(rs.getString(7)));

				vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
				vo.setJumin_num(CommonUtil.justNullToBlank(rs.getString(11)));

				data.add(vo);
			}
			ps.close();
		} catch (SQLException e) {
			throw new TaxInvoiceException(this, e);
		} finally {
			if (ps != null)
				ps.close();
		}
		System.out.println("[selectHanjunUserByName]");
		return data;
	}


	public ArrayList selectHanjunUserById(String id, Connection con) throws SQLException, TaxInvoiceException {
		System.out.println("[selectHanjunUserById]");
		ArrayList data = new ArrayList();

		PreparedStatement ps = null;
		try {
			String sql =
			//	" SELECT SUBSTR(SABUN, 3,10),  '사업자번호', NAME, "   +
			//	"        OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3 , OFC_TEL2,  HP, "   +
			//	" 	     E_MAIL,                '사업소명', '사업자번호',"   +
			//	" 	     'K',                    JUMIN_NO"   +
			//	"   FROM POMASTT1@USER_LINK A,"   +
			//	"        POOFCDT4@USER_LINK B"   +
			//	"  WHERE A.SOSOK_CD = B.OF_CODE"   +
			//	"	 AND (JIKGUB > '30' or JIKGUB is null)	"	+
			//	"    AND SUBSTR(SABUN, 3,10) = ? "  ;
				//  2012.03.26
				//"    AND SABUN = (select GET_10DGT_SABUN( ? )  from dual) "  ;

			//2014.07.11
			"SELECT SUBSTR(SABUN, 3,10), '사업자번호', NAME, " +
			"		OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3, " +
			"		OFC_TEL2,  HP, E_MAIL, '사업소명', '사업자번호', 'K', '' JUMIN_NO " +
			"	FROM EAI_KEPCO_EMP_VIEW A, " +
			"		 EAI_KEPCO_DEP_VIEW B  " +
			"	WHERE A.SOSOK_CD = B.OF_CODE " +
			"		AND (JIKGUB > '30' or JIKGUB is null) " +
			"		AND SUBSTR(SABUN, 3,10) = ? ";

			System.out.println(sql);
				ps = con.prepareStatement(sql);
				ps.setString(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TaxPersonVO vo = new TaxPersonVO();
//				System.out.println("start search get data");
				vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
				vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
				vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

				vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
				vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));
				vo.setHp(CommonUtil.justNullToBlank(rs.getString(6)));

				vo.setEmail(CommonUtil.justNullToBlank(rs.getString(7)));

				vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
				vo.setJumin_num(CommonUtil.justNullToBlank(rs.getString(11)));

				data.add(vo);
			}
			ps.close();
		} catch (SQLException e) {
			throw new TaxInvoiceException(this, e);
		} finally {
			if (ps != null)
				ps.close();
		}
		System.out.println("[selectHanjunUserById]");
		return data;
	}

	public ArrayList selectUserByName(String name, String comp_code, Connection con) throws SQLException, TaxInvoiceException {
		System.out.println("[selectUserByName]");
		ArrayList data = new ArrayList();

		PreparedStatement ps = null;
		try {

			String sql =
			" SELECT EMP_NO,  '사업자번호', EMP_NAME,  ORG_NAME1 ||' '|| ORG_NAME2 ||' '|| ORG_NAME3,  FIRM_TELNO,  MOBILE, EMAIL, 'D' , EMP_NO " +
//2015.12.02 차세대입찰 관련 작업  CDH
//    		" FROM TP_INSA_TBL@USER_LINK " +
    		" FROM TP_INSA_TBL_VIEW " +
    		" WHERE FIRM_NO=? " +
    		"   AND EMP_NAME=? " +
    		"	AND pos_code > '2' "	+
    		"   AND TX_TYPE != 'DELETE' " ;

			 System.out.println(sql);
			ps = con.prepareStatement(sql);
			ps.setString(1, comp_code);
			ps.setString(2, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TaxPersonVO vo = new TaxPersonVO();
//				System.out.println("start search get data");
				vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
				vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
				vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

				vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
				vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));
				vo.setHp(CommonUtil.justNullToBlank(rs.getString(6)));

				vo.setEmail(CommonUtil.justNullToBlank(rs.getString(7)));

				vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(8)));
				vo.setJumin_num(CommonUtil.justNullToBlank(rs.getString(9)));

				data.add(vo);
			}
			ps.close();
		} catch (SQLException e) {
			throw new TaxInvoiceException(this, e);
		} finally {
			if (ps != null)
				ps.close();
		}
		System.out.println("[selectUserByName]");
		return data;
	}

	public ArrayList selectOutterUserByName(String name, Connection con) throws SQLException, TaxInvoiceException {
		System.out.println("[selectHanjunUserByName]");
		ArrayList data = new ArrayList();
		//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
		String BUSI_ENC = CommonUtil.getString("BUSI_ENC");
		String sql = "";
		PreparedStatement ps = null;
		try {
			if(BUSI_ENC.equals("1")){			
				sql =
//					"   SELECT DISTINCT DECODE_SF@USER_LINK(A.REGISTER_NO), DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,	"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
					"   SELECT DISTINCT A.USER_ID, DECODE_SF64@D_EDI2SRM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,							"   +
//					"   SELECT DISTINCT A.USER_ID, DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,									"   +
					"          A.DEPT, A.TEL_NO,	A.MOBILE,																				"   +
					"          A.EMAIL, B.KORNAME, A.SUPPLIER_NO,																			"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
					//추가 상항 : 주민번호 빼기
//					"  		  'N' COMP_TYPE, DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM													"   +
					"  		  'N' COMP_TYPE																									"   +
//					"  		  'N' COMP_TYPE, DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM															"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
//					"     FROM TP_EXUSER_TBL A,																								"   +
//					"          TP_SUPPLIER_TBL@USER_LINK B,																					"   +
//					"   	   	 TP_OWNERLST_TBL@USER_LINK C																				"   +
					"     FROM TP_EXUSER_TBL_VIEW A,																						"   +
					"          TP_SUPPLIER_TBL_VIEW B,																						"   +
					"   	   TP_OWNERLST_TBL_VIEW C																						"   +
					"    WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																				"   +
					"      AND A.SUPPLIER_NO = C.SUPPLIER_NO																				"   +
					//2016.08.17 차세대입찰 관련 작업 CDH
					"    AND C.ID = B.ID																			"   +
					" 	 AND A.USER_NAME = ? 																								"  ;
			}else{
				sql =
//					"   SELECT DISTINCT DECODE_SF@USER_LINK(A.REGISTER_NO), DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,	"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
					"   SELECT DISTINCT A.USER_ID, BUSINESS_NO AS BUSINESS_NO,  A.USER_NAME,												"   +
//					"   SELECT DISTINCT A.USER_ID, DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,									"   +
					"          A.DEPT, A.TEL_NO,	A.MOBILE,																				"   +
					"          A.EMAIL, B.KORNAME, A.SUPPLIER_NO,																			"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
					//추가 상항 : 주민번호 빼기
//					"  		  'N' COMP_TYPE, DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM													"   +
					"  		  'N' COMP_TYPE																									"   +
//					"  		  'N' COMP_TYPE, DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM															"   +
					//2015.12.02 차세대입찰 관련 작업  CDH
//					"     FROM TP_EXUSER_TBL A,																								"   +
//					"          TP_SUPPLIER_TBL@USER_LINK B,																					"   +
//					"   	   	 TP_OWNERLST_TBL@USER_LINK C"   +
					"     FROM TP_EXUSER_TBL_VIEW A,																						"   +
					"          TP_SUPPLIER_TBL_VIEW B,																						"   +
					"   	   TP_OWNERLST_TBL_VIEW C																						"   +
					"    WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																				"   +
					"      AND A.SUPPLIER_NO = C.SUPPLIER_NO																				"   +
					//2016.08.17 차세대입찰 관련 작업 CDH
					"    AND C.ID = B.ID																			"   +
					" 	 AND A.USER_NAME = ? 																								"  ;
			}
			System.out.println(sql);
				ps = con.prepareStatement(sql);
				ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TaxPersonVO vo = new TaxPersonVO();
//				System.out.println("start search get data");
				vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
				vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
				vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

				vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
				vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));
				vo.setHp(CommonUtil.justNullToBlank(rs.getString(6)));

				vo.setEmail(CommonUtil.justNullToBlank(rs.getString(7)));

				vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
//  추가 상항 : 주민번호 빼기
//				vo.setJumin_num(CommonUtil.justNullToBlank(rs.getString(11)));

				data.add(vo);
			}
			ps.close();
		} catch (SQLException e) {
			throw new TaxInvoiceException(this, e);
		} finally {
			if (ps != null)
				ps.close();
		}
		System.out.println("[selectHanjunUserByName]");
		return data;
	}



	 public void insertOftenUser(TaxPersonVO vo, Connection con) throws SQLException, TaxInvoiceException
	    {
	        System.out.println("[START INSERT ETS_TAX_OFTEN_USE_INFO_TB]");
	        PreparedStatement ps = null;
	        try{
	            String sql =
	                " INSERT INTO ETS_TAX_OFTEN_USE_INFO_TB "+
	                " (                                     "+
	                "        PARENT_ID,        LINK_ID      "+
	                " ) VALUES (                            "+
	                "   ?, ?                                "+
	                " )";

	            con.setAutoCommit(false);
	            ps = con.prepareStatement(sql);

	            ps.setString(1, vo.getParent_id());
	            ps.setString(2, vo.getLink_id());
	            ps.executeUpdate();
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END INSERT ETS_TAX_OFTEN_USE_INFO_TB]");
	    }

	 public void insertOftenComp(TaxPersonVO vo, String comp_code, Connection con) throws SQLException, TaxInvoiceException
	    {
	        System.out.println("[START INSERT ETS_TAX_OFTEN_USE_INFO_TB]");
	        PreparedStatement ps = null;
	        try{
	            String sql =
	                " INSERT INTO ETS_TAX_OFTEN_USE_INFO_TB "+
	                " (                                     "+
	                "   PARENT_ID, LINK_ID, comp_code  		"+
	                " ) VALUES (                            "+
	                "   ?, ?, ?                             "+
	                " )";

	            con.setAutoCommit(false);
	            ps = con.prepareStatement(sql);

	            ps.setString(1, vo.getParent_id());
	            ps.setString(2, vo.getLink_id());
	            ps.setString(3, comp_code);
	            ps.executeUpdate();
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END INSERT ETS_TAX_OFTEN_USE_INFO_TB]");
	    }

	    public String isExistOftenUser(TaxPersonVO vo, Connection con) throws SQLException, TaxInvoiceException{
	        System.out.println("[START isExistOftenUser TaxPersonDao]");
	        PreparedStatement ps = null;
	        String isExist = "N";
	        try{
	            String sql =
	                "  SELECT DECODE(COUNT(*),0,'N','Y') ISEXIST   " +
	                "    FROM ETS_TAX_OFTEN_USE_INFO_TB            " +
	                "   WHERE PARENT_ID = ?                        " +
	                "     AND LINK_ID = ?                          " ;
	            ps = con.prepareStatement(sql);
	            ps.setString(1, vo.getParent_id());
	            ps.setString(2, vo.getLink_id());
//	            System.out.println("vo.getParent_id():"+vo.getParent_id());
//	            System.out.println("vo.getLink_id():"+vo.getLink_id());
	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	                isExist = CommonUtil.justNullToBlank(rs.getString(1));
	                System.out.println("isExist:"+isExist);
	            }
	            rs.close();
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END isExistOftenUser TaxPersonDao]");
	        return isExist;
	    }


	    public ArrayList getSelectOftenUserList(String biz_id, String buyerCompCode, Connection con) throws SQLException, TaxInvoiceException
	    {
	        System.out.println("[START getUserListByBizId in getSelectOftenUserList]");
	        ArrayList data = new ArrayList();
	        PreparedStatement ps = null;
	        try{

	            if (buyerCompCode.equals("00")) {
		        	String sql =
		            //	" SELECT SUBSTR(SABUN, 3,10), '사업자번호',   NAME, " +
		            //	"        OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3 ,  OFC_TEL2, E_MAIL,   " +
		            //	"        'K',                  PARENT_ID,   LINK_ID "   +
		            //	"   FROM POMASTT1@USER_LINK A,"   +
		            //	"        POOFCDT4@USER_LINK B,"   +
		            //	"        ETS_TAX_OFTEN_USE_INFO_TB C"   +
		            //	"  WHERE LINK_ID = SUBSTR(SABUN, 3,10)"   +
		            //	"    AND A.SOSOK_CD = B.OF_CODE"   +
		            //	"    AND C.PARENT_ID = ?"  ;

		        		//2014.07.10
		        		"SELECT SUBSTR(SABUN, 3,10), '사업자번호', NAME, " +
		        		"		OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3, " +
		        		"		OFC_TEL2, E_MAIL, 'K', PARENT_ID, LINK_ID " +
		        		"	FROM EAI_KEPCO_EMP_VIEW A, " +
		        		"		 EAI_KEPCO_DEP_VIEW B, " +
		        		"		 ETS_TAX_OFTEN_USE_INFO_TB C " +
		        		"	WHERE LINK_ID = SUBSTR(SABUN, 3,10) " +
		        		"		AND A.SOSOK_CD = B.OF_CODE " +
		        		"		AND C.PARENT_ID = ? ";

		        	ps = con.prepareStatement(sql);
	            	ps.setString(1, biz_id);

	            } else {

		            String sql =
			            " SELECT EMP_NO,  '사업자번호', EMP_NAME,  ORG_NAME1 ||' '|| ORG_NAME2 ||' '|| ORG_NAME3,  FIRM_TELNO,  EMAIL, 'D', PARENT_ID, LINK_ID  " +
//2015.12.02 차세대입찰 관련 작업  CDH
//					" FROM TP_INSA_TBL@USER_LINK,		" +
					" FROM TP_INSA_TBL_VIEW,		" +
			            " 	  ETS_TAX_OFTEN_USE_INFO_TB 	" +
			            " WHERE LINK_ID = EMP_NO			" +
			            "  AND PARENT_ID = ?				" +
			            "  AND FIRM_NO= ?	 				" +
			            "  AND TX_TYPE != 'DELETE' 			" ;

	           		ps = con.prepareStatement(sql);
		            ps.setString(1, biz_id);
		            ps.setString(2, buyerCompCode);
	            }

	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	                TaxPersonVO vo = new TaxPersonVO();

	                vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
	                vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
	                vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

	                vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
	                vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));
	                vo.setEmail(CommonUtil.justNullToBlank(rs.getString(6)));

	                vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(7)));
	                vo.setParent_id(CommonUtil.justNullToBlank(rs.getString(8)));
	                vo.setLink_id(CommonUtil.justNullToBlank(rs.getString(9)));
	                data.add(vo);
	            }
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END getUserListByBizId in getSelectOftenUserList]");
	        return data;
	    }

	    public void deleteOftenUser(TaxPersonVO vo, Connection con) throws SQLException, TaxInvoiceException
	    {
	        System.out.println("[START deleteOftenUser]");
	        PreparedStatement ps = null;
	        try{
	            String sql =
	                " DELETE FROM ETS_TAX_OFTEN_USE_INFO_TB "+
	                "  WHERE PARENT_ID = ?                  "+
	                "    AND LINK_ID = ?                    ";

	            con.setAutoCommit(false);
	            ps = con.prepareStatement(sql);

	            ps.setString(1, vo.getParent_id());
	            ps.setString(2, vo.getLink_id());
	            ps.executeUpdate();
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END deleteOftenUser]");
	    }

	    public String getPersonIdByEmailName(String name, String email, Connection con) throws SQLException, TaxInvoiceException
	    {
	        System.out.println("[START getPersonIdByEmailName]");
	        PreparedStatement ps = null;
	        String id = "";
	        try{
	            String sql =
		            //	"   SELECT DECODE(COUNT(*),0,'',SABUN)"   +
		            //	"     FROM POMASTT1@USER_LINK"   +
		            //	"    WHERE NAME =  ?"   +
		            //	"      AND  E_MAIL = ?"   +
		            //	"    GROUP BY SABUN"  ;

	            	//2014.07.11
	            	"SELECT DECODE(COUNT(*),0,'',SABUN) FROM EAI_KEPCO_EMP_VIEW WHERE NAME = ? AND  E_MAIL = ? GROUP BY SABUN";

	            ps = con.prepareStatement(sql);
	            ps.setString(1, name);
	            ps.setString(2, email);
	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	                id = CommonUtil.justNullToBlank(rs.getString(1));
	            }
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END getPersonIdByEmailName]");
	        return id;
	    }

	    public TaxPersonVO selectPersonById(String id, String comp_type, Connection con) throws SQLException, TaxInvoiceException {
	        System.out.println("[START selectUserById]");
	        TaxPersonVO vo = new TaxPersonVO();
			//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
			String BUSI_ENC = CommonUtil.getString("BUSI_ENC");	        
	        PreparedStatement ps = null;
	        try{
	            String sql = "";
	            if(comp_type.equals("00")){
	            	sql =
//	            		"  SELECT SUBSTR(EMP_NO, 3,10),        '사업자번호',   USER_NAME, "   +
//	            		"         ORG_NAME DEPT,               A.TEL_NO,     MOBILE, " +
//	            		"         EMAIL,                       USER,         '사업자번호',    "   +
//	            		" 		  'K',                         DECODE_SF@USER_LINK(REGISTER_NO) JUMIN_NUM"   +
//	            		"    FROM TP_INUSER_TBL@USER_LINK A,"   +
//	            		" 	      TP_FIRMORG_TBL@USER_LINK B"   +
//	            		"   WHERE A.FIRM_NO = B.FIRM_NO"   +
//	            		"     AND A.ORG_ID = B.ORG_ID"   +
//	            		"     AND SUBSTR(EMP_NO, 3,10) = ?"  ;

	            	//	" SELECT SUBSTR(SABUN, 3,10),  '사업자번호', NAME, "   +
	            	//	"        OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3 ,  OFC_TEL2,    HP,"   +
	            	//	" 	   E_MAIL,                USER,        '사업자번호',"   +
	            	//	" 	   'K', JUMIN_NO"   +
	            	//	"   FROM POMASTT1@USER_LINK A,"   +
	            	//	"        POOFCDT4@USER_LINK B"   +
	            	//	"  WHERE A.SOSOK_CD = B.OF_CODE"   +
	            	//	"    AND SUBSTR(SABUN, 3,10) = ?"  ;
	            	//  2012.03.26
	            	//	"    AND SABUN = (select GET_10DGT_SABUN( ? )  from dual) "  ;

	            	//2014.07.11
	            	"SELECT SUBSTR(SABUN, 3,10), '사업자번호', NAME, " +
	            	"		OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3, " +
	            	"		OFC_TEL2, HP, E_MAIL, USER, '사업자번호', 'K', '' JUMIN_NO " +
	            	"	FROM EAI_KEPCO_EMP_VIEW A, " +
	            	"		 EAI_KEPCO_DEP_VIEW B " +
	            	"	WHERE A.SOSOK_CD = B.OF_CODE " +
	            	"		AND SUBSTR(SABUN, 3,10) = ? ";

	            	ps = con.prepareStatement(sql);
//		            System.out.println("get id:"+id);
		            ps.setString(1, id);

			    //남동발전
	            }else if(comp_type.equals("01")){
	            	sql =
	            		" SELECT EMP_NO,  '사업자번호', EMP_NAME,  ORG_NAME1 ||' '|| ORG_NAME2 ||' '|| ORG_NAME3,  FIRM_TELNO,  MOBILE, EMAIL, 'EBIZ_EX','사업자번호', 'D' , EMP_NO " +
//2015.12.02 차세대입찰 관련 작업  CDH
//					" FROM TP_INSA_TBL@USER_LINK " +
					" FROM TP_INSA_TBL_VIEW " +
	            		" WHERE EMP_NO = ? " +
	            		"   AND FIRM_NO='01' " +
	            		"   AND TX_TYPE != 'DELETE' " ;

	            	ps = con.prepareStatement(sql);
//		            System.out.println("get id:"+id);
		            ps.setString(1, id);

	            }else if(comp_type.equals("N")){
	    			if(BUSI_ENC.equals("1")){	
		            	sql =
//	            			"   SELECT DECODE_SF@USER_LINK(A.REGISTER_NO),  DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,	"   +
		            		//2015.12.02 차세대입찰 관련 작업  CDH
		            		"   SELECT A.USER_ID,  DECODE_SF64@D_EDI2SRM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,							"   +
//							"   SELECT A.USER_ID,  DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,									"   +
			            	"          A.DEPT,                              A.TEL_NO,	                                      A.MOBILE,		"   +
			            	"          A.EMAIL,                             B.KORNAME,                                        A.SUPPLIER_NO,"   +
			            	//2015.12.02 차세대입찰 관련 작업  CDH
			            	// 추가 상항 : 주민번호 빼기
//							"  		  'N' COMP_TYPE,                        DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM					"   +
							"  		  'N' COMP_TYPE																							"   +
//							"  		  'N' COMP_TYPE,                        DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM							"   +
							//2015.12.02 차세대입찰 관련 작업  CDH
//	            			"     FROM TP_EXUSER_TBL A,																						"   +
//	            			"          TP_SUPPLIER_TBL@USER_LINK B,																			"   +
//	            			"   	   	 TP_OWNERLST_TBL@USER_LINK C																		"   +
			            	"     FROM TP_EXUSER_TBL_VIEW A,																				"   +
			            	"          TP_SUPPLIER_TBL_VIEW B,																				"   +
			            	"   	   TP_OWNERLST_TBL_VIEW C																				"   +
			            	"    WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																		"   +
			            	"      AND A.SUPPLIER_NO = C.SUPPLIER_NO																		"   +
//	            			" 	 AND A.REGISTER_NO = ENCODE_SF@USER_LINK( ? )																"  ;
							//2016.08.17 차세대입찰 관련 작업 CDH
							"   AND C.ID = B.ID																			"   +
			            	" 	 AND A.USER_ID =  ? 																						"  ;
	    			}else{
	    				sql =
//	            			"   SELECT DECODE_SF@USER_LINK(A.REGISTER_NO),  DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,	"   +
	    					//2015.12.02 차세대입찰 관련 작업  CDH
	    					"   SELECT A.USER_ID,  BUSINESS_NO AS BUSINESS_NO,  A.USER_NAME,												"   +
//							"   SELECT A.USER_ID,  DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,									"   +
	    					"          A.DEPT,                              A.TEL_NO,	                                      A.MOBILE,		"   +
	    					"          A.EMAIL,                             B.KORNAME,                                        A.SUPPLIER_NO,"	+
	    					//2015.12.02 차세대입찰 관련 작업  CDH
	    					// 추가 상항 : 주민번호 빼기
//							"  		  'N' COMP_TYPE,                        DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM					"   +
	    					"  		  'N' COMP_TYPE																							"   +
//							"  		  'N' COMP_TYPE,                        DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM							"   +
	    					//2015.12.02 차세대입찰 관련 작업  CDH
//	            			"     FROM TP_EXUSER_TBL A,																						"   +
//	            			"          TP_SUPPLIER_TBL@USER_LINK B,																			"   +
//	            			"   	   	 TP_OWNERLST_TBL@USER_LINK C																		"   +
	    					"     FROM TP_EXUSER_TBL_VIEW A,																				"   +
	    					"          TP_SUPPLIER_TBL_VIEW B,																				"   +
	    					"   	   TP_OWNERLST_TBL_VIEW C																				"   +
	    					"    WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																		"   +
	    					"      AND A.SUPPLIER_NO = C.SUPPLIER_NO																		"   +
//	            			" 	 AND A.REGISTER_NO = ENCODE_SF@USER_LINK( ? )																"  ;
							//2016.08.17 차세대입찰 관련 작업 CDH
							"    AND C.ID = B.ID																			"   +
	    					" 	 AND A.USER_ID =  ? 																						"  ;
	    			}
	            	ps = con.prepareStatement(sql);
//		            System.out.println("get id:"+id);
		            ps.setString(1, id);
	            }

	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	                vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
	                vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
	                vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

	                vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
	                vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));
	                vo.setHp(CommonUtil.justNullToBlank(rs.getString(6)));

	                vo.setEmail(CommonUtil.justNullToBlank(rs.getString(7)));

	                vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
//  추가 상항 : 주민번호 빼기
//	                vo.setJumin_num(CommonUtil.justNullToBlank(rs.getString(11)));
	            }
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END selectUserById]");
	        return vo;
	    }

	    public TaxPersonVO selectPersonByIdnCompId(String comp_id, String id, String comp_type, Connection con) throws SQLException, TaxInvoiceException {
	        System.out.println("[START selectUserById]");
			//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
			String BUSI_ENC = CommonUtil.getString("BUSI_ENC");	
	        TaxPersonVO vo = new TaxPersonVO();
	        PreparedStatement ps = null;
	        try{
	            String sql = "";
	            if(comp_type.equals("K")){
	            	sql =
//	            		"  SELECT SUBSTR(EMP_NO, 3,10),        '사업자번호',   USER_NAME, "   +
//	            		"         ORG_NAME DEPT,               A.TEL_NO,     MOBILE, " +
//	            		"         EMAIL,                       USER,         '사업자번호',    "   +
//	            		" 		  'K',                         DECODE_SF@USER_LINK(REGISTER_NO) JUMIN_NUM"   +
//	            		"    FROM TP_INUSER_TBL@USER_LINK A,"   +
//	            		" 	      TP_FIRMORG_TBL@USER_LINK B"   +
//	            		"   WHERE A.FIRM_NO = B.FIRM_NO"   +
//	            		"     AND A.ORG_ID = B.ORG_ID"   +
//	            		"     AND SUBSTR(EMP_NO, 3,10) = ?"  ;
	            	//	" SELECT SUBSTR(SABUN, 3,10),  '사업자번호', NAME, "   +
	            	//	"        OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3 ,  OFC_TEL2,    HP,"   +
	            	//	" 	   E_MAIL,                USER,        '사업자번호',"   +
	            	//	" 	   'K', JUMIN_NO, 'K'    "   +
	            	//	"   FROM POMASTT1@USER_LINK A,"   +
	            	//	"        POOFCDT4@USER_LINK B"   +
	            	//	"  WHERE A.SOSOK_CD = B.OF_CODE"   +
	            	//	"    AND SUBSTR(SABUN, 3,10) = ?"  ;
	            	//  2012.03.26
	            	//"    AND  SABUN = (select GET_10DGT_SABUN( ? )  from dual) "  ;

	            	//2014.07.11
	            	"SELECT SUBSTR(SABUN, 3,10), '사업자번호', NAME, " +
	            	"		OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3, " +
	            	"		OFC_TEL2, HP, E_MAIL, USER, '사업자번호', 'K', '' JUMIN_NO " +
	            	"	FROM EAI_KEPCO_EMP_VIEW A, " +
	            	"		 EAI_KEPCO_EMP_VIEW B " +
	            	"	WHERE A.SOSOK_CD = B.OF_CODE " +
	            	"		AND (JIKGUB > '30' or JIKGUB is null) " +
	            	"		AND SUBSTR(SABUN, 3,10) = ? ";

	            	ps = con.prepareStatement(sql);
//		            System.out.println("get id:"+id);
		            ps.setString(1, id);
	            }else if(comp_type.equals("N")){
	    			if(BUSI_ENC.equals("1")){	            	
		            	sql =
//	            			"   SELECT DECODE_SF@USER_LINK(A.REGISTER_NO),  DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,		"   +
		            		//2015.12.02 차세대입찰 관련 작업  CDH
							"   SELECT A.USER_ID,  DECODE_SF64@D_EDI2SRM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,								"   +
//							"   SELECT A.USER_ID,  DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,										"   +
			            	"          A.DEPT,                              A.TEL_NO,	                                      A.MOBILE,			"   +
			            	"          A.EMAIL,                             B.KORNAME,                                        A.SUPPLIER_NO,	"   +
			            	//2015.12.02 차세대입찰 관련 작업  CDH
			            	//추가 상항 : 주민번호 빼기
//							"  		  'N' COMP_TYPE,                        DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM						"   +
							"  		  'N' COMP_TYPE																								"   +
//							"  		  'N' COMP_TYPE,                        DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM								"   +
			            	"		  ,B.MOB_NUMBER  																							"	+	// 20101011 추가
			            	//2015.12.02 차세대입찰 관련 작업  CDH
//	            			"     FROM TP_EXUSER_TBL A, 																						"   +
//	            			"          TP_SUPPLIER_TBL@USER_LINK B, 																			"   +
//			            	"   	   TP_OWNERLST_TBL@USER_LINK C 																				"   +
			            	"     FROM TP_EXUSER_TBL_VIEW A, 																					"   +
			            	"          TP_SUPPLIER_TBL_VIEW B, 																					"   +
			            	"   	   TP_OWNERLST_TBL_VIEW C 																					"   +
			            	"    WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																			"   +
			            	"      AND A.SUPPLIER_NO = C.SUPPLIER_NO																			"   +
							//2016.08.17 차세대입찰 관련 작업 CDH
							"      	AND C.ID = B.ID																			"   +
			            	//2015.12.02 차세대입찰 관련 작업  CDH
			            	"      AND B.BUSINESS_NO = ENCODE_SF64@D_EDI2SRM(?)																	"   +
//							"      AND B.BUSINESS_NO = ENCODE_SF_SNM(?)																			"   +
//	     			       	" 	   AND A.REGISTER_NO = ENCODE_SF@USER_LINK( ? )																	"  ;
			            	" 	   AND A.USER_ID =  ? 																							"  ;
	    			}else{
	    				sql =
//	            			"   SELECT DECODE_SF@USER_LINK(A.REGISTER_NO),  DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,		"   +
	    					//2015.12.02 차세대입찰 관련 작업  CDH
	    					"   SELECT A.USER_ID,  BUSINESS_NO AS BUSINESS_NO,  A.USER_NAME,													"   +
//							"   SELECT A.USER_ID,  DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,										"   +
	    					"          A.DEPT,                              A.TEL_NO,	                                      A.MOBILE,			"   +
	    					"          A.EMAIL,                             B.KORNAME,                                        A.SUPPLIER_NO,	"   +
	    					//2015.12.02 차세대입찰 관련 작업  CDH
	    					//추가 상항 : 주민번호 빼기
//							"  		  'N' COMP_TYPE,                        DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM						"   +
	    					"  		  'N' COMP_TYPE																								"   +
//							"  		  'N' COMP_TYPE,                        DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM								"   +
	    					"		  ,B.MOB_NUMBER  																							"	+	// 20101011 추가
	    					//2015.12.02 차세대입찰 관련 작업  CDH
//	            			"     FROM TP_EXUSER_TBL A, 																						"   +
//	            			"          TP_SUPPLIER_TBL@USER_LINK B, 																			"   +
//			            	"   	   TP_OWNERLST_TBL@USER_LINK C 																				"   +
	    					"     FROM TP_EXUSER_TBL_VIEW A, 																					"   +
	    					"          TP_SUPPLIER_TBL_VIEW B, 																					"   +
	    					"   	   TP_OWNERLST_TBL_VIEW C 																					"   +
	    					"    WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																			"   +
	    					"      AND A.SUPPLIER_NO = C.SUPPLIER_NO																			"   +
							//2016.08.17 차세대입찰 관련 작업 CDH
							"      	AND C.ID = B.ID																			"   +
	    					//2015.12.02 차세대입찰 관련 작업  CDH
	    					"      AND B.BUSINESS_NO = ?																						"   +
//							"      AND B.BUSINESS_NO = ENCODE_SF_SNM(?)																			"   +
//	     			       	" 	   AND A.REGISTER_NO = ENCODE_SF@USER_LINK( ? )																	"  ;
	    					" 	   AND A.USER_ID =  ? 																							"  ;
	    			}
	            	ps = con.prepareStatement(sql);
//		            System.out.println("get id:"+id);
//		            System.out.println("get comp_id:"+comp_id);
		            ps.setString(1, comp_id);
		            ps.setString(2, id);
	            }

	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	                vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
	                vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
	                vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));
	                vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
	                vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));

	                if(!"".equals(CommonUtil.justNullToBlank(rs.getString(6)))){ // 20101011 추가
	                	vo.setHp(CommonUtil.justNullToBlank(rs.getString(6)));
	                }else{
	                	vo.setHp(CommonUtil.justNullToBlank(rs.getString(11)));
	                }

	                vo.setEmail(CommonUtil.justNullToBlank(rs.getString(7)));
	                vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(10)));
//  추가 상항 : 주민번호 빼기
//	                vo.setJumin_num(CommonUtil.justNullToBlank(rs.getString(11)));
	            }
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END selectUserById]");
	        return vo;
	    }

	    public TaxPersonVO selectExUserByLoingId(String id, Connection con) throws SQLException, TaxInvoiceException {
	        System.out.println("[START selectExUserByLoingId]");
			//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
			String BUSI_ENC = CommonUtil.getString("BUSI_ENC");	
			String sql = "";
	        TaxPersonVO vo = new TaxPersonVO();
	        PreparedStatement ps = null;
	        try{
    			if(BUSI_ENC.equals("1")){
		            sql =
//	  		          	"    SELECT DECODE_SF@USER_LINK(A.REGISTER_NO), DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,	"	+
		            	//2015.12.02 차세대입찰 관련 작업  CDH
						"    SELECT A.USER_ID, DECODE_SF64@D_EDI2SRM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,							"   +
//						"    SELECT A.USER_ID, DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,									"   +
		            	"           A.DEPT, A.TEL_NO,	A.MOBILE,																		"   +
		            	"           A.EMAIL, B.KORNAME, A.SUPPLIER_NO,																	"   +
		            	//2015.12.02 차세대입찰 관련 작업  CDH
		            	//추가 상항 : 주민번호 빼기
//          		 	"   		  'N' COMP_TYPE, DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM										"   +
		            	"   		  'N' COMP_TYPE																						"   +
//	      		      	"   		  'N' COMP_TYPE, DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM												"   +
		            	//2015.12.02 차세대입찰 관련 작업  CDH
//	          		  	"      FROM TP_EXUSER_TBL A,																					"   +
//	        	    	"           TP_SUPPLIER_TBL@USER_LINK B,																		"   +
//	      		      	"    	   	TP_OWNERLST_TBL@USER_LINK C																			"   +
		            	"      FROM TP_EXUSER_TBL_VIEW A,																				"   +
		            	"           TP_SUPPLIER_TBL_VIEW B,																				"   +
		            	"    	   	TP_OWNERLST_TBL_VIEW C																				"   +
		            	"     WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																		"   +
		            	"       AND A.SUPPLIER_NO = C.SUPPLIER_NO																		"   +
						//2016.08.17 차세대입찰 관련 작업 CDH
						"     AND C.ID = B.ID																			"   +
		            	"  	  AND A.USER_ID = ? 																						"   ;
    			}else{
    				sql =
//	  		          	"    SELECT DECODE_SF@USER_LINK(A.REGISTER_NO), DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,	"	+
    					//2015.12.02 차세대입찰 관련 작업  CDH
    					"    SELECT A.USER_ID, BUSINESS_NO AS BUSINESS_NO,  A.USER_NAME,												"   +
//						"    SELECT A.USER_ID, DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,									"   +
    					"           A.DEPT, A.TEL_NO,	A.MOBILE,																		"   +
    					"           A.EMAIL, B.KORNAME, A.SUPPLIER_NO,																	"   +
    					//2015.12.02 차세대입찰 관련 작업  CDH
    					//추가 상항 : 주민번호 빼기
//          		 	"   		  'N' COMP_TYPE, DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM										"   +
    					"   		  'N' COMP_TYPE																						"   +
//	      		      	"   		  'N' COMP_TYPE, DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM												"   +
    					//2015.12.02 차세대입찰 관련 작업  CDH
//	          		  	"      FROM TP_EXUSER_TBL A,																					"   +
//	        	    	"           TP_SUPPLIER_TBL@USER_LINK B,																		"   +
//	      		      	"    	   	TP_OWNERLST_TBL@USER_LINK C																			"   +
    					"      FROM TP_EXUSER_TBL_VIEW A,																				"   +
    					"           TP_SUPPLIER_TBL_VIEW B,																				"   +
    					"    	   	TP_OWNERLST_TBL_VIEW C																				"   +
    					"     WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																		"   +
    					"       AND A.SUPPLIER_NO = C.SUPPLIER_NO																		"   +
						//2016.08.17 차세대입찰 관련 작업 CDH
						"     AND C.ID = B.ID																			"   +
    					"  	  AND A.USER_ID = ? 																						"   ;
    			}
	            	ps = con.prepareStatement(sql);
//		            System.out.println("get id:"+id);
		            ps.setString(1, id);

	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	                vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
	                vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
	                vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

	                vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
	                vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));
	                vo.setEmail(CommonUtil.justNullToBlank(rs.getString(6)));

	                vo.setHp(CommonUtil.justNullToBlank(rs.getString(8)));

	                vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(9)));
//  추가 상항 : 주민번호 빼기
//	                vo.setJumin_num(CommonUtil.justNullToBlank(rs.getString(10)));
	            }
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END selectExUserByLoingId]");
	        return vo;
	    }



	    public TaxPersonVO selectPersonByLoginID(String id, String comp_type, Connection con) throws SQLException, TaxInvoiceException {
	        System.out.println("[START selectPersonByLoginID]");
			//20160229 TP_SUPPLIER_TBL_VIEW의 BUSINESS_NO 암복호화 유무에 따른 분기처리 CDH
			String BUSI_ENC = CommonUtil.getString("BUSI_ENC");	
	        TaxPersonVO vo = new TaxPersonVO();
	        PreparedStatement ps = null;
	        try{
	            String sql = "";
	            if(comp_type.equals("K")){
	            	sql =

	            		//2014.07.11
	            		"SELECT SUBSTR(SABUN, 3,10), '사업자번호', NAME, " +
	            		"		OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3, " +
	            		"		OFC_TEL2, E_MAIL, HP, USER, 'K', '' JUMIN_NO " +
	            		"	FROM EAI_KEPCO_EMP_VIEW A, " +
	            		"		 EAI_KEPCO_DEP_VIEW B " +
	            		"	WHERE A.SOSOK_CD = B.OF_CODE " +
	            		"		AND SUBSTR(SABUN, 3,10) = SUBSTR(?, 3,10) ";

	            		//	" SELECT SUBSTR(SABUN, 3,10),  '사업자번호', NAME, "   +
	            		//	"        OF_HAN1 ||' '|| OF_HAN2 ||' '|| OF_HAN3 ,   OFC_TEL2,   E_MAIL,"   +
	            		//	" 	       HP,             USER,        "   +
	            		//	" 	   'K', JUMIN_NO"   +
	            		//	"   FROM POMASTT1@USER_LINK A,"   +
	            		//	"        POOFCDT4@USER_LINK B"   +
	            		//	"  WHERE A.SOSOK_CD = B.OF_CODE"   +
	            		//	"    AND SUBSTR(SABUN, 3,10) = SUBSTR(?, 3,10) " ;
	            		//  2012.03.26
	            		//"    AND SABUN = (select GET_10DGT_SABUN( SUBSTR(?, 3,10) )  from dual) " ;

	            		//SUBSTR(?, 3,10)"  ;
//	            		"   SELECT SUBSTR(EMP_NO, 3,10),        '사업자번호', USER_NAME,"   +
//	            		"          ORG_NAME DEPT, A.TEL_NO,     EMAIL,"   +
//	            		"           MOBILE, USER,         "   +
//	            		"  		'K',           DECODE_SF@USER_LINK(REGISTER_NO) JUMIN_NUM"   +
//	            		"     FROM TP_INUSER_TBL@USER_LINK A,"   +
//	            		"  	     TP_FIRMORG_TBL@USER_LINK B"   +
//	            		"    WHERE A.FIRM_NO = B.FIRM_NO"   +
//	            		"      AND A.ORG_ID = B.ORG_ID"   +
//	            		" 	 AND A.USER_ID = ? "  ;
	            	ps = con.prepareStatement(sql);
//		            System.out.println("get id:"+id);
		            ps.setString(1, id);

		        //남동발전
	            }else if(comp_type.equals("D")){
	            	sql =
	            		" SELECT EMP_NO,  '사업자번호', EMP_NAME,  ORG_NAME1 ||' '|| ORG_NAME2 ||' '|| ORG_NAME3,  FIRM_TELNO,  EMAIL, MOBILE, 'EBIZ_EX', 'D' , EMP_NO " +
//2015.12.02 차세대입찰 관련 작업  CDH
//					" FROM TP_INSA_TBL@USER_LINK " +
					" FROM TP_INSA_TBL_VIEW " +
	            		" WHERE EMP_NO = SUBSTR(?, 3,10) " +
	            		"   AND FIRM_NO='01' " +
	            		"   AND TX_TYPE != 'DELETE' " ;

	            	ps = con.prepareStatement(sql);
//		            System.out.println("get id:"+id);
		            ps.setString(1, id);

	            }else if(comp_type.equals("N")){
	    			if(BUSI_ENC.equals("1")){
		            	sql =
//		            		"    SELECT DECODE_SF@USER_LINK(A.REGISTER_NO), DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,	"   +
		            		//2015.12.02 차세대입찰 관련 작업  CDH
		            		"    SELECT A.USER_ID, DECODE_SF64@D_EDI2SRM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,							"   +
//		            		"    SELECT A.USER_ID, DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,									"   +
		            		"           A.DEPT, A.TEL_NO,	A.EMAIL,																		"   +
		            		"           A.MOBILE,  B.KORNAME,  																				"   +
		            		//2015.12.02 차세대입찰 관련 작업  CDH
		            		//추가 상항 : 주민번호 빼기
//		            		"   		  'N' COMP_TYPE, DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM										"   +
		            		"   		  'N' COMP_TYPE																						"   +
//		            		"   		  'N' COMP_TYPE, DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM												"   +
		            		//2015.12.02 차세대입찰 관련 작업  CDH
//		            		"      FROM TP_EXUSER_TBL A,																					"   +
//	 		           		"           TP_SUPPLIER_TBL@USER_LINK B,																		"   +
//	 		           		"    	   	TP_OWNERLST_TBL@USER_LINK C																			"   +
		            		"      FROM TP_EXUSER_TBL_VIEW A,																				"   +
		            		"           TP_SUPPLIER_TBL_VIEW B,																				"   +
		            		"    	   	TP_OWNERLST_TBL_VIEW C																				"   +
		            		"     WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																		"   +
		            		"       AND A.SUPPLIER_NO = C.SUPPLIER_NO																		"   +
							//2016.08.17 차세대입찰 관련 작업 CDH
							"     AND C.ID = B.ID																			"   +
		            		" 	  AND A.USER_ID = ? 																						"   ;
	    			}else{
	    				sql =
//		            		"    SELECT DECODE_SF@USER_LINK(A.REGISTER_NO), DECODE_SF@USER_LINK(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,	"   +
	    					//2015.12.02 차세대입찰 관련 작업  CDH
	    					"    SELECT A.USER_ID, BUSINESS_NO AS BUSINESS_NO,  A.USER_NAME,												"   +
//		            		"    SELECT A.USER_ID, DECODE_SF_SNM(BUSINESS_NO) AS BUSINESS_NO,  A.USER_NAME,									"   +
	    					"           A.DEPT, A.TEL_NO,	A.EMAIL,																		"   +
	    					"           A.MOBILE,  B.KORNAME,  																				"   +
	    					//2015.12.02 차세대입찰 관련 작업  CDH
	    					//추가 상항 : 주민번호 빼기
//		            		"   		  'N' COMP_TYPE, DECODE_SF64@D_EDI2SRM(A.REGISTER_NO) JUMIN_NUM										"   +
	    					"   		  'N' COMP_TYPE																						"   +
//		            		"   		  'N' COMP_TYPE, DECODE_SF_SNM(A.REGISTER_NO) JUMIN_NUM												"   +
	    					//2015.12.02 차세대입찰 관련 작업  CDH
//		            		"      FROM TP_EXUSER_TBL A,																					"   +
//	 		           		"           TP_SUPPLIER_TBL@USER_LINK B,																		"   +
//	 		           		"    	   	TP_OWNERLST_TBL@USER_LINK C																			"   +
	    					"      FROM TP_EXUSER_TBL_VIEW A,																				"   +
	    					"           TP_SUPPLIER_TBL_VIEW B,																				"   +
	    					"    	   	TP_OWNERLST_TBL_VIEW C																				"   +
	    					"     WHERE A.SUPPLIER_NO = B.SUPPLIER_NO																		"   +
	    					"       AND A.SUPPLIER_NO = C.SUPPLIER_NO																		"   +
							//2016.08.17 차세대입찰 관련 작업 CDH
							"     AND C.ID = B.ID																			"   +
	    					" 	  AND A.USER_ID = ? 																						"   ;
	    				
	    			}
	            	ps = con.prepareStatement(sql);
//		            System.out.println("get id:"+id);
		            ps.setString(1, id);
	            }
	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	                vo.setId(CommonUtil.justNullToBlank(rs.getString(1)));
	                vo.setBiz_id(CommonUtil.justNullToBlank(rs.getString(2)));
	                vo.setName(CommonUtil.justNullToBlank(rs.getString(3)));

	                vo.setDept(CommonUtil.justNullToBlank(rs.getString(4)));
	                vo.setTel(CommonUtil.justNullToBlank(rs.getString(5)));
	                vo.setEmail(CommonUtil.justNullToBlank(rs.getString(6)));

	                vo.setHp(CommonUtil.justNullToBlank(rs.getString(7)));

	                vo.setComp_type(CommonUtil.justNullToBlank(rs.getString(9)));
//  추가 상항 : 주민번호 빼기
//	                vo.setJumin_num(CommonUtil.justNullToBlank(rs.getString(10)));
	            }
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END selectUserById]");
	        return vo;
	    }

	    public String selectDeckeyByLoginID(String id, Connection con) throws SQLException, TaxInvoiceException {
	        System.out.println("[START selectDeckeyByLoginID]");
	        String key = null;
	        PreparedStatement ps = null;
	        try{
//2015.12.02 차세대입찰 관련 작업  CDH
//	            String sql = "  select DECODE_SF64@D_EDI2SRM(keycode) as key from tp_deskey@USER_LINK "   ;
	            String sql = "  select DECODE_SF64@D_EDI2SRM(keycode) as key from tp_deskey_view "   ;

            	ps = con.prepareStatement(sql);
//	            System.out.println("get id:"+id);
//	            ps.setString(1, id);

	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	            	key = CommonUtil.justNullToBlank(rs.getString(1));

	            }
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END selectDeckeyByLoginID]");
	        return key;
	    }

	    public boolean getAdminInfo(String id, String compcode, Connection con) throws SQLException, TaxInvoiceException {
	        System.out.println("[START getAdminInfo]");
	        boolean adminYN = false;
	        PreparedStatement ps = null;
	        try{
	            String sql = "SELECT ADMIN_ID FROM ETS_TAX_ADMIN_INFO_TB WHERE ADMIN_ID=? AND COMP_CODE=? "   ;

            	ps = con.prepareStatement(sql);
            	ps.setString(1, id);
            	ps.setString(2, compcode);
	            ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	            	adminYN = true;
	            }
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END getAdminInfo]");
	        return adminYN;
	    }

	    public ArrayList getAdminInfoList(String compcode, Connection con) throws SQLException, TaxInvoiceException {
	        System.out.println("[START getAdminInfoList]");
	        ArrayList adminlist =  new ArrayList();
	        PreparedStatement ps = null;
	        try{
	            String sql = "SELECT ADMIN_ID, NAME, COMP_CODE FROM ETS_TAX_ADMIN_INFO_TB " ;
	            if (!compcode.equals("")) sql = sql + " WHERE COMP_CODE=? "   ;

            	ps = con.prepareStatement(sql);
            	if (!compcode.equals("")) ps.setString(1, compcode);
                ResultSet rs = ps.executeQuery();
	            while(rs.next()){
	            	String[] rec = {rs.getString(1),rs.getString(2),rs.getString(3)};
	            	adminlist.add(rec);
	            }
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END getAdminInfoList]");
	        return adminlist;
	    }

	    public void insertAdminInfo(String id, String name, String compcode, Connection con) throws SQLException, TaxInvoiceException {
	        System.out.println("[START insertAdminInfo]");
	        PreparedStatement ps = null;
	        try{
	            String sql = "INSERT INTO ETS_TAX_ADMIN_INFO_TB(ADMIN_ID, NAME, COMP_CODE) VALUES(?, ?, ?) " ;

            	ps = con.prepareStatement(sql);
            	ps.setString(1, id);
            	ps.setString(2, name);
            	ps.setString(3, compcode);
                ps.executeQuery();
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END insertAdminInfo]");
	    }

	    public void deleteAdminInfo(String id, String compcode, Connection con) throws SQLException, TaxInvoiceException {
	        System.out.println("[START deleteAdminInfo]");
	        PreparedStatement ps = null;
	        try{
	            String sql = "DELETE ETS_TAX_ADMIN_INFO_TB WHERE ADMIN_ID=? AND COMP_CODE=? " ;

            	ps = con.prepareStatement(sql);
            	ps.setString(1, id);
            	ps.setString(2, compcode);
                ps.executeQuery();
	            ps.close();
	        }catch(SQLException e){
	            throw new TaxInvoiceException(this, e);
	        }finally{
	            if(ps != null) ps.close();
	        }
	        System.out.println("[END deleteAdminInfo]");
	    }
}
